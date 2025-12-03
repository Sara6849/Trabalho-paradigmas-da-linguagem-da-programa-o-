// CounterDemo.java
//Nomes: Laura Venancio, Maria Julia, Sara Ramos. 
// data: 20/11/2025
import java.util.concurrent.atomic.AtomicInteger;

public class contador {

    
    static class UnsafeCounter {
        public int count = 0;
        public void increment() {
            count = count + 1; 
        }
        public int get() { return count; }
    }

    
    static class SafeCounterSync {
        private int count = 0;
        public synchronized void increment() {
            count++;
        }
        public synchronized int get() { return count; }
    }

    
    static class SafeCounterAtomic {
        private AtomicInteger count = new AtomicInteger(0);
        public void increment() {
            count.incrementAndGet();
        }
        public int get() { return count.get(); }
    }

    public static void runTest(final Runnable incrementTask, int numThreads, int incrementsPerThread) throws InterruptedException {
        Thread[] threads = new Thread[numThreads];
        for (int t = 0; t < numThreads; t++) {
            threads[t] = new Thread(() -> {
                for (int i = 0; i < incrementsPerThread; i++) {
                    incrementTask.run();
                }
            });
            threads[t].start();
        }
        for (Thread th : threads) th.join();
    }

    public static void main(String[] args) throws InterruptedException {
        int numThreads = 8;
        int incrementsPerThread = 100_000;

        
        UnsafeCounter uc = new UnsafeCounter();
        runTest(() -> uc.increment(), numThreads, incrementsPerThread);
        System.out.println("Contador inseguro: Esperado = " + (numThreads * incrementsPerThread) +  " Obtido = " + uc.get());

        
        SafeCounterSync scs = new SafeCounterSync();
        runTest(() -> scs.increment(), numThreads, incrementsPerThread);
        System.out.println("Contador seguro: Esperado = " + (numThreads * incrementsPerThread) + " Obtido = " + scs.get());

        
        SafeCounterAtomic sca = new SafeCounterAtomic();
        runTest(() -> sca.increment(), numThreads, incrementsPerThread);
        System.out.println("Contador seguro (com AtomicInteger): Esperado = " + (numThreads * incrementsPerThread) + " Obtido = " + sca.get());
    }
}

