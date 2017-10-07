package TankWar;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Missile {
	
	int x,y;
	direction dir;
	private TankWarFrame twf;
	boolean live = true;
	boolean good;
	public boolean isLive() {
		return live;
	}

	public static final int XSPEED = 20;
	public static final int YSPEED = 20;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] imgs;
	private static Map<String,Image> m = new HashMap<String,Image>();
	static {
			imgs = new Image[] { 
				tk.getImage(Explode.class.getClassLoader().getResource("images/MissileL.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/MissileLU.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/MissileD.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/MissileRU.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/MissileR.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/MissileRD.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/MissileU.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/MissileLD.gif")),
			};
			m.put("L", imgs[0]);
			m.put("LU", imgs[1]);
			m.put("U", imgs[2]);
			m.put("RU", imgs[3]);
			m.put("R", imgs[4]);
			m.put("RD", imgs[5]);
			m.put("D", imgs[6]);
			m.put("LD", imgs[7]);
	}
	public Missile(int x, int y, direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		
	}
	
	public Missile(int x, int y, direction dir,boolean good,TankWarFrame twf) {
		this(x,y,dir);
		this.good = good;
		this.twf = twf;
		
	}
	public void draw(Graphics g) {
		if(!isLive()) return;
		switch(dir) {
		case L:
			g.drawImage(m.get("L"), x, y, null);
			break;
		case LU:
			g.drawImage(m.get("LU"), x, y, null);
			break;
		case U:
			g.drawImage(m.get("U"), x, y, null);
			break;
		case RU:
			g.drawImage(m.get("RU"), x, y, null);
			break;
		case R:
			g.drawImage(m.get("R"), x, y, null);
			break;
		case RD:
			g.drawImage(m.get("RD"), x, y, null);
			break;
		case D:
			g.drawImage(m.get("D"), x, y, null);
			break;
		case LD:
			g.drawImage(m.get("LD"), x, y, null);
			break;
		}	
		move();
	}

	private void move() {
		switch(dir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		}	
		if(x<0 || y <0 || x>TankWarFrame.GAME_WIDTH || y>TankWarFrame.GAME_HEIGHT) {
			live = false;
			twf.missiles.remove(this);
		}
	}
	public Rectangle getRect() {
		return new Rectangle(x,y,10,10);
	}
	public boolean hitTank(Tank t) {
		if(this.isGood() != t.isGood() && t.getRect().intersects(getRect()) && t.isLive() && live) {
			Explode e = new Explode(x,y,twf);
			twf.explodes.add(e);
			twf.missiles.remove(this);
			if(t.isGood()) {
				t.life -= 20;
				if(t.life <= 0) {
					t.live = false;
				}
			}
			else t.live = false;
			return true;
		}
		return false;
		
	
	}
	
	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public void hitTanks(List<Tank> enemyTanks) {
		for(int i=0; i<enemyTanks.size(); i++) {
			if(enemyTanks.get(i).getRect().intersects(getRect())) {
				this.hitTank(enemyTanks.get(i));
			}
		}
	}
	
	public void hitWall(Wall w) {
		if(w.getRect().intersects(this.getRect())) {
			this.live = false;
			twf.missiles.remove(this);
		}
	}
	
}
