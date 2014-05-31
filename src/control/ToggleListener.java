package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import view.PointPanel;

public class ToggleListener implements KeyListener {

	private PointPanel myPointPanel;
	
	public ToggleListener(PointPanel pp) {
		this.myPointPanel = pp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {	
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			myPointPanel.togglePointType();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
