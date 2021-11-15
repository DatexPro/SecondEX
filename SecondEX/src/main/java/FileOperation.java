import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileOperation {
    private ObjectMapper mapper;
    private byte[] dataMap;

    public FileOperation(){
        mapper = new ObjectMapper();
    }
    @SneakyThrows
    //Метод который читает содержимое файла
    public List<FineTicket> readJSON(File file){
        dataMap = Files.readAllBytes(Paths.get(String.valueOf(file)));
        FineTicket[] arrFine = null;
        mapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
        arrFine = mapper.readValue(dataMap, FineTicket[].class);
        List<FineTicket> fine_ticketList = Arrays.asList(arrFine);
        return fine_ticketList;
    }
    //Метод который записывает значения листа в файл
    void writrJSON(Object list, String name) throws IOException {
        mapper.writeValue(new File(name), list);
    }
}
