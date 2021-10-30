import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;
/*
 * @author Phoebe Lu
 * paints and updates one throw
 * implements the builder pattern
 */
public class Throw extends JPanel implements GameComponent {
	private Throw(Customizer customizer) {
		moveName = customizer.moveName;
		moveColor = customizer.moveColor;
		//used to keep track in case of wraparound
		initialX=customizer.initialX;
		initialY=customizer.initialY; 
		xCoord = customizer.xCoord;
		yCoord = customizer.yCoord;
		xVelocity = customizer.xVelocity;
		yVelocity = customizer.yVelocity;
		dimension=customizer.dimension;	}

	public String getName() {
		return moveName;
	}
	
	//not used, but wanted to include so my code can be extended 
	public Color getColor() {
		return moveColor;
	}

	public long getXCoord() {
		return xCoord;
	}

	public long getYCoord() {
		return yCoord;
	}

	//not used, but wanted to include so my code can be extended 
	public Rectangle2D getRectangle() {
		return rectangle;
	}
	

	public void update() {
		xCoord -= xVelocity;
		yCoord -= yVelocity;
		if (xCoord + rectangle.getWidth() < 0) {
			xCoord = (int)dimension.getWidth();
			yCoord=initialY;
		}
		if (yCoord + rectangle.getHeight() < 0) {
			yCoord = (int)dimension.getHeight(); // this is getWidth() from Component
			xCoord = initialX; 
		}
	}
	

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		// usual font idioms
		Font throwFont = new Font("Courier", Font.BOLD, 20);
		FontRenderContext throwContext = g2d.getFontRenderContext();
		g2d.setColor(moveColor);
		rectangle = throwFont.getStringBounds(moveName, throwContext);
		g2d.drawString(moveName, xCoord, yCoord);
	}

	public static class Customizer {
		public Customizer(String name, Color color, long x, long y,Dimension dim) {
			moveName = name;
			moveColor = color;
			initialX=x;
			initialY=y; 
			xCoord = x;
			yCoord = y;
			//default velocities
			xVelocity = 1;
			yVelocity = 1;
			dimension=dim;
		}
		

		public Throw done() {
			return new Throw(this);
		}
 
		public Customizer setXVelocity(long newVelocity) {
			xVelocity = newVelocity;
			return this;
		}

		public Customizer setYVelocity(long newVelocity) {
			yVelocity = newVelocity;
			return this;
		}

		private String moveName;
		private Color moveColor;
		private long initialX;
		private long initialY; 
		private long xCoord;
		private long yCoord;
		private long xVelocity;
		private long yVelocity;
		private Dimension dimension;

	}


	private String moveName;
	private Color moveColor;
	private long initialX;
	private long initialY; 
	private long xCoord;
	private long yCoord;
	private Rectangle2D rectangle;
	private long xVelocity;
	private long yVelocity;
	private Dimension dimension;

}
