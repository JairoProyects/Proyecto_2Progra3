package chatServer.data;
import chatProtocol.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
    Database db;

    public UsuarioDao() {
        db = Database.instance();
    }
   public List<User> findAll() throws Exception {
        List<User> r = new ArrayList<>();
        String sql = "select * from Users u";
        PreparedStatement stm = db.prepareStatement(sql);
        ResultSet rs = db.executeQuery(stm);
        while (rs.next()) {
            r.add(from(rs, "u"));
        }
        return r;
    }
    public void create(User e) throws Exception {
        String sql = "insert into " +
                "Users " +
                "(id, clave, nombre) " +
                "values(?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getId());
        stm.setString(2, e.getClave());
        stm.setString(3, e.getNombre());
        db.executeUpdate(stm);
    }

    public User read(String id) throws Exception {
        String sql = "select " +
                "* " +
                "from  Users u " +
                "where u.id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, id);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            return from(rs, "u");
        } else {
          throw new Exception("USER NO EXISTE");

        }
    }

    public void update(User e) throws Exception {
        String sql = "update " +
                "User " +
                "set id=?, clave=?, nombre=?" +
                "where id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getId());
        stm.setString(2, e.getClave());
        stm.setString(3, e.getClave());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("USER NO EXISTE");
        }

    }

    public void delete(User e) throws Exception {
        String sql = "delete " +
                "from User " +
                "where id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getId());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("USER NO EXISTE");
        }
    }

    public List<User> findbyCedula(String id) throws Exception {
        List<User> resultado = new ArrayList<User>();
        String sql = "select * " +
                "from " +
                "user u " +
                "where u.id like ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, "%" + id + "%");
        ResultSet rs = db.executeQuery(stm);
        while (rs.next()) {
            resultado.add(from(rs, "u"));
        }
        return resultado;
    }

    public static User from(ResultSet rs, String alias) throws Exception {
        User e = new User("", "", "");
        e.setId(rs.getString(alias + ".id"));
        e.setClave(rs.getString(alias + ".clave"));
        e.setNombre(rs.getString(alias + ".nombre"));
        return e;
    }
}
