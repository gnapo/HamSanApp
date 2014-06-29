package hamSanApp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.lang.Math;

import view.PointType;
import view.VisualPoint;

/**
 * Diese Klasse beinhaltet den eigentlichen Algorithmus und einige Hilfsfunktionen.
 * wichtigie Methoden von au�en: 
 * addLine, removeLine, findLine, findPoint, doAlg
 * @author fabian
 *
 */
public class HamSanAlg {

	public List<Point> lBlue; 		//hier werden die vom Alg. ber�cksichtigten Blauen Linien gespeichert
	public List<Point> lRed;		//hier werden die vom Alg. ber�cksichtigten Roten Linien gespeichert
	public List<Point> lBlueDel;	// Del f�r deleted
	public List<Point> lRedDel;	//hier werden die nicht ber�cksichtigten linien gespeichert
	public List<Point> firstlRed;//Punktemengen zu Beginn des Algorithmus
	public List<Point> firstlBlue;
	public boolean leftborder;		//
	public boolean rightborder;	//bools, die wahr sind, falls der Momentane betrachtungsbereich nach links/rechts beschr�nkt ist
	public double leftb;			//
	public double rightb;			//der linke und Rechte Rand des betrachtungsbereiches
	int levelBlue;			//
	int levelRed;			//die wievielte linie von oben ist die gesuchte medianlinie?
	boolean firstRun;		//ist der Algorithmus schonmal etwas gelaufen (k�nnen wir noch linien ver�ndern?
	public boolean done;			//ist der Algorithmus fertig?
	boolean colorSwap;		//m�ssen wir die Farben gerade vertauscht zeichnen?
	public boolean verticalSol;	//ist die L�sung eine Vertikale Linie?
	public double verticalSolPos;	//position der vertikalen L�sung
	public Point solution;			//position der nicht-vertikalen L�sung
	public double [] borders;		//positionen der grenzen zwischen streifen.
								//konvention: borders[i] ist der linke rand von dem i-ten streifen und die streifen sind halboffen, linker punkt ist drin.
	public List<Crossing> crossings;// hier werden die Kreuzungen gespeichert;
	boolean DEBUG = true;
	public Trapeze trapeze;	//das trapez (zum zeichnen)
	public int minband;		//
	public int maxband;		// zur bin�ren suche auf den intervallen(b�ndern)
	public int step;	//in welchem shritt sind wir?
						// 0: Ausgangssituation
						// 1: Intervalle Eingeteilt
						// 2: Richtiges Intervall rausgesucht
						// 3: Trapez konstruiert

	boolean leftsetthistime = false;	//
	boolean rightsetthistime = false;	// used for going from step 2 to 3.
	boolean leftmannyC=false;//wird auf true gesetzt, falls linker bzw rechter Randbereich bei Intervalleinteilung 
	boolean rightmannyC=false;//aus mehr als (alpfa * Crossings.size()) kreuzungen im negativ bzw positivUnendlichen besteht
	
	final double alpha = 1.0d/32.0d; 	//
	final double eps = 1.0d/8.0d;		//Konstanten f�r den Alg
	
	/**
	 * Konstruktor, macht nichts besonderes.
	 */
	public HamSanAlg(){
		init();
	}
	
	/** 
	 * setzt alle Variablen auf startzust�nde
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
		colorSwap = false;
		solution = null;
		verticalSol = false;
		borders = new double[64];
		crossings = new ArrayList<Crossing>();
		trapeze = null;
		step = 0;
		maxband = 0;
		minband = 0;
		
	}
	
	
	/**
	 * Linien hinzuf�gen in Form zweier Koordinaten.
	 * nur m�glich, wenn der Algorithmus noch nicht angelaufen ist.
	 * @param x erste
	 * @param y zweite koordinate
	 * @param blue ist es eine blaus linie?
	 */
	public Point addLine(double x, double y, boolean blue){
		if (!firstRun) {return null;}
		Point p = new Point(x, y);
		if (blue){
			lBlue.add(p);
		}
		else {
			lRed.add(p);
		}
		return p;
	}
	
