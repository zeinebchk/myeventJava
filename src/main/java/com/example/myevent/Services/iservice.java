package com.example.myevent.Services;


import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface iservice<T> {

    public Set getEventsByClIent_id()throws SQLException;


}

