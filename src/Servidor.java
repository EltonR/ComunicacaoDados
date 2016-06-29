import java.io.*;
import java.net.*;

public class Servidor {
    
    static int porta = 5050;
    
    public static void main(String[] args) throws IOException {
        System.out.println("Servidor no ar e esperando conexão na porta: " + porta);
        ServerSocket soquete = new ServerSocket(porta);
        Socket soqueteCliente = soquete.accept();
        System.out.println("Cliente conectado: '" + soqueteCliente.getInetAddress() + "'");
        
        //Cria duas threads, uma para receber otra para enviar
        ReceberMensagemDoCliente receber = new ReceberMensagemDoCliente(soqueteCliente);
        Thread thread = new Thread(receber);
        thread.start();
        EnviarMensagemAoCliente enviar = new EnviarMensagemAoCliente(soqueteCliente);
        Thread thread2 = new Thread(enviar);
        thread2.start();
    }
}


