package control;

import hamSanApp.HamSanAlg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

	public RandomButtonListener(HamSanAlg hsa, LinePanel lp, PointPanel pp,
			JComboBox<String> c, HamSanApplet applet) {
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
		if (!hsa.done) {
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

			} else if (c.getSelectedItem() == "random points (square)") {
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

			} else if (c.getSelectedItem() == "all Points on one side") {
				// alle Punkte auf einer Geraden
				PointType type = pp.getCurrentType();

				for (int i = -9; i < -4; i += 2) {
					pp.setCurrentType(PointType.BLUE);
					pp.adddoublePoint(i, i);
				}
				pp.setCurrentType(PointType.RED);
				pp.adddoublePoint(-4, -4);
				for (int i = -3; i < 1; i += 2) {
					pp.setCurrentType(PointType.BLUE);
					pp.adddoublePoint(i, i);
				}
				for (int i = 1; i < 5; i += 2) {
					pp.setCurrentType(PointType.RED);
					pp.adddoublePoint(i, i);
				}
				pp.setCurrentType(PointType.BLUE);
				pp.adddoublePoint(5, 5);
				for (int i = 6; i < 10; i += 2) {
					pp.setCurrentType(PointType.RED);
					pp.adddoublePoint(i, i);
				}
				pp.setCurrentType(PointType.RED);
				pp.adddoublePoint(6, 7);
				pp.adddoublePoint(4, 8);
				pp.adddoublePoint(3, 5);
				pp.setCurrentType(PointType.BLUE);
				pp.adddoublePoint(-3, -1);
				pp.adddoublePoint(-8, -4);
				pp.setCurrentType(type);

			} else if (c.getSelectedItem() == "vertical solution") {
				// Vertikale LÃ¶sung
				PointType type = pp.getCurrentType();
				
				pp.setCurrentType(PointType.BLUE);
				for (int i = 4; i > 0; i--) {
					pp.adddoublePoint(-1, i);
				}
				pp.setCurrentType(PointType.RED);
				for (int i = 8; i > 5; i--) {
					pp.adddoublePoint(-1, i);
				}

				pp.setCurrentType(PointType.BLUE);
				for (int i = 1; i >= 0; i--) {
					pp.adddoublePoint(-2, i);
				}

				pp.setCurrentType(PointType.BLUE);
				for (int i = 7; i >= 5; i--) {
					if (i % 2 == 0) {
						pp.setCurrentType(PointType.RED);
					} else {
						pp.setCurrentType(PointType.BLUE);
					}
					pp.adddoublePoint(4, i);
				}

				pp.setCurrentType(PointType.RED);
				for (int i = -2; i >= -4; i--) {
					if (i % 2 == 0) {
						pp.setCurrentType(PointType.RED);
					} else {
						pp.setCurrentType(PointType.BLUE);
					}
					pp.adddoublePoint(-4, i);
				}
				pp.adddoublePoint(2, 2);
				pp.setCurrentType(type);

			} else if (c.getSelectedItem() == "only one color") {
				// nur einfarbige Punkte
				double color = Math.random();
				if (color <= 0.5) {
					for (int i = 0; i < 10 + r.nextDouble() * 20; i++) {
						double phi = r.nextDouble() * Math.PI * 2;
						double rad = r.nextDouble() * 5;
						hsa.addLine(Math.sin(phi) * rad, Math.cos(phi) * rad,
								false);
					}
				} else {
					for (int i = 0; i < 10 + r.nextDouble() * 20; i++) {
						double phi = r.nextDouble() * Math.PI * 2;
						double rad = r.nextDouble() * 5;
						hsa.addLine(Math.sin(phi) * rad, Math.cos(phi) * rad,
								true);
					}
				}

			} else if (c.getSelectedItem() == "collinear case") {
				// alle Punkte auf einer Geraden
				PointType type = pp.getCurrentType();
				for (int i = -9; i < -4; i++) {
					pp.setCurrentType(PointType.BLUE);
					pp.adddoublePoint(i, i);
				}
				pp.setCurrentType(PointType.RED);
				pp.adddoublePoint(-4, -4);
				for (int i = -3; i < 1; i++) {
					pp.setCurrentType(PointType.BLUE);
					pp.adddoublePoint(i, i);
				}
				for (int i = 1; i < 5; i++) {
					pp.setCurrentType(PointType.RED);
					pp.adddoublePoint(i, i);
				}
				pp.setCurrentType(PointType.BLUE);
				pp.adddoublePoint(5, 5);
				for (int i = 6; i < 10; i++) {
					pp.setCurrentType(PointType.RED);
					pp.adddoublePoint(i, i);
				}
				pp.setCurrentType(type);

			} else if (c.getSelectedItem() == "early stop") {
				pp.setCurrentType(PointType.BLUE);
				pp.adddoublePoint(-1, 2);
				pp.adddoublePoint(-1, 1);
				pp.setCurrentType(PointType.RED);
				pp.adddoublePoint(-1, 0);
				pp.adddoublePoint(-1, -1);
				pp.adddoublePoint(-1, -2);

				pp.setCurrentType(PointType.RED);
				pp.adddoublePoint(0, 1);
				pp.adddoublePoint(0, -1);
				pp.adddoublePoint(0, 2);
				pp.adddoublePoint(0, 0);
				pp.adddoublePoint(0, -2);

				pp.setCurrentType(PointType.RED);
				pp.adddoublePoint(1, 1);
				pp.adddoublePoint(1, 2);
				pp.setCurrentType(PointType.BLUE);
				pp.adddoublePoint(1, 0);
				pp.adddoublePoint(1, -1);
				pp.adddoublePoint(1, -2);
			} 
			else if (c.getSelectedItem() == "multiple solutions(2)") {
				// Erste Einfügereihenfolge
				PointType type = pp.getCurrentType();
				pp.setCurrentType(PointType.RED);
				for (int i = 0; i >-4 ; i--) {
					pp.adddoublePoint(-1, i);
				}

				pp.setCurrentType(PointType.BLUE);
				for (int i = 1; i >= 0; i--) {
					pp.adddoublePoint(-2, i);
				}
				
				pp.setCurrentType(PointType.BLUE);
				for (int i = 2; i > 0; i--) {
					pp.adddoublePoint(-1, i);
				}
				pp.setCurrentType(PointType.RED);
				for (int i = 8; i > 5; i--) {
					pp.adddoublePoint(-1, i);
				}

				pp.setCurrentType(PointType.BLUE);
				for (int i = 7; i >= 5; i--) {
					if (i % 2 == 0) {
						pp.setCurrentType(PointType.RED);
					} else {
						pp.setCurrentType(PointType.BLUE);
					}
					pp.adddoublePoint(4, i);
				}

				pp.setCurrentType(PointType.RED);
				for (int i = -2; i >= -4; i--) {
					if (i % 2 == 0) {
						pp.setCurrentType(PointType.RED);
					} else {
						pp.setCurrentType(PointType.BLUE);
					}
					pp.adddoublePoint(-4, i);
				}
				pp.adddoublePoint(2, 2);
				pp.adddoublePoint(-3,8);
				pp.setCurrentType(PointType.RED);
				pp.adddoublePoint(4, 9);
				pp.setCurrentType(type);


			} else if (c.getSelectedItem() == "multiple solutions(1)") {
				//zweite Einfügereihenfolge					
					
					PointType type = pp.getCurrentType();
					
					pp.setCurrentType(PointType.RED);
					for (int i = -3; i<=0 ; i++) {
						pp.adddoublePoint(-1, i);
					}

					pp.setCurrentType(PointType.BLUE);
					for (int i = 1; i >= 0; i--) {
						pp.adddoublePoint(-2, i);
					}
					pp.setCurrentType(PointType.RED);
					for (int i = 6; i < 8; i++) {
						pp.adddoublePoint(-1, i);
					}

					pp.setCurrentType(PointType.BLUE);
					for (int i = 5; i <= 7; i++) {
						if (i % 2 == 0) {
							pp.setCurrentType(PointType.RED);
						} else {
							pp.setCurrentType(PointType.BLUE);
						}
						pp.adddoublePoint(4, i);
					}

					pp.setCurrentType(PointType.RED);
					for (int i = -4; i <= -2; i++) {
						if (i % 2 == 0) {
							pp.setCurrentType(PointType.RED);
						} else {
							pp.setCurrentType(PointType.BLUE);
						}
						pp.adddoublePoint(-4, i);
					}
					pp.adddoublePoint(2, 2);
					pp.setCurrentType(PointType.RED);
					pp.setCurrentType(PointType.BLUE);
					for (int i = 1; i <=2; i++) {
						pp.adddoublePoint(-1, i);
					}
					pp.adddoublePoint(-3,8);
					pp.setCurrentType(PointType.RED);
					pp.adddoublePoint(4, 9);
					pp.setCurrentType(PointType.BLUE);
					pp.adddoublePoint(-1, 8);
					pp.setCurrentType(type);

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
}
