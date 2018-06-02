package controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import modele.MoveType;
import modele.Move;
import modele.PreData;

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
        int nbMovAccel=PreData.getCsvMovAccel().size();
        int nbMovGyro=PreData.getCsvMovGyro().size();
        for (MoveType moveType : MoveType.values())
        {
            if(!moveType.getMovType().equals("NULL"))
            {
                XYChart.Series series = new XYChart.Series();
                int pointGraph = 0;
                for (int i = 0; i < nbMov; i++)
                {
                    Move mouvement = PreData.getCsvMov().get(i);
                    if (mouvement.getMoveType().getMovType().equals(moveType.getMovType())) {
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
                lineChart.setTitle(moveType.getMovType());
                lineChart.getData().add(series);
                chartPanel.getChildren().add(lineChart);

                series = new XYChart.Series();
                pointGraph=0;
                for (int i = 0; i < nbMovAccel; i++)
                {
                    Move mouvement = PreData.getCsvMovAccel().get(i);
                    if (mouvement.getMoveType().getMovType().equals(moveType.getMovType())) {
                        int nbVector = mouvement.getVectorMov().size();
                        for (int j = 0; j < nbVector; j++) {
                            series.getData().add(new XYChart.Data(pointGraph + 1, mouvement.getVectorMov().get(j).norm()));
                            pointGraph++;
                        }
                        pointGraph += 10;
                    }

                }
                xAxis = new NumberAxis(0,350,2);
                lineChart = new LineChart<Number, Number>(xAxis, yAxis);
                lineChart.setTitle(moveType.getMovType());
                lineChart.getData().add(series);
                chartPanelAccel.getChildren().add(lineChart);

                series = new XYChart.Series();
                pointGraph=0;
                for (int i = 0; i < nbMovGyro; i++)
                {
                    Move mouvement = PreData.getCsvMovGyro().get(i);
                    if (mouvement.getMoveType().getMovType().equals(moveType.getMovType())) {
                        int nbVector = mouvement.getVectorMov().size();
                        for (int j = 0; j < nbVector; j++) {
                            series.getData().add(new XYChart.Data(pointGraph + 1, mouvement.getVectorMov().get(j).norm()));
                            pointGraph++;
                        }
                        pointGraph += 10;
                    }

                }
                xAxis = new NumberAxis(0,450,2);
                yAxis = new NumberAxis(0,20,1);
                lineChart = new LineChart<Number, Number>(xAxis, yAxis);
                lineChart.setTitle(moveType.getMovType());
                lineChart.getData().add(series);
                chartPanelGyro.getChildren().add(lineChart);
            }
        }

        chartScrollPane.setContent(chartPanel);
        chartScrollPaneAccel.setContent(chartPanelAccel);
        chartScrollPaneGyro.setContent(chartPanelGyro);
    }
}
