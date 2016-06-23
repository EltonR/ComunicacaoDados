import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceberMensagemDoServidor implements Runnable{
    Socket sock = null;
    BufferedReader recieve = null;

    public ReceberMensagemDoServidor(Socket sock) {
        this.sock = sock;
    }

    @Override
    public void run() {
        try {
            recieve = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));//get inputstream
            String msgRecieved = null;
            while ((msgRecieved = recieve.readLine()) != null) {
                System.out.println("From Server: " + msgRecieved);
                System.out.println("Please enter something to send to server..");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
