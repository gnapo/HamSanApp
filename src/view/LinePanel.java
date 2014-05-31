package view;

import hamSanApp.HamSanAlg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JPanel;

//import java.util.Date;


public class LinePanel extends JPanel implements MouseMotionListener {
	/**
	 * I have no Idea what this is or why i need it
	 */
	private static final long serialVersionUID = 1L;

	public HamSanAlg h;
	public int xmin = -10;
	public int xmax = 10;
	public int ymin = -10;
	public int ymax = 10;
	
	private PointPanel pointPanel;
	
	private List<VisualPoint> visualPoints;

	LinePanel(HamSanAlg hsa) {
		h = hsa;
		visualPoints = hsa.getVisualPoints();
		this.addMouseMotionListener(this);
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
		g.drawLine(0, this.getHeight()/2, this.getWidth(), this.getHeight()/2);
		g.drawLine(this.getWidth()/2, 0, this.getWidth()/2, this.getHeight());
	}
	
	public void drawPoint(Graphics g,int x, int y) {
		g.drawOval(x-2, y-2,4,4);
	}
	
	@Override
	public void paint(Graphics g) {
        super.paint(g);
        drawCross(g);
        int xscale = this.getWidth()/(xmax-xmin); 
        int yscale = this.getHeight()/(ymax-ymin);
        
        for (VisualPoint p : visualPoints) {
        	p.drawAsLine(g, xmin, xmax, ymin, ymax, this.getSize());
        }
        
        g.setColor(Color.gray);
        /*Date d = new Date();
        long upto = d.getSeconds() % h.crossings.size();
        System.out.println(upto);
        System.out.println(h.crossings.size());//*/
        for (int i = 0; i < h.crossings.size(); ++i) {
        	if (h.crossings.get(i).atInf()) {
        		continue;
        		}
        	double dx = ((h.crossings.get(i).crAt())- xmin)*xscale;
        	double dy = ((-h.crossings.get(i).a.eval(h.crossings.get(i).crAt()))+ ymax)*yscale;
        	drawPoint(g,(int) dx, (int) dy);
        }
        g.setColor(Color.black);
        g.drawRect(0, 0, this.getWidth(), this.getHeight());

     }

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (VisualPoint v : visualPoints) {
			v.highlighted = false;
		}
		
		for (VisualPoint v : visualPoints) {
			if (v.containsCursorLine(e.getX(), e.getY(), xmin, xmax, ymin, ymax, this.getSize())) {
				v.highlighted = true;
				pointPanel.setVisualPoints(visualPoints);
				break;
			}
		}
		
		this.revalidate();
		this.repaint();
		pointPanel.revalidate();
		pointPanel.repaint();
	}

}
