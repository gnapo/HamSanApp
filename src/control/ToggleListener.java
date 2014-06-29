package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;

import view.PointPanel;
import view.PointType;

public class ToggleListener implements KeyListener {

	private PointPanel myPointPanel;
	private DoAlgButtonListener algBut;
	private DoAllgButtonListener allgBut;
	private ResetButtonListener resBut;
	private RandomButtonListener randBut;
	private OldpointsResetButtonListener oldresBut;
	private JLabel clabel;
	
	public ToggleListener(JLabel colourlabel,PointPanel pp,DoAlgButtonListener doalg,DoAllgButtonListener doallg,ResetButtonListener res,RandomButtonListener ran, OldpointsResetButtonListener oldpointresBut) {
		myPointPanel = pp;
		algBut = doalg;
		allgBut = doallg;
		resBut = res;
		oldresBut=oldpointresBut;
		randBut = ran;
		clabel=colourlabel;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {	
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			myPointPanel.togglePointType();
			if(myPointPanel.getCurrentType()==PointType.BLUE){
				clabel.setText("colour: blue - space to change" );
			}
			else{
				clabel.setText("colour: red - space to change" );
			}
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
		if (e.getKeyCode() == KeyEvent.VK_S) {
			//reset but keep old points
			oldresBut.doStuff();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
