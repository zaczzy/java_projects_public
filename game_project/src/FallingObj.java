import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Arrays;



public abstract class FallingObj {
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + height;
		result = prime * result + (hit ? 1231 : 1237);
		result = prime * result + Arrays.hashCode(leftPoints);
		result = prime * result + Arrays.hashCode(rightPoints);
		result = prime * result + speed;
		result = prime * result + speedLeft;
		result = prime * result + speedRight;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof FallingObj))
			return false;
		FallingObj other = (FallingObj) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (height != other.height)
			return false;
		if (hit != other.hit)
			return false;
		if (!Arrays.equals(leftPoints, other.leftPoints))
			return false;
		if (!Arrays.equals(rightPoints, other.rightPoints))
			return false;
		if (speed != other.speed)
			return false;
		if (speedLeft != other.speedLeft)
			return false;
		if (speedRight != other.speedRight)
			return false;
		return true;
	}
	protected Point[] leftPoints;
    protected Point[] rightPoints;
    protected int speedLeft;
    protected int speedRight;
    protected int height;
    protected int speed;
    
    protected Color color;
    protected boolean hit;
    
    protected FallingObj(int leftLength, int rightLength){
    	leftPoints = new Point[leftLength];
    	rightPoints = new Point[rightLength];
    }
    
    public abstract void draw(Graphics g);
	
	public void move(){
		for (Point point: leftPoints){
			point.move(point.x + speedLeft, point.y + speed);
		}
		for (Point point: rightPoints){
			point.move(point.x + speedRight, point.y + speed);
		}
	}
	
	public double toBound(int bound){
		if (leftPoints != null && rightPoints != null){
			return bound - leftPoints[0].getY();
		} else{
			return -1;
		}
	}
	
	public double getPos(){
		return leftPoints[0].getY();
	}

	public double distance(int target){
		if (leftPoints != null && rightPoints != null){
			return Math.abs(target - leftPoints[0].getY() - height);
		} else{
			return -1;
		}
	}
	
	public void hit(){
		hit = true;
	}
	
	public boolean isHit(){
		return hit;
	}
	
	public abstract int getScore(int target);
	public abstract String getRating(int target);
}
