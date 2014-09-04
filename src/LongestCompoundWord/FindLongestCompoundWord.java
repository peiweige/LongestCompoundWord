/*
 * Algorithm to Find Longest Word Made of Other Words
 * 
 * Introduction: 
 * 1. The algorithm first scan the file line by line and create a trie by using the words in the file.
 * Before inserting each word into the trie, it will check if the word has any prefixes. If yes, it will create
 * word-suffix pairs and add them into a queue. 
 * 2. Then, the algorithm will pop a pair from the queue to see if the suffix in the word-suffix pair is a word 
 * in the file. If yes, the word is a compound word. Then, it will update the number of compound words, and
 * if it also is longer than the previous compound word, it will update the variables of longestCompoundWord
 * and secondLongestCompoundWord as well. 
 * 3. If the suffix in the word-suffix pair is not a word in the file, it will check if the suffix has any prefixes.
 * If yes, it will create new word-suffix pairs and add them into the queue. Otherwise, it will just discard the pair
 * and pop a new pair from the queue and repeat the process until the queue is empty.
 * 4. The algorithm runs in linear, O(kN),  where N is the number of words in the input file, and k is the maximum 
 * number of words in a compound word.
 */

package LongestCompoundWord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;

public class FindLongestCompoundWord {

	public static void main(String[] args) {
		// Name of input file
		String fileName = "./wordsforproblem.txt";
		// Initialize the root of trie
		TrieNode root = new TrieNode((char) 0);
		// Variable to count the number of compound words
		int numCompoundWord = 0;
		// Longest compound word
		String longestCompoundWord = ""; 
		// Second Longest compound word
		String secondLongestCompoundWord = "";
		
		// Queue to store word-suffix pairs
		LinkedList<WordSuffix> wordSuffixes = new LinkedList<WordSuffix>();
		// List to store all the prefixes of a word
		ArrayList<String> prefixes;
		// variable to store a word-suffix pair
		WordSuffix ws;
		
		// Read strings from input file:
		try {
			BufferedReader bufferReader = new BufferedReader(new FileReader(fileName));
			String word;
			
			// Read file line by line, check if a word has any prefixes. 
			// If yes, create a word-suffix pair and add it to the queue of word-suffix pairs.
			// Finally, insert the word into trie.
			while ((word = bufferReader.readLine()) != null) {
				prefixes = root.getAllPrefixes(word);
				if (!prefixes.isEmpty()) 
					for(String prefix : prefixes) {
						ws = new WordSuffix(word, word.substring(prefix.length()));		
						wordSuffixes.add(ws);
					}
				root.insertWord(word);
			}
			// Close the buffer reader
			bufferReader.close();
		} catch (Exception e) {
			System.out.println("Error while reading file line by line:" + e.getMessage()); 
		}
		
		// Iterate the queue of word-suffix pairs, check if a word is compound word 
		// by repeatedly checking if the suffix in a pair is a word in the file
		while (!wordSuffixes.isEmpty()) {
			// Get a word-suffix pair from the queue
			ws = wordSuffixes.remove();
			String word = ws.word;
			String suffix = ws.suffix;
			
			// If the suffix is a word in the file, then the word is a compound word
			if (root.contain(suffix)) {
				// Increase the number of compound words
				numCompoundWord++;
				// if this compound word is longer than the previous longest compound word, 
				// update the previous longest and second compound word
				if (word.length() >= longestCompoundWord.length()) {
					secondLongestCompoundWord = longestCompoundWord;
					longestCompoundWord = word;
				}
			} else 
			// If the suffix is not a word in the file, check if the suffix is a compound word itself
			{
				// Check if the suffix has any prefixes
				prefixes = root.getAllPrefixes(suffix);
				
				// If the suffix has prefixes, create new word-suffix pair, and add it to the queue
				if (!prefixes.isEmpty()) {
					for(String prefix : prefixes) {
						ws = new WordSuffix(word, suffix.substring(prefix.length()));
						wordSuffixes.add(ws);
					}
				}
			}
		}
		
		// Display the result
		System.out.println("The 1st longest word: " + longestCompoundWord);
		System.out.println("The 2nd longest word: " + secondLongestCompoundWord);
		System.out.println("The count of words that can be constructed: " + numCompoundWord);
	}
}

// Wrapper class used to store word-suffix pair
class WordSuffix {
	public WordSuffix(String w, String s) {
		this.word = w;
		this.suffix = s;
	}
	
	public String word;
	public String suffix;
}