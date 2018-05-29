
import controller.ChartController;
import controller.DataController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vue.IhmApplication;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        IhmApplication application=new IhmApplication(primaryStage);
        primaryStage.setTitle("Hello World");
        application.getRoot().setScaleShape(true);
        primaryStage.setScene(new Scene(application.getRoot(), 1500, 900));
        primaryStage.setResizable(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
