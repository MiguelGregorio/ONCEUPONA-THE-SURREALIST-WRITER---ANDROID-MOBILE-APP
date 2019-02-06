package com.example.alexa.onceupona;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class JogadorAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Jogador> Jogadores;

    public JogadorAdapter(Context context, int layout, ArrayList<Jogador> Jogadores){
        this.context = context;
        this.layout = layout;
        this.Jogadores = Jogadores;
    }


    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return Jogadores.size();
    }

    @Override
    public Object getItem(int position) {
        return Jogadores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView cor;
        EditText nome;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = new ViewHolder();

        if(row == null){ //caso não exista
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null); //cria um novo elemento com o layout jogador

            holder.cor = (ImageView) row.findViewById(R.id.color);
            holder.nome = (EditText) row.findViewById(R.id.nome);
            row.setTag(holder);
        }else{ //caso ja exista
            holder = (ViewHolder) row.getTag();
        }

        Jogador jogador = Jogadores.get(position); //vai buscar o jogador à lista de jogadores

        holder.nome.setHint(jogador.getNome()); //insere o nome na EditText

        holder.cor.setId(position); //insere a cor pretendida na ImageView

        return row;
    }
}
