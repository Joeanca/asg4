import java.util.*;
/**
 * This is the word class that implements the comparable and overrides the equals
 * 
 * @author (Jorge Castano) 
 * @version (February 5 2017)
 */
public class Word implements Comparable<Word>//sort was working with comparable
{
    int count = 1;
    int length = 0;
    String word;

    public Word(String word)
    {
        this.word = word;
        this.length = word.length();
    }

    public void setCounter(){count++;}

    public int getCounter(){return count;}

    public String getWord(){return word;}

    public int compareTo(Word other){
        return this.getWord().compareTo(other.getWord());  
    } 
    
    public int compareToLength(Word other){
    	int toReturn = 0;
    	if (this.length<other.length)
    		toReturn = -1;
    	else if (this.length>other.length)
    		toReturn = 1;
    	else if (this.length==other.length)
    		toReturn = this.compareTo(other);
    	return toReturn;
    }
    
    public int compareToCount(Word other){
    	int toReturn = 0;
    	if (this.count<other.count)
    		toReturn = -1;
    	else if (this.count>other.count)
    		toReturn = 1;
    	else if (this.count==other.count)
    		toReturn = this.compareTo(other);
    	return toReturn;
    }
    
   
    	

    public boolean equals(Object obj)
    {
        Word other = (Word) obj;
        if (word.equals(other.word)) return true;
        else return false;
    }

    public String toString()
    {
    	return getWord()+" : "+ getLength() +" : "+ getCounter();
    }

	private int getLength() {
		return length;
	}
}

