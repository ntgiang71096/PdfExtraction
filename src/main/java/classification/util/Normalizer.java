package classification.util;

import com.google.common.base.*;
import com.google.common.base.Optional;
import com.optimaize.langdetect.LanguageDetector;
import com.optimaize.langdetect.LanguageDetectorBuilder;
import com.optimaize.langdetect.i18n.LdLocale;
import com.optimaize.langdetect.ngram.NgramExtractors;
import com.optimaize.langdetect.profiles.LanguageProfile;
import com.optimaize.langdetect.profiles.LanguageProfileReader;
import com.optimaize.langdetect.text.CommonTextObjectFactories;
import com.optimaize.langdetect.text.TextObject;
import com.optimaize.langdetect.text.TextObjectFactory;
import config.Config;
import edu.vnu.uet.smm.nlp.vntextpro.vnsentsegmenter.VnSentSegmenter;
import edu.vnu.uet.smm.nlp.vntextpro.vntokenizer.VnTokenizer;
import edu.vnu.uet.smm.nlp.vntextpro.vnwordsegmenter.VnWordSegmenter;
import org.apache.log4j.BasicConfigurator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Normalizer {
    private static VnWordSegmenter wordSegmenter;
    private static VnSentSegmenter sentSegmenter;
    private static Set<String> stopWordsVi;
    private static Set<String> stopWordsEn;
    private static Set<String> punctuations;
    private static LanguageDetector languageDetector;
    private static TextObjectFactory textObjectFactory;

    static {
        try {
            loadResources();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Load all languages:
        List<LanguageProfile> languageProfiles = null;
        try {
            languageProfiles = new LanguageProfileReader().readAllBuiltIn();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Build language detector:
        languageDetector = LanguageDetectorBuilder.create(NgramExtractors.standard())
                .withProfiles(languageProfiles)
                .build();

        //Create a text object factory
        textObjectFactory = CommonTextObjectFactories.forDetectingOnLargeText();

        //Init sentence segmenter
        sentSegmenter = new VnSentSegmenter(Config.VNSENTSEGMENTER_MODEL_PATH);
        sentSegmenter.init();

        //Init words segmenter
        wordSegmenter = new VnWordSegmenter(Config.VNWORDSEGMENTER_MODEL_PATH);
        wordSegmenter.init();
    }

    private static void loadResources() throws IOException {
        String resourceDir = Config.RESOURCES_DIR;
        String stopwordsViFile = "stopwords-vi.txt";
        String stopwordsEnFile = "stopwords-en.txt";
        String punctuationsFile = "punctuations.txt";

        // Load Vietnamese stop words
        stopWordsVi = new HashSet<String>();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(resourceDir + stopwordsViFile), "UTF8"));
        String line;
        while ((line = br.readLine()) != null) {
            stopWordsVi.add(line);
        }
        br.close();

        // Load English stop words
        stopWordsEn = new HashSet<String>();
        br = new BufferedReader(
                new InputStreamReader(new FileInputStream(resourceDir + stopwordsEnFile)));
        while ((line = br.readLine()) != null) {
            stopWordsEn.add(line);
        }
        br.close();

        // Load punctuations
        punctuations = new HashSet<String>();
        br = new BufferedReader(
                new InputStreamReader(new FileInputStream(resourceDir + punctuationsFile), "UTF8"));
        while ((line = br.readLine()) != null) {
            punctuations.add(line);
        }
        br.close();
    }

    private static boolean acceptable(String token, String langCode) {
        if (langCode.equals("vi") && stopWordsVi.contains(token)) return false;
        if (langCode.equals("en") && stopWordsEn.contains(token)) return false;
        if (token.matches("_+")) return false;

        for (String punctuation : punctuations) {
            if (token.contains(punctuation)) return false;
        }

        return true;
    }

    private static String removeRedundantPunctuations(String s) {
        while (!s.isEmpty() && punctuations.contains(Character.toString(s.charAt(0)))) {
            s = s.substring(1);
        }

        while (!s.isEmpty() && punctuations.contains(Character.toString(s.charAt(s.length() - 1)))) {
            s = s.substring(0, s.length() - 1);
        }

        return s;
    }

    public static String normalize(String input) throws Exception {
        StringBuilder result = new StringBuilder();
        TextObject textObject = textObjectFactory.forText(input);
        Optional<LdLocale> lang = languageDetector.detect(textObject);
        String langCode = lang.get().toString();
        System.out.println("Detected language: " + langCode);

        if (langCode.equals("vi")) {
            List<String> sentences = sentSegmenter.segment(input);
            for (int i = 0; i < sentences.size(); i++) {

                String sentence = sentences.get(i);
                sentence = sentence.toLowerCase();
                sentence = VnTokenizer.tokenize(sentence);
                sentence = wordSegmenter.segment(sentence);
                StringTokenizer strTok = new StringTokenizer(sentence, " \t\r\n");
                List<String> words = new ArrayList<String>();

                while (strTok.hasMoreTokens()) {
                    String token = removeRedundantPunctuations(strTok.nextToken());
                    //System.out.println(token + " " + acceptable(token, langCode));
                    if (!token.isEmpty() && acceptable(token, langCode)) {
                        words.add(token);
                    }
                }

                String temp = String.join(" ", words);
                result.append(temp).append(" ");
            }
        } else {
            input = input.toLowerCase();
            StringTokenizer strTok = new StringTokenizer(input, " \t\r\n");
            List<String> words = new ArrayList<String>();

            while (strTok.hasMoreTokens()) {
                String token = removeRedundantPunctuations(strTok.nextToken());
                //System.out.println(token + " " + acceptable(token, langCode));
                if (!token.isEmpty() && acceptable(token, langCode)) {
                    words.add(token);
                }
            }

            String temp = String.join(" ", words);
            result.append(temp);
        }

        return result.toString();
    }
}
