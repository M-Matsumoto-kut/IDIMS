package com.example.idims;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.idims.Area.AreaList;
import com.example.idims.databinding.FragmentFirstBinding;

public class Error extends AppCompatActivity {

    //private FragmentFirstBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

         */
        setContentView(R.layout.activity_area_list);

    }

    //毎動作時に実行
    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_area_list);
    }

    /*
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

    }

     */


}