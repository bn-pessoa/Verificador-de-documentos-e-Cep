import java.net.*;

import common.Conexao;
import common.MsgReq;
import common.MsgResp;
import utils.ServerThread;
import utils.Verificar;

public class Servidor {

    static ServerSocket serversocket;
    static Socket client_socket;

    public Servidor() {

        try {
            serversocket = new ServerSocket(9600);
            System.out.println("Validador distribuido no ar ...");
        } catch (Exception e) {
            System.out.println("Nao criei o server socket...");
        }
    }

    public static void main(String[] args) {
        new Servidor();
        while(true){
            if (connect()){
                new ServerThread(client_socket).start();
            }
        }
    }

    static boolean connect() {
        boolean ret;
        try {
            client_socket = serversocket.accept();
            ret = true;
        } catch (Exception e) {
            System.out.println("Nao fez conexao" + e.getMessage());
            ret = false;
        }
        return ret;
    }
}
