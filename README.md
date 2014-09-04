LongestCompoundWord
===================

This is the solution to one of interview questions I had before. This algorithm is inspired by the post in Arden's personal website (httpwww.ardendertat.com20120615programming-interview-questions-28-longest-compound-word).

Question Write a program that reads a file containing a sorted list of words (one word per line, no spaces, all lower case), then identifies the:
- 1. 1st longest word in the file that can be constructed by concatenating copies of shorter words also found in the file. 
- 2. The program should then go on to report the 2nd longest word found 
- 3. Total count of how many of the words in the list can be constructed of other words in the list.

Description to the algorithm 
- 1. The algorithm first scan the file line by line and create a trie by using the words in the file. Before inserting each word into the trie, it will check if the word has any prefixes. If yes, it will create word-suffix pairs and add them into a queue. 
- 2. Then, the algorithm will pop a pair from the queue to see if the suffix in the word-suffix pair is a word in the file. If yes, the word is a compound word. Then, it will update the number of compound words, and if it also is longer than the previous compound word, it will update the variables of longestCompoundWord and secondLongestCompoundWord as well. 
- 3. If the suffix in the word-suffix pair is not a word in the file, it will check if the suffix has any prefixes. If yes, it will create new word-suffix pairs and add them into the queue. Otherwise, it will just discard the pair and pop a new pair from the queue and repeat the process until the queue is empty. 
- 4. The algorithm runs in linear, O(kN), where N is the number of words in the input file, and k is the maximum number of words in a compound word.
