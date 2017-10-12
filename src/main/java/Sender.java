import java.io.*;
import java.net.*;


public class Sender {
    public static void main(String[] args) throws IOException{
        int PORT = Integer.parseInt(args[0]);
        //int PORT = 55;
        System.out.println("Sender ("+ PORT +")Is Running...");
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            System.out.println("To:");
            int messageReceiver = Integer.parseInt(stdIn.readLine());
            System.out.println("Message:");
            String messageTxt = stdIn.readLine();
            Message message = new Message(PORT,messageReceiver, messageTxt);
            //Message message = new Message(123,789, "HI");
            SenderService ss = new SenderService();
            ss.sendMessage(message);
        }
    }
    static class SenderService extends TransportService {
        Message message;

        @Override
        public void run() {
            try {
                Socket socket = new Socket("localhost", 81);
                ObjectOutputStream ois = new ObjectOutputStream(socket.getOutputStream());
                ois.writeObject(message);
                ois.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void sendMessage(Message message) throws IOException{
            this.message = message;
            this.start();
        }
    }
}
