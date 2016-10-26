import java.util.Arrays;
import java.util.Random;

public class MLP {

    public static class MLPLayer {

        float[] output;
        float[] input;
        float[] weights;
        float[] dweights;
        boolean isSigmoid = true;

        public MLPLayer(int inputSize, int outputSize, Random r) {
            output = new float[outputSize];
            input = new float[inputSize + 1];
            weights = new float[(1 + inputSize) * outputSize];
            dweights = new float[weights.length];
            initWeights(r);
        }

        public void setIsSigmoid(boolean isSigmoid) {
            this.isSigmoid = isSigmoid;
        }

        public void initWeights(Random r) {
            for (int i = 0; i < weights.length; i++) {
                weights[i] = (r.nextFloat() - 0.5f) * 4f;
            }
        }

        public float[] run(float[] in) {
            System.arraycopy(in, 0, input, 0, in.length);
            input[input.length - 1] = 1;
            int offs = 0;
            Arrays.fill(output, 0);
            for (int i = 0; i < output.length; i++) {
                for (int j = 0; j < input.length; j++) {
                    output[i] += weights[offs + j] * input[j];
                }
                if (isSigmoid) {
                    output[i] = (float) (1 / (1 + Math.exp(-output[i])));
                }
                offs += input.length;
            }
            return Arrays.copyOf(output, output.length);
        }

        public float[] train(float[] error, float learningRate, float momentum) {
            int offs = 0;
            float[] nextError = new float[input.length];
            for (int i = 0; i < output.length; i++) {
                float d = error[i];
                if (isSigmoid) {
                    d *= output[i] * (1 - output[i]);
                }
                for (int j = 0; j < input.length; j++) {
                    int idx = offs + j;
                    nextError[j] += weights[idx] * d;
                    float dw = input[j] * d * learningRate;
                    weights[idx] += dweights[idx] * momentum + dw;
                    dweights[idx] = dw;
                }
                offs += input.length;
            }
            return nextError;
        }
    }
    MLPLayer[] layers;

    public MLP(int inputSize, int[] layersSize) {
        layers = new MLPLayer[layersSize.length];
        Random r = new Random(1234);
        for (int i = 0; i < layersSize.length; i++) {
            int inSize = i == 0 ? inputSize : layersSize[i - 1];
            layers[i] = new MLPLayer(inSize, layersSize[i], r);
        }
    }

    public MLPLayer getLayer(int idx) {
        return layers[idx];
    }

    public float[] run(float[] input) {
        float[] actIn = input;
        for (int i = 0; i < layers.length; i++) {
            actIn = layers[i].run(actIn);
        }
        return actIn;
    }

    public void train(float[] input, float[] targetOutput, float learningRate, float momentum) {
        float[] calcOut = run(input);
        float[] error = new float[calcOut.length];
        for (int i = 0; i < error.length; i++) {
            error[i] = targetOutput[i] - calcOut[i]; // negative error
        }
        for (int i = layers.length - 1; i >= 0; i--) {
            error = layers[i].train(error, learningRate, momentum);
        }
    }

    public static void main(String[] args) throws Exception {
        float[][] train = new float[][]{new float[]{0, 0}, new float[]{0, 1}, new float[]{1, 0}, new float[]{1, 1}};
        float[][] res = new float[][]{new float[]{0}, new float[]{1}, new float[]{1}, new float[]{0}};
        MLP mlp = new MLP(2, new int[]{2, 1});
        mlp.getLayer(1).setIsSigmoid(false);
        Random r = new Random();
        int en = 500;
        for (int e = 0; e < en; e++) {

            for (int i = 0; i < res.length; i++) {
                int idx = r.nextInt(res.length);
                mlp.train(train[idx], res[idx], 0.3f, 0.6f);
            }

            if ((e + 1) % 50 == 0) {
                System.out.println();
                for (int i = 0; i < res.length; i++) {
                    float[] t = train[i];
                    System.out.printf("%d epoch\n", e + 1);
                    System.out.printf("%.1f, %.1f --> %.3f\n", t[0], t[1], mlp.run(t)[0]);
                }
            }
        }
    }
}