package com.example.alexa.onceupona;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EscolhaModo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolhamodo);
    }

    public void BtCust(View v){
        startActivity(new Intent(this,CustumSettings.class));
    }

    public void BtBasic(View v){
        CustumSettings.NJogadores = 2;
        CustumSettings.NCaracteres = 140;
        CustumSettings.NRondas = 5;
        startActivity(new Intent(this,EditarJogadores.class));
    }
}
