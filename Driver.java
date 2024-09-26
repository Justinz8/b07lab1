import java.io.*;

public class Driver {
	public static void main(String [] args) {
		try {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		double [] c1 = {6,0,0,5};
		int [] d1 = {0,1,2,3};
		Polynomial p1 = new Polynomial(c1, d1);
		Polynomial p2 = new Polynomial(new File("./input.txt"));
		Polynomial s = p1.add(p2);
		
		System.out.println("s(0.1) = " + s.evaluate(0.1));
		if(s.hasRoot(1))
			System.out.println("1 is a root of s");
		else
			System.out.println("1 is not a root of s");
		
		new File("./test.txt");
		
		double[] c2 = {1, 2, 3};
		int[] d2 = {0, 1, 2};
		Polynomial p3 = new Polynomial(c2, d2);
		
		s.multiply(p3).saveToFile("./test.txt");
		} catch (Exception ex) {System.out.println(ex);}
	}
}