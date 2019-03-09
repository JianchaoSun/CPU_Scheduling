import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class RR implements CPU_Scheduling{
	static List<rrTask> taskList = new ArrayList<rrTask>();
	static double turnAround[];
	static int Time_quantum =0 ;
	final static int DOUBLE_TO_INT =100;
	static Queue <rrTask>pq = new LinkedList<rrTask>();
	
	
	public void run() throws FileNotFoundException {//when cpu start to function
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter time quantum");
		Time_quantum = sc.nextInt();
		readFile("");
		int count=0;
		int time =0;
		int idle =0;
		int quantum =0;
		rrTask[] runnin=new rrTask[1];
		sortByArriveTime();
		while(!pq.isEmpty()||count<taskList.size()||runnin[0]!=null) {
			count += addTaskToQueue(time++);
			if(!pq.isEmpty()&&runnin[0]==null) {
				runnin[0]=pq.poll();
				runnin[0].setArt(time);
			}
			if(quantum ==Time_quantum*DOUBLE_TO_INT) {
				pq.add(runnin[0]);
				runnin[0] = pq.poll();
				runnin[0].setArt(time);
				System.out.println("Switched job");
				quantum =0;
			}
			if(runnin[0]!=null) {
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
			quantum++;
		}

		DecimalFormat nf = new DecimalFormat("#0.0");
		
		System.out.print("AWT: "+nf.format(getAWT())+"\nART: "+nf.format(getART())+"\nATT: "+nf.format(getATT())+
				"\nReal ART: "+nf.format(getRealART())+"\nCpu utilization rate:"
				+ (time-idle)/time);
	}
	
	public double getATT() {
		//calculate turn around time
		double turnAround = 0;
		for(rrTask t:taskList) {
			turnAround+=t.getTurnAroundTime();
		}
		return (turnAround/taskList.size())/DOUBLE_TO_INT;
	}
	
	
	
	public double getAWT() {
		//calculate wait time
		double totalTime =0;
		for(rrTask t:taskList) {
			totalTime +=t.getWaitTime();
			
		}
		return (totalTime/taskList.size())/DOUBLE_TO_INT;
		
	}
	
	public static int addTaskToQueue(int time) {
		//when one or more processes is ready, add them to ready queue 
		int taskAdded = 0;
		for(rrTask t: taskList) {
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
		for(rrTask t: pq) {
			t.updateWaitTime();
		//	System.out.println(t.getPid()+" is updated time");
		}
	}
	
	public static void sortByArriveTime() {
		//simple sorting method to sort list by arrive time
		Collections.sort(taskList, new Comparator<rrTask>() {
			@Override
			public int compare(rrTask o1, rrTask o2) {
				//compare by arrive time
				return o1.getArrival_Time()- o2.getArrival_Time();
			}
		});
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
		        taskList.add(new rrTask(pid,arrt1,burt1,pri)); //add to list
		    }
		  } finally
		  {
			    scanner.close();
			  }
		  
	}


	@Override
	public double getART() {
		return getATT()-getAWT();
	}
	public double getRealART() {

		double rt = 0;
		for(rrTask t:taskList) {
			rt+=t.getArt();
		}
		return (rt/taskList.size())/DOUBLE_TO_INT;
	}
	

}

class rrTask implements Comparable<rrTask>{
	//simple class that represent task object,
	//only contain basic data, getter, setter and complete time of each task
	private int Pid ;
	private int Arrival_Time ;
	private int Burst_Time ;
	private int Priority;
	private double completeTime;
	int lifeCycle = 0;//when the process is running, update its time
	private int waitTime = 0;
	private double turnAroundTime =0;
	private double art =-1;
	public rrTask(int pid, int arrt, int burt, int pri) {
		Pid =pid;
		Arrival_Time = arrt;
		Burst_Time = burt;
		Priority = pri;
	//	System.out.print("Pid: "+Pid+"\nArrT: "+Arrival_Time+"\nBurT: "+Burst_Time+"\nPri: "+Priority+"\n");		
	}
	public boolean processing(int i) {
		lifeCycle++;
		if(i%100==0) {
		System.out.println("Process "+Pid+" is running");
		}
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
	public int compareTo(rrTask t) {
		return Arrival_Time- t.getArrival_Time();
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
		this.turnAroundTime = completeTime - Arrival_Time;
	}
	public double getArt() {
		return art;
	}
	public void setArt(double ar) {
		if(art==-1) {
			art = ar - Arrival_Time;
		}
	}
	
}