package view;

import hamSanApp.HamSanAlg;

import java.awt.*;

import javax.swing.*;

import control.ButtonListener;
import control.ToggleListener;

public class MyFrame extends JFrame {

	/**
	 * no Idea what this does but it makes a warning
	 */
	private static final long serialVersionUID = 1L;
	
	PointPanel pp;
	LinePanel lp;
	
	JButton startAlgButton;
	
	HamSanAlg h;
	
	public MyFrame(HamSanAlg hsa) {
		h = hsa;
		lp = new LinePanel(h);
		pp = new PointPanel(h,lp);
		lp.setPointPanel(pp);
		startAlgButton = new JButton("Do Alg");
		startAlgButton.setVisible(true);
		
		JPanel dualPanels = new JPanel(new BorderLayout());
		
		dualPanels.add(lp, BorderLayout.WEST);
		dualPanels.add(pp, BorderLayout.EAST);
		
		this.addKeyListener(new ToggleListener(pp));
		this.setLayout(new FlowLayout());
		
		Container container = getContentPane();
		container.setLayout(null);
		
		Insets ins = container.getInsets();
		Dimension size = new Dimension(301,301);			    
		
	    this.add(pp);
	    lp.setBounds(10+ ins.left, 10 + ins.top, size.width, size.height);
	    this.add(lp);
	    pp.setBounds(20+ins.left+size.width, 10+ins.top, size.width, size.height);
	    startAlgButton.setBounds(20,320,90,40);
	    
	    startAlgButton.setFocusable(false);
	    startAlgButton.addActionListener(new ButtonListener(hsa, pp, lp));
	    
	    this.add(startAlgButton);
	    
	    setFocusable(true);
	    
	    this.pack();
	   
	    //this.add(dualPanels);
	    //this.add(startAlgButton);
	    
	    //container.add(startAlgButton);
	}
}