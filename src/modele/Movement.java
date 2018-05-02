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
