package ss.week7.bounce; 
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JPanel;

/**
 * BallPanel a special JPanel for drawing balls on.
 * Used with TimedBouncer.
 * @version 2005.02.22
 */
/**P 7.5
 * the program should be able to draw one or multiple balls 
 * which are then animated on the screen. 
 * The idea is that they bounce back from the edge of their screen 
 * and that they cannot go through each other. 
 * At this point I believe the ball is not animated 
 * and is only shown in the left corner. 
 * So the question is why there is no animation 
 * and how you could solve this such that you will see the animation.*/

public class BallPanel /*actionlistener*/extends JPanel implements ActionListener {
	private List<Ball> balls; // @invariant balls != null
	private AnimateThread animateThread;
	
	public BallPanel() 
	{ 
		balls = new java.util.ArrayList<Ball>();
		animateThread=new AnimateThread(); //what is the argument?
		animateThread.start();
		int delay = 5; //everytime when a ballPanel is created, the timer starts to count delay. 
		//when delay is timeout ¼´¹ýÊ±,action is performed. when another delay is timeout, another action is performed
		new Timer(delay,this).start();
	}
	
	/**P 7.7
	 * Implements the method from the interface ActionListener
	 * Move and repaint the balls
	 */
	/**Original code:
	 * public void actionPerformed(ActionEvent e) {
	 *     moveBalls();
	 *     repaint();
	 * }*/
	@Override
	public void actionPerformed(ActionEvent e) 
	{
			moveBalls();
			repaint();
	}
	
	public void animate() {
		try {
			while (true) {
				Thread.sleep(5);
				moveBalls();
				repaint();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//P 7.6
	public class AnimateThread extends Thread
	{
		 public void run() {
			 animate();
		 }
	}


	/** Add a new ball to the ball list and start the timer if not yet running. */
	public synchronized void addNewBall() {
		balls.add(new Ball(this));
	}

	/**
	 * Move all balls 
	 * BEWARE: collision effects are not respecting Snellius' law. 
	 */
	public synchronized void moveBalls() {
		for (Ball b : balls) {
			b.move();
		}

		// collision detection
		ListIterator<Ball> ix = balls.listIterator();
		while (ix.hasNext()) {
			Ball b = ix.next();
			ListIterator<Ball> jx = balls.listIterator(ix.nextIndex());
			while (jx.hasNext()) {
				Ball other = jx.next();
				b.collide(other);
			}
		}
	}

	/**
	 * Overrides paintComponent in JPanel.
	 * Is called if repaint is called.
	 * Paints all elements of balls.
	 */
	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Ball b : balls) {
			b.draw(g);
		}
	}


}
