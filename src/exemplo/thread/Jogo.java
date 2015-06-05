package exemplo.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;


public class Jogo implements Observer{
	
	private final HashMap<Jogador, Jogo.Jogada> mesa = new HashMap<Jogador, Jogo.Jogada>(0);
	private final List<Thread> threads = new ArrayList<Thread>(0);
	private final List<Jogada> jogadas = new ArrayList<Jogada>(0);
	
	private static final int QUANTIDADE = 7;
	
	private Jogada sorteados;
	
	int prontos = 0;
	
	public Jogo() { }
	
	public synchronized void iniciar(int quantidade){
		this.mesa.clear();
		Thread.currentThread().setName("MESA DE JOGO");
		int contador = 0;
		while( contador != quantidade){
			final Jogador jogador = new Jogador(this, ("Jogador"+contador), QUANTIDADE);
			final Thread thread = new Thread(jogador);
			threads.add(thread);
			System.out.println("Jogador adicionado ["+jogador.getNome()+"]");
			contador++;
		}
		
		final Sorteio sorteio = new Sorteio(QUANTIDADE, this);
		final Thread thread = new Thread(sorteio);
		thread.start();
	}
	
	public static class Jogada {
		public List<Integer> numeros;
	}

	@Override
	public void update(Observable observable, Object arg) {
		if(null == observable || null == arg) return;
		
		
		final Jogada jogada = Jogada.class.cast(arg);
		
		if(observable instanceof Sorteio){
			this.sorteados = jogada;
			
			System.out.print("SORTEIO [");
			for(final int a : this.sorteados.numeros){
				System.out.print(a+",");
			}
			System.out.print("]\n");
			for(final Thread t : threads){
				t.start();
			}
		}else if(observable instanceof Jogador){
			jogadas.add(jogada);
			
			Jogador jogador = Jogador.class.cast(observable);
			
			System.out.print(jogador.getNome()+" [");
			for(final int a : jogada.numeros){
				System.out.print(a+",");
			}
			System.out.print("]");
			
			
			for(final int i : jogada.numeros){
				for(int a : sorteados.numeros){
					if(a == i){
						jogador.acertos++;
						break;
					}
				}
			}
			prontos++;
			System.out.print("acerto: "+jogador.acertos+"\n");
			
		}
		
	}
	
	
	public static class Sorteio extends Observable implements Runnable{
		int quantidade;
		public Sorteio(int quantidade, final Observer o) {
			addObserver(o);
			this.quantidade = quantidade;
		}
		@Override
		public void run() {
			final Jogada jogada  = new Jogada();
			jogada.numeros = new ArrayList<Integer>(0);
			int cont =0;
			while(cont != quantidade){
				final Random random = new Random();
				int item = random.nextInt(80);
				while(item < 0 && item > 80){
					item = random.nextInt(80);
				}
				if(!jogada.numeros.contains(item)){
					jogada.numeros.add(item);
					cont++;
				}
			}

			setChanged();
			notifyObservers(jogada);
		}
		
	}
}
