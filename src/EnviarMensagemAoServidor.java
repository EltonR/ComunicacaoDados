import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EnviarMensagemAoServidor implements Runnable{
    
    PrintWriter printWriter;
    BufferedReader brinput = null;
    Socket soquete;

    public EnviarMensagemAoServidor(Socket soqueteA) {
        soquete = soqueteA;
    }

    @Override
    public void run() {
        try {
            if (soquete.isConnected()) {
                System.out.println("Client connected to " + soquete.getInetAddress() + " on port " + soquete.getPort());
                printWriter = new PrintWriter(soquete.getOutputStream(), true);
                while (true) {
                    System.out.println("Type your message to send to server..type 'EXIT' to exit");
                    brinput = new BufferedReader(new InputStreamReader(System.in));
                    String msgtoServerString = brinput.readLine();
                    this.printWriter.println(msgtoServerString);
                    this.printWriter.flush();

                    if (msgtoServerString.equals("EXIT")) {
                        break;
                    }
                }
                soquete.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
