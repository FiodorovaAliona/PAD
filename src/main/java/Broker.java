import org.dom4j.DocumentException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Broker {

    //cd D:\IdeaProjects\pad0102\out\production\pad0102
    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException, DocumentException {
        int PORT = 81;
        System.out.println("Broker Is Running...");
        ServerSocket serverSocket = new ServerSocket(PORT);
        List<SendThread> threadList = new ArrayList<>();

        while(true){
            Socket socket = serverSocket.accept();
            BrokerService bs = new BrokerService();
            bs.handleMessage(socket, threadList);
        }
    }
    static class BrokerService implements Runnable{
        //Message message;
        Socket socket;
        boolean portIsWaiting;
        int sendThreadI;
        List<SendThread> threadList;

        void handleMessage(Socket socket, List<SendThread> threadList){
            this.socket = socket;
            this.threadList = threadList;
            this.run();
        }

        @Override
        public void run() {
            portIsWaiting = false;
            sendThreadI = 0;
            ObjectInputStream oos = null;
            try {
                oos = new ObjectInputStream(socket.getInputStream());
                Message message = (Message)oos.readObject();
                oos.close();
                socket.close();
                System.out.println("Message \""+message.getText()+"\" in Queue");
                //region check for empty senders
                if(!threadList.isEmpty()) {
                    for (int i = 0; i < threadList.size(); i++) {
                        if (threadList.get(i).messageQueue.isEmpty()) {
                            threadList.remove(i);
                        }
                    }

                }
                //endregion
                //region check if port is waiting
                for(SendThread st : threadList){
                    if(st.getPORT() == message.getReceiverId()){
                        portIsWaiting = true;
                        //sendThreadI++;
                        break;
                    }
                    sendThreadI++;
                }
                //endregion
                if(portIsWaiting) {//exist in threadList
                    threadList.get(sendThreadI).addToQueue(message);//add message to queue
                }else{//add new SenderThread to list
                    threadList.add(new SendThread(message.getReceiverId()));
                    threadList.get(threadList.size() - 1).addToQueue(message);
                    threadList.get(threadList.size() - 1).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}

/*        public void addToQueue(Message message) {
            this.message = message;
            Runnable thread = new Runnable() {
                @Override
                public void run() {
                    int PORT = message.getReceiverId();
                    try {
                        ServerSocket serverSocket = new ServerSocket(PORT);
                        Socket socket = serverSocket.accept();
                        ObjectOutputStream ois = new ObjectOutputStream(socket.getOutputStream());
                        ois.writeObject(message.getDocument());
                        ois.close();
                        socket.close();
                        serverSocket.close();
                        System.out.println("Message \""+message.getText()+"\" is sent");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.run();
        }
*/
/*
            while(true){
                try {
                    if(!messageQueue.isEmpty()){
                        sendMessage(messageQueue.peek());
                    }else{
                        System.out.print("Waiting");
                        for (int i = 0; i < 10; i++) {
                            sleep(1000);
                            System.out.print(".");
                        }
                        System.out.println();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            */