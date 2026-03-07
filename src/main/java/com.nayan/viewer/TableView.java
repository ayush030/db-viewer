package com.nayan.viewer;

import com.nayan.controller.DBConnector;
import com.nayan.model.Config.DBConfig;
import com.nayan.viewer.utilities.UIUtils;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TableView {
    private final Stage tableViewStage;
    private final DBConfig dbConfig;

    private final HBox searchResultContainer;
    private VBox tableListBox;
    private VBox tableDataContainer;

    private DBConnector db;

    public TableView(Stage tableViewStage, DBConfig dbConfig) {
        this.tableViewStage = tableViewStage;
        this.dbConfig = dbConfig;

        this.searchResultContainer = UIUtils.createSearchResultContainer();
        // this.tableListBox = UIUtils.createTableListBox();
    }

    public void show() {
        VBox mainContainer = new VBox();
        mainContainer.setPadding(new Insets(20));

        mainContainer.getChildren().addAll(searchResultContainer);
    }
}
