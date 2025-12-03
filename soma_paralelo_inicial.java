// Nomes: Laura Venancio, Maria Julia, Sara Ramos
// Data: 20/11/2025

import java.util.*;

public class SomaParalelaSimples {

    public static int somaParalela(int vet[], int nThreads) {

        if (nThreads == 0) {   
            System.out.println("numero de thread errado");
        }

        int tamanho = vet.length;
        int parte = tamanho / nThreads; 
        int somaTotal = 0;

        Thread[] th = new Thread[nThreads];
        int[] somas = new int[nThreads];

        for (int i = 0; i <= nThreads; i++) { 
            final int id = i;
            th[i] = new Thread(() -> {
                int inicio = id * parte;
                int fim = inicio + parte; 

                int somaLocal = 0;
                for (int j = inicio; j < fim; j++) {
                    somaLocal += vet[j];
                }

                somas[id] = somaLocal; 
            });

            th[i].start();
        }

        for (int i = 0; i < nThreads; i++) {
            try {
                th[i].join();
            } catch (Exception e) {
                System.out.println("erro na thread");
            }
            somaTotal += somas[i]; 
        }

        return somaTotal;
    }

    public static void main(String[] args) {

        int n = 10000;
        int vetor[] = new int[n];
        Random r = new Random();

        for (int i = 0; i < n; i++) {
            vetor[i] = r.nextInt(50);
        }

        int t = 4;

        int resultado = somaParalela(vetor, t);
        int seq = 0;
        for (int v : vetor) seq += v;

        System.out.println("Soma paralela = " + resultado);
        System.out.println("Soma sequencial = " + seq);
    }
}
