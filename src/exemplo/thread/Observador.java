package exemplo.thread;

import java.util.Observable;
import java.util.Observer;

import exemplo.thread.Main.Nome;

public class Observador implements Observer {

	
	
	@Override
	public void update(Observable observado, Object arg) {
		
		if(null == observado || null == arg) {
			System.out.println("argumentos nulos!");
			return;
		}
		
		
		final Nome nome = Nome.class.cast(observado);
		System.out.println("Tempo de "+arg.toString()+" de "+nome.getNome());
	}

}
