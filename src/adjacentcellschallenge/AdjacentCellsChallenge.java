/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adjacentcellschallenge;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;


/**
 *
 * @author Manuel Gonçalves
 */
public class AdjacentCellsChallenge{
    
    private int colunaInicial = 0;
    private int linhaInicial = 0;
    private int colunaActual = 0;
    private int linhaActual = 0;
    //Matriz para análise
    private int [][] matriz;
    //Matriz para assinalar posições já verificadas na navegação das adjacências
    private boolean [][] matrizVerificada;
    //Matriz para assinalar posições que já foram inseridas no array parcial/final, evitando repetir posições
    private boolean [][] matrizPosicoesInseridas;
    private String arrayFinal = "";
    private String arrayParcial = "";
    private int numeroColunas = 0;
    private int numeroLinhas = 0;
    private int contadorRecursividade = 0;

    //Construtor
    public AdjacentCellsChallenge() {
    }

    //////////////////////////////////////////////////////
    //                    Getters                       //
    //////////////////////////////////////////////////////
    
    public int getColunaInicial() {
        return colunaInicial;
    }

    public int getLinhaInicial() {
        return linhaInicial;
    }    

    public int getColunaActual() {
        return colunaActual;
    }

    public int getLinhaActual() {
        return linhaActual;
    }    

    public int getNumeroColunas() {
        return numeroColunas;
    }

    public int getNumeroLinhas() {
        return numeroLinhas;
    }    

    public int getNumeroLinhasMatriz()
    {
        return this.matriz.length;
    }
    
    public int getNumeroColunasMatriz()
    {
        return this.matriz[0].length;
    }

    public int getValorPosicao(int y, int x)    
    {
        return this.matriz[y][x];    
    }    
    
    public String getArrayFinal() {
        return arrayFinal;
    }

    public String getArrayParcial() {
        return arrayParcial;
    }    
    
    public boolean getMatrizVerificada(int linha,int coluna) {
        return this.matrizVerificada[linha][coluna];
    }

    public boolean getMatrizPosicoesInseridas(int linha,int coluna) {
        return this.matrizPosicoesInseridas[linha][coluna];
    }    

    public boolean isMatrizNull()
    {
        if(this.matriz == null)
            return true;
        else
            return false;
    }
        
    //////////////////////////////////////////////////////
    //                    Setters                       //
    //////////////////////////////////////////////////////
    
    public void setColunaInicial(int c) {
        this.colunaInicial = c;
    }

    public void setLinhaInicial(int l) {
        this.linhaInicial = l;
    }

    public void setColunaActual(int colunaActual) {
        this.colunaActual = colunaActual;
    }

    public void setLinhaActual(int linhaActual) {
        this.linhaActual = linhaActual;
    }

    public void setNumeroColunas(int numeroColunas) {
        this.numeroColunas = numeroColunas;
    }

    public void setNumeroLinhas(int numeroLinhas) {
        this.numeroLinhas = numeroLinhas;
    }
    
    public void setValorPosicao(int y, int x, int valor)    
    {
        this.matriz[y-1][x-1] = valor;    
    }    

    public void setArrayFinal(String arrayFinal) {
        this.arrayFinal = arrayFinal;
    }

    public void setArrayParcial(String arrayParcial) {
        this.arrayParcial = arrayParcial;
    }    
    
    public void setMatrizVerificada(int linha,int coluna) {
        this.matrizVerificada[linha][coluna] = true;
    }
    
    public void setMatrizPosicoesInseridas(int linha,int coluna) {
        this.matrizPosicoesInseridas[linha][coluna] = true;
    }    
    
    public void setContadorRecursividade(int i) {
        this.contadorRecursividade = i;
    }


    //////////////////////////////////////////////////////
    //              Funções criação matriz              //
    //////////////////////////////////////////////////////
    
