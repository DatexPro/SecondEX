import java.io.File;
import java.util.*;

public class GetFineSecondMEthod {
    private FileOperation fileOperation;
    private Map<String, Fine> mapFine;
    private static final String FILENAME = "src/Results/Second_file.json";

    public GetFineSecondMEthod() {
        fileOperation = new FileOperation();
        mapFine = new HashMap<>();
    }
    //Метод возращает список нарушителей за все года: по каждому нарушителю видно
    //общее количество нарушений, суммарный штраф и среднюю сумму штрафа
    public synchronized void getSecondMethod(File file) {
        try {
            List<FineTicket> fine_ticketList = fileOperation.readJSON(file);
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
            fileOperation.writrJSON(entryList, FILENAME);
        } catch (Exception exp) {
            exp.getMessage();
        }
    }
}
