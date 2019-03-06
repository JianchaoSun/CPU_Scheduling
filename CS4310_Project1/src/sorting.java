


public class sorting implements Runnable 
{ 
	static int[]x = {3,9,11,2,1
			,7,5,6,4,10};
	static int[]x1=new int[10]; //initialize 2 array,one for store final array
	
    public static void main(String[] args) {
	/*	Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				
				for(int i=0;i<4;i++) {
					x[i]=0;
					System.out.print(i+":"+x[i]+"\n");
				
				}
				
			}
			
		});
		*/
    	sorting s = new sorting();
    	Thread t = new Thread(s,"firstPart");
		t.start();//thread to sort first part array

		Thread t2 = new Thread(s,"secondPart");	
		t2.start();//thread to sort second part array
		
		Thread t3 = new Thread(s,"combine");
		t3.start();//thread to combine first and second part of array

		Thread t4 = new Thread(s);
		t4.start(); //thread to print out the final array
		
		
		
	}
    
	
    public void run() {
    
    int mid = x.length/2;
        try
        { 
        		String name = Thread.currentThread().getName();
        		//use thread name to determine which function we want thread to run
        		if(name.equals("firstPart")) {
        			//basic bubble sort to sort first part
        			for(int i=0; i<mid;i++) {
        				for(int j=0;j<mid;j++) {
        					if(x[i]<x[j]) {
        						int temp =x[i];
        						x[i] = x[j];
        						x[j] = temp;
        					}
        				}
        			}
        			
        		}else if(name.equals("secondPart")) {
        			//basic bubble sort to sort second part
        			for(int i=mid; i<x.length;i++) {
        				for(int j=mid;j<x.length;j++) {
        					if(x[i]<x[j]) {
        						int temp =x[i];
        						x[i] = x[j];
        						x[j] = temp;
        					}
        				}
        			}
        			
        			
        		}else if(name.equals("combine")) {
        			//basic merge sort function to combine two array into a new array
        			int i = 0,j=mid,k=0;
        			while(i<mid&&j<x.length) {
        				if(x[i]<=x[j]) {
        					x1[k] = x[i];
        					i++;
        					k++;
        				}else if(x[j]<x[i]) {
        					x1[k] = x[j];
        					j++;
        					k++;
        				}
        				
        			}
        			//combine if have anything left
        				while(i<mid) {
        				x1[k] = x[i];
        				i++;
        				k++;
        				}
        				while(j<x.length) {
            				x1[k] = x[j];
            				j++;
            				k++;
            				}
        			
        		}else {
        			for(int i=0;i<x1.length;i++) {
        				System.out.print(i+":"+x1[i]+"\n");
        			
        			}
        		}
        	
  
        } 
        catch (Exception e) 
        { 
            // Throwing an exception 
            System.out.println ("Exception is caught"); 
        } 
    }

     
} 