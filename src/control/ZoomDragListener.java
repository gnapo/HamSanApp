package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import view.LinePanel;
import view.Mode;

public class ZoomDragListener implements KeyListener {

	private LinePanel linePanel;
	
	public ZoomDragListener(LinePanel linePanel) {
		this.linePanel = linePanel;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			linePanel.setMode(Mode.ZOOM_RECTANGLE);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			linePanel.setMode(Mode.DRAG);
		}
	}

}
