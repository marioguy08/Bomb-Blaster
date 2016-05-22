import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.Font;
import java.io.InputStream;

public class Game extends BasicGame {
	private final static int XDIMENSION = 1400;
	private final static int YDIMENSION = 800;
	private final static int SHOTDELAY = 20;// not in milliseconds
	private Vector2f start;
	private Vector2f barrelStart = new Vector2f((XDIMENSION / 2) - 13, YDIMENSION - 110);
	private double speed = 1.7;
	private int delay = 0;
	private int score;
	private int chance;
	private ArrayList<Bullet> bullets1;
	private ArrayList<Bomb> bombs;
	private ArrayList<Explosion> exp;
	private Image crosshair;
	private Image tBase;
	private Image actualBase;
	private Image background;
	private boolean isLost;
	private TrueTypeFont font;
	private TrueTypeFont font2;
	private TrueTypeFont overFont;
	// private ParticleSystem system;
	// private ConfigurableEmitter emitter;
	Barrel barrel1 = new Barrel(barrelStart, XDIMENSION);
	Explosion explosion;

	public Game(String name) {
		super("Bomb Blaster");
	}

	public void init(GameContainer gc) throws SlickException {
		gc.setShowFPS(false);
		crosshair = new Image("images/Crosshair.png");
		tBase = new Image("images/bottomCircle.png");
		actualBase = new Image("images/ActualBase.png");
		background = new Image("images/background.png");
		explosion = new Explosion(0, 0);
		Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, true);

