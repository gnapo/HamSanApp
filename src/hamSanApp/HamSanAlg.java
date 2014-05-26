package hamSanApp;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Diese Klasse beinhaltet den eigentlichen Algorithmus und einige Hilfsfunktionen.
 * @author fabian
 *
 */
public class HamSanAlg {

	List<Point> lBlue; 		//hier werden die vom Alg. berücksichtigten Blauen Linien gespeichert
	List<Point> lRed;		//hier werden die vom Alg. berücksichtigten Roten Linien gespeichert
	List<Point> lBlueDel;	// Del für deleted
	List<Point> lRedDel;	//hier werden die nicht berücksichtigten linien gespeichert
	boolean leftborder;		//
	boolean rightborder;	//bools, die wahr sind, falls der Momentane betrachtungsbereich nach links/rechts beschränkt ist
	double leftb;			//
	double rightb;			//der linke und Rechte Rand des betrachtungsbereiches
	int levelBlue;			//
	int levelRed;			//die wievielte linie von oben ist die gesuchte medianlinie?
	boolean firstRun;		//ist der Algorithmus schonmal etwas gelaufen (können wir noch linien verändern?
	boolean done;			//ist der Algorithmus fertig?
	boolean colorSwap;		//müssen wir die Farben gerade vertauscht zeichnen?
	boolean verticalSol;	//ist die Lösung eine Vertikale Linie?
	double verticalSolPos;	//position der vertikalen Lösung
	Point solution;			//position der nicht-vertikalen Lösung
	double [] borders;		//positionen der grenzen zwischen streifen.
								//konvention: borders[i] ist der linke rand von dem i-ten streifen
	
	final double alpha = 1.0d/32.0d; 	//
	final double eps = 1.0d/8.0d;		//Konstanten für den Alg
	
	/**
	 * Konstruktor, macht nichts besonderes.
	 */
	HamSanAlg(){
		init();
	}
	
	/** 
	 * setzt alle Variablen auf startzustände
	 */
	public void init() {
		lBlue = new ArrayList<Point>();
		lRed = new ArrayList<Point>();
		lBlueDel = new ArrayList<Point>();
		lRedDel = new ArrayList<Point>();
		leftborder = false;
		rightborder = false;
		leftb = 0;
		rightb = 0;
		firstRun = true;
		done = false;
		solution = null;
		verticalSol = false;
		borders = new double[64];
	}
	
	/**
	 * Linien hinzufügen in Form zweier Koordinaten.
	 * nur möglich, wenn der Algorithmus noch nicht angelaufen ist.
	 * @param x erste
	 * @param y zweite koordinate
	 * @param blue ist es eine blaus linie?
	 */
	public void addLine(double x, double y, boolean blue){
		if (!firstRun) {return;}
		if (blue){
			lBlue.add(new Point(x, y));
		}
		else {
			lRed.add(new Point(x, y));
		}
	}
	
	/**
	 * lösche eine linie aus lBlue und lRed heraus. 
	 * nur möglich, wenn der Algorithmus noch nicht angelaufen ist.
	 * @param l die zu löschende linie
	 */
	public void removeLine(Point l) {
		if (!firstRun) {return;}
		lBlue.remove(l);
		lRed.remove(l);
	}
	
	/**
	 * verstecke eine Linie vor dem Algorithmus. sie wird dann gesondert gezeichnet.
	 * @param l
	 */
	public void hideLine(Point l) {
		if (lBlue.remove(l)) {
			lBlueDel.add(l);
		}
		if (lRed.remove(l)) {
			lRedDel.add(l);
		}
	}
	
	/**
	 * gib die lösung aus. warscheinlich nicht so wichtig.
	 */
	public void presentSolution() {
		if (!done) {
			System.out.println("algorithm not done yet :(");
		}
		if (verticalSol) {
			System.out.println("the solution is a vertical line with x = "+verticalSolPos);
		}
		else {
			System.out.print("the solution is the ");
			solution.repr_line();
		}
	}
	
	/**
	 * gibt die y-Koordinate der level'ten linie von Oben an der stelle x aus
	 * @param x die x-Koordinate
	 * @param blue von den Blauen oder Roten linien?
	 * @param level wievielte linie von oben?
	 * @return der y-Wert
	 */
	public double levelPos(double x, boolean blue, int level) {
		//TODO implement with quickselect//
		//oder halt sortieren weils einfacher geht
		return 0.0d;
	}
	
	/**
	 * Ist an der stelle die Blaue Medianlinie höher als die Rote?
	 * @param x die Stelle
	 * @return true, falls Blau oben.
	 */
	public boolean blueTop(double x) {
		//is the blue level higher than the red level at x?
		return levelPos(x, true, levelBlue)>levelPos(x, false, levelRed);
	}
	
	/**
	 * Hilfsfunktion, um herauszufinden, ob wir eine Kreuzung berücksichtigen müssen.
	 * Schaut nach, ob die Kreuzung innerhalb des momentanen Betrachtungsbereiches ist.
	 * @param c die betreffende Kreuzung
	 * @return true, falls wir die Kreuzung berücksichtigen müssen.
	 */
	public boolean inBorders(Crossing c) {
		if (c.atInf()) {
			if (c.atNegInf() && leftborder) {
				return false;
			}
			if (!c.atNegInf() && rightborder) {
				return false;
			}
		}
		if (leftb <= c.crAt() && c.crAt() <= rightb) {return true;}
		else {return false;}
	}
	
