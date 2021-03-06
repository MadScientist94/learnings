package learningThread;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
class WorkerThread implements Runnable {  

	 
    private String message;  
    public WorkerThread(String s){  
        this.message=s;  
    }  
     public void run() {  
        System.out.println(Thread.currentThread().getName()+" (Start) message = "+message);  
        processmessage();//call processmessage method that sleeps the thread for 2 seconds  
        System.out.println(Thread.currentThread().getName()+" (End)");//prints thread name  
    }  
    private void processmessage() { 
    	LocalTime time = LocalTime.now();
        try {  Thread.sleep(2000);System.out.println("Current time: "+time); time = LocalTime.now();System.out.println("Current time: "+time); } catch (InterruptedException e) { e.printStackTrace(); }  
    }  
}  