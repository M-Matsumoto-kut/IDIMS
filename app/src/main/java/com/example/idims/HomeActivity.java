package com.example.idims;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationRequest;
import android.nfc.FormatException;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {

    //マップ表示用のクラス
    private MapView mMapView;
    //マップ表示に必要な文字列
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    //通知を行う災害が発生しているかを表す
    public boolean do_Wave;
    public boolean do_Landsride;
    public boolean do_Thounder;

    //現在位置の位置情報を取得するために必要なクラス
    private FusedLocationProviderClient fusedLocationClient;

    //現在地付近を表示しているかを表すbool型
    public boolean currentLoc = true;

    //パーミッションの許可コード
    static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        //Mapviewの使用に重要
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);



        //災害状況の発生有無を表示するテキスト表示
        setTextdisasterOccurrences();
        //避難勧告のテキスト表示を行う
        setTextevacuationAdvisory();

        //現在地付近の災害情報を検索する
        if(currentLoc){searchCurrentDisaster();}


    }

    //現在地付近の災害情報を検索するメソッド
    protected void searchCurrentDisaster(){
        //現在時刻と24時間前の時間を取得
        String currentTime = getToday();
        String beforeTime = getYesterday();
        //データベースに接続
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://idims-database-dev-1", "admin", "Numasa_89");
            Statement state = con.createStatement();
            String sql = "SELECT disaster_x, disaster_y, disaster_level, disaster_class, disaster_time FROM disaster WHERE disaster_time >=" + beforeTime;
            ResultSet rs = state.executeQuery(sql);
            while(rs.next()){

            }

        }catch (SQLException ex){

        }

    }

    //現在日時の取得
    public String getToday(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); //年月日のみのデータに変換
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); //年月日のみのデータに変換
        String str = sdf.format(pastDate); //文字型に変換
        return str.replace("/", "");         //文字列にスラッシュ(//)が入っているので除去して返す
    }


    //画面上部の災害発生状況を表すテキストのセット
    protected void setTextdisasterOccurrences(){
        TextView textViewEva = (TextView) findViewById(R.id.textView_DisasterOccurrence);
        if(do_Wave == false && do_Landsride == false && do_Thounder == false){
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

    //画面下部の避難勧告表示のテキストのセット
    protected void setTextevacuationAdvisory(){
        TextView textView = (TextView) findViewById(R.id.textView_Eva);
        if(do_Wave == true || do_Landsride == true){
            textView.setText("避難勧告が発生しています");
        }else{
            textView.setText("避難勧告は発生していません");
        }
    }

    //位置情報が取得できなかった時にテキストを表示する
    protected void cantGetLocation(){
        TextView textView = (TextView) findViewById(R.id.textView_LocErr);
        textView.setText("位置情報が取得できません");
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
        if(currentLoc){
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
                        Log.d("処理直前", "どこまで行ったのかな？");
                        Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).snippet("NowLocation \n " + location.getLatitude() + " , " + location.getLongitude()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                        Log.d("ooooooooooooooooooooooooooooooooooo", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                        marker.showInfoWindow();

                        //デバッグ用のピン map.addMarker(new MarkerOptions().position(new LatLng(33, 133)));
                        Log.d("Home", "onSuccess: " + location.getLatitude() + " , " + location.getLongitude());
                    }else{
                        cantGetLocation();
                        Log.d("馬鹿垂", "失敗だよ");
                    }
                }
            });
            //現在地から近い災害の情報を探す
                //sql文による探索
                //緯度経度から距離を計算し、範囲内である場合マーカーをマップに追加、災害情報もそこに追加
                //sql文により取得した災害が変数maxlevelを超えていた場合画面上部のテキストも更新
            //避難所の表示
                //sql文による探索
                //緯度経度から距離を計算し、範囲内である場合かつ一定以上の災害が発生している場合マーカーをマップに追加(spinpet?も使用して災害情報を入力する,あとcliclmarkerのやつも。)

        }else { //現在地を表示しないのでそのまま検索する
            //sqlを検索
                //住所を検索し、該当箇所に災害が発生している場合情報を追加する
                //災害が発生していた場合に上のテキストに表示する
        }


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