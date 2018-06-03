1- Detalhes sobre a solução encontrada
	1.1- 	A classe AdjacentCellsChallenge, contém a função navegaMatriz, que é invocada de forma recursiva, quando é encontrado
			um elemento da matriz com valor = 1.
	1.2-	Além de ser carregada em memória a matriz para análise, são criadas duas matrizes do tipo boolean, de tamanho idêntico
			ao da matriz em análise, uma armazena as posições da matriz que já foram percorridas pela função navegaMatriz e a outra
			armazena as posições que já foram considerads adjacentes.
	1.3-	Projecto desenvolvido em linguagem Java, IDE Netbeans 8.2.
	1.4-	Para leitura dos ficheiros JSON, foi utiliza a libraria JSON.simple
	1.5-	A aplicação disponibiliza um menu básico, através da consola Output do Netbeans.
				
2- Detalhes sobre algumas pastas do projecto
	2.1 -	./JSONfiles: repositório dos ficheiros JSON com as matrizes para análise de adjacência.
	2.2 - 	./output: localização do ficheiro resultado.txt com todas as células adjacentes da matriz analisada.

3- Limitações
	3.1 - 	Aumentado o tamanho da matriz em análise, por exemplo a 10000 x 10000, a solução encontrada demora bastante tempo (várias 
			horas) a devolver um resultado. Para ultrapassar esta situação, foi considerada a possibilidade de utilizar multi-threading,
			mas a falta de experiência na utilização dessa tecnologia e a necessidade de reajustar o código já desenvolvido (e 100% 
			funcional) para matrizes menos complexos, foram factores chave para não seguir esse caminho.
			
4- Notas finais
	4.1- 	O desafio foi bastante interessante, pois a escolha da solução foi oscilando entre algo baseado na teoria dos grafos, também
			foi considerada a hípotese de um conjunto muito elaborado de condições IF, mas perante a complexidade que tal revelava, o 
			cenário de uma solução recursiva foi-se tornando mais adequado para o problema.
			Implementar a recursividade permitiu melhoras as minhas competências no que concerne a identificar correctamente as condições 
			de início e fim da invocação recursiva, bem como a encontrar a melhor forma de manter, correctamente actualizado, um contador
			com o número de chamadas recursivas ao longo da análise das células adjacentes.
			
