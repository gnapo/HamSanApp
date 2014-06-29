package hamSanApp;

	/**
	 * diese Klasse stellt ein Trapez dar, in dem sich die blaue medianlinie befindet
	 * @author fabian
	 *
	 */
public class Trapeze { // TODO was tun, wenn das trapez in einem unbegrenzten intervall ist?
	/**
	 * Konstruktor, alles ganz selbsterkl�rend
	 */
	Trapeze(double x1, double y_topleft, double y_botleft,double x2, double y_topright, double y_botright) {
		left = x1;
		right = x2;
		topleft = y_topleft;
		topright = y_topright;
		botleft = y_botleft;
		botright = y_botright;
		bounded = true;
		openleft = false;
	}
	
	/**
	 * Konstruktor f�r ein unbeschr�nktes trapez
	 * @param left ist das Trapez nach links unbeschr�nkt?
	 * @param top der gr��ere y-wert
	 * @param bot der kleinere y-wert
	 * @param topslope die Steigungsgrenze oben
	 * @param botslope die Steigungsgrenze unten
	 */
	Trapeze(boolean oleft,double x, double top, double bot, double tslope, double bslope) {
		openleft = oleft;
		if (oleft) {
			right = x;
			topright = top;
			botright = bot;
		}
		else {
			left = x;
			topleft = top;
			botleft = bot;
		}
		topslope = tslope;
		botslope = bslope;
	}
	
	public double left; 		//linker Rand
	public double right;		//rechter Rand
	public double topleft; 	//
	public double topright;	//
	public double botleft; 	//
	public double botright;	// die vier y-Werte zur Beschr�nkung
	public boolean bounded;	//ist das Trapez beschr�nkt?
	public boolean openleft;	//ist das unbeschr�nkte Trapez nach links offen?
	public double topslope;	//
	public double botslope;	// die steigungsgrenzen des Trapezes oben und unten.
	/**
	 * Testet, ob eine Linie das Trapez schneidet
	 * @param i die zu testende Linie
	 * @return +1 wenn die linie obendr�ber geht, 0 wenn sie schneidet, -1 wenn sie untendrunter geht
	 */
	public int intersects(Point i) { //TODO: testen
		
		if (bounded) {
			double y1 = i.eval(left);
			double y2 = i.eval(right);
			if ((y1 < botleft) && ( y2 < botright)) {
				return -1;
			}
			if ((y1 > topleft) && (y2 > topright))  {
				return 1;
			}
			else {
				return 0;
			}
		}
		else {
			if (openleft) {
				double y = i.eval(right);
				if (y>topright) {
					if (i.a < topslope) {
						return 1;
					}
					else return 0;
				}
				if (y< botright) {
					if (i.a > botslope) {
						return -1;
					}
					else return 0;
				}
				return 0;
			}
			else
			{
				double y = i.eval(left);
				if (y>topleft) {
					if (i.a > topslope) {
						return 1;
					}
					else return 0;
				}
				if (y< botright) {
					if (i.a < botslope) {
						return -1;
					}
					else return 0;
				}
				return 0;
			}
		}
	}
}
