package edu.vnu.uet.smm.nlp.vntextpro;

import edu.vnu.uet.smm.nlp.vntextpro.vnsentsegmenter.VnSentSegmenter;
import edu.vnu.uet.smm.nlp.vntextpro.vntokenizer.VnTokenizer;
import edu.vnu.uet.smm.nlp.vntextpro.vnwordsegmenter.VnWordSegmenter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hieupx
 */
public class VnTextPro {
	private VnSentSegmenter sentSegmenter = null;
	private VnWordSegmenter wordSegmenter = null;

	private boolean doSentSegmentation = false;
	private boolean doTokenization = false;
	private boolean doWordSegmentation = false;

	public VnTextPro(boolean doSentSeg, boolean doTokenize, boolean doWordSeg) {
		doSentSegmentation = doSentSeg;
		doTokenization = doTokenize;
		doWordSegmentation = doWordSeg;

		if (doWordSegmentation) {
			doSentSegmentation = true;
			doTokenization = true;
		}

		if (doTokenization) {
			doSentSegmentation = true;
		}

		if (doSentSegmentation) {
			sentSegmenter = new VnSentSegmenter();
		} else {
			sentSegmenter = null;
		}

		if (doWordSegmentation) {
			wordSegmenter = new VnWordSegmenter();
		} else {
			wordSegmenter = null;
		}
	}

	public void init() {
		if (sentSegmenter != null) {
			System.out.println("\nInitialize sentence segmentation model ...");
			sentSegmenter.init();
			System.out.println("Sentence segmentation model initialization completed!");
		}

		if (wordSegmenter != null) {
			System.out.println("\nInitialize word segmentation model ...");
			wordSegmenter.init();
			System.out.println("Word segmentation model initialization completed!\n");
		}
	}

	public synchronized List<String> segmentSent(String text) {
		return sentSegmenter.segment(text);
	}

	public synchronized List<String> tokenizeText(String text) {
		List<String> results = new ArrayList<String>();

		List<String> sents = sentSegmenter.segment(text);
		int len = sents.size();
		for (int i = 0; i < len; i++) {
			results.add(VnTokenizer.tokenize((String) sents.get(i)));
		}

		return results;
	}

	public synchronized List<String> segmentText(String text) {
		List<String> results = new ArrayList<String>();

		List<String> sents = sentSegmenter.segment(text);
		int len = sents.size();
		for (int i = 0; i < len; i++) {
			String sent = (String) sents.get(i);
			results.add(wordSegmenter.segment(VnTokenizer.tokenize(sent)));
		}

		return results;
	}
}