	/**
	 * Funktion, die errechnet, ob im unbeschränkten bereich links die blaue medianlinie über der Roten ist
	 * @return true falls ja
	 */
	private boolean blueTopLeft() {
		// TODO implementieren
		return false;
	}
	/**
	 * der eigentliche Algorithmus. ein ausführen dieses Algorithmus stellt einen
	 * Iterationsschritt dar. wir wollen das warscheinlich noch weiter in 
	 * kleinere Schritte aufteilen.
	 */
	public void doAlg() { //sets done to true iff it has found a solution
		if (firstRun) {
			//make sure that both sets are odd by deleting a point out of each set:
			if ((lBlue.size()%2) == 0) {
				hideLine(lBlue.get(0));
			}
			if ((lRed.size()%2) == 0) {
				hideLine(lRed.get(0));
			}
			//set the levelBlue and levelRed to the correct values:
			levelBlue = (lBlue.size()+1/2);
			levelRed = (lRed.size()+1/2);
			firstRun = false; //so we don't change the points, and only do this once
		}
		
		//check if trivial solution:
		if (lBlue.size()==1 && lRed.size()==1) {
			Point b = lBlue.get(0);
			Point r = lRed.get(0);
			//do we need a vertical line?
			if (b.a == r.a) {
				done = true;
				verticalSol = true;
				verticalSolPos = b.a;
				return;
			}
			done = true;
			//find intersection point and return that. done!
			solution = new Point(b.cross(r),b.eval(b.cross(r)));
			return;
		}
		
		// swap the lines if blue is smaller:
		if (lBlue.size() < lRed.size()) {
			colorSwap = !colorSwap;
			//TODO: 
			//swap lBlue and lRed//
			//also swap lBlueDel and lRedDel//
		}
		
		//generate all the crossings:
		List<Crossing> crossings = new ArrayList<Crossing>();
		for (int i = 0; i < lBlue.size();i++) {
			for (int j = i+1; j < lBlue.size();j++){
				Crossing c = new Crossing(lBlue.get(i),lBlue.get(j));
				if (inBorders(c)) {
					crossings.add(c);
				}
			}
		}
		for (int i = 0; i < lBlue.size();i++) {
			for (int j = 0; j < lRed.size();j++){
				Crossing c = new Crossing(lBlue.get(i),lRed.get(j));
				if (inBorders(c)) {
					crossings.add(c);
				}
			}
		}
		for (int i = 0; i < lRed.size();i++) {
			for (int j = i+1; j < lRed.size();j++){
				Crossing c = new Crossing(lRed.get(i),lRed.get(j));
				if (inBorders(c)) {
					crossings.add(c);
				}
			}
		}
		
		//sort them. crossings implements comparable.
		
		//make stripes with at most alpha*(n choose 2) crossings a piece.
		Collections.sort(crossings);
		int minband = 0;
		int maxband = 0; //wird überschrieben.
		int band = 1;
		int bandsize = (int) (crossings.size()*alpha);
		for (int i = bandsize; i < crossings.size();i+=bandsize){
			while (crossings.get(i).atInf() && crossings.get(i).atNegInf()) {i++;}
			if (crossings.get(i).atInf() && !crossings.get(i).atNegInf()) {
				while (crossings.get(i).atInf() && !crossings.get(i).atNegInf()) {
					i--;
				}
				borders[band] = crossings.get(i).crAt();
				maxband = band;
				break;
			}
			borders[band] = crossings.get(i).crAt();
			band++;
		}
			
		//find strip with odd number of intersections by binary search:		
		boolean bluetop = blueTopLeft();
		while ((maxband-minband) > 1) { //TODO i think this needs to be more robust for the non-bounded cases?
			int testband = (maxband-minband)/2;
			boolean bluetesttop = blueTop(borders[testband]);
			if (bluetop == bluetesttop) {
				minband = testband;
			}
			else {
				maxband = testband;
			}
		}
		leftb = borders[minband];
		rightb = borders[maxband];
		
		//TODO handle non-bounded case
		int topLvl = levelBlue - (int) (eps*lBlue.size());
		int botLvl = levelBlue - (int) (eps*lBlue.size());
		double tl = levelPos(leftb,true,topLvl);
		double tr = levelPos(rightb,true,topLvl);
		double bl = levelPos(leftb,true,botLvl);
		double br = levelPos(rightb,true,botLvl);
		Trapeze t = new Trapeze(leftb, tl, bl, rightb, tr, br);
		
		//cut away lines, count and make sure levelB/R are correct:
		for (int i = 0; i < lBlue.size(); ++i) {
			int s = t.intersects(lBlue.get(i));
			if (s != 0) {
				if (s > 0) {
					levelBlue --;
				}
				hideLine(lBlue.get(i));
			}
		}
		for (int i = 0; i < lRed.size(); ++i) {
			int s = t.intersects(lRed.get(i));
			if (s != 0) {
				if (s > 0) {
					levelRed --;
				}
				hideLine(lRed.get(i));
			}
		}
		
	}

	
}
