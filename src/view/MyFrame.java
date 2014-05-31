package view;

import hamSanApp.HamSanAlg;

import java.awt.*;

import javax.swing.*;

import control.DoAlgButtonListener;
import control.ResetButtonListener;
import control.ToggleListener;

public class MyFrame extends JFrame {

	/**
	 * no Idea what this does but it makes a warning
	 */
	private static final long serialVersionUID = 1L;
	
	PointPanel pp;
	LinePanel lp;
	
	JButton startAlgButton;
	JButton resetButton;
	
	HamSanAlg h;
	
	public MyFrame(HamSanAlg hsa) {
		this.setPreferredSize(new Dimension(1200,1000));
		this.setLayout(new BorderLayout());
		
		// the dual panels
		h = hsa;
		lp = new LinePanel(h);
		pp = new PointPanel(h,lp);
		lp.setPointPanel(pp);
		pp.setPreferredSize(new Dimension(this.getWidth()/2, 400));
		lp.setPreferredSize(new Dimension(this.getWidth()/2, 400));
		JPanel dualPanels = new JPanel(new GridLayout(1, 2));
		
		dualPanels.setPreferredSize(new Dimension(this.getWidth(), 800));
		dualPanels.add(lp);
		dualPanels.add(pp);
		
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
	    resetButton.addActionListener(new ResetButtonListener(hsa, pp, lp));
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setLayout(new FlowLayout());
	    buttonPanel.add(startAlgButton);
	    buttonPanel.add(resetButton);
		
		this.add(dualPanels, BorderLayout.CENTER);
	    this.add(buttonPanel, BorderLayout.SOUTH);
	    this.addKeyListener(new ToggleListener(pp));
	    setFocusable(true);
	    
	    this.pack();
	}
}