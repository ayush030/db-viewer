package com.nayan.viewer;

import com.nayan.viewer.utilities.UIConstants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AccessLevel;
import lombok.Getter;

public enum DatabaseTypes {
    Oracle(UIConstants.OracleDisplayName, UIConstants.CommonDBConfigFields),
    Postgres(UIConstants.PostgresDisplayName, UIConstants.CommonDBConfigFields);

    @Getter(AccessLevel.PUBLIC)
    private final String displayName;

    @Getter(AccessLevel.PUBLIC)
    private final String[] fields;

    DatabaseTypes(String displayName, String[] requiredFields) {
        this.displayName = displayName;
        this.fields = requiredFields;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static ObservableList<String> options() {
        ObservableList<String> list = FXCollections.observableArrayList();

        for (DatabaseTypes type : DatabaseTypes.values()) {
            list.add(type.getDisplayName());
        }

        return list;
    }
}
