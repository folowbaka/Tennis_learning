package controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import modele.MovType;
import modele.Movement;
import modele.PreData;

public class ChartController extends  TennisController {

    @FXML
    private ScrollPane chartScrollPane;

    public void drawChartMov()
    {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle("Coup Droit Coupé");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        //populating the series with data
        int nbMov=PreData.getCsvMov().size();
        int pointCDC=0;
        for(int i=0;i<nbMov;i++)
        {
            if(PreData.getCsvMov().get(i).getMovType()==MovType.CDC)
            {
                Movement mouvement= PreData.getCsvMov().get(i);
                int nbVector=mouvement.getVectorMov().size();
                for(int j=0;j<nbVector;j++)
                {
                    series.getData().add(new XYChart.Data(pointCDC+1,mouvement.getVectorMov().get(j).norm()));
                    pointCDC++;
                }
                pointCDC+=10;

            }
        }
        lineChart.getData().add(series);

        chartScrollPane.setContent(lineChart);
    }
}
