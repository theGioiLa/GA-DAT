package hust.mso.ga;

public class Main {
    static final String BASE_PATH = "./cache";
    static final String MOCK_PATH = "./mock";
    static final String EXT_SAMPLE = ".test";
    static final String EXT_OUT = ".csv";

    public static void main(String[] args) {
        on_start_up(args);
        GA solver = new GA();

        double start = System.currentTimeMillis();
        Individual best_solution = solver.run();
        Parameter.runtime = (System.currentTimeMillis() - start) / 1000;

        Logger.log(best_solution, "ecmin", args[0]);
    }

    static void on_start_up(String[] args) {
        // if (args.length < 3) {
        // System.err.println("Args format: input1 input2 seed");
        // System.exit(-1);
        // }

        String path = String.format("%s/%s%s", BASE_PATH, args[0], EXT_SAMPLE);
        int seed = Integer.parseInt(args[1]);
        double pc = Double.parseDouble(args[2]);
        double pm = Double.parseDouble(args[3]);

        Task.init(path);
        Parameter.init(seed, pc, pm);
    }
}
