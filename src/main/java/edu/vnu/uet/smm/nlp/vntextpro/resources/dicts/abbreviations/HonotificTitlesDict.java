package edu.vnu.uet.smm.nlp.vntextpro.resources.dicts.abbreviations;

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
public class HonotificTitlesDict {
	private static Set<String> dictSet = null;

	static {
		dictSet = new HashSet<String>();
		loadDict(Parameters.getHonotificTitlesFile());
	}

	private static void loadDict(String dictFile) {
		BufferedReader fin;

		try {
			fin = new BufferedReader(new InputStreamReader(new URL(dictFile).openStream(), "UTF-8"));

			String word;
			while ((word = fin.readLine()) != null) {
				word = StrUtil.normalizeStr(word);

				if (word.startsWith("#")) {
					continue;
				}

				if (word.length() >= 2) {
					dictSet.add(word);
				}
			}

			fin.close();

		} catch (UnsupportedEncodingException ex) {
			System.err.println(ex.toString());
			System.exit(1);
		} catch (IOException ex) {
			System.err.println(ex.toString());
			System.exit(1);
		}
	}

	public static boolean contains(String word) {
		return dictSet.contains(word);
	}

	public static void print() {
		Object arrs[] = dictSet.toArray();
		System.out.println("\nHonotific titles dictionary:\n");
		for (int i = 0; i < arrs.length; i++) {
			System.out.println((String) arrs[i]);
		}
	}
}
