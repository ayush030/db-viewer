package com.nayan.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

public class Config {
    @Getter
    private DBConfig dbConfig;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    public static class DBConfig {
        private String database;
        private String hostname;
        private String username;
        private String password;
        private String port;

        public DBConfig() {
            // Default constructor
        }

        public DBConfig(String database, String hostname, String port, String username, String password) {
            this.database = database;
            this.hostname = hostname;
            this.username = username;
            this.password = password;
            this.port = port;
        }
    }

    public Config(DBConfig dbConfig) {
        this.dbConfig = dbConfig;
    }
}