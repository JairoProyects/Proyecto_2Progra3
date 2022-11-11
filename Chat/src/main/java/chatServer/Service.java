package chatServer;
import chatProtocol.User;
import chatProtocol.IService;
import chatProtocol.Message;
import chatServer.data.Data;
import chatServer.data.UsuarioDao;
import chatServer.data.XmlPersister;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Service implements IService{

    private Data data;
    private UsuarioDao usuarioDao;
    private static XmlPersister persister;
    public Service() {
        persister = new XmlPersister("data.xml");
//        try{ data = XmlPersister.instance().load(); }
//        catch(Exception e){ data = new Data(); }
        data = new Data();
//        try {
//            data = XmlPersister.instance().load();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        usuarioDao = new UsuarioDao();
    }
    public Data getData() {
        return data;
    }

    public void store(){
//        try { XmlPersister.instance().store(data); }
//        catch (Exception e) { throw new RuntimeException(e); }
    }

    public void post(Message m){
        // if wants to save messages, ex. recivier no logged on
       data.getMessages().add(m);
       this.show();
         //this.store();
    }
    public void show(){
        for (Message m : data.getMessages()) {
            System.out.println(m.getMessage().toString());
        }
//        Message m = null;
//        m.getMessage().toString();
    }

    
    public User login(User p) throws Exception{
        if (usuarioDao.read(p.getId()) != null) {
            return usuarioDao.read(p.getId());
        } else {
            throw new Exception("User does not exist");
        }
    }

//   System.out.println(p.getNombre() );
//        p.setNombre(p.getId());
//       if(usuarioDao.findByReferencia(p.getId()) != null){
//           return p;
//         }
//       else {
//           throw new Exception("User does not exist");
//       }

//        usuarioDao.create(p);
//        return p;
//    public void agregarEmpleado(User user) throws Exception {
//        usuarioDao.create(user);

    public void logout(User p) throws Exception{
        //nothing to do
    }

    public void register(User p) throws Exception{
        usuarioDao.create(p);
    }
}
