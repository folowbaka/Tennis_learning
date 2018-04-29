package modele;

import java.util.ArrayList;

public class Movement {

    private ArrayList<PreDataVector> vectorMov;

    public Movement(ArrayList<PreDataVector> vectorMov,int begin,int end)
    {
        this.vectorMov=new ArrayList<>();
        for(int i=begin;i<end;i++)
        {
            this.vectorMov.add(vectorMov.get(i));
        }
    }

    public ArrayList<PreDataVector> getVectorMov() {
        return vectorMov;
    }

    public void setVectorMov(ArrayList<PreDataVector> vectorMov) {
        this.vectorMov = vectorMov;
    }
}
