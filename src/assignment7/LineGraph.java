/* Cheaters GUI <LineGraph.java>
 * EE422C Project 7 submission by
 * Kassandra Smith
 * KSS2474
 * 15465
 * Brian Wilmarth
 * bw24274
 * 15455
 * Slip days used: <0>
 * Spring 2018
 */

/*
   Describe here known bugs or issues in this file. If your issue spans multiple
   files, or you are not sure about details, add comments to the README.txt file.
 */
package assignment7;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Hashtable;

public class LineGraph extends Application {
    static HashMap<String, Integer> fileSimilarities = new HashMap<>();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Similarities between files");
        //defining the axes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Compared to file: ");
        yAxis.setLabel("Number of similarities");
        //creating the chart
        final LineChart<String, Number> lineChart =
                new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Cheaters");
        //defining a series
        //            series.setName("File #" + String.valueOf(i));
        Scene scene = new Scene(lineChart, 800, 600);
        HashMap<String, XYChart.Series<String, Number>> trackingSeries = new HashMap<>();
        for (String s : fileSimilarities.keySet()) {
            XYChart.Series<String, Number> series;
            String[] ids = s.split(" ");
            if (trackingSeries.get(ids[0]) == null) {
                series = new XYChart.Series<String, Number>();
                trackingSeries.put(ids[0], series);
            } else {
                series = trackingSeries.get(ids[0]);
            }
            series.setName(ids[0]);
            //populating the series with data
            boolean add = series.getData().add(new XYChart.Data<>(ids[1], fileSimilarities.get(s)));

        }
        for (String s : trackingSeries.keySet()) {
            XYChart.Series<String, Number> series = trackingSeries.get(s);
            lineChart.getData().add(series);
        }
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
