package edu.vnu.uet.smm.nlp.vntextpro;

import edu.vnu.uet.smm.nlp.vntextpro.util.StrUtil;
import edu.vnu.uet.smm.nlp.vntextpro.vnsentsegmenter.VnSentSegmenter;
import edu.vnu.uet.smm.nlp.vntextpro.vntokenizer.VnTokenizer;
import edu.vnu.uet.smm.nlp.vntextpro.vnwordsegmenter.VnWordSegmenter;

import java.util.ArrayList;
import java.util.List;

public class VnTextProSingleton {
	private static VnTextProSingleton instance = null;
	private static VnSentSegmenter sentSegmenter;
	private static VnWordSegmenter wordSegmenter;

	public VnTextProSingleton() throws Exception {
		sentSegmenter = new VnSentSegmenter();
		sentSegmenter.init();
		wordSegmenter = new VnWordSegmenter();
		wordSegmenter.init();
	}

	public static VnTextProSingleton getInstance() throws Exception {
		if (instance == null) {
			instance = new VnTextProSingleton();
		}
		return instance;
	}

	public List<List<String>> segment(String text) {
		List<List<String>> segmentedSents = new ArrayList<List<String>>();

		List<String> sents = sentSegmenter.segment(text);
		for (int i = 0; i < sents.size(); i++) {
			segmentedSents.add(wordSegmenter.segment(StrUtil.tokenizeStr(VnTokenizer.tokenize((String) sents.get(i)))));
		}
		return segmentedSents;
	}
}
