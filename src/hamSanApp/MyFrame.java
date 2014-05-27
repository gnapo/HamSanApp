package hamSanApp;

import java.awt.*;
import javax.swing.*;

public class MyFrame extends JFrame {

	/**
	 * no Idea what this does but it makes a warning
	 */
	private static final long serialVersionUID = 1L;
	
	PointPanel pp;
	LinePanel lp;
	
	HamSanAlg h;
	
	public MyFrame(HamSanAlg hsa) {
		h = hsa;
		pp = new PointPanel(h);
		lp = new LinePanel(h);
		Container container = getContentPane();
		container.setLayout(null);
		Insets ins = container.getInsets();
		Dimension size = new Dimension(301,301);			    
	    container.add(pp);
	    lp.setBounds(10+ ins.left, 10 + ins.top, size.width, size.height);
	    container.add(lp);
	    pp.setBounds(20+ins.left+size.width, 10+ins.top, size.width, size.height);
/*		add(heading);
		add(pp);
		add(lp); */
	}
}