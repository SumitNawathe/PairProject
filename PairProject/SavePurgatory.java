import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SavePurgatory extends JPanel {
	private int save, SCREEN_WIDTH, SCREEN_HEIGHT;
	private double scaleX, scaleY;
	private ArrayList<String> texts;
	private JButton cont, clear, back;
	public SavePurgatory (GameFrame gameFrame, int SCREEN_WIDTH, int SCREEN_HEIGHT, int save) {
		this.SCREEN_WIDTH=SCREEN_WIDTH;
		this.SCREEN_HEIGHT=SCREEN_HEIGHT;
		scaleX=SCREEN_WIDTH/1200.0;
		scaleY=SCREEN_HEIGHT/900.0;
		this.save=save;
		this.setBackground(Color.gray);
		this.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setLayout(null);
		texts=saveText();
		cont=new JButton("CONTINUE SAVE");
		cont.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent event) {
					gameFrame.goToLevelSelectScreen("SaveFiles/SaveFile"+save+".txt");
			}
		});
		this.add(cont);
		cont.setSize(new Dimension(4*SCREEN_WIDTH/21, 2*SCREEN_HEIGHT/21));
		cont.setLocation(16*SCREEN_WIDTH/21, 17*SCREEN_HEIGHT/21);
		
		clear=new JButton("CLEAR SAVE");
		clear.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent event) {
				FileInputStream in = null;
				FileOutputStream out = null;
			    	try{
			    	    File inFile =new File("SaveFiles/ClearSave.txt");
			    	    File outFile =new File("SaveFiles/SaveFile"+save+".txt");
			 
			    	    in = new FileInputStream(inFile);
			    	    out = new FileOutputStream(outFile);
			 
			    	    byte[] buf = new byte[1024];
			 
			    	    int length;
			    	    while ((length = in.read(buf)) > 0){
			    	    	out.write(buf, 0, length);
			    	    }
			    	    in.close();
			    	    out.close();
			    	} catch (Exception e) {System.out.println("Clear Save Failed.");}
				gameFrame.goToIntroScreen();
			}
		});
		this.add(clear);
		clear.setSize(new Dimension(4*SCREEN_WIDTH/21, 2*SCREEN_HEIGHT/21));
		clear.setLocation(SCREEN_WIDTH/21-(int)(20*scaleX), 17*SCREEN_HEIGHT/21);
		
		back=new JButton("BACK");
		back.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent event) {
				gameFrame.goToIntroScreen();
			}
		});
		this.add(back);
		back.setSize(new Dimension(3*SCREEN_WIDTH/21, SCREEN_HEIGHT/21));
		back.setLocation(9*SCREEN_WIDTH/21-(int)(20*scaleX), 17*SCREEN_HEIGHT/21+(int)(15*scaleY));
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, (int)(26*scaleX)));
		g.drawString("Save Game "+save, (int)(500*scaleX), (int)(50*scaleY));
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, (int)(16*scaleX)));
		for (int i=0;i<texts.size();i++) {
			g.drawString(texts.get(i), 6*SCREEN_WIDTH/21+(int)(20*scaleX), 7*SCREEN_HEIGHT/21+16*i);
		}
	}

	private ArrayList<String> saveText() {
		ArrayList<String> saves=new ArrayList<String>();
		try {
			File saveFile = new File("SaveFiles/SaveFile"+save+".txt");
			Scanner scan = new Scanner(saveFile);
			while (scan.hasNextLine()) {
				saves.add(scan.nextLine());
			}
			scan.close();
		} catch (Exception e) {}
		ArrayList<String> ret=new ArrayList<String>();
		for (int i=0;i<saves.size();i++) {
			String text=saves.get(i);
			String[] texts=text.split("\\s+");
			boolean complete=Boolean.parseBoolean(texts[0]);
			String score=texts[1];
			if (complete)
				ret.add("Level: "+(i+1)+"   Status: Complete    Score: "+score);
			else
				ret.add("Level: "+(i+1)+"   Status: Incomplete  Score: "+score);
		}
		return ret;
	}
}
