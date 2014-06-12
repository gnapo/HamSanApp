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
	
	public boolean isDouble(String string) {
	    try {
	        Double.valueOf(string);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	@Override
	public void actionPerformed(ActionEvent arg0){
		 doStuff();
	}
	
	public void doStuff() {
		
		if ((isDouble(x.getText())==false)||(isDouble(y.getText())==false)){
			System.out.println("Bitte Zahlen ins Textfeld eingeben");
		}else{
		 Double xvalue = new Double(x.getText());
		 Double yvalue = new Double(y.getText());
		 pp.adddoublePoint(xvalue,yvalue);
		//jSystem.out.println("ja, ich bin hier gelandet");
		 ha.setStepsEnabled(true);
		}
		ha.requestFocus();
		
	}

}


