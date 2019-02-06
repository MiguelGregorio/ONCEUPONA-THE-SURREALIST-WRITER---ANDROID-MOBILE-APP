package com.example.alexa.onceupona;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoricoAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<HistoricoItem> lista;

    public HistoricoAdapter(Context context, int layout, ArrayList<HistoricoItem> lista){
        this.lista = lista;
        this.layout = layout;
        this.context = context;
    }


    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView datas;
        TextView mensagens;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = new ViewHolder();

        if(row == null){ //caso não exista
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null); //cria um novo elemento com o layout historico_item

            holder.datas = (TextView) row.findViewById(R.id.datahist);
            holder.mensagens = (TextView) row.findViewById(R.id.textohist);
            row.setTag(holder);
        }else{ //caso ja exista
            holder = (ViewHolder) row.getTag();
        }

        HistoricoItem hi = lista.get(position); //vai buscar o HistoricoItem à lista

        holder.datas.setText(hi.getData()); //insere a data no TextView pretendido
        holder.mensagens.setText(hi.getTexto()); //insere o texto no TextView pretendido

        return row;
    }
}
