package view;

import hamSanApp.HamSanAlg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.CoordsListener;
import control.CrossingsListener;
import control.DoAlgButtonListener;
import control.DoAllgButtonListener;
import control.RandomButtonListener;
import control.ResetButtonListener;
import control.ResetZoomListener;
import control.ToggleListener;
import control.VerifyButtonListener;

public class HamSanApplet extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6049999518358173580L;
	
	public void paint(Graphics g) {
		super.paint(g);
		this.requestFocus();
	}

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
		
		// the step label
	    JLabel infoLabel = new JLabel("step 0: place points");
	    infoLabel.setPreferredSize(new Dimension(this.getWidth(), 20));
 
		// the buttons
		JButton startAlgButton = new JButton("Next Step");
		startAlgButton.setMnemonic(KeyEvent.VK_N);
		startAlgButton.setVisible(true);
	    startAlgButton.setFocusable(false);
	    DoAlgButtonListener doAlgButtonListener = new DoAlgButtonListener(hsa, pp, lp, infoLabel); 
	    startAlgButton.addActionListener(doAlgButtonListener);
	    JButton resetButton = new JButton("Reset");
	    resetButton.setMnemonic(KeyEvent.VK_R);
		resetButton.setVisible(true);
	    resetButton.setFocusable(false);
	    ResetButtonListener resetButtonListener = new ResetButtonListener(hsa, pp, lp, infoLabel); 
	    resetButton.addActionListener(resetButtonListener);
	    JCheckBox crossingBox = new JCheckBox("Show crossings?");
	    crossingBox.setEnabled(true);
	    crossingBox.setSelected(true); 
	    crossingBox.setVisible(true);
	    crossingBox.setFocusable(false);
	    crossingBox.addActionListener(new CrossingsListener(crossingBox, lp));
	    JButton doAllgButton = new JButton("All steps");
	    doAllgButton.setMnemonic(KeyEvent.VK_A);
	    doAllgButton.setVisible(true);
	    doAllgButton.setFocusable(false);
	    DoAllgButtonListener doAllgButtonListener = new DoAllgButtonListener(hsa, pp, lp, infoLabel); 
	    doAllgButton.addActionListener(doAllgButtonListener);
	    
	    JButton resetZoomButton = new JButton("Reset Zoom");
	    resetZoomButton.setFocusable(false);
	    resetZoomButton.addActionListener(new ResetZoomListener(lp));
	    
	    JButton verifyButton = new JButton("Verify solution");
	    verifyButton.addActionListener(new VerifyButtonListener(hsa));
	    verifyButton.setFocusable(false); 
	    
	    JButton randomButton = new JButton("Add Points");
	    randomButton.setMnemonic(KeyEvent.VK_P);
	    RandomButtonListener randomButtonListener = new RandomButtonListener(hsa,lp,pp); 
	    randomButton.addActionListener(randomButtonListener);
	    randomButton.setFocusable(false);
	    
	    //coordinates of points
	    JTextField coord1 = new JTextField(3);
	    JTextField coord2 = new JTextField(3);
	    JButton setPoint = new JButton("Setze Punkt an Koordinaten");
		setPoint.setVisible(true);
	    setPoint.setFocusable(false);
	    setPoint.addActionListener(new CoordsListener(this,pp,coord1, coord2));
	    
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setLayout(new FlowLayout());
	    buttonPanel.add(startAlgButton);
	    buttonPanel.add(doAllgButton);
	    buttonPanel.add(resetButton);
	    buttonPanel.add(crossingBox);
	    buttonPanel.add(resetZoomButton);
	    buttonPanel.add(randomButton);
	    buttonPanel.add(verifyButton);
	    buttonPanel.add(setPoint);//Koordinateneingabe
	    buttonPanel.add(coord1);
	    buttonPanel.add(coord2);
	    
	    
	    JPanel buttonsAndLabel = new JPanel();
	    buttonsAndLabel.setLayout(new GridLayout(2,1));
	    buttonsAndLabel.add(buttonPanel);
	    buttonsAndLabel.add(infoLabel);
		
		this.add(dualPanels, BorderLayout.CENTER);
	    this.add(buttonsAndLabel, BorderLayout.SOUTH);
	    this.addKeyListener(new ToggleListener(pp,doAlgButtonListener,doAllgButtonListener,resetButtonListener,randomButtonListener));
	    setFocusable(true);
	    this.requestFocusInWindow();
	    this.requestFocus();
	}

}
