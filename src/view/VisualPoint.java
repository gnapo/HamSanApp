package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class VisualPoint {
	private double a;
	private double b;
	private PointType type;
	public boolean deleted; // points not used in the algorithm
	public boolean highlighted;
	private hamSanApp.Point myPoint;
	
	public VisualPoint(double a, double b, PointType type, boolean deleted) {
		this.a = a;
		this.b = b;
		this.type = type;
		this.deleted = deleted;
		this.myPoint = null;
	}
	
	public VisualPoint(double a, double b, PointType type, boolean deleted, hamSanApp.Point p) {
		this.a = a;
		this.b = b;
		this.type = type;
		this.deleted = deleted;
		this.myPoint = p;
	}
	
	public hamSanApp.Point getMyPoint() {
		return myPoint;
	}
	
	public void setMyPoint(hamSanApp.Point p) {
		this.myPoint = p;
	}
	
	/**
	 * does not compare type!!!
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !(o instanceof VisualPoint)) {
			return false;
		}
		VisualPoint other = (VisualPoint) o;
		return (this.a == other.a && this.b == other.b);
	}
	
	public boolean containsCursorPoint(int x, int y, double xmin, double xmax, double ymin, double ymax, Dimension componentSize) {
		double xscale = componentSize.getWidth() / (xmax - xmin);
		double yscale = componentSize.getHeight() / (ymax - ymin);
		
		double xd = (a - xmin) * xscale;
		double yd = (-b + ymax) * yscale;
		int xa = (int) xd;
		int yb = (int) yd;
		
		double deltaX = Math.abs(x-xa);
		double deltaY = Math.abs(y-yb);
		
		if (deltaX < 2 && deltaY < 2) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Point2D.Double toXY(Point2D.Double pointAB, double xmin, double ymin, double xmax, double ymax, Dimension componentSize) {
		double xscale = componentSize.getWidth() / (xmax - xmin);
		double yscale = componentSize.getHeight() / (ymax - ymin);
		double pointX = (pointAB.x - xmin) * xscale;
		double pointY = (-pointAB.y + ymax) * yscale;
		Point2D.Double p = new Point2D.Double(pointX, pointY);
		return p;
	}
	
	public static double aToX(double value, double xmin, double xmax, Dimension componentSize) {
		double xscale = componentSize.getWidth() / (xmax - xmin);
		double asX = (value - xmin) * xscale;
		return asX;
	}
	
	public static double xToA(double value, double xmin, double xmax, Dimension componentSize) {
		double xscale = componentSize.getWidth() / (xmax - xmin);
		double asA = ((double) value / xscale) + xmin;
		return asA;
	}
	
	public static Point2D.Double toAB(Point2D.Double pointXY, double xmin, double ymin, double xmax, double ymax, Dimension componentSize) {
		double xscale = componentSize.getWidth() / (xmax - xmin);
		double yscale = componentSize.getHeight() / (ymax - ymin);
		
		double a = ((double) pointXY.x / xscale) + xmin;
		double b = ((double) -pointXY.y / yscale) - ymin;
		Point2D.Double p = new Point2D.Double(a, b);
		return p;
	}
	
	public void setXY(Point2D.Double pointXY, double xmin, double ymin, double xmax, double ymax, Dimension componentSize) {
		Point2D.Double pointAB = toAB(pointXY, xmin, ymin, xmax, ymax, componentSize);
		this.a = pointAB.x;
		this.b = pointAB.y;
	}
	
	public double evaluate(double value) {
		return a*value+b;
	}
	
	public boolean containsCursorLine(int x, int y, double xmin, double xmax, double ymin, double ymax, Dimension componentSize) {
		
		// Linie ausgewertet an der Stelle x = 0
		double a0 = xToA(0, xmin, xmax, componentSize);
		double b0 = evaluate(a0);
		Point2D.Double inABCoords0 = new Point2D.Double(a0,b0);
		Point2D.Double inXYCoords0 = toXY(inABCoords0, xmin, ymin, xmax, ymax, componentSize);
		
		// Linie ausgewertet an der Stelle x = 42
		double a1 = xToA(42, xmin, xmax, componentSize);
		double b1 = evaluate(a1);
		Point2D.Double inABCoords1 = new Point2D.Double(a1,b1);
		Point2D.Double inXYCoords1 = toXY(inABCoords1, xmin, ymin, xmax, ymax, componentSize);
		
		Line2D line = new Line2D.Double(inXYCoords0.getX(), inXYCoords0.getY(), inXYCoords1.getX(), inXYCoords1.getY());
		double dist = line.ptLineDist(x, y);
		
		if (dist < 2) {
			return true;
		} else {
			return false;
		}
	}
	
	public void drawAsPoint(Graphics g, double xmin, double xmax, double ymin, double ymax, Dimension componentSize) {	
		double xscale = componentSize.getWidth() / (xmax - xmin);
		double yscale = componentSize.getHeight() / (ymax - ymin);
		double xd = (a - xmin) * xscale;
		double yd = (-b + ymax) * yscale;
		int x = (int) xd;
		int y = (int) yd;
		
		if (this.type == PointType.BLUE) {
			if (this.highlighted) {
				g.setColor(Color.cyan);
			} else {
				g.setColor(Color.blue);
			}
		} else if (this.type == PointType.RED) {
			if (this.highlighted) {
				g.setColor(Color.orange);
			} else {
				g.setColor(Color.red);
			}
		} else {
			throw new IllegalStateException("Invalid point type.");
		}
		
		if (this.deleted) {
			g.drawOval(x - 3, y - 3, 6, 6);
		} else {
			g.fillOval(x - 3, y - 3, 6, 6);
		}
	}
	
	public void drawAsLine(Graphics graphics, double xmin, double xmax, double ymin, double ymax, Dimension componentSize) {
		Graphics2D g = (Graphics2D) graphics;
		
		double xscale = componentSize.getWidth() / (xmax - xmin);
		double yscale = componentSize.getHeight() / (ymax - ymin);
		
		double y1 = a*xmin + b;
    	double y2 = a*xmax + b;

    	double dy1 = ((-y1)+ ymax)*yscale;
    	double dy2 = ((-y2)+ ymax)*yscale;
		
		if (this.type == PointType.BLUE) {
			if (this.highlighted) {
				g.setColor(Color.cyan);
			} else {
				g.setColor(Color.blue);
			}
		} else if (this.type == PointType.RED) {
			if (this.highlighted) {
				g.setColor(Color.orange);
			} else {
				g.setColor(Color.red);
			}
		} else {
			throw new IllegalStateException("Invalid point type.");
		}
		
		if (this.deleted) {
			g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
			g.drawLine(0,(int) dy1,(int) componentSize.getWidth(), (int) dy2);
			g.setStroke(new BasicStroke());
		} else {
        	g.drawLine(0,(int) dy1,(int) componentSize.getWidth(), (int) dy2);
		}
	}

	public double getA() {
		return this.a;
	}
	
	public double getB() {
		return this.b;
	}

	public boolean isBlue() {
		return this.type == PointType.BLUE;
	}
}
