import java.util.*;
import java.io.*;

public class WebPageIndex {

	String url;
	int wordCount;
	RBTreeMap<String, ArrayList<Integer>> wordMap;

	// WebPageIndex constructor
	WebPageIndex(String url) throws IOException {
		wordMap = new RBTreeMap<String, ArrayList<Integer>>();
		wordCount = 0;
		this.url = url;
		HTMLScanner sc = new HTMLScanner(url);
		for (; sc.hasNext(); wordCount++) {
			String word = sc.next().toLowerCase();
			ArrayList<Integer> locations = wordMap.get(word);
			if (locations == null) {
				locations = new ArrayList<Integer>();
				wordMap.put(word, locations);
			}
			locations.add(wordCount);
		}
	}

	// Returns a count of the words in this web page
	public int getWordCount() {
		return wordCount;
	}

	public String getUrl() {
		return url;
	}

	public boolean contains(String s) {
		return (wordMap.get(s.toLowerCase()) != null);
	}

	public int getCount(String s) {
		return getLocations(s).size();
	}

	public double getFrequency(String s) {
		return (double) getCount(s) / (double) wordCount;
	}

	public List<Integer> getLocations(String s) {
		ArrayList<Integer> locations = wordMap.get(s.toLowerCase());
		if (locations == null) {
			locations = new ArrayList<Integer>();
		}
		return locations;
	}

	// return an Iterator over all the words in the index
	public Iterator<String> words() {
		return wordMap.keys();
	}

	public String toString() {
		return wordMap.toString();
	}

	// The main method is an application using a WebPageIndex
	public static void main(String[] args) throws IOException {
		WebPageIndex wpi = null;
		try {
			wpi = new WebPageIndex(args[0]);
		} catch (IOException e) {
			System.out.println("the resource at: " + args[0]
			+ " could not be located or does not exist.");
			System.exit(1);
		}
		System.out.println("Frequency and index of words in " + wpi.getUrl());
		Iterator<String> words = wpi.words();
		while(words.hasNext()) {
			String word = words.next();
			List<Integer> locations = wpi.getLocations(word);
			System.out.printf("%25s %7.5f ", word, locations.size() / wpi.wordCount);
			System.out.println(locations);
		}
		System.out.println();
		System.out.println("Height: " + wpi.wordMap.getHeight());
	}

	/*
	 * additional features to support multi-word phrases work on these after you
	 * have the previous methods working correctly
	 */

	public boolean containsPhrase(String s) {
		return getPhraseLocations(s, 1).size() > 0;
	}

	public int getPhraseCount(String s) {
		return getPhraseLocations(s).size();
	}

	public double getPhraseFrequency(String s) {
		return (double) getPhraseCount(s) / (double) wordCount;
	}

	public List<Integer> getPhraseLocations(String s) {
		return getPhraseLocations(s, -1);
	}

	// returns num locations of the phrase. If num is -1 it will return
	// as many locations as exists, and if there is not num locations in
	// which the phrase exists, it returns as many as there are.
	private List<Integer> getPhraseLocations(String s, int num) {
		ArrayList<Integer> locations = new ArrayList<Integer>();
		String[] phrase = s.split(" ");
		List<Integer> potentials = getLocations(phrase[0]);
		int numFound = 0;
		for (int potential : potentials) {
			if (numFound >= num && num != 0)
				break;
			if (pathExists(potential, phrase)) {
				numFound++;
				locations.add(potential);
			}
		}
		return locations;
	}

	private boolean pathExists(int potential, String[] phrase) {
		boolean pathFound = false;
		for (int i = 0; i < phrase.length - 1; i++) {
			pathFound = false;
			List<Integer> nextMoves = getLocations(phrase[i + 1]);
			for (int move : nextMoves) {
				if (move - potential == i) {
					pathFound = true;
				}
			}
			if (pathFound == false) {
				break;
			}
		}
		return pathFound;
	}

}
