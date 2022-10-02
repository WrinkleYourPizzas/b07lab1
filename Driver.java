import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver { 
	public static void main(String [] args) throws FileNotFoundException {
		 
		Polynomial p = new Polynomial(); 
		System.out.println(p.evaluate(3)); 
	  
		double[] c1 = {6,0,0,5}; 
		int[] e1 = {0,4,5,20};
		Polynomial p1 = new Polynomial(c1, e1);
		
		p1.print();
		
		double[] c2 = {0,-2,0,0,-9};
		int[] e2 = {0,3,4,5,6};
		Polynomial p2 = new Polynomial(c2, e2); 
		
		p2.print();
		
		Polynomial s = p1.add(p2); 
		System.out.println("s(0.1) = " + s.evaluate(0.1)); 
		 
		s.print();
		
		Polynomial m = p1.multiply(p1);
		
		m.print();
		m.saveToFile("output");
		
		Polynomial t = new Polynomial();
		
		t.print();
		
		File read = new File("test.txt");
		Scanner sc = new Scanner(read);
		System.out.println(sc.nextLine());
		Polynomial r = new Polynomial(read);
		
		r.print();
		
		if(s.hasRoot(1)) 
			System.out.println("1 is a root of s"); 
		else 
			System.out.println("1 is not a root of s"); 
	} 
} 