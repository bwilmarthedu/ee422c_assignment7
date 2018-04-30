package assignment7;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;


public class LineGraph extends Application {
    static ArrayList<Hashtable<String, Integer>> fileSimilarities = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Similarities between files");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Compared to file: ");
        yAxis.setLabel("Number of similarities");
        //creating the chart
        final LineChart<Number, Number> lineChart =
                new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Cheaters");
        //defining a series
        Scene scene = new Scene(lineChart, 800, 600);
        for (int i = 0; i < fileSimilarities.size(); i++) {


            XYChart.Series series = new XYChart.Series();
            series.setName("File #" + String.valueOf(i));
            for (String s : fileSimilarities.get(i).keySet()) {
                //populating the series with data
                series.getData().add(new XYChart.Data(1, fileSimilarities.get(i).get(s)));
                series.getData().add(new XYChart.Data(2, fileSimilarities.get(i).get(s)));
                series.getData().add(new XYChart.Data(3, fileSimilarities.get(i).get(s)));
                series.getData().add(new XYChart.Data(4, fileSimilarities.get(i).get(s)));
                series.getData().add(new XYChart.Data(5, fileSimilarities.get(i).get(s)));
                series.getData().add(new XYChart.Data(6, fileSimilarities.get(i).get(s)));
                series.getData().add(new XYChart.Data(7, fileSimilarities.get(i).get(s)));
                series.getData().add(new XYChart.Data(8, fileSimilarities.get(i).get(s)));
                series.getData().add(new XYChart.Data(9, fileSimilarities.get(i).get(s)));
                series.getData().add(new XYChart.Data(10, fileSimilarities.get(i).get(s)));
                series.getData().add(new XYChart.Data(11, fileSimilarities.get(i).get(s)));
                series.getData().add(new XYChart.Data(12, fileSimilarities.get(i).get(s)));


                lineChart.getData().add(series);
            }
            stage.setScene(scene);}
            stage.show();
        }

        public static void main (String[]args){
            launch(args);
        }
    }