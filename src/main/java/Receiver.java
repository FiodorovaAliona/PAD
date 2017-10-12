import org.dom4j.DocumentException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;



public class Receiver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, DocumentException {

        int PORT = Integer.parseInt(args[0]);
        //int PORT = 4848;
        System.out.println("Receiver ("+ PORT +") Is Running...");
        //ServerSocket serverSocket = new ServerSocket(PORT);
        //Socket socket = serverSocket.accept();
        while(true){
            Socket socket;
            while (true) {
                try {
                    socket = new Socket("localhost", PORT);
                    if (socket != null) {
                        break;
                    }
                }catch (IOException e) {
                    Thread.sleep(1000);
                    //System.out.print(".");
                }
            }
            ObjectInputStream oos = new ObjectInputStream(socket.getInputStream());
            Message message = (Message) oos.readObject();
            oos.close();
            System.out.println("Message from "+message.getSenderId()+": "+message.getText());
            socket.close();
        }
    }
}
