public class Main {

    public static void main(String[] args) {
	Perceptron p = new Perceptron();
        double inputs [][] ={{0,0},{0,1},{1,0},{1,1}};
        int outputs[] = {0,0,0,1};
        p.Train(inputs,outputs,0.2,0.1,200);

        System.out.println(p.Output(new double[]{0,0}));
        System.out.println(p.Output(new double[]{1,0}));
        System.out.println(p.Output(new double[]{0,1}));
        System.out.println(p.Output(new double[]{1,1}));
    }
}
