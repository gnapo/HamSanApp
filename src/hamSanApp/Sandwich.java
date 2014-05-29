package hamSanApp;

import java.awt.Insets;
import java.util.ArrayList;

import java.util.List;

//import java.awt.*;
import javax.swing.*;


	/**
	 * diese Klasse verwaltet alles: (HamSanAlg und Gui) und hat die main-methode. 
	 * @author fabian
	 *
	 */
public class Sandwich {
	
	public static void main(String[] args) {
		//tests go here.
		HamSanAlg hsa = new HamSanAlg();
		
		MyFrame f = new MyFrame(hsa);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Insets insets = f.getInsets();
		f.setSize(680 + insets.left + insets.right, 400 + insets.top + insets.bottom);
		f.setVisible(true);
		hsa.addLine((Math.random() * 5)-2.5, (Math.random() * 15)-7.5, false);
		hsa.addLine((Math.random() * 5)-2.5, (Math.random() * 15)-7.5, false);
		hsa.addLine((Math.random() * 5)-2.5, (Math.random() * 15)-7.5, false);
		hsa.addLine((Math.random() * 5)-2.5, (Math.random() * 15)-7.5, true);
		hsa.addLine((Math.random() * 5)-2.5, (Math.random() * 15)-7.5, true);
		hsa.addLine((Math.random() * 5)-2.5, (Math.random() * 15)-7.5, true);
		
		System.out.println(hsa.lBlue);
		List<Point> c = new ArrayList<Point>(hsa.lBlue);
		System.out.println(c);
		hsa.doAlg();
		
		
		Point a = new Point(1,0);
		Point b = new Point(0,1);
		Point.value = 1;
		System.out.println(b.compareTo(a));
	}

}
;