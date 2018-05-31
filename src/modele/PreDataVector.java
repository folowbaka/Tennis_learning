package modele;

public class PreDataVector {

    private double x;
    private double y;
    private double z;
    private int capteur;
    private int time;

    public  PreDataVector(int capteur,int time,double x,double y,double z)
    {
        this.capteur=capteur;
        this.time=time;
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public double norm()
    {
        return Math.sqrt(Math.pow(this.x,2)+Math.pow(this.y,2)+Math.pow(this.z,2));
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public int getCapteur() {
        return capteur;
    }
}
