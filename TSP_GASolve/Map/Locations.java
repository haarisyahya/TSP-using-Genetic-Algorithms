package TSP_GASolve.Map;

//Initializes the locations and their numbers
public class Locations {
    private int locationNum;
    private double x, y;
    public Locations (int locationNum, double x, double y) {
        this.locationNum = locationNum;
        this.x = x;
        this.y = y;
    }
    public int getLocationNum ( ) {return locationNum;}
    public double getX ( ) {return x;}
    public double getY ( ) {return y;}
}
   
