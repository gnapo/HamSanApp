package view;

import java.awt.Color;
import java.awt.Graphics;

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
	
	public void draw(Graphics g, double xmin, double xmax, double ymin, double ymax) {
		double xscale = 300 / (xmax - xmin);
		double yscale = 300 / (ymax - ymin);
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
}
