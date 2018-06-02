package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import modele.Move;
import modele.PreData;
import modele.PreDataVector;

import java.io.File;
import java.util.ArrayList;
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
            preDataFileTextF.setText(selectedFile.getAbsolutePath());
            File[] listOfCSV=selectedFile.listFiles();
            int nbMovGyro=0;
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
                    PreData.detectMov(movType,40,3,PreData.getCsvData(),PreData.getCsvMov(),true);
                    PreData.detectMov(movType,40,9,PreData.getCsvDataAccel(),PreData.getCsvMovAccel(),false);
                    int nbMovGyroBefore=nbMovGyro;
                    PreData.detectMov(movType,7 ,1,PreData.getCsvDataGyro(),PreData.getCsvMovGyro(),false);
                    nbMovGyro=PreData.getCsvMovGyro().size();
                    for(int j=nbMovGyroBefore;j<nbMovGyro;j++)
                    {
                        int nbVectorMovGyro=PreData.getCsvMovGyro().get(j).getVectorMov().size();
                        int beginTime=PreData.getCsvMovGyro().get(j).getVectorMov().get(0).getTime();
                        int endTime=PreData.getCsvMovGyro().get(j).getVectorMov().get(nbVectorMovGyro-1).getTime();
                        ArrayList<PreDataVector> vectorMov=new ArrayList<>();
                        boolean foundBegin=false;

                        int k=0;
                        int nbVector=PreData.getCsvData().size();
                        while(!foundBegin && k<nbVector)
                        {
                            if(PreData.getCsvData().get(k).getTime()==beginTime)
                                foundBegin=true;
                            else
                                k++;
                        }
                        boolean foundEnd=false;
                        while(!foundEnd  && k<nbVector)
                        {
                            if(PreData.getCsvData().get(k).getTime()==endTime)
                                foundEnd=true;
                            vectorMov.add(PreData.getCsvData().get(k));
                            k++;

                        }
                        PreData.getCsvMovAccelGyro().add(new Move(vectorMov,PreData.getCsvMovGyro().get(j).getMoveType().getMovType()));

                    }
                }
            }
            PreData.writeArff(selectedFile,PreData.ARFFKNN,PreData.getCsvMov(),"RAW");
            PreData.writeArff(selectedFile,PreData.TREE,PreData.getCsvMov(),"RAW");
            PreData.writeArff(selectedFile,PreData.TREE,PreData.getCsvMovAccel(),"ACCEL");
            PreData.writeArff(selectedFile,PreData.TREE,PreData.getCsvMovGyro(),"GYRO");
            PreData.writeArff(selectedFile,PreData.TREE,PreData.getCsvMovAccelGyro(),"ACCELGYRO");
            this.getApplication().getChartController().drawChartMov();
        }
    }
}
