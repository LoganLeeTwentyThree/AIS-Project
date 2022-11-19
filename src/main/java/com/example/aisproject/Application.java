package com.example.aisproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuItem;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    public static final int YEAR_RANGE = 40;
    public static final int NUM_ENTRIES = 10;

    int[] years = {1980, 1981, 1982, 1983, 1984, 1985, 1986, 1987, 1988, 1989, 1990, 1991, 1992, 1993, 1994, 1995, 1996,
            1997, 1998, 1999, 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016,
            2017, 2018, 2019, 2020};

    int[] predictYears = {2021, 2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030};

    String[] names = {"Grapefruit", "Lemons", "Oranges", "Tangerines", "Apples", "Avocados", "Cherries", "Grapes", "Nectarines", "Olives", "Peaches"};

    private Stage currStage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        currStage = stage;
        stage.setTitle("Pracker");
        stage.setScene(scene);
        stage.show();

    }



    public static void main(String[] args) {


        launch();
    }

    private int[][] getRawData(){
        CSVReader reader = new CSVReader();
        String[][] result;
        result = reader.readCSVFile("src/main/java/com/example/aisproject/ProduceStats.csv");


        int[][] values = new int[YEAR_RANGE + 1][11];

        for(int i = 1; i < YEAR_RANGE + 1; i++){
            for(int j = 0; j < NUM_ENTRIES + 1; j++){
                try {
                    values[i][j] = Integer.parseInt(result[i][j]);
                }catch(Exception e){

                }

            }
        }

        return values;
    }

    private double[] getCoefficients(int type){
        int[][] values = getRawData();
        int[] produceData = new int[NUM_ENTRIES + 1];

        for(int i = 1; i < NUM_ENTRIES + 1; i++){
            produceData[i] = values[i][type];
        }
        PolynomialRegression polynomialRegression = new PolynomialRegression();
        return polynomialRegression.regress(produceData);
    }

    private LineChart createChart(String xName, String yName, String name){
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();


        yAxis.setAutoRanging(false);
        yAxis.setLabel(yName);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(8000000);
        yAxis.setTickUnit(10000);

        LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle(name);



        return lineChart;
    }

    private XYChart.Series createSeries(String name, int[] dataX, int[] dataY, int length){
        //defining a series

        XYChart.Series series = new XYChart.Series();
        series.setName(name);

        //populating the series with data

        if(length > 30){
            for(int i = 1; i < length + 1; i++){
                series.getData().add(new XYChart.Data(dataX[i], dataY[i]));
            }
        }else{
            for(int i = 0; i < length; i++){
                series.getData().add(new XYChart.Data(dataX[i], dataY[i]));
            }
        }



        return series;
    }

    public MenuItem GrapeFruitButton;
    public MenuItem LemonButton;
    public MenuItem OrangeButton;
    public MenuItem TangerineButton;
    public MenuItem AppleButton;
    public MenuItem AvocadoButton;
    public MenuItem CherryButton;
    public MenuItem GrapeButton;
    public MenuItem NectarineButton;
    public MenuItem OliveButton;
    public MenuItem PeachesButton;
    public LineChart lineChart;

    public NumberAxis xAxis;
    public NumberAxis yAxis;
    public LineChart predictionLineChart;
    public NumberAxis pXAxis;
    public NumberAxis pYAxis;

    public void initialize() {
        GrapeFruitButton.setOnAction(e -> changeSeries(1));
        LemonButton.setOnAction(e -> changeSeries(2));
        OrangeButton.setOnAction(e -> changeSeries(3));
        TangerineButton.setOnAction(e -> changeSeries(4));
        AppleButton.setOnAction(e -> changeSeries(5));
        AvocadoButton.setOnAction(e -> changeSeries(6));
        CherryButton.setOnAction(e -> changeSeries(7));
        GrapeButton.setOnAction(e -> changeSeries(8));
        NectarineButton.setOnAction(e -> changeSeries(9));
        OliveButton.setOnAction(e -> changeSeries(10));
        PeachesButton.setOnAction(e -> changeSeries(11));
    }
    protected void changeSeries(int num) {

        //Data chart
        lineChart.getData().clear();
        XYChart.Series series;

        int[][] values = getRawData();
        int[] data = new int[YEAR_RANGE + 1];
        int max = 0;
        int min = Integer.MAX_VALUE;
        for(int j = 0; j < YEAR_RANGE + 1; j++){
            data[j] = values[j][num];
            if(data[j] > max){
                max = data[j];
            }

            if(data[j] < min){
                min = data[j];
            }

        }

        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(max + 9000);
        yAxis.setLowerBound(min);

        series = createSeries(names[num - 1], years, data, YEAR_RANGE);
        lineChart.getData().add(series);

        //Prediction chart

        predictionLineChart.getData().clear();
        XYChart.Series pSeries;
        PolynomialRegression poly = new PolynomialRegression();
        poly.regress(data);
        int[] result = new int[10];

        int pMin = Integer.MAX_VALUE;
        int pMax = Integer.MIN_VALUE;
        for(int i = 0; i < 10; i++){
            result[i] = poly.prediction(2020+i);

            if(result[i] > pMax){
                pMax = result[i];
            }

            if(result[i] < pMin){
                pMin = result[i];
            }
        }

        pYAxis.setAutoRanging(false);
        pYAxis.setUpperBound(pMax);
        pYAxis.setLowerBound(pMin);
        pYAxis.setTickUnit((pMax + Math.abs(pMin))/200 );
        pSeries = createSeries("Prediction", predictYears, result, 10);
        predictionLineChart.getData().add(pSeries);

    }



}