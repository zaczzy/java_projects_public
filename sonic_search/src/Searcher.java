package netsfinal;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;


public class Searcher {

	public void search(String querystr) throws IOException, ParseException {
		Directory index = new RAMDirectory();
		StandardAnalyzer analyzer = new StandardAnalyzer();
		// Directory index = FSDirectory.open(new File("index-dir"));
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter writer = new IndexWriter(index, config);
		
		CSVParser parser = new CSVParser();
		MultiMap<Vertex> artists = parser.loadGraph();
		for (String name: artists.keySet()) {
			for (Vertex v : artists.getRecords(name)) {
				addDoc(writer, v.getLyrics(), v.getArtist(), v.getSong());
			}
		}
		writer.close();
		Query q = new MultiFieldQueryParser(new String[]{"song", "title", "artist"}, analyzer).parse(querystr);
		searchDisplay(index, q, querystr);
	}

	private static void searchDisplay(Directory index, Query q, String querystr) throws IOException {
		int hitsPerPage = 10;
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
		searcher.search(q, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		System.out.println("Query string: " + querystr);
		System.out.println("Found " + hits.length + " hits.");
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			System.out.println((i + 1) + ". " + d.get("artist") + "\t" + d.get("title"));
		}
	}

	private static void addDoc(IndexWriter w, String song, String artist, String title) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("song", song, Field.Store.YES));
		doc.add(new StringField("artist", artist, Field.Store.YES));
		doc.add(new StringField("title", title, Field.Store.YES));
		w.addDocument(doc);
	}
	
}
