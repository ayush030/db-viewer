package com.nayan.controller.oracle;

import com.nayan.controller.DBConnector;
import com.nayan.model.Config.DBConfig;
import lombok.NonNull;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

// class to provide DB connection utilities
public class OracleDBConnector extends DBConnector {
    private final String dbUrl;
    private final String driverType = "thin";
    private DBConfig config;

    public OracleDBConnector(
            @NonNull
            DBConfig dbConfig
    ) {
        this.config = dbConfig;

        // dbUrl : jdbc:oracle:thin:@hostname:port:sid
        this.dbUrl = "jdbc:oracle:thin:@" + dbConfig.getHostname() + ":" + dbConfig.getPort() + ":" + dbConfig.getDatabase();
    }

    private
    @NonNull Connection
    getConnection() throws SQLException {
            OracleDataSource ds = new OracleDataSource();

            ds.setURL(this.dbUrl);
            ds.setUser(this.config.getUsername());
            ds.setPassword(this.config.getPassword());

            Connection conn = ds.getConnection();
            conn.setAutoCommit(false);
            conn.setReadOnly(true);

            return conn;
    }

    @Override
    // connect : method to check if connection is successful
    public void connect() throws SQLException {
        // check if connection is successful using dbUrl
        this.getConnection();

        // autocloses DB connection
    }

    @Override
    // getAllRelations : method to fetch all relations from the database
    public ResultSet getAllRelations() {
        ResultSet result = null;
        try (Connection conn = this.getConnection()) {
            PreparedStatement fetchRelations = conn.prepareStatement(OracleSQLQueries.GET_ALL_RELATIONS);
            result = fetchRelations.executeQuery();
        } catch (SQLException ex) {
            System.err.println("Error in fetching table list " + "\nStack:\n" + ex.getMessage());
        }

        return result;
    }

    @Override
    // getAllColumnsForARelation : method to fetch all columns for a given relation
    public ResultSet getAllColumnsForARelation(String tableName) {
        ResultSet result = null;

        try (Connection conn = this.getConnection()) {
            PreparedStatement fetchColumns = conn.prepareStatement(OracleSQLQueries.GET_RELATION_COLUMNS);
            fetchColumns.setString(1, tableName.toUpperCase());
            result = fetchColumns.executeQuery();
        } catch (SQLException ex) {
            System.err.println("Error in fetching column detail for table " + tableName + "\nStack:\n" + ex.getMessage());
        }

        return result;
    }

    @Override
    // getAllDataForARelation : method to fetch all data for a given relation
    public ResultSet getAllDataForARelation(String tableName) {
        ResultSet result = null;

        try (Connection conn = this.getConnection()) {
            PreparedStatement fetchData = conn.prepareStatement(OracleSQLQueries.GET_ALL_ROWS);
            fetchData.setString(1, tableName.toUpperCase());
            result = fetchData.executeQuery();
        } catch (SQLException ex) {
            System.err.println("Error in fetching data for table " + tableName + "\nStack:\n" + ex.getMessage());
        }

        return result;
    }
}