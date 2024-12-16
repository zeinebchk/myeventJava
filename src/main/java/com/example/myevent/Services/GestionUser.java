package com.example.myevent.Services;

import com.example.myevent.entities.User;
import com.example.myevent.tools.Connexion;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class GestionUser implements IUser {
    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        Connection con = Connexion.getInstance().getCnx();
        st = con.prepareStatement("SELECT * from users " );
        rs = st.executeQuery();
        int rowCount = 0;
        while (rs.next()) {
            rowCount++;
            User s=new User();
            long idLong = rs.getLong("id"); // Ou vous pouvez utiliser rs.getInt("id") si c'est un int
            BigInteger id = BigInteger.valueOf(idLong);
            s.setId(id);
            s.setEmail(rs.getString("email"));
            s.setNom(rs.getString("nom"));
            s.setPrenom(rs.getString("prenom"));
            s.setPassword(rs.getString("password"));
            s.setImage(rs.getString("image"));
            s.setGenre(rs.getString("genre"));
            s.setNumTel(rs.getInt("numTel"));
            users.add(s);

    }
        for (User s:users){
            System.out.println(s.toString());
        }
        return users;
    }

    @Override
    public User getUserByMail(String email) throws SQLException {
        List<User>users=getAllUsers();
        User s=filterByCriteria(u->u.getEmail()!=null && u.getEmail().equals(email),users);
       if(s!=null)
           System.out.println(s.toString());
        return s;
    }

    @Override
    public User getUserById(int id) throws SQLException {
        List<User>users=getAllUsers();
        User s=filterByCriteria(u->u.getId()!=null && u.getId().equals(id),users);
        if(s!=null)
            System.out.println(s.toString());
        return s;
    }
    


    public User filterByCriteria(Predicate<User> p, List<User> users) throws SQLException {
        for(User c:users)
        {  if(p.test(c))

            return c;
        }
        return null;
    }
}
