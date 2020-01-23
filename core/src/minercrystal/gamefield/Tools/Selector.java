package minercrystal.gamefield.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import minercrystal.gamefield.ActiveFrameInterface;

public class Selector extends Rectangle implements ActiveFrameInterface {
    Texture pl;
    private Array<Rectangle> animFrames;
    private Array<Float> splashFrames;
    private float ACTIVE_TIME, accumulator_spawn = 0;
    private final static float deltaSpawnNew = 0.38f, splashTimer = 0.50f, IndexSize = 0.44f;

    public Selector(float x, float y, float width, float height) {
        super(x, y, width, height);
        animFrames = new Array<>();
        splashFrames = new Array<>();
        pl = new Texture("pl.png");
    }

    public void render(float delta, SpriteBatch spriteBatch) {
        if (ACTIVE_TIME > 0) {
            accumulator_spawn += delta;
            ACTIVE_TIME -= delta;
            if (accumulator_spawn > deltaSpawnNew) {
                accumulator_spawn = 0;
                addAnimFrame();
            }
            for (Rectangle r : animFrames) {
                r.y += IndexSize;
                drawAnimFrame(delta, spriteBatch, r);
            }
            for (Rectangle r: animFrames)
                if (splashFrames.get(animFrames.indexOf(r, false)) < 0) {
                    splashFrames.removeValue(splashFrames.get(animFrames.indexOf(r, false)), false);
                    animFrames.removeValue(r, false);
                }
        }
        spriteBatch.draw(pl, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public void setActive(float milliseconds) {
        this.ACTIVE_TIME = milliseconds;
    }

    @Override
    public void changeRect(Rectangle r) {

    }

    private void drawAnimFrame(float delta, SpriteBatch spriteBatch, Rectangle r) {
        splashFrames.set(animFrames.indexOf(r, false), splashFrames.get(animFrames.indexOf(r, false)) - splashTimer * delta);
        Color c = spriteBatch.getColor();
        spriteBatch.end();
        spriteBatch.setColor(c.r, c.g, c.b, splashFrames.get(animFrames.indexOf(r, false)));
        spriteBatch.begin();
        spriteBatch.draw(pl, r.x, r.y, r.width, r.height);
        spriteBatch.end();
        spriteBatch.begin();
        spriteBatch.setColor(c.r, c.g, c.b, 1);
    }

    private void addAnimFrame() {
        animFrames.add(new Rectangle(this.x, this.y, this.getWidth(), this.getHeight() - 7));
        splashFrames.add(1f);
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }
}
