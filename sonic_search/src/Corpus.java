/* This file is a caller provided by the instructor but modified. */
package netsfinal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class represents a corpus of docs.
 * It will create an inverted index for these docs.
 * @author swapneel
 *
 */
public class Corpus {
	
	/**
	 * An arraylist of all docs in the corpus.
	 */
	private ArrayList<Document> docs;
	
	/**
	 * The inverted index. 
	 * It will map a term to a set of docs that contain that term.
	 */
	private HashMap<String, Set<Document>> invertedIndex;
	
	/**
	 * The constructor - it takes in an arraylist of docs.
	 * It will generate the inverted index based on the docs.
	 * @param docs the list of docs
	 */
	public Corpus(ArrayList<Document> docs) {
		this.docs = docs;
		invertedIndex = new HashMap<String, Set<Document>>();
		
		createInvertedIndex();
	}
	
	/**
	 * This method will create an inverted index.
	 */
	private void createInvertedIndex() {
		System.out.println("Creating the inverted index");
		
		for (Document document : docs) {
			Set<String> terms = document.getTermList();
			
			for (String term : terms) {
				if (invertedIndex.containsKey(term)) {
					Set<Document> list = invertedIndex.get(term);
					list.add(document);
				} else {
					Set<Document> list = new TreeSet<Document>();
					list.add(document);
					invertedIndex.put(term, list);
				}
			}
		}
	}
	
	/**
	 * This method returns the idf for a given term.
	 * @param term a term in a document
	 * @return the idf for the term
	 */
	public double getInverseDocumentFrequency(String term) {
		if (invertedIndex.containsKey(term)) {
			double size = docs.size();
			Set<Document> list = invertedIndex.get(term);
			double documentFrequency = list.size();
			
			return Math.log10(size / documentFrequency);
		} else {
			return 0;
		}
	}

	/**
	 * @return the docs
	 */
	public ArrayList<Document> getdocs() {
		return docs;
	}

	/**
	 * @return the invertedIndex
	 */
	public HashMap<String, Set<Document>> getInvertedIndex() {
		return invertedIndex;
	}
}