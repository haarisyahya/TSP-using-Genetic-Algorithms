package TSP_GASolve;
import TSP_GASolve.Map.Locations;
import TSP_GASolve.Map.Map;
import TSP_GASolve.Map.Pathways;
import java.util.Random;
/**
* TSP_GASolve class is the class responsible for running the whole program. It contains the parameters that
* are filled by the user in the console. It performs the generations, mutations and crossovers.
*/
public class TSP_GASolve {
    int MAX_POP; 
    int POP_SIZE;
    int K_VALUE;
    double MUTATION_RATE;
    double CROSSOVER_RATE;
    int CROSSOVER_TYPE;
    long SEED;
    int elements; // number of locations in the pathways
    Map theMap;
    Random rand;

    public TSP_GASolve (Map theMap, int MAX_POP, int POP_SIZE, int K_VALUE, double MUTATION_RATE, int CROSSOVER_TYPE, double CROSSOVER_RATE, long SEED) {
        this.MAX_POP = MAX_POP;
        this.POP_SIZE = POP_SIZE; 
        this.K_VALUE = K_VALUE;
        this.MUTATION_RATE = MUTATION_RATE;
        this.CROSSOVER_TYPE = CROSSOVER_TYPE;
        this.CROSSOVER_RATE = CROSSOVER_RATE;
        this.SEED = SEED;
        rand = new Random(SEED);
        this.theMap = theMap;
        elements = theMap.getElements();
        doSolve(initPop(theMap));
    } //constructor

    //this method initiates the population randomly
    private Pathways[] initPop (Map theMap) { 
        Pathways[] population = new Pathways[POP_SIZE];
        int index;
        for (int i = 0 ; i < POP_SIZE ; i++) {
            population[i] = new Pathways(theMap, theMap.getElements());
            for (int j = 0 ; j < elements ; j++) { 
                index = (int)(1+rand.nextDouble()*elements);
                while (population[i].contains(theMap.getLocation(index))) { 
                    index = (int)(1+rand.nextDouble()*elements);
                }
                population[i].addLocations(theMap.getLocation(index), j);
        }
    }
    return population;
}


//This method retrieves the best city from the population 
private Pathways evalPop (Pathways[] population) { 
    Pathways tBestPath = new Pathways (theMap, elements);
    double tLength, tBestLength = -1;
    for (int i = 0 ; i < POP_SIZE ; i++) {
        if (tBestLength == -1) {
            tBestPath = population[i];
            tBestLength = population[i].getPathwayLength();
        } else {
            tLength = population[i].getPathwayLength();
            if (tLength < tBestLength) {
                tBestPath = population[i];
                tBestLength = tLength;
            }
        }
    }
    return tBestPath;
}


//perform the OX crossover
 private Pathways[] doOX (Pathways[] population) {
     Pathways[] newPop = new Pathways[POP_SIZE];
     int setSize = (int)(rand.nextDouble()*elements);
     int setStart = (int)(rand.nextDouble()*elements);
     int k;
     //for loop for every 2 chromosomes in the pop
     for (int i = 0 ; i < POP_SIZE ; i+=2) { 
        if (rand.nextDouble() < CROSSOVER_RATE) {
            newPop[i] = new Pathways(theMap, elements);
            newPop[i+1] = new Pathways(theMap, elements);
            for (int j = setStart ; j < setStart+setSize ; j++) {
                newPop[i].addLocations(population[i].getLocations(j%elements), j%elements); // copy from parent 1 to child 1
                newPop[i+1].addLocations(population[i+1].getLocations(j%elements), j%elements); // copy from parent 2 to child 2
            }
            k = setStart+setSize;
            for (int j = setStart+setSize ; j < setStart+setSize+elements ; j++) { 
                if (!newPop[i+1].contains(population[i].getLocations(j%elements)))
                { 
                    while (newPop[i+1].getLocations(k%elements) != null) {
                        k++;
                    }
                    newPop[i+1].addLocations(population[i].getLocations(j%elements), k%elements);
                    k++;
                }
            }
            k = setStart+setSize;
            for (int j = setStart+setSize ; j < setStart+setSize+elements ; j++) { 
                if (!newPop[i].contains(population[i+1].getLocations(j%elements)))
                { 
                    while (newPop[i].getLocations(k%elements) != null) {
                        k++;
                    }
                    newPop[i].addLocations(population[i+1].getLocations(j%elements), k%elements);
                    k++;
                }
            }
        } else {
            newPop[i] = population[i];
            newPop[i+1] = population[i+1];
        }
    }
    return newPop;
}

//peforms the UOX crossover
private Pathways[] doUOX (Pathways[] population) {
    Pathways[] newPop = new Pathways[POP_SIZE];
    int k;
    for (int i = 0 ; i < POP_SIZE ; i+=2) { 
       if (rand.nextDouble() < CROSSOVER_RATE) {
           newPop[i] = new Pathways(theMap, elements);
           newPop[i+1] = new Pathways(theMap, elements);
           for (int j = 0 ; j < elements ; j++) { 
               if ((int)(rand.nextDouble()*2) == 1) { // if random "mask" generates 1
               // copy city to new index for both ( 1 to 1, 2 to 2)
               newPop[i].addLocations(population[i].getLocations(j), j);
               newPop[i+1].addLocations(population[i+1].getLocations(j), j);
           }
       }
       k = 0;
       for (int j = 0 ; j < elements ; j++) { 
           if (!newPop[i+1].contains(population[i].getLocations(j))) { 
               while (k < elements && newPop[i+1].getLocations(k) != null)
               { // add to first available place
                   k++;
               }
               if (newPop[i+1].getLocations(k) == null) {
                   newPop[i+1].addLocations(population[i].getLocations(j), k);
               }
           }
       }
       k = 0;
       for (int j = 0 ; j < elements ; j++) { 
           if (!newPop[i].contains(population[i+1].getLocations(j))) { // if it doesnt exist in new path 1
               while (k < elements && newPop[i].getLocations(k) != null) { //n add to first available place
                   k++;
               }
               if (newPop[i].getLocations(k) == null) {
                   newPop[i].addLocations(population[i+1].getLocations(j), k);
               }
           }
       }
   } else {
       newPop[i] = population[i];
       newPop[i+1] = population[i+1];
   }
}
return newPop;
}

//responsible for performing the crossovers
private Pathways[] doCrossover (Pathways[] population, int CROSSOVER_TYPE) {
    if (CROSSOVER_TYPE == 0) {
        return doUOX(population);
    } else {

        return doOX(population);
    }
}
//does the tournament based on the k-value
private Pathways[] doTourny (Pathways[] population, int kValue) { // k-tournament
    Pathways[] tourny = new Pathways[kValue];
    Pathways[] newPop = new Pathways[POP_SIZE];
    Pathways tBestPath = new Pathways(theMap, elements);
    double tLength, tBestLength;
    for (int i = 0 ; i < POP_SIZE ; i++) { 
        newPop[i] = new Pathways(theMap, elements);
        for (int k = 0 ; k < kValue ; k++) { // lk is picked randomly here
            tourny[k] = population[(int)(rand.nextDouble()*POP_SIZE)];
        }
        tBestLength = -1;
        for (int k = 0 ; k < kValue ; k++) { 
            if (tBestLength == -1) { //this line says that if its the first its the best location
                tBestPath = tourny[k];
                tBestLength = tourny[k].getPathwayLength();
            } else { // otherwise do a comparison
                tLength = tourny[k].getPathwayLength();
                if (tLength < tBestLength) {
                    tBestPath = tourny[k];
                    tBestLength = tLength;
                }
            }
        }
        newPop[i] = tBestPath; // adds the best location to the new
    }
    return newPop;
}

