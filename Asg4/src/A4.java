
import java.io.*;
import java.util.*;

/**
 * This is a file reader that accounts for the amount of words, removes stop
 * words predefined in an array and accounts for repeated words by keeping a
 * counter in the word class, it also accounts for its lenght.
 * 
 * @author (Jorge Castano)
 * @version (Mar 25 2017)
 */
public class A4 {

	private int totalCounter, stopCounter, wordLength, totalLength, countCounter, heightCounter;

	private Scanner inp;
	private ArrayList<Word> nodes;
	private BST<Word> wordList = new BST<Word>();
	private BST<Word> lengthList = new BST<Word>();
	private BST<Word> countList = new BST<Word>();
	private Queue<Word> q = new LinkedList<Word>();
	private Stack<Word> s = new Stack<Word>();
	private String[] stopWords = { "a", "about", "all", "am", "an", "and", "any", "are", "as", "at", "be", "been",
			"but", "by", "can", "cannot", "could", "did", "do", "does", "else", "for", "from", "get", "got", "had",
			"has", "have", "he", "her", "hers", "him", "his", "how", "i", "if", "in", "into", "is", "it", "its", "like",
			"more", "me", "my", "no", "now", "not", "of", "on", "one", "or", "our", "out", "said", "say", "says", "she",
			"so", "some", "than", "that", "thats", "the", "their", "them", "then", "there", "these", "they", "this",
			"to", "too", "us", "upon", "was", "we", "were", "what", "with", "when", "where", "which", "while", "who",
			"whom", "why", "will", "you", "your", "up", "down", "left", "right", "man", "woman", "would", "should",
			"dont", "after", "before", "im", "men" };

	/**
	 * Starts here calls run to trigger multiple methods
	 */
	public static void main(String[] args) {
		A4 a4 = new A4();
		a4.run();
	}

	/**
	 * builds the alphabetical tree by reading the file provided by the user
	 * then it calls the delete method for the stopwords to be deleted
	 */
	private void run() {
		// Read the words from the file and create a BST ordered by the
		// natural(alphabetical) ordering of the words
		try {

			//FileInputStream file = new FileInputStream("inp2.txt");
			// FileReader file = new FileReader("inp3.txt");
			inp = new Scanner(System.in, "UTF-8");
			//inp = new Scanner(file, "UTF-8");
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
			inp.close();
			deleteStopWords();

		} catch (Exception e) {
			System.out.println(e);// "Error opening file"
		}
	}

	/**
	 * This method is called by the run and deletes the stopwords from the alphabetical list
	 * then calls on building the length list
	 */
	private void deleteStopWords() {
		for (int i = 0; i < stopWords.length; i++) {
			Word x = new Word(stopWords[i]);
			if (wordList.find(x) != null)
				stopCounter++;
			wordList.delete(x);
		}

		buildLenghtList();
	}

	/**
	 * This method is called by the deleteStopWords method and builds the lengthlist
	 */
	private void buildLenghtList() {
		// pull from the wordList BST and organize according to length
		nodes = wordList.get();
		totalLength = 0;
		for (Word x : nodes) {
			totalLength += x.length;
			lengthList.add(x, x.LENGTH_ORDER);
		}
		wordLength = totalLength / lengthList.size();
		buildCountList();
	}

	/**
	 * This method builds the count list which only takes in words 
	 * which have a frequency of more than 2, it fills a stack and a queue 
	 */
	private void buildCountList() {

		for (Word x : nodes) {
			if (x.count > 2) {
				countCounter++;
				countList.add(x, x.DECENDING_ORDER);
			}
		}
		Iterator<Word> myIterator = countList.iterator();
		while (myIterator.hasNext()) {
			Word x = myIterator.next();
			q.add(x);
			s.push(x);
		}
		printBST();
	}

	/**
	 * This method calculates the ideal height of the tree which is passed in by the final call to print
	 * it sets a counter to 0 and calls the calculateHeight  
	 */
	private int countHeight(int toCount) {
		heightCounter = 0;
		calculateHeight(toCount);
		return heightCounter+1;
	}
	
	/**
	 * This method calculates the ideal height of the tree which is passed in by the final call to print
	 */	
	private int calculateHeight(int remainder) {
		while (remainder>2){
			remainder=remainder/2;
			heightCounter++;
		}
		if (remainder==3)
			heightCounter++;

		return heightCounter;
	}

	/*private int calculateHeight(int remainder) {
		while (remainder>=1) {
			remainder = calculateHeight(remainder / 2);
			if (remainder==1 || remainder>=0)
				heightCounter++;
		}
		return remainder;
	}*/

	/**
	 * This is the final method which takes all the previously calculated values and prints
	 * according to the specifications
	 */
	private void printBST() {
		// This builds the iterator for the Lenght BSTTree
		Iterator<Word> lengthIterator = lengthList.iterator();

		// Starts the printing formatting for the output
		System.out.println("------\n");
		System.out.println("Total Words: " + totalCounter + "\nStop Words: " + stopCounter + "\nUnique Words: "
				+ wordList.size() + "\n");
		System.out.println("------\n");

		// 20 most frequest printout
		System.out.println("20 Most Frequent\n");
		int counter = 0;
		while (counter < 20 && !s.isEmpty()) {
			System.out.println(q.remove().toString());
			counter++;
		}
		// 20 Most recent printout
		System.out.println("\n------\n\n20 Least Frequent");
		counter = 0;
		while (counter < 20 && !q.isEmpty()) {
			System.out.println(s.pop().toString());
			counter++;
		}
		// longest word pulled from the length iterator
		System.out.println("\n------\n\nThe longest word is " + lengthIterator.next().toString());
		System.out.println("The average word length " + wordLength);
		System.out.println("\n------\n\nAll Words");
		wordList.inOrder();

		System.out.println("\n------\n\nAlphabetic Tree: ( Optimum Height: " + countHeight(wordList.size())
				+ ") ( Actual Height: " + wordList.height() + ")");
		System.out.println("Frequency Tree: ( Optimum Height: " + countHeight(countList.size()) + ") ( Actual Height: "
				+ countList.height() + ")");
		System.out.println("Length Tree: ( Optimum Height: " + countHeight(lengthList.size()) + ") ( Actual Height: "
				+ lengthList.height() + ")\n\n------\n\n");

	}
}
