package hamSanApp;

	/**
	 * diese Klasse stellt die Kreuzung zweier Geraden dar und ist haupts�chlich zum sortieren da.
	 * @author fabian
	 *
	 */
public class Crossing implements Comparable<Crossing> {

	public Point a; //
	public Point b; // die beiden Linien

	/**
	 * Konstruktor
	 */
	Crossing(Point x, Point y){
		a=x;
		b=y;
	}
	
	/**
	 * diese Methode wird aufgerufen, wenn man z.B. println(irgendein crossing) ausf�hrt.
	 */
	public String toString() {
		String r = "";
		//"Crossing of: "+this.a.toString()+" and "+this.b.toString()+" \n";
		if (atInf() && atNegInf()) {
			r +="crossing at -inf";
		}
		if (atInf() && !atNegInf()) {
			r +="crossing at +inf";
		}
		else {
			r +="crossing at "+ crAt();
		}
		return r;
	}
	
	/**
	 * vergleichsfunktion. funktioniert so, wie im Interface vorausgesetzt
	 */
	@Override
	public int compareTo(Crossing other) { //TODO: test this a bit
		//returns -1 if this is left than other, 0 if this is other, 1 if this is to the right
		if (other == null) {throw new NullPointerException("tried to compare to null. whoops.");}
		if (this.equals(other)) {return(0);}
		try {
			return (-1)* Point.op2naive(a, b, other.a, other.b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * funktion um herauszufinden, ob der Schnittpunkt sich bei +- Unendlich befindet
	 * @return true wenn ja
	 */
	public boolean atInf() {
		return a.a==b.a;
	}
	
	/**
	 * funktion um herauszufinden, ob der schnittpunkt bei unendlich bei - oder bei + Unendlich ist
	 * @return true wenn bei -unendl
	 */
	public boolean atNegInf() {
		if (a.i<b.i){
			if (a.b < b.b) {return false;}
			else {return true;}
		}
		else {
			if (a.b < b.b) {return true;}
			else {return false;}
		}
	}
	
	/**
	 * hilfsfunktion, um schnell auf den x-wert des Schnittpunkts zuzugreifen.
	 * CRosses AT
	 * @return der X-wert, falls es einen gibt, sonst 0.
	 */
	public double crAt() {
		if (!atInf()) {
			return a.cross(b);
		}
		else {
			return 0;
		}
	}
}
