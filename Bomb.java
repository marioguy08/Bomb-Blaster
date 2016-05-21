import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Bomb {
	private Image bomb1;
	Vector2f pos = new Vector2f();
	int fallSpeed = 1;
	private int XDIMENSION;

	public Bomb(int xCoord) {
		pos.y = -100;

		pos.x = xCoord;
	}

	public void update(int millis) {
		pos.y += (1 * millis) / 4;
	}

	public void render(Graphics g) throws SlickException {
		bomb1 = new Image("images/bomb.png");
		bomb1.draw(pos.x, pos.y);
	}

	public Rectangle getBoundingBox() {
		return new Rectangle(pos.x, pos.y, 35, 77);
	}

	public int getY() {
		return (int) pos.y;
	}

	public int getX() {
		return (int) pos.x;
	}
}
