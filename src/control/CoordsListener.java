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
		 Double xvalue = new Double(x.getText());//muss noch Fehlerabfrage haben!
		 Double yvalue = new Double(y.getText());
		 pp.adddoublePoint(xvalue,yvalue);
		System.out.println("ja, ich bin hier gelandet");
		ha.requestFocus();
	}

}


