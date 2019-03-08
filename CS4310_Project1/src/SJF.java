import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class SJF implements CPU_Scheduling{
	static List<sjfTask> taskList = new ArrayList<sjfTask>();
	static double turnAround[];
	static int art,att,cur;
	static PriorityQueue <sjfTask>pq = new PriorityQueue<sjfTask>();
	
	public static void main(String[]args) throws FileNotFoundException {
		readFile("");
	/*	run();
		System.out.println("The average waiting time: "+getAVT());
		System.out.println("The average turn around time: "+getATT());
		for(sjfTask s: taskList) {
			pq.add(s);
		}
		
		for(sjfTask s: taskList) {
			System.out.println(pq.poll().getArrival_Time());
		}
		*/
	}
	
	public void run() throws FileNotFoundException {//when cpu start to function
		readFile("");
		int count=0;
		int time =0;
		int idle =0;
		sjfTask[] runnin=new sjfTask[1];
		sortByArriveTime();
		while(!pq.isEmpty()||count<taskList.size()||runnin[0]!=null) {
			count += addTaskToQueue(time++);
			if(!pq.isEmpty()&&runnin[0]==null) {
				runnin[0]=pq.poll();
			}
			if(runnin[0]!=null) {
	             if(!runnin[0].processing(time)) {
	            	 runnin[0].setCompleteTime(time);
	            	 System.out.println("Completion time of task "+runnin[0].getPid()+" is:"+runnin[0].getCompleteTime()/10);
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
		}
		System.out.print("AWT: "+getAWT()+"\nART: "+getART()+"\nATT: "+getATT()+"\nCpu utilization rate:"
				+ (time-idle)/time);
	}
	
	public double getATT() {
		//calculate turn around time
		double turnAround = 0;
		for(sjfTask t:taskList) {
			turnAround+=t.getTurnAroundTime();
		}
		return (turnAround/taskList.size())/10;
	}
	
	
	
	public double getAWT() {
		//calculate wait time
		double totalTime =0;
		for(sjfTask t:taskList) {
			totalTime +=t.getWaitTime();
			
		}
		return (totalTime/taskList.size())/10;
		
	}
	
	public static int addTaskToQueue(int time) {
		//when one or more processes is ready, add them to ready queue 
		int taskAdded = 0;
		for(sjfTask t: taskList) {
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
		for(sjfTask t: pq) {
			t.updateWaitTime();
		//	System.out.println(t.getPid()+" is updated time");
		}
	}
	
	public static void sortByArriveTime() {
		//simple sorting method to sort list by arrive time
		Collections.sort(taskList, new Comparator<sjfTask>() {
			@Override
			public int compare(sjfTask o1, sjfTask o2) {
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
		        int burt1 = (int) (burt*10);
		        int arrt1 = (int)(arrt*10);
		        pri = scanner.nextInt();
		        taskList.add(new sjfTask(pid,arrt1,burt1,pri)); //add to list
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
	

}

class sjfTask implements Comparable<sjfTask>{
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
	public sjfTask(int pid, int arrt, int burt, int pri) {
		Pid =pid;
		Arrival_Time = arrt;
		Burst_Time = burt;
		Priority = pri;
	//	System.out.print("Pid: "+Pid+"\nArrT: "+Arrival_Time+"\nBurT: "+Burst_Time+"\nPri: "+Priority+"\n");		
	}
	public boolean processing(int i) {
		lifeCycle++;
		if(i%10==0) {
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
	public int compareTo(sjfTask t) {
		return Burst_Time- t.getBurst_Time();
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
	
}