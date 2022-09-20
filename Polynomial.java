import java.utils.*;

public class Polynomial {
	public double[] coefficients;
	
	public Polynomial() {
		this.coefficients = new double[0];
	}
	
	public Polynomial(double[] coefficients) {
		for(int i=0; i<coefficients.length(); i++) {
			this.coefficients[i] = coefficients[i];
		}
	}
	
	public Polynomial add(Polynomial other) {
		for(int i=0; i<this.coefficients.length(); i++) {
			this.coefficients[i] += other.coefficients[i];
		}
		
		return this;
	}
	
	public int evaluate(double x) {
		double result = 0;
		
		for(int i=0; i<this.coefficients.length(); i++) {
			result += this.coefficients[i]*x;
		}
		
		return result;
	}
	
	public boolean hasRoot(double root) {
		double sum = 0;
		
		for(int i=0; i<this.coefficients.length(); i++) {
			sum += this.coefficients[i]*root;
		}
		
		return sum == 0;
	}
}