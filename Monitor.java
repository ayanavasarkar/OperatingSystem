//using synchronizer
import java.util.concurrent.*;

public class Monitor
{
    public static void main(String[]args)throws Exception
    {
        Para p=new Para();
        Multi obj=new Multi(p,"tom");
        
        Multi obj1=new Multi(p,"mary");
    }
}

class Multi implements Runnable
{
    Para p;String s;
    Thread thread1;
    public Multi(Para pal,String str)
    {
        p=pal;s=str;
        thread1=new Thread(this);
        thread1.start();
    }
    
    public void run()
    {
        p.display(s);
    }
}

class Para
{
    //private final Semaphore semaobj=new Semaphore(1);
    synchronized void display(String s)
    {
        try
        {
            //semaobj.acquire();
            System.out.print("("+s);
            System.out.println(")");
        
            //semaobj.release();  //next task cannot start without release
        }
        catch(Exception e){}
    }
    
    /*void display (String s)
    {
        System.out.print("("+s);
        System.out.println(")");
    }*/
}


