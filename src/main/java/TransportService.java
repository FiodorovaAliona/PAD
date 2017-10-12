import java.io.IOException;


public class TransportService extends Thread implements Transport {
    @Override
    public void run(){
        super.run();
    }

    @Override
    public Message getMessage() throws IOException {
        return null;
    }

    @Override
    public void sendMessage(Message message) throws IOException{

    }
}