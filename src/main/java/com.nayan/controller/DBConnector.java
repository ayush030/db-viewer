package com.nayan.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DBConnector {
    public abstract void connect() throws SQLException;
    public abstract ResultSet getAllRelations() throws SQLException;
    public abstract ResultSet getAllDataForARelation(String relation) throws SQLException;
    public abstract ResultSet getAllColumnsForARelation(String relation) throws SQLException;
}
