package view;

import hamSanApp.HamSanAlg;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

//import java.util.Date;


public class LinePanel extends JPanel {
	/**
	 * I have no Idea what this is or why i need it
	 */
	private static final long serialVersionUID = 1L;

	public HamSanAlg h;
	public int xmin = -10;
	public int xmax = 10;
	public int ymin = -10;
	public int ymax = 10;

	LinePanel(HamSanAlg hsa) {
		h = hsa;
	}
	
	public void drawCross(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, 300, 300);
		g.setColor(Color.gray);
		g.drawLine(150,1,150,300);
		g.drawLine(1, 150, 300, 150);
	}
	
	public void drawPoint(Graphics g,int x, int y) {
		g.drawOval(x-2, y-2,4,4);
	}
	
	@Override
	public void paint(Graphics g) {
        super.paint(g);
        drawCross(g);
        int xscale = 300/(xmax-xmin); 
        int yscale = 300/(ymax-ymin);
        
        List<VisualPoint> visualPoints = h.getVisualPoints();
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
        g.drawRect(0,0,300,300);

     }

}
