package control;

import hamSanApp.HamSanAlg;
import hamSanApp.Point;
import view.PointType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

import javax.swing.JComboBox;

import view.HamSanApplet;
import view.LinePanel;
import view.PointPanel;
import view.VisualPoint;


public class RandomButtonListener implements ActionListener {

	private HamSanAlg hsa;
	private LinePanel lp;
	private PointPanel pp;
	private Random r;
	private JComboBox<String> c;
	private HamSanApplet applet;
	
	public RandomButtonListener(HamSanAlg hsa,LinePanel lp, PointPanel pp, JComboBox<String> c, HamSanApplet applet) {
		r = new Random();
		r.setSeed(1);
		this.hsa = hsa;
		this.lp = lp;
		this.pp = pp;
		this.c = c;
		this.applet = applet;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		doStuff();
	}
	
	public void doStuff() {
		if (c.getSelectedItem()=="random points (circle)") {
			for (int i = 0; i < 10 + r.nextDouble()*20;i++){
				double phi = r.nextDouble()*Math.PI*2;
				double rad = r.nextDouble()*5;
				hsa.addLine(Math.sin(phi)*rad,Math.cos(phi)*rad , false);
			}
			for (int i = 0; i < 10 + r.nextDouble()*20;i++){
				double phi = r.nextDouble()*Math.PI*2;
				double rad = r.nextDouble()*5;
				hsa.addLine(Math.sin(phi)*rad,Math.cos(phi)*rad , true);
			}
		}
		else if (c.getSelectedItem()=="random points (square)"){
			for (int i = 0; i < 10 + r.nextDouble()*20;i++){
				hsa.addLine((r.nextDouble()*10)-5, (r.nextDouble()*15)-7.5 , false);
			}
			for (int i = 0; i < 10 + r.nextDouble()*20;i++){
				hsa.addLine((r.nextDouble()*10)-5, (r.nextDouble()*15)-7.5 , true);
			}
		}
		else if (c.getSelectedItem()=="single random points"){
			double neg=Math.random(); double steigung=Math.random(); double abschnitt =Math.random();double x=0;
			if (neg <=0.5){x=(steigung*10)-5;}
			else {x=-(steigung*10)-5;}
			PointType type=pp.getCurrentType();
			double neg2=Math.random();
			if (neg2 <=0.5){pp.setCurrentType(PointType.BLUE);}
			else {pp.setCurrentType(PointType.RED);}
			pp.adddoublePoint(x,(abschnitt*15)-7.5);
			pp.setCurrentType(type);
		}
		else if (c.getSelectedItem()=="random paralel lines"){
		/*	double neg=Math.random();double x=0;
			if (neg <=0.5){x=(r.nextDouble()*10)-5;}
			else {x=-((r.nextDouble()*10)-5);}
			PointType type=pp.getCurrentType();
			for (int i = 0; i < 10 + r.nextDouble()*20;i++){
				double neg2=Math.random();
				if (neg2 <=0.5){pp.setCurrentType(PointType.BLUE);}
				else {pp.setCurrentType(PointType.RED);}
				 pp.adddoublePoint(x,(r.nextDouble()*15)-7.5);*/
			double neg=Math.random(); double steigung=Math.random();
			double x=0;
			if (neg <=0.5){x=(steigung*10)-5;}
			else {x=-((steigung*10)-5);}
			PointType type=pp.getCurrentType();
			for (int i = 0; i < 10;i++){
				double neg2=Math.random();
				double abschnitt =Math.random();
				if (neg2 <=0.5){pp.setCurrentType(PointType.BLUE);}
				else {pp.setCurrentType(PointType.RED);}
				 pp.adddoublePoint(x,(abschnitt*15)-7.5);
			}		
			pp.setCurrentType(type);
		}
		else if (c.getSelectedItem()=="test"){
			for (int i = 3; i > 0;i--){
				 pp.adddoublePoint(-1,i);
			}		
			for (int i=2; i>=0; i--){
				if (pp.getCurrentType()==PointType.BLUE){
					try {
						System.out.println(Point.op2naive(hsa.lBlue.get(i), hsa.lBlue.get((i+1)%3),hsa.lBlue.get(i),hsa.lBlue.get((i+2)%3)));
					} catch (Exception e) {
						// TODO Automatisch generierter Erfassungsblock
						e.printStackTrace();
					}}
				else {
					try {
						System.out.println(Point.op2naive(hsa.lRed.get(i), hsa.lRed.get((i+1)%3),hsa.lRed.get(i),hsa.lRed.get((i+2)%3)));
					} catch (Exception e) {
						// TODO Automatisch generierter Erfassungsblock
						e.printStackTrace();
					}
				}
			}
			
		}
		List<VisualPoint> vpoints = hsa.getVisualPoints();
		pp.setVisualPoints(vpoints);
		lp.setVisualPoints(vpoints);
		pp.revalidate();
		pp.repaint();
		lp.revalidate();
		lp.repaint();
		applet.setStepsEnabled(true);
	}
}
