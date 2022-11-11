package chatProtocol;

public interface IService {
    public User  login(User u) throws Exception;
    public void logout(User u) throws Exception;
    public User checkContact(User u) throws Exception;
    public void post(Message m);
    public void register(User u) throws Exception;
}
