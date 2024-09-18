import java.util.*;

public class Polynomial {
	double equation[];
	
	public Polynomial() {
		equation = new double[1];
	}
	public Polynomial(double current[]) {
		equation = current;
	}
	public Polynomial add(Polynomial poly2) {
		double newPoly[] = new double[Math.max(poly2.equation.length, equation.length)];
		for(int i = 0; i<newPoly.length; i++) {
			if(i<poly2.equation.length) newPoly[i]+=poly2.equation[i];
			if(i<equation.length) newPoly[i]+=equation[i];
		}
		return new Polynomial(newPoly);
	}
	public double evaluate(double n) {
		double ans = 0;
		for(int i = 0; i<equation.length; i++) {
			ans+=equation[i]*Math.pow(n, i);
		}
		return ans;
	}
	public boolean hasRoot(double n) {
		return evaluate(n)==0;
	}
}