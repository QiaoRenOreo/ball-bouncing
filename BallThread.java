package ss.week7.bounce;

import ss.week7.bounce.BallPanel;

public class BallThread extends Thread {
	private BallPanel ballPanel;
	BallThread(BallPanel bpArg) 
	{
		this.ballPanel = bpArg;
	} 
	public void run() {
		ballPanel.animate();   //mandelPanel.drawMandel();
	}
}
