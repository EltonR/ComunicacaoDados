
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ReceberMensagemDoServidor implements Runnable {
    
    public static int CENARIO=1; // 0=sem erros; 1=frames perdidos; 2=frames com erro;
    public static int FREQ=5;
    int quadrosPerdidos = 0;
    int quadrosComErro = 0;
    int totalQuadrosRecebidos =0;
    int quadrosDuplos =0;
    Random random;

    Map<String, String> map = new HashMap<String, String>();
    String s="";

    Socket soquete = null;
    BufferedReader bufferReader = null;

    public ReceberMensagemDoServidor(Socket sock) {
        this.soquete = sock;
        random = new Random();
    }

    @Override
    public void run() {
        try {
            bufferReader = new BufferedReader(new InputStreamReader(this.soquete.getInputStream()));//get inputstream
            String quadro = null;
            while ((quadro = bufferReader.readLine()) != null) {
                if(quadro.length()==4){
                    System.out.println("[Cliente] ack recebido: "+quadro);
                    posta(quadro);
                }else if(quadro.equalsIgnoreCase("11111111")){
                    ordenaMAP();
                    System.out.println("Mensagem: "+s);
                    System.out.println("Quadros perdidos: "+quadrosPerdidos);
                    System.out.println("Quadros com erro: "+quadrosComErro);
                    System.out.println("Total de quadros recebidos: "+totalQuadrosRecebidos);
                    System.out.println("Total de quadros : "+quadrosDuplos);
                    totalQuadrosRecebidos=0;
                    quadrosDuplos=0;
                    quadrosComErro=0;
                    quadrosPerdidos=0;
                    s="";
                    map = new HashMap<String, String>();
                }else{
                    int n8 = random.nextInt(FREQ);
                    if(CENARIO == 1 && n8==0){
                        System.out.println("QUADRO PERDIDOS>>> "+quadro);
                        quadrosPerdidos++;
                    }else{
                        System.out.println("FRAME: " + quadro);
                        quadro = Util.desflagaMensagem(quadro);
                        System.out.println("FRAME desflagado: " + quadro);
                        String id = quadro.substring(0, 4);
                        quadro = quadro.substring(4);
                        quadro = Util.desescapaMensagem(quadro);
                        System.out.println("FRAME desescapado: " + quadro);
                        if(CENARIO == 2 && n8==0){
                            quadro = "101"+quadro;
                        }
                        if (Util.descalculaCRC(quadro).equalsIgnoreCase("0000000000000000")) {
                            if(!map.containsKey(id)){
                                System.out.println("[CLIENTE] Quadro recebido: " + Util.converteBinStr(quadro.substring(0, quadro.length() - 16)));
                            }else{
                                quadrosDuplos++;
                            }
                            totalQuadrosRecebidos++;
                            EnviarMensagemAoServidor.enviarACK(id);
                            map.put(id, Util.converteBinStr(quadro.substring(0, quadro.length() - 16)));
                            if (map.size() >= Util.TAMJANELA) {
                                ordenaMAP();
                                map = new HashMap<String, String>();
                            }
                        } else {
                            quadrosComErro++;
                            System.out.println("[CLIENTE] Erro ao receber algum quadro...");
                        }
                    }
                }
            }
        } catch (Exception e) {
            //System.out.println("ERRO: " + e.getMessage());
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
    
    private synchronized void posta(String ack){
        EnviarMensagemAoServidor.ids_Recebidos.add(ack);
    }

}
