package com.jdbc.economydata;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SceneSwitcher {

    public void switchToTableView(Stage primaryStage) {
        TableView<CountryEconomy> tableView = new TableView<>();

        TableColumn<CountryEconomy, String> countryColumn = new TableColumn<>("Country");
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

        TableColumn<CountryEconomy, Double> gdpColumn = new TableColumn<>("GDP");
        gdpColumn.setCellValueFactory(new PropertyValueFactory<>("gdp"));

        TableColumn<CountryEconomy, Double> inflationColumn = new TableColumn<>("Inflation Rate");
        inflationColumn.setCellValueFactory(new PropertyValueFactory<>("inflationRate"));

        TableColumn<CountryEconomy, Double> unemploymentColumn = new TableColumn<>("Unemployment Rate");
        unemploymentColumn.setCellValueFactory(new PropertyValueFactory<>("unemploymentRate"));

        tableView.getColumns().add(countryColumn);
        tableView.getColumns().add(gdpColumn);
        tableView.getColumns().add(inflationColumn);
        tableView.getColumns().add(unemploymentColumn);

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM CountryEconomy WHERE year = 2021")) {

            while (rs.next()) {
                tableView.getItems().add(new CountryEconomy(
                        rs.getString("country"),
                        rs.getDouble("gdp"),
                        rs.getDouble("inflation_rate"),
                        rs.getDouble("unemployment_rate")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button switchToChartButton = new Button("Switch to Chart View");
        switchToChartButton.setOnAction(e -> switchToChartView(primaryStage));

        VBox vbox = new VBox(tableView, switchToChartButton);
        Scene tableViewScene = new Scene(vbox, 800, 600);
        tableViewScene.getStylesheets().add("styles.css");

        primaryStage.setScene(tableViewScene);
    }

    public void switchToChartView(Stage primaryStage) {
        // Re-create the chart view
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

        Button switchToTableButton = new Button("Switch to Table View");
        switchToTableButton.setOnAction(e -> switchToTableView(primaryStage));

        VBox vbox = new VBox(barChart, switchToTableButton);
        Scene chartScene = new Scene(vbox, 800, 600);
        chartScene.getStylesheets().add("styles.css");

        primaryStage.setScene(chartScene);
    }
}
