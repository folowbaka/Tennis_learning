package modele;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PreData {

    private  static ArrayList<PreDataVector> csvData;
    private  static ArrayList<PreDataVector> csvDataAccel;
    private  static ArrayList<PreDataVector> csvDataGyro;
    private  static ArrayList<Move> csvMov=new ArrayList<>();
    private  static ArrayList<Move> csvMovAccel=new ArrayList<>();
    private  static ArrayList<Move> csvMovGyro=new ArrayList<>();
    private static  ArrayList<Move> csvMovAccelGyro=new ArrayList<>();
    public  static final int  ARFFKNN =1;
    public  static final int  TREE =2;
    public static void readCsv(String csvName)
    {
        try (Scanner scanner = new Scanner(new File(csvName)))
        {
            csvData=new ArrayList<>();
            csvDataAccel=new ArrayList<>();
            csvDataGyro=new ArrayList<>();
            scanner.next();
            while(scanner.hasNext())
            {
                String row=scanner.next();
                String[] rowData=row.split(";");
                int capteur=Integer.parseInt(rowData[0]);
                csvData.add(new PreDataVector(capteur,(int)Double.parseDouble(rowData[1]),Double.parseDouble(rowData[2]),Double.parseDouble(rowData[3]),Double.parseDouble(rowData[4])));
                if(capteur==1)
                {
                    csvDataAccel.add(new PreDataVector(capteur,(int)Double.parseDouble(rowData[1]),Double.parseDouble(rowData[2]),Double.parseDouble(rowData[3]),Double.parseDouble(rowData[4])));
                }
                else
                {
                    csvDataGyro.add(new PreDataVector(capteur,(int)Double.parseDouble(rowData[1]),Double.parseDouble(rowData[2]),Double.parseDouble(rowData[3]),Double.parseDouble(rowData[4])));
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public  static void detectMov(String movType, int detectNorm, double normEndMov, ArrayList<PreDataVector> vectors, ArrayList<Move> movs, boolean hasBothCaptor)
    {
        int nbVector=vectors.size();
        double[] vectorNorms=new double[nbVector];
        for(int i=0;i<nbVector;i++)
        {
            vectorNorms[i]=vectors.get(i).norm();
        }
        int numVector=0;

        while(numVector<nbVector)
        {
            if (vectorNorms[numVector] > detectNorm)
            {
                int peak=foundPeakMov(numVector,nbVector,vectorNorms,hasBothCaptor);
                System.out.println("PEAK "+peak);
                int beginInterval=peak;
                int endInterval=peak;
                boolean endMov1=false;
                boolean endMov2=false;
                while (beginInterval>0 && (!endMov1  || !endMov2))
                {
                    if(vectorNorms[beginInterval]<normEndMov)
                    {
                        if(hasBothCaptor && vectors.get(beginInterval).getCapteur()==4 && vectors.get(beginInterval+1).getCapteur()!=4)
                        {


                            if(endMov1)
                                endMov2=true;
                            else {
                                endMov1 = true;
                                beginInterval--;
                            }

                        }
                        else if(!hasBothCaptor)
                        {

                            if(endMov1)
                                endMov2=true;
                            else {
                                endMov1 = true;
                                beginInterval--;
                            }

                        }
                        else
                        {
                            beginInterval--;
                        }
                    }
                    else
                        beginInterval--;
                }
                if(beginInterval<0)
                    beginInterval=0;
                endMov1=false;
                endMov2=false;
                while (endInterval<nbVector && (!endMov1 ||!endMov2))
                {
                    if(vectorNorms[endInterval]<normEndMov)
                    {
                        if(hasBothCaptor && vectors.get(endInterval).getCapteur()==4 && (endInterval+1)!=nbVector && vectors.get(endInterval+1).getCapteur()!=4)
                        {

                                if(endMov1)
                                    endMov2=true;
                                else {
                                    endMov1 = true;
                                    endInterval++;
                                }


                        }
                        else if(!hasBothCaptor)
                        {


                            if(endMov1)
                                endMov2=true;
                            else {
                                endMov1 = true;
                                endInterval++;
                            }


                        }
                        else
                            endInterval++;
                    }
                    else
                        endInterval++;
                }
                numVector=endInterval;
                /*if(peak+50>nbVector)
                {
                    numVector = nbVector;
                }
                else
                {

                    numVector=peak+51;
                }
                endInterval=numVector;
                if(peak-50>0)
                    beginInterval=peak-50;*/
                movs.add(new Move(vectors,beginInterval,endInterval,movType));

            }
            else
            {
                numVector++;
            }
        }

    }

    public static int foundPeakMov(int numVector,int nbVector,double[] vectorNorms,boolean hasBothCaptor)
    {
        int peak=0;
        double maxNorm=0;
        int intervalle=50;
        if(!hasBothCaptor)
            intervalle/=2;
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

    public static void writeArff(File directory,int classifier,ArrayList<Move> moves,String nameFile)
    {

        File file = new File(directory.getAbsolutePath()+"/"+directory.getName()+classifier+nameFile+".arff");
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
        int nbMove=moves.size();
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
            for (int i = 0; i < nbMove; i++)
                attValso.add(("" + i));
            atts.add(new Attribute("bag-id", attValso));
            attsRel = new ArrayList<Attribute>();
            attsRel.add(new Attribute("x"));
            attsRel.add(new Attribute("y"));
            attsRel.add(new Attribute("z"));
            dataRel = new Instances("vectorXYZ", attsRel, 0);
            atts.add(new Attribute("bag", dataRel, 0));
            for (MoveType moveType : MoveType.values()) {
                attVals.add(moveType.getMovType());
            }
            attVals.remove(attVals.size()-1);
            atts.add(new Attribute("class", attVals));
            data = new Instances("Mouvement", atts, 0);
            for (int i = 0; i < nbMove; i++) {
                int nbInterval = moves.get(i).getVectorMov().size();

                // 2. create Instances object
                vals = new double[data.numAttributes()];
                dataRel = new Instances(data.attribute(1).relation(), 0);

                for (int j = 0; j < nbInterval; j++) {
                    valsRel = new double[3];
                    valsRel[0] = moves.get(i).getVectorMov().get(j).getX();
                    valsRel[1] = moves.get(i).getVectorMov().get(j).getY();
                    valsRel[2] = moves.get(i).getVectorMov().get(j).getZ();
                    dataRel.add(new DenseInstance(1.0, valsRel));
                }
                vals[0] = attValso.indexOf(("" + i));
                vals[1] = data.attribute(1).addRelation(dataRel);
                vals[2] = attVals.indexOf(moves.get(i).getMoveType().getMovType());
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
                for (MoveType moveType : MoveType.values()) {
                    attVals.add(moveType.getMovType());
                }
                attVals.remove(attVals.size()-1);
                atts.add(new Attribute("class", attVals));
                data = new Instances("Mouvement", atts, 0);
                for(int i=0;i<nbMove;i++)
                {
                    vals = new double[data.numAttributes()];
                    double moyenne[]=moves.get(i).getMoySection(5);
                    String coeffs[]=moves.get(i).getCoefSection(5);
                    System.arraycopy(moyenne,0,vals,0,moyenne.length);
                    int k=0;
                    for(int j=moyenne.length;j<vals.length-1;j++)
                    {
                        vals[j]=attValso.indexOf(coeffs[k]);
                        k++;
                    }
                    vals[vals.length-1]=attVals.indexOf(moves.get(i).getMoveType().getMovType());
                    data.add(new DenseInstance(1.0, vals));
                }
                break;
            default:
        }
        pw.print(data);
        pw.close();
    }

    public static ArrayList<Move> getCsvMov() {
        return csvMov;
    }

    public static ArrayList<PreDataVector> getCsvData() {
        return csvData;
    }

    public static void setCsvData(ArrayList<PreDataVector> csvData) {
        PreData.csvData = csvData;
    }

    public static ArrayList<PreDataVector> getCsvDataAccel() {
        return csvDataAccel;
    }

    public static void setCsvDataAccel(ArrayList<PreDataVector> csvDataAccel) {
        PreData.csvDataAccel = csvDataAccel;
    }

    public static ArrayList<PreDataVector> getCsvDataGyro() {
        return csvDataGyro;
    }

    public static void setCsvDataGyro(ArrayList<PreDataVector> csvDataGyro) {
        PreData.csvDataGyro = csvDataGyro;
    }

    public static void setCsvMov(ArrayList<Move> csvMov) {
        PreData.csvMov = csvMov;
    }

    public static ArrayList<Move> getCsvMovAccel() {
        return csvMovAccel;
    }

    public static void setCsvMovAccel(ArrayList<Move> csvMovAccel) {
        PreData.csvMovAccel = csvMovAccel;
    }

    public static ArrayList<Move> getCsvMovGyro() {
        return csvMovGyro;
    }

    public static void setCsvMovGyro(ArrayList<Move> csvMovGyro) {
        PreData.csvMovGyro = csvMovGyro;
    }

    public static ArrayList<Move> getCsvMovAccelGyro() {
        return csvMovAccelGyro;
    }

    public static void setCsvMovAccelGyro(ArrayList<Move> csvMovAccelGyro) {
        PreData.csvMovAccelGyro = csvMovAccelGyro;
    }
}
