import java.net.*;

public class Cliente {
    
    static int porta = 5050;

    public static void main(String[] args) {
        try {
            Socket soquete = new Socket("localhost", porta);
            EnviarMensagemAoServidor enviar = new EnviarMensagemAoServidor(soquete);
            Thread thread = new Thread(enviar);
            thread.start();
            ReceberMensagemDoServidor recebida = new ReceberMensagemDoServidor(soquete);
            Thread thread2 = new Thread(recebida);
            thread2.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
