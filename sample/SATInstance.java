package sample;

import  java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;


public class SATInstance
{
    private int numberOfClauses;
    private int numberOfVariables;
    protected BitSet[][] literalsBitSet;
    public BitSet[][] getLiteralsBitSet() {
        return literalsBitSet;
    }

    public SATInstance(int numberOfVariables, int numberOfClauses) {
        this.numberOfClauses = numberOfClauses;
        this.numberOfVariables = numberOfVariables;
        literalsBitSet = new BitSet[2][getNumberOfVariables()];
        for (int i = 0; i < getNumberOfVariables(); i++) {
            literalsBitSet[0][i] = new BitSet(getNumberOfClauses());
            System.out.println(literalsBitSet[0][i]);
            literalsBitSet[1][i] = new BitSet(getNumberOfClauses());
        }
    }

    public static SATInstance loadClausesFromDimacsFile(String pathToCnfFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(pathToCnfFile));
        String line = reader.readLine();
        while (line.charAt(0) != 'p') {
            line = reader.readLine();
        }

        String first[] = line.split("\\s+");
        SATInstance se = new SATInstance(Integer.parseInt(first[2]),Integer.parseInt(first[3]));

        int i = 0;
        while ((line = reader.readLine()) != null && i < se.getNumberOfClauses()) {
            if(line.length()>0 && line.charAt(0) == ' ')
                line = line.substring(1);

            String sLine[] = line.split("\\s+");
            for (int j = 0; j < sLine.length - 1; j++) {
                int i1 = Integer.parseInt(sLine[j]);
                int index = (i1 > 0)?1:0;
                se.literalsBitSet[index][Math.abs(i1)-1].set(i);
            }
            i++;
        }
        reader.close();
        return se;
    }

    public int getNumberOfClausesSatisfied(SATSolution solution)
    {
        BitSet satisfied = new BitSet(getNumberOfClauses());
        satisfied.clear();
        for (int i = 0; i < solution.length(); i++) {
            if(solution.get(i))
                satisfied.or(literalsBitSet[1][i]);
            else
                satisfied.or(literalsBitSet[0][i]);
        }
        return satisfied.cardinality();
    }

    public int getNumberOfClauses() {
        return numberOfClauses;
    }
    public int getNumberOfVariables() {
        return numberOfVariables;
    }

}
