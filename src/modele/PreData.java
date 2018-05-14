package modele;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PreData {

    private  static ArrayList<PreDataVector> csvData;
    private  static ArrayList<Movement> csvMov=new ArrayList<>();
    public  static final int  ARFFKNN =1;
    public  static final int  TREE =2;
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
    public  static void detectMov(String movType)
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
                int beginInterval=0;
                int endInterval=0;

                if(peak+50>nbVector)
                {
                    numVector = nbVector;
                }
                else
                {

                    numVector=peak+51;
                }
                endInterval=numVector;
                if(peak-50>0)
                    beginInterval=peak-50;
                csvMov.add(new Movement(csvData,beginInterval,endInterval,movType));

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

    public static void writeArff(File directory,int classifier)
    {

        File file = new File(directory.getAbsolutePath()+"/"+directory.getName()+classifier+".arff");
        FileWriter fw = null;
        try {

            fw = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedWriter bw;
        PrintWriter pw;
        bw = new BufferedWriter(fw);
        pw = new PrintWriter(bw);
        int nbMov=csvMov.size();
        ArrayList<String> attVals;
        ArrayList<String> attValso;
        ArrayList<Attribute> attsRel;
        ArrayList<Attribute> atts;
        Instances data=null;
        Instances dataRel;
        double[]        vals;
        double[]        valsRel;

        atts=new ArrayList<Attribute>();
        attValso = new ArrayList<String>();
        attVals = new ArrayList<String>();
        switch (classifier)
        {
            case ARFFKNN :
            for (int i = 0; i < nbMov; i++)
                attValso.add(("" + i));
            atts.add(new Attribute("bag-id", attValso));
            attsRel = new ArrayList<Attribute>();
            attsRel.add(new Attribute("x"));
            attsRel.add(new Attribute("y"));
            attsRel.add(new Attribute("z"));
            dataRel = new Instances("vectorXYZ", attsRel, 0);
            atts.add(new Attribute("bag", dataRel, 0));
            for (MovType movType : MovType.values()) {
                attVals.add(movType.getMovType());
            }
            attVals.remove(attVals.size()-1);
            atts.add(new Attribute("class", attVals));
            data = new Instances("Mouvement", atts, 0);
            for (int i = 0; i < nbMov; i++) {
                int nbInterval = csvMov.get(i).getVectorMov().size();

                // 2. create Instances object
                vals = new double[data.numAttributes()];
                dataRel = new Instances(data.attribute(1).relation(), 0);

                for (int j = 0; j < nbInterval; j++) {
                    valsRel = new double[3];
                    valsRel[0] = csvMov.get(i).getVectorMov().get(j).getX();
                    valsRel[1] = csvMov.get(i).getVectorMov().get(j).getY();
                    valsRel[2] = csvMov.get(i).getVectorMov().get(j).getZ();
                    dataRel.add(new DenseInstance(1.0, valsRel));
                }
                vals[0] = attValso.indexOf(("" + i));
                vals[1] = data.attribute(1).addRelation(dataRel);
                vals[2] = attVals.indexOf(csvMov.get(i).getMovType().getMovType());
                // add
                data.add(new DenseInstance(1.0, vals));

                // 4. output data

            }
            break;
            case TREE :
                for(int i=0;i<5;i++)
                {
                    atts.add(new Attribute("mx"+(i+1)));
                    atts.add(new Attribute("my"+(i+1)));
                    atts.add(new Attribute("mz"+(i+1)));
                }
                attValso.add("pos");
                attValso.add("neg");
                for(int i=0;i<5;i++)
                {
                    atts.add(new Attribute("coeffx"+(i+1),attValso));
                    atts.add(new Attribute("coeffy"+(i+1),attValso));
                    atts.add(new Attribute("coeffz"+(i+1),attValso));
                }
                for (MovType movType : MovType.values()) {
                    attVals.add(movType.getMovType());
                }
                attVals.remove(attVals.size()-1);
                atts.add(new Attribute("class", attVals));
                data = new Instances("Mouvement", atts, 0);
                for(int i=0;i<nbMov;i++)
                {
                    vals = new double[data.numAttributes()];
                    double moyenne[]=csvMov.get(i).getMoySection(5);
                    String coeffs[]=csvMov.get(i).getCoefSection(5);
                    System.arraycopy(moyenne,0,vals,0,moyenne.length);
                    int k=0;
                    for(int j=moyenne.length;j<vals.length-1;j++)
                    {
                        vals[j]=attValso.indexOf(coeffs[k]);
                        k++;
                    }
                    vals[vals.length-1]=attVals.indexOf(csvMov.get(i).getMovType().getMovType());
                    data.add(new DenseInstance(1.0, vals));
                }
                break;
            default:
        }
        pw.print(data);
        pw.close();
    }

}
