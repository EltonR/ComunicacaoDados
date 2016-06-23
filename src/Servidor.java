import java.io.*;
import java.net.*;

public class Servidor {
    
    static int porta = 5050;
    
    public static void main(String[] args) throws IOException {
        System.out.println("Servidor no ar e esperando conexão na porta: " + porta);
        ServerSocket soquete = new ServerSocket(porta);
        Socket soqueteCliente = soquete.accept();
        System.out.println("Cliente conectado: '" + soqueteCliente.getInetAddress() + "' utilizando a porta " + soqueteCliente.getPort());
        //create two threads to send and recieve from client
        ReceberMensagemDoCliente recebida = new ReceberMensagemDoCliente(soqueteCliente);
        Thread thread = new Thread(recebida);
        thread.start();
        EnviarMensagemAoCliente send = new EnviarMensagemAoCliente(soqueteCliente);
        Thread thread2 = new Thread(send);
        thread2.start();
    }
}


