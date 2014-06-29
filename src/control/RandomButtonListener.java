package control;

import hamSanApp.Crossing;
import hamSanApp.HamSanAlg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JComboBox;

import view.HamSanApplet;
import view.LinePanel;
import view.PointPanel;
import view.PointType;
import view.VisualPoint;


public class RandomButtonListener implements ActionListener {

	private HamSanAlg hsa;
	private LinePanel lp;
	private PointPanel pp;
	private Random r;
	private JComboBox<String> c;
	private HamSanApplet applet;
	private ArrayList<Crossing> crossings;
	
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
		
		if (c.getSelectedItem() == "random points (circle)") {
			for (int i = 0; i < 10 + r.nextDouble() * 20; i++) {
				double phi = r.nextDouble() * Math.PI * 2;
				double rad = r.nextDouble() * 5;
				hsa.addLine(Math.sin(phi) * rad, Math.cos(phi) * rad, false);
			}
			for (int i = 0; i < 10 + r.nextDouble() * 20; i++) {
				double phi = r.nextDouble() * Math.PI * 2;
				double rad = r.nextDouble() * 5;
				hsa.addLine(Math.sin(phi) * rad, Math.cos(phi) * rad, true);
			}
			
		}else if (c.getSelectedItem() == "random points (square)") {
			for (int i = 0; i < 10 + r.nextDouble() * 20; i++) {
				hsa.addLine((r.nextDouble() * 10) - 5,
						(r.nextDouble() * 15) - 7.5, false);
			}
			for (int i = 0; i < 10 + r.nextDouble() * 20; i++) {
				hsa.addLine((r.nextDouble() * 10) - 5,
						(r.nextDouble() * 15) - 7.5, true);
			}
			
		} else if (c.getSelectedItem() == "single random points") {
			double neg = Math.random();
			double steigung = Math.random();
			double abschnitt = Math.random();
			double x = 0;
			if (neg <= 0.5) {
				x = (steigung * 10);
			} else {
				x = -(steigung * 10);
			}
			PointType type = pp.getCurrentType();
			double neg2 = Math.random();
			if (neg2 <= 0.5) {
				pp.setCurrentType(PointType.BLUE);
			} else {
				pp.setCurrentType(PointType.RED);
			}
			pp.adddoublePoint(x, (abschnitt * 20) - 10);
			pp.setCurrentType(type);
			
		} else if (c.getSelectedItem() == "random paralel lines") {
			double neg = Math.random();
			double steigung = Math.random();
			double x = 0;
			if (neg <= 0.5) {
				x = steigung * 10;
			} else {
				x = -steigung * 10;
			}
			PointType type = pp.getCurrentType();
			double interationen = Math.random() * 5 + 2;
			for (int i = 0; i < interationen; i++) {
				double neg2 = Math.random();
				double abschnitt = Math.random();
				if (neg2 <= 0.5) {
					pp.setCurrentType(PointType.BLUE);
				} else {
					pp.setCurrentType(PointType.RED);
				}
				pp.adddoublePoint(x, (abschnitt * 20) - 10);
			}
			pp.setCurrentType(type);
			
		} else if (c.getSelectedItem() == "special case 1") {
			//Auf einer Seite des Hamsandwichcuts sind Gar keine Punkte!

			pp.setCurrentType(PointType.BLUE);
			for (int i = 4; i > 0; i--) {
				pp.adddoublePoint(-1, i);
			}
			pp.setCurrentType(PointType.RED);
			for (int i = 8; i > 5; i--) {
				pp.adddoublePoint(-1, i);
			}
			
			pp.setCurrentType(PointType.BLUE);
			for (int i = 1; i >=0 ; i--) {
				pp.adddoublePoint(-2, i);
			}
		} else if (c.getSelectedItem() == "special case 2") {
			//Vertikale Lösung

			pp.setCurrentType(PointType.BLUE);
			for (int i = 4; i > 0; i--) {
				pp.adddoublePoint(-1, i);
			}
			pp.setCurrentType(PointType.RED);
			for (int i = 8; i > 5; i--) {
				pp.adddoublePoint(-1, i);
			}
			
			pp.setCurrentType(PointType.BLUE);
			for (int i = 1; i >=0 ; i--) {
				pp.adddoublePoint(-2, i);
			}
			
			pp.setCurrentType(PointType.BLUE);
			for (int i = 7; i >=5 ; i--) {
				if(i%2==0){
					pp.setCurrentType(PointType.RED);
					}else{
						pp.setCurrentType(PointType.BLUE);
					}
				pp.adddoublePoint(4, i);
			}
			
			pp.setCurrentType(PointType.RED);
			for (int i = -2; i >=-4 ; i--) {
				if(i%2==0){
					pp.setCurrentType(PointType.RED);
					}else{
						pp.setCurrentType(PointType.BLUE);
					}
				pp.adddoublePoint(-4, i);
			}
			pp.adddoublePoint(2, 2);
		} else if (c.getSelectedItem() == "special case 3") {
			//nur einfarbige Punkte
			double color = Math.random();
			if(color <=0.5){
				for (int i = 0; i < 10 + r.nextDouble() * 20; i++) {
					double phi = r.nextDouble() * Math.PI * 2;
					double rad = r.nextDouble() * 5;
					hsa.addLine(Math.sin(phi) * rad, Math.cos(phi) * rad, false);
				}
			}else{
				for (int i = 0; i < 10 + r.nextDouble() * 20; i++) {
					double phi = r.nextDouble() * Math.PI * 2;
					double rad = r.nextDouble() * 5;
					hsa.addLine(Math.sin(phi) * rad, Math.cos(phi) * rad, true);
				}
			}

		} else if (c.getSelectedItem() == "test") {
			// Test von compareTo mit 4 parallelen Geraden
			pp.setCurrentType(PointType.BLUE);
			for (int i = 4; i > 0; i--) {
				pp.adddoublePoint(-1, i);
			}
			pp.adddoublePoint(0, 1);
			Crossing c_0_1 = new Crossing(hsa.lBlue.get(0), hsa.lBlue.get(1));
			Crossing c_0_2 = new Crossing(hsa.lBlue.get(0), hsa.lBlue.get(2));
			Crossing c_1_2 = new Crossing(hsa.lBlue.get(1), hsa.lBlue.get(2));
			Crossing c_2_3 = new Crossing(hsa.lBlue.get(2), hsa.lBlue.get(3));
			Crossing c_anders = new Crossing(hsa.lBlue.get(2), hsa.lBlue.get(4)); // von
																					// unterster
																					// Linie
																					// mit
																					// anderer
																					// Linie
																					// Kreuzung

			// public int compareTo(Crossing other) {
			// returns -1 if this is left than other, 0 if this is other, 1 if
			// this is to the right
			System.out.println(c_0_1 + " " + c_0_2 + " " + c_1_2);
			System.out.println("ist das hier" + c_1_2.compareTo(c_0_2)
					+ "= -1 so ist alles super");
			System.out.println("ist das hier" + c_0_2.compareTo(c_0_1)
					+ "=-1 so ist alles super");
			System.out.println("ist das hier" + c_1_2.compareTo(c_0_1)
					+ "=-1 so ist alles super");
			System.out.println("ist das hier" + c_0_1.compareTo(c_2_3)
					+ "=1 so ist alles super");
			System.out.println("ist das hier" + c_anders.compareTo(c_2_3)
					+ "=1 so ist alles super");
			System.out.println("ist das hier" + c_2_3.compareTo(c_anders)
					+ "=-1 so ist alles super");
			crossings = new ArrayList<Crossing>();
			crossings.add(c_anders);
			crossings.add(c_2_3);
			crossings.add(c_1_2);
			crossings.add(c_0_2);
			crossings.add(c_0_1);
			Collections.sort(crossings);
			System.out.println("ich will, dass das hier richtig sortiert ist:"
					+ crossings);

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