    private void doSolve (Pathways[] population){ // main "program loop"
    Pathways bPath = new Pathways(theMap, elements);
    Pathways tBPath;
    double totalLength;
    System.out.println("Number of generations: " + MAX_POP + "\tPopulationSize: " + POP_SIZE + "\tk-Value: " + K_VALUE);
    System.out.print("Mutation Rate: " + MUTATION_RATE + "\t\tCrossover Type:");
    if (CROSSOVER_TYPE == 0) {
        System.out.print("UOX");
    } else {
        System.out.print("OX");
    }
    System.out.println("\tCrossover Rate: " + CROSSOVER_RATE);
    System.out.println("Random Number Seed: " + SEED + "\n");
    
    //generates the generations
    for (int cPop = 0 ; cPop < MAX_POP ; cPop++) { 
    totalLength = 0;
    if (cPop != 0) { // this line enforces elitism for the whole experiment
        for (int i = 0 ; i < elements ; i++) {
            if (population[i].getPathwayLength() == bPath.getPathwayLength()) {
                break;
            } else if (population[i].getPathwayLength() < bPath.getPathwayLength()) {
                population[i] = bPath;
                break;
            }
        }
    }
    tBPath = evalPop(population); 
    if (cPop == 0 || tBPath.getPathwayLength() < bPath.getPathwayLength()) {
        bPath = tBPath;
    }
    population = doMutation(doCrossover(doTourny(population, K_VALUE),CROSSOVER_TYPE)); 
    // print
    System.out.print(bPath.getPathwayLength() + "\t");
    for (int i = 0 ; i < POP_SIZE ; i++) {
        totalLength+=population[i].getPathwayLength();
    }
    System.out.println(totalLength/POP_SIZE);
}

System.out.println();
for (int i = 0 ; i < elements ; i++) {
    System.out.print(bPath.getLocations(i).getLocationNum() + " ");
}
System.out.println();
System.out.println(bPath.getPathwayLength());
}

//This method is responsible for performing the mutations, uses a 1D array
private Pathways[] doMutation (Pathways[] population) { 
    int i, j;
    Locations temp;
    for (int p = 0 ; p < POP_SIZE ; p++) { 
        if (rand.nextDouble() < MUTATION_RATE) {
            i = (int)(rand.nextDouble()*elements); // pick 2 random numbers
            j = i;
            while (j == i) { 
                j = (int)(rand.nextDouble()*elements);
            }
            temp = population[p].getLocations(i); // swap locations in i and j
            population[p].addLocations(population[p].getLocations(j), i);
            population[p].addLocations(temp, j);
        }
    }
    return population;
}


}

  