package common;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.Serializable;

public class MsgReq implements Serializable{
    private int opcao;
    private long numero;

    public MsgReq() {
    }

    public MsgReq(int opcao, long numero) {
        this.opcao = opcao;
        this.numero = numero;
    }

    public int getOpcao() {
        return opcao;
    }

    public void setOpcao(int opcao) {
        this.opcao = opcao;
    }

    public long getNum() {
        return numero;
    }

    public void setNum(long num) {
        this.numero = num;
    }
}
