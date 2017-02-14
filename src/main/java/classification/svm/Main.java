package classification.svm;

import classification.FeatureExtractor;
import classification.PdfProcessor;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static String scaleFileName = "data_scale.txt";
    public static String restoreScaleFileName = "data.scale";

    public static void main(String[] args) throws IOException {
        String fileScale = PdfProcessor.outputDir + "/" + scaleFileName;
        String fileRestoreScale = PdfProcessor.outputDir + "/" + restoreScaleFileName;
        String fileFeatures = PdfProcessor.outputDir + "/" + FeatureExtractor.outFilename;
        String fileTrain = PdfProcessor.outputDir + "/" + "data_train.txt";
        String fileModel = PdfProcessor.outputDir + "/" + "data_model.txt";
        String fileTest = PdfProcessor.outputDir + "/" + "data_test.txt";
        String filePredict = PdfProcessor.outputDir + "/" + "data_predict.txt";

        String[] arg = new String[]{"-s", "0", "-t", "2", "-c", "32.0", "-g", "0.0001220703125", fileTrain, fileModel};
        svm_train.main(arg);
        arg = new String[]{fileTest, fileModel, filePredict};
        svm_predict.main(arg);
    }
}
