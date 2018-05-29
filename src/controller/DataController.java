package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import modele.PreData;

import java.io.File;
import java.util.regex.Pattern;

public class DataController extends TennisController{
    @FXML
    private Button findDataSensor;

    @FXML
    private TextField preDataFileTextF;
    @FXML
    private void chooseCsvDataSensor()
    {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Répertoire CSV");
        File selectedFile=chooser.showDialog(this.getApplication().getPrimaryStage());
        if(selectedFile!=null)
        {
            PreData.initSimpleChartData();
            preDataFileTextF.setText(selectedFile.getAbsolutePath());
            File[] listOfCSV=selectedFile.listFiles();
            for(int i=0;i<listOfCSV.length;i++)
            {
                String movType;
                if(Pattern.matches("^CoupDroit_Coupe*.*",listOfCSV[i].getName()))
                    movType="CDC";
                else if(Pattern.matches("^CoupDroit_Lifte*.*",listOfCSV[i].getName()))
                    movType="CDL";
                else if(Pattern.matches("^CoupDroit_Plat*.*",listOfCSV[i].getName()))
                    movType="CDP";
                else if(Pattern.matches("^Revers_Coupe*.*",listOfCSV[i].getName()))
                    movType="RC";
                else if(Pattern.matches("^Revers_Lifte*.*",listOfCSV[i].getName()))
                    movType="RL";
                else if(Pattern.matches("^Revers_Plat*.*",listOfCSV[i].getName()))
                    movType="RP";
                else if(Pattern.matches("^Service_Coupe*.*",listOfCSV[i].getName()))
                    movType="SC";
                else if(Pattern.matches("^Service_Lifte*.*",listOfCSV[i].getName()))
                    movType="SL";
                else if(Pattern.matches("^Service_Plat*.*",listOfCSV[i].getName()))
                    movType="SP";
                else if(Pattern.matches("^Smatch*.*",listOfCSV[i].getName()))
                    movType="SMA";
                else if(Pattern.matches("^VoletCoupDroit*.*",listOfCSV[i].getName()))
                    movType="VCD";
                else if(Pattern.matches("^VoletRevers*.*",listOfCSV[i].getName()))
                    movType="VR";
                else
                    movType="NULL";
                if(!movType.equals("NULL"))
                {
                    PreData.readCsv(listOfCSV[i].getAbsolutePath());
                    System.out.println(listOfCSV[i].getName());
                    PreData.detectMov(movType);
                }
            }
            PreData.writeArff(selectedFile,PreData.ARFFKNN);
            PreData.writeArff(selectedFile,PreData.TREE);
            this.getApplication().getChartController().drawChartMov();
        }
    }
}
