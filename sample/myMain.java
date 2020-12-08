package sample;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static sample.parameters.rootPath;


public class myMain {
    public static void main(String[] args) throws Exception {
        SATInstance instance = SATInstance.loadClausesFromDimacsFile("UF75.325.100/uf75-01.cnf");
SATSolution solution=SATSolution.generateRandomSolution(instance);
        GA genetic = new GA(instance); SATSolution sol = genetic.Gen(600);
        //PsoSat p = new PsoSat(instance);

        // SATSolution sol = p.PsoSat(100, 60, instance, 0.4  , 1.5, 0.8);
        //System.out.println(instance.getNumberOfClausesSatisfied(sol));
    }
}
/*        static String rootPath = "statistics/";

        static class parameters {
            //  int flip = 5;
            // int maxIterations = 100;
            // int numberOfBees = 20;
            // int maxChances = 6;
            // int maxLocalSearch = 30;
            String path;
            double c1;
            double c2;
            double weight;
            int population;
            int maxIter;
        public parameters(double c1, double c2, double weight, int population, int maxIter) {
                this.c1 = c1;
                this.c2 = c2;
                this.weight = weight;
                this.population = population;
                this.maxIter = maxIter;
                path = rootPath + "param" + c1 + "." + c2 + "." + weight + "." + population + "." + maxIter;
            }

            void createDirectory() {
                new File(path).mkdirs();
            }
        }

        static class Result {
            double averageSat;
            double averageRate;
            double averageTime;

            public Result(double averageSat, double averageRate, double averageTime) {
                this.averageSat = averageSat;
                this.averageRate = averageRate;
                this.averageTime = averageTime;
            }
        }

        public static void main (String[]args) throws Exception {


                new File(rootPath).mkdirs();
            int numberOfInstances = 1;
            int tryPerInstance = 10;
            PrintWriter writer = new PrintWriter(rootPath + "stats.csv");
            writer.write("number of instances per parameter;" + numberOfInstances + "\n");
            writer.write("try per instance;" + tryPerInstance + "\n\n\n");
            //   writer.write("maxIterations;flip;number of bees;maxChances;local searches;average sat;average rate;average time (s)\n");
            writer.write("maxIterations;c1;c2;weight;population;average sat;average rate;average time (s)\n");
for(int i=100;i<=1000;i+=100) {
    parameters params = new parameters(0.4, 1.5, 0.8, 60, i);
    Result res = createCSV(params, numberOfInstances, tryPerInstance);
    writer.write(0.6 + ";" + 2.0 + ";" + 0.1 + ";" + 60 + ";" + 10
            + ";" + res.averageSat + ";" + res.averageRate + ";" + res.averageTime + "\n");
}
            writer.close();
        }
        static Result createCSV (parameters params,int numberOfInstances, int tryPerInstance) throws Exception {
            double c1 = params.c1;
            double c2 = params.c2;
            double weight = params.weight;
            int sizepopulation = params.population;
            int maxIteration = params.maxIter;
            double avgSat = 0;
            double avgRate = 0;
            double avgTime = 0;
            params.createDirectory();
            String path = params.path;
            int numberOfFiles =1;
            PrintWriter total = new PrintWriter(path + "allDetails.csv");
            total.write("try per instance;" + tryPerInstance + "\n\n");
            total.write("maxIterations;flip;number of bees;maxChances;local searches\n");
            total.write(c1 + ";" + c2 + ";" + weight + ";" + sizepopulation + ";" + maxIteration + "\n\n\n");
            total.write(";;instance;clauses satisfied;rate;time\n");
            for (int j = 0; j < numberOfFiles; j++) {
                String fileName = "uf75-0" + (j + 1) + ".cnf";
                String file = "uf75-0" + (j + 1);
                PrintWriter writer = new PrintWriter(path + "/" + file + "details.csv");

                SATInstance instance = SATInstance.loadClausesFromDimacsFile("UF75.325.100/" + fileName);

                writer.write("instance;maxIterations;flip;number of bees;maxChances;local searches\n");
                writer.write(file + ";" + c1 + ";" + c2 + ";" + weight + ";" + sizepopulation + ";" + maxIteration + "\n\n\n");
                writer.write(";;attempt;clauses satisfied;rate;time\n");
                double sumSat = 0;
                double sumRate = 0;
                double sumTime = 0;
                int numberOfAttempts = tryPerInstance;
                PsoSat p = new PsoSat(instance);
                for (int i = 1; i <= numberOfAttempts; i++) {
                    SATSolution start = SATSolution.generateRandomSolution(instance);
                    long currentTime = System.currentTimeMillis();

                    SATSolution sol = p.PsoSat(maxIteration, sizepopulation, instance, c1, c2, weight);

                    long exeTime = System.currentTimeMillis() - currentTime;
                    double t = (double) exeTime / 1000;
                    int sat = instance.getNumberOfClausesSatisfied(sol);
                    double rate = (double) sat / instance.getNumberOfClauses();
                    writer.write(";;" + i + ";" + sat + ";" + rate + ";" + t + "\n");
                    sumRate += rate;
                    sumSat += sat;
                    sumTime += t;
                }
                sumRate /= numberOfAttempts;
                sumSat /= numberOfAttempts;
                sumTime /= numberOfAttempts;
                writer.write(";;average;" + sumSat + ";" + sumRate + ";" + sumTime + "\n");
                writer.close();
                total.write(";;" + file + ";" + sumSat + ";" + sumRate + ";" + sumTime + "\n");
                avgSat += sumSat;
                avgRate += sumRate;
                avgTime += sumTime;
            }
            avgSat /= numberOfFiles;
            avgRate /= numberOfFiles;
            avgTime /= numberOfFiles;
            total.close();
            return new Result(avgSat, avgRate, avgTime);
        }
    }
*/

