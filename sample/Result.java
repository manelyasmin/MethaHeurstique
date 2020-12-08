package sample;


import javafx.beans.property.SimpleDoubleProperty;

import javafx.beans.property.SimpleStringProperty;

import java.io.PrintWriter;

public class Result {
  private SimpleDoubleProperty averageSat;
  private   SimpleDoubleProperty averageRate;
    private SimpleDoubleProperty averageTime;
    private SimpleStringProperty instance;

    public String getInstance() {
        return instance.get();
    //return  this.instance.getName();
    }
    public String getInstanceName(){return this.instance.getName();}


    public double getAverageRate() {
        return averageRate.get();
    }

    public double getAverageSat() {
        return averageSat.get();
    }

    public double getAverageTime() {
        return averageTime.get();
    }
    public Result(double averageSat, double averageRate, double averageTime ) {//nombre clause sat et rate et time

        this.averageSat = new SimpleDoubleProperty(averageSat);
        this.averageRate = new SimpleDoubleProperty(averageRate);
        this.averageTime = new SimpleDoubleProperty(averageTime);

    }
    public Result(double averageSat, double averageRate, double averageTime,String instance) {//nombre clause sat et rate et time

        this.averageSat = new SimpleDoubleProperty(averageSat);
        this.averageRate = new SimpleDoubleProperty(averageRate);
        this.averageTime = new SimpleDoubleProperty(averageTime);
        this.instance=new SimpleStringProperty(instance);
    }
    static Result createCSV(parameters params, int numberOfInstances, long tryPerInstance) throws Exception {

        int maxIterations = params.getMaxIter();

        int maxChances = params.getSizePop();
        long maxLocalSearch = params.getRateCroisement();
        long max=params.getRateMutation();
        double avgSat = 0;
        double avgRate = 0;
        double avgTime = 0;
        params.createDirectory();
        String path = params.path;
        int numberOfFiles = numberOfInstances;
       PrintWriter total = new PrintWriter(path + "/" + "allFiles.csv");
        total.write("try by  instance :" + tryPerInstance + "\n\n");
        total.write("max Iterations | size population | rate croissement | rate mutation |\n");
        total.write(maxIterations +   "|"   + maxChances + "|" + maxLocalSearch +"|" + max +"\n\n\n");
        total.write("instance       |clauses satisfied|     rate          | time \n");
        for (int j = 0; j <numberOfInstances; j++) {
            String fileName = "uuf75-0" + (j + 1) + ".cnf";
            String file = "uuf75-0" + (j + 1);
            PrintWriter writer = new PrintWriter(path + "/" + fileName + "details.csv");
            SATInstance instance =  SATInstance.loadClausesFromDimacsFile("UUF75.325.100/" +fileName);
            writer.write("file |   size of population |  Rate croissever |   Rate mutation \n");
            writer.write(fileName  + maxIterations + "||"   + maxChances + "||" + maxLocalSearch + "\n\n\n");
            writer.write("| attempt|  clauses satisfied  |     rate | time\n");
            double sumSat = 0;
            double sumRate = 0;
            double sumTime = 0;
            int numberOfAttempts = (int)tryPerInstance;
            for (int i = 1; i <= numberOfAttempts; i++) {

                long currentTime = System.currentTimeMillis();
                GA genetic=new GA(instance);
                genetic.setSizePop(100);
                genetic.setRateMutation(20);
                genetic.setRateCroisement(60);
                SATSolution sol = genetic.Gen(600);
                long exeTime = System.currentTimeMillis() - currentTime;
                double t = (double) exeTime / 1000;
                int sat = instance.getNumberOfClausesSatisfied(sol);
                double rate = (double) sat / instance.getNumberOfClauses(); //pourcentage clause satsified par rapport au total clause
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

            total.write(";;" + fileName + ";" + sumSat + ";" + sumRate + ";" + sumTime + "\n");
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