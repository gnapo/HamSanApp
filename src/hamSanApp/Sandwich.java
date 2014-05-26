package hamSanApp;

//import javax.swing.*;
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
		hsa.addLine(1, 2, true);
		hsa.addLine(3, 4, false);
		hsa.addLine(6, 5, false);
		System.out.println(hsa.lBlue);
		System.out.println(hsa.lRed);
	}

}
;