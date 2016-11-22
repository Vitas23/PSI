
public class Neuron 
{
	double[] weights;
	int xLocation, yLocation;
	
	public Neuron(int row, int col, int dimension)
	{
		xLocation = row;
		yLocation = col;
		weights = new double[dimension];
	}
	
	public double GetMinkowskiDistance(double values[],double power)
	{
		double result = 0;
		
		for(int weight = 0; weight < weights.length; weight++)
			result+=Math.pow(Math.abs((values[weight] - weights[weight])), power);

		return Math.pow(result, 1/power);
	}
}
