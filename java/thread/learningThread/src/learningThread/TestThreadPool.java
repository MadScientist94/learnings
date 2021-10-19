package learningThread;

import java.time.LocalTime;
//import java.util.*;
import java.util.concurrent.*;

public class TestThreadPool {  
	static LocalTime time = LocalTime.now();
    public static void main(String[] args) {  
       ExecutorService executor = Executors.newFixedThreadPool(5);//creating a pool of 5 threads  
       System.out.println("Current time: "+time);
       for (int i = 0; i < 10; i++) {  
           Runnable worker = new WorkerThread("" + i);  
           executor.execute(worker);//calling execute method of ExecutorService  
         }  
       System.out.println("Current time: "+time);
       executor.shutdown();  
       while (!executor.isTerminated()) {   }  
 
       System.out.println("Finished all threads");  
   }  
}  