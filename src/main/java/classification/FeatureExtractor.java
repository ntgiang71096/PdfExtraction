package classification;

import classification.util.BagOfWords;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FeatureExtractor {
    public static String outFilename = "data_features.txt";

    public static void main(String[] args) throws IOException {
        //Read data to featurize
        BufferedReader br = new BufferedReader(new FileReader(PdfProcessor.outputDir + "/" + PdfProcessor.outFileName));
        String line;
        BagOfWords bagOfWords = new BagOfWords();
        List<String[]> data = new ArrayList<String[]>();
        List<String> labels = new ArrayList<String>();

        while ((line = br.readLine()) != null) {
            String label = Character.toString(line.charAt(0));
            String content = line.substring(2);
            labels.add(label);

            String[] words = content.split("\\s+");
            data.add(words);
            bagOfWords.addWords(words);
        }

        br.close();

        //Write features data to file
        BufferedWriter bw = new BufferedWriter(new FileWriter(PdfProcessor.outputDir + "/" + outFilename));

        for (int i=0; i<data.size(); i++) {
            String[] d = data.get(i);
            Map<Integer, Integer> features = bagOfWords.featurize(d);
            bw.write(labels.get(i) + " ");
            for (Map.Entry<Integer, Integer> entry : features.entrySet()) {
                bw.write(entry.getKey() + ":" + entry.getValue() + " ");
            }
            bw.newLine();
        }

        bw.flush(); bw.close();
    }
}
