package hamSanApp;

import javax.swing.*;
import java.awt.*;

public class PointPanel extends JPanel {
	/**
	 * I have no Idea what this is or why i need it
	 */
	private static final long serialVersionUID = 1L;

	public HamSanAlg h;
	public int xmin = -10;
	public int xmax = 10;
	public int ymin = -10;
	public int ymax = 10;
	
	PointPanel(HamSanAlg hsa) {
		h = hsa;
	}
	
	public void drawPoint(Graphics g,int x, int y) {
		g.drawOval(x-2, y-2,4,4);
	}
	
	public void fillPoint(Graphics g,int x, int y) {
		g.fillOval(x-3, y-3,6,6);
	}
	
	public void drawCross(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, 300, 300);
		g.setColor(Color.gray);
		g.drawLine(150,1,150,300);
		g.drawLine(1, 150, 300, 150);
	}
	
	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCross(g);
        int xscale = 300/(xmax-xmin);
        int yscale = 300/(ymax-ymin);
    
        g.setColor(Color.blue.brighter());
        for (int i = 0; i < h.lBlueDel.size(); ++i) {
        	double dx = ((h.lBlueDel.get(i).a)- xmin)*xscale;
        	double dy = ((-h.lBlueDel.get(i).b)+ ymax)*yscale;
        	drawPoint(g,(int) dx, (int) dy);
        }
        
        g.setColor(Color.red.brighter());
        for (int i = 0; i < h.lRedDel.size(); ++i) {
        	double dx = ((h.lRedDel.get(i).a)- xmin)*xscale;
        	double dy = ((-h.lRedDel.get(i).b)+ ymax)*yscale;
        	drawPoint(g,(int) dx, (int) dy);
        }
        
        g.setColor(Color.blue);
        for (int i = 0; i < h.lBlue.size(); ++i) {
        	double dx = ((h.lBlue.get(i).a)- xmin)*xscale;
        	double dy = ((-h.lBlue.get(i).b)+ ymax)*yscale;
        	fillPoint(g,(int) dx, (int) dy);
        }
        
        g.setColor(Color.red);
        for (int i = 0; i < h.lRed.size(); ++i) {
        	double dx = ((h.lRed.get(i).a)- xmin)*xscale;
        	double dy = ((-h.lRed.get(i).b)+ ymax)*yscale;
        	fillPoint(g,(int) dx, (int) dy);
        }
        
        g.setColor(Color.magenta);
        if (h.done){
        	if (h.verticalSol) {
        		double dx = (h.verticalSolPos-xmin)*xscale;
        		g.drawLine((int)dx, 1,(int) dx, 300);
        	}
        	else {
        		double y1 = h.solution.eval(xmin);
        		double y2 = h.solution.eval(xmax);

        		double dy1 = ((-y1)+ ymax)*yscale;
        		double dy2 = ((-y2)+ ymax)*yscale;
        	
        		g.drawLine(0,(int) dy1,300, (int) dy2);
        	}
        }
        g.setColor(Color.black);
        g.drawRect(0,0,300,300);

     }

}
