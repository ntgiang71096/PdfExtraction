/**
 * Created by Admin on 2/12/2017.
 */
public class App {
    public static void main(String[] args) {
        Extractor extractor = new Extractor();
        String text = extractor.toText("C:\\Users\\Admin\\Desktop\\Recent\\Extraction.pdf");
        System.out.println(text);
    }
}
