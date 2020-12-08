package sample;

import java.io.File;

public class parameters {
    static String rootPath = "statistics/";
 private   long RateMutation  ;
    private long RateCroisement ;
   private  int MaxIter  ;
   private  int sizePop  ;
   private double c1,c2,weight;
    String path;

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setC1(double c1) {
        this.c1 = c1;
    }

    public void setC2(double c2) {
        this.c2 = c2;
    }

    public double getC1() {
        return c1;
    }

    public double getC2() {
        return c2;
    }

    public double getWeight() {
        return weight;
    }

    public void setSizePop(int sizePop) {
        this.sizePop = sizePop;
    }

    public void setRateCroisement(long rateCroisement) {
        RateCroisement = rateCroisement;
    }

    public void setRateMutation(long rateMutation) {
        RateMutation = rateMutation;
    }

    public void setMaxIter(int maxIter) {
        MaxIter = maxIter;
    }

    public int getSizePop() {
        return sizePop;
    }

    public int getMaxIter() {
        return MaxIter;
    }

    public long getRateCroisement() {
        return RateCroisement;
    }

    public long getRateMutation() {
        return RateMutation;
    }

    public parameters(int MaxIter, int sizePop, long RateMutation, long RateCroisement) {
        this.MaxIter=MaxIter;
        this.sizePop=sizePop;
        this.RateCroisement=RateCroisement;
        this.RateMutation=RateMutation;
        path=rootPath+"parmas" + sizePop+"_"+MaxIter+"_"+RateCroisement+"_"+RateMutation;
    }

public parameters (double c1,double c2,double weight){
        this.c1=c1;
        this.c2=c2;
        this.weight=weight;
        path=rootPath+"params"+sizePop+"_"+c1+"_"+c2+"_"+weight;
}
    void createDirectory() {
        new File(path).mkdirs();
    }
}