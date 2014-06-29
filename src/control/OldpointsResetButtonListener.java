package control;

import hamSanApp.HamSanAlg;
import hamSanApp.Point;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;

import view.HamSanApplet;
import view.LinePanel;
import view.PointPanel;
import view.PointType;

public class OldpointsResetButtonListener implements ActionListener {

	private HamSanAlg hsa;
	private PointPanel pp;
	private LinePanel lp;
	private JLabel l;
	private HamSanApplet applet;
	
	public OldpointsResetButtonListener(HamSanAlg hsa, PointPanel pp, LinePanel lp, JLabel label, HamSanApplet hamSanApplet) {
		this.hsa = hsa;
		this.pp = pp;
		this.lp = lp;
		this.l = label;
		this.applet = hamSanApplet;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		doStuff();
	}
	

	public void doStuff() {
		applet.setPlacingEnabled(true);
		applet.setStepsEnabled(true);
		
		/*List<Point> oldBlue; 		
		oldBlue=hsa.lBlue;
		oldBlue.addAll(hsa.lBlueDel);
		List<Point> oldRed;	
		oldRed=hsa.lRed;
		oldRed.addAll(hsa.lRedDel);
		//sorge dafür, das kleinster Index erstes Element ist,
		//denn dieses wird unter Umständen zu Beginn gelöscht
		int smallestBlue = oldBlue.get(0).i;
		int smallestRed = oldRed.get(0).i;
		for(int j=0;j<oldBlue.size();j++){
			if(smallestBlue>oldBlue.get(j).i){
				smallestBlue=j;
			}
		}
		for(int j=0;j<oldRed.size();j++){
			if(smallestRed>oldRed.get(j).i){
				smallestRed=j;
			}
		}
		Point firstBlue=oldBlue.get(0);
		oldBlue.set(0, oldBlue.get(smallestBlue-1));
		oldBlue.set(smallestBlue-1,firstBlue);*/
		
	//	Point firstRed=oldRed.get(0);
	//	oldRed.set(0, oldRed.get(smallestRed-1));
	//	oldRed.set(smallestRed-1,firstRed);
		List<Point> oldBlue; 		
		oldBlue=hsa.firstlBlue;
		List<Point> oldRed;	
		oldRed=hsa.firstlRed;
		
		hsa.init();
		hsa.lBlue=oldBlue;
		hsa.lRed=oldRed;

		l.setText("step 0: place points");
		pp.setAddingAllowed(true);
		pp.setVisualPoints(hsa.getVisualPoints());
		lp.setMinAndMax(-10, -10, 10, 10);
		pp.refreshAll();
		pp.setCurrentType(PointType.BLUE);
	}
}