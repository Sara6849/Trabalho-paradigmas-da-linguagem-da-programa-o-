import java.util.concurrent.*;
import java.util.*;

public class soma_parallelo {

    public static long somaParalela(int[] A, int numThreads) throws InterruptedException, ExecutionException {
        if (A == null) return 0L;
        if (numThreads <= 0) numThreads = 1;
        int n = A.length;
        ExecutorService pool = Executors.newFixedThreadPool(numThreads);
        List<Future<Long>> futures = new ArrayList<>();

        int chunkSize = (n + numThreads - 1) / numThreads; // ceil division
        for (int t = 0; t < numThreads; t++) {
            final int start = t * chunkSize;
            final int end = Math.min(start + chunkSize, n);
            if (start >= end) break; // nada para esse thread
            Callable<Long> task = () -> {
                long localSum = 0L;
                for (int i = start; i < end; i++) {
                    localSum += A[i];
                }
                return localSum;
            };
            futures.add(pool.submit(task));
        }

        long total = 0L;
        for (Future<Long> f : futures) {
            total += f.get();
        }

        pool.shutdown();
        return total;
    }

    // método main para testar
    public static void main(String[] args) throws Exception {
        int n = 10_000_000;
        int[] A = new int[n];
        Random r = new Random(0);
        for (int i = 0; i < n; i++) A[i] = r.nextInt(100);

        int threads = 4;
        long start = System.currentTimeMillis();
        long sum = somaParalela(A, threads);
        long elapsed = System.currentTimeMillis() - start;

        // validação (soma sequencial)
        long seq = 0L;
        for (int v: A) seq += v;

        System.out.println("Soma paralela = " + sum);
        System.out.println("Soma sequencial = " + seq);
        System.out.println("Tempo (ms) = " + elapsed);
    }
}
