package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import view.PointPanel;

public class ToggleListener implements KeyListener {

	private PointPanel myPointPanel;
	private DoAlgButtonListener algBut;
	private DoAllgButtonListener allgBut;
	private ResetButtonListener resBut;
	private RandomButtonListener randBut;
	
	public ToggleListener(PointPanel pp,DoAlgButtonListener doalg,DoAllgButtonListener doallg,ResetButtonListener res,RandomButtonListener ran) {
		myPointPanel = pp;
		algBut = doalg;
		allgBut = doallg;
		resBut = res;
		randBut = ran;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {	
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			myPointPanel.togglePointType();
		}
		if (e.getKeyCode() == KeyEvent.VK_N) {
			//next step
			algBut.doStuff();
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			//all steps
			allgBut.doStuff();
		}
		if (e.getKeyCode() == KeyEvent.VK_R) {
			//reset
			resBut.doStuff();
		}
		if (e.getKeyCode() == KeyEvent.VK_P) {
			//add Points
			randBut.doStuff();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
