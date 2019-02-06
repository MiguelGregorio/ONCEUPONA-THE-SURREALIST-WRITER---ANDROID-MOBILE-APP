package com.example.alexa.onceupona;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class End extends Activity {

    String texto;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        Intent iCame = getIntent();
        texto = iCame.getStringExtra("texto"); //obtem o texto recebido do jogo

        TextView txt = findViewById(R.id.texto);

        txt.setText(Jogo.builder);

        Jogo.builder.clear();

        Date dt = new Date();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy    HH:mm:ss"); //obtem a data e hora do instante que acaba

        MainActivity.dbh.insertData(df.format(dt),texto); //insere na DB o texto e a data para aparecer no historico
    }

    public void Share(View v){ //funçao para partilhar o texto
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, texto);

        startActivity(Intent.createChooser(share, "Onceupona"));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Save(View v){ //função para guardar o texto no dispositivo
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) //pede, caso nao tenha, a permissão para aceder ao armazenamento
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);

        AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(End.this,R.style.myDialog)); //cria um dialog para receber o nome do ficheiro onde pretende guardar o texto
        dialog.setMessage(R.string.FileName)
                .setTitle(R.string.Save);

        final EditText edittext = new EditText(getApplicationContext());

        dialog.setView(edittext);

        dialog.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { //cria o ficheiro, insere o texto e guarda-o
                try {
                    String filename = edittext.getText().toString() + ".txt";
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),filename);
                    FileOutputStream fp = new FileOutputStream(file);
                    fp.write(texto.getBytes());
                    fp.close();
                    Toast.makeText(End.this,R.string.SaveSuc,Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        dialog.show();

    }

    public void Recomecar(View v){
        Intent i=new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
