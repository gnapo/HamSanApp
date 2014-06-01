package hamSanApp;

import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;









//import java.awt.*;
import javax.swing.JFrame;

import view.MyFrame;


	/**
	 * diese Klasse verwaltet alles: (HamSanAlg und Gui) und hat die main-methode. 
	 * @author fabian
	 *
	 */
public class Sandwich {
	
	static public boolean Test_monoton(List<Crossing> crossings){
		boolean r=true;
		 for (int i = 1; i < crossings.size(); i++) {
			if (crossings.get(i-1).crAt()>=crossings.get(i).crAt()){r=false;}
		 }
		return r;
	}
	
	static public boolean Test_wellordered(){
		boolean r=true;
		HamSanAlg hsa = new HamSanAlg();
		MyFrame f = new MyFrame(hsa);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Insets insets = f.getInsets();
		f.setSize(680 + insets.left + insets.right, 400 + insets.top + insets.bottom);
		f.setVisible(true);
		 for (int i = 0; i < 15; i++) {
			 hsa.addLine(Math.random()*10-5,Math.random()*10-5,true);
		 }
		 for (int i = 0; i < 15; i++) {
			 hsa.addLine(Math.random()*10-5,Math.random()*10-2.5,false);
		 }
		//generate all the crossings:
		 List<Crossing> crossings;
			crossings = new ArrayList<Crossing>();
			for (int i = 0; i < hsa.lBlue.size();i++) {
				for (int j = i+1; j < hsa.lBlue.size();j++){
					Crossing c = new Crossing(hsa.lBlue.get(i),hsa.lBlue.get(j));
					if (hsa.inBorders(c)) {
						crossings.add(c);
					}
				}
			}
			for (int i = 0; i < hsa.lBlue.size();i++) {
				for (int j = 0; j < hsa.lRed.size();j++){
					Crossing c = new Crossing(hsa.lBlue.get(i),hsa.lRed.get(j));
					if (hsa.inBorders(c)) {
						crossings.add(c);
					}
				}
			}
			for (int i = 0; i < hsa.lRed.size();i++) {
				for (int j = i+1; j < hsa.lRed.size();j++){
					Crossing c = new Crossing(hsa.lRed.get(i),hsa.lRed.get(j));
					if (hsa.inBorders(c)) {
						crossings.add(c);
					}
				}
			}
			
			
			//sort them. crossings implements comparable.
			
			//make stripes with at most alpha*(n choose 2) crossings a piece.
			Collections.sort(crossings);
			System.out.println("Kreuzungen"+crossings);
			r= Test_monoton(crossings);
			if (r=false){System.out.println("Sortierung nicht monoton");};
			if (r=true){System.out.println("Sortierung ist monoton");};
			for (int i=0; i< hsa.crossings.size();i++){
				
				for (int j=i; j< hsa.crossings.size();j++){
					if(  crossings.get(i).compareTo(crossings.get(j)) != -1) {
						r=false;
						System.out.println("Ordnung schlägt fehl bei Index"+crossings.get(i));
					}
				}
				
			}
			return r;
		
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

		hsa.addLine(0,1, true);
		return hsa.TestLineSort2(x,true);

	}
	
	static public void Testopt2() throws Exception 
	//static public boolean Testlevelpos() 
	{   //boolean r =true;
		HamSanAlg hsa = new HamSanAlg();
		MyFrame f = new MyFrame(hsa);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Insets insets = f.getInsets();
		f.setSize(680 + insets.left + insets.right, 400 + insets.top + insets.bottom);
		f.setVisible(true);
		Point a,b,c;
		b=hsa.addLine(0,1,true);
		a=hsa.addLine(1,0,true);
		c=hsa.addLine(-1,2,true);
		System.out.println("Im Fall -1 liegt kreuzung ij links neben kl "+Point.op2naive(b,c,a,b));
	}
	
	public static void main(String[] args) throws Exception {
		//tests go here.
		
		//randomlines();
		//System.out.println(Testlevelpos());
	
		//Point a = new Point(1,2);
		//Point b = new Point(1,1);
		//Crossing c = new Crossing(a,b);
		//System.out.println(c.atNegInf());
		//Point.value = 1;
		//System.out.println(b.compareTo(a));
		//Testlevelpos();
		
		/*HamSanAlg hsa = new HamSanAlg();
		MyFrame f = new MyFrame(hsa);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Insets insets = f.getInsets();
		f.setSize(680 + insets.left + insets.right, 400 + insets.top + insets.bottom);
		f.setVisible(true);*/
		Testopt2();
		
	}

}


