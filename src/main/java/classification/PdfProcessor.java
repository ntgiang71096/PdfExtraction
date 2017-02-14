package classification;

import classification.util.Normalizer;
import config.Config;
import extractor.Extractor;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PdfProcessor {
    public static String outputDir = Config.BASE_PATH + "/src/main/resources/data";
    public static String outFileName = "data_total.txt";
    private static int limit = -1;

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.OFF);

        //Process scientific data
        File dataScientific = new File(Config.DATA_SCIENTIFIC_PATH);
        File[] scientificPdfs= dataScientific.listFiles();
        Extractor extractor = new Extractor();
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputDir + "/" + outFileName));
        int count = 0;

        for (File pdf : scientificPdfs) {
            count++;

            if (pdf.isFile()) {
                System.out.println(pdf.getAbsolutePath());
                String text = extractor.toText(pdf.getAbsolutePath());

                try {
                    String normalText = Normalizer.normalize(text);
                    if (normalText != null) {
                        bw.write("1 " + normalText);
                        bw.newLine();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (limit >=0 && count >= limit) break;
        }

        //Process non-scientific data
        File dataNonScientific = new File(Config.DATA_NON_SCIENTIFIC_PATH);
        File[] nonScientificPdfs = dataNonScientific.listFiles();
        count = 0;

        for (File pdf : nonScientificPdfs) {
            count++;

            if (pdf.isFile()) {
                System.out.println(pdf.getAbsolutePath());
                String text = extractor.toText(pdf.getAbsolutePath());

                try {
                    String normalText = Normalizer.normalize(text);
                    if (normalText != null) {
                        bw.write("0 " + normalText);
                        bw.newLine();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (limit >=0 && count >= limit) break;
        }

        bw.flush(); bw.close();
    }
}
