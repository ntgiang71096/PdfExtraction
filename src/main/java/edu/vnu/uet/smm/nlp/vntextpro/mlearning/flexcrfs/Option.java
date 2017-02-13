/*
    Copyright (C) 2006, Xuan-Hieu Phan
    
    Email:	hieuxuan@ecei.tohoku.ac.jp
		pxhieu@gmail.com
    URL:	http://www.hori.ecei.tohoku.ac.jp/~hieuxuan
    
    Graduate School of Information Sciences,
    Tohoku University
*/

package edu.vnu.uet.smm.nlp.vntextpro.mlearning.flexcrfs;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;

public class Option {

	static public final int FIRST_ORDER = 1;
	// second-order Markov is not supported currently
	static public final int SECOND_ORDER = 2;

	public static final String inputSeparator = "/";
	public static final String outputSeparator = "/";

	// model directory, default is current dir
	public String modelDir = ".";
	// model file (mapping, dictionary, and features)
	public final String modelFile = "model.txt";
	// option file
	public final String optionFile = "option.txt";

	public int order = FIRST_ORDER; // 1: first-order Markov; 2: second-order
									// Markov

	public Option() {
	}

	public Option(String modelDir) {
		if (modelDir.endsWith(File.separator)) {
			this.modelDir = modelDir.substring(0, modelDir.length() - 1);
		} else {
			this.modelDir = modelDir;
		}
	}

	public boolean readOptions() {
		String filename = modelDir + "/" + optionFile;
		BufferedReader fin;
		String line;

		try {
			fin = new BufferedReader(new InputStreamReader(new URL(filename).openStream()));

			System.out.println("Reading options ...");

			// read option lines
			while ((line = fin.readLine()) != null) {
				String trimLine = line.trim();
				if (trimLine.startsWith("#")) {
					// comment line
					continue;
				}

				StringTokenizer strTok = new StringTokenizer(line, "= \t\r\n");
				int len = strTok.countTokens();
				if (len != 2) {
					// invalid parameter line, ignore it
					continue;
				}

				String strOpt = strTok.nextToken();
				String strVal = strTok.nextToken();

				if (strOpt.compareToIgnoreCase("order") == 0) {
					int numTemp = Integer.parseInt(strVal);
					order = numTemp;
				}
			}

			System.out.println("Reading options completed!");

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public BufferedReader openModelFile() {
		String filename = modelDir + "/" + modelFile;
		BufferedReader fin;

		try {
			fin = new BufferedReader(new InputStreamReader(new URL(filename).openStream()));

		} catch (IOException e) {
			e.printStackTrace();
			fin = null;
		}

		return fin;
	}

} // end of class Option
