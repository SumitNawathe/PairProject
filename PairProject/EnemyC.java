import java.util.Arrays;
import java.util.Random;

public class EnemyC extends Enemy {
	public double theta;
	public double fireCounter;
	private Vector pship, initPos;
	private boolean[] firestage, movestage;
	private int moveCounter, dist, d, ai, difficulty;
	public EnemyC (Vector pos, int dist, int ai, int difficulty) {
		super(pos, dist, ai, difficulty);
		firestage=new boolean[4];
		movestage=new boolean[4];
		this.dist=dist;
		this.difficulty=difficulty;
		setPos(pos);
		try {
			setTris(Mesh.loadFromObjFile("Models/Ship Model 2.obj", "Textures/Ship Model 2 Map.png").getTris());
		} catch (Exception e) { System.out.println("Error loading smallbulletsphere2.obj"); }
		translate(pos);
	}

	public void update0 (GraphicsPanel graphicsPanel) {
		pship=graphicsPanel.getPlayerShip().getPos();
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

	private void move (GraphicsPanel graphicsPanel, int stage) {
		switch (stage) {
		case 0: move0(graphicsPanel, initPos); break;
		case 1: move1(graphicsPanel); break;
		case 2: move2(graphicsPanel); break;
		case 3: move3(graphicsPanel); break;
		}
	}

	private void move0 (GraphicsPanel graphicsPanel, Vector init) {
		theta += Math.PI/60;
		this.translate(new Vector(pship.getX()+dist-getPos().getX(), d*(10-10*Math.cos(theta))-getPos().getY()+init.getY(), d*(10*Math.sin(theta))-getPos().getZ()+init.getZ()));
		this.setPos(new Vector(pship.getX()+dist, d*(10-10*Math.cos(theta))+init.getY(), d*(10*Math.sin(theta))+init.getZ()));
		if (moveCounter==119) {
			Arrays.fill(movestage, false);
		}
	}

	private void move1(GraphicsPanel graphicsPanel) {
		if (moveCounter<30) {
			this.translate(new Vector(pship.getX()+dist-getPos().getX(), d*10.0/30, 0));
			this.setPos(new Vector(pship.getX()+dist, d*10.0/30+getPos().getY(), getPos().getZ()));
		} else if (moveCounter<90) {
			this.translate(new Vector(pship.getX()+dist-getPos().getX(), -d*10.0/30, 0));
			this.setPos(new Vector(pship.getX()+dist, -d*10.0/30+getPos().getY(), getPos().getZ()));
		} else if (moveCounter<120) {
			this.translate(new Vector(pship.getX()+dist-getPos().getX(), d*10.0/30, 0));
			this.setPos(new Vector(pship.getX()+dist, d*10.0/30+getPos().getY(), getPos().getZ()));
		} else if (moveCounter==120) {
			Arrays.fill(movestage, false);
		}
	}

	private void move2(GraphicsPanel graphicsPanel) {
		if (moveCounter<30) {
			this.translate(new Vector(pship.getX()+dist-getPos().getX(), 0, d*10.0/30));
			this.setPos(new Vector(pship.getX()+dist, getPos().getY(), d*10.0/30+getPos().getZ()));
		} else if (moveCounter<90) {
			this.translate(new Vector(pship.getX()+dist-getPos().getX(), 0, -d*10.0/30));
			this.setPos(new Vector(pship.getX()+dist, getPos().getY(), -d*10.0/30+getPos().getZ()));
		} else if (moveCounter<120) {
			this.translate(new Vector(pship.getX()+dist-getPos().getX(), 0, d*10.0/30));
			this.setPos(new Vector(pship.getX()+dist, getPos().getY(), d*10.0/30+getPos().getZ()));
		} else if (moveCounter==120) {
			Arrays.fill(movestage, false);
		}
	}

	private void move3(GraphicsPanel graphicsPanel) {
		this.translate(new Vector(pship.getX()+dist-getPos().getX(),0,0));
		this.setPos(new Vector(pship.getX()+dist,getPos().getY(),getPos().getZ()));
		if (moveCounter==25) {
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

	private void fire0 (GraphicsPanel graphicsPanel) {
		//System.out.println(fireCounter);
		int firespeed;
		if (difficulty==0)
			firespeed=30;
		else if (difficulty==1)
			firespeed=20;
		else
			firespeed=15;
		if (fireCounter%firespeed==0) {
			graphicsPanel.fireBullet(getPos().plus(new Vector(-3, 0, 0)), 
					pship.plus(new Vector(30,0,0)).minus(getPos().plus(new Vector(-3,0,0))).unit().scale(0.5), 0.3, true);
		}
		if (fireCounter==224) {
			Arrays.fill(firestage, false);
			//System.out.println("firestage");
		}
	}

	private void fire1 (GraphicsPanel graphicsPanel) {
		int firespeed;
		if (difficulty==0)
			firespeed=30;
		else if (difficulty==1)
			firespeed=20;
		else
			firespeed=15;
		if (fireCounter%firespeed==0) {
			graphicsPanel.fireBullet(getPos().plus(new Vector(-3, 0, 0)), new Vector(-1,0,0), 0.3, true);
		}
		if (fireCounter==224) {
			Arrays.fill(firestage, false);
		}
	}

	private void fire2 (GraphicsPanel graphicsPanel) {
		if (fireCounter==84) {
			Arrays.fill(firestage, false);
		}
	}

	private void fire3 (GraphicsPanel graphicsPanel) {
		int firespeed;
		if (difficulty==0)
			firespeed=10;
		else if (difficulty==1)
			firespeed=7;
		else
			firespeed=5;
		if (fireCounter>25&&fireCounter<55&&fireCounter%firespeed==0)
			graphicsPanel.fireBullet(getPos().plus(new Vector(-3, 0, 0)), 
					pship.plus(new Vector(30,0,0)).minus(getPos().plus(new Vector(-3,0,0))).unit().scale(0.5), 0.3, true);
		if (fireCounter==85) {
			Arrays.fill(firestage, false);
		}
	}
}
