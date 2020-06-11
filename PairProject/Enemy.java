import java.util.*;

public class Enemy extends SpaceShip {
	
	public Explosion explosion;
	public double theta;
	public double fireCounter;
	private Vector pship, initPos;
	private boolean[] firestage, movestage;
	private int moveCounter, dist, d, ai, difficulty;
	
	public Enemy (Vector pos, int dist, int ai, int difficulty) {
		super();
		setCollisionRadius(2);
		firestage=new boolean[4];
		movestage=new boolean[4];
		this.dist=dist;
		this.ai=ai;
		this.difficulty=difficulty;
	}
	
	public boolean bulletCollision (ArrayList<Bullet> bulletList) {
		for (int i=0;i<bulletList.size();i++) {
			if (!bulletList.get(i).getEnemy()&&getPos().clone().minus(bulletList.get(i).getPos()).magnitude() < (bulletList.get(i).getCollisionRadius() + getCollisionRadius())) {
				return true;
			}
		}
		return false;
	}
	
	public void update (GraphicsPanel graphicsPanel) {
		switch (ai) {
		case 0: update0(graphicsPanel); break;
		case 1: update1(graphicsPanel); break;
		}
	}
	
	/**
	 * Standard difficulty AI.
	 * @param graphicsPanel
	 */
	public void update0 (GraphicsPanel graphicsPanel) {
		pship=graphicsPanel.getPlayerShip().getPos();
		//System.out.println(game.getPlayerShip().getPos());
		//System.out.println(getPos());
		//		if (getPos().getX() > pship.getX()+dist) {
		//			this.translate(new Vector(0, getPos().getY(), getPos().getZ()));
		//			this.setPos(new Vector(getPos().getX(), getPos().getY(), getPos().getZ()));
		//if (getPos().getX() > pship.getX()+dist) {
		//			System.out.println("piqoudsihfoy");
		//			this.translate(new Vector(0, 10*Math.cos(theta)-getPos().getY(), 10*Math.sin(theta)-getPos().getZ()));
		//			this.setPos(new Vector(getPos().getX(), 10*Math.cos(theta), 10*Math.sin(theta)));
		if (explosion == null) {
			if (getPos().getX() <= pship.getX()+dist) {
				int movecurrent=-1;
				for (int i=0;i<movestage.length;i++) {
					if (movestage[i]) {
						movecurrent=i;
						break;
					}
				}
				if (movecurrent!=-1) {
					moveCounter++;
					move(graphicsPanel, movecurrent);
				} else {
					moveCounter=d=0;
					theta=0;
					Random rand=new Random();
					int randmove=rand.nextInt(movestage.length);
					if (randmove==0) {
						initPos=getPos();
					}
					int rint=rand.nextInt(2);
					if (rint==0) {
						d=1;
					} else {
						d=-1;
					}

					movestage[randmove]=true;
					move(graphicsPanel, randmove);
				}
			}

			int firecurrent=-1;
			for (int i=0;i<firestage.length;i++) {
				if (firestage[i]) {
					firecurrent=i;
					break;
				}
			}
			if (firecurrent!=-1) {
				fireCounter++;
				fire(graphicsPanel, firecurrent);
			} else if (getPos().getX() > pship.getX()+dist) {
				fireCounter=0;
				Random rand=new Random();
				int randfire=rand.nextInt(firestage.length-1);
				firestage[randfire]=true;
				fire(graphicsPanel, randfire);
			} else {
				fireCounter=0;
				Random rand=new Random();
				int randfire=rand.nextInt(firestage.length);
				firestage[randfire]=true;
				fire(graphicsPanel, randfire);
			}
		} else {
			if (!explosion.update(getPos())) {
				System.out.println("clear");
				this.getTris().clear();
			}
		}
	}
	
