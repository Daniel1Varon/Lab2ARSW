package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrimeFinderThread extends Thread{
    
    boolean suspender; //Suspende un hilo cuando es true
    boolean pausar;    //Detiene un hilo cuando es true
    int a,b;
    
    private List<Integer> primes=new LinkedList<Integer>();
    
    public PrimeFinderThread(int a, int b) {
        super();
        this.a = a;
        this.b = b;
        suspender=false;
        pausar=false;
    }
    
    @Override
    public void run(){
        for (int i=a;i<=b;i++){
            if (isPrime(i)){
                primes.add(i);
                System.out.println(i);
            }
            synchronized (this) {
                while (suspender) {
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PrimeFinderThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (pausar) break;
            }
        }
    }
    
    boolean isPrime(int n) {
        if (n%2==0) return false;
        for(int i=3;i*i<=n;i+=2) {
            if(n%i==0)
                return false;
        }
        return true;
    }
    
    public List<Integer> getPrimes() {
        return primes;
    }
    
    //Pausar el hilo
    synchronized void pausarHilo(){
        pausar=true;
        //lo siguiente garantiza que un hilo suspendido puede detenerse.
        suspender=false;
        notify();
    }
    //Suspender un hilo
    synchronized void suspenderHilo(){
        suspender=true;
    }
    //Renaudar un hilo
    synchronized void renaudarHilo(){
        suspender=false;
        notify();
    }
}
