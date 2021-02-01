package arsw.threads;

import java.util.concurrent.Semaphore;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author rlopez
 * 
 */
public class Galgo extends Thread {
	private int paso;
	private Carril carril;
	RegistroLlegada regl;
	private static Semaphore mutex = new Semaphore(1);
	
	private boolean pausa=false;
	public Galgo(Carril carril, String name, RegistroLlegada reg) {
		super(name);
		this.carril = carril;
		paso = 0;
		this.regl=reg;
	}
	
	public synchronized void pausa() throws InterruptedException {
		this.pausa=true;
		
	}
	
	public synchronized void reanudar() { 
		this.pausa=false;
		synchronized(this) {
			notifyAll();

        }
	}
	
	public void corra() throws InterruptedException {
		while (paso < carril.size()) {
			synchronized(this) {
				if(pausa) {
					wait();
				}
			}
			Thread.sleep(100);
			carril.setPasoOn(paso++);
			carril.displayPasos(paso);
			if (paso == carril.size()) {						
				carril.finish();
				mutex.acquire();
					int ubicacion=regl.getUltimaPosicionAlcanzada();
					regl.setUltimaPosicionAlcanzada(ubicacion+1);
				mutex.release();
				System.out.println("El galgo "+this.getName()+" llego en la posicion "+ubicacion);
				if (ubicacion==1){
					regl.setGanador(this.getName());
				}
				
			}
		}
	}


	@Override
	public void run() {
		
		try {
			corra();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
