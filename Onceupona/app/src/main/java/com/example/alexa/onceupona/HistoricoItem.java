package com.example.alexa.onceupona;


public class HistoricoItem {
    private String data;
    private String texto;
    int id;

    public HistoricoItem(int id,String data, String texto){
        this.data = data;
        this.texto = texto;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
