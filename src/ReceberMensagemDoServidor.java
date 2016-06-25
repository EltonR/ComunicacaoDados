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
                if(Util.descalculaCRC(msgRecieved).equalsIgnoreCase("0000000000000000"))
                    System.out.println("[CLIENTE] Quadro recebido: "+Util.converteBinStr(msgRecieved.substring(0,msgRecieved.length()-16)));
                else
                    System.out.println("[CLIENTE] Erro ao receber algum quadro...");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
