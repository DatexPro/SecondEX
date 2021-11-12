import java.io.File;
import java.io.FilenameFilter;
import java.nio.charset.StandardCharsets;

public class GetAlljsonFile {
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
