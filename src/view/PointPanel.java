package view;

import hamSanApp.HamSanAlg;
import hamSanApp.Point;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import control.ToggleListener;

public class PointPanel extends JPanel implements MouseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5611393992807346242L;
	
	private PointType currentType = PointType.BLUE;
	private boolean addingAllowed = true;
	private Set<VisualPoint> visualPoints;
	
	
	private HamSanAlg h;
	public int getXmin() {
		return xmin;
	}

	private int xmin = -10;
	private int xmax = 10;
	private int ymin = -10;
	private int ymax = 10;
	
	private int xscale = 300 / (xmax - xmin);
	private int yscale = 300 / (ymax - ymin);

	PointPanel(HamSanAlg hsa) {
		super();
		h = hsa;
		this.addMouseListener(this);
		
		visualPoints = new HashSet<VisualPoint>();
	}
	
	public void togglePointType() {
		if (currentType == PointType.BLUE) {
			currentType = PointType.RED;
		} else {
			currentType = PointType.BLUE;
		}
	}
	
	public boolean addPoint(int x, int y, PointType type) {
		if (addingAllowed) {
			double a = ((double) x / xscale) + xmin;
			double b = ((double) -y / yscale) - ymin;
			
			VisualPoint candidate = new VisualPoint(a, b, type);
			if (visualPoints.contains(candidate)) {
				return false;
			} else {
				visualPoints.add(candidate);
				if (type == PointType.BLUE) {
					h.addLine(a, b, true);
				} else {
					h.addLine(a, b, false);
				}
				System.out.println(visualPoints.size());
				System.out.println("wuff");
				this.revalidate();
				this.repaint();
				return true;
			}
		} else {
			return false;
		}
	}

	public void drawCross(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, 300, 300);
		g.setColor(Color.gray);
		g.drawLine(150, 1, 150, 300);
		g.drawLine(1, 150, 300, 150);
	}

	@Override
	public void paint(Graphics g) {
		System.out.println("painting");
		super.paint(g);
		drawCross(g);

		g.setColor(Color.magenta);
		if (h.done) {
			if (h.verticalSol) {
				double dx = (h.verticalSolPos - xmin) * xscale;
				g.drawLine((int) dx, 1, (int) dx, 300);
			} else {
				double y1 = h.solution.eval(xmin);
				double y2 = h.solution.eval(xmax);

				double dy1 = ((-y1) + ymax) * yscale;
				double dy2 = ((-y2) + ymax) * yscale;

				g.drawLine(0, (int) dy1, 300, (int) dy2);
			}
		}
		g.setColor(Color.black);
		g.drawRect(0, 0, 300, 300);

		for (VisualPoint v : visualPoints) {
			v.draw(g, xmin, xmax, ymin, ymax);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		addPoint(e.getX(), e.getY(), currentType);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
