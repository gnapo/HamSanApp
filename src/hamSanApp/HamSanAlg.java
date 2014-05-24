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
		if (lBlue.contains(l)) {
			lBlue.remove(l);
		}
		if (lRed.contains(l)) {
			lRed.remove(l);
		}
	}
	
	public void doAlg() {
		if (firstRun) {
			//make sure that both sets are odd by deleting a point out of each set// 
			//set the levelBlue and levelRed to the correct values:
			levelBlue = (lBlue.size()+1/2);
			levelRed = (lRed.size()+1/2);
			firstRun = false; //so we don't change the points
		}
		
		//check if trivial solution:
		if (lBlue.size()==1 && lRed.size()==1) {
			//find intersection point and return that. done!//
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
