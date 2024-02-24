import java.util.ArrayList;
import java.util.Random;
// import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.*;

public class MinoCrystalVase {
    public static void main(String[] args) {

        // Declare and initialize the number of guest
        int numberOfGuest = 8;
        vaseGuest[] threads = new vaseGuest[numberOfGuest];

        // Starting the threads
        for (int i = 0; i < numberOfGuest; i++){
            threads[i] = new vaseGuest("Guest "+i, i);
            threads[i].start();
        }


        // Making sure all threads end
        for (vaseGuest guest : threads) {
            try {
                guest.join();
            } catch (Exception e) {
               e.printStackTrace();
            }
        }
    }
}


class vaseGuest extends Thread{


    // Flag to keep track if all guest have entered and saw the vase
    static ArrayList<Boolean> vaseVisitorTracker = new ArrayList<Boolean>();

    // Lock needed to make sure only one thread can access the Vase room
    static Lock lock = new ReentrantLock();

    // Following the prompt that there is a sign
    static String sign = "AVAILABLE";
    // static AtomicReference<String> sign = new AtomicReference<>("AVAILABLE");

    int index;
    String green = "AVAILABLE";
    String red = "BUSY";
    Random rnd = new Random();

    // Constructor for class
    public vaseGuest(String name, int index){
        this.setName(name);
        vaseVisitorTracker.add(false);
        this.index = index;
    }

    @Override
    public void run(){
        
        // Condition to have the guest keep going until they all have seen the vase
        while(vaseVisitorTracker.contains(false)){

            /*
             * If the sign is BUSY, the guest can continue at the party as prompted.
             * If both are true, then that guest can enter to view the vase. They will make sure to
             * change the sign so the other guest don't enter.
             */
            if (sign.equals("AVAILABLE") && lock.tryLock()){
                try {
                        // this is need to make sure there are no errors when checking the sign from other threads (guest)
                        synchronized (this){
                        sign = "BUSY";
                        

                        
                        sign = "AVAILABLE";
                        
                        vaseVisitorTracker.set(this.index,true);
                        }

                } catch (Exception e) {
                     e.printStackTrace();
                } finally{
                    
                    lock.unlock();
                }
            } else {
                try {
                   
                    // this simulates that the guest saw the sign was "BUSY" and continues what they are doing
                    System.out.println(this.getName()+ " is having fun at the party!");
                    Thread.sleep(rnd.nextInt(3));
                } catch (Exception e) {
                  
                    e.printStackTrace();
                }
            }

            

        }

        System.out.println("Everyone has seen the vase!!!");

    }

} 

