package hamSanApp;

	/**
	 * diese Klasse stellt die Kreuzung zweier Geraden dar und ist haupts�chlich zum sortieren da.
	 * @author fabian
	 *
	 */
public class Crossing implements Comparable<Crossing> {
//die beiden Linien:
	public Point line1; // Linie mit kleinerem
	public Point line2; //Linie mit größerem Index 

	/**
	 * Konstruktor
	 */
	public Crossing(Point crossline1, Point crossline2){
		if (crossline1.i<=crossline2.i){
			line1=crossline1;
			line2=crossline2;
		}
		else{
			line1=crossline2;
			line2=crossline1;
		}
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
			//kommt Gerade mit kleinstem Index zweimal vor?
			//boolean idoppelt=false;
			//int j=0;
			/*if(this.line1.i==smallindex){j++;};
			if (this.line2.i==smallindex){j++;};
			if (other.line1.i==smallindex){j++;};
			if (other.line2.i==smallindex){j++;};*/
			//if (j>1){idoppelt=true;};
			//Fall: beide Kreuzungen sind im Unendlichen 
			//Sonderbehandlung für drei parallele Geraden notwendig.
			//Ergebnis hängt dann von y-Achsenabschnitt ab
			if(this.atInf() && other.atInf()){
				if (this.atNegInf()&&(other.atNegInf()==false))//this negativ, other positiv
					{return -1;}
				else if (this.atNegInf()==false &&(other.atNegInf()))//this positiv, other negativ
					{return 1;}
				else if (this.atNegInf() && other.atNegInf()) {//this neg, oth neg //bei jedem Kreuzungspaar ist Gerade mit kleinerem Index weiter oben
					//Theorie: Kreuzungspaar mit kleinerem Index ist weiter rechts
					if (smallindex == this.line1.i){ 
						if(smallindex==other.line1.i){//Sonderfall: 3 Parallele Geraden
							if(this.line2.b>other.line2.b){
								return 1;
							}
							else{return -1;}
						}
						else{
							return 1;
						}
						
					}
					else{
						return -1;
					}				
				}
				else if (this.atNegInf()==false && other.atNegInf()==false){//this positiv, other positiv
					if ((smallindex == this.line1.i) || (smallindex == this.line2.i)){
					return -1;
					}
					else{
						return 1;
					}
					
				}
			}
			//Fall: eine Kreuzung ist im Unendlichen 
			else{
				if (this.atInf()&& this.atNegInf()){
					return -1;
				}
				else if (this.atInf()&& this.atNegInf()==false){
					return 1;
				}
				else if (other.atInf()&& other.atNegInf()){
					return 1;
				}
				else if (other.atInf()&& other.atNegInf()==false){
					return -1;
				}
			//Fall: Alle Kreuzungen sind nicht im Unendlichen und Schnittpunkte liegen nicht übereinander
				else if (this.crAt()<other.crAt()){
						return -1;
				}
				else if (this.crAt()>other.crAt()){
						return 1;
				}
			//Fall: Alle Kreuzungen sind nicht im Unendlichn und Schnittpunkte liegen übereinander
				else if (this.crAt()==other.crAt()){
			//Fall: Gerade mit kleinstem Index kommt nicht in beiden Kreuzungen vor
					if (this.line1.i!=other.line1.i){
					//Fall: Kreuzung ist rechts von der Null oder auf der y-Achse
						if (this.crAt()>=0){
							if(this.line1.i<other.line1.i){//Kleinstes Indexpaar ist bei Kreuzungspaar this
								if(this.line1.a-this.line1.a>0){//Gerade mit kleinstem Index hat größere Steigung
									//Kreuzung this wandert nach links
									return -1;
								}
								else {return 1;}
							}
							else{//kleinster Index ist bei Krezungspaar other
								if(other.line1.a-other.line1.a>0){//Gerade mit kleinstem Index hat größere Steigung
									//Kreuzung other wandert nach links
									return 1;
								}
								else {return -1;}	
							}
							
						}
						else{//Kreuzungspunkt ist im Negativen Berich
							if(this.line1.i<other.line1.i){//Kleinstes Indexpaar ist bei Kreuzungspaar this
								if(this.line1.a-this.line1.a>0){//Gerade mit kleinstem Index hat größere Steigung
									//Kreuzung this wandert nach rechts
									return 1;
								}
								else {return -1;}
							}
							else{//kleinster Index ist bei Krezungspaar other
								if(other.line1.a-other.line1.a>0){//Gerade mit kleinstem Index hat größere Steigung
									//Kreuzung other wandert nach rechts
									return -1;
								}
								else {return 1;}	
							}
							
						}
					}
			//Fall: Gerade mit kleinstem Index kommt in beiden Kreuzungen vor
			//die beiden Kreuzungen werden also durch 3 Geraden gebildet, die sich in einem Punkt schneiden
					else{
						//Fall: Kreuzung ist rechts von der Null oder auf der y-Achse
						if (this.crAt()>=0){
							//Gerade mit kleinstem Index hat größte Steigung
							if(this.line1.a-this.line2.a>0 && this.line1.a-other.line2.a>0){
								if(this.line2.a-other.line2.a>0){//Vergleich der Steigungen der aneren beiden Geraden, die nicht den kleinsten Index haben
									return -1;
								}
								else {return 1;}
								
							}//Gerade mit kleinstem Index liegt zwischen anderen beiden Geraden(hat mittlere Steigung)
							//Gerade other.line2 liegt oberhalb von this.line1
							else if(this.line1.a-this.line2.a>0 && this.line1.a-other.line2.a<0){
								return -1;
								
							}//Gerade other.line2 liegt unterhalb von this.line1
							else if(this.line1.a-this.line2.a<0 && this.line1.a-other.line2.a>0){
								return 1;
							}
							//Gerade mit kleinstem Index hat kleinste Steigung
							else if(this.line1.a-this.line2.a<0 && this.line1.a-other.line2.a<0){
								if(this.line2.a-other.line2.a>0){//Vergleich der Steigungen der aneren beiden Geraden, die nicht den kleinsten Index haben
									return -1;
								}
								else {return 1;}
								
							}
							else{System.out.println("Fehlerhafte Abfrage in compareToCrosing, Kreuzung im Positiven");}
						}
						else{
						//Fall: Kreuzung ist im negativen Bereich
		
							//Gerade mit kleinstem Index hat größte Steigung
							if(this.line1.a-this.line2.a>0 && this.line1.a-other.line2.a>0){
								if(this.line2.a-other.line2.a>0){//Vergleich der Steigungen der aneren beiden Geraden, die nicht den kleinsten Index haben
									return 1;
								}
								else {return -1;}
								
							}//Gerade mit kleinstem Index liegt zwischen anderen beiden Geraden(hat mittlere Steigung)
							//Gerade other.line2 liegt oberhalb von this.line1
							else if(this.line1.a-this.line2.a>0 && this.line1.a-other.line2.a<0){
								return 1;
								
							}//Gerade other.line2 liegt unterhalb von this.line1
							else if(this.line1.a-this.line2.a<0 && this.line1.a-other.line2.a>0){
								return -1;
							}
							//Gerade mit kleinstem Index hat kleinste Steigung
							else if(this.line1.a-this.line2.a<0 && this.line1.a-other.line2.a<0){
								if(this.line2.a-other.line2.a>0){//Vergleich der Steigungen der aneren beiden Geraden, die nicht den kleinsten Index haben
									return 1;
								}
								else {return -1;}
								
							}
							else{
								System.out.println("Fehlerhafte Abfrage in compareToCrosing, Kreuzung im Negativen");
			
							}
							
						}//Ende Else Fall: Kreuzung im Negativbereich
						
					}//Ende Kreuzung werden durch 3 Geraden gebildet
				}//Ende : Kreuzungen liegen übereinaner
				
				
			}//Ende: Kreuzungen liegen nicht beide im Unendlichen
			
			

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
