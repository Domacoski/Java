package exemplo.thread;

public class Main {


	public static void main(String[] args) {
		Jogo jogo = new Jogo();
		jogo.iniciar(123);
	}
	
	
	
	public static interface Nome{
		public String getNome();
	}
}
