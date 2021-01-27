package edu.eci.arsw.primefinder;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	private static ArrayList<PrimeFinderThread> hilos= new ArrayList<PrimeFinderThread>();
	public static void main(String[] args) throws InterruptedException {
		int numeroHilos=1;
		int cotaMenor= 0;
		int cotaMayor= 30000000;
		int inicio=0;
		int cota=(int) Math.ceil(30000000/numeroHilos)-1;
		int fin=cota;

		for (int i=0; i<numeroHilos;i++) {
			PrimeFinderThread pft = new PrimeFinderThread(inicio, fin);
			hilos.add(pft);
			inicio=fin+1;
			if(fin<cotaMayor-cota && cotaMayor-cota<fin+cota) fin=cotaMayor;
			else if(fin+cota<cotaMayor) fin=fin+cota;
			else fin=cotaMayor;



		}
		for(PrimeFinderThread x:hilos){x.start();}
			Thread.currentThread().sleep(5000);


			for (PrimeFinderThread ini:hilos){
				ini.suspend();
				System.out.println("me suspendi");
				ini.start();
			}
			//pulsar enter

		System.out.println("here");
		Scanner scanner = new Scanner(System.in);
		String readString = scanner.nextLine();
		while(readString!=null) {
			System.out.println(readString);

			if (readString.isEmpty()) {
				System.out.println("Read Enter Key.");
			}

			if (scanner.hasNextLine()) {
				readString = scanner.nextLine();
			} else {
				readString = null;
			}
		}
		for (PrimeFinderThread e:hilos){
			e.resume();

		}

		}
	
}
