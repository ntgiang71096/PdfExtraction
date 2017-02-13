package edu.vnu.uet.smm.nlp.vntextpro.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pxhieu
 */
public class Parameters {
	private static String rootDir = null;
	private static URL propertiesFile = null;
	private static Properties properties = null;
	private static Map<String, String> propertiesMap = null;
	private static ClassLoader classLoader;

	static {
		classLoader = Parameters.class.getClassLoader();
		properties = new Properties();
		rootDir = Path.getRootDir();
		
		propertiesFile = classLoader.getResource("models/vietnamesetextpro.properties");
		// propertiesFile = Path.getPropertiesFile();
		// propertiesFile = "../models/vietnamesetextpro.properties";

		loadProperties();
		processAndStoreProperties();
	}

	private static void loadProperties() {
		InputStream proInputStream;
		try {
			proInputStream = propertiesFile.openStream();
			properties.load(proInputStream);
			proInputStream.close();

		} catch (IOException ex) {
			Logger.getLogger(Parameters.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private static void processAndStoreProperties() {
		propertiesMap = new HashMap<String, String>();

		for (Map.Entry<Object, Object> proItem : properties.entrySet()) {
			String key = (String) proItem.getKey();
			String value = (String) proItem.getValue();			

			propertiesMap.put(key, value);
		}
	}

	public static String getNlpVnSentSegmenterModelDir() {
		URL dir = classLoader.getResource(propertiesMap.get("nlpVnSentSegmenterModelDir"));
		return removeEndingSlash(dir.toString());
	}

	public static String getNlpVnTokenizerModelDir() {
		URL dir = classLoader.getResource(propertiesMap.get("nlpVnTokenizerModelDir"));
		return removeEndingSlash(dir.toString());
	}

	public static String getNlpVnWordSegmenterModelDir() {
		URL dir = classLoader.getResource(propertiesMap.get("nlpVnWordSegmenterModelDir"));
		return removeEndingSlash(dir.toString());
	}

	public static String getRegexDir() {
		URL dir = classLoader.getResource(propertiesMap.get("regexDir"));
		return removeEndingSlash(dir.toString());
	}

	public static String getNamesDictDir() {
		URL dir = classLoader.getResource(propertiesMap.get("namesDictDir"));
		return removeEndingSlash(dir.toString());
	}

	public static String getLexiconsDictDir() {
		URL dir = classLoader.getResource(propertiesMap.get("lexiconsDictDir"));
		return removeEndingSlash(dir.toString());
	}

	public static String getSpecialcharsDictDir() {
		URL dir = classLoader.getResource(propertiesMap.get("specialCharsDictDir"));
		return removeEndingSlash(dir.toString());
	}

	public static String getAbbreviationsDir() {
		URL dir = classLoader.getResource(propertiesMap.get("abbreviationsDir"));
		return removeEndingSlash(dir.toString());
	}	

	public static String getVnDictDir() {
		URL dir = classLoader.getResource(propertiesMap.get("vnDictDir"));
		return removeEndingSlash(dir.toString());
	}

	public static String getHumanTopicMapDir() {
		URL dir = classLoader.getResource(propertiesMap.get("humanTopicMapDir"));
		return removeEndingSlash(dir.toString());
	}

	public static String getConceptsDir() {
		URL dir = classLoader.getResource(propertiesMap.get("conceptsDir"));
		return removeEndingSlash(dir.toString());
	}

	public static String getConceptsFilesDir() {
		URL dir = classLoader.getResource(propertiesMap.get("conceptsFilesDir"));
		return removeEndingSlash(dir.toString());
	}

	public static String getRegexFile() {
		String file = (String) propertiesMap.get("regexFile");
		if (file != null) {
			return getRegexDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getRegexPunctuationMarksFile() {
		String file = (String) propertiesMap.get("regexPunctuationMarksFile");
		if (file != null) {
			return getRegexDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getNamesPersonsFile() {
		String file = (String) propertiesMap.get("namesPersonsFile");
		if (file != null) {
			return getNamesDictDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getNamesLocationsFile() {
		String file = (String) propertiesMap.get("namesLocationsFile");
		if (file != null) {
			return getNamesDictDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getNamesOrganizationsFile() {
		String file = (String) propertiesMap.get("namesOrganizationsFile");
		if (file != null) {
			return getNamesDictDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getNamesProductsFile() {
		String file = (String) propertiesMap.get("namesProductsFile");
		if (file != null) {
			return getNamesDictDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getLexiconsVnUnitsFile() {
		String file = (String) propertiesMap.get("lexiconsVnUnitsFile");
		if (file != null) {
			return getLexiconsDictDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getLexiconsEnUnitsFile() {
		String file = (String) propertiesMap.get("lexiconsEnUnitsFile");
		if (file != null) {
			return getLexiconsDictDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getStopWordsFile() {
		String file = (String) propertiesMap.get("stopWordsFile");
		if (file != null) {
			return getLexiconsDictDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getStopWordsTextClassificationFile() {
		String file = (String) propertiesMap.get("stopWordsTextClassificationFile");
		if (file != null) {
			return getLexiconsDictDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getStopNamesFile() {
		String file = (String) propertiesMap.get("stopNamesFile");
		if (file != null) {
			return getLexiconsDictDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getSpecialcharsFile() {
		String file = (String) propertiesMap.get("specialCharsFile");
		if (file != null) {
			return getSpecialcharsDictDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getNonAlphaCharsFile() {
		String file = (String) propertiesMap.get("nonAlphaCharsFile");
		if (file != null) {
			return getSpecialcharsDictDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getVnStopAbbrsFile() {
		String file = (String) propertiesMap.get("vnStopAbbrsFile");
		if (file != null) {
			return getAbbreviationsDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getEnStopAbbrsFile() {
		String file = (String) propertiesMap.get("enStopAbbrsFile");
		if (file != null) {
			return getAbbreviationsDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getHonotificTitlesFile() {
		String file = (String) propertiesMap.get("honotificTitlesFile");
		if (file != null) {
			return getAbbreviationsDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getPrefixTitlesFile() {
		String file = (String) propertiesMap.get("prefixTitlesFile");
		if (file != null) {
			return getAbbreviationsDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getVnDictFile() {
		String file = (String) propertiesMap.get("vnDictFile");
		if (file != null) {
			return getVnDictDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getVnExtendedDictFile() {
		String file = (String) propertiesMap.get("vnExtendedDictFile");
		if (file != null) {
			return getVnDictDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getVnIdiomsDictFile() {
		String file = (String) propertiesMap.get("vnIdiomsDictFile");
		if (file != null) {
			return getVnDictDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getVnFirstWordsDictFile() {
		String file = (String) propertiesMap.get("vnFirstWordsDictFile");
		if (file != null) {
			return getVnDictDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getVnFamilyNamesDictFile() {
		String file = (String) propertiesMap.get("vnFamilyNamesDictFile");
		if (file != null) {
			return getVnDictDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getVnMiddleLastNamesDictFile() {
		String file = (String) propertiesMap.get("vnMiddleLastNamesDictFile");
		if (file != null) {
			return getVnDictDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getVnLocationsDictFile() {
		String file = (String) propertiesMap.get("vnLocationsDictFile");
		if (file != null) {
			return getVnDictDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getVnBaomoiWordListFile() {
		String file = (String) propertiesMap.get("vnBaomoiWordListFile");
		if (file != null) {
			return getVnDictDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getVnBaomoiNameListFile() {
		String file = (String) propertiesMap.get("vnBaomoiNameListFile");
		if (file != null) {
			return getVnDictDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getVnBaomoiNameListForConceptLookupFile() {
		String file = (String) propertiesMap.get("vnBaomoiNameListForConceptLookupFile");
		if (file != null) {
			return getVnDictDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getHumanTopicMapFile() {
		String file = (String) propertiesMap.get("humanTopicMapFile");
		if (file != null) {
			return getHumanTopicMapDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String getMappedTopicsFile() {
		String file = (String) propertiesMap.get("mappedTopicsFile");
		if (file != null) {
			return getHumanTopicMapDir() + "/" + file;
		} else {
			return "";
		}
	}

	public static String removeEndingSlash(String path) {
		if (path.endsWith("/")) {
			return path.substring(0, path.length() - 1);
		}
		return path;
	}
}
