package netsfinal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Recommendation {
	Map<String, Document> documents;
	MultiMap<Vertex> documentByArtists;
	
	
	public Recommendation() throws IOException {
		CSVParser parser = new CSVParser();
		documents = parser.loadDocuments();
		documentByArtists = parser.loadGraph();
	}
	
	public List<String> getRecommendationsByArtists(String song, int maxNumRecommend) throws IOException {
		Document target = documents.get(song);
		LinkedList<String> results = new LinkedList<>();
		BinaryMinHeapImpl<String, Double> heap = new BinaryMinHeapImpl<>();
		int size = 0;
		for (String artist : documentByArtists.keySet()) {
			ArrayList<Document> corpus = new ArrayList<>();
			for (Vertex v : documentByArtists.getRecords(artist)) {
				corpus.add(new Document(v));
			}
			corpus.add(target);
			VectorSpaceModel model = new VectorSpaceModel(new Corpus(corpus));
			for (Document doc : corpus) {
				if (!doc.getFileName().equals(song)) {
					if (size < maxNumRecommend) {
						heap.add(doc.getFileName(), model.cosineSimilarity(target, doc));
						size++;
					} else {
						double newKey = model.cosineSimilarity(target, doc);
						System.out.println(doc.getFileName());
						if (heap.getKey(heap.peek()) < newKey) {
							heap.extractMin();
							heap.add(doc.getFileName(), newKey);
						}
					}
				}
			}
			
		}
		while (!heap.isEmpty()) {
			results.addFirst(heap.extractMin());
		}
		return results;
	}

	public static void main(String[] args) throws IOException {
		Recommendation r = new Recommendation();
		List<String> rs = r.getRecommendationsByArtists("Happy New Year-ABBA", 10);
		System.out.println("Similar to Happy New Year:");
		int i = 1;
		for (String song : rs) {
			System.out.print(i++);
			System.out.println(" " + song);
		}
	}
}
