

import java.io.*;

import java.util.*;
import java.util.List;



public class GetFineFirstMethod {
    private static final String FILENAME = "src/Results/First_file.json";
    private Map<String, Double> mapPrice;

    private FileOperation fileOperation;
    public GetFineFirstMethod() {
        mapPrice = new HashMap<>();
        fileOperation = new FileOperation();
    }
    //Записывает общую сумма штрафа по каждому
    //типу нарушений за все года, отсортировав по сумме.
    public synchronized void getFirstMethod(File file) {
        try {
            double price = 0;
            List<FineTicket> fine_ticketList = fileOperation.readJSON(file);
            for (FineTicket fine_ticket : fine_ticketList) {
                if (mapPrice.containsKey(fine_ticket.getType())) {
                    price = mapPrice.get(fine_ticket.getType()) + fine_ticket.getFine_amount();
                    mapPrice.put(fine_ticket.getType(), price);
                } else {
                    mapPrice.put(fine_ticket.getType(), fine_ticket.getFine_amount());
                }
            }
            List<Map.Entry<String, Double>> listMap = new ArrayList<>(mapPrice.entrySet());
            Collections.sort(listMap, ((o1, o2) -> o1.getValue().compareTo(o2.getValue())));
            fileOperation.writrJSON(listMap, FILENAME);
        } catch (Exception e) {
            e.getMessage();
        }
    }

}

