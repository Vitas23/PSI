import java.util.*;
import java.io.*;

class Hopfield {

    static final int M = 9;
    static final int N = M*M;
    static final int MAX_EXAMPLES = 4;
    static int[][] weights = new int[N][N];
    static int[] inputs = new int[N];
    static int[] outputs = new int[N];

    /* Create all the example states, then store in LinkedList. */
    static ArrayList<int[]> examples = new ArrayList<int[]>();

    /* Plus. */
    static int[] ex1 = {
            1,  1,  1,  1, -1,  1,  1,  1,  1,
            1,  1,  1,  1, -1,  1,  1,  1,  1,
            1,  1,  1,  1, -1,  1,  1,  1,  1,
            1,  1,  1,  1, -1,  1,  1,  1,  1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1,
            1,  1,  1,  1, -1,  1,  1,  1,  1,
            1,  1,  1,  1, -1,  1,  1,  1,  1,
            1,  1,  1,  1, -1,  1,  1,  1,  1,
            1,  1,  1,  1, -1,  1,  1,  1,  1
    };

    /* Box. */
    static int[] ex2 = {
            -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1,  1,  1,  1,  1,  1,  1,  1, -1,
            -1,  1,  1,  1,  1,  1,  1,  1, -1,
            -1,  1,  1,  1,  1,  1,  1,  1, -1,
            -1,  1,  1,  1,  1,  1,  1,  1, -1,
            -1,  1,  1,  1,  1,  1,  1,  1, -1,
            -1,  1,  1,  1,  1,  1,  1,  1, -1,
            -1,  1,  1,  1,  1,  1,  1,  1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1
    };

    /* X. */
    static int[] ex3 = {
            -1,  1,  1,  1,  1,  1,  1,  1, -1,
            1, -1,  1,  1,  1,  1,  1, -1,  1,
            1,  1, -1,  1,  1,  1, -1,  1,  1,
            1,  1,  1, -1,  1, -1,  1,  1,  1,
            1,  1,  1,  1, -1,  1,  1,  1,  1,
            1,  1,  1, -1,  1, -1,  1,  1,  1,
            1,  1, -1,  1,  1,  1, -1,  1,  1,
            1, -1,  1,  1,  1,  1,  1, -1,  1,
            -1,  1,  1,  1,  1,  1,  1,  1, -1
    };

    /* Small box. */
    static int[] ex4 = {
            1,  1,  1,  1,  1,  1,  1,  1,  1,
            1,  1,  1,  1,  1,  1,  1,  1,  1,
            1,  1, -1, -1, -1, -1, -1, -1,  1,
            1,  1, -1,  1,  1,  1,  1, -1,  1,
            1,  1, -1,  1,  1,  1,  1, -1,  1,
            1,  1, -1,  1,  1,  1,  1, -1,  1,
            1,  1, -1, -1, -1, -1, -1, -1,  1,
            1,  1,  1,  1,  1,  1,  1,  1,  1,
            1,  1,  1,  1,  1,  1,  1,  1,  1
    };


    /* The hopfield sign function */
    public static int SGN (int x) {
        if (x >= 0) return 1;
        else return -1;
    }


    public static void setInputsToExample (int ex, double p) {
        Random rand = new Random();
        
		/* Grab the input cell */
        int[] tmp = examples.get(ex);

        for (int i = 0; i < N; i++) {
			
			/* Grab the input cell */
            inputs[i] = tmp[i];

            /* Mutate the input of probability permits */
            if (rand.nextDouble() < p) inputs[i] *= -1;
        }

        return;
    }


    public static void genWeights() {
        int e, r, c;
        
        /* First, clear the weights */
        for (r = 0; r < N; r++) {
            for (c = 0; c < N; c++){
                weights[r][c] = 0;
            }
        }

        for (e = 0; e < MAX_EXAMPLES; e++) {
            for (r = 0; r < N; r++){
                for (c = 0; c < N; c++) {
                    
                    /* Don't permit self-connections */
                    if (r==c) continue;

                    int[] ex = examples.get(e);

                    weights[r][c] += (ex[r]) * (ex[c]);
                }
            }
        }

        return;
    }


    public static void computeActivations() {
        int r, c;
        int[][] temp = new int[N][N];

        for (r = 0; r < N; r++) {
            for (c = 0; c < N; c++) {
                if (r == c) continue;
                temp[r][c] += inputs[r] * weights[r][c];

            }
        }

        for (c = 0; c < N; c++) {
            outputs[c] = 0;
            for (r = 0; r < N; r++) {
                outputs[c] += temp[r][c];
            }
            outputs[c] = SGN(outputs[c]);

        }

        return;
    }


    public static void emitResults (int[] vector) {
        int i;

        for (i = 0; i < N; i++){
            //System.out.print( vector[i] );
            if ( (i % M) == 0) System.out.print("\n");

            if (vector[i] < 0) System.out.print("* ");

            else System.out.print("  ");
        }

        System.out.print("\n");
    }


    public static void main (String[] args) {
        examples.add(ex1);
        examples.add(ex2);
        examples.add(ex3);
        examples.add(ex4);

        int e, i;
        double noise;

        genWeights();

        for (e = 0; e < MAX_EXAMPLES; e++) {

            noise = 0.0;

            for (i = 0; i < 21; i++) {
                System.out.print("\n--\nZageszczenie: " + noise + "\n");

                setInputsToExample( e, noise );
                emitResults(inputs);
//                System.out.println("WAGA : " + weights[r][c]);

                computeActivations();

                System.out.print("Wynik dla wzorca nr  " + (e+1) + "\n");
                emitResults(outputs);

                noise += 0.02;

            }

            System.out.print("\n---------------------------------\n\n");

        }
    }
}