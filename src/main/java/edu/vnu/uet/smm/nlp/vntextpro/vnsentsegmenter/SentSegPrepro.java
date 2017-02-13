package edu.vnu.uet.smm.nlp.vntextpro.vnsentsegmenter;

/**
 *
 * @author hieupx
 */
public class SentSegPrepro {
	public static String preprocessText(String text) {
		text = text.replaceAll("\\.{4,}", "...");
		text = text.replaceAll("…{2,}", "…");
		text = text.replaceAll("\\!{2,}", "!");
		text = text.replaceAll("\\?{2,}", "?");

		return text;
	}
}
