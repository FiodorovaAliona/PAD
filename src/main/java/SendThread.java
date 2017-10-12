import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class SendThread extends Thread {
    int PORT;
    Queue<Message> messageQueue;

    SendThread(int port){
        this.PORT = port;
        this.messageQueue = new ConcurrentLinkedQueue<>();
    }

    public int getPORT() {
        return PORT;
    }

    @Override
    public void run() {
        ServerSocket serverSocket;
        Socket socket;
        try {
            serverSocket = new ServerSocket(PORT);
            while(!messageQueue.isEmpty()){
                socket = serverSocket.accept();
                if (socket.isConnected()){
                    System.out.println("Connected to " + socket.getPort());
                    ObjectOutputStream ois = new ObjectOutputStream(socket.getOutputStream());
                    ois.writeObject(messageQueue.peek());
                    System.out.println("Message \"" + messageQueue.poll().getText() + "\" is Sent");
                    ois.close();
                    socket.close();
                }
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addToQueue(Message m){
        messageQueue.add(m);
    }


}
