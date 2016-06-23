import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceberMensagemDoCliente implements Runnable{
    Socket clientSocket = null;
    BufferedReader brBufferedReader = null;

    public ReceberMensagemDoCliente(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            brBufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

            String messageString;
            while (true) {
                while ((messageString = brBufferedReader.readLine()) != null) {//assign message from client to messageString
                    if (messageString.equals("EXIT")) {
                        break;//break to close socket if EXIT
                    }
                    System.out.println("From Client: " + messageString);//print the message from client
                    System.out.println("Please enter something to send back to client..");
                }
                this.clientSocket.close();
                System.exit(0);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
