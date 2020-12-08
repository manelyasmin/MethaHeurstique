package sample;

import java.util.*;

import java.util.ArrayList;

public class GA extends SATSolution {


    private SATInstance instance;
    private long beginTime, endTime, totalTime;
    private long RateMutation=20 , RateCroisement=60 ;
    private int MaxIter=100;
    private int sizePop =100;
public GA(SATInstance instance,int sizePop,long RateMutation,long RateCroisement){
    this.instance=instance;
    this.sizePop=sizePop;
    this.RateCroisement=RateCroisement;
    this.RateMutation=RateMutation;
}

    public long getRateMutation() {
        return RateMutation;
    }

    public long getRateCroisement() {
        return RateCroisement;
    }

    public void setRateMutation(long rateMutation) {
        this.RateMutation = rateMutation;
    }

    public void setRateCroisement(long rateCroisement) {
       this .RateCroisement = rateCroisement;
    }

    public void setSizePop(int sizePop) {
        this.sizePop = sizePop;
    }

    public GA(SATInstance instance) {

        this.instance=instance;
    }

    ArrayList<SATSolution> genratePopulationSolution(int sizeOfPopultion ) {
        SATSolution solution = new SATSolution(instance);
        ArrayList<SATSolution> randomGen = new ArrayList<>();

        for (int i = 0; i < sizeOfPopultion; i++) {
            randomGen.add(solution.generateRandomSolution(instance));
        }
        return randomGen;


    }
public ArrayList<Integer> fitness(ArrayList<SATSolution> solutions,SATInstance instance ) {
        ArrayList<Integer> results = new ArrayList<>();
        int i = 0;
        while (i < solutions.size()) {
            int j = instance.getNumberOfClausesSatisfied(solutions.get(i));

            results.add(j);
            i++;
        }
        return results;
    }
    public ArrayList<SATSolution> selection(ArrayList<SATSolution> solutions, ArrayList<Integer> results, int number) {//normally we chosse number equals to two to compare two parents
        ArrayList<SATSolution> newGen = new ArrayList<>();
        //int max, maxi;
       Collections.sort(results);
        Collections.reverse(results);
        for (int j = 0; j < number; j++) {
            for (int i = 0; i < solutions.size(); i++) {

                if (this.instance.getNumberOfClausesSatisfied(solutions.get(i)) == results.get(j)) {
                    newGen.add(solutions.get(i));
                }
            }
        }


       /*  for (int i = 0; i < number; i++) {
            max = 0;
            maxi = -1;
            for (int j = 0; j < results.size(); j++) {
                if (results.get(j) > max) {
                    maxi = j;
                    max = results.get(j);
                }
            }
            if (maxi != -1) {
                results.remove(maxi);
                newGen.add(solutions.get(maxi));
            }
        }*/
        return newGen;
    }

    public ArrayList<SATSolution> mutation(ArrayList<SATSolution> population ) {// With a mutation probability mutate new offspring at each locus (position in chromosome).
        int lower = 0, upper = this.instance.getNumberOfVariables();
        int rand = (int) (Math.random() * (upper - lower)) + lower;
        int individu = (int) (Math.random() * ((population.size() - 1) - 0)) + 0;



        if (population.get(individu).get(rand) == false) {
            //that mean is negative
 population.get(individu).flip(rand);
        }else{
             population.get(individu).flip(rand);

        }
        return population;
    }

    public ArrayList<SATSolution> croisement(ArrayList<SATSolution> population ) { //With a crossover probability cross over the
        //// parents to form a new offspring (children). If no crossover was performed, offspring is an exact copy of parent
        int lower = 0, upper = instance.getNumberOfVariables();
        int rand = (int) (Math.random() * (upper - lower)) + lower;
        int individu = (int) (Math.random() * ((population.size() - 1) - 0)) + 0;

        SATSolution solution = new SATSolution(this.instance);

        for (int i = 0; i < rand; i++) {
            //1100 //1010  1110 1000
                solution.setIndexValue(i, population.get(individu).flip(i).get(i));
                }

        for (int i = rand; i < instance.getNumberOfVariables(); i++) {
            solution.setIndexValue(i, population.get(individu).flip(i).get(i));

        }
        population.add(solution);

        return population;
    }





    @Override
    public void BeginTime() {
        this.beginTime = System.currentTimeMillis();
    }

    @Override
    public long TotalTime() {
        this.totalTime = this.endTime - this.beginTime;
    return this.totalTime;
    }

    @Override
    public void EndTime() {
        this.endTime =System.currentTimeMillis();

    }

    public SATInstance getInstance() {
        return instance;
    }

    public int getMaxIter() {
        return MaxIter;
    }

    @Override
    public double getEvaluation() {
        return super.getEvaluation();
    }

    public int getSizePop() {
        return sizePop;
    }


    public SATSolution Gen(int timing ) {

        this.BeginTime();
        boolean Found = false;
        int meanStagn = 0;
        ArrayList<SATSolution> population = this.genratePopulationSolution(this.sizePop );
        ArrayList<Integer> results ;

        int countStagn = 0;

        int best = 0;
        long check = 0;
        int countIter = 0;
        while (!Found && ((timing != 0 && check < timing) || timing == 0) && (this.MaxIter == 0 || (this.MaxIter != 0 && countIter < this.MaxIter)))
        {

            if (countStagn > 4)
            {
                ArrayList<SATSolution> individus = this.genratePopulationSolution(1 );
                individus.add(population.get(0));
                individus = this.croisement(individus );
                individus.add(population.get(1));
                ArrayList<Integer> resultsNew ;
                resultsNew = this.fitness(individus ,instance);
                population = this.selection(individus, resultsNew, 2 );
            }

            int compteur = 0;
            while (compteur < this.RateCroisement) {
                population = this.croisement(population );
                compteur++;
            }


            compteur = 0;
            if (compteur < this.RateMutation) {
                population = this.mutation(population );
                compteur++;
            }


            results = this.fitness(population,instance );
            for (int d = 0; d < results.size(); d++) {
                if (results.get(d) == instance.getNumberOfClauses()) {
                    Found = true; //found be true if we have a soultion satisifed all 325 clauses
                }
                if (results.get(d) > best) { //if we found better than best ,the new became our best
                    best = results.get(d);
                }
            }

            int oldStagn = meanStagn; // on sauvegarde l'ancienne moyenne de nombre de clauses satisfaites
            meanStagn = 0;
            int j;
            for (j = 0; j < results.size(); j++) {
                meanStagn += results.get(j);     // On calcule la nouvelle moyenne
            }
            meanStagn = meanStagn / results.size();
         /*
           Si la difference des moyennes de clauses satisfaites entre les deux iterations dépasse un
          certain seuil on incrémente le compteur d'iteration de stagnation, SINON on le remet à 0
          */
            if (oldStagn != 0 && Math.abs((double) oldStagn - meanStagn) > 4) {
                countStagn += 1;
            } else {
                countStagn = 0;
            }

            // Effectuer une selection des 2 meilleures individus de la population actuelle
            population = this.selection(population, results,2 );  //

            check = ((System.currentTimeMillis()-this.beginTime) / (1000))  ;
            System.out.println("ga "+countIter);
            // Variable pour controler le temps
            countIter++;

        }

        this.EndTime();
   //     System.out.println("CHECK : " + check+"couniter "+countIter+"\t"+population.size());
//System.out.println(this.instance.getNumberOfClausesSatisfied(population.get(1)));
     return population.get(0);
    }


}