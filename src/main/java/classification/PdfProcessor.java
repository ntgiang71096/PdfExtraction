package classification;

import classification.util.Normalizer;
import config.Config;
import extractor.Extractor;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;

public class PdfProcessor {
    private static String scientificOutputDir = Config.BASE_PATH + "/src/main/resources/data/scientific";
    private static String nonScientificOutputDir = Config.BASE_PATH + "/src/main/resources/data/non_scientific";
    private static int limitFiles = 5;

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.OFF);

        File dataScientific = new File(Config.DATA_SCIENTIFIC_PATH);
        File[] scientificPdfs= dataScientific.listFiles();
        Extractor extractor = new Extractor();
        int count = 0;

        for (File pdf : scientificPdfs) {
            count++;
            if (pdf.isFile()) {
                System.out.println(pdf.getAbsolutePath());
                String text = extractor.toText(pdf.getAbsolutePath());

                try {
                    String normalText = Normalizer.normalize(text);
                    System.out.println(normalText);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if (count >= limitFiles) break;
        }
    }
}
