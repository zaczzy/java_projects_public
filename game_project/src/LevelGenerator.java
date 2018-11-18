import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;


public class LevelGenerator {
	public static void generateLevel(){
		try{
			File f = new File("/level.txt");
			f.createNewFile();
			BufferedWriter bf = new BufferedWriter(new FileWriter("level.txt"));
			for (int i = 0; i < 8; i++){
				for (int j = 0; j < 10; j++){
					int time = (int) (5000 + Math.random() * 4000);
					bf.write(Integer.toString(time) + " ");
				}
				for (int j = 0; j < 20; j++){
					int time = (int) (3000 + Math.random() * 3000);
					bf.write(Integer.toString(time) + " ");
				}
				for (int j = 0; j < 20; j++){
					int time = (int) (3000+ Math.random() * 2000);
					bf.write(Integer.toString(time) + " ");
				}
				bf.newLine();
			}
			bf.close();
		} 
		catch (IOException e){
				System.out.println(e.getMessage());
		}
	}
	
	public static void generateSpeed(){
		try{
			File f = new File("/speed.txt");
			f.createNewFile();
			BufferedWriter bf = new BufferedWriter(new FileWriter("speed.txt"));
			for (int i = 0; i < 8; i++){
				for (int j = 0; j < 10; j++){
					int speed = 8;
					bf.write(Integer.toString(speed) + " ");
				}
				for (int j = 0; j < 20; j++){
					int speed = (int)(Math.random() * 2 + 1 ) * 8;
					bf.write(Integer.toString(speed) + " ");
				}
				for (int j = 0; j < 20; j++){
					int speed = (int)(Math.random() * 2 + 2 ) * 8;
					bf.write(Integer.toString(speed) + " ");
				}
				bf.newLine();
			}
			bf.close();
		} 
		catch (IOException e){
				System.out.println(e.getMessage());
		}
	}
	
	public static int[][] readLevel(){
		int[][] time = new int[8][50];
		BufferedReader bf = null;
		try{
			FileReader fr = new FileReader(new File("level.txt"));
			bf = new BufferedReader(fr);
			int i = 0;
			int j = 0;
				
			String s = bf.readLine();
			while (s != null){
				int space = s.indexOf(" ");
				while(space != -1){
					time[i][j] = Integer.parseInt(s.substring(0, space));
					j += 1;
					s = s.substring(space + 1);
					space = s.indexOf(" ");
				}
				if (j != 50){
					System.out.println("File Damaged!");
					return new int[8][50];
				}
				j = 0;
				i += 1;
				s = bf.readLine();
			}
			fr.close();
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		} catch (IOException e){
			System.out.println(e.getMessage());
		} finally{
			if (bf != null) {
				try { bf.close(); } catch (IOException e) {
				System.out.println(e.getMessage());
				}
			}
		}
		return time;
	}
	
	public static int[][] readSpeed(){
		int[][] speed = new int[8][50];
		BufferedReader bf = null;
		try{
			FileReader fr = new FileReader(new File("speed.txt"));
			bf = new BufferedReader(fr);
			int i = 0;
			int j = 0;
				
			String s = bf.readLine();
			while (s != null){
				int space = s.indexOf(" ");
				while(space != -1){
					speed[i][j] = Integer.parseInt(s.substring(0, space));
					j += 1;
					s = s.substring(space + 1);
					space = s.indexOf(" ");
				}
				if (j != 50){
					System.out.println("File Damaged!");
					return new int[8][50];
				}
				j = 0;
				i += 1;
				s = bf.readLine();
			}
			fr.close();
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		} catch (IOException e){
			System.out.println(e.getMessage());
		} finally{
			if (bf != null) {
				try { bf.close(); } catch (IOException e) {
				System.out.println(e.getMessage());
				}
			}
		}
		return speed;
	}
	public static String readHighScore(){
		try {
			BufferedReader read = new BufferedReader(new FileReader("highScore.txt"));
			String next = read.readLine();
			String s = "";
			while(next != null){
				s = s + next + "\n";
				next = read.readLine();
			}
			read.close();
			return s;
		} catch (IOException e) {
			newRecord(1, 0);
			return readHighScore();
		}
	}
	public static void updateHighScore(int playerCode, int score) throws IOException, NumberFormatException, FileNotFoundException{
			BufferedReader read = new BufferedReader(new FileReader("highScore.txt"));
			TreeMap<Integer, TreeSet<String>> scoreBoard = new TreeMap<Integer, TreeSet<String>>();
			read.readLine(); //skip the first line because that is current player name
			String s = read.readLine();
			while(s != null){
				/**
				 * current player: 45 
				 * 1. player1: 77
				 * 2. player2: 182
				 * 3. player3: 88
				 * */
				int startIndex = s.indexOf(".");
				int divideIndex = s.indexOf(":");
				if (startIndex == -1 || divideIndex == -1){
					read.close();
					throw new IOException("Cannot find divider index \":\".");
				}
				String playerName = s.substring(startIndex + 1, divideIndex).trim();
				String playerScoreString = s.substring(divideIndex + 1).trim();
				int playerScore = Integer.parseInt(playerScoreString);
				if (!scoreBoard.containsKey(playerScore)){
					TreeSet<String> diff = new TreeSet<String>();
					diff.add(playerName);
					scoreBoard.put(playerScore, diff);
				} else {
					TreeSet<String> exists = scoreBoard.get(playerScore);
					exists.add(playerName);
					scoreBoard.put(playerScore, exists);
				}
				s = read.readLine();
			}
			read.close();
			// add result of this game
			if (!scoreBoard.containsKey(score)){
				TreeSet<String> diff = new TreeSet<String>();
				diff.add("Player" + playerCode);
				scoreBoard.put(score, diff);
			} else {
				TreeSet<String> exists = scoreBoard.get(score);
				exists.add("Player" + playerCode);
				scoreBoard.put(score, exists);
			}
			
			//writing to file
			BufferedWriter bf = new BufferedWriter(new FileWriter("highScore.txt"));
			bf.write("Current Player: " + (playerCode + 1)); 
			bf.newLine();
			int count = 1;
			Iterator<Integer> scoreItr = scoreBoard.keySet().iterator();
			
			while(scoreItr.hasNext()){
				Integer thisScore = scoreItr.next();
				TreeSet<String> players = scoreBoard.get(thisScore);
				Iterator<String> playerItr = players.iterator();
				while(playerItr.hasNext()){
					bf.write(count + ". " + playerItr.next()+ ": " + thisScore);
					count++;
					bf.newLine();
				}
			}
			bf.close();
	}
	
	public static int getCurrentPlayerCode() throws FileNotFoundException, IOException{
			BufferedReader read = new BufferedReader(new FileReader("highScore.txt"));
			String line = read.readLine();
			read.close();
			if(line != null){
				int divideIndex = line.indexOf(":");
				if (divideIndex == -1){
					throw new IOException("Did not find the current player code!");
				}
				String codeS = line.substring(divideIndex + 1).trim();
				int code = Integer.parseInt(codeS);
				return code;
			} else {
				throw new IOException("File Empty!");
			}
	}
	public static void newRecord(int playerCode, int score){
			File f = new File("highScore.txt");
			try {
				f.createNewFile();
				BufferedWriter bf = new BufferedWriter(new FileWriter("highScore.txt"));
				bf.write("Current Player: 1");
				bf.close();
				updateHighScore(1, score);
			} catch (IOException e) {
				e.printStackTrace();
			}				
	}
}
