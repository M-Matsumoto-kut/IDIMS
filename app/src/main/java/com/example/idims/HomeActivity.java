package com.example.idims;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.idims.Menu.MenuActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;


import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



//AWSコールバックを使用するため、インターフェースを使用
public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback, AWSConnect.CallBackTask {

    //マップ表示用のクラス
    private MapView mMapView;
    //マップ表示に必要な文字列
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    //一定レベル以上の災害が発生しているかを検索するメソッド
    public boolean disasterOccureing = false;
    public boolean do_Wave;
    public boolean do_Landsride;
    public boolean do_Thounder;

    //現在位置の位置情報を取得するために必要なクラス
    private FusedLocationProviderClient fusedLocationClient;

    //警告を発する災害レベルの定義
    private int maxlevel = 3; //災害レベル



    //パーミッションの許可コード
    static final int REQUEST_CODE = 1;

    //災害を検知する距離と避難所の距離
    static final double disasterDistance = 200; //200メートル
    static final double shelterDistance = 50; //50メートル

    //現在から24時間前までの緯度、経度、レベルと種類を格納する
    private ArrayList<Double> disLat; //緯度
    private ArrayList<Double> disLng; //経度
    private ArrayList<Integer> disCon; //災害種類
    private ArrayList<Integer> dislevel; //レベル
    private ArrayList<String> disTime; //時間

    //現在地付近で発生した災害の緯度経度などを格納する
    private ArrayList<Double> nearLat; //緯度
    private ArrayList<Double> nearLng; //経度
    private ArrayList<Integer> nearCon; //災害種類
    private ArrayList<Integer> nearLevel; //レベル
    private ArrayList<String> nearTime; //時間

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //MySQLへ接続して24時間前までの災害を検索
        AWSConnect con = new AWSConnect(); //AWSConnectインスタンス宣言
        String url = "http://ec2-44-198-252-235.compute-1.amazonaws.com/disastersearch_now.php"; //24時間前の災害を検索するSQLクエリの入ったphpファイルのurl
        String startTime = getYesterday(); //検索開始時刻,まぁ24時間前
        String endTime = getToday(); //現在時刻を取得
        StringBuffer dist = new StringBuffer().append("value=").append(startTime).append(",").append(endTime); //startTime,endTime の形で条件を送る
        con.setOnCallBack(this); //コールバックメソッド呼び出し
        con.execute(url, String.valueOf(dist)); //AWS接続


        //Mapviewを使用して表示,災害発生個所のピン立てはマップ表示の際に行う
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);

        //災害状況の発生有無を表示するテキスト表示
        setTextdisasterOccurrences();

        //メニュー画面への遷移
        Button menuButton = (Button) findViewById(R.id.button_Menu);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

    }

    //SQLによる検索結果を処理
    public void CallBack(String result){ //stringには緯度,経度,災害レベル,災害種類,災害発生時間,...で格納されている
        String[] tmp = result.split(","); //,(カンマ)を区切り文字として文字型配列に格納
        int alpha = 5; //SQLで要求する要素数
        for(int i = 0; i < tmp.length - alpha; i += alpha){
            disLat.add(Double.parseDouble(tmp[i])); //緯度を追加
            disLng.add(Double.parseDouble(tmp[i + 1])); //経度を追加
            dislevel.add(Integer.parseInt(tmp[i + 2])); //災害レベルを追加
            disCon.add(Integer.parseInt(tmp[i + 3])); //災害種類を追加
            disTime.add(tmp[4]); //災害時間を追加
        }
    }


    //現在日時の取得
    public String getToday(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/hh/mm/ss"); //年月日と時間分秒のデータに変換
        String str = sdf.format(date); //文字型に変換
        return str.replace("/", "");         //文字列にスラッシュ(//)が入っているので除去して返す
    }

    //24時間前の時間を取得
    protected String getYesterday(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance(); //24時間前の時間を取得するためにCalendarクラスに渡す
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, -24); //24時間前の時間を取得
        Date pastDate = calendar.getTime(); //Date型に戻す
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/hh/mm/ss"); //年月日と時間分秒のデータに変換
        String str = sdf.format(pastDate); //文字型に変換
        return str.replace("/", "");         //文字列にスラッシュ(//)が入っているので除去して返す
    }

    //引数に渡された数字に応じて災害名の文字列を返すメソッド
    private String getDisasterName(int num){
        if(num == 1){return "津波";}
        else if(num == 2){return "土砂崩れ";}
        else if(num == 3){return "雷";}
        else{return "識別エラー";}
    }


    //画面上部の災害発生状況を表すテキストのセット
    protected void setTextdisasterOccurrences(){
        TextView textViewEva = (TextView) findViewById(R.id.textView_DisasterOccurrence);
        if(disasterOccureing){
            textViewEva.setText("24時間以内の災害発生はありません");
        }else{
            textViewEva.setText("災害が発生しています");
        }

        //検知時刻のテキスト表示
        TextView textViewTime = (TextView) findViewById(R.id.textView_CurrentTime);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        String str = sdf.format(date);
        textViewTime.setText(str + "現在");
    }


    //位置情報が取得できなかった時にテキストを表示する
    protected void cantGetLocation(){
        TextView textView = (TextView) findViewById(R.id.textView_LocErr);
        textView.setText("位置情報が取得できません");
    }

    //現在地と災害との距離を図り、bool型を返すメソッド
    protected boolean judgeDisasterDistance(double nowLat, double nowLng, double disLat, double disLng){ //左から現在地緯度,現在地経度、災害発生緯度、災害発生経度を表す
        //結果を返す溜めの配列。1番目に2点間の距離、2番目に始点からみた方位角、3番目に終点から見た方位角が格納される
        float[] result = new float[3];
        Location.distanceBetween(nowLat, nowLng, disLat, disLng, result); //2点間の距離を緯度経度から割り出すメソッド(Locationクラスの標準搭載)
        if(result[0] <= disasterDistance){ //もし　2点間の距離 <= 災害検知距離　ならば真を返す
            return true;
        }
        return false;
    }


    //ここから下は地図の表示に必要
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if(mapViewBundle == null){
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) { //マップのセッティングを行う 災害発生個所の表示、避難所の表示を主にここで行う
        //現在地付近の災害の表示

            //現在位置の測定
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            Log.d("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            //測位の精度を上げる
            /*
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            */
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //Android専用のダイアログで位置情報の使用許可を求める
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        double nowLat = location.getLatitude();
                        double nowLng = location.getLongitude();
                        Log.d("処理直前", "どこまで行ったのかな？");
                        Marker markerNow = map.addMarker(new MarkerOptions().position(new LatLng(nowLat, nowLng)).snippet("NowLocation \n " + location.getLatitude() + " , " + location.getLongitude()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        Log.d("ooooooooooooooooooooooooooooooooooo", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");


                        //デバッグ用のピン map.addMarker(new MarkerOptions().position(new LatLng(33, 133)));
                        Log.d("Home", "onSuccess: " + location.getLatitude() + " , " + location.getLongitude());


                        //最後に位置情報と発生災害との距離を図り、一定距離にある災害のみ表示する
                        try{
                            for(int i = 0; i < disLat.size(); i++){
                                if(judgeDisasterDistance(nowLat, nowLng, disLat.get(i), disLng.get(i))){ //もし自身の位置より近くにあればその災害をマップのマーカーに表示して表示する災害情報を格納
                                    if(disCon.get(i) == 1){ //津波の場合青色に設定
                                        Marker markerDisaster = map.addMarker(new MarkerOptions().position(new LatLng(disLat.get(i), disLng.get(i))).title(getDisasterName(disCon.get(i)) + ":レベル" + String.valueOf(dislevel.get(i))).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))); //マーカーをセット
                                    }else if(disCon.get(i) == 2){ //地すべりの場合緑
                                        Marker markerDisaster = map.addMarker(new MarkerOptions().position(new LatLng(disLat.get(i), disLng.get(i))).title(getDisasterName(disCon.get(i)) + ":レベル" + String.valueOf(dislevel.get(i))).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))); //マーカーをセット
                                    }else if(disCon.get(i) == 3){ //雷の場合黄色
                                        Marker markerDisaster = map.addMarker(new MarkerOptions().position(new LatLng(disLat.get(i), disLng.get(i))).title(getDisasterName(disCon.get(i)) + ":レベル" + String.valueOf(dislevel.get(i))).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))); //マーカーをセット
                                    }
                                    nearLat.add(disLat.get(i)); //緯度
                                    nearLng.add(disLng.get(i)); //経度
                                    nearCon.add(disCon.get(i)); //災害種類
                                    nearLevel.add(dislevel.get(i)); //災害レベル
                                    nearTime.add(disTime.get(i)); //時間
                                    if(dislevel.get(i) >= maxlevel){ //もし一定レベル以上の災害が発生しているならば警告を出す
                                        disasterOccureing = true;
                                    }
                                }
                            }
                        }catch(NullPointerException e){ //DBからデータを受け取っていない場合災害の表示を行わない

                            Log.d("失敗しています","Nullを取得しました");
                        }


                    }else{
                        cantGetLocation();
                        Log.d("馬鹿垂", "失敗だよ");
                    }
                }
            });

    }




    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}