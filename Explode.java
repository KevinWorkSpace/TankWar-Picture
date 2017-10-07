package TankWar;

import java.awt.*;
public class Explode {
	int x,y;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] imgs = {
			tk.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/9.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/10.gif"))
	};
	int step = 0;
	TankWarFrame twf;
	boolean live = true;
	
	public Explode(int x, int y, TankWarFrame twf) {
		this.x = x;
		this.y = y;
		this.twf = twf;
	}
	
	public void draw(Graphics g) {
		if(!live) return;
		if(step == imgs.length) {
			live = false;
			twf.explodes.remove(this);
			step = 0;
			return;
		}
		g.drawImage(imgs[step], x, y, null);
		step++;
	}
	
}
