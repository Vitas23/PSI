import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import Graphics.Canvas;
import Graphics.Window;

public class Initializer 
{
	private static Random gen;

	public static void main(String[] args) throws IOException, InterruptedException 
	{
		Window window = new Window(500,500,1,1,"SOM Demo");
		Canvas canvas = new Canvas(500,500,33,Color.BLACK);
		window.AddCanvas(canvas);
		
		int epochs = 200;
		gen = new Random();

		Lattice lat = new Lattice(500 , 500 , 3);
		lat.InitializeWeights(0, 255);
		
		ArrayList<double[]> training = new ArrayList<double[]>();
		
		for(int x = 0 ; x < 300; x++)
			training.add(CreateRandomVector(3 , 0 , 255));

		for(int epoch = 0; epoch < epochs; epoch++)
		{			
			lat.UpdateWeights(training.get(gen.nextInt(training.size())), epoch/(float)epochs);
			canvas.ClearBuffer(lat.CreateImage());
		}
		
		System.out.println("/ / / / / / COMPLETE / / / / / /");
	}

	private static double[] CreateRandomVector(int dimension, double min, double max)
	{
		double[] result = new double[dimension];
		for(int i = 0; i < result.length; i++)
			result[i] = gen.nextDouble()*(max-min) + min;
		return result;
	}
}
