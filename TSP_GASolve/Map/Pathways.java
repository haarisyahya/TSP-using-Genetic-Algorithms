package TSP_GASolve.Map;

public class Pathways {
    private Map theMap;
    private Locations[] thePathway;

    public Pathways (Map theMap, int elements) {
        this.theMap = theMap;
        thePathway = new Locations[elements];
    }
    //method to add locations in the  Map
    public void addLocations (Locations c, int index) {
        thePathway[index] = c;
    }
    //returns the locations on a specific array index
    public Locations getLocations (int index) {
        return thePathway[index];
    }
    //returns the length of the path taken
    public double getPathwayLength() {
        double length = 0;
        for (int i = 0 ; i < thePathway.length-1 ; i++) {
            length = length + theMap.getDistance(thePathway[i].getLocationNum(),thePathway[i+1].getLocationNum());
        }
        length = length + theMap.getDistance(thePathway[thePathway.length-1].getLocationNum(), thePathway[0].getLocationNum());
        return length;
    }

    public boolean contains (Locations c) {
        for (Locations locations : thePathway) {
            if (locations != null && c.getLocationNum() == locations.getLocationNum()) {
                return true;
            }
        }
        return false;
    }
}
   
