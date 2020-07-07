package hust.mso.ga;

import java.util.ArrayList;
import java.util.Random;

public class Parameter {
    public final static int POPULATION_SIZE = 100;
    public final static int PATIENCE_GENERATION = 1000;
    public static int FES = 0; // current number of function evalutions
    public static int Max_FES = 100000; // Maximum number of evaluations to perform

    public static int GENE_SIZE;

    public static double pc = 0.7;
    public static double pm = 0.1;

    public static Random rand;
    public static int seed = 0;

    public static int INF = (int) 1e8;

    public static final int UPPER_BOUND = 1;
    public static final int LOWER_BOUND = 0;

    public static double runtime = 0;
    public static ArrayList<Double> gen_best; // for each generation, using this to save the best objectives

    public static Individual best_ind;
    public static double best_fitness = INF;

    public static void init(int seed, double pc, double pm) {
        set_seed(seed);
        Parameter.pc = pc;
        Parameter.pm = pm;
    }

    public static void set_seed(int _seed) {
        gen_best = new ArrayList<>();
        seed = _seed;
        rand = new Random(seed);
    }

    public static void save_generation(int gen) {
        String str = String.format("%d: %.0f", gen, best_ind.fitness);
        // System.out.println(str);
        gen_best.add(gen, best_ind.fitness);
    }
}
