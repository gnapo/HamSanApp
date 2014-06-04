package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import view.HamSanApplet;
import view.PointPanel;

public class CoordsListener implements ActionListener  {
	
	private PointPanel pp;
	private JTextField x,y;
	private HamSanApplet ha;
	
	public CoordsListener(HamSanApplet ha ,PointPanel pp, JTextField x, JTextField y) {
		this.pp = pp;
		this.x=x;
		this.y=y;
		this.ha = ha;
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0){
		 doStuff();
	}
	
	public void doStuff() {
		double xvalue = new Double(x.getText());//TODO muss noch Fehlerabfrage haben!
		double yvalue = new Double(y.getText());
		pp.adddoublePoint(xvalue,yvalue);
		ha.requestFocus();
	}

}


