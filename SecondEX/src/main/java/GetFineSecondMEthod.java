import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GetFineSecondMEthod {

    private final String FILENAME2;
    private byte[] dataMap;
    private Map<String, Fine> mapFine;
    GetAlljsonFile getAlljsonFile = new GetAlljsonFile();
    private int COUNT = getAlljsonFile.getJSONfile().length;
    private File[] file;
    private ObjectMapper mapper;
    public GetFineSecondMEthod(String f2) {
        mapper = new ObjectMapper();
        FILENAME2 = f2;
        mapFine = new HashMap<>();
        file = getAlljsonFile.getJSONfile();
    }
    public void getSecondFineTiketMethod() {
        CountDownLatch countDownLatch1 = new CountDownLatch(COUNT);
        ExecutorService executorService;
        executorService = Executors.newFixedThreadPool(3);
        System.out.println("Second method : Запуск потоков");
        for (int i = 0; i <COUNT; i++) {
            executorService.execute(new MyThread(countDownLatch1, file[i]));
        }
        try {
            countDownLatch1.await();
        } catch (InterruptedException exc) {
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
                getSecondMethod(file);
                Thread.sleep(1500);
                latch.countDown();
                System.out.println(file + " : " + latch);

            } catch (InterruptedException e) {
            }
        }
    }
    public synchronized void getSecondMethod(File file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            dataMap = Files.readAllBytes(Paths.get(String.valueOf(file)));
            FineTicket[] arrFine = null;
            objectMapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
            arrFine = objectMapper.readValue(dataMap, FineTicket[].class);
            List<FineTicket> fine_ticketList = Arrays.asList(arrFine);
            for (FineTicket fine_ticket : fine_ticketList) {
                String name = fine_ticket.getFirst_name() + " " + fine_ticket.getLast_name();
                if (mapFine.containsKey(name)) {
                    Fine fine = mapFine.get(name);
                    fine.setCount(fine.getCount() + 1);
                    fine.setPrice(fine.getPrice() + fine_ticket.getFine_amount());
                    fine.setAvePrice(fine.getPrice() / fine.getCount());
                } else {
                    Fine fine = new Fine();
                    fine.setName(name);
                    fine.setCount(1);
                    fine.setPrice(fine_ticket.getFine_amount());
                    fine.setAvePrice(fine_ticket.getFine_amount());
                    mapFine.put(name, fine);
                }
            }
            List<Map.Entry<String, Fine>> entryList = new ArrayList<>(mapFine.entrySet());
            writrJSON(entryList, FILENAME2);
        } catch (Exception exp) {
            exp.getMessage();
        }
    }
    @SneakyThrows
    private void writrJSON(Object list, String name) {
        mapper.writeValue(new File(name), list);
    }
}
