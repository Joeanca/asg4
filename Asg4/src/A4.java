
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

	private int totalCounter, stopCounter,wordLength;

	Scanner inp;
	ArrayList<Word> nodes;
	BST<Word> wordList = new BST<Word>();
	BST<Word> lengthList = new BST<Word>();
	BST<Word> countList = new BST<Word>();
	Queue<Word> q = new LinkedList<Word>();
	Stack<Word> s = new Stack<Word>();
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

			FileInputStream file = new FileInputStream("inp5.txt");
			// FileReader file = new FileReader("inp3.txt");
			// inp = new Scanner(System.in, "UTF-8");
			inp = new Scanner(file, "UTF-8");
			String word = "";
			while (inp.hasNext()) {
				word = inp.next().toLowerCase().trim().replaceAll("[^a-z]", "");
				if (!word.equals("")) {
					totalCounter++;
					Word w = new Word(word);
					if (wordList.find(w) != null)
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
		for (int i = 0; i < stopWords.length; i++) {
			Word x = new Word(stopWords[i]);
			if (wordList.find(x) != null)
				stopCounter++;
			wordList.delete(x);
		}

		buildLenghtList();
	}

	private void buildLenghtList() {
		// pull from the wordList BST and organize according to length
		nodes = wordList.get();
		int totalLength = 0;
		for (Word x : nodes) {
			totalLength+=x.length;
			lengthList.add(x, x.LENGTH_ORDER);
		}
		wordLength = totalLength/lengthList.size();
		buildCountList();

	}

	private void buildCountList() {
		
		for (Word x : nodes) {
			if (x.count>2){
			countList.add(x, x.DECENDING_ORDER);			
			}
		}
		
		printBST();
	}

	private void printBST() {
		
		Iterator<Word> myIterator = countList.iterator();
		while (myIterator.hasNext()){
			Word x = myIterator.next();
			q.add(x);
			s.push(x);
		}
		
		Iterator<Word> lengthIterator = lengthList.iterator();

		
		System.out.println("------\n");
		System.out.println("Total Words: " + totalCounter + "\nStop Words: " + stopCounter + "\nUnique Words: "
				+ wordList.size() + "\n");
		System.out.println("------\n");
		System.out.println("20 Most Frequent\n");
		int counter = 0;
		while (counter < 20 && !s.isEmpty()) {
			System.out.println(q.remove().toString());
			counter++;
			
		};
		System.out.println("\n------\n\n20 Least Frequent");
		counter = 0;
		while (counter < 20 && !q.isEmpty()) {
			System.out.println(s.pop().toString());
			counter++;		
		};
		System.out.println("\n------\n\nThe longest word is " + lengthIterator.next().toString());
		System.out.println("The average word length " + wordLength);
		System.out.println("\n------\n\nAll Words");
		wordList.inOrder();
		
		System.out.println("\n------\n\nAlphabetic Tree: ( Optimum Height: 7) ( Actual Height: " + wordList.height() + ")");
		System.out.println("Frequency Tree: ( Optimum Height: 4) ( Actual Height: " + countList.height() + ")");
		System.out.println("Length Tree: ( Optimum Height: 7) ( Actual Height: "+ lengthList.height() + ")\n\n------\n\n");



	}
}
