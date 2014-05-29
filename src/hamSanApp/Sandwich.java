package hamSanApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;








//import java.awt.*;
import javax.swing.*;


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
		hsa.addLine((Math.random() * 5)-2.5, (Math.random() * 15)-7.5, false);
		hsa.addLine((Math.random() * 5)-2.5, (Math.random() * 15)-7.5, false);
		hsa.addLine((Math.random() * 5)-2.5, (Math.random() * 15)-7.5, false);
		hsa.addLine((Math.random() * 5)-2.5, (Math.random() * 15)-7.5, true);
		hsa.addLine((Math.random() * 5)-2.5, (Math.random() * 15)-7.5, true);
		hsa.addLine((Math.random() * 5)-2.5, (Math.random() * 15)-7.5, true);
		System.out.println(hsa.lBlue);
		System.out.println(hsa.lRed);
		//List<Point> c = new ArrayList<Point>(hsa.lBlue);
		//System.out.println(c);
		hsa.doAlg();
	}
	
	static public List<Point> Testlevelpos() 
	//static public boolean Testlevelpos() 
	{   //boolean r =true;
	    double x =1;
		HamSanAlg hsa = new HamSanAlg();
	
		MyFrame f = new MyFrame(hsa);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Insets insets = f.getInsets();
		f.setSize(680 + insets.left + insets.right, 400 + insets.top + insets.bottom);
		f.setVisible(true);
		//Test1
		hsa.addLine(-1,2, true);
		hsa.addLine(1,1, true);
		hsa.addLine(0,1, true);
		//Test2
		/*hsa.addLine(1,2, true);
		hsa.addLine(-1,0, true);
		hsa.addLine(0,1, true);*/
		//System.out.println(hsa.lBlue);
		//System.out.println(hsa.lRed);
	    /*for (int i = 0; i < hsa.lBlue.size(); i++) {
	            System.out.println(hsa.lBlue.get(i)+" y-Wert: "+hsa.lBlue.get(i).eval(1));
	        }
	     for (int i = 1; i < hsa.lBlue.size()+1; i++) {
	            System.out.println(i+" te blaue Linie von Oben hat y-Wert "+hsa.levelPos(x, true, i));
	        }
	     for (int i = 0; i < hsa.lRed.size(); i++) {
	            System.out.println(hsa.lRed.get(i)+" y-Wert: "+hsa.lRed.get(i).eval(1));
	        }*/
	    // for (int i = 1; i < hsa.lRed.size()+1; i++) {
	    //        System.out.println(i+" te rote Linie von Oben hat an Stelle"+x+"y-Wert "+hsa.levelPos(x, false, i));
	     //   }
       // for (int i = 1; i < hsa.lRed.size()+1; i++) {
       //    if (hsa.levelPos(-1, false, i)!=hsa.lRed.get(i-1).eval(1)) r=false;
      // }
		return hsa.TestLineSort2(x,true);
	

	}
	
	public static void main(String[] args) {
		//tests go here.
		System.out.println(Testlevelpos());
		
	}

}


