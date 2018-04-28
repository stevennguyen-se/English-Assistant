package com.example.thesis.handler;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.thesis.object.Word;

public class StringHandler {
	public static String getFirstDefinition(String str) {
		if (str != null && str.contains("[") && str.contains("]")) {
			return str.substring(str.indexOf("[") + 1, str.indexOf("]"));
		}
		return str;
	}
	
	public static String getAllDefinition(String str) {
		if (str != null && str.contains("[") && str.contains("]")) {
			str = str.replace("[", "+   ");
			str = str.replace("]", "." + "\n");
		}
		return str;
	}
	
	// fall time in milliseconds
	public static int getTimeForWord(Word word, int numberOfParachutist, int fallTime) {
		if (word == null || numberOfParachutist == 0) {
			return 0;
		} else {
			fallTime /= 1000;
		}
		
		int mod = word.getWord().length() % numberOfParachutist;
		if (mod == 0) {
			return (word.getWord().length() / numberOfParachutist) * fallTime; 
		} else {
			return ((word.getWord().length() / numberOfParachutist) + 1) * fallTime;
		}
	}
	
	public static int getTimeForSelectedWords(List<Word> selectedWords, int numberOfParachutist, int fallTime) {
		if (selectedWords == null) {
			return 0;
		}
		
		int totalTime = 0;
		for (int i = 0; i < selectedWords.size(); ++i) {
			totalTime += getTimeForWord(selectedWords.get(i), numberOfParachutist, fallTime);
		}
		return totalTime;
	}
	
	// Example
	public static List<String> cutExample(Elements examples) {
		if (examples == null || examples.isEmpty()) {
			return null;
		}
		
		String mExample = null;
		List<String> mExamples = new ArrayList<String>();
		
		for (int i = 0; i < examples.size(); ++i) {
			Element example = examples.get(i);
			mExample = "+ " + example.text().toString();
			mExamples.add(mExample);
		}
		return mExamples;
	}
	
}
