package chatServer.data;

import chatProtocol.Message;
import chatProtocol.User;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {
//    private List<User> users;
//    private UsuarioDao usuarioDao;
    List<Message> messages;
    public Data() {
        messages = new ArrayList<>();
        
    }
    public List<Message> getMessages() {
        return messages;
    }


//    public List<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<User> users) {
//        this.users = users;
//    }
}
