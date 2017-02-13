package edu.vnu.uet.smm.nlp.vntextpro.vnsentsegmenter;

import edu.vnu.uet.smm.nlp.vntextpro.mlearning.maxent.Classification;
import edu.vnu.uet.smm.nlp.vntextpro.resources.regexes.PunctuationsMatcher;
import edu.vnu.uet.smm.nlp.vntextpro.util.Pair;
import edu.vnu.uet.smm.nlp.vntextpro.util.Parameters;
import edu.vnu.uet.smm.nlp.vntextpro.util.StrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hieupx
 */
public class VnSentSegmenter {
	private Classification classifier = null;

	public VnSentSegmenter() {
		this.classifier = new Classification(Parameters.getNlpVnSentSegmenterModelDir());
	}
	
	public VnSentSegmenter(String modelDir) {
		this.classifier = new Classification(modelDir);
	}

	public void init() {
		if (!this.classifier.isInitialized()) {
			this.classifier.init();
		}
	}

	public List<String> segment(String text) {		
		List<String> tokens = StrUtil.tokenizeStr(SentSegPrepro.preprocessText(text));

		List<String> data = new ArrayList<String>();
		List<Integer> majorIdxes = new ArrayList<Integer>();
		List<Pair<Integer, Integer>> minorIdxes = new ArrayList<Pair<Integer, Integer>>();

		// generate features
		FeaGenerator.scanFeatures(tokens, data, majorIdxes, minorIdxes);

		// classify
		List<String> labels = this.classifier.classify(data);

		// split sentences
		return segmentSentences(tokens, labels, majorIdxes, minorIdxes);
	}

	public List<String> segmentSentences(List<String> tokens, List<String> labels, List<Integer> majorIdxes,
			List<Pair<Integer, Integer>> minorIdxes) {

		List<String> results = new ArrayList<String>();

		List<String> positiveLabels = new ArrayList<String>();
		List<Integer> positiveMajorIdxes = new ArrayList<Integer>();
		List<Pair<Integer, Integer>> positiveMinorIdxes = new ArrayList<Pair<Integer, Integer>>();

		for (int i = 0; i < labels.size(); i++) {
			String label = (String) labels.get(i);
			Integer majorIdx = (Integer) majorIdxes.get(i);
			Pair<Integer, Integer> minorIdx = (Pair<Integer, Integer>) minorIdxes.get(i);

			if (label.equals(SentSegConstants.positiveLabel)) {
				positiveLabels.add(label);
				positiveMajorIdxes.add(majorIdx);
				positiveMinorIdxes.add(minorIdx);
			}
		}

		if (positiveLabels.size() <= 0) {
			String sentence = StrUtil.join(tokens);
			if (sentence.length() > 0) {
				results.add(sentence);
			}

			return results;
		}

		int lastMajorIdx = 0;
		int lastMinorIdx = 0;

		for (int i = 0; i < positiveLabels.size(); i++) {
			String sentence = "";

			Integer majorIdx = (Integer) positiveMajorIdxes.get(i);
			Pair<Integer, Integer> minorIdx = (Pair<Integer, Integer>) positiveMinorIdxes.get(i);

			String bToken = (String) tokens.get(lastMajorIdx);
			String cToken = (String) tokens.get(majorIdx.intValue());

			String suffix = bToken.substring(lastMinorIdx, bToken.length());
			if (suffix.length() > 0 && !(PunctuationsMatcher.match("right-quotation", suffix)
					|| PunctuationsMatcher.match("right-braket", suffix))) {
				sentence += suffix + " ";
			}

			for (int j = lastMajorIdx + 1; j < majorIdx.intValue(); j++) {
				String token = (String) tokens.get(j);
				sentence += token + " ";
			}

			if (minorIdx.second <= cToken.length() - 1) {
				String fPartSE = cToken.substring(0, minorIdx.second);
				String sPart = cToken.substring(minorIdx.second);

				if (sPart != null && sPart.length() > 0 && (PunctuationsMatcher.match("right-quotation", sPart)
						|| PunctuationsMatcher.match("right-braket", sPart))) {
					sentence += cToken;
				} else {
					sentence += fPartSE;
				}

			} else {
				sentence += cToken;
			}

			sentence = StrUtil.normalizeStr(sentence);
			if (sentence.length() > 0) {
				results.add(sentence);
			}

			lastMajorIdx = majorIdx.intValue();
			lastMinorIdx = minorIdx.second.intValue();
		}

		if (lastMajorIdx < tokens.size() - 1) {
			String sentence = "";

			String bToken = (String) tokens.get(lastMajorIdx);

			String suffix = bToken.substring(lastMinorIdx, bToken.length());
			if (suffix.length() > 0 && !(PunctuationsMatcher.match("right-quotation", suffix)
					|| PunctuationsMatcher.match("right-braket", suffix))) {
				sentence += suffix + " ";
			}

			for (int j = lastMajorIdx + 1; j < tokens.size(); j++) {
				sentence += (String) tokens.get(j) + " ";
			}

			sentence = StrUtil.normalizeStr(sentence);
			if (sentence.length() > 0) {
				results.add(sentence);
			}
		}

		return results;
	}
}
