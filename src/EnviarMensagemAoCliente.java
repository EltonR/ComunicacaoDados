import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnviarMensagemAoCliente implements Runnable {

    static Socket soquete;
    static PrintWriter printWriter;
    public static ArrayList<String> ids_Recebidos;
    public static ArrayList<String> ids_Enviados;
    int total_ids_Enviados;
    int total_ids_Recebidos;
    public int inicioJanela = 0;
    public int fimJanela = 0 + Util.TAMJANELA;
    boolean pronto = false;

    public EnviarMensagemAoCliente(Socket soquete) {
        this.soquete = soquete;
        try {    
            printWriter = new PrintWriter(new OutputStreamWriter(soquete.getOutputStream()));
        } catch (IOException ex) {
            Logger.getLogger(EnviarMensagemAoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            while (1 == 1) {
                //pega o input do usuário
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                String mensagem = input.readLine().trim();
                

                //prepara os frames
                pronto=false;
                String[] frames = mensagem.split(" ");
                String quadro;
                ArrayList<String> quadros = new ArrayList();
                int id=0;
                for (int i = 0; i < frames.length; i++) {
                    if(frames[i].equalsIgnoreCase(""))
                        quadro = Util.converteStrBin(" ");
                    else
                        quadro = Util.converteStrBin(frames[i]);
                    quadro += Util.calculaCRC(quadro);
                    quadro = Util.escapaMensagem(quadro);
                    quadro = Util.FLAG + Util.geraID(id) + quadro + Util.FLAG;
                    id++;
                    if(id>15)
                        id=0;
                    quadros.add(quadro);
                    
                }
                System.out.println("[Servidor] Finalizada a criação dos quadros...");

                //Iniciando o envio de pacotes 
                inicioJanela=0;
                fimJanela=0+Util.TAMJANELA;
                total_ids_Enviados=0;
                total_ids_Recebidos=0;
                ids_Recebidos = new ArrayList<>();
                ids_Enviados = new ArrayList<>();
                
                //Thread da Janela...
                Runnable janela = new Runnable() {
                    public void run() {
                        while(total_ids_Enviados < quadros.size()){
                            if(ids_Enviados.size()>0 && ids_Recebidos.contains(ids_Enviados.get(0))){
                                fimJanela++;
                                inicioJanela++;
                                ids_Recebidos.remove(ids_Enviados.get(0));
                                ids_Enviados.remove(0);
                            }
                            try { Thread.sleep(10); }catch (InterruptedException ex) { }
                            //System.out.println("Enviados: "+ids_Enviados.toString());
                            //System.out.println("Recebidos: "+ids_Recebidos.toString());
                        }
                        System.out.println("[Servidor] fim da janela...");
                        
                    }
                };Thread t = new Thread(janela);t.start();

                while (total_ids_Enviados < quadros.size()) {
                    if(total_ids_Enviados<fimJanela && total_ids_Enviados<quadros.size()){
                        enviarQuadro(quadros.get(total_ids_Enviados), total_ids_Enviados);
                        total_ids_Enviados++;
                    }
                    try { Thread.sleep(10); }catch (InterruptedException ex) { }
                }
                System.out.println("[Servidor] Todos os quadros enviados.");
                printWriter.println("11111111"); //sinaliza o fim das mensagens
                printWriter.flush();
                pronto = true;
            }
        } catch (Exception e) {
            System.out.println("Erro 2: "+e.getMessage());
        }
    }

    private void enviarQuadro(String quadro, int numQuadro) {
        if(pronto)
            return;
        try {
            
            final String id = quadro.substring(8,12);
            printWriter.flush();
            printWriter.println(quadro);
            printWriter.flush();
            if(!verifica(ids_Enviados, id))
                ids_Enviados.add(id);

            Runnable r = new Runnable() {
                public void run() {
                    long start_time = System.nanoTime();
                    while(start_time < System.nanoTime()+Util.TIMEOUT){
                        if(verifica(ids_Recebidos, id))
                            break;
                    }
                    try { Thread.sleep(10); }catch (InterruptedException ex) { }
                    if(!verifica(ids_Recebidos, id)){
                        enviarQuadro(quadro, numQuadro);
                    }
                }
            };
            Thread t = new Thread(r);
            t.start();

        } catch (Exception ex) {
            Logger.getLogger(EnviarMensagemAoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void enviarACK(String ACK){
        System.out.println("[Servidor] enviando ack de confirmacao"+ACK);
        try {
            if (soquete.isConnected()) {
                printWriter.flush();
                printWriter.println(ACK);
                printWriter.flush();
            }
        } catch (Exception e) {
            System.out.println("ERRO 01-SOCKET [EnviarMsgToCliente]: "+e.getMessage());
        }
    }

    private synchronized boolean verifica(ArrayList<String> lista, String s){
        return lista.contains(s);
    }
    
}
