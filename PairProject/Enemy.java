import java.util.Arrays;
import java.util.Random;

public class Enemy extends SpaceShip {
	
	public Explosion explosion;
	public double theta;
	public double fireCounter;
	private Vector pship, initPos;
	private boolean[] firestage, movestage;
	private int moveCounter, dist, d, ai;
	
	public Enemy (Vector pos, int dist, int ai) {
		super();
		setCollisionRadius(2);
		firestage=new boolean[4];
		movestage=new boolean[4];
		this.dist=dist;
		this.ai=ai;
	}
	
	public void update (Game game) {
		switch (ai) {
		case 0: update0(game); break;
		case 1: update1(game); break;
		}
	}
	
	/**
	 * Standard difficulty AI.
	 * @param game
	 */
	public void update0 (Game game) {
		pship=game.getPlayerShip().getPos();
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
					move(game, movecurrent);
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
					move(game, randmove);
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
				fire(game, firecurrent);
			} else if (getPos().getX() > pship.getX()+dist) {
				fireCounter=0;
				Random rand=new Random();
				int randfire=rand.nextInt(firestage.length-1);
				firestage[randfire]=true;
				fire(game, randfire);
			} else {
				fireCounter=0;
				Random rand=new Random();
				int randfire=rand.nextInt(firestage.length);
				firestage[randfire]=true;
				fire(game, randfire);
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
	 * @param game
	 */
	public void update1 (Game game) {
		if (getPos().getX() > game.getPlayerShip().getPos().getX()+10) {
			this.translate(new Vector(0, 10*Math.cos(theta)-getPos().getY(), 10*Math.sin(theta)-getPos().getZ()));
			this.setPos(new Vector(getPos().getX(), 10*Math.cos(theta), 10*Math.sin(theta)));
		} else {
			this.translate(new Vector(game.getPlayerShip().getPos().getX()+10-getPos().getX(), 10*Math.cos(theta)-getPos().getY(), 10*Math.sin(theta)-getPos().getZ()));
			this.setPos(new Vector(game.getPlayerShip().getPos().getX()+10, 10*Math.cos(theta), 10*Math.sin(theta)));
		}
		theta += Math.PI/80;
		fireCounter++;
		if (explosion == null) {
			if (fireCounter >= 10) {
				game.fireBullet(getPos().plus(new Vector(-3, 0, 0)), new Vector(-1, 0, 0), 0.3);
				fireCounter = 0;
			}
		} else {
			if (!explosion.update(getPos())) {
				System.out.println("clear");
				this.getTris().clear();
			}
		}
	}

	private void move (Game game, int stage) {
		switch (stage) {
		case 0: move0(game, initPos); break;
		case 1: move1(game); break;
		case 2: move2(game); break;
		case 3: move3(game); break;
		}
	}

	/**
	 * Moves in a circle.
	 * @param game
	 * @param init
	 */
	private void move0 (Game game, Vector init) {
		theta += Math.PI/80;
		this.translate(new Vector(pship.getX()+dist-getPos().getX(), d*(10-10*Math.cos(theta))-getPos().getY(), d*(10*Math.sin(theta))-getPos().getZ()));
		this.setPos(new Vector(pship.getX()+dist, d*(10-10*Math.cos(theta))+init.getY(), d*(10*Math.sin(theta))+init.getZ()));
		if (moveCounter==159) {
			Arrays.fill(movestage, false);
		}
	}

	/**
	 * Moves up and down.
	 * @param game
	 */
	private void move1(Game game) {
		if (moveCounter<40) {
			this.translate(new Vector(pship.getX()+dist-getPos().getX(), d*10.0/40, 0));
			this.setPos(new Vector(pship.getX()+dist, d*10.0/40+getPos().getY(), 0));
		} else if (moveCounter<120) {
			this.translate(new Vector(pship.getX()+dist-getPos().getX(), -d*10.0/40, 0));
			this.setPos(new Vector(pship.getX()+dist, -d*10.0/40+getPos().getY(), 0));
		} else if (moveCounter<160) {
			this.translate(new Vector(pship.getX()+dist-getPos().getX(), d*10.0/40, 0));
			this.setPos(new Vector(pship.getX()+dist, d*10.0/40+getPos().getY(), 0));
		} else if (moveCounter==160) {
			Arrays.fill(movestage, false);
		}
	}

	/**
	 * Moves left and right.
	 * @param game
	 */
	private void move2(Game game) {
		if (moveCounter<40) {
			this.translate(new Vector(pship.getX()+dist-getPos().getX(), 0, d*10.0/40));
			this.setPos(new Vector(pship.getX()+dist, 0, d*10.0/40+getPos().getZ()));
		} else if (moveCounter<120) {
			this.translate(new Vector(pship.getX()+dist-getPos().getX(), 0, -d*10.0/40));
			this.setPos(new Vector(pship.getX()+dist, 0, -d*10.0/40+getPos().getZ()));
		} else if (moveCounter<160) {
			this.translate(new Vector(pship.getX()+dist-getPos().getX(), 0, d*10.0/40));
			this.setPos(new Vector(pship.getX()+dist, 0, d*10.0/40+getPos().getZ()));
		} else if (moveCounter==160) {
			Arrays.fill(movestage, false);
		}
	}
	
	/**
	 * Stays still for 50 game ticks.
	 * @param game
	 */
	private void move3(Game game) {
		this.translate(new Vector(pship.getX()+dist-getPos().getX(),0,0));
		this.setPos(new Vector(pship.getX()+dist,getPos().getY(),getPos().getZ()));
		if (moveCounter==50) {
			Arrays.fill(movestage, false);
		}
	}

	private void fire (Game game, int stage) {
		switch (stage) {
		case 0: fire0(game); break;
		case 1: fire1(game); break;
		case 2: fire2(game); break;
		case 3: fire3(game); break;
		}
	}

	/**
	 * Fires 8 consecutive bullets towards the player
	 * @param game
	 */
	private void fire0 (Game game) {
		//System.out.println(fireCounter);
		if (fireCounter%25==0) {
			game.fireBullet(getPos().plus(new Vector(-3, 0, 0)), 
					pship.plus(new Vector(30,0,0)).minus(getPos().plus(new Vector(-3,0,0))).unit().scale(0.5), 0.3);
		}
		if (fireCounter==224) {
			Arrays.fill(firestage, false);
			//System.out.println("firestage");
		}
	}

	/**
	 * Fires 8 consecutive bullets forwards
	 * @param game
	 */
	private void fire1 (Game game) {
		if (fireCounter%25==0) {
			game.fireBullet(getPos().plus(new Vector(-3, 0, 0)), new Vector(-1,0,0), 0.3);
		}
		if (fireCounter==224) {
			Arrays.fill(firestage, false);
		}
	}

	/**
	 * Fires nothing for 124 game ticks
	 * @param game
	 */
	private void fire2 (Game game) {
		if (fireCounter==124) {
			Arrays.fill(firestage, false);
		}
	}

	/**
	 * Fires a quick burst towards the player after a short time
	 * @param game
	 */
	private void fire3 (Game game) {
		if (fireCounter>50&&fireCounter<80&&fireCounter%5==0)
			game.fireBullet(getPos().plus(new Vector(-3, 0, 0)), 
					pship.plus(new Vector(30,0,0)).minus(getPos().plus(new Vector(-3,0,0))).unit().scale(0.5), 0.3);
		if (fireCounter==119) {
			Arrays.fill(firestage, false);
		}
	}

	public void destroy (Game game) {
		System.out.println("destroy");
		if (explosion == null) {
			explosion = new Explosion(this.getPos(), 10, 10);
			game.getMeshList().add(explosion);
		}
	}

}