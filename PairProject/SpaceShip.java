import java.util.*;

public class SpaceShip extends Mesh {
	private Vector pos;
	public Vector getPos () { return pos; }
	public void setPos (Vector pos) { this.pos = pos; }
	
	private double collisionRadius;
	public double getCollisionRadius () { return collisionRadius; }
	public void setCollisionRadius (double radius) { collisionRadius = radius; }
	
	public boolean collision (ArrayList<Bullet> bulletList) {
		for (Bullet bullet : bulletList)
			if (pos.clone().minus(bullet.getPos()).magnitude() < (0.3 + collisionRadius))
				return true;
		return false;
	}
	
	public void update (Game game) {}
}
