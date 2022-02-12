/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PROYECTO;

import java.util.Scanner;

/**
 *
 * @author Chriss
 */
public class tresEnRaya {
    public static void main(String[] args) throws InterruptedException {
        Scanner entrada = new Scanner(System.in);
        String M[][] = new String[3][3];
        iniciarMatriz(M);
        String humano, ficha, ia;
        String ganador = null;
        int pocision;
        //ingresa el humano la ficha valida con la que jugara
        while (true) {            
            System.out.print("Seleccione una de las fichas(X/O): ");
            ficha = entrada.next().toUpperCase();
            if (ficha.equals("X")) {
                humano = "X";
                ia = "O";
                break;
            }
            else if (ficha.equals("O")) {
                humano = "O";
                ia = "X";
                break;
            }
            else{
                System.out.println("ingrese una ficha valida!!!!!!");
            }
        }
        //se empieza a jugar
        while (ganador == null) {//continua el ciclo siempre que no haya ganador ni empate
            imprimirMatriz(M);
            System.out.print(humano + " ingrese una pocicion: ");
            pocision = entrada.nextInt(); 
            if (! (1 <= pocision && pocision <= 9)) {
                System.out.println("pocicion no valida, ingrese una"
                        + " pocicion valida");
                continue;
            }
            if (humano.equals("O")) {
                if (cambioElementoMatriz(M, pocision, humano)) {
                    if (!estaLleno(M) && GanaXuO(M, ganador) == null) {
                        System.out.println("El Ordenador esta pensando...");
                    Thread.sleep(1000);
                    cambioElementoMatriz(M, DeterminaPocicionIA(M, ia, humano), ia);
                    }
                }
                else{
                    System.out.println("esa pocicion ya esta ocupada!, "
                            + "ingrese otra pocicion: ");
                }
                
            }
            else {
                if (cambioElementoMatriz(M, pocision, humano)) {
                    if (!estaLleno(M) && GanaXuO(M, ganador) == null) {
                        System.out.println("El Ordenador esta pensando...");
                    Thread.sleep(1000);
                    cambioElementoMatriz(M, DeterminaPocicionIA(M, ia, humano), ia);
                    }
                    
                }
                else{
                    System.out.println("esa pocicion ya esta ocupada!, "
                            + "ingrese otra pocicion: ");
                }
                
            }
            ganador = GanaXuO(M, ganador);
        }
        imprimirMatriz(M);
        if (ganador.equals(humano)) {
            System.out.println("Gana el humano!!");
        }
        else if (ganador.equals(ia)) {
            System.out.println("Gana el ordenador!!!!!");
        }
        else{
            System.out.println("empate chocos!!!!");
        }
        
    }

    private static void iniciarMatriz(String[][] M) {//se llena la matriz de numeros en string
        int contador = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                M[i][j] = String.valueOf(contador + 1);
                contador ++;
            }
        }
    }

    private static void imprimirMatriz(String[][] M) {//imprime el tablero del 3 en raya
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("|" + M[i][j]);
            }
            System.out.println("|");
        }
    }
    private static void copiarMatriz(String[][] M, String[][] M1){//copia de una matriz a otra
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                M1[i][j] = M[i][j];
            }
        }
    }
    private static boolean cambioElementoMatriz(String[][] M, int cambio, String turno) {//se modifica un elemento de la matriz
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (M[i][j].equals(String.valueOf(cambio))) {
                    M[i][j] = turno;
                    return true;
                }
            }
        }
        return false;
    }
    private static int DeterminaPocicionIA(String[][] M, String ia, String humano) {//retorna la mejor pocicion
         /*
        aca es donde la IA decidira en que casilla le combiene poner su jugada
        */
        String M1[][] = new String[3][3];
        copiarMatriz(M, M1);
        int pocicionIA = 0, pocicionHumano = 0;//verifica en que pociciones jugando la IA puede ganar
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cambioElementoMatriz(M1, pocicionIA, ia)) {
                    if (GanaXuO(M1, null) != null && GanaXuO(M1, null).equals(ia)) {
                        return pocicionIA;
                    }
                    else{
                        copiarMatriz(M, M1);
                    }
                }
                pocicionIA ++;
            }  
        }
        if (pocicionIA == 9) {//verifica simulando la siguiente jugada del humano y si esta gana ocupa su pocicion
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (cambioElementoMatriz(M1, pocicionHumano, humano)) {
                        if (GanaXuO(M1, null) != null && GanaXuO(M1, null).equals(humano)) {
                            return pocicionHumano;
                        }
                        else{
                            copiarMatriz(M, M1);
                        }
                    }
                    pocicionHumano ++;
                }
            }
        }
        if (pocicionIA == 9) {//de tener pocos elementos en la matriz IA pondra su jugada en una casilla al azar
            while (true) {
                pocicionIA = (int)(Math.random()*(8-0+1))+0;
//                System.out.println(pocicionIA);
                if (cambioElementoMatriz(M1, pocicionIA, ia)) {
                    break;
                }
            }
        }
        return pocicionIA;
    }
    private static String GanaXuO(String[][] M, String ganador) {//verifica si gana X u O, o si hay empate
        int contadorO = 0, contadorX = 0;
        //verifica las filas
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (M[i][j].equals("O")) {
                    contadorO += 1;
                }
                else if (M[i][j].equals("X")) {
                    contadorX += 1;
                }
                if (contadorX == 3) {
                    ganador = "X";
                    return ganador;
                }
                else if (contadorO == 3) {
                    ganador = "O"; 
                    return ganador;
                }
            }
            contadorO = 0; contadorX = 0;
        }
        //verifica las columnas
        contadorO = 0;
        contadorX = 0;
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if (M[i][j].equals("O")) {
                    contadorO += 1;
                }
                else if (M[i][j].equals("X")) {
                    contadorX += 1;
                }
                if (contadorX == 3) {
                    ganador = "X";
                    return ganador;
                }
                else if (contadorO == 3) {
                    ganador = "O";  
                    return ganador;
                }
            }
            contadorO = 0;
            contadorX = 0;
        }
        //verifica la diagonal principal
        contadorO = 0;
        contadorX = 0;
        for (int i = 0; i < 3; i++) {
            if (M[i][i].equals("O")) {
                contadorO += 1;
            }
            else if (M[i][i].equals("X")) {
                contadorX += 1;
            }
            if (contadorX == 3) {
                ganador = "X";
                return ganador;
            }
            else if (contadorO == 3) {
                ganador = "O"; 
                return ganador;
            }
        }
        //verifica la diagonal secundaria
        contadorO = 0;
        contadorX = 0;
        int contadorColumna = 2;
        for (int i = 0; i < 3; i++) {
            if (M[i][contadorColumna].equals("X")) {
                contadorX += 1;
            }
            else if (M[i][contadorColumna].equals("O")) {
                contadorO += 1;
            }
            if (contadorX == 3) {
                ganador = "X";
                return ganador;
            }
            else if (contadorO == 3) {
                ganador = "O"; 
                return ganador;
            }
            contadorColumna --;
        }
        //verifica si hay empate
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Character.isDigit(M[i][j].charAt(0))) {
                    return null;
                }
            }
        }
        return "-1";//hay empate -1 representa que no se encontro :D
    }
    private static boolean estaLleno(String M[][]){
        int contador = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (M[i][j].equals(String.valueOf(contador))) {
                    return false;
                }
                contador ++;
            }
        }
        return true;
    }
}
