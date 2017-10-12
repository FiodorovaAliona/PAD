import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import java.io.Serializable;

public class Message implements Serializable{
    private String mText;
    private int mSenderId;
    private int mReceiverId;
    private Document mDocument;

    public Message(){}

    public Message(int senderId, int receiverId, String text) {
        this.mText = text;
        this.mSenderId = senderId;
        this.mReceiverId = receiverId;
        Document document = DocumentHelper.createDocument();
        document.addElement("message")
                .addAttribute("sender_id", Integer.toString(senderId))
                .addAttribute("receiver_id", Integer.toString(receiverId))
                .addAttribute("text", text);
        this.mDocument = document;
        //System.out.println(document.asXML());
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public int getSenderId() {
        return mSenderId;
    }

    public void setSenderId(int senderId) {
        mSenderId = senderId;
    }

    public int getReceiverId() {
        return mReceiverId;
    }

    public void setReceiverId(int receiverId) {
        mReceiverId = receiverId;
    }

    public Document getDocument() {
        return mDocument;
    }

    public void setDocument(Document mDocument) {
        this.mDocument = mDocument;
        Node root = this.mDocument.node(0);
        this.mText = root.valueOf("@text");
        this.mSenderId = Integer.parseInt(root.valueOf("@sender_id"));
        this.mReceiverId = Integer.parseInt(root.valueOf("@receiver_id"));

    }
}
