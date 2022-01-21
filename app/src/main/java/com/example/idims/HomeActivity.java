package com.example.idims;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;


import android.nfc.FormatException;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback{

    private MapView mMapView;

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    //通知を行う災害が発生しているかを表す
    public boolean do_Wave;
    public boolean do_Landsride;
    public boolean do_Thounder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Mapviewの使用に重要
        Bundle mapViewBundle = null;
        if(savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);

        //災害状況の発生有無を表示するテキスト表示

        //避難勧告のテキスト表示を行う
        setTextevacuationAdvisory();
    }

    //画面上部の災害発生状況を表すテキストのセット
    protected void setTextdisasterOccurrences(){
        TextView textView = (TextView) findViewById(R.id.textView_DisasterOccurrence);
        if(do_Wave == false && do_Landsride == false && do_Thounder == false){
            textView.setText("");
        }
    }

    //画面下部の避難勧告表示のテキストのセット
    protected void setTextevacuationAdvisory(){
        TextView textViewEva = (TextView) findViewById(R.id.textView_Eva);
        if(do_Wave == true || do_Landsride == true){
            textViewEva.setText("避難勧告が発生しています");
        }else{
            textViewEva.setText("避難勧告は発生していません");
        }

        //検知時刻のテキスト表示
        TextView textViewTime = (TextView) findViewById(R.id.textView_CurrentTime);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        String str = sdf.format(date);
        textViewTime.setText(str);

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