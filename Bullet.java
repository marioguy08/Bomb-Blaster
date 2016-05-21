import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Bullet {
	Vector2f pos = new Vector2f();
	Vector2f size;
	double angle;
	private Image bullet;
	private double speed;
	private int xMouse;
	private int yMouse;
	private int lifeTime = 0;
	private int XDIMENSION;

	public Bullet(double speed, double angle, int xMouse, int yMouse, Vector2f startPos,int x) {
		this.speed = speed;
		this.angle = angle;
		this.xMouse = xMouse;
		this.yMouse = yMouse;
		pos.x = startPos.x;
		pos.y = startPos.y;
		XDIMENSION=x;
	}

	public void update(int millis) {
		// moves the bullet in the direction of the angle

		if (xMouse < XDIMENSION/2) {
			pos.x -= Math.sin(Math.toRadians(angle)) * speed * millis;
			pos.y -= Math.cos(Math.toRadians(angle)) * speed * millis;
			lifeTime += millis;
		}
		if (xMouse > XDIMENSION/2) {
			pos.x += Math.sin(Math.toRadians(angle)) * speed * millis;
			pos.y -= Math.cos(Math.toRadians(angle)) * speed * millis;
			lifeTime += millis;
		}

	}

	public double getAngle() {
		return angle;
	}

	public void rotateMethod() {
		bullet.rotate((float) angle);
	}

	public int getLife() {
		return lifeTime;
	}

	public void render(Graphics g) throws SlickException {
		// draws the bullet then rotates it accordingly
		bullet = new Image("images/JustBullet.png");
		if (xMouse > XDIMENSION/2) {
			bullet.rotate((float) angle);
		}
		if (xMouse < XDIMENSION/2) {
			bullet.rotate(((float) angle) * -1);
		}

		bullet.draw(pos.x, pos.y);
	}

	public void renderBorder(Graphics g) {
		g.setLineWidth(4);
		g.drawRect(pos.x, pos.y, size.x, size.y);
	}

	public Rectangle getBoundingBox() {
		// 20 and 50 are the images dimensions
		// returns a rectangle for collision detection
		return new Rectangle(pos.x, pos.y, 20, 50);
	}
}
