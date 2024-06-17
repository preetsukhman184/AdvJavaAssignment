package com.jdbc.economydata;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Economy Comparison");

        // Create the chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        xAxis.setLabel("Country");
        yAxis.setLabel("GDP in Billions");

        XYChart.Series<String, Number> gdpSeries = new XYChart.Series<>();
        gdpSeries.setName("GDP 2021");

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT country, gdp FROM CountryEconomy WHERE year = 2021")) {

            while (rs.next()) {
                gdpSeries.getData().add(new XYChart.Data<>(rs.getString("country"), rs.getDouble("gdp")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        barChart.getData().add(gdpSeries);

        // Create the button
        Button switchToTableButton = new Button("Switch to Table View");
        switchToTableButton.setOnAction(e -> new SceneSwitcher().switchToTableView(primaryStage));

        // Layout
        VBox vbox = new VBox(barChart, switchToTableButton);
        Scene chartScene = new Scene(vbox, 800, 600);
        chartScene.getStylesheets().add("styles.css");

        // Set stage properties
        primaryStage.getIcons().add(new Image("file:path/to/icon.png")); // Ensure the path is correct
        primaryStage.setScene(chartScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
