package com.example.alexa.onceupona;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import java.util.ArrayList;

public class EditarJogadores extends Activity {

    ListView ls;
    public static ArrayList<Jogador> jogadores;
    JogadorAdapter adapter;
    private String[] nomes = {"Inácio","Ronaldo","Goucha","Toy","Maria Leal","Jesus","Camões","Cristina Ferreira","Sara Sampaio","Bruno de Carvalho","Marcelo","Emplastro","Dono disto tudo","Figo","Amália"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_jogadores);

        jogadores = new ArrayList<>(); //cria uma nova lista com Njogadores
        for(int i=0;i<CustumSettings.NJogadores;i++){
            Jogador j = new Jogador("Jogador " + i,R.color.colorAccent);
            jogadores.add(j);
        }


        ls = findViewById(R.id.ListView);
        adapter = new JogadorAdapter(this,R.layout.jogador,jogadores); //Pega na lista de jogadores e insere cada um no Layout do jogador
        ls.setAdapter(adapter); //Pega no adapter de cima e insere cada elemento numa posição da ListView
        adapter.notifyDataSetChanged();

    }


    //colorPicker src = "https://android-arsenal.com/details/1/5429"
    public void ColorChange(final View v){
        ColorPickerDialog.Builder builder = new ColorPickerDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK); //cria um dialog para o utilizador escolher a cor do jogador
        builder.setTitle("Escolha uma cor");
        builder.setPositiveButton(getString(R.string.confirm), new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) { //caso carregue em "confirmar" a cor é alterada na imageview selecionada e na sua posição na ArrayList jogadores
                v.getBackground().setColorFilter(envelope.getColor(),PorterDuff.Mode.SRC_ATOP);
                jogadores.get(v.getId()).setCor(envelope.getColor());
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.attachAlphaSlideBar(); // attach AlphaSlideBar
        builder.attachBrightnessSlideBar(); // attach BrightnessSlideBar
        builder.show(); // show dialog
    }

    public void Comecar(View v){
        EditText edt;
        for(int i=0;i<=ls.getLastVisiblePosition()-ls.getFirstVisiblePosition();i++){ //atualiza todos os nomes consuante o que se encontra na EditText
            v = ls.getChildAt(i);
            edt = (EditText)v.findViewById(R.id.nome);
            if(!(edt.getText().toString().matches("")))
                jogadores.get(i).setNome(edt.getText().toString());
        }
        startActivity(new Intent(this,Jogo.class));
    }

    public void RandomName(View v){ //escolhe um nome random dentro do array de nomes
        RelativeLayout rl = (RelativeLayout)v.getParent();
        EditText edt = (EditText)rl.getChildAt(1);
        int r = (int)Math.floor(Math.random()*(nomes.length));
        edt.setText(nomes[r]);
    }
}
