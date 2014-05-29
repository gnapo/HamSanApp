package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class VisualPoint {
	private double a;
	private double b;
	private PointType type;
	public boolean deleted; // points not used in the algorithm
	public boolean highlighted;
	
	public VisualPoint(double a, double b, PointType type, boolean deleted) {
		this.a = a;
		this.b = b;
		this.type = type;
		this.deleted = deleted;
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
	
	public void drawAsPoint(Graphics g, double xmin, double xmax, double ymin, double ymax, Dimension componentSize) {
		double xscale = componentSize.getWidth() / (xmax - xmin);
		double yscale = componentSize.getHeight() / (ymax - ymin);
		double xd = (a - xmin) * xscale;
		double yd = (-b + ymax) * yscale;
		int x = (int) xd;
		int y = (int) yd;
		
		if (this.type == PointType.BLUE) {
			if (this.highlighted) {
				g.setColor(Color.blue.brighter());
			} else {
				g.setColor(Color.blue.darker());
			}
		} else if (this.type == PointType.RED) {
			if (this.highlighted) {
				g.setColor(Color.red.brighter());
			} else {
				g.setColor(Color.red.darker());
			}
		} else {
			throw new IllegalStateException("Invalid point type.");
		}
		
		if (this.deleted) {
			g.drawOval(x - 2, y - 2, 4, 4);
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
				g.setColor(Color.blue.brighter());
			} else {
				g.setColor(Color.blue.darker());
			}
		} else if (this.type == PointType.RED) {
			if (this.highlighted) {
				g.setColor(Color.red.brighter());
			} else {
				g.setColor(Color.red.darker());
			}
		} else {
			throw new IllegalStateException("Invalid point type.");
		}
		
		if (this.deleted) {
			//g.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
			g.drawLine(0,(int) dy1,(int) componentSize.getWidth(), (int) dy2);
		} else {
			g.setStroke(new BasicStroke());
        	g.drawLine(0,(int) dy1,(int) componentSize.getWidth(), (int) dy2);
		}
	}
}
