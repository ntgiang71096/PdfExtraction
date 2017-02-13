import extractor.Extractor;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;


public class App {
    public static void main(String[] args) throws IOException {
        String s = "_asdasda____";
        System.out.println(s.matches("_+"));
    }
}
