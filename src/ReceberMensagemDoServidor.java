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
            String quadro = null;
            while ((quadro = recieve.readLine()) != null) {
                System.out.println("FRAME: "+quadro);
                quadro = Util.desflagaMensagem(quadro);
                System.out.println("FRAME desflagado: "+quadro);
                quadro = Util.desescapaMensagem(quadro);
                System.out.println("FRAME desescapado: "+quadro);
                if(Util.descalculaCRC(quadro).equalsIgnoreCase("0000000000000000"))
                    System.out.println("[CLIENTE] Quadro recebido: "+Util.converteBinStr(quadro.substring(0,quadro.length()-16)));
                else
                    System.out.println("[CLIENTE] Erro ao receber algum quadro...");
            }
        } catch (Exception e) {
            System.out.println("ERRO: "+e.getMessage());
        }
    }
}
