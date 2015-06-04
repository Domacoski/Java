package br.com;

public class Threads {
	
	
	public static void main(String[] args) {
		
		int index = 0; 
		while(index != 10){
			index++;
			final Thread thread = new Thread(new Threads.Tarefa(index));
			thread.start();
			
		}
		
	}
	
	
	
	public static class Tarefa implements Runnable{

		final Integer tarefa;
		public Tarefa(Integer tarefa) {
			this.tarefa = tarefa;
		}
		
		@Override
		public void run() {
			int index = 0;
			while(index != 5){
				
				index++;
				try {
					Thread.sleep(1000 *index);
					System.out.println("tarefa: "+this.tarefa+" #"+index);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}

}
