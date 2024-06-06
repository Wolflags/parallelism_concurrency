package edu.pucmm;

/**
 * @author me@fredpena.dev
 * @created 06/06/2024  - 08:46
 */
public class ParallelMatrixSearch {

    private static final int MATRIX_SIZE = 1000;
    private static final int THREAD_COUNT = 4;
    private static final int[][] matrix = new int[MATRIX_SIZE][MATRIX_SIZE];
    private static final int TARGET = 256; // Número a buscar
    private static long startTime;
    private static long endTime;

    public static void main(String[] args) throws InterruptedException {
        // Inicializar la matriz con valores aleatorios
        for(int i=0,j=0; i<MATRIX_SIZE; i++) {
            for(j=0; j<MATRIX_SIZE; j++) {
                matrix[i][j] = (int)(Math.random() * 1000);
            }
        }

        // Medir el tiempo de ejecución de la búsqueda secuencial
        startTime = System.currentTimeMillis();
        sequentialSearch();
        endTime = System.currentTimeMillis();


        //System.out.println("Resultado búsqueda secuencial: " + ...);
        System.out.println("Tiempo búsqueda secuencial: " + (endTime - startTime) + "ms");

        // Medir el tiempo de ejecución de la búsqueda paralela
        //...
        startTime = System.currentTimeMillis();
        parallelSearch();
        endTime = System.currentTimeMillis();
        // System.out.println("Resultado búsqueda paralela: " + ...);
        System.out.println("Tiempo búsqueda paralela: " + (endTime - startTime) + "ms");
    }

    private static void sequentialSearch() {
        for(int i=0; i<MATRIX_SIZE; i++) {
            for(int j=0; j<MATRIX_SIZE; j++) {
                if(matrix[i][j] == TARGET) {
                    System.out.println("Elemento encontrado en la posición [" + i + "][" + j + "]");
                    return;
                }
            }
        }
    }

    private static void parallelSearch() throws InterruptedException {
        Thread[] threads = new Thread[THREAD_COUNT];
        for(int i=0;i<THREAD_COUNT;i++){
            int Inicio = i*(matrix.length/THREAD_COUNT);
            int Final = (i+1)*(matrix.length/THREAD_COUNT);
            threads[i] = new Thread(new Busqueda(Inicio,Final));
        }
        for(int i=0;i<THREAD_COUNT;i++){
            threads[i].start();
            threads[i].join();
        }

    }

    public static class Busqueda implements Runnable{
        private int Inicio;
        private int Final;

        public Busqueda(int Inicio, int Final){
            this.Inicio = Inicio;
            this.Final = Final;
        }

        @Override
        public void run(){
            for(int i=Inicio; i<Final; i++) {
                for(int j=0; j<MATRIX_SIZE; j++) {
                    if(matrix[i][j] == TARGET) {
                        System.out.println("Elemento encontrado en la posición [" + i + "][" + j + "]");
                        return;
                    }
                }
            }
        }
    }


}
