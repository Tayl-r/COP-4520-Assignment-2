# COP-4520-Assignment-2

## Problem 1: Minotaur’s Birthday Party
According to the prompt, the guest have to come up with a strategy to know that all guest have entered the Labryinth. There are many different strategies that can be implemented to use as an indicator to tell the Minotaur all guest have entered the Labryinth. The code simulates a strategy that once they enter and exit the Labryinth, the guest stands in the designated second line (this can be a separate table, etc). This is second line is declared as a ArrayList<Boolean> and initialized as false. Once the guest accesses the Labryinth with the Lock, their assigned index is chnaged to true. Once the ArrayList does not contain false, then the party is ended.

## Problem 2: Minotaur’s Crystal Vase
My code is simulating option 2. The guest will see that the sign is either "Available" or "Busy". If the sign says "Busy", the guest will not bother to try entering the room to see the vase. If the sign says "Available", the guest will enter room and make sure to change the sign accordingly. This part of the code is expressed in the if statement and has to be defined in this exact order. "(sign.equals("AVAILABLE") && lock.tryLock())" The code does NOT continue to check the lock if the sign is "Busy". This allows the threads that are not accessing the room to the vase to continue "partying" as I simulated in the code. No thread is waiting too long for the lock.

The prompt could also use strategy 3 by using a Conditional variable, .await(), and .signal(). This would simulate the prompt where the guest waits, .await(), in a queue for the room and then the next guest is notified, .signal(), once that active thread leaves the critical section.
