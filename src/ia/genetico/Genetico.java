package ia.genetico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Genetico {
	
	public Integer[][] helpArray;
	public int qtdPopulacao;
	public ArrayList<Individuo> populacao;
	
	public Genetico(Integer[][] helpArray, int qtdPopulacao){
		this.helpArray = helpArray;
		this.qtdPopulacao = qtdPopulacao;
		gerarPopulacaoInicial();
	}
	
	public ArrayList<Individuo> getPopulacao() {
		return populacao;
	}

	private void gerarPopulacaoInicial() {
		populacao = new ArrayList<Individuo>();
		
		for(int cont = 0; cont < this.qtdPopulacao; cont++){
			Integer[][] crom = replicarIndividuo(this.helpArray);
			
			for(int b = 0; b < 9; b++)
				preencherBloco(crom, b);
			
			Individuo i = new Individuo(crom);
			populacao.add(i);
		}
	}

	private void preencherBloco(Integer[][] individuo, int b) {
		ArrayList<Integer> faltando = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
		
		int baseL = 0;
		int baseC = 0;
		
		if(b >= 3 && b < 6) baseL = 3;
		if(b >= 6) baseL = 6;
		if(b % 3 == 1) baseC = 3;
		if(b %3 == 2) baseC = 6;
		
		for(int lb = 0+baseL; lb < 3+baseL; lb++){
			for(int cb = 0+baseC; cb < 3+baseC; cb++){
				if(individuo[lb][cb] != 0)
					faltando.remove(faltando.indexOf(individuo[lb][cb]));
			}
		}
		
		for(int lb = 0+baseL; lb < 3+baseL; lb++){
			for(int cb = 0+baseC; cb < 3+baseC; cb++){
				if(individuo[lb][cb] == 0){
					int index = new Random().nextInt(faltando.size());
					individuo[lb][cb] = faltando.get(index);
					faltando.remove(index);
				}
			}
		}
	}

	private Integer[][] replicarIndividuo(Integer[][] tabuleiro) {
		Integer[][] individuo = new Integer[9][9]; 
		for(int c = 0; c < 9; c++)
			for(int l = 0; l < 9; l++)
					individuo[c][l] = tabuleiro[c][l];
		return individuo;
	}
	
	public Individuo resolver(int loops){
		int i = 0;
		
		System.out.println("Geração\tMelhor\tPior\tMédia");
		
		int startBest = populacao.get(indiceMelhor()).avaliacao;
		
		while(loops == 0 || i < loops){
			System.out.print(i);
			
			int melhor = indiceMelhor();
			int pior = indicePior();
			double media = media();
			System.out.print("\t"+populacao.get(melhor).avaliacao+"\t"+populacao.get(pior).avaliacao+"\t"+media+"\n");
			
			if(populacao.get(melhor).avaliacao == 243){
				System.out.println("Achou solução!");
				break;
			}
			
			evoluir();
			i++;
		}
		
		Individuo melhor = populacao.get(indiceMelhor());
		
		System.out.println("O melhor indivíduo da população inicial: f(i) = " + startBest);
		System.out.println("Melhor solução encontrada na última geração: f(i) = " + melhor.avaliacao);
		System.out.println(melhor);
		return melhor;
	}

	private void evoluir() {
		ArrayList<Individuo> intermediaria = selecao();
		populacao = crossover(intermediaria);
		mutacao();
	}

	private void mutacao() {
		for(Individuo i : populacao){
			boolean mutacao = (new Random().nextDouble() < 0.3);
			
			if(mutacao)
				mutacao1(i);
			
		}
	}

	private void mutacao1(Individuo i) {
		int blocoMutacao = (new Random().nextInt(9));
		
		int baseL = 0;
		int baseC = 0;
		
		if(blocoMutacao >= 3 && blocoMutacao < 6) baseL = 3;
		if(blocoMutacao >= 6) baseL = 6;
		if(blocoMutacao % 3 == 1) baseC = 3;
		if(blocoMutacao %3 == 2) baseC = 6;
		
		int gen_a_l;
		int gen_a_c;
		int gen_b_l;
		int gen_b_c;
		do{
			gen_a_l = new Random().nextInt(3);
			gen_a_c = new Random().nextInt(3);
			gen_b_l = new Random().nextInt(3);
			gen_b_c = new Random().nextInt(3);
		} while((helpArray[gen_a_l+baseL][gen_a_c+baseC] != 0) || (helpArray[gen_b_l+baseL][gen_b_c+baseC] != 0));
		
		int temp = i.cromossomo[gen_a_l+baseL][gen_a_c+baseC];
		i.cromossomo[gen_a_l+baseL][gen_a_c+baseC] = i.cromossomo[gen_b_l+baseL][gen_b_c+baseC];
		i.cromossomo[gen_b_l+baseL][gen_b_c+baseC] = temp;
				
	}

	private ArrayList<Individuo> crossover(ArrayList<Individuo> intermediaria) {
		ArrayList<Individuo> result = new ArrayList<Individuo>();
		
		// Elitismo sem crossover
		int t1 = intermediaria.size();
		ArrayList<Individuo> aux = new ArrayList<Individuo>(intermediaria);
		Collections.sort(aux);
		result.add(aux.get(0));
		result.add(aux.get(1));
		intermediaria.remove(aux.get(0));
		intermediaria.remove(aux.get(1));
		if(t1 == intermediaria.size()){
			System.out.println("ruim");
		}
		// --------
		
		while(intermediaria.size() > 0){
			if(intermediaria.size() == 1){
				result.addAll(intermediaria);
				break;
			}
			
			int indice1 = new Random().nextInt(intermediaria.size());
			int indice2 = new Random().nextInt(intermediaria.size());
			
			while(indice2 == indice1) indice2 = new Random().nextInt(intermediaria.size());
			
			boolean crossover = (new Random().nextDouble() <= 0.8);
			if(crossover){
				int pontoTroca = new Random().nextInt(8)+1;
				ArrayList<Individuo> novosFilhos = gerarFilhosCrossover(populacao.get(indice1), populacao.get(indice2), pontoTroca);
				result.addAll(novosFilhos);
			}
			else{
				result.add(intermediaria.get(indice1));
				result.add(intermediaria.get(indice2));
			}
			
			intermediaria.remove(indice1);
			if(indice2 > indice1) indice2--;
			intermediaria.remove(indice2);
			
		}
		
		return result;
	}

	private ArrayList<Individuo> gerarFilhosCrossover(Individuo individuo1, Individuo individuo2, int pontoTroca) {
		Integer[][] arrfilho1 = new Integer[9][9], arrfilho2 = new Integer[9][9];
		
		for(int bloco = 0; bloco < 9; bloco++){
			
			int baseL = 0;
			int baseC = 0;
			
			if(bloco >= 3 && bloco < 6) baseL = 3;
			if(bloco >= 6) baseL = 6;
			if(bloco % 3 == 1) baseC = 3;
			if(bloco %3 == 2) baseC = 6;
			
			for(int lb = 0+baseL; lb < 3+baseL; lb++){
				for(int cb = 0+baseC; cb < 3+baseC; cb++){
					if(bloco < pontoTroca){
						arrfilho1[lb][cb] = individuo1.cromossomo[lb][cb];
						arrfilho2[lb][cb] = individuo2.cromossomo[lb][cb];
					}
					else{
						arrfilho1[lb][cb] = individuo2.cromossomo[lb][cb];
						arrfilho2[lb][cb] = individuo1.cromossomo[lb][cb];
					}
				}
			}
		}
		
		ArrayList<Individuo> result = new ArrayList<Individuo>();
		result.add(new Individuo(arrfilho1));
		result.add(new Individuo(arrfilho2));
		return result;
	}

	private ArrayList<Individuo> selecao() {
		ArrayList<Individuo> intermediaria = new ArrayList<Individuo>();
		ArrayList<Integer> roleta = new ArrayList<Integer>();
		
		int somatotal = 0;
		
		for(Individuo i : populacao)
			somatotal += i.avaliacao;
		
		
		for(int i = 0; i < populacao.size(); i++){
			int qtdInserirRoleta = ((100 * populacao.get(i).avaliacao)/somatotal) + 1;
			for(int j = 0; j < qtdInserirRoleta; j++){
				roleta.add(i);
			}
		}
		
		// Elitismo
		ArrayList<Individuo> aux = new ArrayList<Individuo>(populacao);
		Collections.sort(aux);
		intermediaria.add(aux.get(0));
		intermediaria.add(aux.get(1));
		
		for(int i = 0; i < qtdPopulacao/100; i ++)
			intermediaria.add(populacao.get(indiceMelhor()));
		// -----
		
		while(intermediaria.size() < qtdPopulacao){
			int escolhido = new Random().nextInt(roleta.size());
			intermediaria.add(populacao.get(roleta.get(escolhido)));
		}
		
		return intermediaria;
	}

	private double media() {
		int sum = 0;
		
		for(Individuo i : populacao)
			sum += i.avaliacao;
		
		return (double)sum / (double) populacao.size();
	}

	private int indicePior() {
		int i_pior = 0;
		
		for(int i = 0; i < populacao.size(); i++){
			if(populacao.get(i).avaliacao < populacao.get(i_pior).avaliacao)
				i_pior = i;
		}
		
		return i_pior;
	}

	private int indiceMelhor() {
		int i_pior = 0;
		
		for(int i = 0; i < populacao.size(); i++){
			if(populacao.get(i).avaliacao > populacao.get(i_pior).avaliacao)
				i_pior = i;
		}
		
		return i_pior;
	}
}
