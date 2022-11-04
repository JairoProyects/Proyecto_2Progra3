package chatServer;
import chatProtocol.User;
import chatProtocol.IService;
import chatProtocol.Message;
import chatServer.data.Data;
import chatServer.data.UsuarioDao;

import javax.swing.*;

public class Service implements IService{

    private Data data;
    private UsuarioDao usuarioDao;
    public Service() {
        data =  new Data();
        usuarioDao = new UsuarioDao();
    }

    public Data getData() {
        return data;
    }

    public void post(Message m){
        // if wants to save messages, ex. recivier no logged on
    }
    
    public User login(User p) throws Exception{
        if (usuarioDao.read(p.getId()) != null) {
            return p;
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
