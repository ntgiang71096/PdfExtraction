import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

/**
 * Created by Admin on 2/12/2017.
 */
public class Extractor {
    public String toText(String filePath){
        File file = new File(filePath);
        try {
            PDDocument document = PDDocument.load(file);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String text = pdfTextStripper.getText(document);
            document.close();
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
