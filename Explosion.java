import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Explosion {
	private int xCoord;
	private int yCoord;
	private SpriteSheet explosion;
	private Animation explosionAnimation;
	private int life = 0;

	public Explosion(int x, int y) throws SlickException {
		explosion = new SpriteSheet("images/Explosion.png", 96, 96);
		explosionAnimation = new Animation(explosion, 75);
		xCoord = (x - 30);
		yCoord = y;
		explosionAnimation.setLooping(false);
	}

	public void draw(int x, int y) {
		explosionAnimation.draw(xCoord, yCoord);
		// System.out.println(x);
	}

	public void update(int millis) {
		explosionAnimation.update(millis);
		life += millis;
	}

	public void render(Graphics g) throws SlickException {

	}

	public int getLife() {
		return life;
	}
}
