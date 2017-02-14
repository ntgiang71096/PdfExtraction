package classification.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class BagOfWords {
    private Map<String, Integer> bag;
    private int bagSize;

    public BagOfWords() {
        bag = new HashMap<String, Integer>();
        bagSize = 0;
    }

    public void addWords(String[] words) {
        for (String word : words) {
            if (!bag.containsKey(word)) {
                bagSize++;
                bag.put(word, bagSize);
            }
        }
    }

    public Map<Integer, Integer> featurize(String[] words) {
        Map<Integer, Integer> result = new TreeMap<Integer, Integer>();

        for (String word : words) {
            if (bag.containsKey(word)) {
                int indexOfFeature = bag.get(word);
                if (!result.containsKey(indexOfFeature)) {
                    result.put(indexOfFeature, 1);
                } else {
                    int currentCount = result.get(indexOfFeature);
                    result.put(indexOfFeature, currentCount + 1);
                }
            }
        }

        return result;
    }

    public Map<String, Integer> getBag() {
        return bag;
    }

    public int getBagSize() {
        return bagSize;
    }
}
