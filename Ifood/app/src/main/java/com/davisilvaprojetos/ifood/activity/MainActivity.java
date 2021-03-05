package com.davisilvaprojetos.ifood.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.davisilvaprojetos.ifood.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                abrirAutenticacao();
            }
        },3000);
    }

    private void abrirAutenticacao(){
        Intent i = new Intent(MainActivity.this, AutenticacaoActivity.class);
        startActivity(i);
        finish();
    }
}