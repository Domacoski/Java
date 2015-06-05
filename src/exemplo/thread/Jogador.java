package exemplo.thread;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import exemplo.thread.Jogo.Jogada;
import exemplo.thread.Main.Nome;

/**
 * Classe sorteia uma quantidade de números e retorna para a classe {@link Observer}
 * @author tl.domacoski@gmail.com
 */
public class Jogador extends Observable  implements Runnable, Nome{



	private final String nome;
	private final int quantidade;

	public int acertos = 0;

	public Jogador(final Observer observador, final String nome, int quantidade) {
		addObserver(observador);
		this.nome = nome;
		this.quantidade = quantidade;
	}

	@Override
	public void run() {
		Thread.currentThread().setName(nome);


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
				try {
					Thread.sleep( (item*10L) );
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		setChanged();
		notifyObservers(jogada);
	}

	@Override
	public String getNome() {
		return this.nome;
	}
}
