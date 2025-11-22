// CounterDemo.java
import java.util.concurrent.atomic.AtomicInteger;

public class contador {

    // Versão insegura (race)
    static class UnsafeCounter {
        public int count = 0;
        public void increment() {
            count = count + 1; // não-atômico
        }
        public int get() { return count; }
    }

    // Versão segura 1: synchronized
    static class SafeCounterSync {
        private int count = 0;
        public synchronized void increment() {
            count++;
        }
        public synchronized int get() { return count; }
    }

    // Versão segura 2: AtomicInteger
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

        // Unsafe
        UnsafeCounter uc = new UnsafeCounter();
        runTest(() -> uc.increment(), numThreads, incrementsPerThread);
        System.out.println("Contador inseguro: esperado =" + (numThreads * incrementsPerThread) +       "Obtido=" + uc.get());

        // Safe sync
        SafeCounterSync scs = new SafeCounterSync();
        runTest(() -> scs.increment(), numThreads, incrementsPerThread);
        System.out.println("Contador seguro: esperado =" + (numThreads * incrementsPerThread) + " Obtido=" + scs.get());

        // Safe atomic
        SafeCounterAtomic sca = new SafeCounterAtomic();
        runTest(() -> sca.increment(), numThreads, incrementsPerThread);
        System.out.println("Contador seguro (com AtomicInteger): esperado=" + (numThreads * incrementsPerThread) + " Obtido=" + sca.get());
    }
}

