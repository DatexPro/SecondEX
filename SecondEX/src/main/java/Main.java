import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        FirstMultithreading firstMultithreading = new FirstMultithreading();
        firstMultithreading.getFirstFineTiketMethod();
        SecondMultithreading secondMultithreading = new SecondMultithreading();
        secondMultithreading.getSecondFineTiketMethod();
    }
}
