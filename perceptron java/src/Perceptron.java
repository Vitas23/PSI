import java.util.Random;

/**
 * Created by ematesw on 2016-10-11.
 */
public class Perceptron {

    double [] weights;
    double threshold;


    public void Train(double [][] inputs, int [] outputs, double threshold,double lrate, int iterations)
    {
        this.threshold = threshold;
        int n = inputs[0].length;
        int p = outputs.length;
        weights = new double[n];
        Random r = new Random();


        // INICJALIZACJA WAG
        for(int i=0;i<n;i++){
            weights[i]=r.nextDouble();
        }

        for(int i=0;i<iterations;i++){
            int totalError = 0;
            for(int j=0;j<p;j++){
                int output = Output(inputs[j]);
                int error = outputs[j] - output;
                totalError+=error;
                for(int k=0;k<n;k++){
                    double delta = lrate * inputs[j][k] * error;
                    weights[k]+=delta;
                }
            }
            if(totalError == 0) break;
        }
    }

    public int Output(double [] input){
        double sum = 0.0;
        for(int i=0;i<input.length;i++){
            sum+=weights[i]*input[i];
        }
        if(sum>=threshold) return 1;
        else return 0;
    }
}
