import java.io.*;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
    private static final int[] Prueba = {2, 4, 8, 16, 32};

    /*
    Con esta funcion se crea un archivo donde se va a guardar el millon de valores que se generan de manera ramdon
    */
    public static void generador() throws IOException {
        Random random = new Random();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("numbers.txt"))) {
            for (int i = 0; i < 1000000; i++) {
                writer.write(String.valueOf(random.nextInt(10_000) + 1));
                writer.newLine();
            }
        }
    }

    /*
    En esta funcion se lee todos los valores que se generaron en el archivo y lo hace un array
    */
    public static int[] leer() throws IOException {
        int[] array = new int[1000000];
        try (BufferedReader reader = new BufferedReader(new FileReader("numbers.txt"))) {
            for (int i = 0; i < 1000000; i++) {
                array[i] = Integer.parseInt(reader.readLine());
            }
        }
        return array;
    }

    /*
    Esta funcion hace una suma sencuencial
    */
    public static long SumSequen(int[] array) {
        long sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }

    /*
     Esta funcion hace una suma de usando virtual threads y divide el array en partes iguales dependiendo de
     la cantidad de threads
    */
    public static long SumParalela(int[] array, int numThreads) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        int chunkSize = array.length / numThreads;
        AtomicLong totalSum = new AtomicLong(0);
        Future<?>[] futures = new Future[numThreads];

        for (int i = 0; i < numThreads; i++) {
            final int start = i * chunkSize;
            final int end = (i == numThreads - 1) ? array.length : (i + 1) * chunkSize;
            futures[i] = executor.submit(() -> {
                long partialSum = 0;
                for (int j = start; j < end; j++) {
                    partialSum += array[j];
                }
                totalSum.addAndGet(partialSum);
            });
        }
        for (Future<?> future : futures) {
            future.get();
        }
        executor.shutdown();
        return totalSum.get();
    }

    /*
    Aqui se hace una corrida para poder medir el rendimiento y imprimmir de forma ordenada los resultados
    */
    public static void imprimir(int[] array) throws InterruptedException, ExecutionException {
        long startTime = System.nanoTime();
        long sequentialResult = SumSequen(array);
        double sequentialTime = (System.nanoTime() - startTime) / 1_000_000_000.0;

        double[] parallelTimes = new double[Prueba.length];
        long[] parallelResults = new long[Prueba.length];

        for (int i = 0; i < Prueba.length; i++) {
            int threads = Prueba[i];
            startTime = System.nanoTime();
            parallelResults[i] = SumParalela(array, threads);
            parallelTimes[i] = (System.nanoTime() - startTime) / 1_000_000_000.0;
        }

        System.out.println("| Threads | tiempo de ejecucion | tiempo en paralelo | Speedup | eficiencia |");
        System.out.println("|---------|---------------------|--------------------|---------|------------|");
        System.out.printf("| 1       | %.4f              | -                  | 1.0000  | 1.0000     |%n", sequentialTime);
        for (int i = 0; i < Prueba.length; i++) {
            double speedup = sequentialTime / parallelTimes[i];
            double efficiency = speedup / Prueba[i];
            System.out.printf("| %d       | %.4f              | %.4f             | %.4f  | %.4f     |%n",
                    Prueba[i], sequentialTime, parallelTimes[i], speedup, efficiency);
        }
    }

    public static void main(String[] args) {
        try {
            generador();
            int[] array = leer();

            imprimir(array);

        } catch (IOException | InterruptedException | ExecutionException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}