package modele;

import java.util.ArrayList;

public class Move {

    private ArrayList<PreDataVector> vectorMov;
    private MoveType moveType;

    public Move(ArrayList<PreDataVector> vectorMov,String moveType)
    {
        this.vectorMov=vectorMov;
        this.moveType =MoveType.getMovType(moveType);
    }
    public Move(ArrayList<PreDataVector> vectorMov, int begin, int end, String moveType)
    {
        this.vectorMov=new ArrayList<>();
        for(int i=begin;i<end;i++)
        {
            this.vectorMov.add(vectorMov.get(i));
        }
        this.moveType =MoveType.getMovType(moveType);
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
                moyenneX+=vectorMov.get(j+i*vectorPerSection).getX();
                moyenneY+=vectorMov.get(j+i*vectorPerSection).getY();
                moyenneZ+=vectorMov.get(j+i*vectorPerSection).getZ();
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
    public String[] getCoefSection(int nbSection)
    {
        int nbVector=vectorMov.size();
        int vectorPerSection=nbVector/nbSection;
        String coeffs[]=new String[3*nbSection];
        int begin=0;
        for(int i=0;i<nbSection;i++)
        {
            double coeffX=0;
            double coeffY=0;
            double coeffZ=0;
            coeffX=vectorMov.get(begin).getX()-vectorMov.get(((i+1)*vectorPerSection)-1).getX();
            coeffY=vectorMov.get(begin).getX()-vectorMov.get(((i+1)*vectorPerSection)-1).getX();
            coeffZ=vectorMov.get(begin).getX()-vectorMov.get(((i+1)*vectorPerSection)-1).getX();
            begin+=vectorPerSection;
            coeffs[3*i]=coeffX>0?"pos":"neg";
            coeffs[1+3*i]=coeffY>0?"pos":"neg";
            coeffs[2+3*i]=coeffZ>0?"pos":"neg";
        }
        return coeffs;
    }
    public ArrayList<PreDataVector> getVectorMov() {
        return vectorMov;
    }

    public void setVectorMov(ArrayList<PreDataVector> vectorMov) {
        this.vectorMov = vectorMov;
    }

    public MoveType getMoveType() {
        return moveType;
    }
}
