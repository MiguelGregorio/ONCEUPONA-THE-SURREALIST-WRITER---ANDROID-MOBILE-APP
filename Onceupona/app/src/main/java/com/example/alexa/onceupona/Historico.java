package com.example.alexa.onceupona;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Historico extends Activity {

    ListView ls;
    ArrayList<HistoricoItem> list;
    HistoricoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_historico);

        ls = (ListView)findViewById(R.id.histls);

        list = new ArrayList<>();
        adapter = new HistoricoAdapter(this,R.layout.historico_item,list); //cria um adapter para os elementos do Historico
        ls.setAdapter(adapter);
        updateHistorico();

        ls.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) { //caso fique a persionar durante um tempo num elemento da ListView aparece um dialog com as opções de Guardar e Apagar
                CharSequence[] items = {"Guardar", "Apagar"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(Historico.this,R.style.myDialog));
                dialog.setTitle(R.string.WTD);
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // Guardar
                            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);

                            android.app.AlertDialog.Builder dialogSave = new android.app.AlertDialog.Builder(new ContextThemeWrapper(Historico.this,R.style.myDialog));
                            dialogSave.setMessage(R.string.FileName)
                                    .setTitle(R.string.Save);

                            final EditText edittext = new EditText(getApplicationContext());

                            dialogSave.setView(edittext);

                            dialogSave.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        String filename = edittext.getText().toString() + ".txt";
                                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),filename);
                                        FileOutputStream fp = new FileOutputStream(file);
                                        fp.write(list.get(position).getTexto().getBytes());
                                        fp.close();
                                        Toast.makeText(Historico.this,R.string.SaveSuc,Toast.LENGTH_SHORT).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            dialogSave.show();

                        } else {
                            // Apagar
                            showDialogDelete(list.get(position).getId());
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    //Dialog da opção "Apagar"
    private void showDialogDelete(final int position){

        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(new ContextThemeWrapper(Historico.this,R.style.myDialog)); //cria um novo dialog de Aviso

        dialogDelete.setTitle(R.string.Warning);
        dialogDelete.setMessage(R.string.sure);
        dialogDelete.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { //caso confirme chama a função da DBHelper para apagar o elemento
                try {
                    MainActivity.dbh.deleteData(position);
                    Toast.makeText(getApplicationContext(), R.string.ApagSuc,Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    e.printStackTrace();
                }
                updateHistorico();
            }
        });

        dialogDelete.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }


    public void updateHistorico(){ //atualiza o historico
        Cursor cursor = MainActivity.dbh.getData("SELECT * FROM ONCEUPONA"); //seleciona todos os elementos da BD
        list.clear(); //limpa a lista
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String data = cursor.getString(1);
            String texto = cursor.getString(2);
            list.add(new HistoricoItem(id,data,texto)); //insere todos os elementos na lista
        }
        Collections.reverse(list); //inverte a ordem da lista para aparecer o mais recente primeiro
        adapter.notifyDataSetChanged(); //avisa o adapater para este atualizar a ListView
    }
}
