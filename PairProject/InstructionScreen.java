import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class InstructionScreen extends JPanel {
	private int SCREEN_WIDTH, SCREEN_HEIGHT;
	private double scaleX, scaleY;
	private BufferedImage shipBack, shipRocket, shipRing;
	private Image shipBack1, shipRocket1, shipRing1;
	private JButton startButton;

	public InstructionScreen (GameFrame gameFrame, int SCREEN_WIDTH, int SCREEN_HEIGHT, int difficulty, int abilityState, boolean canRoll) {
		this.SCREEN_WIDTH=SCREEN_WIDTH;
		this.SCREEN_HEIGHT=SCREEN_HEIGHT;
		scaleX=(double)SCREEN_WIDTH/1200;
		scaleY=(double)SCREEN_HEIGHT/900;
		
		this.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setLayout(null);

		try {
			shipBack=ImageIO.read(new File("Textures/Ship Back.png"));
			shipRocket=ImageIO.read(new File("Textures/Ship Rocket.png"));
			shipRing=ImageIO.read(new File("Textures/Ship Ring.png"));
		} catch (Exception e) {}
		int xscale=480, yscale=270;
		shipBack1 = shipBack.getScaledInstance((int)(xscale*scaleX), (int)(yscale*scaleY), Image.SCALE_SMOOTH);
		shipRocket1 = shipRocket.getScaledInstance((int)(xscale*scaleX), (int)(yscale*scaleY), Image.SCALE_SMOOTH);
		shipRing1 = shipRing.getScaledInstance((int)(xscale*scaleX), (int)(yscale*scaleY), Image.SCALE_SMOOTH);
//		this.setLayout(new GridLayout(0,2));
//		JLabel pic1=new JLabel(new ImageIcon(shipBack));
//		JLabel pic2=new JLabel(new ImageIcon(shipRocket));
//		JLabel pic3=new JLabel(new ImageIcon(shipFire));
//		
//		JLabel text1=new JLabel("Hello");
//		JLabel text2=new JLabel("Hello");
//		JLabel text3=new JLabel("Hello");
//
//		add(pic1);
//		add(text1);
//		add(pic2);
//		add(text2);
//		add(pic3);
//		add(text3);
		startButton = new JButton("BEGIN!");
		startButton.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent event) {
					gameFrame.startLevel(new AgilityLevel1(), difficulty, abilityState, canRoll);
			}
		});
		this.add(startButton);
		startButton.setSize(new Dimension(4*SCREEN_WIDTH/21, 2*SCREEN_HEIGHT/21));
		startButton.setLocation(16*SCREEN_WIDTH/21, 17*SCREEN_HEIGHT/21);
	}
	
	public void paintComponent (Graphics g) {
		Graphics2D g2d=(Graphics2D) g;
		g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		g2d.setColor(Color.gray);
		g2d.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		g2d.setColor(Color.black);
		g2d.drawImage(shipBack1, (int)(10*scaleX), (int)(10*scaleY), null);
		g2d.drawImage(shipRocket1, (int)(10*scaleX), (int)(290*scaleY), null);
		g2d.drawImage(shipRing1, (int)(10*scaleX), (int)(570*scaleY), null);
		g2d.drawString("Arrow keys to move", (int)(550*scaleX), (int)(140*scaleY));
		g2d.drawString("Space to boost", (int)(540*scaleX), (int)(400*scaleY));
		g2d.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		g2d.drawString("(Move forwards twice as fast but uses energy)", (int)(540*scaleX), (int)(440*scaleY));
		g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		g2d.drawString("Move through rings to collect them.", (int)(540*scaleX), (int)(640*scaleY));
		g2d.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		g2d.drawString("Rings replenish your energy, which you need to fire.", (int)(540*scaleX), (int)(673*scaleY));
		g2d.drawString("In agility levels, rings also reward points.", (int)(540*scaleX), (int)(703*scaleY));

	}
}
