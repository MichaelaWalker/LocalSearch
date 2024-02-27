package optimization_solutions;

import core_algorithms.GeneticAlgorithm;
import core_algorithms.Individual;
import optimization_problems.TSP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm_TSP extends GeneticAlgorithm<Integer> {
    private final TSP problem;

    public GeneticAlgorithm_TSP(int maxGen, double mRate, double elitism, TSP problem){
        super(maxGen, mRate, elitism);
        this.problem = problem;
    }

    public double calcFitnessScore(List<Integer> chromosome) {
        return 1/problem.cost(chromosome);
    }

    public Individual<Integer> reproduce(
            Individual<Integer> p1, Individual<Integer> p2) {
        List<Integer> parent1Chromosome = p1.getChromosome();
        List<Integer> parent2Chromosome = p2.getChromosome();

        int size = parent1Chromosome.size();
        int startPos = new Random().nextInt(size);
        int endPos = new Random().nextInt(size);

        if (startPos > endPos) {
            int temp = startPos;
            startPos = endPos;
            endPos = temp;
        }

        List<Integer> childChromosome = new ArrayList<>();
        for (int i = startPos; i < endPos; i++) {
            childChromosome.add(parent1Chromosome.get(i));
        }

        for (int i = 0; i < size; i++) {
            int gene = parent2Chromosome.get(i);
            if (!childChromosome.contains(gene)) {
                childChromosome.add(gene);
            }
        }

        double fitnessScore = calcFitnessScore(childChromosome);
        return new Individual<>(childChromosome, fitnessScore);
    }

    public Individual<Integer> mutate(Individual<Integer> i){
        List<Integer> chromosome = new ArrayList<>(i.getChromosome());
        int size = chromosome.size();

        int pos1 = new Random().nextInt(size);
        int pos2 = new Random().nextInt(size);

        Collections.swap(chromosome, pos1, pos2);

        double fitnessScore = calcFitnessScore(chromosome);
        return new Individual<>(chromosome, fitnessScore);
    }

    public List<Individual<Integer>> generateInitPopulation(
            int popSize,int numCities){
        List<Individual<Integer>> population =
                new ArrayList<>(popSize);
        for(int i=0; i<popSize; i++){
            List<Integer> chromosome = new ArrayList<>(numCities);
            for(int j=0; j<numCities; j++){
                chromosome.add(j);
            }
            Collections.shuffle(chromosome);
            Individual<Integer> indiv = new Individual<>(
                    chromosome, calcFitnessScore(chromosome));
            population.add(indiv);
        }
        return population;
    }

    public static void main(String[] args) {
        int MAX_GEN = 200;
        double MUTATION_RATE = 0.05;
        int POPULATION_SIZE = 1000;
        int NUM_CITIES = 26; //choose from 5, 6, 17, 26
        double ELITISM = 0.2;

        TSP problem = new TSP(NUM_CITIES);

        GeneticAlgorithm_TSP agent = new GeneticAlgorithm_TSP(
                MAX_GEN, MUTATION_RATE, ELITISM, problem);

        Individual<Integer> best = agent.evolve(agent.generateInitPopulation(
                NUM_CITIES, POPULATION_SIZE));


        System.out.println(best);
        System.out.println(problem.cost(best.getChromosome()));
    }
}
