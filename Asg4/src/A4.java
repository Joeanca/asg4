
import java.io.*;
import java.util.*;
/**
 * This is a file reader that accounts for the amount of words, removes stop
 * words predefined in an array and accounts for repeated words by keepin a
 * counter in the word class
 * 
 * @author (Jorge Castano)
 * @version (Feb 05 2017)
 */
public class A4 {

	private int totalCounter, stopCounter,avgWordLength;

	Scanner inp;
	ArrayList<Word> nodes;
	BST<Word> wordList = new BST<Word>();
	BST<Word> ascending = new BST<Word>();
	BST<Word> descending = new BST<Word>();
	private String[] stopWords = { "a", "about", "all", "am", "an", "and", "any", "are", "as", "at", "be", "been",
			"but", "by", "can", "cannot", "could", "did", "do", "does", "else", "for", "from", "get", "got", "had",
			"has", "have", "he", "her", "hers", "him", "his", "how", "i", "if", "in", "into", "is", "it", "its", "like",
			"more", "me", "my", "no", "now", "not", "of", "on", "one", "or", "our", "out", "said", "say", "says", "she",
			"so", "some", "than", "that", "thats", "the", "their", "them", "then", "there", "these", "they", "this",
			"to", "too", "us", "upon", "was", "we", "were", "what", "with", "when", "where", "which", "while", "who",
			"whom", "why", "will", "you", "your", "up", "down", "left", "right", "man", "woman", "would", "should",
			"dont", "after", "before", "im", "men" };

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		A4 a4 = new A4();
		a4.run();
	}

	public void run() {
		// Read the words from the file and create a BST ordered by the
		// natural(alphabetical) ordering of the words
		try {
			
			FileInputStream file = new FileInputStream("inp3.txt");
			// FileReader file = new FileReader("inp2.txt");
			// inp = new Scanner(System.in, "UTF-8");
			inp = new Scanner(file, "UTF-8");
			String word = "";
			while (inp.hasNext()) {
				word = inp.next().toLowerCase().trim().replaceAll("[^a-z]", "");
				if (!word.equals("")) {
					totalCounter++;
					Word w = new Word(word);
					if (wordList.find(w)!=null)
						wordList.find(w).setCounter();
					else {
						// add to list
						wordList.add(w);
					}
				}

			}
			file.close();
			deleteStopWords();

		} catch (Exception e) {
			System.out.println(e);// "Error opening file"
		}
	}

	private void deleteStopWords() {
		for (int i=0; i<stopWords.length; i++){
			Word x = new Word(stopWords[i]);
			if (wordList.find(x)!=null)
				stopCounter++;
			 wordList.delete(x);
		}
		buildMostFrequent();
	}
	
	public class ascComparator implements Comparator<Word> {
	    @Override
	    public int compare(Word o1, Word o2) {
	        return o1.compareToCount(o2);
	    }
	}
	
	public class lengthComparator implements Comparator<Word> {
	    @Override
	    public int compare(Word o1, Word o2) {
	        return o1.compareToLength(o2);
	    }
	}
	
	private void buildMostFrequent()
	{
		//pull from the wordList BST and organize according to length
		wordList.inOrder();
		nodes= wordList.get();
		
		printBST();

		//buildLeastFrequent();
		
	}
	
	private void buildLeastFrequent()
	{
		printBST();
	}

	private void printBST() {
		System.out.println("------\n");
		System.out.println("Total Words: " + totalCounter + "\nStop Words: " + stopCounter + "\nUnique Words: " + wordList.size() + "\n");
		System.out.println("------\n");
		System.out.println("20 Most Frequent\n");
		Collections.sort(nodes,new ascComparator());
		for (int i=nodes.size()-1; i >= nodes.size()-20; i--)
			System.out.println(nodes.get(i));
		System.out.println("\n------\n\n20 Least Frequent\n\n");
		Collections.sort(nodes,new lengthComparator());
		for (int i=nodes.size()-1; i >= nodes.size()-20; i--)
			System.out.println(nodes.get(i));
		System.out.println("\n------\n\nThe longest word is " + nodes.get(nodes.size()-1));
		System.out.println("The average word length " + avgWordLength);
		System.out.println("\n------\n\nAll Words");
		wordList.inOrder();
	
	}
}
