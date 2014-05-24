package hamSanApp;

import java.util.ArrayList;
import java.util.List;

public class HamSanAlg {

	List<Point> lBlue;
	List<Point> lRed;
	List<Point> lBlueDel;
	List<Point> lRedDel;
	boolean leftborder;
	boolean rightborder;
	double leftb;
	double rightb;
	int levelBlue;
	int levelRed;
	boolean firstRun;
	boolean done;
	boolean verticalSol;
	double verticalSolPos;
	Point solution;
	
	final double alpha = 1.0d/32.0d;
	final double eps = 1.0d/8.0d;
	
	HamSanAlg(){
		init();
	}
	
	public void init() {
		lBlue = new ArrayList<Point>();
		lRed = new ArrayList<Point>();
		lBlueDel = new ArrayList<Point>();
		lRedDel = new ArrayList<Point>();
		leftborder = false;
		rightborder = false;
		leftb = 0;
		rightb = 0;
		firstRun = true;
		done = false;
		solution = null;
		verticalSol = false;
	}
	
	public void addLine(double x, double y, boolean blue){
		if (!firstRun) {return;}
		if (blue){
			lBlue.add(new Point(x, y));
		}
		else {
			lRed.add(new Point(x, y));
		}
	}
	
	public void removeLine(Point l) {
		if (!firstRun) {return;}
		lBlue.remove(l);
		lRed.remove(l);
	}
	
	public void hideLine(Point l) {
		if (lBlue.remove(l)) {
			lBlueDel.add(l);
		}
		if (lRed.remove(l)) {
			lRedDel.add(l);
		}
	}
	
	public void presentSolution() {
		if (!done) {
			System.out.println("algorithm not done yet :(");
		}
		if (verticalSol) {
			System.out.println("the solution is a vertical line with x = "+verticalSolPos);
		}
		else {
			System.out.print("the solution is the ");
			solution.repr_line();
		}
	}
	
	public void doAlg() { //sets done to true iff it has found a solution
		if (firstRun) {
			//make sure that both sets are odd by deleting a point out of each set:
			if ((lBlue.size()%2) == 0) {
				hideLine(lBlue.get(0));
			}
			if ((lRed.size()%2) == 0) {
				hideLine(lRed.get(0));
			}
			//set the levelBlue and levelRed to the correct values:
			levelBlue = (lBlue.size()+1/2);
			levelRed = (lRed.size()+1/2);
			firstRun = false; //so we don't change the points, and only do this once
		}
		
		//check if trivial solution:
		if (lBlue.size()==1 && lRed.size()==1) {
			Point b = lBlue.get(0);
			Point r = lRed.get(0);
			//do we need a vertical line?
			if (b.a == r.a) {
				done = true;
				verticalSol = true;
				verticalSolPos = b.a;
				return;
			}
			done = true;
			//find intersection point and return that. done!
			solution = new Point(b.cross(r),b.eval(b.cross(r)));
			return;
		}
		
		// swap the lines if blue is smaller//
		
		//generate all the crossings//
		List<Point> crossings = new ArrayList<Point>();
			//there are three kinds of crossings: blue-blue
			//									  blue-red
			// 									  red-red
		
		//sort them. crossings implements comparable.//
		
		//make stripes with at most alpha*(n choose 2) crossings a piece. //
		//find strip with odd number of intersections by binary search.//
		//figure out M //
		//construct Trapeze//
		//cut away lines, count and make sure levelB/R are correct//
	}
}
