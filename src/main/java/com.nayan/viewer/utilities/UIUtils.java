package com.nayan.viewer.utilities;

import com.nayan.viewer.DatabaseTypes;
import com.nayan.viewer.utilities.UIConstants;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.HashMap;
import java.util.Objects;

public class UIUtils {
    private static final HashMap<String, String> defaultConfigValue = new HashMap<>() {
        {
           put(UIConstants.HostnameFieldLabel, "localhost");
           put(UIConstants.PortFieldLabel, "1521");
           put(UIConstants.SIDFieldLabel, "XE");
           put(UIConstants.UsernameFieldLabel, "system");
        }
    };
    public static HBox createButtonBox(HashMap<String, Runnable> buttonActions) {
        HBox buttonBox = new HBox(10);

        for (String t : buttonActions.keySet() ) {
            Button button = new Button(t);
            button.setPrefWidth(120);
            button.setStyle("-fx-padding: 10; -fx-font-size: 12;");
            button.setOnAction(e -> buttonActions.get(t).run());
            buttonBox.getChildren().add(button);
        }

        return buttonBox;
    }

    public static HBox createDatabaseSelectionBox() {
        HBox selectionBox = new HBox(10);
        selectionBox.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-padding: 10;");

        Label typeLabel = new Label("Database Type:");
        typeLabel.setStyle("-fx-font-weight: bold;");

        ComboBox<String> databaseBox = new ComboBox<>();
        databaseBox.setEditable(false);
        databaseBox.setItems(DatabaseTypes.options());

        // set default selection to Oracle
        databaseBox.setValue(DatabaseTypes.Oracle.getDisplayName());
        databaseBox.setUserData(DatabaseTypes.Oracle.getDisplayName());
        databaseBox.setPrefWidth(150);

        databaseBox.setOnAction(e -> {
            databaseBox.setUserData(databaseBox.getValue());
        });

        selectionBox.getChildren().addAll(typeLabel, databaseBox);
        return selectionBox;
    }

    public static GridPane createConfigurationInfoBox(String[] fields) {
        GridPane configGrid = new GridPane();
        configGrid.setHgap(10);
        configGrid.setVgap(10);

        TextField[] textFields = new TextField[fields.length];

        for (int i = 0; i < fields.length; i++) {
            Label fieldLabel = new Label(fields[i] + ":");
            fieldLabel.setStyle("-fx-font-weight: bold;");

            // set echo character for password field
            if (Objects.equals(fields[i], UIConstants.PasswordFieldLabel)) {
                PasswordField pf = new PasswordField();
                pf.setPromptText("Enter password");
                pf.setPrefWidth(300);

                textFields[i] = pf;
            } else {
                TextField tf = new TextField();
                if (defaultConfigValue.containsKey(fields[i])) {
                    tf.setText(defaultConfigValue.get(fields[i]));
                } else {
                    tf.setPromptText("Enter " + fields[i].toLowerCase());
                }

                tf.setPrefWidth(300);
                textFields[i] = tf;
            }

            configGrid.add(fieldLabel, 0, i);
            configGrid.add(textFields[i], 1, i);
        }

        configGrid.setUserData(textFields);

        return configGrid;
    }
}