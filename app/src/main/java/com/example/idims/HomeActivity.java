package com.example.idims;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;


import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationRequest;
import android.nfc.FormatException;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //現在位置の測定
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //測位の精度を上げる
        /*
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        */
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {

                }
            }
        });

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
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
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