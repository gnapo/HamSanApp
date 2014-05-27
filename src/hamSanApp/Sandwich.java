package hamSanApp;

import java.awt.Insets;

//import java.awt.*;
import javax.swing.*;


	/**
	 * diese Klasse verwaltet alles: (HamSanAlg und Gui) und hat die main-methode. 
	 * @author fabian
	 *
	 */
public class Sandwich {
	/*
	private static void makeGui() {
		JFrame frame = new JFrame("draw thingie");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel("hiya!");
        frame.getContentPane().add(label);
        
        frame.pack();
        frame.setVisible(true);
        
	}*/

	public static void main(String[] args) {
		//tests go here.
		HamSanAlg hsa = new HamSanAlg();
		hsa.addLine(0, 0, false);
		hsa.addLine(2, 0, false);
		hsa.addLine(0, 1, true);
		hsa.addLine(0, 2, true);
		hsa.addLine(1, 0, true);
		MyFrame f = new MyFrame(hsa);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Insets insets = f.getInsets();
		f.setSize(680 + insets.left + insets.right, 400 + insets.top + insets.bottom);
		f.setVisible(true);
	}

}
;