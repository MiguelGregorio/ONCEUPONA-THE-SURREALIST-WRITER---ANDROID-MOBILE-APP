package com.example.alexa.onceupona;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    public static DBHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbh = new DBHelper(this);
    }

    public void BtStart(View v){
        startActivity(new Intent(this,EscolhaModo.class));
    }

    public void Historico(View v){ startActivity(new Intent(this,Historico.class));}
}
