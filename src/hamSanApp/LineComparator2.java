package hamSanApp;

import java.util.Comparator;

/**
 * comparator für blueTopleft
 * @author fabian
 *
 */
public class LineComparator2 implements Comparator<Point> {

	@Override
	public int compare(Point x, Point y) {
		if (x.equals(y)) return 0;
		if (x.a == y.a) {
			if (x.i < y.i) return -1;
			else return 1;
		}
		if (x.a < y.a) return 1;
		else return -1;
	}

}
