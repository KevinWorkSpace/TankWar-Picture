package TankWar;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 * 这是坦克
 * @author 11612431 赵禹开
 *@date 2017年9月24日 下午7:36:10
 */
public class Tank {
	public static final int XSPEED = 5;
	public static final int YSPEED = 5 ;
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	int x,y;
	int oldX,oldY;
	int life = 100;
	TankWarFrame twf;
	boolean u = false, d = false, l = false, r = false;
	boolean good = true;
	direction dir = direction.STOP;
	direction ptdir = direction.D;
	boolean live = true;
	private static Random r1 = new Random();
	public static int step = r1.nextInt(12) + 3;
	direction[] dirs = dir.values();
	BloodBar bb = new BloodBar();
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] imgs;
	private static Map<String,Image> m = new HashMap<String,Image>();
	static {
		imgs = new Image[] {	tk.getImage(Explode.class.getClassLoader().getResource("images/TankL.gif")),
								tk.getImage(Explode.class.getClassLoader().getResource("images/TankLU.gif")),
								tk.getImage(Explode.class.getClassLoader().getResource("images/TankU.gif")),
								tk.getImage(Explode.class.getClassLoader().getResource("images/TankRU.gif")),
								tk.getImage(Explode.class.getClassLoader().getResource("images/TankR.gif")),
								tk.getImage(Explode.class.getClassLoader().getResource("images/TankRD.gif")),
								tk.getImage(Explode.class.getClassLoader().getResource("images/TankD.gif")),
								tk.getImage(Explode.class.getClassLoader().getResource("images/TankLD.gif")),
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
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public Tank(int x, int y,boolean good) {
		super();
		this.x = x;
		this.y = y;
		this.good = good;
	}
	
	public Tank(int x, int y, boolean good, direction dir, TankWarFrame twf) {
		this(x,y,good);
		this.dir = dir;
		this.twf = twf;
	}
	
	public void draw(Graphics g) {
		if(!isLive()) {
			twf.enemyTanks.remove(this);
			return;
		}
		
		if(!good) {
			if(step==0) {
			step = r1.nextInt(12) +3;
			int rn = r1.nextInt(8);
			dir = dirs[rn]; 
			}
			step--;
			if(r1.nextInt(40) >38) this.fire();
		}
		move();
		if(dir != direction.STOP) {
			ptdir = dir;
		}
		drawPt(g);
		if(good) bb.draw(g);;
	}
	
	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public void drawPt(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		switch(ptdir) {
		case L:
			g.drawImage(m.get("L"), x, y,null);
			break;
		case LU:
			g.drawImage(m.get("LU"), x, y,null);
			break;
		case U:
			g.drawImage(m.get("U"), x, y,null);
			break;
		case RU:
			g.drawImage(m.get("RU"), x, y,null);
			break;
		case R:
			g.drawImage(m.get("R"), x, y,null);
			break;
		case RD:
			g.drawImage(m.get("RD"), x, y,null);
			break;
		case D:
			g.drawImage(m.get("D"), x, y,null);
			break;
		case LD:
			g.drawImage(m.get("LD"), x, y,null);
			break;
		}
		g.setColor(c);
		
		
	}
	
	public void move() {
		this.oldX = x;
		this.oldY = y;
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
		case STOP:
			break;
		}
		if(x < 0) {
			x = 0;
		}
		if(y < 30) {
			y = 30;
		}
		if(x + Tank.WIDTH > TankWarFrame.GAME_WIDTH) {
			x = TankWarFrame.GAME_WIDTH - Tank.WIDTH;
		}
		if(y + Tank.HEIGHT > TankWarFrame.GAME_HEIGHT) {
			y = TankWarFrame.GAME_HEIGHT - Tank.HEIGHT;
		}
		
		
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		
			case KeyEvent.VK_RIGHT:
				r = true;
				break;
			case KeyEvent.VK_LEFT:
				l = true;
				break;
			case KeyEvent.VK_UP:
				u = true;
				break;
			case KeyEvent.VK_DOWN: 
				d = true;
				break;
			case KeyEvent.VK_F2:
				this.live = true;
				this.life = 100;
		}
		locateDirection();
	}
	
	public Missile fire() {
		if(!live) return null;
		Missile m = new Missile(x,y,ptdir,good,twf);
		twf.missiles.add(m);
		return m;
	}
	
	public Missile fire(direction dir) {
		if(!live) return null;
		Missile m = new Missile(x,y,dir,good,twf);
		twf.missiles.add(m);
		return m;
	}
	
	public void superfire() {
		for(int i=0; i<8; i++) {
			fire(dirs[i]);
		}
	}

	public void locateDirection() {
		if(l && !r && !u && !d ) {
			dir = direction.L;
		}
		else if(l && !r && u && !d ) {
			dir = direction.LU;
		}
		else if(!l && !r && u && !d ) {
			dir = direction.U;
		}
		else if(!l && r && u && !d ) {
			dir = direction.RU;
		}
		else if(!l && r && !u && !d ) {
			dir = direction.R;
		}
		else if(!l && r && !u && d ) {
			dir = direction.RD;
		}
		else if(!l && !r && !u && d ) {
			dir = direction.D;
		}
		else if(l && !r && !u && d) {
			dir = direction.LD;
		}
		else if(!l && !r && !u && !d ) {
			dir = direction.STOP;
		}
		
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
			case KeyEvent.VK_CONTROL:
			    fire();
				break;
			case KeyEvent.VK_RIGHT:
				r = false;
				break;
			case KeyEvent.VK_LEFT:
				l = false;
				break;
			case KeyEvent.VK_UP:
				u = false;
				break;
			case KeyEvent.VK_DOWN: 
				d = false;
				break;
			case KeyEvent.VK_A:
				superfire();
				break;
		}
		locateDirection();
		
	}
	public Rectangle getRect() {
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	
	public void hitWall(Wall w) {
		
		if(this.getRect().intersects(w.getRect())) {
			x = oldX;
			y = oldY;
		}
	}
	
	public void hitTank(java.util.List<Tank> tanks) {
		for(int i=0; i<tanks.size(); i++) {
			Tank t = tanks.get(i);
			if(this != t) {
				if(this.getRect().intersects(t.getRect())) {
					x = oldX;
					y = oldY;
					t.x = t.oldX;
					t.y = t.oldY;
				}
			}
		}
	}
	
	public void eatBlood(Blood b) {
		if(this.live && b.live && this.getRect().intersects(b.getRect())) {
			this.life = 100;
			b.live = false;
		}
	}
	
	public class BloodBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y-10, WIDTH, 10);
			g.fillRect(x, y-10, WIDTH*life/100, 10);
			g.setColor(c);
		}
	}
	
}
