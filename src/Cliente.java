import java.net.*;

public class Cliente {
    
    static int porta = 5050;

    public static void main(String[] args) {
        try {
            System.out.println("Cliente no ar tentando conexão na porta: " + porta);
            Socket soquete = new Socket("localhost", porta);
            System.out.println("Conexão estabelecida...");
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
