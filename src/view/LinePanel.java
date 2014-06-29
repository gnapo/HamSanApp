package view;

import hamSanApp.Crossing;
import hamSanApp.HamSanAlg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

//import java.util.Date;

public class LinePanel extends JPanel implements MouseMotionListener, MouseWheelListener, MouseInputListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1230109349211303663L;

	private HamSanAlg h;
	private double xmin = -10;
	private double xmax = 10;
	private double ymin = -10;
	private double ymax = 10;
	private double referenceLength = 20;
	private double referenceHeight = 20;

	private double zoomLength = 20;
	private double zoomHeight = 20;
	private Point2D.Double zoomCenterAB = new Point2D.Double(0, 0);

	private double zoomFactor = 1;
	private Point2D.Double corner1, corner2;

	private boolean showCrossings = false;

	private VisualPoint highlightedPoint = null;

	private PointPanel pointPanel;

	private List<VisualPoint> visualPoints;

	private boolean drawDeleted = true;

	private int initialX, initialY;

	private Mode mode = Mode.DRAG;

	private int currentMouseButton = -1;

	LinePanel(HamSanAlg hsa) {
		h = hsa;
		visualPoints = hsa.getVisualPoints();
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.addMouseListener(this);
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public void setPointPanel(PointPanel pp) {
		this.pointPanel = pp;
	}

	public void setVisualPoints(List<VisualPoint> visualPoints) {
		this.visualPoints = visualPoints;
	}

	public void drawCross(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.gray);

		int x0 = (int) VisualPoint.aToX(0, xmin, xmax, this.getSize());
		int y0 = (int) VisualPoint.bToY(0, ymin, ymax, this.getSize());

		g.drawLine(0, this.getHeight() - y0, this.getWidth(), this.getHeight() - y0);
		g.drawLine(x0, 0, x0, this.getHeight());
	}

	public void drawPoint(Graphics g, int x, int y) {
		g.fillOval(x - 2, y - 2, 4, 4);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		drawCross(g);

		if (drawDeleted) {
			for (VisualPoint p : visualPoints) {
				if (p.deleted) {
					p.drawAsLine(g, xmin, xmax, ymin, ymax, this.getSize());
				}
			}
		}

		for (VisualPoint p : visualPoints) {
			if (!p.deleted) {
				p.drawAsLine(g, xmin, xmax, ymin, ymax, this.getSize());
			}
		}

		if (showCrossings) {
			g.setColor(Color.GREEN);
			for (Crossing c : h.crossings) {
				if (c.atInf()) {
					continue;
				}
				double crossingA = c.crAt();
				double crossingB = c.line1.a * crossingA + c.line1.b;

				Point2D.Double asAB = new Point2D.Double(crossingA, crossingB);
				Point2D.Double asXY = VisualPoint.toXY(asAB, xmin, ymin, xmax, ymax, this.getSize());
				int x = (int) asXY.x;
				int y = (int) asXY.y;

				g.drawLine(x - 2, this.getHeight() - y, x + 2, this.getHeight() - y);
				g.drawLine(x, this.getHeight() - y - 2, x, this.getHeight() - y + 2);

				// drawPoint(g, (int) asXY.x, (int) asXY.y);
			}
		}

		g.setColor(Color.magenta);
		if (h.done && (h.solution != null)) {
			Point2D.Double cutAB = new Point2D.Double(-h.solution.a, h.solution.b);
			Point2D.Double cutXY = VisualPoint.toXY(cutAB, xmin, ymin, xmax, ymax, this.getSize());
			int x = (int) cutXY.x;
			int y = (int) cutXY.y;

			g.fillOval(x - 4, this.getHeight() - y - 4, 8, 8);
		}

		if (corner1 != null && corner2 != null) {
			g.setColor(Color.YELLOW);
			drawZoomRectangle(g);
		}

		g.setColor(Color.gray.brighter()); // draw vertical lines to distinguish
											// intervals

		for (int i = Math.max(1, h.minband); i <= h.maxband; i++) {
			int x0 = (int) VisualPoint.aToX(h.borders[i], xmin, xmax, this.getSize());
			g.drawLine(x0, 0, x0, this.getHeight());
		}
		g.setColor(Color.black); // draw leftB and rightb
		if (h.leftborder) {
			int x0 = (int) VisualPoint.aToX(h.leftb, xmin, xmax, this.getSize());
			g.drawLine(x0, 0, x0, this.getHeight());
		}
		if (h.rightborder) {
			int x0 = (int) VisualPoint.aToX(h.rightb, xmin, xmax, this.getSize());
			g.drawLine(x0, 0, x0, this.getHeight());
		}

		if (h.trapeze != null && h.trapeze.bounded) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(2.5f));
			int x1 = (int) VisualPoint.aToX(h.trapeze.left, xmin, xmax, this.getSize());
			int x2 = (int) VisualPoint.aToX(h.trapeze.right, xmin, xmax, this.getSize());
			int ytl = (int) VisualPoint.bToY(h.trapeze.topleft, ymin, ymax, this.getSize());
			int ybl = (int) VisualPoint.bToY(h.trapeze.botleft, ymin, ymax, this.getSize());
			int ybr = (int) VisualPoint.bToY(h.trapeze.botright, ymin, ymax, this.getSize());
			int ytr = (int) VisualPoint.bToY(h.trapeze.topright, ymin, ymax, this.getSize());
			g2d.drawLine(x1, this.getHeight() - ytl, x1, this.getHeight() - ybl);
			g2d.drawLine(x1, this.getHeight() - ybl, x2, this.getHeight() - ybr);
			g2d.drawLine(x2, this.getHeight() - ybr, x2, this.getHeight() - ytr);
			g2d.drawLine(x2, this.getHeight() - ytr, x1, this.getHeight() - ytl);
			g2d.setStroke(new BasicStroke());
		}
		if (h.trapeze != null && !h.trapeze.bounded) {
			if (h.trapeze.openleft) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setStroke(new BasicStroke(2.5f));
				int x2 = (int) VisualPoint.aToX(h.trapeze.right, xmin, xmax, this.getSize());
				int ybr = (int) VisualPoint.bToY(h.trapeze.botright, ymin, ymax, this.getSize());
				int ytr = (int) VisualPoint.bToY(h.trapeze.topright, ymin, ymax, this.getSize());
				double dx1 = VisualPoint.xToA(0, xmin, xmax, this.getSize());
				int x1 = (int) VisualPoint.aToX(dx1, xmin, xmax, this.getSize());
				int ytl = (int) VisualPoint.bToY(h.trapeze.topright + (dx1 - h.trapeze.right) * h.trapeze.topslope,
						ymin, ymax, this.getSize());
				int ybl = (int) VisualPoint.bToY(h.trapeze.botright + (dx1 - h.trapeze.right) * h.trapeze.botslope,
						ymin, ymax, this.getSize());

				g2d.drawLine(x1, this.getHeight() - ybl, x2, this.getHeight() - ybr);
				g2d.drawLine(x2, this.getHeight() - ybr, x2, this.getHeight() - ytr);
				g2d.drawLine(x2, this.getHeight() - ytr, x1, this.getHeight() - ytl);
				g2d.setStroke(new BasicStroke());
			} else {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setStroke(new BasicStroke(2.5f));
				int x1 = (int) VisualPoint.aToX(h.trapeze.left, xmin, xmax, this.getSize());
				int ybl = (int) VisualPoint.bToY(h.trapeze.botleft, ymin, ymax, this.getSize());
				int ytl = (int) VisualPoint.bToY(h.trapeze.topleft, ymin, ymax, this.getSize());
				double dx2 = VisualPoint.xToA(this.getSize().width, xmin, xmax, this.getSize());
				int x2 = (int) VisualPoint.aToX(dx2, xmin, xmax, this.getSize());
				int ytr = (int) VisualPoint.bToY(h.trapeze.topleft + (dx2 - h.trapeze.left) * h.trapeze.topslope, ymin,
						ymax, this.getSize());
				int ybr = (int) VisualPoint.bToY(h.trapeze.botleft + (dx2 - h.trapeze.left) * h.trapeze.botslope, ymin,
						ymax, this.getSize());
				g2d.drawLine(x1, this.getHeight() - ytl, x1, this.getHeight() - ybl);
				g2d.drawLine(x1, this.getHeight() - ybl, x2, this.getHeight() - ybr);

				g2d.drawLine(x2, this.getHeight() - ytr, x1, this.getHeight() - ytl);
				g2d.setStroke(new BasicStroke());
			}
		}

		g.setColor(Color.black);
		g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
	}

	private void drawZoomRectangle(Graphics g) {
		int x1 = (int) Math.min(corner1.x, corner2.x);
		int y1 = (int) Math.min(corner1.y, corner2.y);
		int x2 = (int) Math.max(corner1.x, corner2.x);
		int y2 = (int) Math.max(corner1.y, corner2.y);
		int dx = x2 - x1;
		int dy = y2 - y1;
		g.drawRect(x1, y1, dx, dy);
	}

	private void doZoom() {
		if (corner1 == null || corner1.equals(corner2)) {
			return;
		}

		// set new xmin, xmax, ymin, ymax
		int x1 = (int) Math.min(corner1.x, corner2.x);
		int y1 = (int) Math.min(this.getHeight() - corner1.y, this.getHeight() - corner2.y);
		int x2 = (int) Math.max(corner1.x, corner2.x);
		int y2 = (int) Math.max(this.getHeight() - corner1.y, this.getHeight() - corner2.y);

		double aMin = VisualPoint.xToA(x1, xmin, xmax, this.getSize());
		double aMax = VisualPoint.xToA(x2, xmin, xmax, this.getSize());
		double bMin = VisualPoint.yToB(y1, ymin, ymax, this.getSize());
		double bMax = VisualPoint.yToB(y2, ymin, ymax, this.getSize());

		System.out.println("aMin: " + aMin + ", aMax: " + aMax + ", bMin: " + bMin + ", bMax: " + bMax);

		this.setMinAndMax(aMin, bMin, aMax, bMax);

		corner1 = null;
		corner2 = null;
		this.repaint();
	}

	public void followTrapeze() {
		if (h.trapeze == null)
			return;
		if (!h.trapeze.bounded) {
			if (h.trapeze.openleft) {
				double top = h.trapeze.topright;
				double bot = h.trapeze.botright;
				double he = top - bot;
				setMinAndMax(h.trapeze.right - 5, bot - he, h.trapeze.right + 1, top + he);
			} else {
				double top = h.trapeze.topleft;
				double bot = h.trapeze.botleft;
				double he = top - bot;
				setMinAndMax(h.trapeze.left - 1, bot - he, h.trapeze.left + 5, top + he);
			}
		} else {
			double w = h.trapeze.right - h.trapeze.left;

			double top = Math.max(h.trapeze.topleft, h.trapeze.topright);
			double bot = Math.min(h.trapeze.botleft, h.trapeze.botright);
			double he = (top - bot) / 2;

			setMinAndMax(h.trapeze.left - w, bot - he, h.trapeze.right + w, top + he);
		}
		this.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		highlightedPoint = null;

		for (VisualPoint v : visualPoints) {
			v.highlighted = false;
		}

		for (VisualPoint v : visualPoints) {
			if (v.containsCursorLine(e.getX(), e.getY(), xmin, xmax, ymin, ymax, this.getSize())) {
				v.highlighted = true;
				highlightedPoint = v;
				pointPanel.setVisualPoints(visualPoints);
				break;
			}
		}
		this.repaint();
		pointPanel.repaint();
	}

	public boolean isShowCrossings() {
		return showCrossings;
	}

	public void setShowCrossings(boolean showCrossings) {
		this.showCrossings = showCrossings;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		double zoom = zoomFactor + e.getPreciseWheelRotation() / 100;
		if (zoom < 0)
			zoom = 0.0000000000001;
		/*
		 * if (zoom > 10) zoom = 10;
		 */
		this.setZoomFactor(zoom, zoomCenterAB);
		this.repaint();
	}

	private void setZoomFactor(double zoomFactor, Point2D.Double zoomCenterAB) {
		this.zoomFactor = zoomFactor;

		zoomLength = zoomFactor * referenceLength;
		zoomHeight = zoomFactor * referenceHeight;
		xmin = zoomCenterAB.x - zoomLength / 2;
		xmax = zoomCenterAB.x + zoomLength / 2;
		ymin = zoomCenterAB.y - zoomHeight / 2;
		ymax = zoomCenterAB.y + zoomHeight / 2;
		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3 && pointPanel.addingAllowed && highlightedPoint != null) {
			h.removeLine(highlightedPoint.getMyPoint());
			visualPoints.remove(highlightedPoint);
			pointPanel.refreshAll();
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		currentMouseButton = e.getButton();
		switch (mode) {
		case ZOOM_RECTANGLE:
			if (e.getButton() == MouseEvent.BUTTON1) {
				corner1 = new Point2D.Double(e.getX(), e.getY());
			} else { // dragging
				initialX = e.getX();
				initialY = e.getY();
			}
			break;
		case DRAG:
			if (e.getButton() == MouseEvent.BUTTON1) {
				initialX = e.getX();
				initialY = e.getY();
			} else { // zooming rectangle
				corner1 = new Point2D.Double(e.getX(), e.getY());
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		switch (mode) {
		case ZOOM_RECTANGLE:
			if (currentMouseButton == MouseEvent.BUTTON1) {
				corner2 = new Point2D.Double(e.getX(), e.getY());
				this.repaint();
			} else { // dragging
				int dx = e.getX() - initialX;
				int dy = e.getY() - initialY;

				initialX += dx;
				initialY += dy;
				corner1 = new Point2D.Double(-dx, -dy);
				corner2 = new Point2D.Double(this.getWidth() - dx, this.getHeight() - dy);
				doZoom();
				this.repaint();
			}
			break;
		case DRAG:
			if (currentMouseButton == MouseEvent.BUTTON1) {
				int dx = e.getX() - initialX;
				int dy = e.getY() - initialY;

				initialX += dx;
				initialY += dy;
				corner1 = new Point2D.Double(-dx, -dy);
				corner2 = new Point2D.Double(this.getWidth() - dx, this.getHeight() - dy);
				doZoom();
				this.repaint();
			} else { // zooming rectangle
				corner2 = new Point2D.Double(e.getX(), e.getY());
				this.repaint();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (mode) {
		case ZOOM_RECTANGLE:
			if (e.getButton() == MouseEvent.BUTTON1) {
				corner2 = new Point2D.Double(e.getX(), e.getY());
				doZoom();
			}
			break;
		case DRAG:
			if (e.getButton() == MouseEvent.BUTTON3) { // zoom rectangle
				corner2 = new Point2D.Double(e.getX(), e.getY());
				doZoom();
			}
		}
		currentMouseButton = -1;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void setMinAndMax(double xmin, double ymin, double xmax, double ymax) {
		this.xmin = xmin;
		this.xmax = xmax;
		this.ymin = ymin;
		this.ymax = ymax;
		this.zoomFactor = 1;
		this.referenceLength = (xmax - xmin);
		this.referenceHeight = (ymax - ymin);
		this.zoomCenterAB = new Point2D.Double((xmax + xmin) / 2, (ymax + ymin) / 2);
	}

	public void setDrawDeleted(boolean selected) {
		this.drawDeleted = selected;
	}

}
