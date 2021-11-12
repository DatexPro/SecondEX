import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GetFineFirstMethod extends Thread {
    private final String FILENAME1;
    private Map<String, Double> mapPrice;
    private byte[] dataMap;
    GetAlljsonFile getAlljsonFile = new GetAlljsonFile();
    private int COUNT = getAlljsonFile.getJSONfile().length;
    private File[] file;
    private ObjectMapper mapper;

    public GetFineFirstMethod(String f1) {
        mapper = new ObjectMapper();
        FILENAME1 = f1;
        mapPrice = new HashMap<>();
        file = getAlljsonFile.getJSONfile();
    }

    public void getFirstFineTiketMethod() {
        CountDownLatch countDownLatch1 = new CountDownLatch(COUNT);

        ExecutorService executorService;
        executorService = Executors.newFixedThreadPool(3);
        System.out.println("First method: Запуск потоков");
        for (int i = 0; i <COUNT; i++) {
            executorService.execute(new MyThread(countDownLatch1, file[i]));
        }
        try {
           countDownLatch1.await();
        } catch (InterruptedException exc) {
            exc.getMessage();
        }
        executorService.shutdown();
        System.out.println("Завершение потока");
    }

    class MyThread implements Runnable {
        CountDownLatch latch;
        File file;

        MyThread(CountDownLatch l, File file1) {
            latch = l;
            file = file1;
            //new Thread(this);
        }
        public void run() {
            try {
                //getSecondMethod(file1);
                getFirstMethod(file);
                Thread.sleep(1500);
                latch.countDown();
                System.out.println(file + " : " + latch);

            } catch (InterruptedException e) {
            }
        }
    }

    public synchronized void getFirstMethod(File file) {

        try {
            double price = 0;
            dataMap = Files.readAllBytes(Paths.get(String.valueOf(file)));
            FineTicket[] arrFine = null;
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
            arrFine = objectMapper.readValue(dataMap, FineTicket[].class);
            List<FineTicket> fine_ticketList = Arrays.asList(arrFine);
            for (FineTicket fine_ticket : fine_ticketList) {
                if (mapPrice.containsKey(fine_ticket.getType())) {
                    price = mapPrice.get(fine_ticket.getType()) + fine_ticket.getFine_amount();
                    mapPrice.put(fine_ticket.getType(), price);
                } else {
                    mapPrice.put(fine_ticket.getType(), fine_ticket.getFine_amount());
                }
            }

            List<Map.Entry<String, Double>> l = new ArrayList<>(mapPrice.entrySet());
            Collections.sort(l, new Comparator<Map.Entry<String, Double>>() {
                @Override
                public int compare(Map.Entry<String, Double> a, Map.Entry<String, Double> b) {
                    return (a.getValue()).compareTo(b.getValue());
                }
            });
            writrJSON(l, FILENAME1);
        } catch (Exception e) {
            e.getMessage();
        }
    }
    @SneakyThrows
    private void writrJSON(Object list, String name) {
        mapper.writeValue(new File(name), list);
    }
}

