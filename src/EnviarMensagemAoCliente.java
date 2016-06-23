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

                //Envia a mensagem e esvazia o printWriter
                printWriter.println(mensagem);
                printWriter.flush();
                System.out.println("Escreva algo para retornar ao cliente: ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
