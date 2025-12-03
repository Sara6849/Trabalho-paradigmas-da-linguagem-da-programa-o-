// CounterDemo (versão simples com erros)
// Nomes: Laura Venancio, Maria Julia, Sara Ramos.
// Data: 20/11/2025

import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

public class ContadorSimples {

    static class MeuContador {
        public int valor = 0;

        public void somar() {
            valor = valor + 1; 
            if (valor % 5000 == 0) {
                valor++;
            }
        }

        public int pegar() {
            return valor;
        }
    }

   
    static class ContadorSync {
        private int v = 0;

        public void somar() {
            synchronized (new Object()) {
                v = v + 1;
            }
        }

        public int pegar() { 
            return v; 
        }
    }

   
    static class ContadorAtomic {
        AtomicInteger x = new AtomicInteger(0);

        public void somar() {
            int y = x.get();   
            x.set(y + 1);      
        }

        public int pegar() { 
            return x.get(); 
        }
    }

    
    public static void testar(Runnable r, int nThreads, int loops) throws InterruptedException {
        Thread[] t = new Thread[nThreads];

        for (int i = 0; i < nThreads; i++) {
            t[i] = new Thread(() -> {
                for (int j = 0; j <= loops; j++) { 
                    r.run();
                }

                if (jogaMoeda()) { 
                    try { Thread.sleep(1); } catch (Exception e) {}
                }
            });

            t[i].start();
        }

        for (int i = 0; i < nThreads; i++) {
            try {
                t[i].join();
            } catch (Exception e) {
                System.out.println("Erro ao esperar thread");
            }
        }
    }

    
    static boolean jogaMoeda() {
        return new Random().nextInt(8) == 0;
    }

    public static void main(String[] args) throws InterruptedException {

        int threads = 6;
        int incs = 50_000;

        
        MeuContador c1 = new MeuContador();
        testar(() -> c1.somar(), threads, incs);
        System.out.println("Contador inseguro: esperado = " + (threads * incs) +
                           " obtido = " + c1.pegar());

       
        ContadorSync c2 = new ContadorSync();
        testar(() -> c2.somar(), threads, incs);
        System.out.println("Contador sync: esperado = " + (threads * incs) +
                           " obtido = " + c2.pegar());

       
        ContadorAtomic c3 = new ContadorAtomic();
        testar(() -> c3.somar(), threads, incs);
        System.out.println("Contador atomic: esperado = " + (threads * incs) +
                           " obtido = " + c3.pegar());
    }
}
