import extractor.Extractor;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;


public class App {
    public static void main(String[] args) throws IOException {
        Extractor extractor = new Extractor();
        String text = extractor.toText("/home/zeta/projects/spminer/test/7251-25976-1-PB.pdf");
        System.out.println(text);
    }
}
