package learningThread;


class TestSleepMethod1 extends Thread{  
	 public void run(){  
	  if (false) {
		  System.out.println(Thread.currentThread());
	  }
	  else {
		 for(int i=1;i<5;i++){  
	    try{Thread.sleep(500);}catch(InterruptedException e){System.out.println(e);}  
	    System.out.println(i);  
		 }
	    }  
	 }  
}
	 
public  class ThreadMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		  System.out.println(Thread.currentThread());
		TestSleepMethod1 t1=new TestSleepMethod1();  
		TestSleepMethod1 t2=new TestSleepMethod1();  
		TestSleepMethod1 t3=new TestSleepMethod1();  
		 System.out.println("Name of t1:"+t1.getName());  
		  System.out.println("Name of t2:"+t2.getName());  
		  System.out.println("id of t1:"+t1.getId());  
		  System.out.println("id of t2:"+t2.getId()); 
//		  t1.setName("Sonoo Jaiswal");  
		  t1.setPriority(Thread.MIN_PRIORITY);  
		  t2.setPriority(Thread.MAX_PRIORITY); 
		  System.out.println("After changing name of t1:"+t1.getName());  
		 
		  t1.start();  
//		  try{  
//			  t1.join();  
//			 }catch(Exception e){System.out.println(e);}  
//		  try{  
//			  t1.join(1500);  
//			 }catch(Exception e){System.out.println(e);}  
//			  
		  t2.start();  
		  t3.start();
		  try{  
			  t2.join();  
			 }catch(Exception e){System.out.println(e);}  
			  
//		  t1.start();  it will generate an exception
//		   java.lang.IllegalThreadStateException
//		  when a thread start twice
	}

}
