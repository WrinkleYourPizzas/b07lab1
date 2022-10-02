import java.io.*;
import java.util.*;

public class Polynomial {
	public double[] coefficients;
	public int[] exponents;
	
	public Polynomial() {
		this.coefficients = new double[1];
		this.exponents = new int[1];
		
		this.coefficients[0] = 0;
		this.exponents[0] = 0;
	}
	
	public Polynomial(double[] coefficients, int[] exponents) {
		this.coefficients = new double[coefficients.length];
		this.exponents = new int[coefficients.length];
		
		for(int i=0; i<coefficients.length; i++) {
			this.coefficients[i] = coefficients[i];
			this.exponents[i] = exponents[i];
		}
	}
	
	public Polynomial (File file) {
		try {
			Scanner reader = new Scanner(file);
			String polynomialStr = reader.nextLine();
			
			String terms[] = polynomialStr.split("((?<=-)|(?=-)|(?<=\\+)|(?=\\+))|X|x");
			
			ArrayList<Double> tempCoef = new ArrayList<Double>();
			ArrayList<Integer> tempExp = new ArrayList<Integer>();
			
			int negative = 1;
			
			for(int i=0; i<terms.length; i++) {
				if(terms[i].equals("-")) {
					negative = -1;
					continue;
				}
				else if(terms[i].equals("+")) {
					negative = 1;
					continue;
				}
				
				if(terms[i+1].equals("-") | terms[i+1].equals("+")) {
					tempCoef.add(Double.parseDouble(terms[i]) * negative);
					tempExp.add(0);
				}
				else {
					tempCoef.add(Double.parseDouble(terms[i]) * negative);
					tempExp.add(Integer.parseInt(terms[i+1]));
					i++;
				}
			}
			
			this.coefficients = new double[tempCoef.size()];
			this.exponents = new int[tempExp.size()];
			
			for(int j=0; j<tempCoef.size(); j++) {
				this.coefficients[j] = tempCoef.get(j);
				this.exponents[j] = tempExp.get(j);
			}
			
			reader.close();
				
		} catch(FileNotFoundException e) {
			System.out.println("An error occured while reading file");
		}
	}
	
	public Polynomial add(Polynomial other) {
		int largest = 0;
		
		for(int i=0; i<this.exponents.length; i++)
			if(this.exponents[i] > largest)
				largest = this.exponents[i];
		
		for(int j=0; j<other.exponents.length; j++)
			if(other.exponents[j] > largest)
				largest = other.exponents[j];
		
		double[] tempCoef = new double[largest+1];
		int[] tempExp = new int[largest+1];
		
		for(int l=0; l<largest+1; l++) {
			tempExp[l] = l;
			tempCoef[l] = 0;
		}
		
		for(int k=0; k<this.exponents.length; k++)
			tempCoef[this.exponents[k]] += this.coefficients[k];
		
		for(int h=0; h<other.exponents.length; h++)
			tempCoef[other.exponents[h]] += other.coefficients[h];
		
		largest = 0;
		
		for(int n=0; n<tempCoef.length; n++)
			if(tempCoef[n] != 0)
				largest++;
		
		this.coefficients = new double[largest];
		this.exponents = new int[largest];
		
		int index = 0;
		
		for(int m=0; m<tempCoef.length; m++) {
			if(tempCoef[m] != 0) {
				this.exponents[index] = tempExp[m];
				this.coefficients[index] = tempCoef[m];
				index++;
			}
		}
		
		return this;
	}
	
	public Polynomial multiply(Polynomial other) {
		TreeMap<Integer, Double> tree = new TreeMap<Integer, Double>();
		
		for(int i=0; i<this.coefficients.length; i++) {
			for(int j=0; j<other.coefficients.length; j++) {
				int key = this.exponents[i]+other.exponents[j];
				
				if(tree.containsKey(this.exponents[i] + other.exponents[j]))
					tree.replace(key, tree.get(key) + this.coefficients[i]*other.coefficients[j]);
					
				else
					tree.put(key, this.coefficients[i]*other.coefficients[j]);
			}
		}
		
		int[] resultExp = new int[tree.size()];
		double[] resultCoef = new double[tree.size()];
		int index = 0;
		
		for(Map.Entry<Integer, Double> entry : tree.entrySet()) {
			resultExp[index] = entry.getKey();
			resultCoef[index] = entry.getValue();
			index++;
		}
		
		this.coefficients = resultCoef.clone();
		this.exponents = resultExp.clone();
		
		return this;
	}
	
	public double evaluate(double x) {
		double result = 0;
		
		for(int i=0; i<this.exponents.length; i++)
			result += this.coefficients[i] * Math.pow(x, this.exponents[i]);
		
		return result;
	}
	
	public boolean hasRoot(double root) {
		return Double.compare(this.evaluate(root), 0) == 0;
	}
	
	public void saveToFile(String polynomialStr) {
		polynomialStr = polynomialStr + ".txt";
		

		File output = new File(polynomialStr);
		
		try {
			output.createNewFile();
			
			FileWriter writer = new FileWriter(polynomialStr);
			
			for(int i=0; i<this.coefficients.length; i++) {
				if(this.coefficients[i] > 0 && i>0)
					writer.write("+" + Double.toString(this.coefficients[i]));
				else
					writer.write(Double.toString(this.coefficients[i]));
				
				if(this.exponents[i] != 0) 
					writer.write("X" + Integer.toString(this.exponents[i]));
			}
			
			writer.close();
			
		} catch (IOException e) {
			System.out.println("An error occured while creating and writing to file");
		}
	}
	
	public int find(int[] array, int query) {
		for(int i=0; i<array.length; i++)
			if(array[i] == query)
				return i;
		return -1;
	}
	
	public void print() {
		for(int i=0; i<this.coefficients.length; i++) {
			if(this.coefficients[i] > 0 && i>0)
				System.out.print("+" + Double.toString(this.coefficients[i]));
			else
				System.out.print(Double.toString(this.coefficients[i]));
			
			if(this.exponents[i] != 0) 
				System.out.print("X" + Integer.toString(this.exponents[i]));
		}
		System.out.println("");
	}
}