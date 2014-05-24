package hamSanApp;

public class Trapeze {
	Trapeze(double x1, double y_topleft, double y_botleft,double x2, double y_topright, double y_botright) {
		left = x1;
		right = x2;
		topleft = y_topleft;
		topright = y_topright;
		botleft = y_botleft;
		botright = y_botright;
	}
	double left;
	double right;
	double topleft;
	double topright;
	double botleft;
	double botright;
	
	public boolean intersects(Point i) { //TODO: testing
		double y1 = i.eval(left);
		double y2 = i.eval(right);
		if (((y1 < botleft) || ( y1 > topleft)) && ((y2 < botright) || (y2 > topright)))  {
			return false;
		}
		else {
			return true;
		}
	}
}
