package com.example.myevent.Services;


import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Set;

public interface IEventService<T> {

    public  boolean chercherOffreDansEvent(BigInteger idevent, BigInteger idOffre) throws SQLException;
    public Set getEventsByClIent_id()throws SQLException;


}

