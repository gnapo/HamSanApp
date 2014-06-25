package control;

import hamSanApp.HamSanAlg;
import hamSanApp.Point;
import view.PointType;
import hamSanApp.Crossing;

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
			if (neg <=0.5){x=(steigung*10);}
			else {x=-(steigung*10);}
			PointType type=pp.getCurrentType();
			double neg2=Math.random();
			if (neg2 <=0.5){pp.setCurrentType(PointType.BLUE);}
			else {pp.setCurrentType(PointType.RED);}
			pp.adddoublePoint(x,(abschnitt*20)-10);
			pp.setCurrentType(type);
		}
		else if (c.getSelectedItem()=="random paralel lines"){
			double neg=Math.random(); double steigung=Math.random();
			double x=0;
			if (neg <=0.5){x=steigung*10;}
			else {x=-steigung*10;}
			PointType type=pp.getCurrentType();
			double interationen=Math.random()*5+2;
			for (int i = 0; i < interationen;i++){
				double neg2=Math.random();
				double abschnitt =Math.random();
				if (neg2 <=0.5){pp.setCurrentType(PointType.BLUE);}
				else {pp.setCurrentType(PointType.RED);}
				 pp.adddoublePoint(x,(abschnitt*20)-10);
			}		
			pp.setCurrentType(type);
		}
		else if (c.getSelectedItem()=="test"){
		/*	//Test  von compareTo mit 4 parallelen Geraden
		 * pp.setCurrentType(PointType.BLUE);
			for (int i = 4; i > 0;i--){
				 pp.adddoublePoint(-1,i);
			}	
			pp.adddoublePoint(0,1);
			Crossing c_0_1=new Crossing(hsa.lBlue.get(0),hsa.lBlue.get(1));
			Crossing c_0_2=new Crossing(hsa.lBlue.get(0),hsa.lBlue.get(2));
			Crossing c_1_2=new Crossing(hsa.lBlue.get(1),hsa.lBlue.get(2));
			Crossing c_2_3=new Crossing(hsa.lBlue.get(2),hsa.lBlue.get(3));
			Crossing c_anders=new Crossing(hsa.lBlue.get(2),hsa.lBlue.get(4));	// von unterster Linie mit anderer Linie Kreuzung
			
			//public int compareTo(Crossing other) { 
		    //returns -1 if this is left than other, 0 if this is other, 1 if this is to the right
			System.out.println(c_0_1 +" "+ c_0_2 + " "+c_1_2);
			System.out.println("ist das hier" +c_1_2.compareTo(c_0_2)+"= -1 so ist alles super");
			System.out.println("ist das hier"+ c_0_2.compareTo(c_0_1)+"=-1 so ist alles super");
			System.out.println("ist das hier"+ c_1_2.compareTo(c_0_1)+"=-1 so ist alles super");
			System.out.println("ist das hier"+ c_0_1.compareTo(c_2_3)+"=1 so ist alles super");
			System.out.println("ist das hier"+ c_anders.compareTo(c_2_3)+"=1 so ist alles super");
		    System.out.println("ist das hier"+ c_2_3.compareTo(c_anders)+"=-1 so ist alles super");
			crossings = new ArrayList<Crossing>();
			crossings.add(c_anders);
			crossings.add(c_2_3);
			crossings.add(c_1_2);
			crossings.add(c_0_2);
			crossings.add(c_0_1);
			Collections.sort(crossings);
			System.out.println("ich will, dass das hier richtig sortiert ist:"+crossings);
			*/
			
			// generate all the crossings:
			crossings = new ArrayList<Crossing>();
			
			for (int i = 0; i < hsa.lBlue.size(); i++) { //blue-red
				for (int j = 0; j < hsa.lRed.size(); j++) {
					Crossing c = new Crossing(hsa.lBlue.get(i), hsa.lRed.get(j));
					if (hsa.inBorders(c)) {
						crossings.add(c);
					}
				}
			}
			
			if (crossings.size() == 1) { //beseitigt glaub ich einige fehlerf�lle? ja, das ist aber schlecht. :<
				Crossing c = crossings.get(0);
				return;
			}
			
			for (int i = 0; i < hsa.lBlue.size(); i++) { //blue-blue
				for (int j = i + 1; j < hsa.lBlue.size(); j++) {
					Crossing c = new Crossing(hsa.lBlue.get(i), hsa.lBlue.get(j));
					if (hsa.inBorders(c)) {
						crossings.add(c);
					}
				}
			}
			
			
			for (int i = 0; i < hsa.lRed.size(); i++) { //red-red
				for (int j = i + 1; j < hsa.lRed.size(); j++) {
					Crossing c = new Crossing(hsa.lRed.get(i), hsa.lRed.get(j));
					if (hsa.inBorders(c)) {
						crossings.add(c);
					}
				}
			}

			// sort them. crossings implements comparable.

			// make stripes with at most alpha*(n choose 2) crossings a piece.
			Collections.sort(crossings);
			
			
			
			// might work?
			/*
			if (DEBUG && false) {
				// warning: cheating going on.
				for (int i = 0; i < crossings.size(); i++) {
					double pos = crossings.get(i).crAt();
					if (levelPos(pos, true, levelBlue) == levelPos(pos, false,
							levelRed)) {
						System.out.println("yayy!");
						done = true;
						solution = new Point(-pos, levelPos(pos, true,
								levelBlue));
						return;
					}
				}
				System.out.println("aww :'(");
			}*/
			//int a=crossings.size()/2;int b=crossings.size()-1;
			//System.out.println("haben folgende kreuzungen: "+"bei 0 "+crossings.get(0)+" in der Mitte : "+crossings.get(a)+"; am Ende "+crossings.get(b));
			System.out.println("haben folgende kreuzungen: "+crossings);

			
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