		// load font from a .ttf file
		try {
			InputStream inputStream = ResourceLoader.getResourceAsStream("images/karma_future.ttf");

			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(50f);
			font2 = new TrueTypeFont(awtFont2, true);
			awtFont2 = awtFont2.deriveFont(50f);
			overFont = new TrueTypeFont(awtFont2, true);

		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * Image image = new Image("images/particle.png"); system = new
		 * ParticleSystem(image,1500); File xmlFile = new
		 * File("images/particleConfig.xml"); try { emitter =
		 * ParticleIO.loadEmitter(xmlFile); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 * emitter.setPosition(400, 300); system.addEmitter(emitter);
		 * system.setBlendingMode(ParticleSystem.BLEND_ADDITIVE);
		 */
		setup();

	}

	public void setup() {
		chance = 200;
		isLost = false;
		score = 0;
		start = new Vector2f(XDIMENSION / 2 - 10, YDIMENSION - 25);
		barrelStart = new Vector2f((XDIMENSION / 2) - 13, YDIMENSION - 110);
		bullets1 = new ArrayList<>();// creates arraylist with bullets
		bombs = new ArrayList<>();
		exp = new ArrayList<>();

	}

	public void spawnBullet(int x, int y, GameContainer gc) {
		double angle = getAngle(x, y, gc);
		bullets1.add(0, (new Bullet(speed, angle, x, y, start, XDIMENSION)));// adds
																				// bullet
		// to the
		// arraylist
	}

	public void spawnBomb() {
		int xCoord = (int) (Math.random() * (XDIMENSION - 1) + 1);
		// System.out.println(xCoord);
		bombs.add(0, new Bomb(xCoord));
	}

	public void drawCrosshair(GameContainer gc) {
		crosshair.draw(gc.getInput().getMouseX() - 10, gc.getInput().getMouseY() - 10);
	}

	public void update(GameContainer gc, int millis) throws SlickException {

		if (gc.getInput().isMousePressed(0) && delay <= 0) {
			this.spawnBullet(gc.getInput().getMouseX(), gc.getInput().getMouseY(), gc);
			double angle1 = getAngle(gc.getInput().getMouseX(), gc.getInput().getMouseY(), gc);
			// System.out.println(angle1);
			barrel1.recoil(millis, angle1);
			delay = SHOTDELAY * millis;
			// system.update(millis);

			// called spawnbullet if mouse is pressed
		}

		for (int i = 0; i < bullets1.size(); i++) {

			// removes the bullet from the arraylist after 2000 milliseconds
			Bullet temp = bullets1.get(i);
			temp.update(millis);

			for (int x = 0; x < bombs.size(); x++) {
				Bomb temp1 = bombs.get(x);
				if (temp1.getBoundingBox().intersects(temp.getBoundingBox())) {
					exp.add(0, new Explosion(temp1.getX(), temp1.getY()));
					bombs.remove(x);
					if (chance > 50) {
						chance -= 2;
					}
					x--;
					bullets1.remove(i);
					i--;
					score++;

				}
			}

			if (temp.getLife() > 2000) {
				bullets1.remove(i);
				i--;
			}
		}

		int angle = (int) this.getAngle(gc.getInput().getMouseX(), gc.getInput().getMouseY(), gc);
		barrel1.update(millis, angle, gc);
		if (delay > 0) {
			delay -= millis;
		}
		int probability = (int) ((Math.random() * chance + 1));
		if (probability == 1) {
			this.spawnBomb();
		}

		for (Bomb b : bombs) {
			b.update(millis);
			if (b.getY() > 700) {
				isLost = true;
			}
		}

		for (int i = 0; i < exp.size(); i++) {
			Explosion temp = exp.get(i);
			temp.update(millis);
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {

		if (isLost == true) {
			renderLost(gc, g);
		} else {
			g.setFont(font2);
			// int angle12=(int) this.getAngle(gc.getInput().getMouseX(),
			// gc.getInput().getMouseY(), gc);
			background.draw(0, 0);

			for (int i = 0; i < bullets1.size(); i++) {

				Bullet temp = bullets1.get(i);

				for (int x = 0; x < bombs.size(); x++) {

					Bomb temp1 = bombs.get(x);
					if (temp1.getBoundingBox().intersects(temp.getBoundingBox())) {
						System.out.println("hit");

					}
				}

			}

			// system.render();
			for (Bullet tempBullet : bullets1)
				// updates the bullets
				try {
					tempBullet.render(g);

				} catch (SlickException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			for (int i = 0; i < exp.size(); i++) {
				Explosion temp = exp.get(i);
				temp.draw(0, 0);
				if (temp.getLife() > 1000) {
					exp.remove(i);
					i--;
				}

			}
			barrel1.render(g, gc);
			renderGun(gc, g);// renders the turret

			gc.setMouseCursor("images/tempMouse.png", 0, 0);
			g.drawString("Score: " + score, 20, 700);
			for (Bomb b : bombs) {
				b.render(g);

			}

			drawCrosshair(gc);// draws crosshair
		}

	}

	// renders the gun and rotates it so its pointing at the mouse
	public void renderGun(GameContainer gc, Graphics g) {
		int x = gc.getInput().getMouseX();
		int y = gc.getInput().getMouseY();
		double tempRot = this.getAngle(x, y, gc);
		double tempRot1 = (this.getAngle(x, y, gc)) * -1;
		if (gc.getInput().getMouseX() > XDIMENSION / 2) {
			g.rotate(XDIMENSION / 2, YDIMENSION, (float) tempRot);
		}
		if (gc.getInput().getMouseX() < XDIMENSION / 2) {
			g.rotate(XDIMENSION / 2, YDIMENSION, (float) tempRot1);
		}

		tBase.draw((XDIMENSION / 2) - 30, YDIMENSION - 30);
		g.resetTransform();
		actualBase.draw((XDIMENSION / 2) - 200, YDIMENSION - 10);

		g.resetTransform();

		// if (gc.getInput().isMousePressed(0)) {

		// for(int i =)
		// }
	}

	// gets the angle needed for a bottom centered object to face the mouse
	public double getAngle(int x, int y, GameContainer gc) {
		int bottom = Math.abs(gc.getInput().getMouseX() - XDIMENSION / 2);
		int opposite = Math.abs(gc.getInput().getMouseY() - YDIMENSION);
		float tanx = ((float) opposite / bottom);
		double rotation1 = Math.toDegrees(Math.atan(tanx));
		double rotation = 90 - (rotation1);
		return rotation;
	}

	public void renderLost(GameContainer gc, Graphics g) {
		g.setFont(overFont);
		g.drawString("Your Final Score Is : " + score, XDIMENSION / 2 - 275, YDIMENSION / 2 + 100);
		// g.translate(300, -100);
		g.drawString("GAME OVER!", XDIMENSION / 2 - 150, YDIMENSION / 2 - 100);
		g.setColor(Color.red);
		if (gc.getInput().isKeyDown(Input.KEY_SPACE)) {
			setup();
			g.setColor(Color.white);
		}
	}

	public static void main(String[] args) {
		System.out.println("Starting up the game!");
		try {
			AppGameContainer game = new AppGameContainer(new Game("Title goes here"));
			game.setDisplayMode(XDIMENSION, YDIMENSION, false);
			game.setTargetFrameRate(144);
			game.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
