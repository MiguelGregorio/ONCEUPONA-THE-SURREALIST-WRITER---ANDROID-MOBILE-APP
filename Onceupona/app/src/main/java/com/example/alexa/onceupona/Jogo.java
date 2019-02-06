package com.example.alexa.onceupona;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Jogo extends Activity {

    TextView jg, rd, lw;
    EditText frase;
    private String texto = "";
    Button btn;
    String n;
    int rondaAtual = 1, pos=0;
    int TodosJogaram[] = new int[CustumSettings.NJogadores];
    static SpannableStringBuilder builder = new SpannableStringBuilder();
    String txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);

        jg = findViewById(R.id.Jogador);
        rd = findViewById(R.id.ronda);
        lw = findViewById(R.id.LastWords);
        frase = findViewById(R.id.texto);
        btn = findViewById(R.id.Next);

        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(CustumSettings.NCaracteres); //define o numero maximo de caracteres consuante o escolhido anteriormente
        frase.setFilters(filterArray);

        if(CustumSettings.ran == 1){ //caso o modo de jogo seja aleatorio escolhe o jogador a começar aleatoriamente
            Random rand = new Random();
            pos= rand.nextInt(CustumSettings.NJogadores-1);
            TodosJogaram[pos]=1;
        }



        jg.setText(EditarJogadores.jogadores.get(pos).getNome()); //insere o nome do jogador
        jg.setTextColor(EditarJogadores.jogadores.get(pos).getCor());//insere a cor do jogador
        n = rondaAtual + "/"+CustumSettings.NRondas;
        rd.setText(n); //insere o numero de rondas
        lw.setText("");
        frase.setTextColor(EditarJogadores.jogadores.get(pos).getCor()); //insere a cor no texto

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CustumSettings.ran == 0)
                    RoundRobin();
                else
                    Aleatorio();
            }
        });

    }


    public void RoundRobin() {
        try {
            String trimmed = frase.getText().toString().trim(); //remove os espaços a mais
            int palavras = trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length; //conta o numero de palavras
            if (palavras < 2) { //caso não tenha palavras suficientes
                throw new Exception();
            }

            texto += frase.getText().toString(); //insere a frase no texto
            txt = frase.getText().toString() + " ";
            ForegroundColorSpan fcs = new ForegroundColorSpan(EditarJogadores.jogadores.get(pos).getCor());
            SpannableString ss = new SpannableString(txt);
            ss.setSpan(fcs,0,txt.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(ss);

            if(jg.getText().toString().equals(EditarJogadores.jogadores.get(CustumSettings.NJogadores-1).getNome())){ //caso já seja o utlimo jogador a jogar
                pos=0; //volta ao primeiro
                rondaAtual += 1;
                n = rondaAtual+"/"+CustumSettings.NRondas; //muda a ronda
            }else //caso ainda nao tenham jogado todos
                pos++; //proximo jogador

            jg.setText(EditarJogadores.jogadores.get(pos).getNome()); //insere o proximo jogador
            jg.setTextColor(EditarJogadores.jogadores.get(pos).getCor()); //insere a cor do proximo jogador
            rd.setText(n); //insere a ronda em que se encontra


            int espacos = 0, caract = 0;

            for(char c : frase.getText().toString().toCharArray()){ //obtem as 2 ultimas palavras do jogador anterior
                if (espacos==palavras-2)
                    break;
                caract++;
                if(c == ' ')
                    espacos++;
            }

            lw.setText(frase.getText().toString().substring(caract, frase.getText().toString().length())); //insere as 2 ultimas palavras na textview

            frase.setText("");
            frase.setTextColor(EditarJogadores.jogadores.get(pos).getCor());

            if(rd.getText().toString().equals(CustumSettings.NRondas+"/"+CustumSettings.NRondas) && jg.getText().toString().equals(EditarJogadores.jogadores.get(CustumSettings.NJogadores-1).getNome())) { //Caso ja tenham jogado todos os jogadores e já é a ultima ronda
                btn.setText(R.string.End);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { //começa a class End e envia o texto juntamente
                        texto += frase.getText().toString();
                        txt = frase.getText().toString();
                        ForegroundColorSpan fcs = new ForegroundColorSpan(EditarJogadores.jogadores.get(pos).getCor());
                        SpannableString ss = new SpannableString(txt);
                        ss.setSpan(fcs,0,txt.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        builder.append(ss);
                        Intent iGo = new Intent(Jogo.this,End.class);
                        iGo.putExtra("texto",texto);
                        startActivity(iGo);
                    }
                });
            }


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Insira pelo menos 2 palavras", Toast.LENGTH_SHORT).show();
        }

    }

    int verifica(int[] v){
        for(int j=0;j<v.length;j++)
            if(v[j]==0)
                return 0;
        return 1;
    }

    public void Aleatorio(){
        try {
            String trimmed = frase.getText().toString().trim();
            int palavras = trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;
            if (palavras < 2) {
                throw new Exception();
            }

            texto += frase.getText().toString();
            txt = frase.getText().toString() + " ";
            ForegroundColorSpan fcs = new ForegroundColorSpan(EditarJogadores.jogadores.get(pos).getCor());
            SpannableString ss = new SpannableString(txt);
            ss.setSpan(fcs,0,txt.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(ss);

            if(verifica(TodosJogaram)==1){ //caso todos os jogadores ja tenham jogado(ronda ja acabou)
                pos= (int)Math.floor(Math.random()*(CustumSettings.NJogadores)); //proximo jogador random
                rondaAtual += 1; //muda a ronda
                n = rondaAtual+"/"+CustumSettings.NRondas;
                for(int j=0;j<CustumSettings.NJogadores;j++){ //mete o vetor dos jogaodres que ja jogaram a 0's
                    TodosJogaram[j]=0;
                }
                TodosJogaram[pos] = 1; //altera a posição do jogador em que se encontra para 1(ou seja, ja jogou nesta ronda)
            }else{ //cao ainda nao tenham jogado todos(ronda ainda nao acabou)
                while(TodosJogaram[pos]==1){ //escolhe o proximo jogador random diferente de um que ja tenha jogado
                    Random rand = new Random();
                    pos= rand.nextInt(CustumSettings.NJogadores);
                }
                TodosJogaram[pos] = 1;
            }

            jg.setText(EditarJogadores.jogadores.get(pos).getNome());
            jg.setTextColor(EditarJogadores.jogadores.get(pos).getCor());
            rd.setText(n);

            int espacos = 0, caract = 0;

            for(char c : frase.getText().toString().toCharArray()){
                if (espacos==palavras-2)
                    break;
                caract++;
                if(c == ' ')
                    espacos++;
            }

            lw.setText(frase.getText().toString().substring(caract, frase.getText().toString().length()));

            frase.setText("");
            frase.setTextColor(EditarJogadores.jogadores.get(pos).getCor());

            if(verifica(TodosJogaram)==1 && rd.getText().toString().equals(CustumSettings.NRondas+"/"+CustumSettings.NRondas)){
                btn.setText(R.string.End);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        texto += frase.getText().toString();
                        txt = frase.getText().toString();
                        ForegroundColorSpan fcs = new ForegroundColorSpan(EditarJogadores.jogadores.get(pos).getCor());
                        SpannableString ss = new SpannableString(txt);
                        ss.setSpan(fcs,0,txt.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        builder.append(ss);
                        Intent iGo = new Intent(Jogo.this,End.class);
                        iGo.putExtra("texto",texto);
                        startActivity(iGo);
                    }
                });
            }

        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Insira pelo menos 2 palavras", Toast.LENGTH_SHORT).show();
        }
    }

}
