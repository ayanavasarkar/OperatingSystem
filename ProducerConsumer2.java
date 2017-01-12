class Queue
{
    private int x;
    Boolean bufferfullflag=false;
    int[] boundedbuffer;
    int counter; //keep track of no of entries in the buffer
    int in,out;
    synchronized void put(int i)
    {
        try
        {
            if(counter==3)  //buffer is full....thus block the producer
            wait();
            boundedbuffer[in]=i;
            counter=counter+1;
            //x=i;
            //bufferfullflag=true;
           notify();
        }
        catch(Exception e)
        {
            System.out.println("error");
        }
    }
    public Queue()
    {
        x=0;in=0;out=0;counter=0;
        boundedbuffer = new int [3];
    }
    synchronized int get()
    {
        try
        {   int ReadItem;
            if(counter==0)
            wait();
            ReadItem=boundedbuffer[out];
            counter=counter-1;
           /* boundedbuffer[in]=i;
            //if(!bufferfullflag)
            wait();
            bufferfullflag=false;
            notify();
            return x;*/
        }
        catch (Exception e)
        {
            System.out.println("error");
        }
        return x;
    }
}
class Producer implements Runnable
{
    Queue q;
    Producer(Queue queueobj)
    {
        q=queueobj;
        Thread produceThread = new Thread(this);
        produceThread.start();
    }
    public void run()
    {
        try
        {
            for(int i=0;i<10;i++)
            {
                Thread.sleep(1000);
                q.put(i);
                System.out.println("produced="+i);
            }
        }
        catch(Exception e)
        {
            System.out.println("error");
        }
    }
}
class Consumer implements Runnable
{
    Queue q;
    Consumer(Queue queueobj)
    {
        q=queueobj;
        Thread consumerThread= new Thread (this);
        consumerThread.start();
    }
    public void run()
    {
        int returnval;
        while(true)
        {
            try
            {
                Thread.sleep(1000);
                returnval=q.get();
                System.out.println("The consumer value "+returnval);
            }
            catch (Exception e)
            {
                System.out.println("error");
            }
        }
    }
}

public class PC2
{

    public static void main(String[]args)
    {
        Queue queueobj = new Queue();
        Consumer consumerThread = new Consumer(queueobj);
        Producer producerThread = new Producer(queueobj);
    }
}
