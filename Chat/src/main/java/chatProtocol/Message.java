package chatProtocol;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;

import java.io.Serializable;
//@XmlAccessorType(XmlAccessType.FIELD)
public class Message implements Serializable{
    User sender;
    User Receiver; // Este sera el User que recibe el mensaje
//    @XmlID
    String message;
    public Message() {
    }

    public Message(User sedner,String message,User Receiver) {
        this.sender = sedner;
        this.message = message;
        this.Receiver=Receiver;
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
        return Receiver;
    }

    public void setReceiver(User receiver) {
        Receiver = receiver;
    }
}
