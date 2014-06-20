package hamSanApp;

	/**
	 * diese Klasse stellt die Kreuzung zweier Geraden dar und ist haupts�chlich zum sortieren da.
	 * @author fabian
	 *
	 */
public class Crossing implements Comparable<Crossing> {

	public Point line1; //
	public Point line2; // die beiden Linien

	/**
	 * Konstruktor
	 */
	public Crossing(Point crossline1, Point crossline2){
		line1=crossline1;
		line2=crossline2;
	}
	
	/**
	 * diese Methode wird aufgerufen, wenn man z.B. println(irgendein crossing) ausf�hrt.
	 */
	/*public String toString() {
		String r = "";
		//"Crossing of: "+this.a.toString()+" and "+this.b.toString()+" \n";
		if (atInf() && atNegInf()) {
			r +="Crossing of: "+this.a.toString()+" and "+this.b.toString()+" \n"+"crossing at -inf";
		}
		if (atInf() && !atNegInf()) {
			r +="Crossing of: "+this.a.toString()+" and "+this.b.toString()+" \n"+"crossing at +inf";
		}
		else {
			r +="Crossing of: "+this.a.toString()+" and "+this.b.toString()+" \n"+"crossing at "+ crAt();
		}
		return r;
	}*/
	public String toString() {
		String r = "";
		//"Crossing of: "+this.a.toString()+" and "+this.b.toString()+" \n";
		if (atInf() && atNegInf()) {
			r +="crossing at -inf";
		}
		else if (atInf() && !atNegInf()) {
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
		//returns -1 if this is more left (than other), 0 if this is other, 1 if this is more right (than other)
		if (other == null) {throw new NullPointerException("tried to compare to null. whoops.");}
		if (this.equals(other)) {return(0);}
		try {
			int smallindex=Math.min(Math.min(Math.min(this.line1.i,this.line2.i),other.line1.i),other.line2.i);
			//Fall: beide Kreuzungen sind im Unendlichen
			if(this.atInf() && other.atInf()){
				if (this.atNegInf()&&(other.atNegInf()==false))//this negativ, other positiv
					{return -1;}
				else if (this.atNegInf()==false &&(other.atNegInf()))//this positiv, other negativ
					{return 1;}
				else if (this.atNegInf() && other.atNegInf()) {//this neg, oth neg
					if ((smallindex == this.line1.i) || (smallindex == this.line2.i)){ //Theorie: Kreuzungspaar mit kleinerem Index ist weiter rechts
						return 1;
					}else {return -1;}
						
				}
			}

		//	return (-1)* Point.op2naive(a, b, other.a, other.b);
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
		return line1.a==line2.a;
	}
	
	/**
	 * funktion um herauszufinden, ob der schnittpunkt bei unendlich bei - oder bei + Unendlich ist
	 * @return true wenn bei -unendl
	 */
	public boolean atNegInf() {
		if (!atInf()) {return false;}
		if (line1.i<line2.i){
			if (line1.b < line2.b) {return false;}
			else {return true;}
		}
		else {
			if (line1.b < line2.b) {return true;}
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
			return line1.cross(line2);
		}
		else {
			return 0;
		}
	}
}
