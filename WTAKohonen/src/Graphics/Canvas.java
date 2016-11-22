package Graphics;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Canvas extends JPanel implements Runnable
{
	private static final long serialVersionUID = 8898608544543193367L;
	private BufferedImage buffer;
	private int refresh_rate;
	private Color background;
	private Graphics graphics;
	
	public Canvas(int width, int height, int refresh_rate, Color background) throws InterruptedException
	{
		this.setSize(width, height);
		
		this.refresh_rate = refresh_rate;
		this.background = background;
		
		buffer = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		graphics = buffer.getGraphics();
		ClearBuffer();


		Thread runner = new Thread(this);
		runner.start();
	}
	
	public void run() 
	{
		this.repaint();
		try
		{
			Thread.sleep(1000/refresh_rate);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g)
	{
		g.drawImage(buffer, 0, 0,this);
	}
	
	public Graphics getGraphics()
	{
		return graphics;
	}
	
	public void ClearBuffer()
	{
		graphics.setColor(background);
		graphics.fillRect(0, 0, buffer.getWidth(this), buffer.getHeight(this));
		graphics.setColor(graphics.getColor());
	}
	
	public void ClearBuffer(Color c)
	{
		Color temp = graphics.getColor();
		graphics.setColor(c);
		graphics.fillRect(0, 0, buffer.getWidth(this), buffer.getHeight(this));
		graphics.setColor(temp);
	}
	
	public void ClearBuffer(BufferedImage image)
	{
		graphics.drawImage(image,0,0,this);
		paintComponent(super.getGraphics());
	}
	
}
