package com.example.alexa.onceupona;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Switch;

import java.util.Random;

public class CustumSettings extends Activity {

    NumberPicker jogador;
    NumberPicker caracteres;
    NumberPicker rondas;
    public static int NJogadores;
    public static int NCaracteres;
    public static int NRondas;
    CheckBox checkBox;
    public static int ran = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custum_settings);

        jogador = (NumberPicker) findViewById(R.id.np1);
        caracteres = (NumberPicker) findViewById(R.id.np2);
        rondas = (NumberPicker) findViewById(R.id.np3);
        checkBox = (findViewById(R.id.checkbox));

        jogador.setMinValue(2);
        jogador.setMaxValue(10);
        jogador.setWrapSelectorWheel(true);

        caracteres.setMinValue(20);
        caracteres.setMaxValue(500);
        caracteres.setWrapSelectorWheel(true);

        rondas.setMinValue(2);
        rondas.setMaxValue(20);
        rondas.setWrapSelectorWheel(true);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox.isChecked()) //remove a opção de escolher um numero de rondas caso o utilizador escolha a opção "aleatorio"
                    rondas.setVisibility(View.INVISIBLE);
                else
                    rondas.setVisibility(View.VISIBLE);
            }
        });
    }

    public void onClick(View v){
        Switch NoOrder = findViewById(R.id.sw);
        if(NoOrder.isChecked()) //variavel global para verificar o tipo de rondas(aleatorio ou nao)
            ran = 1;
        NJogadores = jogador.getValue();
        NCaracteres = caracteres.getValue();
        Random rand = new Random();
        if(checkBox.isChecked()){ //caso nº de rondas seja random
            NRondas = rand.nextInt(20) + 2; //numero de rondas random entre 2 e 20
        }else
            NRondas = rondas.getValue();
        startActivity(new Intent(this,EditarJogadores.class));
    }

}
