import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class CannonScreen extends JPanel {
	private int SCREEN_WIDTH, SCREEN_HEIGHT;
	private double scaleX, scaleY;
	private BufferedImage shipCannon;
	private Image shipCannon1;
	private JButton okBtn;

	public CannonScreen (GameFrame gameFrame, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
		this.SCREEN_WIDTH=SCREEN_WIDTH;
		this.SCREEN_HEIGHT=SCREEN_HEIGHT;
		scaleX=(double)SCREEN_WIDTH/1200;
		scaleY=(double)SCREEN_HEIGHT/900;
		
		this.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setLayout(null);

		try {
			shipCannon=ImageIO.read(new File("Textures/Ship Charge.png"));

		} catch (Exception e) {}
		int xscale=480, yscale=270;
		shipCannon1 = shipCannon.getScaledInstance((int)(xscale*scaleX), (int)(yscale*scaleY), Image.SCALE_SMOOTH);
		
		okBtn = new JButton("Understood.");
		okBtn.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent event) {
					gameFrame.goToLevelSelectScreen(gameFrame.CURRENT_SAVEDATA_LOCATION);
			}
		});
		this.add(okBtn);
		okBtn.setSize(new Dimension(4*SCREEN_WIDTH/21, 2*SCREEN_HEIGHT/21));
		okBtn.setLocation(SCREEN_WIDTH/2-2*SCREEN_WIDTH/21, 17*SCREEN_HEIGHT/21);
	}
	
	public void paintComponent (Graphics g) {
		Graphics2D g2d=(Graphics2D) g;
		g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
		g2d.setColor(Color.gray);
		g2d.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		g2d.setColor(Color.black);
		g2d.drawImage(shipCannon1, (int)(360*scaleX), (int)(80*scaleY), null);
		g2d.drawString("New Ability Unlocked:", (int)(380*scaleX), (int)(50*scaleY));
		g2d.drawString("Cannon", (int)(530*scaleX), (int)(400*scaleY));
		g2d.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		g2d.drawString("Hold down the D key for 0.3 seconds to charge.", (int)(260*scaleX), (int)(520*scaleY));
		g2d.drawString("Release the D key to fire a devastating cannonball.", (int)(220*scaleX), (int)(560*scaleY));
		g2d.drawString("(Note: Uses more energy than regular bullets)", (int)(260*scaleX), (int)(600*scaleY));


	}
}
