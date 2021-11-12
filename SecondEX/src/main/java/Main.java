import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        GetFineFirstMethod getFineTiketMethod1 = new GetFineFirstMethod("src/Results/First_file.json");
        getFineTiketMethod1.getFirstFineTiketMethod();
        GetFineSecondMEthod getFineSecondMEthod = new GetFineSecondMEthod("src/Results/Second_file.json");
        getFineSecondMEthod.getSecondFineTiketMethod();

    }
}
