package Xpath;

public class First {

	public static void main(String[] args) {
		System.out.print(sum (5,5));
		validate(3);
		System.out.println("end");
		

	}
	
	private static int sum(int a,int b) {
		
		
		
		return a+b;
	}
	
	private static void validate(int n) {
		if(n%2==0)
			System.out.println("even");
		else
			System.out.println("odd");
	try {
		Thread.sleep(10000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
