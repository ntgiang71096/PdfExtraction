package config;

public class Config {
	public static String BASE_PATH = System.getProperty("user.dir");

	public static String ABSOLUTE_MODEL_PATH = "file://" + BASE_PATH + "/src/main/resources/models/nlp/vn";
	
	public static String VNSENTSEGMENTER_MODEL_PATH = ABSOLUTE_MODEL_PATH + "/vnsentsegmenter";
	
	public static String VNWORDSEGMENTER_MODEL_PATH = ABSOLUTE_MODEL_PATH + "/vnwordsegmenter/model-f1-98.86";

	public static String RESOURCES_DIR = BASE_PATH + "/src/main/resources/dictionaries/";

	public static String DATA_SCIENTIFIC_PATH = "/home/zeta/projects/spminer/test";
}