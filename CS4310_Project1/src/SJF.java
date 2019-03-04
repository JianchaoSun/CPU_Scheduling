import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class SJF {
	static List<sjfTask> taskList = new ArrayList<sjfTask>();
	static double turnAround[];
	static int art,att,cur;
	static PriorityQueue <sjfTask>pq = new PriorityQueue<sjfTask>();
	
	public static void main(String[]args) throws FileNotFoundException {
		readFile("");
		running();
		System.out.println(getAVT());
	/*	for(sjfTask s: taskList) {
			pq.add(s);
		}
		
		for(sjfTask s: taskList) {
			System.out.println(pq.poll().getArrival_Time());
		}
		*/
	}
	
	public static void running() {
		int count=0;
		int time =0;
		sjfTask[] runnin=new sjfTask[1];
		sortByArriveTime();
		while(!pq.isEmpty()||count<taskList.size()||runnin[0]!=null) {
			count += addTaskToQueue(time++);
			if(!pq.isEmpty()&&runnin[0]==null) {
				runnin[0]=pq.poll();
			}
			if(runnin[0]!=null) {
	             if(!runnin[0].processing()) {
	            	 runnin[0]=null;
			}
			}
			else {
				System.out.println("No process running at this time");
			}
			updateWaitTime();
			
		}
	}
	
	public static double getAVT() {
		double totalTime =0;
		for(sjfTask t:taskList) {
			totalTime +=t.getWaitTime();
			System.out.println(t.getWaitTime());
			
		}
		return totalTime/taskList.size();
		
	}
	
	public static int addTaskToQueue(int time) {
		int taskAdded = 0;
		for(sjfTask t: taskList) {
			if(t.getArrival_Time() == time) {
				if(pq.add(t)) {
				System.out.println(t.getPid()+" is added to waiting queue");
				}
				taskAdded++;
			}
		}
		return taskAdded;
	}
	
	public static void updateWaitTime() {
		
		for(sjfTask t: pq) {
			t.updateWaitTime();
			System.out.println(t.getPid()+" is updated time");
		}
	}
	
	public static void sortByArriveTime() {
		//simple sorting method to sort list by arrive time
		Collections.sort(taskList, new Comparator<sjfTask>() {
			@Override
			public int compare(sjfTask o1, sjfTask o2) {
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
		    	int pid = 0,arrt=0,burt=0,pri=0;
		               	
		        pid = scanner.nextInt(); 
		        arrt = scanner.nextInt();
		        burt = scanner.nextInt(); 
		        pri = scanner.nextInt();
		        taskList.add(new sjfTask(pid,arrt,burt,pri)); //add to list
      
		    }
		  } finally
		  {
			    scanner.close();
			  }
		  
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
	int lifeCycle = 0;
	private int waitTime = 0;
	public sjfTask(int pid, int arrt, int burt, int pri) {
		Pid =pid;
		Arrival_Time = arrt;
		Burst_Time = burt;
		Priority = pri;
	//	System.out.print("Pid: "+Pid+"\nArrT: "+Arrival_Time+"\nBurT: "+Burst_Time+"\nPri: "+Priority+"\n");		
	}
	public boolean processing() {
		lifeCycle++;
		System.out.println("Process "+Pid+" is running");
		return lifeCycle != Burst_Time;
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
		waitTime ++;
	}
	
}
