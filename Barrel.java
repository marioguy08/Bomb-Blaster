import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Barrel {
	Vector2f pos = new Vector2f();
	int angle;
	private Image barrel;
	private int animLength = 0;
	private float xOffset = 0;
	private float yOffset = 0;
	private double staticAngle = 0;
	private int sentinel = 0;
	private int XDIMENSION;

	public Barrel(Vector2f pos, int x) {
		this.pos.x = pos.x;
		this.pos.y = pos.y;
		this.XDIMENSION = x;

	}

	public void render(Graphics g, GameContainer gc) throws SlickException {

		barrel = new Image("images/barrel1.png");

		// System.out.println(barrel.getCenterOfRotationY());
		if (gc.getInput().getMouseX() > XDIMENSION / 2) {
			barrel.setRotation(angle);

		}
		if (gc.getInput().getMouseX() < XDIMENSION / 2) {
			barrel.rotate(angle * -1);
		}

		g.resetTransform();
		barrel.draw(pos.x, pos.y);
		g.resetTransform();

	}

	public void update(int millis, int angle, GameContainer gc) {
		// System.out.println(staticAngle);
		this.angle = angle;
		if (gc.getInput().isMousePressed(0)) {

		}
		// System.out.println(xOffset);
		if (animLength > 10) {
			if (gc.getInput().getMouseX() < XDIMENSION / 2) {
				float offsetX = (float) ((Math.sin(Math.toRadians(angle)) * millis) / 1);
				float offsetY = (float) ((Math.cos(Math.toRadians(angle)) * millis) / 1);
				pos.x += offsetX;
				pos.y += offsetY;
				xOffset += offsetX;
				yOffset += offsetY;
				barrel.setRotation((float) staticAngle);
			}
			if (gc.getInput().getMouseX() > XDIMENSION / 2) {
				float offsetX = (float) ((Math.sin(Math.toRadians(angle)) * millis) / 1);
				float offsetY = (float) ((Math.cos(Math.toRadians(angle)) * millis) / 1);
				pos.x -= offsetX;
				pos.y += offsetY;
				xOffset += offsetX;
				yOffset += offsetY;
				barrel.setRotation((float) -staticAngle);

			}
			animLength--;
		}
		if (animLength > 0 && animLength <= 10) {
			if (gc.getInput().getMouseX() < XDIMENSION / 2) {
				// float offsetX1=(float) ((Math.sin(Math.toRadians(angle)) *
				// millis)*2);
				// float offsetY1=(float) ((Math.cos(Math.toRadians(angle))
				// *millis)*2);

				float xIncrement = xOffset / 10;
				float yIncrement = yOffset / 10;
				pos.x -= xIncrement;
				pos.y -= yIncrement;
				barrel.setRotation((float) staticAngle);
				animLength--;
			}

			if (gc.getInput().getMouseX() > XDIMENSION / 2) {
				// float offsetX=(float) ((Math.sin(Math.toRadians(angle)) *
				// millis)*2);
				// float offsetY=(float) ((Math.cos(Math.toRadians(angle))
				// *millis)*2);
				float xIncrement = xOffset / 10;
				float yIncrement = yOffset / 10;
				pos.x += xIncrement;
				pos.y -= yIncrement;
				barrel.setRotation((float) -staticAngle);
				animLength--;
			}

		}
		if (animLength == 0) {
			xOffset = 0;
			yOffset = 0;
			sentinel = 0;
		}

		barrel.setCenterOfRotation(12, 99);
	}

	public void recoil(int millis, double angle) {

		animLength += 15;
		if (sentinel < 1) {
			staticAngle = angle;
			sentinel++;
		}
	}
}
