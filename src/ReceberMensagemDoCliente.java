import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceberMensagemDoCliente implements Runnable{
    Map<String, String> map = new HashMap<String, String>();
    String s="";
    
    Socket soquete = null;
    BufferedReader bufferReader = null;

    public ReceberMensagemDoCliente(Socket clientSocket) {
        this.soquete = clientSocket;
    }

    @Override
    public void run() {
        try {
            bufferReader = new BufferedReader(new InputStreamReader(this.soquete.getInputStream()));
            String quadro;
            while (true) {
                while ((quadro = bufferReader.readLine()) != null) {
                    if(quadro.equalsIgnoreCase("11111111")){
                        ordenaMAP();
                        System.out.println("Mensagem: "+s);
                        s="";
                        map = new HashMap<String, String>();
                    }else{
                        System.out.println("FRAME: " + quadro);
                        quadro = Util.desflagaMensagem(quadro);
                        System.out.println("FRAME desflagado: " + quadro);
                        String id = quadro.substring(0, 4);
                        quadro = quadro.substring(4);
                        quadro = Util.desescapaMensagem(quadro);
                        System.out.println("FRAME desescapado: " + quadro);
                        if (Util.descalculaCRC(quadro).equalsIgnoreCase("0000000000000000")) {
                            if(!map.containsKey(id))
                                System.out.println("[CLIENTE] Quadro recebido: " + Util.converteBinStr(quadro.substring(0, quadro.length() - 16)));
                            EnviarMensagemAoServidor.enviarACK(id);
                            map.put(id, Util.converteBinStr(quadro.substring(0, quadro.length() - 16)));
                            if (map.size() >= Util.TAMJANELA) {
                                ordenaMAP();
                                map = new HashMap<String, String>();
                            }
                        } else {
                            System.out.println("[CLIENTE] Erro ao receber algum quadro...");
                        }
                    }
                }
                this.soquete.close();
                System.exit(0);
            }

        } catch (Exception ex) {
            //System.out.println(ex.getMessage());
        }
    }
    
    private void ordenaMAP() {
        List lista = new ArrayList(map.keySet());
        Collections.sort(lista);
        String ss = "";
        for(int i=0 ;i<lista.size(); i++){
            ss += map.get(lista.get(i))+" ";
        }
        s+=ss;
    }
    
}
