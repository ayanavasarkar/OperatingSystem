import java.util.*;

class Q
{
    int itemadded;
    boolean value=false;
    synchronized void put(int i) //method by which producer adds an item
    {
        if(value==true)
        {   try{
                wait();}
               
            catch(Exception e)
            {}
        }
        itemadded=i;
        value=true;
        notify();   ////notifies a waiting consumer thread to wakeup nd proceed
    }
    synchronized int get()   //called by consumer thread
    {
        if(!value)
        {
            try{
                wait();}
               
            catch(Exception e)
            {}
        }
        
        value=false;    //indicating item has been consumed
        notify();   //notifies a waiting producer thread to wakeup nd proceed
        return itemadded;
        
        
    }
}


class Producer implements Runnable
{
    Q q;
    Producer(Q obj)
    {
        q=obj;
        Thread tprod=new Thread(this);
        tprod.start();
        
        
    }
    public void run()
    {
        int read=q.get();
        System.out.println("produced-- "+read);
    }
    
}

class Consumer implements Runnable
{
    Q q;
    Consumer(Q obj)
    {
        q=obj;
        Thread tcon=new Thread(this);
        tcon.start();
        
        
    }
    
    public void run()
    {
        int read=q.get();
        System.out.println("consumed-- "+read);
    } 
}

//public class PC
{
    public static void main(String args[])throws Exception
    {
        
    }
}