    //Matriz a partir de ficheiro JSON
    public int[][] criaMatrizJSON(int opcao)
    {
        JSONParser parser = new JSONParser();

        try {
            Object obj = new Object();
            switch(opcao)
            {
                case 1: 
                    obj = parser.parse(new FileReader("./JSONfiles/100x100.json"));
                    break;
                case 2: 
                    obj = parser.parse(new FileReader("./JSONfiles/1000x1000.json"));
                    break;
                case 3: 
                    obj = parser.parse(new FileReader("./JSONfiles/10000x10000.json"));
                    break;                                        
                case 4: 
                    obj = parser.parse(new FileReader("./JSONfiles/20000x20000.json"));
                    break;
            }
            JSONArray linhas = (JSONArray) obj;
            JSONArray colunas = (JSONArray) linhas.get(0); 
            int numeroLinhasJSON = linhas.size();
            int numeroColunasJSON = colunas.size();

            this.matriz = new int[numeroLinhasJSON][numeroColunasJSON];
            this.matrizVerificada = new boolean[numeroLinhasJSON][numeroColunasJSON];
            this.matrizPosicoesInseridas = new boolean[numeroLinhasJSON][numeroColunasJSON];
            this.numeroColunas = numeroColunasJSON;
            this.numeroLinhas = numeroLinhasJSON;
            
            for(int i = 0;i < numeroLinhasJSON;i++)
            {
                colunas = (JSONArray)linhas.get(i);
                for(int j = 0;j < numeroColunasJSON;j++)
                {
                    this.matriz[i][j] = (int)(long) colunas.get(j);
                    this.matrizVerificada[i][j] = false;
                    this.matrizPosicoesInseridas[i][j] = false;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado!\nDeverá estar colocado na pasta JSONfiles (raíz do projecto).");
        } catch (IOException e) {
            System.out.println("Erro leitura do ficheiro!");
        } catch (ParseException e) {
            System.out.println("Erro ao efectuar o parse to ficheiro, verifique se existe algum erro na sua estrutura de dados.");
        }
        return matriz;
    }
    
    //Matriz para testes funcionais
    public int[][] criaMatrizTeste(int y, int x)
    {
        this.matriz = new int[y][x];
        this.matrizVerificada = new boolean[y][x];
        this.matrizPosicoesInseridas = new boolean[y][x];
        this.numeroColunas = x;
        this.numeroLinhas = y;
        
        matriz[0][0] = 0;matriz[0][1] = 0;matriz[0][2] = 0;matriz[0][3] = 1;matriz[0][4] = 0;matriz[0][5] = 0;matriz[0][6] = 1;matriz[0][7] = 1;
        matriz[1][0] = 0;matriz[1][1] = 0;matriz[1][2] = 1;matriz[1][3] = 1;matriz[1][4] = 1;matriz[1][5] = 0;matriz[1][6] = 1;matriz[1][7] = 1;
        matriz[2][0] = 0;matriz[2][1] = 0;matriz[2][2] = 0;matriz[2][3] = 0;matriz[2][4] = 0;matriz[2][5] = 0;matriz[2][6] = 1;matriz[2][7] = 0;
        matriz[3][0] = 0;matriz[3][1] = 0;matriz[3][2] = 0;matriz[3][3] = 1;matriz[3][4] = 0;matriz[3][5] = 0;matriz[3][6] = 1;matriz[3][7] = 1;
        matriz[4][0] = 0;matriz[4][1] = 0;matriz[4][2] = 0;matriz[4][3] = 1;matriz[4][4] = 0;matriz[4][5] = 0;matriz[4][6] = 1;matriz[4][7] = 1;
        
//        matriz[0][0] = 1;matriz[0][1] = 0;matriz[0][2] = 1;matriz[0][3] = 0;matriz[0][4] = 1;matriz[0][5] = 0;matriz[0][6] = 1;matriz[0][7] = 0;
//        matriz[1][0] = 0;matriz[1][1] = 1;matriz[1][2] = 0;matriz[1][3] = 1;matriz[1][4] = 0;matriz[1][5] = 1;matriz[1][6] = 0;matriz[1][7] = 1;
//        matriz[2][0] = 1;matriz[2][1] = 0;matriz[2][2] = 1;matriz[2][3] = 0;matriz[2][4] = 1;matriz[2][5] = 0;matriz[2][6] = 1;matriz[2][7] = 0;
//        matriz[3][0] = 0;matriz[3][1] = 1;matriz[3][2] = 0;matriz[3][3] = 1;matriz[3][4] = 0;matriz[3][5] = 1;matriz[3][6] = 0;matriz[3][7] = 1;
//        matriz[4][0] = 1;matriz[4][1] = 0;matriz[4][2] = 1;matriz[4][3] = 0;matriz[4][4] = 1;matriz[4][5] = 0;matriz[4][6] = 1;matriz[4][7] = 0;

//        matriz[0][0] = 1;matriz[0][1] = 1;matriz[0][2] = 1;matriz[0][3] = 1;matriz[0][4] = 1;matriz[0][5] = 1;matriz[0][6] = 1;matriz[0][7] = 1;
//        matriz[1][0] = 1;matriz[1][1] = 1;matriz[1][2] = 1;matriz[1][3] = 1;matriz[1][4] = 1;matriz[1][5] = 1;matriz[1][6] = 1;matriz[1][7] = 1;
//        matriz[2][0] = 1;matriz[2][1] = 1;matriz[2][2] = 1;matriz[2][3] = 1;matriz[2][4] = 1;matriz[2][5] = 1;matriz[2][6] = 1;matriz[2][7] = 1;
//        matriz[3][0] = 1;matriz[3][1] = 1;matriz[3][2] = 1;matriz[3][3] = 1;matriz[3][4] = 1;matriz[3][5] = 1;matriz[3][6] = 1;matriz[3][7] = 1;
//        matriz[4][0] = 1;matriz[4][1] = 1;matriz[4][2] = 1;matriz[4][3] = 1;matriz[4][4] = 1;matriz[4][5] = 1;matriz[4][6] = 1;matriz[4][7] = 1;

//        matriz[0][0] = 1;matriz[0][1] = 1;matriz[0][2] = 0;matriz[0][3] = 1;matriz[0][4] = 1;matriz[0][5] = 1;matriz[0][6] = 0;matriz[0][7] = 1;
//        matriz[1][0] = 0;matriz[1][1] = 1;matriz[1][2] = 0;matriz[1][3] = 1;matriz[1][4] = 0;matriz[1][5] = 1;matriz[1][6] = 0;matriz[1][7] = 0;
//        matriz[2][0] = 1;matriz[2][1] = 1;matriz[2][2] = 0;matriz[2][3] = 1;matriz[2][4] = 0;matriz[2][5] = 1;matriz[2][6] = 0;matriz[2][7] = 0;
//        matriz[3][0] = 1;matriz[3][1] = 0;matriz[3][2] = 0;matriz[3][3] = 1;matriz[3][4] = 0;matriz[3][5] = 1;matriz[3][6] = 0;matriz[3][7] = 1;
//        matriz[4][0] = 1;matriz[4][1] = 1;matriz[4][2] = 1;matriz[4][3] = 1;matriz[4][4] = 0;matriz[4][5] = 1;matriz[4][6] = 1;matriz[4][7] = 1;
        
//        matriz[0][0] = 1;matriz[0][1] = 1;matriz[0][2] = 1;matriz[0][3] = 0;matriz[0][4] = 1;matriz[0][5] = 0;matriz[0][6] = 1;matriz[0][7] = 0;
//        matriz[1][0] = 0;matriz[1][1] = 0;matriz[1][2] = 1;matriz[1][3] = 1;matriz[1][4] = 1;matriz[1][5] = 0;matriz[1][6] = 1;matriz[1][7] = 0;
//        matriz[2][0] = 1;matriz[2][1] = 1;matriz[2][2] = 0;matriz[2][3] = 1;matriz[2][4] = 0;matriz[2][5] = 1;matriz[2][6] = 1;matriz[2][7] = 1;
//        matriz[3][0] = 1;matriz[3][1] = 1;matriz[3][2] = 0;matriz[3][3] = 1;matriz[3][4] = 1;matriz[3][5] = 1;matriz[3][6] = 0;matriz[3][7] = 0;
//        matriz[4][0] = 0;matriz[4][1] = 0;matriz[4][2] = 1;matriz[4][3] = 0;matriz[4][4] = 1;matriz[4][5] = 0;matriz[4][6] = 0;matriz[4][7] = 1;

        String output = "";
        for(int i = 0;i < 5;i++)
        {
            for(int j = 0;j < 8;j++)
            {
                this.matrizPosicoesInseridas[i][j] = false;
                this.matrizVerificada[i][j] = false;
                output = output+matriz[i][j]+",";
            }
            System.out.println(output);
            output = "";
        }
        return matriz;
    }    
    
    private void insereArrayParcial(int linha, int coluna)
    {
        if(!this.arrayParcial.equals(""))
        {
            this.arrayParcial = this.arrayParcial + ",["+linha+","+coluna+"]";
        }
        else
        {
            this.arrayParcial = "[ ["+linha+","+coluna+"]";
        }
    }
    
    private void insereArrayFinal()
    {
        if(!this.getArrayParcial().equals(""))
            this.arrayFinal = this.arrayFinal + this.arrayParcial + " ]\n";
    }

    //////////////////////////////////////////////////////
    //         Funções suporte navegação matriz         //
    //////////////////////////////////////////////////////
    
    //Nos 4 sentidos possiveis de verificação, quando os limites da linha/coluna são excedidos,
    //a função devolve -1
    
    public int verificaDireita(int linha, int coluna) 
    {
        if(coluna + 1 == this.numeroColunas)
        {
            //Indica final de coluna
            return -1;
        }
        else
        {
            return this.matriz[linha][coluna + 1];
        }
    }
    
    public int verificaEsquerda(int linha, int coluna) 
    {
        if(coluna - 1 < 0)
        {
            //Indica final de coluna
            return -1;
        }        
        else
        {
            return this.matriz[linha][coluna - 1];
        }
    }    

    public int verificaAbaixo(int linha, int coluna) 
    {
        if(linha + 1 == this.numeroLinhas)
        {
            return -1;
        }
        else
        {        
            return this.matriz[linha + 1][coluna];
        }
    }   
    
    public int verificaAcima(int linha, int coluna) 
    {
        if(linha - 1 < 0)
        {
            //Indica final de linha
            return -1;
        }
        else
        {         
            return this.matriz[linha - 1][coluna];
        }
    }
    
    //////////////////////////////////////////////////////
    //            Função navegação recursiva            //
    //////////////////////////////////////////////////////
    
    public void navegaMatriz(int linha, int coluna)
    {
        //Flags para sinalizar os 4 sentidos possiveis de navegação
        boolean flagDireita = false;
        boolean flagEsquerda = false;
        boolean flagAcima = false;
        boolean flagAbaixo = false;       
        //Variavel que permite manter registo da profundidade da recursividade
        this.contadorRecursividade++;
        
        //Condiçoes:
        //1- O valor na posição (linha,coluna) da matriz em análise tem de ser 1
        //E
        //2- O valor na posição (linha,coluna) da matriz de verificação tem de ser false
        if(this.getValorPosicao(linha, coluna) == 1 && !this.getMatrizVerificada(linha, coluna))
        {
            //Sinaliza na matriz de verificação que esta posição já foi percorrida (true)
            this.setMatrizVerificada(linha, coluna);
            
            //**** Secção de verificação dos campos adjacentes (direita, esquerda, cima e baixo) ****
            
            //Condições comuns aos 4 sentidos de verificação:
            //1- O valor na posição adjacente (linha,coluna) da matriz em análise tem de ser 1 
            //E
            //2- O valor na posição adjacente (linha +/- 1,coluna +/- 1) da matriz de verificação tem de ser false
            if(this.verificaDireita(linha,coluna) == 1 && !this.getMatrizVerificada(linha, coluna + 1))
            {
                if(!this.getMatrizPosicoesInseridas(linha, coluna + 1))
                {
                    //Posição adjacente inserida apenas no caso de não ter sido inserida anteriormente num array parcial ou final
                    this.insereArrayParcial(linha, coluna + 1);
                    //Marca posição adjacente como inserida
                    this.setMatrizPosicoesInseridas(linha, coluna + 1);
                }
                //Flag para posteriormente chamar função navegaMatriz de forma recursiva
                flagDireita = true;
            }
            else
            {
                //Não estão reunidas as condições para chamar função navegaMatriz recursivamente
                flagDireita = false;
            }
            
            if(this.verificaEsquerda(linha,coluna) == 1 && !this.getMatrizVerificada(linha, coluna - 1))
            {
                if(!this.getMatrizPosicoesInseridas(linha, coluna - 1))
                {
                    //Posição adjacente inserida apenas no caso de não ter sido inserida anteriormente num array parcial ou final
                    this.insereArrayParcial(linha, coluna - 1);
                    //Marca posição adjacente como inserida
                    this.setMatrizPosicoesInseridas(linha, coluna - 1);
                }          
                //Flag para posteriormente chamar função navegaMatriz de forma recursiva
                flagEsquerda = true;
            }
            else
            {
                //Não estão reunidas as condições para chamar função navegaMatriz recursivamente
                flagEsquerda = false;
            }
            
            if(this.verificaAcima(linha,coluna) == 1 && !this.getMatrizVerificada(linha - 1, coluna))
            {
                if(!this.getMatrizPosicoesInseridas(linha - 1, coluna))
                {
                    //Posição adjacente inserida apenas no caso de não ter sido inserida anteriormente num array parcial ou final
                    this.insereArrayParcial(linha - 1, coluna);
                    //Marca posição adjacente como inserida
                    this.setMatrizPosicoesInseridas(linha - 1, coluna);
                }                
                //Flag para posteriormente chamar função navegaMatriz de forma recursiva
                flagAcima = true;
            }
            else
            {
                //Não estão reunidas as condições para chamar função navegaMatriz recursivamente
                flagAcima = false;
            }        
            
            if(this.verificaAbaixo(linha, coluna) == 1 && !this.getMatrizVerificada(linha + 1, coluna))
            {
                if(!this.getMatrizPosicoesInseridas(linha + 1, coluna))
                {
                    //Posição adjacente inserida apenas no caso de não ter sido inserida anteriormente num array parcial ou final
                    this.insereArrayParcial(linha + 1, coluna);
                    //Marca posição adjacente como inserida
                    this.setMatrizPosicoesInseridas(linha + 1, coluna);
                }                    
                //Flag para posteriormente chamar função navegaMatriz de forma recursiva
                flagAbaixo = true;
            }
            else
            {
                //Não estão reunidas as condições para chamar função navegaMatriz recursivamente
                flagAbaixo = false;
            }        
            
            //**** Secção para chamar função navegaMatriz recursivamente ****
            
            //Condições comuns aos 4 sentidos recursivos:
            //1- Valor da flag correspondente tem de ser true
            //E
            //2- A coluna/linha acrescido/substraido em 1 nao pode exceder limites da coluna/linha
            
            if(flagDireita && coluna+1 < this.numeroColunas)
            {
              this.navegaMatriz(linha,coluna + 1);
              //Quando a função recursiva termina, é necessário subtrair em 1 o número da profundidade da recursividade
              this.contadorRecursividade--;
              //Após a função recursiva terminar, esta flag passa a false, para permitir sinalizar o fim de verificação da adjacência neste sentido
              flagDireita = false;                                
            }

            if(flagEsquerda && coluna - 1 >= 0)
            {
                this.navegaMatriz(linha,coluna - 1);
                //Quando a função recursiva termina, é necessário subtrair em 1 o número da profundidade da recursividade
                this.contadorRecursividade--;
                //Após a função recursiva terminar, esta flag passa a false, para permitir sinalizar o fim de verificação da adjacência neste sentido
                flagEsquerda = false;                                  
            }

            if(flagAbaixo && linha+1 < this.numeroLinhas)
            {

                this.navegaMatriz(linha + 1, coluna);
                //Quando a função recursiva termina, é necessário subtrair em 1 o número da profundidade da recursividade
                this.contadorRecursividade--;
                //Após a função recursiva terminar, esta flag passa a false, para permitir sinalizar o fim de verificação da adjacência neste sentido
                flagAbaixo = false;                                  
            }   

            if(flagAcima && linha - 1 >= 0)
            {
               this.navegaMatriz(linha - 1, coluna);
               //Quando a função recursiva termina, é necessário subtrair em 1 o número da profundidade da recursividade
               this.contadorRecursividade--;    
               //Após a função recursiva terminar, esta flag passa a false, para permitir sinalizar o fim de verificação da adjacência neste sentido
               flagAcima = false;
            }

            //**** Função para detectar o fim da recursividade da função navegaMatriz **** 
            //Condições:
            //1- O valor das 4 flags de verificação tem de ser false
            //E
            //2- O valor da profundidade da recursividade tem de ser 1, garantindo que não existem ainda outras funções recursivas por terminar
            //E
            //3- O valor do array parcial não pode ser vazio, para assim não inserir os casos, da matriz em análise, cuja posição possui valor 1 e todas as posições adjacentes são 0
            if(!flagAbaixo && !flagAcima && !flagDireita & !flagEsquerda && this.contadorRecursividade == 1 && !this.arrayParcial.isEmpty())
            {    
                //A posição que deu início à navegação é inserido no array parcial de posições
                this.insereArrayParcial(this.linhaInicial, this.colunaInicial);
                //Marca posição inicial como inserida
                this.setMatrizPosicoesInseridas(this.linhaInicial, this.colunaInicial);
                //O array parcial é inserido no array final
                this.insereArrayFinal();
                //O array parcial passa a vazio
                this.setArrayParcial("");
                //A profundidade de recursividade passa a 0
                this.contadorRecursividade = 0;
            }
        }
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        AdjacentCellsChallenge matriz = new AdjacentCellsChallenge();
        

        Scanner teclado = new Scanner(System.in);
        System.out.println("Seleccione matriz:\n1- 100x100\n2- 1000x1000\n3- 10000x10000\n4- 20000x20000\n5- 5x8 (teste)");
        try{
            int opcao = teclado.nextInt();
            switch(opcao)
            {
                case 1:
                    matriz.criaMatrizJSON(1);
                    break;
                case 2:
                    matriz.criaMatrizJSON(2);
                    break;
                case 3:
                    matriz.criaMatrizJSON(3);
                    break;
                case 4:
                    matriz.criaMatrizJSON(4);
                    break;
                case 5:
                    matriz.criaMatrizTeste(5,8);
                    break;                
                default:
                    System.out.println("Opção inválida!");
            }

            if(opcao > 0 && opcao < 6 && !matriz.isMatrizNull())
            {
                int linha = matriz.getNumeroLinhasMatriz();
                int coluna = matriz.getNumeroColunasMatriz();

                for(int y = 0; y < linha; y++)
                {
                    for(int x = 0; x < coluna; x++)
                    {
                        if(matriz.getValorPosicao(y, x) == 1 && !matriz.getMatrizVerificada(y, x))
                        {
                            matriz.setLinhaInicial(y);
                            matriz.setColunaInicial(x);
                            matriz.navegaMatriz(y,x);
                            matriz.setContadorRecursividade(0);
                        }
                    }
                }
            }            
            
        } catch (InputMismatchException e){
            System.out.println("É permitido apenas inserir números.");
        }

        if(matriz.getArrayFinal().isEmpty())
            matriz.setArrayFinal("Sem resultados");
           
        BufferedWriter output = null;
        
        try 
        {
            File file = new File("./output/resultado.txt");
            output = new BufferedWriter(new FileWriter(file));
            output.write(matriz.getArrayFinal());
        }
        catch ( IOException e )
        {
            System.out.println("Não foi possível guardar resultado no ficheiro \\output\\resultado.txt");
        }
        finally 
        {
          if ( output != null ) {
            output.close();
        }
        }
    }
}
        
    



