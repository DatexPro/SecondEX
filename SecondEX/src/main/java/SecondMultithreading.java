import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SecondMultithreading {
    GetAlljsonFile getAlljsonFile = new GetAlljsonFile();
    private int COUNT = getAlljsonFile.getJSONfile().length;
    private File[] file;
    private GetFineSecondMEthod getFineSecondMethod;

    public SecondMultithreading() {
        file = getAlljsonFile.getJSONfile();
        getFineSecondMethod = new GetFineSecondMEthod();
    }
    //Метод для второго задания который дает возможность парсить несколько
    //файлов одновременно
    public void getSecondFineTiketMethod() {
        CountDownLatch flow = new CountDownLatch(COUNT);
        ExecutorService executorService;
        executorService = Executors.newFixedThreadPool(3);
        System.out.println("Second method : Запуск потоков");
        for (int i = 0; i < COUNT; i++) {
            executorService.execute(new SecondMultithreading.MyThread(flow, file[i]));
        }
        try {
            flow.await();
        } catch (InterruptedException exc) {
        }
        executorService.shutdown();
        System.out.println("Завершение потока");
    }

    class MyThread implements Runnable {
        CountDownLatch latch;
        File file;
        MyThread(CountDownLatch latch, File file) {
            this.latch = latch;
            this.file = file;
        }

        public void run() {
            getFineSecondMethod.getSecondMethod(file);
            latch.countDown();
            System.out.println(file + " : " + latch);

        }
    }
}
