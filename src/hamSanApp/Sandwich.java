package hamSanApp;

import java.awt.Insets;


//import java.awt.*;
import javax.swing.JFrame;

import view.MyFrame;


	/**
	 * diese Klasse verwaltet alles: (HamSanAlg und Gui) und hat die main-methode. 
	 * @author fabian
	 *
	 */
public class Sandwich {
	
	static public void randomlines() 
	{
		HamSanAlg hsa = new HamSanAlg();
	
		MyFrame f = new MyFrame(hsa);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Insets insets = f.getInsets();
		f.setSize(680 + insets.left + insets.right, 400 + insets.top + insets.bottom);
		f.setVisible(true);
		
		for (int i = 0; i< 2; ++i) {
			hsa.addLine((Math.random() * 5)-2.5, (Math.random() * 15)-7.5, false);
			hsa.addLine((Math.random() * 5)-2.5, (Math.random() * 15)-7.5, true);
		}
		//System.out.println(hsa.lBlue);
		//System.out.println(hsa.lRed);
		//List<Point> c = new ArrayList<Point>(hsa.lBlue);
		//System.out.println(c);
		hsa.doAlg();
	}
	
	static public void Testlevelpos() 
	{
		HamSanAlg hsa = new HamSanAlg();
	
		MyFrame f = new MyFrame(hsa);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Insets insets = f.getInsets();
		f.setSize(680 + insets.left + insets.right, 400 + insets.top + insets.bottom);
		f.setVisible(true);
		hsa.addLine(0,1, false);
		hsa.addLine(0,2, false);
		hsa.addLine(0,3, false);
		hsa.addLine(0,4, true);
		hsa.addLine(0,5, true);
		hsa.addLine(0,6, true);
		//System.out.println(hsa.lBlue);
		//System.out.println(hsa.lRed);
	     for (int i = 0; i < hsa.lBlue.size(); i++) {
	            System.out.println(hsa.lBlue.get(i)+"y-Wert: "+hsa.lBlue.get(i).eval(1));
	        }
	     for (int i = 1; i < hsa.lBlue.size()+1; i++) {
	            System.out.println(i+" te Linie von Oben hat y-Wert "+hsa.levelPos(1, true, i));
	        }
		/*hsa.addLine(1,1, true);
		hsa.addLine(1, 2, true);
		Point a = hsa.lBlue.get(0);
		Point b = hsa.lBlue.get(1);

		//hsa.addLine(1, 0, true);
		//Point a = new Point(1,1);
		//Point b = new Point(1,0);
		//Point b = hsa.lRed.get(0);
		System.out.println("Index von a: "+a.i);
		System.out.println("Index von b: "+b.i);
		Point.value=0;
		System.out.println("Auswertung in value: "+Point.value);
		System.out.println(a.compareTo(b));
		System.out.println(hsa.levelPos(0, true, 4));*/
	}
	
	public static void main(String[] args) {
		//tests go here.
		randomlines();
		//Testlevelpos();
		//Point a = new Point(1,0);
		//Point b = new Point(0,1);
		//Point.value = 1;
		//System.out.println(b.compareTo(a));
		//Testlevelpos();
		Point a = new Point(1,0);
		Point b = new Point(0,1);

	}

}


