import java.io.FileNotFoundException;

public class Test {
	public static void main(String[] args) throws FileNotFoundException {
		/*FCFS t1 = new FCFS();
		t1.run();
		SJF t2 = new SJF();
		t2.run();
		*/
		Preemptive_Priority_Scheduling t3 = new Preemptive_Priority_Scheduling();
		t3.run();
	}

}
