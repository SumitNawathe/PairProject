import java.util.Arrays;
import java.util.Random;

public class BossFrame extends SpaceShip {
	private double theta;
	public double getTheta () { return theta; }
	private double[][] rotMat;
	private int fireMod;
	private int[] fireCounters, fireCurrents;
	private boolean[][] firestages;
	private Vector pship;

	public BossFrame (Game game) {
		setPos(new Vector(game.getPlayerShip().getPos().getX()+20, 0, 0));
		try {
			setTris(Mesh.loadFromObjFile("Models/BossFrame1.obj", "Textures/BorgImage2.png").getTris());
			//			setTris(Mesh.loadFromObjFile("Models/BossWeaknessModel.obj", "Textures/BossWeakness.png").getTris());
			//			setTris(Mesh.loadFromObjFile("Models/Ship Model 4.obj", "Textures/Ship Model 4 Map.png").getTris());
		} catch (Exception e) { System.out.println("Error loading smallbulletsphere2.obj"); }

		for (Triangle tri : getTris())
			tri.setVerts(new Vector[] {tri.getVert1().scale(5), 
					tri.getVert2().scale(5), 
					tri.getVert3().scale(5)});

		translate(new Vector(game.getPlayerShip().getPos().getX()+20, 0, 0));
		setCollisionRadius(0.0);
		rotMat = Matrix.getRotMatX(Math.PI/2/120);
		firestages=new boolean[4][4];
		fireCurrents=new int[4];
		fireCounters=new int[4];
		pship=game.getPlayerShip().getPos();
	}

	public void update(Game game) {
		translate(getPos().clone().scale(-1));
		for (Triangle tri : getTris())
			tri.setVerts(new Vector[] {Matrix.multMatVec(rotMat, tri.getVert1()), 
					Matrix.multMatVec(rotMat, tri.getVert2()), 
					Matrix.multMatVec(rotMat, tri.getVert3())});
		translate(new Vector(game.getPlayerShip().getPos().getX()+20, 0, 0));
		setPos(new Vector(game.getPlayerShip().getPos().getX()+20, 0, 0));
		theta += Math.PI/2/120;

//		if (fireMod % 5 == 0) {
//			int firePos = (int) (4*Math.random());
//			game.fireBullet(getPos().clone().plus(new Vector(0, 5*3.5*Math.cos(firePos*Math.PI/2+theta+Math.PI/4), 
//				5*3.5*Math.sin(firePos*Math.PI/2+theta+Math.PI/4))), new Vector(-1, 0, 0), 1.5);
//		}
//		fireMod++;

		Arrays.fill(fireCurrents, -1);
		for (int j=0;j<firestages.length;j++) {
			for (int i=0;i<firestages[0].length;i++) {
				if (firestages[j][i]) {
					fireCurrents[j]=i;
					break;
				}
			}
			if (fireCurrents[j]!=-1) {
				fireCounters[j]++;
				fire(game, j, fireCurrents[j]);
			} else {
				fireCounters[j]=0;
				Random rand=new Random();
				int randfire=rand.nextInt(4);
				randfire=0;
				firestages[j][randfire]=true;
				fire(game, j, randfire);
			}
		}
	}

	private void fire(Game game, int firePos, int stage) {
		switch(stage) {
		case 0: fire0(game, firePos); break;
		case 1: fire1(game, firePos); break;
		case 2: fire2(game, firePos); break;
		case 3: fire3(game, firePos); break;
		}
	}
	
	private void fire0 (Game game, int firePos) {
		//System.out.println(fireCounter);
		if (fireCounters[firePos]%25==0) {
			//if (firePos==0)
			//System.out.println(pship.plus(new Vector(30,0,0)).minus(getPos().clone().plus(new Vector(0, 5*3.5*Math.cos(firePos*Math.PI/2+theta+Math.PI/4), 5*3.5*Math.sin(firePos*Math.PI/2+theta+Math.PI/4)))).unit().scale(0.5));
			game.fireBullet(getPos().clone().plus(new Vector(0, 5*3.5*Math.cos(firePos*Math.PI/2+theta+Math.PI/4), 5*3.5*Math.sin(firePos*Math.PI/2+theta+Math.PI/4))), 
					pship.plus(new Vector(30,0,0)).minus(getPos().clone().plus(new Vector(0, 5*3.5*Math.cos(firePos*Math.PI/2+theta+Math.PI/4), 5*3.5*Math.sin(firePos*Math.PI/2+theta+Math.PI/4)))).unit().scale(0.5), 0.3);
		}
		if (fireCounters[firePos]==224) {
			Arrays.fill(firestages[firePos], false);
			//System.out.println("firestage");
		}
	}

	private void fire1 (Game game, int firePos) {
		if (fireCounters[firePos]%25==0) {
			game.fireBullet(getPos().clone().plus(new Vector(0, 5*3.5*Math.cos(firePos*Math.PI/2+theta+Math.PI/4), 5*3.5*Math.sin(firePos*Math.PI/2+theta+Math.PI/4))),
					new Vector(-1,0,0), 0.3);
		}
		if (fireCounters[firePos]==224) {
			Arrays.fill(firestages[firePos], false);
		}
	}

	private void fire2 (Game game, int firePos) {
		if (fireCounters[firePos]==124) {
			Arrays.fill(firestages[firePos], false);
		}
	}

	private void fire3 (Game game, int firePos) {
		if (fireCounters[firePos]>50&&fireCounters[firePos]<80&&fireCounters[firePos]%5==0)
			game.fireBullet(getPos().clone().plus(new Vector(0, 5*3.5*Math.cos(firePos*Math.PI/2+theta+Math.PI/4), 5*3.5*Math.sin(firePos*Math.PI/2+theta+Math.PI/4))), 
					pship.plus(new Vector(30,0,0)).minus(getPos().plus(new Vector(-3,0,0))).unit().scale(0.5), 0.3);
		if (fireCounters[firePos]==119) {
			Arrays.fill(firestages[firePos], false);
		}
	}
	
	public void destroy(Game game) {

	}
}
