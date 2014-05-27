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
	
	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int xscale = 300/(xmax-xmin);
        int yscale = 300/(ymax-ymin);
        g.setColor(Color.black);
        g.drawRect(0,0,300,300);

    	g.setColor(Color.blue);
        for (int i = 0; i < h.lBlue.size(); ++i) {
        	double dx = ((h.lBlue.get(i).a)- xmin)*xscale;
        	double dy = ((-h.lBlue.get(i).b)+ ymax)*yscale;
        	drawPoint(g,(int) dx, (int) dy);
        }
        
        g.setColor(Color.red);
        for (int i = 0; i < h.lRed.size(); ++i) {
        	double dx = ((h.lRed.get(i).a)- xmin)*xscale;
        	double dy = ((-h.lRed.get(i).b)+ ymax)*yscale;
        	drawPoint(g,(int) dx, (int) dy);
        }
     }

}
