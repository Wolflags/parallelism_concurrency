package edu.pucmm;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author me@fredpena.dev
 * @created 06/06/2024  - 08:46
 */
public class ProducerConsumer {
    private static final int QUEUE_CAPACITY = 10;
    private static final int PRODUCER_COUNT = 2;
    private static final int CONSUMER_COUNT = 2;
    private static final int PRODUCE_COUNT = 100;
    private static int PRODUCIDO = 0;

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
        Thread[] producers = new Thread[PRODUCER_COUNT];
        Thread[] consumers = new Thread[CONSUMER_COUNT];
        for (int i = 0; i < PRODUCER_COUNT; i++) {
            producers[i] = new Thread(new Producer(queue));
        }
        for (int i = 0; i < CONSUMER_COUNT; i++) {
            consumers[i] = new Thread(new Consumer(queue));
        }
        for (int i = 0; i < PRODUCER_COUNT; i++) {
        producers[i].start();
        }
        for (int i = 0; i < CONSUMER_COUNT; i++) {
            consumers[i].start();
        }


    }

    static class Producer implements Runnable {
        private BlockingQueue<Integer> queue;

        public Producer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (PRODUCIDO < PRODUCE_COUNT) {
                int Random = new Random().nextInt();
                while (queue.remainingCapacity() == 0) {
                    Thread.yield();
                }
                queue.add(Random);
                System.out.println("Producido " + Random);
                PRODUCIDO++;
            }



        }
    }

    static class Consumer implements Runnable {

        private BlockingQueue<Integer> queue;

        public Consumer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                for(int i=0;i<PRODUCE_COUNT;i++){
                    System.out.println("Consumido " + queue.take());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
