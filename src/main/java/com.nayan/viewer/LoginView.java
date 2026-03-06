package com.nayan.viewer;

import com.nayan.controller.oracle.OracleDBConnector;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.nayan.viewer.utilities.UIConstants;
import com.nayan.viewer.utilities.UIUtils;
import com.nayan.model.Config.DBConfig;

import java.util.HashMap;

public class LoginView {
    private final Stage primaryStage;
    private DBConfig dbConfig;
    private final HBox databaseSelectionBox;
    private final GridPane configFieldPane;
    private final HBox buttonBox;
    private final Label messageLabel;

    public LoginView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.dbConfig = new DBConfig();

        // Database Type Selection
        this.databaseSelectionBox = UIUtils.createDatabaseSelectionBox();

        // Dynamic Fields Container
        this.configFieldPane =  UIUtils.createConfigurationInfoBox(UIConstants.CommonDBConfigFields);

        // Message Labels
        this.messageLabel = new Label();
        this.messageLabel.setStyle("-fx-text-fill: #0066cc;");

        HashMap<String, Runnable> buttonActions = new HashMap<>();
        buttonActions.put(UIConstants.TestButtonLabel, this::testDBConnection);
//        buttonActions.put(UIConstants.OpenButtonLabel, this::OpenConnection);
        buttonActions.put(UIConstants.ClearButtonLabel, this::clearFields);

        // Define button actions
        // Test connection box and Connect button
        this.buttonBox = UIUtils.createButtonBox(buttonActions);
    }

    public void show() {
        primaryStage.setTitle("Nayan - Database Viewer");
        primaryStage.setWidth(800);
        primaryStage.setHeight(700);

        // Create main container
        VBox mainContainer = new VBox(15);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setStyle("-fx-font-family: 'Segoe UI', Arial; -fx-font-size: 11;");

        // Title
        Label titleLabel = new Label("Database Connection Configuration");
        titleLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        // Add all components to main container
        mainContainer.getChildren().addAll(
                titleLabel,
                new Separator(),
                databaseSelectionBox,
                new Label("Connection Details:"),
                configFieldPane,
                new Separator(),
                messageLabel,
                buttonBox
        );

        Scene scene = new Scene(mainContainer);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void testDBConnection() {
        String databaseType = (String) this.databaseSelectionBox.getChildren().get(1).getUserData();
        TextField[] textFields = (TextField[]) this.configFieldPane.getUserData();

        if (textFields == null || textFields.length == 0) {
            showMessage("Error: No fields available", true);
            return;
        }

        String hostname = textFields[0].getText();
        String port = textFields[1].getText();
        String sid = textFields[2].getText();
        String username = textFields[3].getText();
        String password = textFields[4].getText();


        // Basic validation
        if (hostname.isEmpty() || port.isEmpty() || sid.isEmpty()) {
            showMessage("Error: Provide complete DB config", true);
            return;
        }

        if (username.isEmpty() || password.isEmpty()) {
            showMessage("Error: Username and password are required", true);
            return;
        }

        this.dbConfig = new DBConfig(sid, hostname, port, username, password);

        switch (databaseType) {
            case UIConstants.OracleDisplayName:
                testOracleConnection(this.dbConfig);
                break;
            case UIConstants.PostgresDisplayName:
                showMessage("Implementation for PostgreSQL driver is pending", false);
                break;
            default:
                showMessage("Error: Unknown database type selected", true);
        }
    }

    private void testOracleConnection(DBConfig dbConfig) {
        OracleDBConnector db = new OracleDBConnector(dbConfig);
        try {
            db.connect();
            showMessage("✓ Connection successful!", false);
        } catch (Exception e) {
            showMessage("X Error: " + e.getMessage(), true);
        }
    }

    private void clearFields() {
        TextField[] textFields = (TextField[]) configFieldPane.getUserData();
        if (textFields != null) {
            for (TextField field : textFields) {
                field.clear();
            }
        }
        messageLabel.setText("");
    }

    private void showMessage(String message, boolean isError) {
        messageLabel.setText(message);
        if (isError) {
            messageLabel.setStyle("-fx-text-fill: #ff0000;");
        } else {
            messageLabel.setStyle("-fx-text-fill: #008000;");
        }
    }
}
