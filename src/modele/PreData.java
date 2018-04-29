package modele;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class PreData {

    private  static ArrayList<PreDataVector> csvData;

    public static void readCsv(String csvName)
    {
        try (Scanner scanner = new Scanner(new File(csvName)))
        {
            csvData=new ArrayList<>();

            scanner.next();
            while(scanner.hasNext())
            {
                String row=scanner.next();
                String[] rowData=row.split(";");
                csvData.add(new PreDataVector(Double.parseDouble(rowData[2]),Double.parseDouble(rowData[3]),Double.parseDouble(rowData[4])));

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public  static void detectMov()
    {
        int nbVector=csvData.size();
        double[] vectorNorms=new double[nbVector];
        for(int i=0;i<nbVector;i++)
        {
            vectorNorms[i]=csvData.get(i).norm();
        }
        int numVector=0;

        while(numVector<nbVector)
        {
            if (vectorNorms[numVector] > 35)
            {
                int peak=foundPeakMov(numVector,nbVector,vectorNorms);
                System.out.println("PEAK "+peak);
                if(peak+50>nbVector)
                    numVector=nbVector-peak;
                else
                {
                    numVector=peak+50;
                }

            }
            else
            {
                numVector++;
            }
        }

    }

    public static int foundPeakMov(int numVector,int nbVector,double[] vectorNorms)
    {
        int peak=0;
        double maxNorm=0;
        int intervalle=50;
        if(numVector+intervalle>nbVector)
        {
            intervalle=nbVector-numVector;
        }
        for(int i=numVector;i<numVector+intervalle;i++)
        {
            if(vectorNorms[i]>maxNorm)
            {
                maxNorm=vectorNorms[i];
                peak=i;
            }
        }
        return peak;
    }

}
