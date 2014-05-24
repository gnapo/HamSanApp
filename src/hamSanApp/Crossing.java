package hamSanApp;

public class Crossing implements Comparable<Crossing> {

	Point a;
	Point b;
	Point cr;
	boolean atInf= false;
	Crossing(Point x, Point y){
		a=x;
		b=y;
		if (x.a == y.a) {
			atInf = true;
		}
		else {
			cr = new Point(x.cross(y),x.eval(x.cross(y)));
		}
	}
	
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
