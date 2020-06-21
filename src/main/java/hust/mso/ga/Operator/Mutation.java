package hust.mso.ga.Operator;

import hust.mso.ga.Parameter;
import hust.mso.ga.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mutation {
    // polynomial params
    protected static final int eta_m = 20; // distribution index for mutation

    // gaussian params
    protected static double sigma = 0.4 / (Parameter.UPPER_BOUND - Parameter.LOWER_BOUND); // [0.25, 0.5]/(U-L)

    public static ArrayList<Double> execute(ArrayList<Double> parent_genes) {
        // return polynomial(parent_genes);
        // return gaussian(parent_genes);
        // return normal(parent_genes);
        return shuffle(parent_genes);
    }

    protected static ArrayList<Double> normal(ArrayList<Double> parent_genes) {
        ArrayList<Double> chromosome = new ArrayList<>();
        double rnd;
        double y;
        for (Double x : parent_genes) {
            rnd = Parameter.rand.nextGaussian(); // [-1, 1]
            y = x + rnd;
            if (Util.out_bound(y, Parameter.LOWER_BOUND, Parameter.UPPER_BOUND)) y = x;
            // y = Util.repair_sol_with_random_value(y, Parameter.LOWER_BOUND, Parameter.UPPER_BOUND);
            // System.out.println(y);
            chromosome.add(y);
        }

        return chromosome;
    }

    protected static ArrayList<Double> gaussian(ArrayList<Double> parent_genes) {
        ArrayList<Double> chromosome = new ArrayList<>();

        double u, uL, uR;
        double y;
        double u_common;
        for (Double x : parent_genes) {
            u = Parameter.rand.nextDouble();

            if (u <= 0.5) {
                double expr = Util.erf((Parameter.LOWER_BOUND - x)
                        / (Math.sqrt(2) * (Parameter.UPPER_BOUND - Parameter.LOWER_BOUND) * sigma)) + 1;
                uL = 0.5 * expr;
                // System.out.println("uL: " + uL);
                u_common = 2 * uL * (1 - 2 * u);
            } else {
                double expr = Util.erf((Parameter.UPPER_BOUND - x)
                        / (Math.sqrt(2) * (Parameter.UPPER_BOUND - Parameter.LOWER_BOUND) * sigma)) + 1;
                uR = 0.5 * expr;
                // System.out.println("uR: " + uR);
                u_common = 2 * uR * (2 * u - 1);
            }

            u_common = Util.repair_sol_with_random_value(u_common, Parameter.LOWER_BOUND, Parameter.UPPER_BOUND);

            // if u_common > 1 -> erf-1(u_common) = nan ?? how to FIX
            // System.out.println(u_common);

            y = x + Math.sqrt(2) * sigma * (Parameter.UPPER_BOUND - Parameter.LOWER_BOUND) * Util.erf_inverse(u_common);
            y = Util.repair_sol_in_bound(y, Parameter.LOWER_BOUND, Parameter.UPPER_BOUND);
            chromosome.add(y);
            // System.out.println(y);
        }

        return chromosome;
    }

    protected static ArrayList<Double> polynomial(ArrayList<Double> parent_genes) {
        ArrayList<Double> chromosome = new ArrayList<>(parent_genes);

        double u, delta_1, delta_2, deltaq;
        double y, val, xy;
        final double lower_bound = Parameter.LOWER_BOUND, upper_bound = Parameter.UPPER_BOUND;
        double mut_pow = 1.0 / (eta_m + 1);

        // int l = 0, r = Parameter.GENE_SIZE - 1;
        int l = Parameter.rand.nextInt(Parameter.GENE_SIZE);
        int r = Parameter.rand.nextInt(Parameter.GENE_SIZE);
        while (r == l) r = Parameter.rand.nextInt(Parameter.GENE_SIZE);

        if (r < l) {
            r = r + l;
            l = r - l;
            r = r - l;
        }

        for (int i = l; i <= r; i++) {
            y = parent_genes.get(i);
            delta_1 = (y - lower_bound) / (upper_bound - lower_bound);
            delta_2 = (upper_bound - y) / (upper_bound - lower_bound);
            u = Parameter.rand.nextDouble();

            if (u > 0.5) {
                xy = 1 - delta_2;
                val = 2 * (1 - u) + 2 * (u - 0.5) * Math.pow(xy, eta_m + 1);
                deltaq = 1 - Math.pow(val, mut_pow);
            } else {
                xy = 1 - delta_1;
                val = 2 * u + (1 - 2 * u) * Math.pow(xy, eta_m + 1);
                deltaq = Math.pow(val, mut_pow) - 1;
            }

            y = y + deltaq * (upper_bound - lower_bound);
            y = Util.repair_sol_in_bound(y, lower_bound, upper_bound);

            chromosome.set(i, y);
        }

        return chromosome;
    }

    protected static ArrayList<Double> shuffle(ArrayList<Double> parent_genes) {
        ArrayList<Double> chromosome = new ArrayList<>(parent_genes);

        int l = Parameter.rand.nextInt(Parameter.GENE_SIZE);
        int r = Parameter.rand.nextInt(Parameter.GENE_SIZE);
        while (r == l) r = Parameter.rand.nextInt(Parameter.GENE_SIZE);

        // int l = 0, r = Parameter.GENE_SIZE - 1;
        // int[] range = Util.two_points_random_in_range_by_probs(l, r + 1, Parameter.segment_probs);
        // l = range[0]; r = range[1];

        if (r < l) {
            r = r + l;
            l = r - l;
            r = r - l;
        }

        List<Double> shuffle_segment = chromosome.subList(l, r+1);
        Collections.shuffle(shuffle_segment, Parameter.rand);
        // for (int i = l; i <= r; i++) {
            // chromosome.set(i, shuffle_segment.get(i-l));
        // }

        return chromosome;
    }
}
