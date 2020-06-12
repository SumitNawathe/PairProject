import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

import javax.imageio.*;
import javax.swing.*;

public class IntroScreen extends JPanel {
	private int SCREEN_WIDTH, SCREEN_HEIGHT;
	private double scaleX, scaleY, scale;
	private JButton startSave1, startSave2, startSave3;
	private File clearSave;
	private BufferedImage title;
	private Image title1;
	private byte[] cs;

	public IntroScreen (GameFrame gameFrame, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
		this.SCREEN_WIDTH = SCREEN_WIDTH;
		this.SCREEN_HEIGHT = SCREEN_HEIGHT;
		scaleX=SCREEN_WIDTH/1200.0;
		scaleY=SCREEN_HEIGHT/900.0;
		this.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setLayout(null);		
		
		clearSave=new File("SaveFiles/ClearSave.txt");
		try {
			cs=Files.readAllBytes(clearSave.toPath());
		} catch (Exception e) {System.out.println("Failed to read Clear Save");}
		//System.out.println("cs: "+new String(new byte[] {cs[11]}));
		
		startSave1=setButton(gameFrame, compareFiles(1), 1);
		this.add(startSave1);

		startSave2=setButton(gameFrame, compareFiles(2), 2);
		this.add(startSave2);

		startSave3=setButton(gameFrame, compareFiles(3), 3);
		this.add(startSave3);
		try {
			title=ImageIO.read(new File("Textures/title.png"));

		} catch (Exception e) {}
		int xscale=480, yscale=270;
		scale=1041.0/162;
		//title1 = title.getScaledInstance((int)(xscale*scaleX), (int)(yscale*scaleY), Image.SCALE_SMOOTH);
	}

	private boolean compareFiles(int save) {
		File saveFile=new File("SaveFiles/SaveFile"+save+".txt");
		try {
			byte[] fileBytes=Files.readAllBytes(saveFile.toPath());
			return Arrays.equals(cs, fileBytes);
		} catch (Exception e) {System.out.println("Failed to read Save File "+save);}
		return false;
	}

	private JButton setButton(GameFrame gameFrame, boolean compare, int save) {
		JButton ret=new JButton("GAME "+save);
		if (compare) {
			ret.addActionListener(new ActionListener () {
				public void actionPerformed (ActionEvent event) {
					gameFrame.goToDifficultyScreen(save);
				}
			});	
		} else {
			ret.addActionListener(new ActionListener () {
				public void actionPerformed (ActionEvent event) {
					gameFrame.goToPurgatory(save);
				}
			});	
		}
		ret.setSize(new Dimension(SCREEN_WIDTH/5, SCREEN_HEIGHT/20));
		ret.setLocation(save*SCREEN_WIDTH/4-SCREEN_WIDTH/5/2, 4*SCREEN_HEIGHT/5-SCREEN_HEIGHT/20/2);
		return ret;
	}

	public void paintComponent (Graphics g) {
		try {
			g.drawImage(ImageIO.read(new File("Textures/CoverImage.png")), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
			g.drawImage(title, 0, (int)(30*scaleY), (int)((1041+20*scale)*scaleX), (int)((162+20)*scaleY), null);
		} catch (Exception e) {}
	}
}