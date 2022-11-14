/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatClient.presentation;

import chatProtocol.Message;
import chatProtocol.User;
import chatServer.data.UsuarioDao;

import java.util.ArrayList;
import java.util.List;

public class Model extends java.util.Observable {
    User currentUser;
    UsuarioDao usuarioDao;
    List<Message> messages;
    List<User> users;

    public Model() throws Exception {
       currentUser = null;
       messages= new ArrayList<>();
       usuarioDao = new UsuarioDao();
//         users= usuarioDao.findAll();
        users = new ArrayList<>();
        users.add(new User("111","001","Jairo"));
        users.add(new User("444","004","Andres"));
    }
    User getUserAt(int index){
//        return users.get(index);
        if(index != -1){
            return users.get(index);
        }
         return null;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void addObserver(java.util.Observer o) {
        super.addObserver(o);
        this.commit(Model.USER+Model.CHAT);
    }
    
    public void commit(int properties){
        this.setChanged();
        this.notifyObservers(properties);        
    } 
    
    public static int USER=1;
    public static int CHAT=2;
}
