package ia.sudoku;

import ia.genetico.Genetico;

import java.util.Date;

// Alfabeto: [0-9]
// Gen = dígito
// Tamanho cromossomo = matriz 9x9, total de 81 dígitos
// indivíduo = matriz 9x9 onde cada linha e coluna representa as linhas e colunas do tabuleiro, respectivamente


public class Sudoku {
	public static void main(String args[]) throws Exception{
//		System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("/tmp/saida"+new Date().getTime()+".csv")), true));
		Integer[][] tabuleiro = {   {0,0,0,7,0,0,0,0,0},
									{1,0,0,0,0,0,0,0,0},
									{0,0,0,4,3,0,2,0,0},
									{0,0,0,0,0,0,0,0,6},
									{0,0,0,5,0,9,0,0,0},
									{0,0,0,0,0,0,4,1,8},
									{0,0,0,0,8,1,0,0,0},
									{0,0,2,0,0,0,0,5,0},
									{0,4,0,0,0,0,3,0,0}
							};
		
//		Integer[][] tabuleiro = {   {5,3,0,7,0,0,0,0,0},
//									{6,0,0,1,9,5,0,0,0},
//									{0,9,8,0,0,0,0,6,0},
//									{8,0,0,0,6,0,0,0,3},
//									{4,0,0,8,0,3,0,0,1},
//									{7,0,0,0,2,0,0,0,6},
//									{0,6,0,0,0,0,2,8,0},
//									{0,0,0,4,1,9,0,0,5},
//									{0,0,0,0,8,0,0,7,9}
//		};
		
//		Integer[][] tabuleiro = { {3,5,2,4,7,6,8,1,9},
//				{1,6,8,3,5,9,4,7,2},
//				{4,9,7,8,2,1,6,3,5},
//				{2,4,5,9,6,7,3,8,1},
//				{9,8,6,1,4,3,5,2,7},
//				{7,3,1,2,8,5,9,6,4},
//				{6,2,3,7,9,4,1,5,8},
//				{5,7,9,6,1,8,2,4,3},
//				{8,1,4,5,3,2,7,9,6} };
		
		
//		System.out.println(new Individuo(tabuleiro).avaliacao);
		
		Date inicio = new Date();
		Genetico AG = new Genetico(tabuleiro, 1000);
		
		AG.resolver(0);
		
		Date fim = new Date();
		System.out.println("Fim do processamento("+(fim.getTime()-inicio.getTime())/1000+" segundos)");
	}
	
	public static void imprimeTabuleiro(Integer[][] tab){
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				System.out.print(tab[i][j] + " ");
				if((j+1)%3 == 0 && j < 8) System.out.print("| ");
			}
			if((i+1)%3 == 0 && i < 8) System.out.println("\n---------------------");
			else System.out.println();
		}
	}
}
