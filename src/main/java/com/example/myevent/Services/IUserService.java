package com.example.myevent.Services;

import com.example.myevent.entities.Transaction;
import com.example.myevent.entities.User;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;

public interface IUserService {

    public List<User> getAllUsers() throws SQLException;
    public  User getUserByMail(String email) throws SQLException;
    public User getUserById(int id) throws SQLException;
    public Object filterByCriteria(Predicate<User> p, List<User> users) throws SQLException;
    public List<Transaction> getConfirmedUserReservation();
}
