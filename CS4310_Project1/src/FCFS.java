import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.TimerTask;

public class FCFS implements CPU_Scheduling{
	static List<Task> taskList = new ArrayList<Task>();
	static double turnAround[];
	static int art,att,cur;
	
	public static void main(String[] args) throws FileNotFoundException {
		readFile("");
		Collections.sort(taskList);
	/*	printProcess();
    	System.out.println("Awt:"+getAWT());
		System.out.println("Att:"+findATT());
		System.out.println("Art:"+findART());
		*/
	}
	
	public double getATT() {
		double totalAtt =0;
		//we know turn around time equal complete time - arrive time
		//and we don't know complete time, so we want to calculate first
		turnAround = new double[taskList.size()];
		turnAround[0] = taskList.get(0).getBurst_Time();
		//turn around for first task is just its burst time
		taskList.get(0).setCompleteTime(taskList.get(0).getBurst_Time()+
				taskList.get(0).getArrival_Time());//find complete time for first task
		
		
		totalAtt+=turnAround[0];
		
		for(int i=1;i<turnAround.length;i++) {
			taskList.get(i).setCompleteTime(findCompleteTime(taskList.get(i-1).
					getCompleteTime(),taskList.get(i)));
			//find complete time for each task using its previous task's complete time
			turnAround[i] = taskList.get(i).getCompleteTime()-
					taskList.get(i).getArrival_Time();	
			//since we will need turn around time to find waiting time,
			//i store them in a global array,just complete time-arrive time
			totalAtt+=turnAround[i];
		}
		return totalAtt/turnAround.length;		
	}
	
	public double getAWT() {
		getATT();//since waiting time equals turn around time-burst time, we find turn around time first
		double totalW =0;
		for(int i=0;i<taskList.size();i++) {
			totalW+= turnAround[i] - taskList.get(i).getBurst_Time();
			//calculate each waiting time and add them together
		}
		return totalW/taskList.size(); //find average time
	}
	
	public double getART() {
		return getATT()-getAWT();
	}
	
	public static double findCompleteTime(double t1, Task t2) {
	//find complete time of tasks
		if(t1 <=t2.getArrival_Time()) {
			return t2.getArrival_Time() + t2.getBurst_Time();
			/*if current task arrive after previous task complete
			 * complete time will just be its arrive time +
			 * its burst time
			 */
		}
		return t1 +t2.getBurst_Time();	
		/*if current task arrive before previous task complete,
		 * need to wait for previous to finish
		 * complete time will be previous complete time + current burst time	
		 */
	}
	
	public void run() throws FileNotFoundException {
		//simple process to print task running using thread
		readFile("");
		for(int i=0;i<taskList.size();i++) {
			for(int j=0;j<taskList.get(i).getBurst_Time();j++) {
		try {
	        Thread.sleep(1);
	    } catch(Exception e) {
	        System.out.println("Exception : "+e.getMessage());
	    }
		String st = "Processing task "+taskList.get(i).getPid();
        System.out.println(st);
			}
		}
		/*f
		 * Timer timer = new Timer();
		 * or(int i=0;i<taskList.size();i++) {
			String st = "Processing task "+taskList.get(i).getPid();
		    timer.schedule(new PrintTask(st), 1, taskList.get(i).getBurst_Time());
		}
		*/
	}
	
	
	public static void readFile(String fil) throws FileNotFoundException{
		//read file and store them in a list 
		Scanner scanner = new Scanner(new FileReader("/Applications/workspace/textFile.txt")); //path is changeable
		  try
		  {
		    while( scanner.hasNext() )
		    {
		    	int pid = 0,arrt=0,burt=0,pri=0;
		               	
		        pid = scanner.nextInt(); 
		        arrt = scanner.nextInt();
		        burt = scanner.nextInt(); 
		        pri = scanner.nextInt();
		        taskList.add(new Task(pid,arrt,burt,pri)); //add to list
      
		    }
		  } finally
		  {
			    scanner.close();
			  }
		  
	}

}
class PrintTask extends TimerTask {
	String st="";
	public PrintTask(String st) {
		this.st = st;
	}
    public void run() {
       System.out.println(st); 
    }
}


class Task implements Comparable<Task>{
	//simple class that represent task object,
	//only contain basic data, getter, setter and complete time of each task
	private int Pid ;
	private int Arrival_Time ;
	private int Burst_Time ;
	private int Priority;
	private double completeTime;
	public Task(int pid, int arrt, int burt, int pri) {
		Pid =pid;
		Arrival_Time = arrt;
		Burst_Time = burt;
		Priority = pri;
	//	System.out.print("Pid: "+Pid+"\nArrT: "+Arrival_Time+"\nBurT: "+Burst_Time+"\nPri: "+Priority+"\n");
		
	}
	public void printSt() {
		System.out.print("Pid: "+Pid+"\nArrT: "+Arrival_Time+"\nBurT: "+Burst_Time+"\nPri: "+Priority+"\n");
	}
	public int getPid() {
		return Pid;
	}
	public void setPid(int pid) {
		Pid = pid;
	}
	public int getArrival_Time() {
		return Arrival_Time;
	}
	public void setArrival_Time(int arrival_Time) {
		Arrival_Time = arrival_Time;
	}
	public int getBurst_Time() {
		return Burst_Time;
	}
	public void setBurst_Time(int burst_Time) {
		Burst_Time = burst_Time;
	}
	public int getPriority() {
		return Priority;
	}
	public void setPriority(int priority) {
		Priority = priority;
	}
	//task class implements comparable so i can sort it easily 
	@Override
	public int compareTo(Task t) {
		return Arrival_Time- t.getArrival_Time();
	}
	public double getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(double completeTime) {
		this.completeTime = completeTime;
	}
	
}
