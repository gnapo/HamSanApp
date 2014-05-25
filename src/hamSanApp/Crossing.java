package hamSanApp;

	/**
	 * diese Klasse stellt die Kreuzung zweier Geraden dar und ist hauptsächlich zum sortieren da.
	 * @author fabian
	 *
	 */
public class Crossing implements Comparable<Crossing> {

	Point a; //
	Point b; // die beiden Linien

	/**
	 * Konstruktor
	 */
	Crossing(Point x, Point y){
		a=x;
		b=y;
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
			return Point.op2naive(a, b, other.a, other.b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
