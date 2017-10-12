import java.io.IOException;


public interface Transport {
    Message getMessage()throws IOException;
    void sendMessage(Message message) throws IOException;
}
