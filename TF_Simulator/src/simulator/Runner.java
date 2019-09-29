/*
 Steps:
 1)Created one list with 100 elements
 
 2)passed this list through random function and taken 10 random players from it, so now this getRandom list has 10 random players
 
 3)Performance coefficient is not same for every player and it will be given random, so I have created  a list with 10 Random values between o to 1

 4)Finally now i have two lists with players list and performance coefficients, so I have taken a Map and added these two as key,value pair to that new Map
 
 5)Now i have a Map with Player and Coefficient and I sorted this MAP and by using thread concept , displayed the output 
 
 6) Sample Input and Output
 
 
 1 2 3 Go...Race Started!!!!

----------------------->
Athlet no:79
----------------------->
Athlet no:100
----------------------->
Athlet no:94
----------------------->
Athlet no:8
----------------------->
Athlet no:25
----------------------->
Athlet no:83
----------------------->
Athlet no:38
----------------------->
Athlet no:73
----------------------->
Athlet no:92
----------------------->
Athlet no:15
----------------------->


Race Summary:

Number of Racers Participated: 10

{79=0.06255399568894804, 100=0.06679608859037323, 83=0.21079750280721687, 38=0.25241045032804665, 8=0.42165426244962856, 25=0.42797330953980073, 15=0.5229113038503631, 73=0.6924348588741545, 94=0.784762649691626, 92=0.8722360285095341}

LEADERBOARD
------------------------------------------------
Position 1: 79 -----> Gold Medal Winner
Position 2: 100 -----> Silver Medal Winner
Position 3: 83 -----> Bronze Medal Winner
Position 4: 38
Position 5: 8
Position 6: 25
Position 7: 15
Position 8: 73
Position 9: 94
Position 10: 92
------------------------------------------------

 
 
 */

package simulator;

import java.util.concurrent.*;
import java.security.KeyStore.Entry;
import java.util.*;

public class Runner {

     
	public List<Integer> completed = Collections.synchronizedList(new ArrayList<Integer>());
   // public List<Integer> completed = new ArrayList<Integer>();

   
	
 List<Integer> getRandomElement(List<Integer> list1, 
			int totalItems) 
{ 
Random rand = new Random(); 


List<Integer> newList = new ArrayList<>(); 
//List<Integer> list2 = new ArrayList<>(); 
for (int i = 0; i < totalItems; i++) { 


int randomIndex = rand.nextInt(list1.size()); 


newList.add(list1.get(randomIndex)); 

 
list1.remove(randomIndex); 
} 
//	Collections.copy(list1, list2);
return newList; 

} 
	////one way
 
 static List<Integer> normalise(List<Integer> list1, int totalItems) 
{
	 
	 int max=list1.get(0);
	 int min=list1.get(0);
	for(int i=0;i<list1.size();i++) {
		
		if(list1.get(i)>max) {
			max=list1.get(i);
		}
		
		if(list1.get(i)<min) {
			min=list1.get(i);
		}
	}
	 
	List<Float>normal=new ArrayList<Float>();
	// int k=0;
	 for(int i=0;i<list1.size();i++) {
		
		//double  k=((list1.get(i)-min)/(max-min));
		 
		 float k=(list1.get(i)-min)/(float)(max-min);
		// float l=max-min;
		// float res=k/l;
	//	System.out.print(k+" ");
		normal.add(k);
	 }
	 
	// System.out.println(max+" "+min+" ");
	 
	// for(int i=0;i<normal.size();i++) {
		// System.out.printf("%.2f",normal.get(i));
		// System.out.println("\n");
	// }
	 
	 return list1;
 
}
 
 ////
 
 public static <K,V> Map<K,V> sortByValues(Map<K,V> map)
	{
		Comparator<K> comparator = new CustomComparator(map);

		Map<K,V> sortedMap = new TreeMap<>(comparator);
		sortedMap.putAll(map);

		return sortedMap;
	}
 
 
 
 
 ////
	
    public static void main(String[] args) {
    	  int tasks = 10;
		List<Integer> list1 = Collections.synchronizedList(new ArrayList<>()); 
		List<Integer> getRandom=new ArrayList<Integer>();
		List<Double> performance=new ArrayList<Double>();
		
		
		for(int i=1;i<=100;i++) {
			list1.add(i);
		}
	
		
		
        Runner r = new Runner();
        getRandom=r.getRandomElement(list1, tasks);
	//	System.out.println(r.getRandomElement(list1,tasks)); 

    //   System.out.println(normalise(getRandom, tasks));
        
        
        for(int i =0; i<10; i++){
            Double randomDouble = (double) Math.random();

            performance.add(randomDouble);
        //  System.out.println("Random Number in Java: " + randomDouble);
       }


        
        Map<String,Double> map = new HashMap<String,Double>();  
       
        for (int i=0; i<getRandom.size(); i++) {
          map.put(getRandom.get(i).toString(), performance.get(i));    
        }
        
              
     //   System.out.println(map);  
     map=   sortByValues(map);
       
     
      // System.out.println(map);
       
       
       
        ExecutorService exe = Executors.newFixedThreadPool(5000);
      
        CountDownLatch latch = new CountDownLatch(tasks);
      
        System.out.println("1 2 3 Go...Race Started!!!!"+"\n");
        
        for(int i=0;i<tasks;i++) {
        	exe.submit(r.new Task(getRandom.get(i),latch));
        	System.out.println("----------------------->");
        	System.out.println("Athlet no:"+ getRandom.get(i));
        	
        }
        System.out.println("----------------------->");
        System.out.println("\n");
        
     
        
        try {
            latch.await();
            System.out.println("Race Summary:");
            System.out.println("\nNumber of Racers Participated: "
                    + r.completed.size()+"\n");
            
            System.out.println(map);
          //  System.out.println(map.keySet());
            int i=1;
           System.out.println("\nLEADERBOARD");
           System.out.println("------------------------------------------------");
            for ( Map.Entry<String, Double> entry : map.entrySet()) {
                
            	String key = entry.getKey();
         if(i==1) {
                System.out.println("Position "+i+": "+key+" "+"-----> Gold Medal Winner");
         }
         else if(i==2) {
        	  System.out.println("Position "+i+": "+key+" "+"-----> Silver Medal Winner");
         }
         else if(i==3) {
       	  System.out.println("Position "+i+": "+key+" "+"-----> Bronze Medal Winner");
        }
         else {
        	 System.out.println("Position "+i+": "+key);
         }
                i++;
            }
            System.out.println("------------------------------------------------");  
            
            
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        exe.shutdown();
    }
    
    
	
    
    
    class Task implements Runnable {

        private int id;
        private CountDownLatch latch;

        public Task(int id, CountDownLatch latch) {
            this.id = id;
            this.latch = latch;
        }

        public void run() {
            Random r = new Random();
            try {               Thread.sleep(r.nextInt(5000)); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            completed.add(id);
            latch.countDown();
        }
    }
}
