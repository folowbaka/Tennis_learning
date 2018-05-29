package vue;

import controller.ChartController;
import controller.DataController;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.Chart;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IhmApplication {

    private DataController dataController;
    private ChartController chartController;
    private BorderPane root;
    private Stage primaryStage;

    public IhmApplication(Stage primaryStage) throws Exception
    {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("sample.fxml"));
        this.root =loader.load();
        this.dataController=loader.getController();
        loader=new FXMLLoader(getClass().getResource("chartPanel.fxml"));
        TabPane centerTab=loader.load();
        this.chartController=loader.getController();
        ((VBox)this.root.getCenter()).getChildren().add(centerTab);
        this.dataController.setApplication(this);
        this.chartController.setApplication(this);
        this.primaryStage=primaryStage;

    }

    public DataController getDataController() {
        return dataController;
    }

    public void setDataController(DataController dataController) {
        this.dataController = dataController;
    }

    public ChartController getChartController() {
        return chartController;
    }

    public void setChartController(ChartController chartController) {
        this.chartController = chartController;
    }

    public BorderPane getRoot() {
        return root;
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
