import java.util.*;

public class PlayerShip extends SpaceShip {
	private double health = 100, energy = 100;;
	private int horizAngleState, vertAngleState;
	private static final double standardPlayerSpeed = 1;
	private Vector playerVel = new Vector(standardPlayerSpeed, 0, 0);
	private int rollCounter;
	private static final double[][] rollRight = Matrix.getRotMatX(-Math.PI/5), rollLeft = Matrix.getRotMatX(Math.PI/5);
	private boolean rollingRight;
	
	public void decreaseHealth (double amt) { health -= amt; if (health < 0) health = 0; if (health > 100) health = 100;}
	public double getHealth () { return health; }
	public void decreaseEnergy (double amt) { energy -= amt; if (energy < 0) energy = 0; if (energy > 100) energy = 100;}
	public double getEnergy () { return energy; }
	
	public void startRightRoll () {
		if (rollCounter == 0 && energy > 5) {
			rollCounter = 1;
			rollingRight = true;
			energy -= 5;
		}
	}
	
	public void startRollLeft () {
		if (rollCounter == 0 && energy > 5) {
			rollCounter = 1;
			rollingRight = false;
			energy -= 5;
		}
	}
	
	public void moveShipTo (Vector pos) {
		translate(pos.minus(getPos()));
		setPos(pos);
	}
//	public Vector getPlayerPos () { return playerPos; }
	
	public PlayerShip (Vector offset) {
		super();
		setPos(offset);
		try {
			setTris(Mesh.loadFromObjFile("Models/Ship Model 1.obj", "Textures/Ship Model 1 Map.png").getTris());
		} catch (Exception e) { System.out.println("Error loading ShipModel2.obj"); }
		translate(offset);
		System.out.println(this.getTris().get(0).getTexture());
		setCollisionRadius(2.5);
	}
	
	public boolean bulletCollision(ArrayList<Bullet> bulletList) {
		for (int i=0;i<bulletList.size();i++) {
			if (bulletList.get(i).getEnemy()&&getPos().clone().minus(bulletList.get(i).getPos()).magnitude() < (bulletList.get(i).getCollisionRadius() + getCollisionRadius())) {
				return true;
			}
		}
		return false;
	}
	
	public void destroy (GraphicsPanel graphicsPanel) {}
	
	public void update (GraphicsPanel graphicsPanel) {
		update(0, 0, 0);
	}
	
	public void update (double horiz, double vert, double speed) {
		if (rollCounter > 0) {
			translate(getPos().clone().scale(-1));
			if (rollingRight) {
				for (Triangle tri : getTris())
					tri.setVerts(new Vector[] {Matrix.multMatVec(rollRight, tri.getVert1()), 
							Matrix.multMatVec(rollRight, tri.getVert2()), 
							Matrix.multMatVec(rollRight, tri.getVert3())});
				setPos(getPos().clone().plus(new Vector(playerVel.getX(), 0, -2)));
				setPos(new Vector(getPos().getX(), getPos().getY(), Math.max(getPos().getZ(), -19)));				
			} else {
				for (Triangle tri : getTris())
					tri.setVerts(new Vector[] {Matrix.multMatVec(rollLeft, tri.getVert1()), 
							Matrix.multMatVec(rollLeft, tri.getVert2()), 
							Matrix.multMatVec(rollLeft, tri.getVert3())});
				setPos(getPos().clone().plus(new Vector(playerVel.getX(), 0, 2)));
				setPos(new Vector(getPos().getX(), getPos().getY(), Math.min(getPos().getZ(), 19)));		
			}
			translate(getPos());
			rollCounter++;
			if (rollCounter == 11)
				rollCounter = 0;
		} else {
			double[][] rotMat = Matrix.getIdentityMatrix();
			if ((horiz > 0 && horizAngleState < 15) || (horiz == 0 && horizAngleState < 0)) {
				horizAngleState++;
				rotMat = Matrix.mulMatMat(rotMat, Matrix.getRotMatX(-Math.PI/50));
			} else if ((horiz < 0 && horizAngleState > -15) || (horiz == 0 && horizAngleState > 0)) {
				horizAngleState--;
				rotMat = Matrix.mulMatMat(rotMat, Matrix.getRotMatX(Math.PI/50));
			}
			//NOTE: cannot have horiz and vert rotation simultaneously, otherwise breaks
	//		if ((vert > 0 && vertAngleState < 15) || (vert == 0 && vertAngleState < 0)) {
	//			vertAngleState++;
	//			rotMat = Matrix.mulMatMat(rotMat, Matrix.getRotMatZ(Math.PI/50));
	//		} else if ((vert < 0 && vertAngleState > -15) || (vert == 0 && vertAngleState > 0)) {
	//			vertAngleState--;
	//			rotMat = Matrix.mulMatMat(rotMat, Matrix.getRotMatZ(-Math.PI/50));
	//		}
			translate(getPos().clone().scale(-1));
			for (Triangle tri : getTris())
				tri.setVerts(new Vector[] {Matrix.multMatVec(rotMat, tri.getVert1()), 
						Matrix.multMatVec(rotMat, tri.getVert2()), 
						Matrix.multMatVec(rotMat, tri.getVert3())});
			translate(getPos());
			
	//		System.out.println("playerVel: " + playerVel);
			if ((playerVel.getX() >= 0.3+standardPlayerSpeed && speed == 1) || (playerVel.getX() <= -0.3+standardPlayerSpeed && speed == -1))
				speed = 0;
			else if (speed == 0 && playerVel.getX() > 0+standardPlayerSpeed)
				speed = -0.25;
			else if (speed == 0 && playerVel.getX() < 0+standardPlayerSpeed)
				speed = 0.25;
			
			if ((playerVel.getZ() <= -0.4 && horiz == 1) || (playerVel.getZ() >= 0.4 && horiz == -1))
				horiz = 0;
			else if (horiz == 0 && playerVel.getZ() < 0)
				horiz = Math.max(-0.25, playerVel.getZ());
			else if (horiz == 0 && playerVel.getZ() > 0)
				horiz = Math.min(0.25, playerVel.getZ());
			
			if ((playerVel.getY() >= 0.4 && vert == 1) || (playerVel.getY() <= -0.4 && vert == -1))
				vert = 0;
			else if (vert == 0 && playerVel.getY() > 0)
				vert = Math.max(-0.25, -playerVel.getY());
			else if (vert == 0 && playerVel.getY() < 0)
				vert = Math.min(0.25, -playerVel.getY());
			
			playerVel = playerVel.plus(new Vector(speed/30.0, vert/20.0, -horiz/20.0));
			if ((getPos().getY() >= 17 && playerVel.getY() > 0) || (getPos().getY() <= -17 && playerVel.getY() < 0))
				playerVel = new Vector(playerVel.getX(), 0, playerVel.getZ());
			if ((getPos().getZ() >= 19 && playerVel.getZ() > 0) || (getPos().getZ() <= -19 && playerVel.getZ() < 0))
				playerVel = new Vector(playerVel.getX(), playerVel.getY(), 0);
			moveShipTo(getPos().plus(playerVel));
		}
	}
}
