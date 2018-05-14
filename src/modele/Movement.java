package modele;

import java.util.ArrayList;

public class Movement {

    private ArrayList<PreDataVector> vectorMov;
    private MovType movType;

    public Movement(ArrayList<PreDataVector> vectorMov,int begin,int end,String movType)
    {
        this.vectorMov=new ArrayList<>();
        for(int i=begin;i<end;i++)
        {
            this.vectorMov.add(vectorMov.get(i));
        }
        this.movType=MovType.getMovType(movType);
    }

    public double[] getMoySection(int nbSection)
    {
        int nbVector=vectorMov.size();
        int vectorPerSection=nbVector/nbSection;
        double moyennes[]=new double[3*nbSection];
        for(int i=0;i<nbSection;i++)
        {
            double moyenneX=0;
            double moyenneY=0;
            double moyenneZ=0;
            for(int j=0;j<vectorPerSection;j++)
            {
                moyenneX+=vectorMov.get(j).getX();
                moyenneY+=vectorMov.get(j).getY();
                moyenneZ+=vectorMov.get(j).getZ();
            }
            moyenneX=moyenneX/vectorPerSection;
            moyenneY=moyenneY/vectorPerSection;
            moyenneZ=moyenneZ/vectorPerSection;
            moyennes[3*i]=moyenneX;
            moyennes[1+3*i]=moyenneY;
            moyennes[2+3*i]=moyenneZ;
        }
        return moyennes;
    }

    public ArrayList<PreDataVector> getVectorMov() {
        return vectorMov;
    }

    public void setVectorMov(ArrayList<PreDataVector> vectorMov) {
        this.vectorMov = vectorMov;
    }

    public MovType getMovType() {
        return movType;
    }
}
