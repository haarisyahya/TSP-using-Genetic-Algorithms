# TSP-using-Genetic-Algorithms
In this assignment and document, the Travelling Salesman Problem (TSP) is solved by implementing a Genetic Algorithm (GA) capable of executing any number of input parameter combinations such that the user is able to explore strategic mutation rates, crossover rates, tournament k-values, crossover types, population sizes, and number of generations.

This Java project was put into a jar file that can be
run by the following command that you can call in the main
class:
java -jar TSP GA.jar The program expects the input
parameters to be in the following way:
java -jar TSP GA.jar ”path/to/file.tsp.txt” MAX-GEN POPSIZE
K-VALUE MUTATION-RATE CROSSOVER-TYPE
CROSSOVER-RATE SEED
If they are passed incorrectly, there will be an error and
the program will not run. The description for each parameter
is below:
1. MAX-GEN: This is an integer parameter that signifies
the maximum generations that were run before the program
terminated.
2. POP-SIZE: This is another integer parameter that sets the
population size of the chromosomes for the GA.
3. K-VALUE: This is an integer parameter, the K-value used
for tournament selection.
4. MUTATION-RATE: A double parameter that signifies the
rate of the occurence of mutations in the chromosomes. The
range for this value is between 0.0 and 1.0 as we are using a
percentage value.
5. CROSSOVER-TYPE: An integer parameter that sets the
type of crossover. 0 for Uniform Order Crossover and 1 for
Order Crossover.
6. CROSSOVER-RATE: A double parameter that signifies
the rate of the occurence of crossovers.
7. SEED: This is a long parameter, a seed used for the
random number generator. This allows us to reproduce
executions so we can analyze them.
8. path/to/file.tsp.txt: The file’s location needs to be inputted
into the console before the other parameters can be inputted.
Most importantly, the files needs to be saved as a .txt file and
that has to be included, otherwise there will be an error.