	/**
	 * l�sche eine linie aus lBlue und lRed heraus. 
	 * nur m�glich, wenn der Algorithmus noch nicht angelaufen ist.
	 * @param l die zu l�schende linie
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
	 * Funktion, die einen Punkt zur�ckgibt, der in der n�he der position (x,y) ist.
	 * @param tolerance wie weit entfernt (x,y) von dem Punkt sein darf;
	 * @return der Punkt
	 */
	public Point findPoint(double x, double y, double tolerance) {
		Point best = null;
		double bestdist = 9999;
		for (int i = 0; i < lBlue.size(); i++) {
			Point test = lBlue.get(i);
			double dist = Math.sqrt((test.a-x)*(test.a-x)+(test.b-y)*(test.b-y));
			if (dist < tolerance && dist < bestdist) {
				best = test;
				bestdist = dist;
			}
		}
		for (int i = 0; i < lRed.size(); i++) {
			Point test = lRed.get(i);
			double dist = Math.sqrt((test.a-x)*(test.a-x)+(test.b-y)*(test.b-y));
			if (dist < tolerance && dist < bestdist) {
				best = test;
				bestdist = dist;
			}
		}
		return best;
	}
	
	/**
	 * Funktion, die eine Gerade zur�ckgibt, der in der n�he der position (x,y) ist.
	 * @param tolerance wie weit entfernt (x,y) von dem Punkt sein darf;
	 * @return der Punkt
	 */
	public Point findLine(double x, double y, double tolerance) {
		Point best = null;
		double bestdist = 9999;
		for (int i = 0; i < lBlue.size(); i++) {
			Point test = lBlue.get(i);
			double dist = (Math.abs(y-test.eval(x)))*Math.cos(Math.atan(test.a));
			if (dist < tolerance && dist < bestdist) {
				best = test;
				bestdist = dist;
			}
		}
		for (int i = 0; i < lRed.size(); i++) {
			Point test = lRed.get(i);
			double dist = (Math.abs(y-test.eval(x)))*Math.cos(Math.atan(test.a));
			if (dist < tolerance && dist < bestdist) {
				best = test;
				bestdist = dist;
			}
		}
		return best;
	}
	

	public boolean TestLineSort(double x, boolean blue){
		boolean r=true;
		LineComparator x_evaluation = new LineComparator(x);
		List<Point> locList;
		if(blue==true){
			locList = new ArrayList<Point>(lBlue);
		}
		else {
			locList= new ArrayList<Point>(lRed);
		}
		Collections.sort(locList, x_evaluation);
		for (int i = 0; i < locList.size(); i++) {
	        if (i+1!=locList.size()){
	        	if(  (locList.get(i).eval(x)==locList.get(i+1).eval(x)) && ( locList.get(i).i <=locList.get(i+1).i ) ){
	        		if (x<0){r=false;}
	        	}
	        	else if ( locList.get(i).i >locList.get(i+1).i ){
	        		if (x>=0){r=false;}
	        	}
	        }
	        		

        }
		return r;
	}
	
	public  List<Point> TestLineSort2(double x, boolean blue){
		LineComparator x_evaluation = new LineComparator(x);
		List<Point> locList;
		if(blue==true){
			locList = new ArrayList<Point>(lBlue);
		}
		else {
			locList= new ArrayList<Point>(lRed);
		}
		Collections.sort(locList, x_evaluation);

		return locList;
	}

	
	/**
	 * gibt die y-Koordinate der level'ten linie von Oben an der stelle x aus
	 * Dabei nimmt Level Werte zwischen 1 und lBlue.size()+1 bzw l.size()+1 an! 
	 * @param x die x-Koordinate
	 * @param blue von den Blauen oder Roten linien?
	 * @param level wievielte linie von oben?
	 * @return der y-Wert 
	 */
	
	public double levelPos(double x, boolean blue, int level) {
		LineComparator x_evaluation = new LineComparator(x);
		List<Point> locList;
		if(blue==true){
			locList = new ArrayList<Point>(lBlue);
		}
		else {
			locList= new ArrayList<Point>(lRed);
		}
		Collections.sort(locList, x_evaluation);
		return locList.get(level-1).eval(x);
	}
	
	/**
	 * Ist an der stelle die Blaue Medianlinie h�her als die Rote?
	 * @param x die Stelle
	 * @return 1, falls blau oben, -1 falls rot, 0 falls wir einen Schnittpunkt haben.
	 */
	public int blueTop(double x) {
		//is the blue level higher than the red level at x?
		double bluePos = levelPos(x, true, levelBlue);
		double redPos = levelPos(x, false, levelRed);
		if (bluePos > redPos) {
			return 1;
		}	
		if (bluePos < redPos) {
			return -1;
		}
		return 0;
	}
	
