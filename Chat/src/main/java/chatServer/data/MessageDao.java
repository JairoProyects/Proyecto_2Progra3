package chatServer.data;

import chatProtocol.Message;
import chatProtocol.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {
    Database db;

    public MessageDao() {
        db = Database.instance();
    }

    public List<Message> findAll() throws Exception {
        List<Message> r = new ArrayList<>();
        String sql = "select * from Messages u";
        PreparedStatement stm = db.prepareStatement(sql);
        ResultSet rs = db.executeQuery(stm);
        while (rs.next()) {
            r.add(from(rs, "u"));
        }
        return r;
    }

    public void create(Message e) throws Exception {
        String sql = "insert into " +
                "Messages " +
                "(message, sender, receiver) " +
                "values(?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getMessage());
        stm.setString(2, e.getSender().getId());
        stm.setString(3, e.getReceiver().getId());
        db.executeUpdate(stm);
    }

    public List<Message> read(String id) throws Exception {
        Message message;
        List<Message> messages = new ArrayList<>();
        String sql = "select " +
                "s.*, " +
                "a.*, " +
                "b.*, " +
                "from  Messages as s " +
                "inner join Users as a " +
                "on s.sender=a.id and s.receiver=? " +
                "inner join Users as b " +
                "on s.sender=b.id and s.receiver=? ";

        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, id);
        stm.setString(2, id);
        ResultSet rs = db.executeQuery(stm);
        while (rs.next()) {
            message = from (rs,"s");
            message.setSender(UsuarioDao.from(rs,"a"));
            message.setReceiver(UsuarioDao.from(rs,"b"));
            messages.add(message);
        }
        return messages;
    }


    public void delete(String userID) throws Exception {
        String sql = "delete " +
                "from Messages " +
                "where receiver=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, userID);
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("MESSAGE NO EXISTE");
        }
    }

//    public List<User> findbyCedula(String id) throws Exception {
//        List<User> resultado = new ArrayList<User>();
//        String sql = "select * " +
//                "from " +
//                "user u " +
//                "where u.id like ?";
//        PreparedStatement stm = db.prepareStatement(sql);
//        stm.setString(1, "%" + id + "%");
//        ResultSet rs = db.executeQuery(stm);
//        while (rs.next()) {
//            resultado.add(from(rs, "u"));
//        }
//        return resultado;
//    }

    public Message from(ResultSet rs, String alias) throws Exception {
        Message e = new Message();
        e.setMessage(rs.getString(alias + "message"));
        return e;
    }
}
