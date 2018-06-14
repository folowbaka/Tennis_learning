package modele;
/**
 * Classe representant une ligne de données
 */
public class PreDataVector {
    /**
     * Donnée sur l'axe x
     */
    private double x;
    /**
     * Donnée sur l'axe y
     */
    private double y;
    /**
     * Donnée sur l'axe z
     */
    private double z;
    /**
     * Entier représentant le capteur utilisé : 1 pour l'accéléromètre  et 4 pour le gyroscope
     */
    private int capteur;
    /**
     * Le temps du relevé par le capteur.
     */
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