	/**
	 * Hilfsfunktion, um herauszufinden, ob wir eine Kreuzung ber�cksichtigen m�ssen.
	 * Schaut nach, ob die Kreuzung innerhalb des momentanen Betrachtungsbereiches ist.
	 * @param c die betreffende Kreuzung
	 * @return true, falls wir die Kreuzung ber�cksichtigen m�ssen.
	 */
	public boolean inBorders(Crossing c) { //Don't know if commenting out this makes it work. huh
		double tolerance = 0.00001;
		if (c.atInf()) {
			if (c.atNegInf() && leftborder) {
				return false;
			}
			if (!c.atNegInf() && rightborder) {
				return false;
			}
		}
		if (leftborder && c.crAt() < leftb-tolerance) { return false;}
		if (rightborder && c.crAt() >= rightb+tolerance) { return false;}
		//if (leftborder && c.crAt() < leftb) { return false;}
		//if (rightborder && c.crAt() >= rightb) { return false;}//
		return true;
	}
	
	/**
	 * Funktion, die errechnet, ob im unbeschr�nkten bereich links die blaue medianlinie �ber der Roten ist
	 * @return true falls ja
	 */
	public boolean blueTopLeft() { //todo Testme
		LineComparator2 c = new LineComparator2();
		
		List<Point> blueLoc = new ArrayList<Point>(lBlue);
		List<Point> redLoc = new ArrayList<Point>(lRed);
		Collections.sort(blueLoc, c);
		Collections.sort(redLoc, c);
		return 1 == c.compare(blueLoc.get(levelBlue-1), redLoc.get(levelRed-1));
	}
	
	/**
	 * gibt die level-t groesste steigung der roten oder blauen geraden zurueck
	 * wird fuer unbeschraenkte trapeze gebraucht.
	 * @param blue die blauen geraden?
	 * @param level wievielt-groesste steigung?
	 * @return die steigung
	 */
	public double getslope(boolean blue, int level) { //TODO testme
		LineComparator2 c = new LineComparator2();
		List<Point> col;
		if (blue) {
			col = new ArrayList<Point>(lBlue);
		}
		else {
			col = new ArrayList<Point>(lRed);
		}
		Collections.sort(col, c);
		Collections.reverse(col); //this might give us less wonky trapezes. let's see :D
		return col.get(level).a;
	}
	
	/**
	 * funktion, die prueft, ob ein gegebener schnitt valide ist.
	 * @return Ja falls valider Schnitt
	 */
	public boolean validSol(boolean verbose) {
		if (!done){
			if (DEBUG && verbose) {
				System.out.println("algo is not done!");
			}
			return false; //haben noch keinen schnitt.
		}
			
		if (!verticalSol && solution == null) return false; //wtf? had a trapeze not kill any lines.
		double tol = 0.0000001; //tolerance
		if (verticalSol) {
			int bleft = 0;
			int bright = 0;
			int rleft = 0;
			int rright = 0;
			
			for (int i = 0; i< lBlue.size();i++) {
				Point t = lBlue.get(i);
				if (verticalSolPos +tol < t.a) bright ++;
				if (verticalSolPos -tol > t.a) bleft ++;
			}
			for (int i = 0; i< lBlueDel.size();i++) {
				Point t = lBlueDel.get(i);
				if (verticalSolPos +tol < t.a) bright ++;
				if (verticalSolPos -tol > t.a) bleft ++;
			}
			for (int i = 0; i< lRed.size();i++) {
				Point t = lRed.get(i);
				if (verticalSolPos +tol < t.a) rright ++;
				if (verticalSolPos -tol > t.a) rleft ++;
			}
			for (int i = 0; i< lRedDel.size();i++) {
				Point t = lRedDel.get(i);
				if (verticalSolPos +tol < t.a) rright ++;
				if (verticalSolPos -tol > t.a) rleft ++;
			}
			if (verbose) {
				System.out.println("There are "+bleft+" blue points left, "+bright+" right of a total of "+(lBlue.size()+lBlueDel.size()));
				System.out.println("There are "+rleft+" red points left, "+rright+" right of a total of "+(lRed.size()+lRedDel.size()));
			}	
			
			if (Math.max(bleft, bright) > (lBlue.size()+lBlueDel.size())/2) return false;
			if (Math.max(rleft, rright) > (lRed.size()+lRedDel.size())/2) return false;
			
		//	System.out.println("haben Vertikale Lösung gefunden");
			return true;
		}
		
		int babove = 0; //blue above
		int bbelow = 0; //blue below
		int rabove = 0; //red ..
		int rbelow = 0;
		
		for (int i = 0; i< lBlue.size();i++) {
			Point t = lBlue.get(i);
			if (solution.eval(t.a) +tol < t.b) babove ++;
			if (solution.eval(t.a) -tol > t.b) bbelow ++;
		}
		for (int i = 0; i< lBlueDel.size();i++) {
			Point t = lBlueDel.get(i);
			if (solution.eval(t.a) +tol < t.b) babove ++;
			if (solution.eval(t.a) -tol > t.b) bbelow ++;
		}
		for (int i = 0; i< lRed.size();i++) {
			Point t = lRed.get(i);
			if (solution.eval(t.a) +tol < t.b) rabove ++;
			if (solution.eval(t.a) -tol > t.b) rbelow ++;
		}
		for (int i = 0; i< lRedDel.size();i++) {
			Point t = lRedDel.get(i);
			if (solution.eval(t.a) +tol < t.b) rabove ++;
			if (solution.eval(t.a) -tol > t.b) rbelow ++;
		}
		if (verbose) {
			System.out.println("There are "+bbelow+" blue points below, "+babove+" above of a total of "+(lBlue.size()+lBlueDel.size()));
			System.out.println("There are "+rbelow+" red points below, "+rabove+" above of a total of "+(lRed.size()+lRedDel.size()));
		}
		
		if (Math.max(bbelow, babove) > (lBlue.size()+lBlueDel.size())/2) return false;
		if (Math.max(rbelow, rabove) > (lRed.size()+lRedDel.size())/2) return false;
		
	//	System.out.println("haben korrekten Cut gefunden.");
		return true;
	}
	
