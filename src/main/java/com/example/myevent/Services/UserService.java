package com.example.myevent.Services;

import com.example.myevent.entities.*;
import com.example.myevent.tools.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserService implements IUserService {
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = Connexion.getInstance().getCnx();
    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();

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
//    public User getEntrepreneurById(BigInteger id) throws SQLException {
//        long userId = id.longValue();
//        st=con.prepareStatement("SELECT * from entrepreneurs where user_id=?");
//        st.setLong(1, userId);
//        rs = st.executeQuery();
//        User user=new Entrepreneur();
//        while (rs.next()) {
//            user.setId();
//            user.(rs.getBigDecimal("client_id").toBigInteger());
//            user.
//            user.setNom( rs.getString("nom"));
//            user.setPrenom(rs.getString("prenom"));
//            user.setEmail( rs.getString("email"));
//        }
//        return  user;
//    }

    public List<Transaction> getConfirmedUserReservation() {
       System.out.println("getConfirmedUserReservation");
        ObservableList<Transaction> transactions= FXCollections.observableArrayList();
        BigInteger bigId = UserSession.getInstance().getUser().getId();
        long userId = bigId.longValue();
        System.out.println("userId:"+userId);
        try {
             st=con.prepareStatement("SELECT r.*, u.*, o.*, e.*" +
                     "FROM reservations r" +
                     " JOIN users u ON r.client_id = u.id" +
                     " JOIN offre o ON r.offre_id = o.id" +
                     " JOIN entrepreneurs e ON o.entrepreneur_id = e.id" +
                     " JOIN users u2 ON u2.id = e.user_id" +
                     " WHERE e.user_id = ? ");
              st.setLong(1, userId);
             rs = st.executeQuery();
            while (rs.next()) {
                User user=new User();
                user.setId(rs.getBigDecimal("client_id").toBigInteger());
                user.setNom( rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setEmail( rs.getString("email"));
                Reservation reservation=new Reservation();
                reservation.setStatus(rs.getString("status"));
                reservation.setDateReservation(rs.getDate("dateReservation"));
                reservation.setHeureDebut(rs.getTime("heureDebut"));
                reservation.setHeureFin(rs.getTime("heureFin"));
                reservation.setAvanceClient(rs.getInt("avanceClient"));
                SalleFete s=new SalleFete();
                s.setTitre(rs.getString("titre"));
                s.setPrixInitial(rs.getDouble("prixInitial"));


                // Créer une nouvelle transaction avec l'objet Client et l'objet Offre
                Transaction transaction = new Transaction(user,s,reservation);
                transactions.add(transaction);
            }
            System.out.println("size"+transactions.size());
            for (Transaction s:transactions) {
                System.out.println("aaaaaa");
                System.out.println(s.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions.stream().filter(x->x.getReservation().getStatus().equals("confirme")).collect(Collectors.toList());

    }}
