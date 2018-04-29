package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import modele.PreData;

import java.io.File;

public class DataController extends TennisController{
    @FXML
    private Button findDataSensor;

    @FXML
    private TextField preDataFileTextF;
    @FXML
    private void chooseCsvDataSensor()
    {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisissez un fichier csv");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Csv files","*.csv"));
        File selectedFile=fileChooser.showOpenDialog(this.getStage());
        if(selectedFile!=null)
        {
            preDataFileTextF.setText(selectedFile.getAbsolutePath());
            PreData.readCsv(selectedFile.getAbsolutePath());
            PreData.detectMov();
        }
    }
}
