package hamSanApp;

import javax.swing.*;
import java.awt.*;

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
	
	
	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //int xscale = 300/(xmaxﬂ-xmin); //wird wohl nicht gebraucht?
        int yscale = 300/(ymax-ymin);
        g.setColor(Color.black);
        g.drawRect(0,0,300,300);

    	g.setColor(Color.blue);
        for (int i = 0; i < h.lBlue.size(); ++i) {
        	double y1 = h.lBlue.get(i).eval(xmin);
        	double y2 = h.lBlue.get(i).eval(xmax);

        	double dy1 = ((-y1)+ ymax)*yscale;
        	double dy2 = ((-y2)+ ymax)*yscale;
        	
        	g.drawLine(0,(int) dy1,300, (int) dy2);
        }
        
        g.setColor(Color.red);
        for (int i = 0; i < h.lRed.size(); ++i) {
        	double y1 = h.lRed.get(i).eval(xmin);
        	double y2 = h.lRed.get(i).eval(xmax);

        	double dy1 = ((-y1)+ ymax)*yscale;
        	double dy2 = ((-y2)+ ymax)*yscale;
        	
        	g.drawLine(0,(int) dy1,300, (int) dy2);
        }
     }

}
