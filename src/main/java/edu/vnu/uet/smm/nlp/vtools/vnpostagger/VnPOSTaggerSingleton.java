package edu.vnu.uet.smm.nlp.vtools.vnpostagger;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.postag.POSTaggerME;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class VnPOSTaggerSingleton {
	private static VnPOSTaggerSingleton instance = null;
	private static URL model = VnPOSTaggerSingleton.class.getClassLoader()
			.getResource("models/nlp/vn/vnpostagger/vi-pos.model");
	private static POSTagger tagger;

	public VnPOSTaggerSingleton() throws Exception {
		InputStream in = model.openStream();
		POSModel posModel = new POSModel(in);
		tagger = new POSTaggerME(posModel);
	}

	public static VnPOSTaggerSingleton getInstance() throws Exception {
		if (instance == null) {
			instance = new VnPOSTaggerSingleton();
		}
		return instance;
	}

	public String[] tag(String[] text) throws Exception {
		return tagger.tag(text);
	}

	public List<String[]> tag(List<List<String>> tokenizedSents) throws Exception {
		List<String[]> taggedSents = new ArrayList<String[]>();
		for (List<String> sents : tokenizedSents) {
			taggedSents.add(tagger.tag(sents.toArray(new String[sents.size()])));
		}
		return taggedSents;
	}

	public String tagToString(List<List<String>> tokenizedSents) throws Exception {
		String tagged = "";

		for (List<String> sents : tokenizedSents) {
			String[] tags = tagger.tag(sents.toArray(new String[sents.size()]));
			for (int i = 0; i < tags.length; i++)
				tagged += sents.get(i) + "/" + tags[i] + " ";
			tagged += "\n";
		}
		return tagged;
	}

	public List<String> getKeywords(String[] text) throws Exception {
		List<String> keywords = new ArrayList<String>();
		String[] tags = tagger.tag(text);
		for (int i = 0; i < tags.length; i++)
			if (tags[i].startsWith("Np") || tags[i].startsWith("V"))
				keywords.add(text[i]);
		return keywords;
	}
}
