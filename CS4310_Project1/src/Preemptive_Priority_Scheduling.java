import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Preemptive_Priority_Scheduling implements CPU_Scheduling{

	static List<ppsTask> taskList = new ArrayList<ppsTask>();
	static double turnAround[];
	static int art,att,cur;
	final static int DOUBLE_TO_INT =100;
	static PriorityQueue <ppsTask>pq = new PriorityQueue<ppsTask>();

	@Override
	public void run() throws FileNotFoundException {
		readFile("");
		sortByArriveTime();	
		int count=0;
		int time =0;
		int idle =0;
		
		ppsTask[] runnin=new ppsTask[1];
//		count+=addTaskToQueue(0);
		while(!pq.isEmpty()||count<taskList.size()||runnin[0]!=null) {
			count += addTaskToQueue(time);
			if(!pq.isEmpty()&&runnin[0]==null) {
				runnin[0]=pq.poll();
				runnin[0].setResponseTime(time-runnin[0].getArrival_Time());
			}
			if(runnin[0]!=null) {
				 if(replaceTask(runnin[0])) {
						 pq.add(runnin[0]);
	            		 runnin[0].killProcess();
	            		 runnin[0] = pq.poll();
	            		 runnin[0].setResponseTime(time-runnin[0].getArrival_Time());
	            	 }
				 if(!runnin[0].processing(time)) {
	            	 runnin[0].setCompleteTime(time);
	            	 System.out.println("Completion time of task "+runnin[0].getPid()+" is:"+runnin[0].getCompleteTime()/DOUBLE_TO_INT);
	            	 runnin[0].setTurnAroundTime();
	            	 runnin[0]=null;
	            	 }
			}
			else {
				System.out.println("No process running at this time");
				//if no process in queue
				idle++;
			}
			updateWaitTime();	
			time++;
		}	

		DecimalFormat nf = new DecimalFormat("#0.0");
		
		System.out.print("AWT: "+nf.format(getAWT())+"\nART: "+nf.format(getART())+"\nATT: "+nf.format(getATT())+"\nCpu utilization rate:"
				+ (time-idle)/time+"\n");
		
	}
	
	public boolean replaceTask(ppsTask t) {
		if(pq.isEmpty())
			return false;
		return pq.peek().getPriority()<t.getPriority();
	}

	
	@Override
	public double getART() {
		return getATT()-getAWT();
	}
	
	public double getATT() {
		//calculate turn around time
		double turnAround = 0;
		for(ppsTask t:taskList) {
			turnAround+=t.getTurnAroundTime();
		}
		return (turnAround/taskList.size())/DOUBLE_TO_INT;
	}	
	
	public double getAWT() {
		//calculate wait time
		double totalTime =0;
		for(ppsTask t:taskList) {
			totalTime +=t.getWaitTime();	
		}
		return (totalTime/taskList.size())/DOUBLE_TO_INT;		
	}
	
	public static void readFile(String fil) throws FileNotFoundException{
		//read file and store them in a list 
		Scanner scanner = new Scanner(new FileReader("/Applications/workspace/textFile.txt")); //path is changeable
		  try
		  {
		    while( scanner.hasNext() )
		    {
		    	int pid = 0,pri =0;
		    	double arrt=0,burt=0;
		               	
		        pid = scanner.nextInt(); 
		        arrt = scanner.nextDouble();
		        burt = scanner.nextDouble(); 
		        int burt1 = (int) (burt*DOUBLE_TO_INT);
		        int arrt1 = (int)(arrt*DOUBLE_TO_INT);
		        pri = scanner.nextInt();
		        taskList.add(new ppsTask(pid,arrt1,burt1,pri));  //add to list
      
		    }
		  } finally
		  {
			    scanner.close();
			  }
		  
	}
	
	public static void sortByArriveTime() {
		//simple sorting method to sort list by arrive time
		Collections.sort(taskList, new Comparator<ppsTask>() {
			@Override
			public int compare(ppsTask o1, ppsTask o2) {
				//compare by arrive time
				return o1.getArrival_Time()- o2.getArrival_Time();
			}
		});
	}
	
	public static int addTaskToQueue(int time) {
		//when one or more processes is ready, add them to ready queue 
		int taskAdded = 0;
		for(ppsTask t: taskList) {
			if(t.getArrival_Time() == time) {
				if(pq.add(t)) {
				System.out.println(t.getPid()+" is added to waiting queue");
				}
				taskAdded++;
			}
		}
		//record how many tasks were added at once
		return taskAdded;
	}
	
	public static void updateWaitTime() {
		//for all tasks already in ready queue, update their waiting time every millisecond 
		for(ppsTask t: pq) {
			t.updateWaitTime();
	}

}
}


class ppsTask implements Comparable<ppsTask>{
	//simple class that represent task object,
	//only contain basic data, getter, setter and complete time of each task
	private int Pid ;
	private int Arrival_Time ;
	private int Burst_Time ;
	private int Priority;
	private double completeTime;
	int lifeCycle = 0;//when the process is running, update its time
	private int waitTime = 0;
	//private int timeLeft=0;
	private double turnAroundTime =0;
	private double responseTime = -1;
	public ppsTask(int pid, int arrt, int burt, int pri) {
		Pid =pid;
		Arrival_Time = arrt;
		Burst_Time = burt;
		Priority = pri;
	//	System.out.print("Pid: "+Pid+"\nArrT: "+Arrival_Time+"\nBurT: "+Burst_Time+"\nPri: "+Priority+"\n");		
	}
	public void killProcess() {
		//lifeCycle =0;
	}
	public boolean processing(int i) {
		lifeCycle++;
		if(i%100==0) 
		System.out.println("Process "+Pid+" is running ");
		//when a process is running
		return lifeCycle != Burst_Time; //return if the process is finished
	}
	public void printSt() {
		System.out.print("Pid: "+Pid+"\nArrT: "+Arrival_Time+"\nBurT: "+Burst_Time+"\nPri: "+Priority+"\n");
	}
	
	//most below are basic getter and setter
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
	public int compareTo(ppsTask t) {
		return Priority- t.getPriority();
	}
	public double getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(double completeTime) {
		this.completeTime = completeTime;
	}
	public int getWaitTime() {
		return waitTime;
	}
	public void updateWaitTime() {
		//update individual wait time
		waitTime ++;
	}
	public double getTurnAroundTime() {
		return turnAroundTime;
	}
	public void setTurnAroundTime() {
		//calculate the turn around time of individual task
		this.turnAroundTime = completeTime - Arrival_Time+1;
	}
	public double getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(double responseTime) {
		if(this.responseTime==-1) {
		this.responseTime = responseTime;
		}
	}
}
	

