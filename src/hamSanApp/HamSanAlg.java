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
	public double levelpos(double x, boolean blue, int level) {
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
		return levelpos(x, true, levelBlue)>levelpos(x, false, levelRed);
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
		//TODO: vorher abfragen, ob das crossing innerhalb unserer momentanen begrenzungen liegt.
		List<Crossing> crossings = new ArrayList<Crossing>();
		for (int i = 0; i < lBlue.size();i++) {
			for (int j = i+1; j < lBlue.size();j++){
				crossings.add(new Crossing(lBlue.get(i),lBlue.get(j)));
			}
		}
		for (int i = 0; i < lBlue.size();i++) {
			for (int j = 0; j < lRed.size();j++){
				crossings.add(new Crossing(lBlue.get(i),lRed.get(j)));
			}
		}
		for (int i = 0; i < lRed.size();i++) {
			for (int j = i+1; j < lRed.size();j++){
				crossings.add(new Crossing(lRed.get(i),lRed.get(j)));
			}
		}
		
		//sort them. crossings implements comparable.
		Collections.sort(crossings);
		
		//TODO: everything from here
			
		//make stripes with at most alpha*(n choose 2) crossings a piece. //
			//speichern als ein array von x-werten
		//find strip with odd number of intersections by binary search.//
			//dann haben wir leftb und rightb
		//figure out M //
			//nachlesen wie sich M berechnet
		//construct Trapeze//
			//sonderfall, falls trapez im unbeschränkten intervall ist!
		//cut away lines, count and make sure levelB/R are correct//
	}
}
