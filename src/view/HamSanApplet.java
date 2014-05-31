package view;

import hamSanApp.HamSanAlg;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
	
	HamSanAlg hsa;
	
	public HamSanApplet() {
		this.setPreferredSize(new Dimension(1200,1000));
		this.setLayout(new BorderLayout());
		
		// the dual panels
		hsa = new HamSanAlg();
		lp = new LinePanel(hsa);
		pp = new PointPanel(hsa,lp);
		lp.setPointPanel(pp);
		pp.setPreferredSize(new Dimension(this.getWidth()/2, 400));
		lp.setPreferredSize(new Dimension(this.getWidth()/2, 400));
		JPanel dualPanels = new JPanel(new GridLayout(1, 2));
		
		dualPanels.setPreferredSize(new Dimension(this.getWidth(), 800));
		dualPanels.add(pp);
		dualPanels.add(lp);
		
		// the buttons
		startAlgButton = new JButton("Do Alg");
		startAlgButton.setVisible(true);
		startAlgButton.setBounds(20,320,90,40);
	    startAlgButton.setFocusable(false);
	    startAlgButton.addActionListener(new DoAlgButtonListener(hsa, pp, lp));
		resetButton = new JButton("Reset");
		resetButton.setVisible(true);
	    resetButton.setBounds(130,320,90,40);
	    resetButton.setFocusable(false);
	    resetButton.addActionListener(new ResetButtonListener(hsa, pp));
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setLayout(new FlowLayout());
	    buttonPanel.add(startAlgButton);
	    buttonPanel.add(resetButton);
		
		this.add(dualPanels, BorderLayout.CENTER);
	    this.add(buttonPanel, BorderLayout.SOUTH);
	    this.addKeyListener(new ToggleListener(pp));
	    setFocusable(true);
	    this.requestFocus();
		
	    //this.pack();
	}

}
