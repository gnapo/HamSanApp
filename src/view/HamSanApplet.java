package view;

import hamSanApp.HamSanAlg;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;

import control.DoAlgButtonListener;
import control.ResetButtonListener;
import control.ToggleListener;

public class HamSanApplet extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6118920217871814528L;
	
	PointPanel pp;
	LinePanel lp;
	
	JButton startAlgButton;
	JButton resetButton;
	
	HamSanAlg h;
	
	public HamSanApplet() {
		h = new HamSanAlg();
		lp = new LinePanel(h);
		pp = new PointPanel(h,lp);
		lp.setPointPanel(pp);
		startAlgButton = new JButton("Do Alg");
		startAlgButton.setVisible(true);
		
		resetButton = new JButton("Reset");
		resetButton.setVisible(true);
		
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
	    startAlgButton.addActionListener(new DoAlgButtonListener(h, pp, lp));
	    
	    resetButton.setBounds(130,320,90,40);
	    resetButton.setFocusable(false);
	    resetButton.addActionListener(new ResetButtonListener(h, pp));
	    
	    this.add(startAlgButton);
	    this.add(resetButton);
	    
	    setFocusable(true);
	    
	    //this.pack();
	}

}
