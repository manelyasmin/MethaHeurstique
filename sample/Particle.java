package sample;

import java.util.ArrayList;
import java.util.Random;

public class Particle {
    ArrayList<SATSolution> positionParticleListe;
    private  double velocity;
    private double c1,c2;
    private double weight ;
    private SATSolution actualPos,Gbest;
    private int bestScore=0,actualScore=0;

    public void setC1(double c1) {
        this.c1 = c1;
    }

    public void setC2(double c2) {
        this.c2 = c2;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setActualPos(SATSolution actualPos) {
        this.actualPos = actualPos.copy();
    }

    public void  updateScore(int score){
     if(score>this.bestScore) {
         this.bestScore = score;
         this.Gbest = this.actualPos.copy();}
         this.actualScore = score;

    }
    SATSolution getActualPos(){

        return actualPos;
 }

    Particle(double c1,double c2,double weight,SATInstance instance){
        this.c1=c1;
        this.c2=c2;
        this.weight=weight;
        this.velocity=(int) (Math.random() * (75 - 0)) + 0;
positionParticleListe=new ArrayList<>();
this.actualPos=SATSolution.generateRandomSolution(instance) ;
positionParticleListe=this.getCloseOne(this.actualPos);
        this.Gbest=this.actualPos.copy();
for(int i=0;i<positionParticleListe.size();i++){
     if(instance.getNumberOfClausesSatisfied(positionParticleListe.get(i))>instance.getNumberOfClausesSatisfied(this.Gbest)){
        this.Gbest=positionParticleListe.get(i).copy();
    }
}

    }

    public double getWeight() {
        return weight;
    }

    int  updateVelocity(SATSolution solution){

double r1= (Math.random() * (1 - 0)) + 0;
double r2=(Math.random() * (1 - 0)) + 0;
 //System.out.println(this.actualPos.distance(solution)+"\t"+this.Gbest.distance(solution));
        this.velocity=(int) this.weight*this.velocity+this.c1*r1*this.actualPos.distance(solution)+this.c2*r2*this.Gbest.distance(solution) ;

        return (int) this.velocity;
    }

SATSolution updatePosition(int flip,SATSolution solution,SATInstance instance){
    SATSolution sol=solution.copy();
    ArrayList<SATSolution > liste=new ArrayList<>();
//System.out.println(flip+"fmip");
    //    int flipafter=(int)(flip/120);
       // for(int i=0;i<flipafter;i++) {
for(int i=0;i<flip;i++){
            SATSolution solu = sol.copy().flip(i);

liste.add(solu);
        }
        for(int i=0;i<liste.size();i++){
            if(instance.getNumberOfClausesSatisfied(liste.get(i))>instance.getNumberOfClausesSatisfied(solution)){
                solution=liste.get(i).copy();
            }
        }
        this.actualPos=solution.copy();
return this.actualPos;
}
ArrayList<SATSolution> getCloseOne(SATSolution solution){

        ArrayList<SATSolution> liste=new ArrayList<>();
    SATSolution sol=solution.copy();
    for(int i=0;i<75;i++){
SATSolution solu=sol.copy().flip(i);
        liste.add(solu);

    }

    return liste;
    }
}
