package core_algorithms;

import java.util.List;

public abstract class Individual<G> {

    private List<G> chromosome;

    private double fitnessScore;


    public Individual(List<G> chromosome, double fitnessScore){
        this.chromosome = chromosome;
        this.fitnessScore =
                calcFitnessScore(this.chromosome);
    }

    public abstract double calcFitnessScore(List<G> chromosome);

    public List<G> getChromosome() {
        return chromosome;
    }

    public double getFitnessScore() {
        return fitnessScore;
    }
}
