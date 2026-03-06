package com.nayan.controller;

import java.sql.SQLException;

public abstract class DBConnector {
    public abstract void connect() throws SQLException;
}
