package edu.vnu.uet.smm.nlp.vntextpro.vnwordsegmenter;

import edu.vnu.uet.smm.nlp.vntextpro.mlearning.flexcrfs.Prediction;
import edu.vnu.uet.smm.nlp.vntextpro.util.Parameters;
import edu.vnu.uet.smm.nlp.vntextpro.util.StrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hieupx
 */
public class VnWordSegmenter {
	private Prediction predictor = null;

	public VnWordSegmenter() {
		predictor = new Prediction(Parameters.getNlpVnWordSegmenterModelDir());
	}

	public VnWordSegmenter(String modelDir) {
		predictor = new Prediction(modelDir);
	}

	public void init() {
		predictor.init();
	}

	public List<String> correctTags(List<String> tags) {
		List<String> results = new ArrayList<String>();

		String tag = tags.get(0);
		if (tag.equals("I")) {
			results.add("B");
		} else {
			results.add(tag);
		}

		int len = tags.size();
		for (int i = 1; i < len; i++) {
			String previousTag = tags.get(i - 1);
			String currentTag = tags.get(i);
			if (currentTag.equals("I") && previousTag.equals("O")) {
				results.add("B");
			} else {
				results.add(currentTag);
			}
		}

		return results;
	}

	public List<String> predict(List<String> sent) {
		List<List<String>> data = FeaGenerator9886F1.scanFeatures(sent);
		return predictor.predict(data);
	}

	public String predict(String sent) {
		return StrUtil.join(predict(StrUtil.tokenizeStr(sent)));
	}

	public List<String> segment(List<String> sent) {
		List<String> words = new ArrayList<String>();

		List<String> tags = correctTags(predict(sent));

		String word = "";
		int i = 0;
		while (i < sent.size()) {
			String tag = (String) tags.get(i);
			String token = (String) sent.get(i);

			if (tag.equals("B")) {
				if (!word.isEmpty()) {
					words.add(word);
				}
				word = token;

			} else if (tag.equals("I")) {
				word += "_" + token;

			} else if (tag.equals("O")) {
				if (!word.isEmpty()) {
					words.add(word);
					word = "";
				}

				words.add(token);
			}

			i++;
		}

		// for the last word (if any)
		if (!word.isEmpty()) {
			words.add(word);
		}

		return words;
	}

	public String segment(String sent) {
		return StrUtil.join(segment(StrUtil.tokenizeStr(sent)));
	}
}
