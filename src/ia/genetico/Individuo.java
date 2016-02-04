package ia.genetico;

import java.util.HashSet;

public class Individuo implements Comparable<Individuo> {
	public Integer[][] cromossomo;
	public int avaliacao;
	
	public Individuo(Integer[][] cromossomo){
		this.cromossomo = cromossomo;
		this.avaliacao = funcaoAvaliacao();
	}
	
	/**
	 * 
	 * @return conta elementos distintos em cada linha, coluna e bloco
	 * @Maximo max = 243
	 * @Minimo min = 0 se tabuleiro vazio ou min = 27 se apenas um número no tabuleiro
	 */
	private int funcaoAvaliacao() {
		int f = 0;
		
		for(int i = 0; i < 9; i++){
			f += distintosLinha(i);
			f += distintosColuna(i);
			f += distintosBloco(i);
		}
		
		return f;
	}

	private int distintosLinha(int linha) {
		HashSet<Integer> presente = new HashSet<Integer>();
		for(int i = 0; i < 9; i++){
			presente.add(cromossomo[linha][i]);
		}
		return presente.size();
	}
	private int distintosColuna(int coluna) {
		HashSet<Integer> presente = new HashSet<Integer>();
		for(int i = 0; i < 9; i++){
			presente.add(cromossomo[i][coluna]);
		}
		return presente.size();
	}
	private int distintosBloco(int bloco) {
		HashSet<Integer> presente = new HashSet<Integer>();
		
		int baseL = 0;
		int baseC = 0;
		
		if(bloco >= 3 && bloco < 6) baseL = 3;
		if(bloco >= 6) baseL = 6;
		if(bloco % 3 == 1) baseC = 3;
		if(bloco %3 == 2) baseC = 6;
		
		for(int i = 0; i < 3; i ++)
			for(int j = 0; j < 3; j++)
				presente.add(cromossomo[i+baseL][j+baseC]);
		
		return presente.size();
	}
	
	public String toString(){
		String str = "";
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				str += (cromossomo[i][j] + " ");
				if((j+1)%3 == 0 && j < 8) str += ("| ");
			}
			if((i+1)%3 == 0 && i < 8) str+=("\n---------------------\n");
			else str +="\n";
		}
		return str;
	}

	@Override
	public int compareTo(Individuo o) {
		return -(this.avaliacao - o.avaliacao);
	}
}
