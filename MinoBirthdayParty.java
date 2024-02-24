import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.*;

public class MinoBirthdayParty{
    public static void main(String[] args){

        // Delcare and initialize the number of guest at the party
        int numberOfGuest = 8;
        BirthdayGuest[] threads = new BirthdayGuest[numberOfGuest];

        // Starting the "party" for each thread aka guest 
        for (int i = 0; i < numberOfGuest; i++)
        {
            threads[i] = new BirthdayGuest("Guest #"+i, i);
            threads[i].start();
        }

        // Needed to make sure that the threads stop running
        for (BirthdayGuest guest : threads)
        {
            try {
                guest.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }
}

class BirthdayGuest extends Thread{

    // Lock will make sure that only one thread (guest) is entering the Labryinth
    static Lock lock = new ReentrantLock();

    // This will be my flag, condition when the party will end
    /* Second Line will simulate that the guest will make 
     a another line as they wait to be called for the Labryinth.
     There are many different conditions that we can use to stop the party.
     Other flag can be the cupcakes, but I chose this strategy for the prompt.
    */
    static ArrayList<Boolean> secondLine = new ArrayList<Boolean>();

    // Variables needed for class
    int index;
    static boolean cupcake = true;
    Random random = new Random();
    
    // Constructor for birthday guest
    public BirthdayGuest(String name, int index){
        this.setName(name);
        this.index = index;
        secondLine.add(false);
    }


    @Override
    public void run(){

        // Party ends until all guest move to the second line
        while(secondLine.contains(false))
        {
            // Try Lock is used to allow threads to continue and not entirely wait for the lock
            if (lock.tryLock()){
                try {
                    System.out.println("Guest #" + this.index + " entered the labryinth");

                    // Guest will move to the "second line" once they exit the Labrinth 
                    secondLine.set(this.index, true);

                    // Roleplaying according to the prompt and to show my locks are working properly
                    cupcakeDecision();

                } catch (Exception e) {
                    e.printStackTrace();
                }   finally{
                    System.out.println("\tGuest #" + this.index + " is exiting the labryinth");
                    lock.unlock();
                }
            }
            

        }

        System.out.println("Guest #" + this.index + " announces we all have entered the Labryinth!");
    
    }

    /*
     * This just adds some random cases on what the guest will decide for the cupcake they are presented
     */
    private void cupcakeDecision(){
        
        lock.lock();
        // Random random = new Random();

        if (cupcake){

            // Here we simulate the decision of the Guest eating or pasing the cupcake
            cupcake = random.nextBoolean();
            
            if(cupcake){

                System.out.println("\t\tGuest #" +this.index+ " did NOT eat the cupcake.");
            
            } 
            else {
                
                System.out.println("\t\tGuest #" +this.index+ " did eat the cupcake.");
            }
                   
        }
        else {
            // Here we simulate the decision of requesting another cupcake
            cupcake = random.nextBoolean();

            if(cupcake){
                System.out.println("\t\tGuest #" +this.index+ " is REQUESTING a cupcake, please.");
                cupcakeDecision();
            } else {
                System.out.println("\t\tGuest #" +this.index+ " did NOT eat or request the cupcake.");
            }
        }
        lock.unlock();
    }
              
}