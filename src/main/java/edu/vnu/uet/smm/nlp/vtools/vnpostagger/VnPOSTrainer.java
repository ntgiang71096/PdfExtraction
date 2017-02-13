package edu.vnu.uet.smm.nlp.vtools.vnpostagger;

import opennlp.tools.ml.maxent.quasinewton.QNTrainer;
import opennlp.tools.postag.*;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.TrainingParameters;
import opennlp.tools.util.model.ModelType;

import java.io.*;

public class VnPOSTrainer {
	static int cutoff = 1;
	static int iteration = 100;

	private static ObjectStream<POSSample> createSampleStream() throws IOException {
		InputStream in = new FileInputStream("models/nlp/vn/vnpostagger/vi-pos.train");

		return new VnPOSSampleStream(new InputStreamReader(in));
	}

	static POSModel trainPOSModel(ModelType type) throws Exception {
		TrainingParameters mlParams = new TrainingParameters();
		mlParams.put(TrainingParameters.ALGORITHM_PARAM, QNTrainer.MAXENT_QN_VALUE);
		mlParams.put(TrainingParameters.ITERATIONS_PARAM, Integer.toString(iteration));
		mlParams.put(TrainingParameters.CUTOFF_PARAM, Integer.toString(cutoff));

		return POSTaggerME.train("vi", createSampleStream(), mlParams, new POSTaggerFactory(null, null));
	}

	public static void main(String[] args) throws Exception {
		POSModel posModel = trainPOSModel(ModelType.MAXENT);

		OutputStream out = new FileOutputStream("models/nlp/vn/vnpostagger/vi-pos.model");
		posModel.serialize(out);
		out.close();

		InputStream in = new FileInputStream("models/nlp/vn/vnpostagger/vi-pos.model");
		// POSModel posModel = new POSModel(in);
		posModel = new POSModel(in);

		POSTagger tagger = new POSTaggerME(posModel);

		String sentenceString = "1 . Bản_chất của Nhà_nước ta là nhà_nước của nhân_dân , do nhân_dân , vì nhân_dân , là sự thể_hiện quyền làm_chủ của nhân_dân dưới sự lãnh_đạo của Đảng .";
		String[] tokens = WhitespaceTokenizer.INSTANCE.tokenize(sentenceString);
		String[] tags = tagger.tag(tokens);

		for (int i = 0; i < tokens.length; i++)
			System.out.print(tokens[i] + "/" + tags[i] + " ");

		in.close();
	}

}
