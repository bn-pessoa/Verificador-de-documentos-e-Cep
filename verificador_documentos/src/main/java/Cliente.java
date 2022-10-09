import java.io.*;
import java.net.*;
import java.util.Scanner;

import common.Conexao;
import common.MsgReq;
import common.MsgResp;

public class Cliente {

    static Socket socket;

    public Cliente() {
        try {
            socket = new Socket("localhost", 9600);
        } // fase de conex�o
        catch (Exception e) {
            System.out.println("Nao consegui resolver o host...");
        }
    }

    public static void main(String[] args){
        /*
        insira os códigos do cliente aqui
        */
        Scanner in = new Scanner(System.in);
        System.out.println("------------------");
        System.out.println("Verificar Documentos");
        System.out.println("------------------\n");

        while(true){
            new Cliente();

            System.out.println("Qual documento você deseja verificar?");
            System.out.println("[1] CPF\n[2] RG\n[3] CEP\n[4] Titulo de Eleitor\n[5] CNPJ" +
                    "\n[6] Fechar o programa");
            int opcao = in.nextInt();
            if(opcao == 6) break;

            System.out.print("Digite o numero do documento: ");
            long numDoc = in.nextLong();

            try{
                MsgReq requisicao = new MsgReq(opcao, numDoc);
                Conexao.send(socket, requisicao);
            }
            catch(Exception e){
                System.out.println("Erro: nao foi possivel encotrar o servidor");
            }

            MsgResp resposta = (MsgResp)Conexao.receive(socket);
            switch(resposta.getStatus()) {
                case 0:
                    System.out.println("O documento é valido");
                    break;
                case 1:
                    System.out.println("O documento NÃO é valido");
                    break;
                case 2:
                    System.out.println("A opção " + opcao + " é inválida");
                    break;
            }
            if(!(resposta.getAdicionalInfo() == null)){
                System.out.println("#######################");
                System.out.println("Informações adicionais:");
                System.out.println(resposta.getAdicionalInfo());
                System.out.println("#######################");
            }
        }

        try {
            socket.close();                               // fase de desconex�o
        } catch (IOException e) {
            System.out.println("Nao encerrou a conexao corretamente" + e.getMessage());
        }
    }
}