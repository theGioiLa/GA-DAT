package hust.mso.ga.Operator;

import java.util.ArrayList;

import hust.mso.ga.Individual;
import hust.mso.ga.Parameter;

public class Initialization {
    public static ArrayList<Individual> random(int size) {
        ArrayList<Individual> individuals = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Individual ind = new Individual();
            ind.init_random();
            individuals.add(ind);
        }

        return individuals;
    }

    public static ArrayList<Individual> heuristic_rand(int size) {
        ArrayList<Individual> individuals = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Individual ind = new Individual();
            ind.init_heuristic_random();
            individuals.add(ind);
        }

        return individuals;

    }

    public static ArrayList<Individual> mixed_rand(int size) {
        ArrayList<Individual> individuals = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Individual ind = new Individual();
            if (Parameter.rand.nextDouble() < 0.6) {
                ind.init_heuristic_random();
            } else {
                ind.init_random();
            }
            individuals.add(ind);
        }

        return individuals;
    }
}
