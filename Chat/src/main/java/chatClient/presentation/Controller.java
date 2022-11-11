package chatClient.presentation;

import chatClient.logic.ServiceProxy;
import chatProtocol.Message;
import chatProtocol.User;
import chatServer.Service;
import chatServer.data.UsuarioDao;

import java.util.ArrayList;

public class Controller {
    View view;
    Model model;
    UsuarioDao usuarioDao;
    Service service;
    ServiceProxy localService;
    
    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        localService = (ServiceProxy)ServiceProxy.instance();
        localService.setController(this);
        view.setController(this);
        view.setModel(model);
        usuarioDao = new UsuarioDao();
        service = new Service();
    }

    public void login(User u) throws Exception{
        User logged=ServiceProxy.instance().login(u);
        System.out.println(u.getId() + " " + u.getClave() + " " + u.getNombre());
//        usuarioDao.create(logged);
     //   System.out.println(usuarioDao.read("001").getId() + " " + usuarioDao.read("001").getClave());
        model.setCurrentUser(logged);
        model.commit(Model.USER);
    }

    public void post(String text, User u) throws Exception{
        Message message = new Message();
        message.setMessage(text);
        message.setSender(model.getCurrentUser());
        message.setReceiver(u);
        ServiceProxy.instance().post(message);
        model.commit(Model.CHAT);
    }

    public void logout(){
        try {
            ServiceProxy.instance().logout(model.getCurrentUser());
            model.setMessages(new ArrayList<>());
            model.commit(Model.CHAT);
        } catch (Exception ex) {
        }
        model.setCurrentUser(null);
        model.commit(Model.USER+Model.CHAT);
    }
        
    public void deliver(Message message){
        model.messages.add(message);
        model.commit(Model.CHAT);       
    }
    public void register(User u) throws Exception{
        ServiceProxy.instance().register(u);
    }
//    void addContact(User u) throws Exception{
//        ServiceProxy.instance().login(u);
//    }

    public Model getModel() {
        return model;
    }
}
