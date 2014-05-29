package view;

import hamSanApp.HamSanAlg;

import java.awt.*;

import javax.swing.*;

import control.ToggleListener;

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
		lp = new LinePanel(h);
		pp = new PointPanel(h,lp);
		
		this.addKeyListener(new ToggleListener(pp));
		
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