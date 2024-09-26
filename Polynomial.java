import java.io.*;
import java.util.*;

public class Polynomial {
	double coefficient[];
	int degree[];
	
	public Polynomial() {
		coefficient = new double[0];
		degree = new int[0];
	}
	public Polynomial(double coefficient[], int degree[]) {
		this.coefficient = coefficient.clone();
		this.degree = degree.clone();
	}
	public Polynomial(File file) throws IOException {
		Scanner sc = new Scanner(file);
		
		String poly = sc.nextLine();
		String parsedPoly[] = (poly.charAt(0)+poly.substring(1).replace("-", "+-")).split("\\+");
		int polysize = parsedPoly.length;
		
		int newDegree[] = new int[polysize];
		double newCoeff[] = new double[polysize];
		
		for(int i = 0; i<polysize; i++) {
			String parsedTerm[] = parsedPoly[i].split("x");
			if(parsedTerm.length == 1) {
				newDegree[i] = 0;
				if(parsedPoly[i].contains("x")) newDegree[i] = 1;
				newCoeff[i] = Double.parseDouble(parsedTerm[0]);
			}else {
				newDegree[i] = Integer.parseInt(parsedTerm[1]);
				newCoeff[i] = Double.parseDouble(parsedTerm[0]);
			}
		}
		
		degree = newDegree;
		coefficient = newCoeff;
	}
	
	public void saveToFile(String fileName) throws FileNotFoundException {
		PrintStream output = new PrintStream(fileName);
		for(int i = 0; i<coefficient.length; i++) {
			if(coefficient[i]==0) continue;
			if(coefficient[i]>0 && i!=0) output.append('+');
			if(coefficient[i] == (int) coefficient[i]) {
				output.append(((int) coefficient[i])+"");
			}else {
				output.append(coefficient[i]+"");
			}
			if(degree[i]==0) continue;
			output.append('x');
			if(degree[i]==1) continue;
			output.append(degree[i]+"");
		}
	}
	
	public Polynomial add(Polynomial poly2) {
		
		int maxDegree = 0;//largest degree between both polys
		
		for(int i:degree) maxDegree = Math.max(maxDegree, i);
		for(int i:poly2.degree) maxDegree = Math.max(maxDegree, i);
		
		double tmpPoly[] = new double[maxDegree+1];
		
		int PolyLength = 0; //track how many distinct degrees there are
		
		for(int i = 0; i<degree.length; i++) {
			if(tmpPoly[degree[i]]==0 && coefficient[i]!=0) PolyLength++;
			tmpPoly[degree[i]]+=coefficient[i]; //add coefficient to this degree
		}
		
		for(int i = 0; i<poly2.degree.length; i++) {
			if(tmpPoly[poly2.degree[i]]==0 && poly2.coefficient[i]!=0) PolyLength++;
			tmpPoly[poly2.degree[i]]+=poly2.coefficient[i];
		}
		
		double newCoeff[] = new double[PolyLength];
		int newDegree[] = new int[PolyLength];
		
		int idx = 0;
		
		for(int i = 0; i<maxDegree+1; i++) {
			if(tmpPoly[i]!=0) { //if coefficient for this degree is 0 then skip
				newCoeff[idx] = tmpPoly[i];
				newDegree[idx] = i;
				idx++;
			}
		}
		
		return new Polynomial(newCoeff, newDegree);
	}
	
	public Polynomial multiply(Polynomial poly2) {
		int maxDegree = 0;
		for(int i = 0; i<degree.length; i++) {
			for(int j = 0; j<poly2.degree.length; j++) {
				maxDegree = Math.max(maxDegree, degree[i]+poly2.degree[j]); //find max degree from multiplying
			}
		}
		double tmpPoly[] = new double[maxDegree+1];
		
		int polyLength = 0; //track number of distinct degrees
		
		for(int i = 0; i<degree.length; i++) {
			for(int j = 0; j<poly2.degree.length; j++) { //if coefficient for this degree is 0 then skip
				int newD = degree[i]+poly2.degree[j];
				if(tmpPoly[newD]==0 && coefficient[i]*poly2.coefficient[j]!=0) polyLength++;//if distinct degree then increment count
				tmpPoly[newD] += coefficient[i]*poly2.coefficient[j];
			}
		}
		
		int newDegree[] = new int[polyLength];
		double newCoefficient[] = new double[polyLength];
		int idx = 0;
		
		for(int i = 0; i<maxDegree+1; i++) {
			if(tmpPoly[i]!=0) {
				newDegree[idx] = i;
				newCoefficient[idx] = tmpPoly[i];
				idx++;
			}
		}
		return new Polynomial(newCoefficient, newDegree);
	}
	
	public double evaluate(double n) {
		double ans = 0;
		for(int i = 0; i<coefficient.length; i++) {
			ans+=coefficient[i]*Math.pow(n, degree[i]);
		}
		return ans;
	}
	public boolean hasRoot(double n) {
		return evaluate(n)==0;
	}
}