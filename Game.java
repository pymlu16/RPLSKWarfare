import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;
/*
 * @author Phoebe Lu
 * This class serves as an aggregate for all the Throws
 * It also detects which throws should be destroyed
 * Corner cases: It's ok if all Strings are stationary, and the user can set them 
 * to be stationary through setXVelocity and setYVelocity. The user can always change that
 * by calling setXVelocity and setYVelocity and changing the speed.
 * My window stops accepting Throws after it reaches 20 throws.
 *Throws cannot "hop" over each other-- I made the margin of collision quite large so as
 *soon as they become close they will collide.
 */

public class Game extends JPanel implements GameComponent {

	public Game() {
		// using ArrayList because it's more efficient to add and remove elements
		moves = new ArrayList<Throw>();
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		// keeps track of if the current Throw gets destroyed so it an decide whether to
		// paint it or not
		boolean destroyed = false;
		super.paintComponent(g);
		// not using for-each loop so I can keep track of indices for faster removal
		for (int i = 0; i < moves.size(); i++) {
			for (int x = 0; x < moves.size(); x++) {
				// decided 50 after trial and error
				if (i != x && Math.abs(moves.get(i).getXCoord() - moves.get(x).getXCoord()) < 50
						&& Math.abs(moves.get(i).getYCoord() - moves.get(x).getYCoord()) < 50) {
					Rules rules = new Rules();
					if (rules.getResult(moves.get(i).getName(), moves.get(x).getName()) == -1) {
						System.out.println(moves.get(i).getName() + moves.get(x).getName());
						g2d.setColor(Color.RED);
						g2d.drawString(moves.get(x).getName() + " crushes " + moves.get(i).getName(), RESULTSX,
								RESULTSY);
						destroyed = true;
					}

				}
			}
			if (blackHole != null && blackHole.inHole(moves.get(i).getXCoord(), moves.get(i).getYCoord())) {
				destroyed = true;
			}
			if (!destroyed) {
				moves.get(i).paintComponent(g);
			} else {
				moves.remove(i);
				destroyed = false;
			}

		}
	}

	public void update() {
		// implementing Iterator pattern
		for (Throw move : moves) {
			move.update();
		}

	}

	public void addThrow(Throw userThrow) {
		if (moves.size() == 20) {
			System.out.println("too many throws");
			return;
		}
		moves.add(userThrow);
	}

	// uses information from the black hole to see which Throws touched it
	public void acceptBlackHole(BlackHole hole) {
		blackHole = hole;
	}

	private ArrayList<Throw> moves;
	BlackHole blackHole;
	private final int RESULTSX = 200;
	private final int RESULTSY = 200;

}