	/**
	 * der eigentliche Algorithmus. ein ausf�hren dieses Algorithmus stellt einen
	 * Iterationsschritt dar. wir wollen das warscheinlich noch weiter in 
	 * kleinere Schritte aufteilen.
	 */
	
	//Im Fall, dass Lösung eine Kreuzung im Unendlichen ist, ist die Lösung eine vertikale Gerade. 
	//Gehe alle Kreuzungen vor index oder ab index durch und finde den Cut!
	public boolean verticalcut(){
		System.out.println("Sind im Fall, dass Hamsandwichcut eine Vertikale ist");
		for(int i=0; i<lBlue.size(); i++){
			verticalSolPos=lBlue.get(i).a;
			if (validSol(true)){
				System.out.println("Verticale Lösung durch blauen Punkt gefunden");
				return true;
			}
		}
		System.out.println("Es gibt keine Vertikale Lösung durch einen blauen Punkt");
		for(int i=0; i<lRed.size(); i++){
			verticalSolPos=lRed.get(i).a;
			if (validSol(true)){
				System.out.println("Verticale Lösung durch roten Punkt gefunden");
				return true;
			}
		System.out.println("Es gibt überhaupt keine Vertikale Lösung");
		}
		return false;
		
	}
		

	public void doAlg() { //sets done to true iff it has found a solution
		if (done) {
			if (validSol(true)) System.out.println("Yay it worked");
			return;
		}
		if (lBlue.size() == 0 && lRed.size() == 0) {
			return; //nix zu tun!
		}
		switch (step) {
		
		case 0:
			trapeze = null;
			if (firstRun) {
				//speichere Anfangskonstellation der Punkte ab
				firstlRed = new ArrayList<Point>(lRed);
				firstlBlue = lRed;//Punktemengen zu Beginn des Algorithmus
				firstlBlue = new ArrayList<Point>(lBlue);
				// make sure that both sets are odd by deleting a point out of
				// each set:
				if (((lBlue.size() % 2) == 0) && lBlue.size() > 0) {
					hideLine(lBlue.get(0));
				}
				if (((lRed.size() % 2) == 0) && lRed.size() > 0) {
					hideLine(lRed.get(0));
				}
				// set the levelBlue and levelRed to the correct values:
				levelBlue = ((lBlue.size() + 1) / 2);
				levelRed = ((lRed.size() + 1) / 2);
				firstRun = false; // so we don't change the points, and only do
									// this once
			}

			if (lBlue.size() == 0) { // only red lines.
				double rL = levelPos(0, false, (levelRed));
				solution = new Point(0, rL);
				done = true;
				firstRun = false;
				return;
			}
			if (lRed.size() == 0) { // only red lines.
				double bL = levelPos(0, true, (levelBlue));
				solution = new Point(0, bL);
				done = true;
				firstRun = false;
				return;
			}

			// check if trivial solution:
			if (lBlue.size() == 1 && lRed.size() == 1) {
				Point b = lBlue.get(0);
				Point r = lRed.get(0);
				// do we need a vertical line?
				if (b.a == r.a) { // TODO das hier testen
					System.out.println("haben genau zwei verschiedenfarbene parallele Geraden");
					done = true;
					verticalSol = true;
					verticalSolPos = b.a;
					return;
					
				}
				done = true;
				// find intersection point and return that. done!
				// double sl = (b.b-r.b)/(b.a-r.a);
				// solution = new Point(sl,r.b-r.a*sl);
				// or should it be:
				Crossing c = new Crossing(r, b);
				solution = new Point(-c.crAt(), r.eval(c.crAt()));
				return;
			}

			// swap the lines if blue is smaller:
			if (lBlue.size() < lRed.size()) {
				colorSwap = !colorSwap;
				List<Point> temp = lBlue;
				lBlue = lRed;
				lRed = temp;
				temp = lBlueDel;
				lBlueDel = lRedDel;
				lRedDel = temp;
				int tempint = levelBlue;
				levelBlue = levelRed;
				levelRed = tempint;
			}

			// generate all the crossings:
			crossings = new ArrayList<Crossing>();
			
			for (int i = 0; i < lBlue.size(); i++) { //blue-red
				for (int j = 0; j < lRed.size(); j++) {
					Crossing c = new Crossing(lBlue.get(i), lRed.get(j));
					if (inBorders(c)) {
						crossings.add(c);
					}
				}
			}
			
			if (crossings.size() == 1) { //beseitigt glaub ich einige fehlerf�lle? ja, das ist aber schlecht. :<
				
				Crossing c = crossings.get(0);
				solution = new Point(-c.crAt(), c.line1.eval(c.crAt()));
				if (DEBUG) { System.out.println("es gibt nur eine Kreuzung im Betrachteten Bereich zwischen roten und blauen Linien. es muss die Loesung sein");}
				done = true;
				return;
			}
			
			for (int i = 0; i < lBlue.size(); i++) { //blue-blue
				for (int j = i + 1; j < lBlue.size(); j++) {
					Crossing c = new Crossing(lBlue.get(i), lBlue.get(j));
					if (inBorders(c)) {
						crossings.add(c);
					}
				}
			}
			
			
			for (int i = 0; i < lRed.size(); i++) { //red-red
				for (int j = i + 1; j < lRed.size(); j++) {
					Crossing c = new Crossing(lRed.get(i), lRed.get(j));
					if (inBorders(c)) {
						crossings.add(c);
					}
				}
			}

			// sort them. crossings implements comparable.

			// make stripes with at most alpha*(n choose 2) crossings a piece.
			Collections.sort(crossings);
			
			
			
			// might work?
			/*
			if (DEBUG && false) {
				// warning: cheating going on.
				for (int i = 0; i < crossings.size(); i++) {
					double pos = crossings.get(i).crAt();
					if (levelPos(pos, true, levelBlue) == levelPos(pos, false,
							levelRed)) {
						System.out.println("yayy!");
						done = true;
						solution = new Point(-pos, levelPos(pos, true,
								levelBlue));
						return;
					}
				}
				System.out.println("aww :'(");
			}*/
			//int a=crossings.size()/2;int b=crossings.size()-1;
			//System.out.println("haben folgende kreuzungen: "+"vorne "+crossings.get(0)+" in der Mitte : "+crossings.get(a)+"; am Ende "+crossings.get(b));
			//System.out.println("haben folgende kreuzungen: "+crossings);
			minband = 0;
			maxband = 0; // wird �berschrieben.
			int band = 1;
			int bandsize = (int) (crossings.size() * alpha);
			bandsize = Math.max(1, bandsize);
			// here's how things are meant to be: all crossings at negInf are left of borders[band] 
			// all crossings at posInf are to the right of borders[maxband], so that all crossings at real values
			// are geq borders[i] and less than borders[i+1] for 1<=i<maxborders
			
			leftmannyC=false;//wird auf true gesetzt,falls linker bzw rechter Randbereich bei Intervalleinteilung 
			rightmannyC=false;//mehr als bandsize viele Kreutungen im negativ/bzw positiv Unendlichen haben
			if (DEBUG) {System.out.println("Intervalle werden eingeteilt");}
			for (int i = bandsize; i < crossings.size(); i += bandsize) { 
                //Fall, dass bei aktuellem Index i Kreuzung im Unendlichen liegt
				if (crossings.get(i).atInf()){
					if (crossings.get(i).atNegInf()){//Fall, dass erste bandsize Kreutungen im negativ-Unendlichen liegen

					// gibt es im negativ Unendlichen mehr als bandsize Kreuzungen, so vergrößere erstes Intervall so, 
					//dass alle Kreuzungen im negativ Unendlichen darin enthalten sind.
						leftmannyC=true;
						 System.out.println("haben viele kreuzungen im negativ-Unendlichen");
						while (i<crossings.size() && crossings.get(i).atInf() && crossings.get(i).atNegInf()){
							i++;
						}
						//Gibt es nur Kreuzungen im negativ und positiv unendlichen, so besteht die Eingabe aus parallelen 
						//Geraden bzw aus Punkten mit gleicher x-Koordinate. die Vertikalte durch all diese Punkte 
						//ist in diesem Fall die Lösung
						if ((i==crossings.size())||crossings.get(i).atInf() && !crossings.get(i).atNegInf()){
							System.out.println("Da alle Geraden parallel sind, haben "
									+ "eingegebene Punkte gleiche x-Koordinate. Deshalb ist "
							 		+ "das Ergebnis eine Vertikale durch alle Punkte hindurch; "
							 		+ "Fall von vielen kreutzungen bei - Inf");
								done = true;
								verticalSol = true;
								verticalSolPos = lBlue.get(0).a;
								return;
						}
					}//Ende: Fall, dass erste bandsize Kreuzungen im negativ-Unendlichen leigen
					
       
					else{//Fall: haben bei Index i Kreuzung im Positiv Unendlichen
						if (i==bandsize){//passiert das schon zu Beginn der Intervalleinteilung,
							//haben wir wahrscheinlich parallele Geraden als Eingabe
							boolean isparallel=false;
							for(int j=0;j<crossings.size()-1; j++){
								if(crossings.get(j).crAt()==crossings.get(j+1).crAt()){
								isparallel=true;
								}
							}
							if (isparallel==true){
								System.out.println("Da alle Geraden parallel sind, haben "
							 		+ "eingegebene Punkte gleiche x-Koordinate. Deshalb ist "
								 		+ "das Ergebnis eine Vertikale durch alle Punkte hindurch"
								 		+ "im Fall, dass Parallelität geprüft wird");
										done = true;
										verticalSol = true;
										verticalSolPos = lBlue.get(0).a;
										return;
							}
							else{
								System.out.println("komischer fall, in dem im Ersten Intervall schon Kreuzung Unendlich auftaucht");
								//da nicht alle Geraden parallel sind, muss es eine Kreuzung geben, die nicht im Unendlichen liegt 
								//und im ersten Itervall enthalten ist. 
								//Wir erhalten also in diesem Fall nur genau zwei Intervalle!
									System.out.println("haben genau zwei Intervalle. Im Ersten Intervall ist mindestens "
											+ "eine Kreutung enthalten, die nicht im Unendlichen liegt");
									rightmannyC=true;
									while(crossings.get(i).atInf() && !crossings.get(i).atNegInf()&& i>1) {
										i--; 
										 //	 System.out.println("sind bei Index"+i+"und kreuzung "+crossings.get(i)); 
										 // System.out.println("haben nun index verschoben und sind bei i="+i);
									}
									// if ((band>1)&&borders[band-1]!=crossings.get(i).crAt()){
									borders[band] = crossings.get(i).crAt(); band++; maxband = band;//}
									break; 
								}
								
						}//Ende des Falls, dass wir bei Beginn der Intervalleinteilung eine Kreutzung im positiv Unendlicheh haben
						
						else{//Haben Kreutzung im positiv Unendlichen nicht zu Beginn der Intervalleinteilung
							System.out.println("haben viele kreuzungen im Positiv Unendlichen");
							rightmannyC=true;
							while(crossings.get(i).atInf() && !crossings.get(i).atNegInf()&& i>1) {
								i--; 
								 //	 System.out.println("sind bei Index"+i+"und kreuzung "+crossings.get(i)); 
								 // System.out.println("haben nun index verschoben und sind bei i="+i);
							}
							// if ((band>1)&&borders[band-1]!=crossings.get(i).crAt()){
							borders[band] = crossings.get(i).crAt(); band++; maxband = band;//}
							break; //band++ hinzugefügt 
						}
					}//Ende vom Fall, dass beim aktuellen Index Kreuzung im Positiv Unendlichen ist
				}//Ende fom Fall, dass beim aktuellen Index Kreutzung im Unendlichen ist
	///////Fall, dass beim aktuellen Index keine Kreuzung im Unendlichen ist
				
				borders[band] = crossings.get(i).crAt();
				band++;
				maxband = band;
			}
			step ++;
			if (DEBUG) {System.out.println("Intervalle eingeteilt!");}
			break;
		case 1:
			// find strip with odd number of intersections by binary search:
			boolean bluetop;  //TODO i think this is how bluetop should be initialized, someone review?
			if (leftborder) {
				int res = blueTop(leftb);
				if (res == 0) {
					if (DEBUG) {
						System.out.println("schnittpunkt zufaellig bei binaerer Suche gefunden!");
					}
					done = true;
					solution = new Point(-leftb, levelPos(leftb, true, levelBlue));
					return;
				}
				if (res == 1){
					bluetop = true;
				}
				else
					bluetop = false;
			}
			else{
				bluetop = blueTopLeft();
			}
			
			leftsetthistime = false;
			rightsetthistime = false;
				
			while ((maxband - minband) > 1) {
				int testband = minband + (maxband - minband) / 2; 
				int bluetesttop = blueTop(borders[testband]);
				if (bluetop == (bluetesttop == 1)) {
					minband = testband;
					leftborder = true;
					leftsetthistime = true;
				} else if (bluetop == (bluetesttop == -1)) {
					maxband = testband;
					rightborder = true;
					rightsetthistime = true;
				} else if (bluetesttop == 0) { // we have a winner!
					if (DEBUG) {System.out.println("schnittpunkt zufaellig bei binaerer Suche gefunden!");}
					done = true;
					solution = new Point(-borders[testband], levelPos(
							borders[testband], true, levelBlue));
					return;
				}

			}
			step ++;
			if (DEBUG) {System.out.println("Richtiges Intervall rausgesucht");}
			break;
		case 2:

			// grenzen nur setzen, falls wir wissen, dass da welche sind.
			if (leftborder && leftsetthistime) {
				leftb = borders[minband];
			}
			if (rightborder && rightsetthistime) {
				rightb = borders[maxband];
			}

			if (!leftborder && !rightborder) {
				if (DEBUG) {
					System.out.println("nope, this shouldn't ever happen. no bounds were set. do we even have crossings?");
				}
				return;
			}
			
			//prüfe, ob Betrachtungsbereich nur Kreuzungen bei - inf oder + inf hat und berechne 
			//in diesem Fall die Vertikale Lösung
			if (!leftborder && leftmannyC){
				System.out.println("Sind im Fall, dass es links viele Kreuzungen bei - inf gibt");
				done = true;
				verticalSol=true;
				//solution = verticalcut(maxband);
				//solution = verticalcut();
				verticalcut();//hier wierd verticalSolPos berechnet
				return;
			}
			if (!rightborder && rightmannyC){
				System.out.println("Sind im Fall, dass es links viele Kreuzungen bei - inf gibt");
				done=true;
				verticalSol=true;
				//solution=verticalcut(minband);
				//solution=verticalcut();
				verticalcut();//hier wierd verticalSolPos berechnet
				return;
			}

			int delta = (int) Math.round(eps * lBlue.size());
			//int delta = (int) (eps * lBlue.size()+1);
		
			int topLvl = levelBlue - delta;
			int botLvl = levelBlue + delta;
			if (true) { //sanity check
				if (levelBlue < 1 || levelBlue >= lBlue.size()) {
					System.out.println("REALLY BAD ERROR: yeah, levelBlue is fubar. go home and try again.");
				}
				if (topLvl < 1) {
					System.out.println("toplvl to small: fixing");
					topLvl = 1;
				}
				if (botLvl >= lBlue.size()) {
					System.out.println("botlvl to big: fixing");
					botLvl = lBlue.size();
				}
			}
			if (!leftborder || !rightborder) {

				if (!leftborder) { // nach rechts offen
					double tr = levelPos(rightb, true, topLvl);
					double br = levelPos(rightb, true, botLvl);
					double ts = getslope(true, topLvl);
					double bs = getslope(true, botLvl);
					trapeze = new Trapeze(true, rightb, tr, br, ts, bs);
					if (DEBUG) { System.out.println("making a trapeze open to the left:");}
					if (DEBUG) {System.out.println("rightb: "+rightb+" tr: "+tr+" br: "+br+" ts: "+ts+" bs: "+bs);}
					
				} else if (!rightborder) { // nach links offen
					double tl = levelPos(leftb, true, topLvl);
					double bl = levelPos(leftb, true, botLvl);
					double ts = getslope(true, lBlue.size()-topLvl);
					double bs = getslope(true, lBlue.size()-botLvl);
					trapeze = new Trapeze(false, leftb, tl, bl, ts, bs);
					if (DEBUG) {System.out.println("making a trapeze open to the right");}
					if (DEBUG) {System.out.println("rightb: "+rightb+" tl: "+tl+" bl: "+bl+" ts: "+ts+" bs: "+bs);}
				}

			} else {
				double tl = levelPos(leftb, true, topLvl);
				double tr = levelPos(rightb, true, topLvl);
				double bl = levelPos(leftb, true, botLvl);
				double br = levelPos(rightb, true, botLvl);
				if (DEBUG) {
					System.out.println("lefftb:"+leftb+" rightb:"+rightb+" tl:"+tl+" bl:"+bl+" tr:"+tr+" br:"+br);
					System.out.println("blue top at leftb: "+blueTop(leftb)+" blue top at rightb: "+blueTop(rightb));
				}
				trapeze = new Trapeze(leftb, tl, bl, rightb, tr, br);
			}
			step++;
			//trapeze = new Trapeze(true, -7, 1, -1, -3,3); debug gr�nde, ich glaube trapeze tun nicht ganz wie sie sollen
			if (DEBUG) {System.out.println("Trapez konstruiert");}
			borders = new double[64];
			minband = 0;
			maxband = 0;
			break;
		case 3:
			step ++;
			break;
		case 4:
			
			// cut away lines, count and make sure levelB/R are correct:
			int deleted = 0;
			for (int i = 0; i < lBlue.size();) {
				int s = trapeze.intersects(lBlue.get(i));
				if (s != 0) {
					if (s > 0) {
						levelBlue--;
					}
					hideLine(lBlue.get(i));
					deleted ++;
				} else {
					i++;
				}
			}
			for (int i = 0; i < lRed.size();) {
				int s = trapeze.intersects(lRed.get(i));
				if (s != 0) {
					if (s > 0) {
						levelRed--;
					}
					hideLine(lRed.get(i));
					deleted ++;
				} else {
					i++;
				}
			}
			step = 0;
			
			if (DEBUG) {System.out.println(deleted +" Linien ausserhalb des intervalls entfernt.");}
			if (deleted == 0) { //ya done goof'd
				done = true;
				return;
			}
			break;
		}
	}	
	
