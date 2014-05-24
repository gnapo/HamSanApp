package hamSanApp;

public class Point {
		static int index = 0;
		Point(double x, double y) {
			a = x;
			b = y;
			i = Point.index;
			index +=1;
		}
		public double a;
		public double b;
		public final int i;
		public int ell = 5;
		
		public void repr_point(){
			System.out.println("point at "+a+" "+b);
		}
		public void repr_line(){
			System.out.println("line: y= "+a+"x + "+b);
		}
		public double eval(double x) {
			return a*x + b;
		}
		public double cross(Point other) {
			if (a == other.a) {return 0;}
			return (b-other.b)/(a-other.a);
		}
		
		public static double det3 (double a11, double a12, double a13,
								   double a21, double a22, double a23,
								   double a31, double a32, double a33) {
			return a11*a22*a33
					+a12*a23*a31
					+a13*a21*a32
					-a11*a23*a32
					-a12*a21*a33
					-a13*a22*a31;
		} 
		
		public static double det2 (double a11, double a12,
								   double a21, double a22) {
			return a11*a22-a12*a21;
		}
		
		/*do lines i and j intersect above k (1), below k (-1) or on k (0) ?*/
		public static int op1(Point i, Point j, Point k) {
			double Delta1;
			if (i.i < j.i) {
				Delta1 = det3(i.a,i.b,1,j.a,j.b,1,k.a,k.b,1);
			}
			else {
				Delta1 = det3(j.a,j.b,1,i.a,i.b,1,k.a,k.b,1);
			}
			
			System.out.println("determinant evaluated to "+Delta1);
			if (Delta1 > 0) { return 1;}
			if (Delta1 < 0) { return -1;}
			return 0;
		}
		
		public static int op1naive(Point i, Point j, Point k) {
			//calculate the crossing point of i and j:
			if (i.a != j.a){
				double x = (i.b-j.b)/(i.a-j.a);
				double y = i.a * x + i.b;
				double diff = y -( k.a * x + k.b);
				if (diff > 0) {return 1;}
				if (diff < 0) {return -1;}
				//TODO handle
				return 0;
			} 
			else {
				//they don't cross
				//TODO handle
				
				return -2;
			}
		}
		
		public static int op2naive(Point i, Point j, Point k, Point l) throws Exception {
			//if ij crosses left of kl, return -1, if right return +1
			if (i.equals(j)||i.equals(k)||i.equals(l)||j.equals(k)||j.equals(l)||k.equals(l)) 
				{throw new Exception("op2 was called with stupid arguments");}
			double diff1 = i.a - j.a;
			double diff2 = k.a - l.a;
			if (diff1 != 0 && diff2 != 0) {
				double x1 = i.cross(j);
				double x2 = k.cross(l);
				if (x1 < x2) {
					return 1;
				}
				else {
					if (x1 > x2) {
						return -1;
					}
					else {
						//find the smallest index of the four
						if (i.i < j.i && i.i < k.i && i.i < l.i) {
							if (diff1 > 0) {return -1;}
							else {return 1;}
						}
						if (j.i < i.i && j.i < k.i && j.i < l.i) {
							if (diff1 < 0) {return -1;}
							else {return 1;}
						}
						if (k.i < i.i && k.i < j.i && k.i < l.i) {
							if (diff2 < 0) {return -1;}
							else {return 1;}
						}
						if (l.i < i.i && l.i < j.i && l.i < k.i) {
							if (diff2 > 0) {return -1;}
							else {return 1;}
						}
						throw new Exception("no smallest index found, this shouldn't happen. :(");
					}
				}
			}
			else {
				if (diff1 != 0){
					if (k.i < l.i) {
						if (k.b > l.b) {return 1;}
						else {return -1;}
					}
					else { //k.i > l.i
						if (k.b > l.b) {return -1;}
						else {return 1;}
					}
				}
				if (diff2 != 0) {
					if (i.i < j.i) {
						if (i.b > j.b) {return -1;}
						else {return 1;}
					}
					else { //i.i > j.i
						if (i.b > j.b) {return 1;}
						else {return -1;}
					}
				}
				//sanity:
				if (diff1 == 0 && diff2 == 0) {
					if ((i.i < j.i && i.i < k.i && i.i < l.i) || (j.i < i.i && j.i < k.i && j.i < l.i)) {
						if (k.i < l.i) {
							if (k.b < l.b) {return -1;}
							else {return 1;}
						}
						else {
							if (k.b < l.b) {return 1;}
							else {return -1;}
						}
					}
					else { // k oder l haben kleinsten index
						if (i.i < j.i) {
							if (i.b < j.b) {return 1;}
							else {return -1;}
						}
						else {
							if (i.b < j.b) {return -1;}
							else {return 1;}
						}
					}
				}
				
				throw new Exception("uh, something went wrong comparing");
			}
		}
		public static int op3naive(Point i, Point j, Point k, Point l, Point m) {
			//sanity: make sure i,j,k,l pairwise distinct,
			//even need to do? make sure we need this.
			return 0;
		}
}
