import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelSortingAlgorithms {
    
    public static void bubbleSort(int[] arr, FileWriter writer) {
        long startTime = System.currentTimeMillis();

        int n = arr.length;
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (arr[j] > arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }

        long endTime = System.currentTimeMillis();
        writeResult(writer, "Bubble Sort", endTime - startTime);
    }

    public static void quickSort(int[] arr, int low, int high, FileWriter writer) {
        long startTime = System.currentTimeMillis();

        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1, writer);
            quickSort(arr, pi + 1, high, writer);
        }

        long endTime = System.currentTimeMillis();
        if (low == 0 && high == arr.length - 1) {
            writeResult(writer, "Quick Sort", endTime - startTime);
        }
    }

    public static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    public static void mergeSort(int[] arr, int l, int r, FileWriter writer) {
        long startTime = System.currentTimeMillis();

        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(arr, l, m, writer);
            mergeSort(arr, m + 1, r, writer);
            merge(arr, l, m, r);
        }

        long endTime = System.currentTimeMillis();
        if (l == 0 && r == arr.length - 1) {
            writeResult(writer, "Merge Sort", endTime - startTime);
        }
    }

    public static void merge(int[] arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
        int[] L = new int[n1];
        int[] R = new int[n2];
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    public static void insertionSort(int[] arr, FileWriter writer) {
        long startTime = System.currentTimeMillis();

        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }

        long endTime = System.currentTimeMillis();
        writeResult(writer, "Insertion Sort", endTime - startTime);
    }

    public static void writeResult(FileWriter writer, String algorithm, long executionTime) {
        try {
            writer.append(algorithm + "," + executionTime + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[1000];
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(1000);
        }
    
        try {
            final FileWriter writer = new FileWriter("resultados.csv");
            writer.append("Algoritmo,Tempo de Execução (ms)\n");
    
            ExecutorService executorService = Executors.newFixedThreadPool(4); // Altere o número de threads conforme desejado
    
            executorService.execute(() -> bubbleSort(arr.clone(), writer));
            executorService.execute(() -> quickSort(arr.clone(), 0, arr.length - 1, writer));
            executorService.execute(() -> mergeSort(arr.clone(), 0, arr.length - 1, writer));
            executorService.execute(() -> insertionSort(arr.clone(), writer));
    
            executorService.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
