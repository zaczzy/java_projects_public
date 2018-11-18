import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Bar extends FallingObj {
	
	private int score;
	
	public Bar(Point left, Point right, int speedLeft, int speedRight, int speed, Color c, int score) {
		super(1, 1);
		leftPoints[0] = left;
		rightPoints[0] = right;
		this.speedLeft = speedLeft;
		this.speedRight = speedRight;
		height = 1;
		this.speed = speed;
		this.color = c;
		this.score = score;
	}
	
	public int getScore(int target){
		double d = distance(target);
		if (d < 0){
			return 0;
		}
		if (d < 5){
			return score * 3;
		}
		if (d < 10){
			return score * 2;
		}
		if(d < 20){
			return score;
		}
		if(d < 30){
			return - score / 2;
		}
		return 0;
	}
	
	public String getRating(int target){
		double d = distance(target);
		if (d < 0){
			return "missed";
		}
		if (d < 5){
			return "perfect";
		}
		if (d < 12){
			return "excellent";
		}
		if(d < 20){
			return "good";
		}
		if(d < 30){
			return "ok";
		}
		return "missed";
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D gc = (Graphics2D) g;
		if(hit == true){
			gc.setColor(Color.GRAY);
		} else {
			gc.setColor(color);
		}
		if(score <= 5){
			gc.setStroke(new BasicStroke(2));
		} else if(score <= 10){
			gc.setStroke(new BasicStroke(4));
		} else if(score <= 15){
			gc.setStroke(new BasicStroke(6));
		} else {
			gc.setStroke(new BasicStroke(8));
		}
		
		gc.drawLine((int) leftPoints[0].getX(),(int) leftPoints[0].getY(), 
				(int) rightPoints[0].getX(), (int) rightPoints[0].getY());

	}
	

}
