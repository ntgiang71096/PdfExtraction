package edu.vnu.uet.smm.nlp.vntextpro.resources.dicts.vndicts;

import edu.vnu.uet.smm.nlp.vntextpro.util.Parameters;
import edu.vnu.uet.smm.nlp.vntextpro.util.StrUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author hieupx
 */
public class VnDict {
	private static Set<String> dictSet = null;

	/*
	 * initialization
	 */
	static {
		dictSet = new HashSet<String>();
		loadDict(Parameters.getVnDictFile());
	}

	private static void loadDict(String dictFile) {
		BufferedReader fin;
		try {
			fin = new BufferedReader(new InputStreamReader(new URL(dictFile).openStream(), "UTF-8"));

			String word;
			while ((word = fin.readLine()) != null) {
				word = StrUtil.normalizeStr(word);
				if (word.length() > 0) {
					dictSet.add(word);
				}
			}

			fin.close();

		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static boolean contains(String word) {
		boolean res = dictSet.contains(word);

		if (!res) {
			String loweredWord = word.toLowerCase();
			res = dictSet.contains(loweredWord);
		}

		return res;
	}
}
