import java.util.ArrayList;

public class User {
    //author: ___________ in group 0110 for CSC207H1 summer 2020 project

    private int numBorrowed = 0;

    private int numLent = 0;

    protected ArrayList<Trade> requestQueue;

    //A stack of trade requests. Queue at the end and dequeue at index 0.
    private ArrayList<Trade> tradeRequestQueue;


    public int getNumBorrowed(){
        return this.numBorrowed;
    }

    public int getNumLent(){
        return this.numLent;
    }

    public Trade dequeueTradeRequest(){
        return this.tradeRequestQueue.remove(0);
    }
}
