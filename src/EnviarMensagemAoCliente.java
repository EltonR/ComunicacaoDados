import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class EnviarMensagemAoCliente implements Runnable{
    
    Socket soqueteCliente;
    PrintWriter printWriter;

    public EnviarMensagemAoCliente(Socket soquete) {
        soqueteCliente = soquete;
    }

    @Override
    public void run() {
        try {
            printWriter = new PrintWriter(new OutputStreamWriter(soqueteCliente.getOutputStream()));
            while (1==1) {
                //pega o input do usuário
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                String mensagem = input.readLine();
                
                String[] frames = mensagem.split(" ");
                String quadro;
                for(int i=0; i<frames.length; i++){
                    
                    quadro = Util.converteStrBin(frames[i]);
                    quadro += Util.calculaCRC(quadro);
                    System.out.println("FRAME ["+i+"]: "+frames[i]+"  >> "+quadro);
                    
                    printWriter.println(quadro);
                    printWriter.flush();
                }
                //Envia a mensagem e esvazia o printWriter
                printWriter.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
