package netsfinal;


import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.print.Doc;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


public class CSVParser {

	public MultiMap<Vertex> loadGraph() throws IOException {
		MultiMap<Vertex> artists = new MultiMap<>();
		Reader in = new FileReader("data/songdata.csv");
		Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);

		for (CSVRecord record : records) {

			String artist = record.get(0);
			String song = record.get(1);
			String lyrics = record.get(3);
			if (!lyrics.contains("NA")) {
				Vertex v = new Vertex(artist, song, lyrics);
				artists.addRecord(v.getArtist(), v);
			}
		}
		return artists;
	}

	public Map<String, Document> loadDocuments() throws IOException {
		Reader in = new FileReader("data/songdata.csv");
		Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
		Map<String, Document> songs = new HashMap<>();
		for (CSVRecord record : records) {

			String artist = record.get(0);
			String song = record.get(1);
			String lyrics = record.get(3);
			if (!lyrics.contains("NA")) {
				Document v = new Document(new Vertex(artist, song, lyrics));
				songs.put(v.getFileName(), v);
			}
		}
		return songs;
	}

	public Map<String, double[][]> similarityMatrix(MultiMap<Vertex> artists) {
		Map<String, double[][]> matrixMap = new HashMap<>();
		for (String artist : artists.keySet()) {
			int dimension = artists.count(artist);
			double[][] matrix = new double[dimension][dimension];
			ArrayList<Document> documents = new ArrayList<>();
			for (Vertex v : artists.getRecords(artist)) {
				documents.add(new Document(v));
			}
			Corpus c = new Corpus(documents);
			VectorSpaceModel model = new VectorSpaceModel(c);
			for (int i = 0; i < documents.size(); i++) {
				for (int j = 0; j < documents.size(); j++) {
					matrix[i][j] = model.cosineSimilarity(documents.get(i), documents.get(j));
				}
			}
			matrixMap.put(artist, matrix);
		}
		return matrixMap;
	}
	/**
	 * 
	 * @param a1 the name of the first artist
	 * @param a2 the name of the second artist
	 * @param artists the multimap of artists to their collection of songs
	 * @return
	 */
	public double[][] similarityMatrixBetweenArtists(String a1, String a2, MultiMap<Vertex> artists) {
		int l = artists.count(a1);
		int h = artists.count(a2);
		double[][] matrix = new double[l][h];
		ArrayList<Document> songs1 = new ArrayList<>();
		for (Vertex v : artists.getRecords(a1)) {
			songs1.add(new Document(v));
		}
		ArrayList<Document> songs2 = new ArrayList<>();
		for (Vertex v : artists.getRecords(a2)) {
			songs2.add(new Document(v));
		}
		ArrayList<Document> songs = new ArrayList<>(songs1);
		songs.addAll(songs2);
		Corpus c = new Corpus(songs);
		VectorSpaceModel model = new VectorSpaceModel(c);
		for (int i = 0; i < l; i++) {
			for (int j = 0; j< h; j++) {
				matrix[i][j] = model.cosineSimilarity(songs1.get(i), songs2.get(j));
			}
		}
		return matrix;
	}

	public static void main(String[] args) throws IOException {
		CSVParser c = new CSVParser();
		MultiMap<Vertex> artists = c.loadGraph();
		System.out.println("Total number of songs: " + artists.size());
	}
}