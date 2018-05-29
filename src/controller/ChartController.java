package controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import modele.MovType;
import modele.Movement;
import modele.PreData;

import java.util.HashMap;

public class ChartController extends  TennisController {

    @FXML
    private ScrollPane chartScrollPane;

    @FXML
    private ScrollPane chartScrollPaneAccel;

    @FXML
    private ScrollPane chartScrollPaneGyro;

    public void drawChartMov()
    {
        //creating the chart
        VBox chartPanel=new VBox();
        VBox chartPanelAccel=new VBox();
        VBox chartPanelGyro=new VBox();
        chartPanel.setMinSize(1500,4000);
        chartPanelAccel.setMinSize(1500,5000);
        LineChart<Number,Number> lineChart = null;
        //defining a series
        //populating the series with data
        int nbMov=PreData.getCsvMov().size();
        for (MovType movType : MovType.values())
        {
            if(!movType.getMovType().equals("NULL"))
            {
                XYChart.Series series = new XYChart.Series();
                int pointGraph = 0;
                for (int i = 0; i < nbMov; i++)
                {
                    Movement mouvement = PreData.getCsvMov().get(i);
                    if (mouvement.getMovType().getMovType().equals(movType.getMovType())) {
                        int nbVector = mouvement.getVectorMov().size();
                        for (int j = 0; j < nbVector; j++) {
                            series.getData().add(new XYChart.Data(pointGraph + 1, mouvement.getVectorMov().get(j).norm()));
                            pointGraph++;
                        }
                        pointGraph += 10;
                    }

                }
                NumberAxis yAxis = new NumberAxis();
                NumberAxis xAxis = new NumberAxis();
                lineChart = new LineChart<Number, Number>(xAxis, yAxis);
                lineChart.setTitle(movType.getMovType());
                lineChart.getData().add(series);
                chartPanel.getChildren().add(lineChart);

                series = new XYChart.Series();
                int nbPointAcel=PreData.acelleroData.get(movType.getMovType()).size();
                for (int i = 0; i < nbPointAcel; i++)
                {

                            series.getData().add(new XYChart.Data(i+1,PreData.acelleroData.get(movType.getMovType()).get(i)));
                }
                xAxis = new NumberAxis(1,series.getData().size(),3);
                xAxis.setAutoRanging(false);
                lineChart = new LineChart<Number, Number>(xAxis, yAxis);
                lineChart.setTitle(movType.getMovType());
                lineChart.getData().add(series);
                chartPanelAccel.getChildren().add(lineChart);

                series = new XYChart.Series();
                int nbPointGyro=PreData.gyroData.get(movType.getMovType()).size();
                for (int i = 0; i < nbPointGyro; i++)
                {

                    series.getData().add(new XYChart.Data(i+1,PreData.gyroData.get(movType.getMovType()).get(i)));
                }
                xAxis = new NumberAxis(1,series.getData().size(),3);
                yAxis=new NumberAxis(1,10,1);
                lineChart = new LineChart<Number, Number>(xAxis, yAxis);
                lineChart.setTitle(movType.getMovType());
                lineChart.getData().add(series);
                chartPanelGyro.getChildren().add(lineChart);
            }
        }

        chartScrollPane.setContent(chartPanel);
        chartScrollPaneAccel.setContent(chartPanelAccel);
        chartScrollPaneGyro.setContent(chartPanelGyro);
    }
}
