import java.util.*;
import java.util.concurrent.*;
class Queue
{
    // instance variables - replace the example below with your own
    private int x;
    Boolean bufferfullflag=false;;
    int[] boundedbuffer;
    
    public Semaphore mutex;
    public Semaphore empty;
    public Semaphore full;
    // counter to keep track of how many entries
    //in the buffer

    int counter;// no of items addeed into buffer
    int Producerindex;//index into buffer where producer add an item
    int Consumerindex;// index into buffer where consumer can remove an item
  void put(int i)
    {
        
        try
        {
            
            
            boundedbuffer[Producerindex]=i;
            counter=counter+1;
            Producerindex=Producerindex+1;
            if(Producerindex==3)
            Producerindex=0;
            
           
            
           
            
        }
        catch (Exception e)
        {
            System.out.println("exception");
        }
    }
    public Queue()
    {
        // initialise instance variables
        x = 0;
        counter=0;
        Producerindex=0;
        Consumerindex=0;
        boundedbuffer= new int[3];
        mutex = new Semaphore(1);
        empty = new Semaphore(3);
        full = new Semaphore(0);
    }
   int get()
    {
        int ReadItem;
        try
        {
           
            ReadItem =boundedbuffer[Consumerindex];
            counter=counter-1;
            Consumerindex=Consumerindex+1;
            if(Consumerindex==3)//reset the consumer index to 0 
            Consumerindex=0;
           
            return ReadItem;
            
        }
        
      
          
    
    catch (Exception e)
        {
            System.out.println("exception");
        }
        return x;
    }

    
}

 class Producer implements Runnable
{
   Queue q;
    
    Producer(Queue queobject)
    {
        q=queobject;
        Thread ProduceThread= new Thread(this);
        ProduceThread.start();
    }
   public void run()
    {
        
        
     {
            try
        {
                
           for (int i=0;i<10;i++)
           {
               //Thread.sleep(1000);  //include sleep here for deadlock
               q.empty.acquire();   //initially empty=3
               q.mutex.acquire();   //initially mutex=0
               q.put(i);
               q.mutex.release();
               q.full.release();
               
               Thread.sleep(1000);  //include sleep here for not introducing deadlock
            
               System.out.println("produced =" + i);
            }
        }
            catch(Exception e)
            {
                System.out.println("error");
            }
      }
    }
    
    
}
 class Consumer implements Runnable
{
   Queue q;
   Consumer(Queue queobject)
    {
        q=queobject;
        Thread consumThread= new Thread(this);
        consumThread.start();
    }
    public void run()
    
    {
        int returnvalue;
        while(true)
        {
            try
            {
                
                Thread.sleep(1000);
                q.full.acquire(); //aids in deadlock commenting it
                q.mutex.acquire();
                //q.full.acquire();   //for deadlock 0-1=-1 thus blocked
                returnvalue=q.get();
                q.mutex.release();
                q.empty.release();
                
                System.out.println(" The consumed value "+returnvalue);
            }
    catch(Exception e)
        {
        
         System.out.println("error");
        }
    }
}
}
    public class WithoutDeadlock
    {
        
        public static void main(String[]args)
        {
            Queue qobject= new Queue();
            Consumer consumerthread= new Consumer(qobject);
            
            Producer prodThread=new Producer(qobject);
            
        }
    
    
    
}
