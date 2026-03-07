package com.nayan.controller.oracle;

public final class OracleSQLQueries {
    // fetch all relations from the database
    public static final String GET_ALL_RELATIONS = "SELECT table_name FROM user_tables";

    // fetch all data for a given relation
    public static final String GET_ALL_ROWS = "SELECT * FROM ?";

    // fetch all columns for a given relation
    public static final String GET_RELATION_COLUMNS = "SELECT column_name FROM user_tab_columns WHERE table_name = ?";
}