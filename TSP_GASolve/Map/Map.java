package TSP_GASolve.Map;

import java.io.BufferedReader;
import java.io.FileReader;


public class Map {
    private int elements;
    private Locations[] theMap;
    public Map (String fileName) {
        genMap(fileName);
    }

 public int getElements () {
     return elements;
    }

//Adds locations and checks to see whether its in the arrays bounds, maps locations to a number-1
private void addLocation (Locations c) {
    int locationNum = c.getLocationNum();
    if (locationNum > elements) { 
        throw new LocationNumOutOfBounds();
    } else {
        theMap[locationNum-1] = c; 
    }
}

//Returns the euclidean distance between two locations, returns the coordinates
public Locations getLocation (int locationNum) {
    return theMap[locationNum-1];
}

public double getDistance (int c1, int c2) {
    return Math.sqrt(Math.pow(theMap[c2-1].getX()-theMap[c1-1].getX(),2)+Math.pow(theMap[c2-1].getY()-theMap[c1-1].getY(), 2));
}

private class LocationNumOutOfBounds extends RuntimeException {
    LocationNumOutOfBounds ( ) {
        super("Location Number out of array bounds.");
    }
}
//This method reads the .tsp.txt file and parses through it, catches an exception if not in the correct format.
 private void genMap (String fileName) {
     String line;
     int ni, xi, yi;
     int cityNum;
     double x, y;
     try {
         FileReader fr = new FileReader(fileName);
         BufferedReader br = new BufferedReader(fr);
         br.readLine();
         br.readLine();
         br.readLine();
         line = br.readLine();
         elements = Integer.parseInt(line.substring(11, line.length()));
         br.readLine();
         br.readLine();
         theMap = new Locations[elements];
         while((line = br.readLine()) != null) {
             if (line.equals("EOF")) {
                 break;
                }
                ni = 0;
                while (line.charAt(ni) != ' '){
                    ni++;
                }
                xi = ni + 1;
                while (line.charAt(xi) != ' '){
                    xi++;
                }
                yi = xi + 1;
                while (yi != line.length()){
                    yi++;
                }
                cityNum = Integer.parseInt(line.substring(0, ni));
                x = Double.parseDouble(line.substring(ni + 1, xi));
                y = Double.parseDouble(line.substring(xi+1, yi));
                addLocation(new Locations(cityNum, x, y));
            }
            br.close();
        } catch(Exception e) {
            System.out.println("Invalid problem source file.");
            e.printStackTrace();
        }
    }
    
   
}
