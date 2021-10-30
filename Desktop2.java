import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * @author jrk
 * @edited by Phoebe Lu . based on cay horstmann
 * 
 *         modified from
 *         https://docs.oracle.com/javase/tutorial/uiswing/painting/step3.html
 * 
 *         fakes applet by 1) incorporating init() and paint() into main(), like
 *         Java Web Start 2) not using the functionality of start(), stop(), or
 *         destroy() 3) hardcoding JFrame size, instead of in html 4) hardcoding
 *         "throw" specifications in separate class
 * 
 *         This class is like a general framework that instantiates different
 *         components of the game It also takes advantage of the composite
 *         patterns through its calls to paintComponent and update
 *         This sets up the RPLSK game. 
 * 
 */

public class Desktop2 {

	// Java 8 independent thread idiom, from tutorial
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	// the main() method for the graphics thread, from tutorial
	private static void createAndShowGUI() {
		// from tutorial: idiom to check on Event Dispatch Thread (EDT)
		System.out.println("Created GUI on EDT? " + SwingUtilities.isEventDispatchThread());
		// usual frame idioms
		JFrame myFrame = new JFrame("Desktop version");

		DesktopPanel myPanel = new DesktopPanel();
		myPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();
				myPanel.addHole(mouseX, mouseY, 200, 200);
			}

		});

		myFrame.add(myPanel);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.pack();
		myFrame.setVisible(true);
		// usual timer idioms
		Timer myTimer = new Timer(30, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// myPanel knows enough to do the job
				myPanel.update(); // send signal to JPanel
				myPanel.repaint();
			}
		});
		myTimer.start();
	}
}

class DesktopPanel extends JPanel {
	public DesktopPanel() {
		components = new ArrayList<GameComponent>();
		newGame = new Game();

		newGame.addThrow(
				new Throw.Customizer("Paper", Color.BLACK, 200, 300, getPreferredSize()).setXVelocity(0).done());
		newGame.addThrow(
				new Throw.Customizer("Rock", Color.BLACK, 400, 300, getPreferredSize()).setYVelocity(-1).done());
		newGame.addThrow(
				new Throw.Customizer("Lizard", Color.BLACK, 300, 40, getPreferredSize()).setYVelocity(0).done());
		newGame.addThrow(new Throw.Customizer("Spock", Color.BLACK, 0, 500, getPreferredSize()).setXVelocity(0)
				.setYVelocity(2).done());
		newGame.addThrow(new Throw.Customizer("Scissors", Color.BLACK, 200, 500, getPreferredSize()).setXVelocity(0)
				.setYVelocity(3).done());
		newGame.addThrow(
				new Throw.Customizer("Rock", Color.BLACK, 100, 60, getPreferredSize()).setYVelocity(-1).done());

		components.add(newGame);
	}

	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}

	protected void paintComponent(Graphics g) {
		// clear previous screen
		super.paintComponent(g);
		// implements composite pattern
		for (GameComponent component : components) {
			component.paintComponent(g);
		}
	}

	protected void update() {
		for (GameComponent component : components) {
			component.update();
		}
	}

	protected void addHole(int x, int y, int w, int h) {
		BlackHole hole = new BlackHole.Customizer(x, y).setDimensions(w, h).done();
		components.add(hole);
		// makes the game aware of the black hole so it knows which Throws to destroy
		newGame.acceptBlackHole(hole);
	}

	private Game newGame;
	private ArrayList<GameComponent> components;

}
