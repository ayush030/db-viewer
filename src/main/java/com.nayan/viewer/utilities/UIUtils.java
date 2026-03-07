package com.nayan.viewer.utilities;

import com.nayan.viewer.DatabaseTypes;

import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

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

    public static HBox createSearchResultContainer() {
        HBox searchResultContainer = new HBox(10);
        searchResultContainer.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-padding: 10;");

        Label searchLabel = new Label("Search");
        searchLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        ComboBox<String> searchColumnBox = new ComboBox<>();
        searchColumnBox.setPrefWidth(300);

        String searchConditionPrompt = "bmFtZT0nTmF2aW4nIGFuZCBzdGF0dXM9J3NpbmdsZSc=";
        Base64.Decoder decoded = Base64.getDecoder();
        byte[] decodedBytes = decoded.decode(searchConditionPrompt);
        searchColumnBox.setPromptText(new String(decodedBytes, StandardCharsets.UTF_8));

        searchColumnBox.setOnAction(e -> {
            String selectedColumn = searchColumnBox.getValue();
            if (selectedColumn != null && !selectedColumn.isEmpty()) {
                // Implement search logic based on selected column
                searchColumnBox.setUserData(selectedColumn);

            }
        });

        Image reconnImage = new Image("file:images/reconnect.jpeg");
        ImageView reconnIcon = new ImageView(reconnImage);
        reconnIcon.setFitHeight(30);

        Button reConnectButton = new Button();
        reConnectButton.setGraphic(reconnIcon);
        reConnectButton.setStyle("-fx-background-color: transparent;");

        reConnectButton.setOnMouseEntered(e -> reConnectButton.setText("Reconnect DB"));
        reConnectButton.setOnMouseExited(e -> reConnectButton.setText(""));

        searchResultContainer.getChildren().addAll(searchLabel, searchColumnBox, reConnectButton);
        return searchResultContainer;
    }

    public static VBox createTableListBox(Runnable populateTableList, Runnable onTableSelect) {
        VBox tableListBox = new VBox(10);

        tableListBox.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-padding: 10;");
        tableListBox.setPrefWidth(100);


        return tableListBox;
    }
}