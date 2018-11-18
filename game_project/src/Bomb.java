import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

public class Bomb extends FallingObj {
	private boolean isBlack;
	
	public Bomb(Point leftUp, Point leftDown, Point rightUp, Point rightDown, int speedLeft, int speedRight, int speed) {
		super(2, 2);
		leftPoints[0] = leftDown;
		rightPoints[0] = rightDown;
		leftPoints[1] = leftUp;
		rightPoints[1] = rightUp;
		this.speedLeft = speedLeft;
		this.speedRight = speedRight;
		height = 64;
		this.speed = speed;
	}

	@Override
	public void draw(Graphics g) {
		Polygon p = new Polygon();
		p.addPoint((int) (leftPoints[0].getX()), (int) (leftPoints[0].getY()));
		p.addPoint((int) (leftPoints[1].getX()), (int) (leftPoints[1].getY()));
		p.addPoint((int) (rightPoints[1].getX()), (int) (rightPoints[1].getY()));
		p.addPoint((int) (rightPoints[0].getX()), (int) (rightPoints[0].getY()));
		Graphics2D gc = (Graphics2D) g;
		if(hit == true){
			gc.setColor(Color.GRAY);
			
		} else if(isBlack){
			gc.setColor(Color.BLACK);
			isBlack = !isBlack;
		} else {
			gc.setColor(Color.WHITE);
			isBlack = !isBlack;
		}
		gc.fillPolygon(p);
	}

	@Override
	public int getScore(int target) {
		// TODO Auto-generated method stub
		double d = toBound(target);
		if (d < 0 && d + height > 0){
			return -50;
		} else {
			return 5;
		}
	}

	@Override
	public String getRating(int target) {
		// TODO Auto-generated method stub
		double d = toBound(target);
		if (d < 0 && d + height > 0){
			return "explosion";
		} else {
			return "defused";
		}
	}

}
