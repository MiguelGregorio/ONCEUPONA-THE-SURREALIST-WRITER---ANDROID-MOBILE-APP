package com.example.alexa.onceupona;

public class Jogador {
    private int cor;
    private String nome;

    public Jogador(String nome, int cor){
        this.cor = cor;
        this.nome = nome;
    }

    public int getCor(){
        return cor;
    }

    public void setCor(int cor){
        this.cor = cor;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

}
