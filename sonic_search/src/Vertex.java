package netsfinal;

import java.util.Set;

public class Vertex{
	private int rank;
	private int year;
	private String artist;
	private String song;
	private String lyrics;
	
	private Set<Vertex> neighbors;
	
	public Vertex(String artist, String song, String lyrics){
		this.artist = artist;
		this.song = song;
		this.lyrics = lyrics;
	}
	
	public Vertex(String artist, String song, String lyrics, int rank, int year){
		this.artist = artist;
		this.song = song;
		this.lyrics = lyrics;
		this.rank = rank;
		this.year = year;
	}

	public int getRank() {
		return rank;
	}

	public int getYear() {
		return year;
	}

	public String getArtist() {
		return artist;
	}

	public String getSong() {
		return song;
	}

	public String getLyrics() {
		return lyrics;
	}

	public Set<Vertex> getNeighbors() {
		return neighbors;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (song == null) {
			if (other.song != null)
				return false;
		} else if (!song.equals(other.song))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vertex [artist=" + artist + ", song=" + song + "]";
	}
	
	
	
	
	
}
