import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


@SuppressWarnings("serial")
public class GameCourt extends JPanel{
	public boolean playing = false; // whether the game is running
	private JLabel status; // Current status text (i.e. Running...)
	
	//Game variables 
	private LinkedList<FallingObj> list1;
	private LinkedList<FallingObj> list2;
	private LinkedList<FallingObj> list3;
	private LinkedList<FallingObj> list4;
	private LinkedList<FallingObj> list5;
	private LinkedList<FallingObj> list6;
	private LinkedList<FallingObj> list7;
	private LinkedList<FallingObj> list8;
	
	private TreeMap<String, Integer> eval;
	private int playerCode;
	private int score;
	private int[][] time;
	private int[][] speed;
	private BufferedImage image;
	private String message;
	private Mode mode;
	private int combo;
	
	
	// Game constants
	public static final int COURT_WIDTH = 1600;
	public static final int COURT_HEIGHT = 800;
		
	// Update interval for movement, in milliseconds
	public static final int INTERVAL = 40;
	
	//Game Sound
	Clip clip;
	
	public enum Mode {
		NORMAL,
		VK_A,
		VK_S,
		VK_D,
		VK_F,
		VK_J,
		VK_K,
		VK_L,
		VK_SEMICOLON,
	}
	
	public GameCourt(JLabel status){
		eval = new TreeMap<String, Integer>();
		eval.put("perfect", 0);
		eval.put("excellent", 0);
		eval.put("good", 0);
		eval.put("ok", 0);
		eval.put("missed", 0);
		eval.put("defused", 0); //bomb_mod
		eval.put("explosion", 0); //bomb_mod
		
		list1 = new LinkedList<FallingObj>();
		list2 = new LinkedList<FallingObj>();
		list3 = new LinkedList<FallingObj>();
		list4 = new LinkedList<FallingObj>();
		list5 = new LinkedList<FallingObj>();
		list6 = new LinkedList<FallingObj>();
		list7 = new LinkedList<FallingObj>();
		list8 = new LinkedList<FallingObj>();
		
		LevelGenerator.generateLevel();
		LevelGenerator.generateSpeed();
		time = LevelGenerator.readLevel();
		speed =LevelGenerator.readSpeed();
		
		 try {                
	          image = ImageIO.read(new File("guitar_hero.jpg"));
	       } catch (IOException ex) {
	            System.out.println(ex.getMessage());
	       }
	    
		
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("lies.wav").getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (Exception ex){
			System.out.println("Error with opening soundtrack.");
		}
				
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			// read in the time lapse between the makeFalling method gets called.
			tick(list1);
			tick(list2);
			tick(list3);
			tick(list4);
			tick(list5);
			tick(list6);
			tick(list7);
			tick(list8);
		}
		});
		timer.start();
		
		
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
					cleanUp();
					System.exit(0);
				}
				
				if (e.getKeyCode() == KeyEvent.VK_A){
					mode = Mode.VK_A;
				}
				if (e.getKeyCode() == KeyEvent.VK_S){
					mode = Mode.VK_S;
				}
				if (e.getKeyCode() == KeyEvent.VK_D){
					mode = Mode.VK_D;
				}
				if (e.getKeyCode() == KeyEvent.VK_F){
					mode = Mode.VK_F;
				}
				if (e.getKeyCode() == KeyEvent.VK_J){
					mode = Mode.VK_J;
				}
				if (e.getKeyCode() == KeyEvent.VK_K){
					mode = Mode.VK_K;
				}
				if (e.getKeyCode() == KeyEvent.VK_L){
					mode = Mode.VK_L;
				}
				if (e.getKeyCode() == KeyEvent.VK_SEMICOLON){
					mode = Mode.VK_SEMICOLON;
				}
			}
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_A){
					if(!list1.isEmpty()){hitObj(list1);}
					mode = Mode.NORMAL;
				}
				if (e.getKeyCode() == KeyEvent.VK_S){
					if(!list2.isEmpty()){hitObj(list2);}
					mode = Mode.NORMAL;
				}
				if (e.getKeyCode() == KeyEvent.VK_D){
					if(!list3.isEmpty()){hitObj(list3);}
					mode = Mode.NORMAL;
				}
				if (e.getKeyCode() == KeyEvent.VK_F){
					if(!list4.isEmpty()){hitObj(list4);}
					mode = Mode.NORMAL;
				}
				if (e.getKeyCode() == KeyEvent.VK_J){
					if(!list5.isEmpty()){hitObj(list5);}
					mode = Mode.NORMAL;
				}
				if (e.getKeyCode() == KeyEvent.VK_K){
					if(!list6.isEmpty()){hitObj(list6);}
					mode = Mode.NORMAL;
				}
				if (e.getKeyCode() == KeyEvent.VK_L){
					if(!list7.isEmpty()){hitObj(list7);}
					mode = Mode.NORMAL;
				}
				if (e.getKeyCode() == KeyEvent.VK_SEMICOLON){
					if(!list8.isEmpty()){hitObj(list8);}
					mode = Mode.NORMAL;
				}
			}
			});
		
		setFocusable(true);
		this.status = status;
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		// Read Current Player Name
		try{
			playerCode =LevelGenerator.getCurrentPlayerCode();
		} catch (FileNotFoundException e){
			message = "Scores is not found!";
			playerCode = 1;
		} catch (IOException e){
			message = "Scores tempered with!";
			playerCode = 1;
		}
	}
	public String getPlayerName(){
		return "Player" + playerCode;
	}
	private void hitObj(LinkedList<FallingObj> list){

		if(!list.isEmpty()){
			int pointer = 0;
			int size = list.size();
			while(pointer < size && list.get(pointer).isHit()){
				pointer += 1;
			}
			boolean hasHit = false;
			
			if(size >= pointer + 3){ 
				FallingObj first = list.get(pointer);
				FallingObj second = list.get(pointer + 1);
				FallingObj third = list.get(pointer + 2);
				if ( third.getPos() > second.getPos() && third.getPos() > first.getPos() 
						&& !third.isHit() && !hasHit || third.getPos() > first.getPos() && second.isHit()
						&& !third.isHit() && !hasHit){
					third.hit();
					String rating = third.getRating(600);
					message = rating;
					accumCombo(rating);
					eval.put(rating, eval.get(rating) + 1);
					score += (int) third.getScore(600) * (1 + combo * 0.1);
					hasHit = true;
				} 
			}
	
			if(size >= pointer + 2){
				FallingObj first = list.get(pointer);
				FallingObj second = list.get(pointer + 1);
				if ( second.getPos() > first.getPos() && !second.isHit() && !hasHit){
				    second.hit();
					String rating = second.getRating(600);
					message = rating;
					accumCombo(rating);
					eval.put(rating, eval.get(rating) + 1);
					score += (int) second.getScore(600) * (1 + combo * 0.1);
					hasHit = true;
				}
			} 
			
			if (size >= pointer + 1){
				FallingObj first = list.get(pointer);
				if(!first.isHit() && !hasHit){
					first.hit();
					String rating = first.getRating(600);
					message = rating;
					accumCombo(rating);
					eval.put(rating, eval.get(rating) + 1);
					score += (int) first.getScore(600) * (1 + combo * 0.1);
					hasHit = true;
				}
			}
			status.setText("Perfect: " + eval.get("perfect") + "; Excellent: " + eval.get("excellent") + 
					"; Good: " + eval.get("good") + "; Ok:" + eval.get("ok") + 
					"; Missed: " + eval.get("missed") + ". The score is: " + score);
		}
	}
	private void accumCombo(String rating){
		switch (rating){
		case "perfect": combo++; break;
		case "excellent": combo++; break;
		case "good": break;
		case "ok": break;
		case "missed": combo = 0; break;
		default: break;
		}
	};
	private void tick(LinkedList<FallingObj> list1) {
		if (playing) {
			// advance the falling objects to their next position
			if(!list1.isEmpty()){
				for (FallingObj obj: list1){
					obj.move();
				}
				FallingObj first = list1.getFirst();
				if (first.toBound(800) < 0){
					list1.removeFirst();
					if (!first.isHit()){
						eval.put("missed", eval.get("missed") + 1);
						score -= 1;
					}
					status.setText("Perfect: " + eval.get("perfect") + "; Excellent: " + eval.get("excellent") + 
							"; Good: " + eval.get("good") + "; Ok:" + eval.get("ok") + 
							"; Missed: " + eval.get("missed") + ". The score is: " + score);
				}
				// update the display
				repaint();
			}
		}
	}
	
	
	private Color randomColor(){
		int c = (int) (Math.random() * 5);
		switch(c){
		case 0: return Color.MAGENTA;
		case 1: return Color.RED;
		case 2: return Color.YELLOW;
		case 3: return Color.BLUE;
		default: return Color.GREEN;
		}
	}
	private int randomScore(int range){
		return 1 + (int) (Math.random() * range);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
		g.setColor(Color.WHITE);
		g.drawLine(800, 0, 0, 800);
		g.drawLine(800, 0, 200, 800);
		g.drawLine(800, 0, 400, 800);
		g.drawLine(800, 0, 600, 800);
		g.drawLine(800, 0, 800, 800);
		g.drawLine(800, 0, 1000, 800);
		g.drawLine(800, 0, 1200, 800);
		g.drawLine(800, 0, 1400, 800);
		g.drawLine(800, 0, 1600, 800);
		g.drawLine(200, 600, 1400, 600);
		g.drawLine(200, 596, 1400, 596);
		Polygon p = new Polygon();
		if (mode == Mode.VK_A){
			p.addPoint(200, 600);
			p.addPoint(0, 800);
			p.addPoint(200, 800);
			p.addPoint(350, 600);
			g.setColor(Color.MAGENTA);
			g.fillPolygon(p);;
		}
		if (mode == Mode.VK_S){
			p.addPoint(350,600);
			p.addPoint(200, 800);
			p.addPoint(400, 800);
			p.addPoint(500, 600);
			g.setColor(Color.ORANGE);
			g.fillPolygon(p);;
		}
		if (mode == Mode.VK_D){
			p.addPoint(500,600);
			p.addPoint(400, 800);
			p.addPoint(600, 800);
			p.addPoint(650, 600);
			g.setColor(Color.CYAN);
			g.fillPolygon(p);;
		}
		if (mode == Mode.VK_F){
			p.addPoint(650, 600);
			p.addPoint(600, 800);
			p.addPoint(800, 800);
			p.addPoint(800, 600);
			g.setColor(Color.GREEN);
			g.fillPolygon(p);;
		}
		if (mode == Mode.VK_J){
			p.addPoint(800, 600);
			p.addPoint(800, 800);
			p.addPoint(1000, 800);
			p.addPoint(950, 600);
			g.setColor(Color.YELLOW);
			g.fillPolygon(p);;
		}
		if (mode == Mode.VK_K){
			p.addPoint(950, 600);
			p.addPoint(1000, 800);
			p.addPoint(1200, 800);
			p.addPoint(1100, 600);
			g.setColor(Color.BLUE);
			g.fillPolygon(p);;
		}
		if (mode == Mode.VK_L){
			p.addPoint(1100, 600);
			p.addPoint(1200, 800);
			p.addPoint(1400, 800);
			p.addPoint(1250, 600);
			g.setColor(Color.RED);
			g.fillPolygon(p);;
		}
		if (mode == Mode.VK_SEMICOLON){
			p.addPoint(1250, 600);
			p.addPoint(1400, 800);
			p.addPoint(1600, 800);
			p.addPoint(1400, 600);
			g.setColor(Color.PINK);
			g.fillPolygon(p);;
		}
		
		//message
		if(message != null){
			g.setFont(new Font(Font.SERIF, Font.BOLD, 120));
			g.setColor(Color.WHITE);
			g.drawString(message, 10, 180);
		}
		//keys
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 120));
		g.setColor(new Color(32435008));
		g.drawString("A", 100, 800);
		g.drawString("S", 300, 800);
		g.drawString("D", 500, 800);
		g.drawString("F", 700, 800);
		g.drawString(";", 1430, 780);
		g.drawString("L", 1250, 800);
		g.drawString("K", 1050, 800);
		g.drawString("J", 850, 800);
				
		//combo
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.SERIF, Font.PLAIN, 120));
		if (combo > 19){
			g.setColor(randomColor());
			g.drawString("Yeah!!!!", 1000, 180);
		} else if (combo > 9){
			g.setColor(Color.RED);
			g.drawString("Combo: " + combo + "!!!", 1000, 180);
		} else if (combo > 4){
			g.setColor(Color.YELLOW);
			g.drawString("Combo: " + combo + "!", 1000, 180);
		} else {
			g.drawString("Combo: " + combo, 1000, 180);
		}
		
		
		for(FallingObj obj: list1){
			obj.draw(g);
		}
		for(FallingObj obj: list2){
			obj.draw(g);
		}
		for(FallingObj obj: list3){
			obj.draw(g);
		}
		for(FallingObj obj: list4){
			obj.draw(g);
		}
		for(FallingObj obj: list5){
			obj.draw(g);
		}
		for(FallingObj obj: list6){
			obj.draw(g);
		}
		for(FallingObj obj: list7){
			obj.draw(g);
		}
		for(FallingObj obj: list8){
			obj.draw(g);
		}
	}
	private FallingObj createrHelper(int speed, int left, int right){
		int odds = (int)(Math.random() * 10 + 1);
		if (odds > 9){
			return new Bomb(new Point(800, 0), new Point(32 * left + 800, 128), new Point(800,0), new Point(32 * right + 800, 128), 2 * left, 2 * right, 8);
		} else {
			return new Bar(new Point(800, 0), new Point(800,0),
					left * speed/4, right *speed/4, speed, randomColor(), randomScore(20));
		}
	}
	private void createTimer(int[] intervals, int count, int[] speed, LinkedList<FallingObj> list1, int left, int right){
		Timer timer = new Timer(intervals[count], new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int currentSpeed = speed[count];
				list1.add(createrHelper(currentSpeed, left, right)); //bomb_mod
						//new Bar(new Point(400, 0), new Point(400,0),
						//left * currentSpeed/4, right *currentSpeed/4, currentSpeed, randomColor(), randomScore(20)));
				if(count < 49){
					createTimer(intervals, count + 1, speed, list1, left, right);
				} else {
					cleanUp();
				}
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	private void initialize(){
		createTimer(time[0], 0, speed[0], list1, -4, -3);
		createTimer(time[1], 0, speed[1], list2, -3, -2);
		createTimer(time[2], 0, speed[2], list3, -2, -1);
		createTimer(time[3], 0, speed[3], list4, -1, 0);
		createTimer(time[4], 0, speed[4], list5, 0, 1);
		createTimer(time[5], 0, speed[5], list6, 1, 2);
		createTimer(time[6], 0, speed[6], list7, 2, 3);
		createTimer(time[7], 0, speed[7], list8, 3, 4);
	}
	
	public void defreeze() {
		playing = true;
		playSound();
		initialize();

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}
	
	private void playSound() {
	    clip.start();
	}
	
	private void cleanUp(){
		clip.stop();
		clip.close();
		playing = false;
		status.setText("Perfect: " + eval.get("perfect") + "; Excellent:" + eval.get("excellent") + 
				"; Good:" + eval.get("good") + "; Ok:" + eval.get("ok") + 
				"; Missed:" + eval.get("missed") + ". The final score is: " + score);
		try {
			LevelGenerator.updateHighScore(playerCode, score);
		} catch (NumberFormatException e) {
			message = "File tempered. Return Player1.";
			LevelGenerator.newRecord(1, score);
		} catch (FileNotFoundException e) {
			message = "File tempered. Return Player1.";
			LevelGenerator.newRecord(1, score);
		} catch (IOException e) {
			message = "File tempered. Return Player1.";
			LevelGenerator.newRecord(1, score);
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}
