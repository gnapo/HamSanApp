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
import control.OldpointsResetButtonListener;
import control.RandomButtonListener;
import control.ResetButtonListener;
import control.ResetZoomListener;
import control.ToggleColorButtonListener;
import control.ToggleListener;
import control.ZoomDragListener;

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
	    //infoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
	    infoLabel.setPreferredSize(new Dimension(this.getWidth(), 20));
	    
	    // colour labels
	    JLabel colourlabel = new JLabel("Color: blue - space to change");
	    colourlabel.setText("<html>Color: <font color='blue'>blue</font> - space to change</html>");
	    //colourlabel.setPreferredSize(new Dimension(this.getWidth(), 20));
	    JButton colorButton = new JButton("Toggle Color");
	    colorButton.setFocusable(false);
	    colorButton.addActionListener(new ToggleColorButtonListener(pp, colourlabel));
	    colourlabel.setAlignmentX(10);
 
	    //Preset ComboBox
	    String[] presets = {"random points (square)", "random points (circle)","random paralel lines",
	    		"single random points","all Points on one side", "vertical solution", "only one color",
	    		"collinear case", "multiple solutions(1)","multiple solutions(2)","early stop"};

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
	    
	    JButton oldpointsresetButton = new JButton("Start again");
	    oldpointsresetButton.setMnemonic(KeyEvent.VK_S);
	    oldpointsresetButton.setVisible(true);
	    oldpointsresetButton.setFocusable(false);
	    OldpointsResetButtonListener OldpointsresetButtonListener = new OldpointsResetButtonListener(hsa, pp, lp, infoLabel, this); 
	    oldpointsresetButton.addActionListener(OldpointsresetButtonListener);

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
	    
	    JButton resetZoomButton = new JButton("Reset view");
	    resetZoomButton.setFocusable(false);
	    ResetZoomListener resetZoomListener = new ResetZoomListener(lp);
	    resetZoomButton.addActionListener(resetZoomListener);
	    resetZoomButton.setMnemonic(KeyEvent.VK_V);
	    
	    randomButton = new JButton("Add points");
	    randomButton.setMnemonic(KeyEvent.VK_P);
	    RandomButtonListener randomButtonListener = new RandomButtonListener(hsa,lp,pp,presetList, this); 
	    randomButton.addActionListener(randomButtonListener);
	    randomButton.setFocusable(false);
	    
	    //coordinates of points
	    JLabel xLabel = new JLabel("x:");
	    JLabel yLabel = new JLabel("y:");
	    coord1 = new JTextField(3);
	    coord2 = new JTextField(3);
	    setPoint = new JButton("Place point at");
		setPoint.setVisible(true);
	    setPoint.setFocusable(false);
	    setPoint.addActionListener(new CoordsListener(this,pp,coord1, coord2));
	    
	    JPanel xPanel = new JPanel();
	    xPanel.add(xLabel);
	    xPanel.add(coord1);
	    
	    JPanel yPanel = new JPanel();
	    yPanel.add(yLabel);
	    yPanel.add(coord2);
	    
	    
	    JPanel lowerPanel = new JPanel();
	    lowerPanel.setLayout(new GridLayout(1, 2));
	    
	    JPanel neccessaryPanel = new JPanel();
	    neccessaryPanel.setLayout(new GridLayout(3, 1));
	    
	    JPanel algPanel = new JPanel();
	    algPanel.add(startAlgButton);
	    algPanel.add(doAllgButton);
	    algPanel.add(resetButton);
	    algPanel.add(oldpointsresetButton);
	    neccessaryPanel.add(algPanel);
	    
	    JPanel colorPanel = new JPanel();
	    colorPanel.add(colourlabel);
	    colorPanel.add(colorButton);
	    neccessaryPanel.add(colorPanel);
	    
	    neccessaryPanel.add(infoLabel);
	    
	    JPanel optionalPanel = new JPanel();
	    optionalPanel.setLayout(new GridLayout(3, 1));
	    
	    JPanel visualPanel = new JPanel();
	    visualPanel.add(crossingBox);
	    visualPanel.add(deletedBox);
	    visualPanel.add(resetZoomButton);
	    
	    optionalPanel.add(visualPanel);
	    
	    JPanel placeRandomPanel = new JPanel();
	    placeRandomPanel.add(presetList);
	    placeRandomPanel.add(randomButton);
	    
	    optionalPanel.add(placeRandomPanel);
	    
	    JPanel placeCoordinatesPanel = new JPanel();
	    placeCoordinatesPanel.add(xPanel);
	    placeCoordinatesPanel.add(yPanel);
	    placeCoordinatesPanel.add(setPoint);//Koordinateneingabe
	    
	    optionalPanel.add(placeCoordinatesPanel);
	    
	    
	    lowerPanel.add(neccessaryPanel);
	    lowerPanel.add(optionalPanel);
		
		this.add(dualPanels, BorderLayout.CENTER);
	    this.add(lowerPanel, BorderLayout.SOUTH);
	    this.addKeyListener(new ToggleListener(colourlabel,pp,doAlgButtonListener,doAllgButtonListener,resetButtonListener,randomButtonListener, OldpointsresetButtonListener, resetZoomListener));
		this.addKeyListener(new ZoomDragListener(lp));

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
	
	public void setSize(int width, int height)
	{
	   super.setSize(width,height);
	   validate();
	}

}
