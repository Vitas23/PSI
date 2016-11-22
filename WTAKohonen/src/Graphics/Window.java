package Graphics;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame
{
	private static final long serialVersionUID = 1L;
	JPanel pane;
	
	public Window(int width, int height, int rows, int columns, String title)
	{
		this.setSize(width, height);
		this.setTitle(title);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		pane = new JPanel();
		pane.setLayout(new GridLayout(rows,columns));
		this.add(pane);
	}
	
	public void AddCanvas(Canvas c)
	{
		pane.add(c);
	}
	
}
