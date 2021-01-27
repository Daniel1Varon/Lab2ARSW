package edu.eci.arsw.primefinder;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    
    private static ArrayList<PrimeFinderThread> hilos= new ArrayList<PrimeFinderThread>();
    
    public static void main(String[] args) throws InterruptedException {
        int numeroHilos=3;
        int cotaMayor= 30000000;
        int inicio=0;
        int cota=(int) Math.ceil(30000000/numeroHilos);
        int fin=cota-1;
        
        for (int i=0; i<numeroHilos;i++) {
            PrimeFinderThread pft = new PrimeFinderThread(inicio, fin);
            hilos.add(pft);
            inicio=fin+1;
            if(fin<cotaMayor-cota && cotaMayor-cota<fin+cota) fin=cotaMayor;
            else if(fin+cota<cotaMayor) fin=fin+cota;
            else fin=cotaMayor;
        }
        
        hilos.forEach(x -> { x.start();});
        
        Thread.sleep(5000);
        hilos.forEach(ini -> {ini.suspenderHilo();});
        
        pressAnyKeyToContinue();
        
        hilos.forEach(ini -> {ini.renaudarHilo();});
        
    }
    
    static public void pressAnyKeyToContinue()
    {
        String seguir;
        Scanner teclado = new Scanner(System.in);
        System.out.println("Press Enter key to continue...");
        try
        {
            seguir = teclado.nextLine();
        }
        catch(Exception e)
        {}
    }
    
}
