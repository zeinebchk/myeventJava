package com.example.myevent.interfaces;

import com.example.myevent.entities.User;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;

public interface IUser {

    public List<User> getAllUsers() throws SQLException;
    public  User getUser(String email) throws SQLException;
    public Object filterByCriteria(Predicate<User> p, List<User> users) throws SQLException;

}
