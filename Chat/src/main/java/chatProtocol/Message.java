package chatProtocol;

import java.io.Serializable;
//@XmlAccessorType(XmlAccessType.FIELD)
public class Message implements Serializable{
    User sender;
    User receiver; // Este sera el User que recibe el mensaje
//    @XmlID
    String message;
    public Message() {
    }

    public Message(User sedner,String message,User Receiver) {
        this.sender = sedner;
        this.message = message;
        this.receiver =Receiver;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
