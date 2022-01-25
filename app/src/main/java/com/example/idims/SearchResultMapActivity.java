package com.example.idims;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.maps.OnMapReadyCallback;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchResultMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    //マップ表示用のクラス
    private MapView mMapView;
    //マップ表示に必要な文字列
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    //検索結果のリスト
    private int areaNumber; //検索地方
    private ArrayList<Double> listLat = new ArrayList<>(); //緯度
    private ArrayList<Double> listLng = new ArrayList<>(); //経度
    private ArrayList<Integer> listLevel = new ArrayList<>(); //災害レベル
    private ArrayList<Integer> listConDis = new ArrayList<>(); //災害種類
    private ArrayList<Double> listTime = new ArrayList<>(); //発生時刻
    private ArrayList<String> listArea = new ArrayList<>(); //発生地域
    private ArrayList<Integer> disasterNum = new ArrayList<>(); //災害番号

    //マーカーのリスト
    private ArrayList<Marker> marker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_map);

        //検索結果リストからデータを受け取る
        Intent searchResultList = getIntent();
        //listLat = searchResultList.getExtra("resultLat"); //緯度
        //listLng = searchResultList.getExtra("resultLng"); //経度
        listLevel = searchResultList.getIntegerArrayListExtra("resultLevel"); //災害レベル
        listConDis = searchResultList.getIntegerArrayListExtra("resultConDis"); //災害種類
        //listTime = searchResultList.getExtra("resultTime"); //発生時刻
        listArea = searchResultList.getStringArrayListExtra("resultArea"); //発生地域
        disasterNum = searchResultList.getIntegerArrayListExtra("disasterNumber"); //検索番号
        areaNumber = searchResultList.getIntExtra("areaNumber", 0); //検索地方


        //MapViewを使用するのに必要
        Bundle mapViewBundle = null;
        if(savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.mapView_Result);
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);

        //検索する地方付近にカメラをズームする




        //結果リストに戻る
        Button backResultList = (Button) findViewById(R.id.button_BackList);
        backResultList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish(); //一応そのまま戻ってほしいのでこうした
            }
        });

        //検索条件画面に戻る
        Button backDisasterSearch = (Button) findViewById(R.id.button_DisasterSA);
        backDisasterSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SearchResultMapActivity.this, DisasterSearchActivity.class);
                startActivity(intent); //新しいインテントを開始する
            }
        });

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
    public void onMapReady(GoogleMap map) { //マップのセッティング
        //受け取ったリストからマーカーを作成する
        for(int i = 0; i < listLat.size(); i++){
            marker.add(map.addMarker(new MarkerOptions().position(new LatLng(listLat.get(i), listLng.get(i)))));
            marker.get(i).setTag(i); //現在の番号をタグとしてセットする
            map.setOnMarkerClickListener(this);
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

    //マーカーをクリックした際に動作するメソッド
    @Override
    public boolean onMarkerClick(final Marker marker){
        Integer disasterNum = (Integer) marker.getTag(); //タグ番号を取ってくる
        if (disasterNum != null){ //タグ番号がnullでない場合
            //画面下部レイアウトに災害情報の詳細を書き出す
            TextView disasterInfo = (TextView) findViewById(R.id.textView_DisasterInfo);
            disasterInfo.setText(getDisasterName(listConDis.get(disasterNum)) + "  [レベル" + listLevel.get(disasterNum) + "] " + listArea.get(disasterNum)); //災害情報の概要
            TextView timeInfo = (TextView) findViewById(R.id.textView_TimeInfo);
            timeInfo.setText("発生日時: " + getNowTimeString(listTime.get(disasterNum))); //発生時間
            TextView latlngInfo = (TextView) findViewById(R.id.textView_InfoLatLng);
            latlngInfo.setText("発生予測地点 \n 緯度:" + listLat.get(disasterNum) + "\n 経度: " + listLng.get(disasterNum));
        }
        return false;
    }

    //引数に渡された数字に応じて災害名の文字列を返すメソッド
    private String getDisasterName(int num){
        if(num == 1){return "津波";}
        else if(num == 2){return "土砂崩れ";}
        else if(num == 3){return "雷";}
        else{return "識別エラー";}
    }

    //~年~月~日~:~の表記として返すメソッド
    private String getNowTimeString(Double time){
        String before = String.valueOf(time); //ダブル型を文字列型に変換
        String year = before.substring(0, 3); //年を取得
        String month = before.substring(4, 5); //月
        String day = before.substring(6, 7); //日
        String hour = before.substring(8, 9); //時
        String minute = before.substring(10, 11); //分
        StringBuffer str = new StringBuffer().append(year).append("年").append(month).append("月").append(day).append("日").append(hour).append(":").append(minute); //結合
        return String.valueOf(str);

    }
}