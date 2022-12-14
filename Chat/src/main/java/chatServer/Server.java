
package chatServer;

import chatProtocol.Protocol;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import chatProtocol.IService;
import chatProtocol.Message;
import chatProtocol.User;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;

public class Server {
    ServerSocket srv;
    Service service;
    List<Worker> workers; 
    
    public Server() {
        try {
            srv = new ServerSocket(Protocol.PORT);
            workers =  Collections.synchronizedList(new ArrayList<Worker>());
            service = new Service();
            System.out.println("Servidor iniciado...");
        } catch (IOException ex) {
        }
    }
    
    public void run(){
       int method;
        boolean continuar = true;
        ObjectInputStream in;
        ObjectOutputStream out;
       while (continuar) {
           try {
               Socket skt = srv.accept();
               in = new ObjectInputStream(skt.getInputStream());
               out = new ObjectOutputStream(skt.getOutputStream());
               System.out.println("conexion establecida...");
               method = in.readInt();

               switch(method){
               case Protocol.LOGIN:
                   try {
                    User user = service.login((User) in.readObject());
                    out.writeInt(Protocol.ERROR_NO_ERROR);
                    out.writeObject(user);
                    out.flush();
                    Worker worker = new Worker(this, in, out, user, service);
                    workers.add(worker);
                    worker.start();
                   }catch (Exception ex) {
                          out.writeInt(Protocol.ERROR_LOGIN);
                          out.flush();
                          skt.close();
                       System.out.println("Conexion cerrada...");
                       System.out.println(service.getData().getMessages().toString());
                   } break;
                case Protocol.REGISTER:
                    try {
                        service.register((User) in.readObject());
                        out.writeInt(Protocol.ERROR_NO_ERROR);
                        out.flush();
                        skt.close();
                    } catch (Exception ex) {
                        out.writeInt(Protocol.ERROR_REGISTER);
                        out.flush();
                        skt.close();
                        System.out.println("Conexion cerrada...");
                    } break;
                   default:  out.writeInt(Protocol.ERROR_LOGIN);
                          out.flush();
                          skt.close();
                       System.out.println("Conexion cerrada...");
               }
           }
           catch (IOException ex) {

           }
       }
    }
    
    private User login(ObjectInputStream in,ObjectOutputStream out,IService service) throws IOException, ClassNotFoundException, Exception{
        int method = in.readInt();
        if (method!=Protocol.LOGIN) throw new Exception("Should login first");
        User user=(User)in.readObject();                          
        user=service.login(user);
        out.writeInt(Protocol.ERROR_NO_ERROR);
        out.writeObject(user);
        out.flush();
        return user;
    }
    
    public void deliver(Message message){
//        for(Worker wk:workers){
//            wk.deliver(message);
//        }
//       if(workers.indexOf(message.getReceiver())!=-1){
//           workers.get(workers.indexOf(message.getReceiver())).deliver(message);
//       } else{
//           System.out.println("USER ERROR ");
//       }
        for(Worker wk:workers){
            if(wk.user.equals(message.getReceiver())){
                wk.deliver(message);
            }
        }
    }
    
    public void remove(User u){
        for(Worker wk:workers) if(wk.user.equals(u)){workers.remove(wk);break;}
        System.out.println("Quedan: " + workers.size());
    }
    
}