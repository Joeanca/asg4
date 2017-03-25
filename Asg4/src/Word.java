import java.util.*;

/**
 * This is the word class that implements the comparable and overrides the
 * equals
 * 
 * @author (Jorge Castano)
 * @version (February 5 2017)
 */
public class Word implements Comparable<Word>// sort was working with comparable
{
	int count = 1;
	int length = 0;
	String word;

	public Word(String word) {
		this.word = word;
		this.length = word.length();
	}

	public void setCounter() {
		count++;
	}

	public int getCounter() {
		return count;
	}

	public String getWord() {
		return word;
	}

	public int compareTo(Word other) {
		return this.getWord().compareTo(other.getWord());
	}

	public  final Comparator<Word> LENGTH_ORDER = new Comparator<Word>() {
		@Override
		public int compare(Word o1, Word o2) {
			int toReturn = 0;
			if (o2.length < o1.length)
				toReturn = -1;
			else if (o2.length > o1.length)
				toReturn = 1;
			else if (o2.length == o1.length)
				toReturn = o1.compareTo(o2);
			return toReturn;
		}
	};

	public  final Comparator<Word> DECENDING_ORDER = new Comparator<Word>() {
		@Override
		public int compare(Word o1, Word o2) {
			int toReturn = 0;
			if (o2.count < o1.count)
				toReturn = -1;
			else if (o2.count > o1.count)
				toReturn = 1;
			else if (o2.count == o1.count)
				toReturn = o1.compareTo(o2);
			return toReturn;
		}
	};

	public boolean equals(Object obj) {
		Word other = (Word) obj;
		if (word.equals(other.word))
			return true;
		else
			return false;
	}

	public String toString() {
		return getWord() + " : " + getLength() + " : " + getCounter();
	}

	private int getLength() {
		return length;
	}
}
