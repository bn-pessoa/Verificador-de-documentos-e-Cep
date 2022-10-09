package utils;

import common.Conexao;
import common.MsgReq;
import common.MsgResp;

import java.net.Socket;

public class ServerThread extends Thread{
    private Socket client_socket;

    public ServerThread(Socket socket){
        this.client_socket = socket;
    }

    public void run(){
        try {
            // insira os códigos do servidor aqui
            MsgReq requisicao = (MsgReq) Conexao.receive(client_socket);
            MsgResp resposta = new MsgResp();

            Verificar verificacao = new Verificar();

            switch (requisicao.getOpcao()) {
                case 1:
                    if (verificacao.verificarCPF(requisicao.getNum(), resposta)) {
                        resposta.setStatus(0);
                    } else {
                        resposta.setStatus(1);
                    }
                    break;
                case 2:
                    if (verificacao.verificarRG(requisicao.getNum(), resposta)) {
                        resposta.setStatus(0);
                    } else {
                        resposta.setStatus(1);
                    }
                    break;
                case 3:
                    if (verificacao.verificarCEP(requisicao.getNum(), resposta)) {
                        resposta.setStatus(0);
                    } else {
                        resposta.setStatus(1);
                    }
                    break;
                case 4:
                    if (verificacao.verificarTituloEleitor(requisicao.getNum(), resposta)) {
                        resposta.setStatus(0);
                    } else {
                        resposta.setStatus(1);
                    }
                    break;

                case 5:
                    if (verificacao.verificarCPNJ(requisicao.getNum(), resposta)) {
                        resposta.setStatus(0);
                    } else {
                        resposta.setStatus(1);
                    }
                    break;
                default:
                    resposta.setStatus(2);
            }

            Conexao.send(client_socket, resposta);
        }
        catch(Exception e){
            System.out.println("Excessao");
        }
    }
}
