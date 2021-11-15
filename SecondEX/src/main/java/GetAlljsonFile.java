import java.io.File;
import java.io.FilenameFilter;

public class GetAlljsonFile {
    //Метод который проверяет и возвращает все JSON файлы
    public File[] getJSONfile(){
        File file = new File("src/FineTicket");
        return file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".json");
            }
        });

    }
}
