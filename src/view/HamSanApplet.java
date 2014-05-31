package view;

import hamSanApp.HamSanAlg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import control.CrossingsListener;
import control.DoAlgButtonListener;
import control.ResetButtonListener;
import control.ToggleListener;

public class HamSanApplet extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6049999518358173580L;

	public void init() {
		this.setPreferredSize(new Dimension(1200,1000));
		this.setLayout(new BorderLayout());
		
		// the dual panels
		HamSanAlg hsa = new HamSanAlg();
		LinePanel lp = new LinePanel(hsa);
		PointPanel pp = new PointPanel(hsa,lp);
		lp.setPointPanel(pp);
		pp.setPreferredSize(new Dimension(this.getWidth()/2, 400));
		lp.setPreferredSize(new Dimension(this.getWidth()/2, 400));
		JPanel dualPanels = new JPanel(new GridLayout(1, 2));
		dualPanels.setPreferredSize(new Dimension(this.getWidth(), 800));
		dualPanels.add(pp);
		dualPanels.add(lp);
		
		// the buttons
		JButton startAlgButton = new JButton("Next Step");
		startAlgButton.setVisible(true);
		startAlgButton.setBounds(20,320,90,40);
	    startAlgButton.setFocusable(false);
	    startAlgButton.addActionListener(new DoAlgButtonListener(hsa, pp, lp));
	    JButton resetButton = new JButton("Reset");
		resetButton.setVisible(true);
	    resetButton.setBounds(130,320,90,40);
	    resetButton.setFocusable(false);
	    resetButton.addActionListener(new ResetButtonListener(hsa, pp, lp));
	    JCheckBox crossingBox = new JCheckBox("Show crossings?");
	    crossingBox.setEnabled(true);
	    crossingBox.setSelected(true);
	    crossingBox.setVisible(true);
	    crossingBox.setFocusable(false);
	    crossingBox.addActionListener(new CrossingsListener(crossingBox, lp));
	    
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setLayout(new FlowLayout());
	    buttonPanel.add(startAlgButton);
	    buttonPanel.add(resetButton);
	    buttonPanel.add(crossingBox);
	    
	    // the step label
	    JLabel infoLabel = new JLabel("Step 0: Place the points! Yes! Now! Place them!");
	    infoLabel.setPreferredSize(new Dimension(this.getWidth(), 20));
	    
	    JPanel buttonsAndLabel = new JPanel();
	    buttonsAndLabel.setLayout(new GridLayout(2,1));
	    buttonsAndLabel.add(buttonPanel);
	    buttonsAndLabel.add(infoLabel);
		
		this.add(dualPanels, BorderLayout.CENTER);
	    this.add(buttonsAndLabel, BorderLayout.SOUTH);
	    this.addKeyListener(new ToggleListener(pp));
	    setFocusable(true);
	    this.requestFocus();
	}

}
