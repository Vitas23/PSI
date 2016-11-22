import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Lattice 
{
	//random number generator
	private Random gen;
	//represents lattice of neurons
	private Neuron[][] grid;
	
	private double initialLearningRate, initialNeighborhoodRadius;
	
	public Lattice(int rows, int columns, int dimension)
	{
		//setting properties
		initialLearningRate = 0.2;
		initialNeighborhoodRadius = Math.sqrt(rows*columns)*0.33;
		
		gen = new Random();
		grid = new Neuron[rows][columns];
		
		for(int row = 0; row < rows; row++)
			for(int col=0; col < columns; col++)
				grid[row][col] = new Neuron(row, col, dimension);
	}

	public void InitializeWeights(double minimum, double maximum)
	{
		for(int row = 0; row < grid.length; row++)
			for(int col=0; col < grid[row].length; col++)
				for(int weight = 0; weight < grid[row][col].weights.length; weight++)
					grid[row][col].weights[weight] = gen.nextDouble()*(maximum-minimum)+minimum;
	}

	
	public void UpdateWeights(double[] input, double fractionComplete)
	{
		Neuron winner = GetWinner(input);
		
		double learningRate = initialLearningRate * Math.pow(Math.E, -fractionComplete);
		double neighborhoodSize = initialNeighborhoodRadius * Math.pow(Math.E, -fractionComplete);
		
		for(int row = 0; row < grid.length; row++)
		{
			for(int col=0; col < grid[row].length; col++)
			{
				int distanceOnGrid = (winner.xLocation - row) * (winner.xLocation - row) + (winner.yLocation - col) * (winner.yLocation - col);
				double effect = Math.pow(Math.E, -distanceOnGrid / (2*neighborhoodSize * neighborhoodSize));
				
				for(int weight = 0; weight < grid[row][col].weights.length; weight++)
					grid[row][col].weights[weight] = grid[row][col].weights[weight] + effect*learningRate*(input[weight]-grid[row][col].weights[weight]);
			}
		}
		
	}
	
	private Neuron GetWinner(double input[])
	{
		ArrayList<Neuron> winners = new ArrayList<Neuron>();
		double dist, minimum = Double.MAX_VALUE;

		for(int row = 0; row < grid.length; row++)
		{
			for(int col=0; col < grid[0].length; col++)
			{
				dist = grid[row][col].GetMinkowskiDistance(input,2);

				if(dist<minimum)
				{
					winners.clear();
					winners.add(grid[row][col]);
					minimum=dist;
				}
				else if(dist==minimum)
				{
					winners.add(grid[row][col]);
				}
			}
		}
		return winners.get(gen.nextInt(winners.size()));
	}
	
	public BufferedImage CreateImage() throws IOException
	{
		BufferedImage result = new BufferedImage(grid[0].length,grid.length,BufferedImage.TYPE_INT_RGB);
		for(int row = 0; row < grid.length; row++)
			for(int col=0; col < grid[row].length; col++)
			{
				Color c = new Color((int)grid[row][col].weights[0],(int)grid[row][col].weights[1],(int)grid[row][col].weights[2]);
				result.setRGB(col, row, c.getRGB());
			}
		return result;
	}
	
	public void SetInitialLearningRate(double rate)
	{
		initialLearningRate = rate;
	}
	
	public void SetInitialNeighborhoodRadius(double radius)
	{
		initialNeighborhoodRadius = radius;
	}
}
