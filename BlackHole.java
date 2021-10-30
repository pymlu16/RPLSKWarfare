import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
/*
 * @author Phoebe Lu
 * A stationary black hole that destroys any Throws that touch it. 
 * Implements builder pattern
 */

public class BlackHole implements GameComponent {
	public BlackHole(Customizer customizer) {
		xCoord = customizer.xCoord;
		yCoord = customizer.yCoord;
		width = customizer.width;
		height = customizer.height;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g.fillOval((int)xCoord, (int)yCoord, (int)width,(int) height);

	}
	public void update() {
		
	}
	public boolean inHole(long x, long y)
	{
		//equation to find if a point is inside an ellipses
		if(((Math.pow(x-xCoord,2)/Math.pow(width/2,2))+(Math.pow(y-yCoord,2)/Math.pow(height/2,2)))<1) {
			return true; 
			
		}
		return false; 
	}

	public static class Customizer
	{

		public Customizer(long x, long y) {
			xCoord = x;
			yCoord = y;
			width = 200;
			height = 200;
		}
		public Customizer setDimensions(long w, long h)
		{
			width=w;
			height=h;
			return this;
		}

		public BlackHole done() {
			return new BlackHole(this);
		}

		private long xCoord;
		private long yCoord;
		private long width;
		private long height;
	}

	private long xCoord;
	private long yCoord;
	private long width;
	private long height;


}