	public List<VisualPoint> getVisualPoints() {
		List<VisualPoint> result = new ArrayList<VisualPoint>();
		for (Point p : lBlue) {
			if (colorSwap) {
				VisualPoint newPoint = new VisualPoint(p.a, p.b, PointType.RED, false, p);
				result.add(newPoint);
			} else {
				VisualPoint newPoint = new VisualPoint(p.a, p.b, PointType.BLUE, false, p);
				result.add(newPoint);
			}
		}
		
		for (Point p : lRed) {
			if (colorSwap) {
				VisualPoint newPoint = new VisualPoint(p.a, p.b, PointType.BLUE, false, p);
				result.add(newPoint);
			} else {
				VisualPoint newPoint = new VisualPoint(p.a, p.b, PointType.RED, false, p);
				result.add(newPoint);
			}
		}
		
		for (Point p : lBlueDel) {
			if (colorSwap) {
				VisualPoint newPoint = new VisualPoint(p.a, p.b, PointType.RED, true, p);
				result.add(newPoint);
			} else {
				VisualPoint newPoint = new VisualPoint(p.a, p.b, PointType.BLUE, true, p);
				result.add(newPoint);
			}
		}
		
		for (Point p : lRedDel) {
			if (colorSwap) {
				VisualPoint newPoint = new VisualPoint(p.a, p.b, PointType.BLUE, true, p);
				result.add(newPoint);
			} else {
				VisualPoint newPoint = new VisualPoint(p.a, p.b, PointType.RED, true, p);
				result.add(newPoint);
			}
		}
		
		return result;
	}
}
