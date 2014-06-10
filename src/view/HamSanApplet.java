package view;

import hamSanApp.HamSanAlg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.CoordsListener;
import control.CrossingsListener;
import control.DeletedListener;
import control.DoAlgButtonListener;
import control.DoAllgButtonListener;
import control.RandomButtonListener;
import control.ResetButtonListener;
import control.ResetZoomListener;
import control.ToggleListener;

public class HamSanApplet extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6049999518358173580L;
	
	private JButton randomButton;
	private JTextField coord1,coord2;
	private JButton setPoint;
	private JComboBox<String> presetList;
	private JButton startAlgButton;
	private JButton doAllgButton;
	
	public void paint(Graphics g) {
		super.paint(g);
		this.requestFocus();
	}

	public void init() {
		this.setPreferredSize(new Dimension(1024,600));
		this.setLayout(new BorderLayout());
		
		// the dual panels
		HamSanAlg hsa = new HamSanAlg();
		LinePanel lp = new LinePanel(hsa);
		PointPanel pp = new PointPanel(hsa,lp, this);
		lp.setPointPanel(pp);
		pp.setPreferredSize(new Dimension(this.getWidth()/2, 400));
		lp.setPreferredSize(new Dimension(this.getWidth()/2, 400));
		JPanel dualPanels = new JPanel(new GridLayout(1, 2));
		dualPanels.setPreferredSize(new Dimension(this.getWidth(), 500));
		dualPanels.add(pp);
		dualPanels.add(lp);
		
		// the label
	    JLabel infoLabel = new JLabel("step 0: place points");
	    infoLabel.setPreferredSize(new Dimension(this.getWidth(), 20));
 
	    //Preset ComboBox
	    String[] presets = {"random points (square)", "random points (circle)"};
	    presetList = new JComboBox<String>(presets);
	    presetList.setFocusable(false);
	    
		// the buttons
		startAlgButton = new JButton("Next Step");
		startAlgButton.setMnemonic(KeyEvent.VK_N);
		startAlgButton.setVisible(true);
	    startAlgButton.setFocusable(false);
	    startAlgButton.setEnabled(false);
	    DoAlgButtonListener doAlgButtonListener = new DoAlgButtonListener(hsa, pp, lp, infoLabel, this); 
	    startAlgButton.addActionListener(doAlgButtonListener);
	    JButton resetButton = new JButton("Reset");
	    resetButton.setMnemonic(KeyEvent.VK_R);
		resetButton.setVisible(true);
	    resetButton.setFocusable(false);
	    ResetButtonListener resetButtonListener = new ResetButtonListener(hsa, pp, lp, infoLabel, this); 
	    resetButton.addActionListener(resetButtonListener);
	    
	    JCheckBox crossingBox = new JCheckBox("Show crossings?");
	    crossingBox.setEnabled(true);
	    crossingBox.setSelected(false); 
	    crossingBox.setVisible(true);
	    crossingBox.setFocusable(false);
	    crossingBox.addActionListener(new CrossingsListener(crossingBox, lp));
	    
	    JCheckBox deletedBox = new JCheckBox("Show deleted lines?");
	    deletedBox.setEnabled(true);
	    deletedBox.setSelected(true); 
	    deletedBox.setVisible(true);
	    deletedBox.setFocusable(false);
	    deletedBox.addActionListener(new DeletedListener(deletedBox, lp));
	    
	    doAllgButton = new JButton("All steps");
	    doAllgButton.setMnemonic(KeyEvent.VK_A);
	    doAllgButton.setVisible(true);
	    doAllgButton.setEnabled(false);
	    doAllgButton.setFocusable(false);
	    DoAllgButtonListener doAllgButtonListener = new DoAllgButtonListener(hsa, pp, lp, infoLabel, this); 
	    doAllgButton.addActionListener(doAllgButtonListener);
	    
	    JButton resetZoomButton = new JButton("Reset Zoom");
	    resetZoomButton.setFocusable(false);
	    resetZoomButton.addActionListener(new ResetZoomListener(lp));
	    
	    randomButton = new JButton("Add Points");
	    randomButton.setMnemonic(KeyEvent.VK_P);
	    RandomButtonListener randomButtonListener = new RandomButtonListener(hsa,lp,pp,presetList, this); 
	    randomButton.addActionListener(randomButtonListener);
	    randomButton.setFocusable(false);
	    
	    //coordinates of points
	    coord1 = new JTextField(3);
	    coord2 = new JTextField(3);
	    setPoint = new JButton("Setze Punkt an Koordinaten");
		setPoint.setVisible(true);
	    setPoint.setFocusable(false);
	    setPoint.addActionListener(new CoordsListener(this,pp,coord1, coord2));
	    
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setLayout(new GridLayout(2, 1));
	    
	    buttonPanel.add(startAlgButton);
	    buttonPanel.add(doAllgButton);
	    buttonPanel.add(resetButton);
	    buttonPanel.add(crossingBox);
	    buttonPanel.add(deletedBox);
	    buttonPanel.add(resetZoomButton);
	    buttonPanel.add(randomButton);
	    buttonPanel.add(setPoint);//Koordinateneingabe
	    buttonPanel.add(coord1);
	    buttonPanel.add(coord2);
	    buttonPanel.add(presetList);
	    
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
	
	public void setPlacingEnabled(boolean enabled) {
		randomButton.setEnabled(enabled);
		coord1.setEnabled(enabled);
		coord2.setEnabled(enabled);
		setPoint.setEnabled(enabled);
		presetList.setEnabled(enabled);
	}

	public void setStepsEnabled(boolean enabled) {
		startAlgButton.setEnabled(enabled);
		doAllgButton.setEnabled(enabled);
	}

}
