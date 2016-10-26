class Example{
    int x1, x2;
    double o;
    Example(int x1, int x2, double o){
        this.x1 = x1;
        this.x2 = x2;
        this.o = o;
    }
}

class DeltaRulePerceptron {
    double w1, w2;
    double dw1, dw2;
    double n;

    DeltaRulePerceptron(){
        //Random values
        w1 = -0.8;
        w2 = 0.7;
        n = 0.05;
    }

    public double computeOutput(Example example){
        return example.x1 * w1 + example.x2*w2;
    }

    public void trianWithDelta(Example[] examples){
        for(int i=0;i<1000;++i){

            System.out.println("Iteration #"+i);
            dw1 = 0;
            dw2 = 0;

            for(Example ex:examples){
                double o = computeOutput(ex);
                double t = ex.o;

                System.out.println("o = "+o+" t = "+t);

                dw1 = dw1 + n*(t-o)*ex.x1;
                dw2 = dw2 + n*(t-o)*ex.x2;
            }

            w1 += dw1;
            w2 += dw2;
        }
    }

    public static void main(String[] args){
        DeltaRulePerceptron dr = new DeltaRulePerceptron();

        //AND boolean function
        Example[] examples = new Example[]{
                new Example(-1, -1, -1),
                new Example(-1 , 1, -1),
                new Example( 1, -1, -1),
                new Example( 1,  1, 1)
        };
        dr.trianWithDelta(examples);
        System.out.println("Trained weights : "+dr.w1+" "+dr.w2);

        //Test
        System.out.println(dr.sign(dr.computeOutput(examples[0])));
        System.out.println(dr.sign(dr.computeOutput(examples[1])));
        System.out.println(dr.sign(dr.computeOutput(examples[2])));
        System.out.println(dr.sign(dr.computeOutput(examples[3])));


    }

    public int sign(double output){
        return output>0?+1:-1;
    }
}