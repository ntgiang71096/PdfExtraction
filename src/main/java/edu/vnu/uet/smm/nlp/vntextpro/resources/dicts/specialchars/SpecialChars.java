package edu.vnu.uet.smm.nlp.vntextpro.resources.dicts.specialchars;

import edu.vnu.uet.smm.nlp.vntextpro.util.Parameters;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 *
 * @author hieupx
 */
public class SpecialChars {
    private static Set<Character> scharSet = null;
    private static Map<Character, String> schar2nameMap = null;

    static {
        scharSet = new HashSet();
        schar2nameMap = new HashMap();
        
        readSpecialChars(Parameters.getSpecialcharsFile());
    }
    
    private static void readSpecialChars(String scharXMLFile) {
        try {
            URL xmlFile = new URL(scharXMLFile);
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = (Document)dBuilder.parse(xmlFile.openStream());
            doc.getDocumentElement().normalize();
            
            NodeList scharNodeList = doc.getElementsByTagName("schar");
            
            for (int i = 0; i < scharNodeList.getLength(); i++) {
                Node scharNode = scharNodeList.item(i);
                Element scharElement = (Element)scharNode;
                
                String scharName = scharElement.getAttribute("name");
                Character scharValue = ' ';
                
                Node scharTextNode = scharNode.getFirstChild();
                if (scharTextNode != null) {
                    String tempStr = scharTextNode.getNodeValue();
                    if (tempStr.length() > 0) {
                        scharValue = tempStr.charAt(0);
                    }
                }
                
                if (scharValue != ' ') {
                    if (scharSet.contains(scharValue)) {
                        System.out.println(scharValue + " : " + scharName);
                    } else {
                        scharSet.add(scharValue);
                    }
                    schar2nameMap.put(scharValue, scharName);
                }
            }
            
        } catch (ParserConfigurationException ex) {
            System.err.println(ex.toString());
            System.exit(1);
        } catch (SAXException ex) {
            System.err.println(ex.toString());
            System.exit(1);
        } catch (IOException ex) {
            System.err.println(ex.toString());
            System.exit(1);
        }
    }
    
    public static boolean containsChar(Character ch) {
        return scharSet.contains(ch);
    }
    
    public static boolean checkSpecialCharIn(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (scharSet.contains(str.charAt(i))) {
                return true;
            }
        }
        
        return false;
    }
    
    public static void print() {
        Iterator it = scharSet.iterator();
        while (it.hasNext()) {
            Character scharValue = (Character)it.next();
            String scharName = (String)schar2nameMap.get(scharValue);
            System.out.println("Schar: " + scharValue + ", ScharName: " + scharName);
        }
    }
}
