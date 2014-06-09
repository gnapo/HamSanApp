package view;

import hamSanApp.HamSanAlg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JPanel;

public class PointPanel extends JPanel implements MouseListener, MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5611393992807346242L;

	private PointType currentType = PointType.BLUE;
	private boolean addingAllowed = true;

	private List<VisualPoint> visualPoints;

	private VisualPoint highlightedPoint = null;

	private LinePanel linePanel;

	private HamSanAlg h;

	public int getXmin() {
		return xmin;
	}

	private int xmin = -10;
	private int xmax = 10;
	private int ymin = -10;
	private int ymax = 10;

	PointPanel(HamSanAlg hsa, LinePanel lp) {
		super();
		h = hsa;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		visualPoints = h.getVisualPoints();
		this.linePanel = lp;
	}

	public void refreshAll() {
		this.repaint();
		linePanel.setVisualPoints(visualPoints);
		linePanel.repaint();
	}

	public void setVisualPoints(List<VisualPoint> visualPoints) {
		this.visualPoints = visualPoints;
	}

	public List<VisualPoint> getVisualPoints() {
		return visualPoints;
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
			
			double a = VisualPoint.xToA(x, xmin, xmax, this.getSize());
			double b = VisualPoint.yToB(y, ymin, ymax, this.getSize());
			/* old code, didn't work for some reason
			double xscale = this.getWidth() / (xmax - xmin);
			double yscale = this.getHeight() / (ymax - ymin);
			double a = ((double) x / xscale) + xmin;
			double b = ((double) -y / yscale) - ymin;*/

			VisualPoint candidate = new VisualPoint(a, b, type, false);
			if (visualPoints.contains(candidate)) {
				System.out.println("not allowed");
				return false;
			} else {
				visualPoints.add(candidate);
				if (type == PointType.BLUE) {
					candidate.setMyPoint(h.addLine(a, b, true));
				} else {
					candidate.setMyPoint(h.addLine(a, b, false));
				}
				this.refreshAll();
				return true;
			}
		} else {
			return false;
		}
	}
	
	
	public boolean adddoublePoint(double x, double y) {
		if (addingAllowed) {
			double a = x + xmin +(xmax-xmin)/2;
			double b =y + ymin +(ymax-ymin)/2;

			VisualPoint candidate = new VisualPoint(a, b, currentType, false);
			if (visualPoints.contains(candidate)) {
				System.out.println("not allowed, hier, obwohl ich nicht hier sein will");
				return false;
			} else {
				visualPoints.add(candidate);
				if (currentType == PointType.BLUE) {
					System.out.println("currentTyp ist Blau, Punkt wird Blau");
					candidate.setMyPoint(h.addLine(a, b, true));
				} else {
					System.out.println("currentTyp ist Rot, Punkt wird Rot");
					candidate.setMyPoint(h.addLine(a, b, false));
				}
				this.refreshAll();
				return true;
			}
		} else {
			return false;
		}
	}
	

	public void drawCross(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.gray);
		g.drawLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
		g.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		drawCross(g);

		g.setColor(Color.magenta);
		if (h.done && (h.verticalSol || h.solution != null)) {
			
			if (h.verticalSol) {
				double xVal = VisualPoint.aToX(h.verticalSolPos, xmin, xmax, this.getSize());
				
				g.drawLine((int) xVal, 0, (int) xVal, this.getHeight());
			} else {
				double bMin = h.solution.a * xmin + h.solution.b;
				double bMax = h.solution.a * xmax + h.solution.b;
				
				double yMin = VisualPoint.bToY(bMin, ymin, ymax, this.getSize());
				double yMax = VisualPoint.bToY(bMax, ymin, ymax, this.getSize());

				g.drawLine(0, (int) yMin, this.getWidth(), (int) yMax);
			}
		}
		g.setColor(Color.black);
		g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);

		for (VisualPoint v : visualPoints) {
			v.drawAsPoint(g, xmin, xmax, ymin, ymax, this.getSize());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			addPoint(e.getX(), e.getY(), currentType);
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			if (highlightedPoint != null && addingAllowed) {
				h.removeLine(highlightedPoint.getMyPoint());
				visualPoints.remove(highlightedPoint);
				this.refreshAll();
			}
		}
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

	public boolean isAddingAllowed() {
		return addingAllowed;
	}

	public void setAddingAllowed(boolean addingAllowed) {
		this.addingAllowed = addingAllowed;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (highlightedPoint != null && addingAllowed) {
			h.removeLine(highlightedPoint.getMyPoint());
			highlightedPoint.setXY(new Point2D.Double(e.getX(), e.getY()), xmin, ymin, xmax, ymax, this.getSize());
			highlightedPoint.setMyPoint(h.addLine(highlightedPoint.getA(), highlightedPoint.getB(),
					highlightedPoint.isBlue()));
			refreshAll();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		highlightedPoint = null;

		for (VisualPoint v : visualPoints) {
			v.highlighted = false;
		}

		for (VisualPoint v : visualPoints) {
			if (v.containsCursorPoint(e.getX(), e.getY(), xmin, xmax, ymin, ymax, this.getSize())) {
				v.highlighted = true;
				highlightedPoint = v;
				linePanel.setVisualPoints(visualPoints);
			}
		}

		this.revalidate();
		this.repaint();
		linePanel.revalidate();
		linePanel.repaint();
	}

	public PointType getCurrentType() {
		return currentType;
	}

	public void setCurrentType(PointType currentType) {
		this.currentType = currentType;
	}

}
