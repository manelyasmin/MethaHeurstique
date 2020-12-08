package sample;

import java.util.ArrayList;

import java.util.Random;

import static java.lang.System.*;

public class PsoSat extends SATSolution {
    private double c1, c2, weight;
    private int sizepop ;
    private int maxI=1000;
    private SATInstance instance;
    private long beginTime, endTime, totalTime;
PsoSat(SATInstance instance){
    this.instance=instance;
}
public void BeginTime() {

    this.beginTime = currentTimeMillis();
    }

    public void setC2(double c2) {
        this.c2 = c2;
    }

    public void setC1(double c1) {
        this.c1 = c1;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public long TotalTime() {
    this.totalTime = this.endTime - this.beginTime;
        return this.totalTime;
    }

public void EndTime() {
        this.endTime = currentTimeMillis();

    }
 
int fitness(Particle p) {

    return this.instance.getNumberOfClausesSatisfied(p.getActualPos());
    }

SATSolution PsoSat(int maxI, int sizeof,SATInstance instance,double c1,double c2, double weight ) {

       this.sizepop = sizeof;
       //,double c1,double c2,double weight
       boolean Found = false;
       int Gbest = 0;
       long check = 0;
       int evaluation  ;
       int countIter=0;

       ArrayList<Particle> population = new ArrayList<>();
       this.BeginTime();
       SATSolution globalBest =new SATSolution(instance);
           for (int i = 0; i < sizeof; i++) {
               Particle p = new Particle( c1, c2, weight, instance);

               evaluation = fitness(p);
                p.updateScore(evaluation);
               if (evaluation > Gbest) {
              Gbest = evaluation;
                   globalBest = p.getActualPos().copy();
                }



                   population.add(p);
               }

while(!Found && (maxI!=0 && maxI>countIter)){
  //  while (!Found && ((timing != 0 && check < timing) || timing == 0) && (this.maxI == 0 || (this.maxI != 0 && countIter < this.maxI))) {

        for (int i = 0; i <population.size(); i++) {

            int vel = population.get(i).updateVelocity(globalBest);
        //    System.out.println("vel "+vel+"weight "+population.get(i).getWeight());
    SATSolution soltionsat=   population.get(i).updatePosition(vel, population.get(i).getActualPos().copy(),instance);

population.get(i).setActualPos(soltionsat);
          evaluation = fitness(population.get(i));
            population.get(i).updateScore(evaluation);
            if (evaluation > Gbest) {
   Gbest = evaluation;
                globalBest = population.get(i).getActualPos().copy();


            }
        }

           if(this.instance.getNumberOfClausesSatisfied(globalBest)==this.instance.getNumberOfClauses()){Found=true;}


    countIter++;
           check = (long ) ((System.currentTimeMillis() - this.beginTime) * 0.001);
  }
System.out.println(check +"in seconde ");
       this.EndTime();

return globalBest;


    }
}

