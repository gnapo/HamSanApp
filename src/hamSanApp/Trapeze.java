package hamSanApp;

	/**
	 * diese Klasse stellt ein Trapez dar, in dem sich die blaue medianlinie befindet
	 * @author fabian
	 *
	 */
public class Trapeze { // TODO was tun, wenn das trapez in einem unbegrenzten intervall ist?
	/**
	 * Konstruktor, alles ganz selbsterklärend
	 */
	Trapeze(double x1, double y_topleft, double y_botleft,double x2, double y_topright, double y_botright) {
		left = x1;
		right = x2;
		topleft = y_topleft;
		topright = y_topright;
		botleft = y_botleft;
		botright = y_botright;
	}
	double left; 	//linker Rand
	double right;	//rechter Rand
	double topleft; //
	double topright;//
	double botleft; //
	double botright;// die vier y-Werte zur Beschränkung
	
	/**
	 * Testet, ob eine Linie das Trapez schneidet
	 * @param i die zu testende Linie
	 * @return +1 wenn die linie obendrüber geht, 0 wenn sie schneidet, -1 wenn sie untendrunter geht
	 */
	public int intersects(Point i) { //TODO: testen
		
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
}
