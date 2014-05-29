/**
 * 
 */
package hamSanApp;

import java.util.Comparator;
//vergleicht zwei Linien nach ihrem Wert in Koordinate x. 
//Linien werden so sortiert, dass gilt: 
//Je größer der Wert der Geraden in Koordinate x, desto kleiner ist Gerade in Geradenordnung.
//Bei Gleichheit: ist value positiv, so ist Gerade mit kleinerem Index oberhalb
//ist value negativ, so ist Gerade mit kleinerem Index unterhalb
//ist value = 0 und Schneiden sich die Geraden im Positiven, 
//so ist die Gerade mit kleinerem Index oberhalb der anderen. 
// ist value = 0 und schneiden sich die Geraden im Negativen, so ist 
// die Gerade mit kleinerem Index unterhalb von der anderen. 

/**
 * @author annette
 *
 */
public class LineComparator implements Comparator<Point> {
	/**
	 * @param x Stelle zur Auswertung
	 */
	public LineComparator(double x) {
		super();
		this.x = x;
	}

	private double x;
	
	@Override
	public int compare(Point arg0, Point arg1) {
		double y0 = arg0.eval(x);
		double y1 = arg1.eval(x);
		if(y0 < y1)
			return 1;
		else if(y1 < y0)
			return -1;
		else
		{
			//y0==y1
			if(x > 0 || (x == 0 && y0 > 0))
			{
				if(arg0.i < arg1.i)
					return 1;
				else
					return -1;
			}
			else
			{
				if(arg0.i < arg1.i)
					return -1;
				else
					return 1;

			}
		}
	}

}