	/**
	 * Simple AI to move in circles.
	 * @param graphicsPanel
	 */
	public void update1 (GraphicsPanel graphicsPanel) {
		if (getPos().getX() > graphicsPanel.getPlayerShip().getPos().getX()+10) {
			this.translate(new Vector(0, 10*Math.cos(theta)-getPos().getY(), 10*Math.sin(theta)-getPos().getZ()));
			this.setPos(new Vector(getPos().getX(), 10*Math.cos(theta), 10*Math.sin(theta)));
		} else {
			this.translate(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+10-getPos().getX(), 10*Math.cos(theta)-getPos().getY(), 10*Math.sin(theta)-getPos().getZ()));
			this.setPos(new Vector(graphicsPanel.getPlayerShip().getPos().getX()+10, 10*Math.cos(theta), 10*Math.sin(theta)));
		}
		theta += Math.PI/80;
		int firespeed;
		if (difficulty==0)
			firespeed=32;
		else if (difficulty==1)
			firespeed=20;
		else
			firespeed=14;
		fireCounter++;
		if (explosion == null) {
			if (fireCounter >= firespeed) {
				graphicsPanel.fireBullet(getPos().plus(new Vector(-3, 0, 0)), new Vector(-1, 0, 0), 0.3, true);
				fireCounter = 0;
			}
		} else {
			if (!explosion.update(getPos())) {
				System.out.println("clear");
				this.getTris().clear();
			}
		}
	}

	private void move (GraphicsPanel graphicsPanel, int stage) {
		switch (stage) {
		case 0: move0(graphicsPanel, initPos); break;
		case 1: move1(graphicsPanel); break;
		case 2: move2(graphicsPanel); break;
		case 3: move3(graphicsPanel); break;
		}
	}

	/**
	 * Moves in a circle.
	 * @param graphicsPanel
	 * @param init
	 */
	private void move0 (GraphicsPanel graphicsPanel, Vector init) {
		theta += Math.PI/80;
		this.translate(new Vector(pship.getX()+dist-getPos().getX(), d*(10-10*Math.cos(theta))-getPos().getY()+init.getY(), d*(10*Math.sin(theta))-getPos().getZ()+init.getZ()));
		this.setPos(new Vector(pship.getX()+dist, d*(10-10*Math.cos(theta))+init.getY(), d*(10*Math.sin(theta))+init.getZ()));
		if (moveCounter==159) {
			Arrays.fill(movestage, false);
		}
	}

	/**
	 * Moves up and down.
	 * @param graphicsPanel
	 */
	private void move1(GraphicsPanel graphicsPanel) {
		if (moveCounter<40) {
			this.translate(new Vector(pship.getX()+dist-getPos().getX(), d*10.0/40, 0));
			this.setPos(new Vector(pship.getX()+dist, d*10.0/40+getPos().getY(), getPos().getZ()));
		} else if (moveCounter<120) {
			this.translate(new Vector(pship.getX()+dist-getPos().getX(), -d*10.0/40, 0));
			this.setPos(new Vector(pship.getX()+dist, -d*10.0/40+getPos().getY(), getPos().getZ()));
		} else if (moveCounter<160) {
			this.translate(new Vector(pship.getX()+dist-getPos().getX(), d*10.0/40, 0));
			this.setPos(new Vector(pship.getX()+dist, d*10.0/40+getPos().getY(), getPos().getZ()));
		} else if (moveCounter==160) {
			Arrays.fill(movestage, false);
		}
	}

	/**
	 * Moves left and right.
	 * @param graphicsPanel
	 */
	private void move2(GraphicsPanel graphicsPanel) {
		if (moveCounter<40) {
			this.translate(new Vector(pship.getX()+dist-getPos().getX(), 0, d*10.0/40));
			this.setPos(new Vector(pship.getX()+dist, getPos().getY(), d*10.0/40+getPos().getZ()));
		} else if (moveCounter<120) {
			this.translate(new Vector(pship.getX()+dist-getPos().getX(), 0, -d*10.0/40));
			this.setPos(new Vector(pship.getX()+dist, getPos().getY(), -d*10.0/40+getPos().getZ()));
		} else if (moveCounter<160) {
			this.translate(new Vector(pship.getX()+dist-getPos().getX(), 0, d*10.0/40));
			this.setPos(new Vector(pship.getX()+dist, getPos().getY(), d*10.0/40+getPos().getZ()));
		} else if (moveCounter==160) {
			Arrays.fill(movestage, false);
		}
	}
	
	/**
	 * Stays still for 50 game ticks.
	 * @param graphicsPanel
	 */
	private void move3(GraphicsPanel graphicsPanel) {
		this.translate(new Vector(pship.getX()+dist-getPos().getX(),0,0));
		this.setPos(new Vector(pship.getX()+dist,getPos().getY(),getPos().getZ()));
		if (moveCounter==50) {
			Arrays.fill(movestage, false);
		}
	}

	private void fire (GraphicsPanel graphicsPanel, int stage) {
		switch (stage) {
		case 0: fire0(graphicsPanel); break;
		case 1: fire1(graphicsPanel); break;
		case 2: fire2(graphicsPanel); break;
		case 3: fire3(graphicsPanel); break;
		}
	}

	/**
	 * Fires 8 consecutive bullets towards the player
	 * @param graphicsPanel
	 */
	private void fire0 (GraphicsPanel graphicsPanel) {
		//System.out.println(fireCounter);
		int firespeed;
		if (difficulty==0)
			firespeed=20;
		else if (difficulty==1)
			firespeed=15;
		else
			firespeed=10;
		if (fireCounter%firespeed==0) {
			graphicsPanel.fireBullet(getPos().plus(new Vector(-3, 0, 0)), 
					pship.plus(new Vector(30,0,0)).minus(getPos().plus(new Vector(-3,0,0))).unit().scale(0.5), 0.3, true);
		}
		if (fireCounter==224) {
			Arrays.fill(firestage, false);
			//System.out.println("firestage");
		}
	}

	/**
	 * Fires 8 consecutive bullets forwards
	 * @param graphicsPanel
	 */
	private void fire1 (GraphicsPanel graphicsPanel) {
		int firespeed;
		if (difficulty==0)
			firespeed=20;
		else if (difficulty==1)
			firespeed=15;
		else
			firespeed=10;
		if (fireCounter%firespeed==0) {
			graphicsPanel.fireBullet(getPos().plus(new Vector(-3, 0, 0)), new Vector(-1,0,0), 0.3, true);
		}
		if (fireCounter==224) {
			Arrays.fill(firestage, false);
		}
	}

	/**
	 * Fires nothing for set game ticks
	 * @param graphicsPanel
	 */
	private void fire2 (GraphicsPanel graphicsPanel) {
		if (fireCounter==84) {
			Arrays.fill(firestage, false);
		}
	}

	/**
	 * Fires a quick burst towards the player after a short time
	 * @param graphicsPanel
	 */
	private void fire3 (GraphicsPanel graphicsPanel) {
		int firespeed;
		if (difficulty==0)
			firespeed=7;
		else if (difficulty==1)
			firespeed=5;
		else
			firespeed=4;
		if (fireCounter>25&&fireCounter<55&&fireCounter%firespeed==0)
			graphicsPanel.fireBullet(getPos().plus(new Vector(-3, 0, 0)), 
					pship.plus(new Vector(30,0,0)).minus(getPos().plus(new Vector(-3,0,0))).unit().scale(0.5), 0.3, true);
		if (fireCounter==85) {
			Arrays.fill(firestage, false);
		}
	}

	public void destroy (GraphicsPanel graphicsPanel) {
		System.out.println("destroy");
		if (explosion == null) {
			explosion = new Explosion(this.getPos(), 10, 10);
			graphicsPanel.getMeshList().add(explosion);
		}
	}

}