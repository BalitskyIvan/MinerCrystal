package minercrystal.gamefield.Animation;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;



public class BoostButtonFrame {

    private Texture frame;
    private Array<Rectangle> frames;
    private Array<Float> splashAr;
    private Vector2 position;
    private boolean isActive = false;
    private float splashTimer = 1.40f, DeltaFrames = 0.25f,
            FramesAccumulator, mainAccumulator, IndexSize = 0.52f, W, H, Long;
    public BoostButtonFrame(float W,float H) {
        this.W = W;
        this.H = H;
       position = new Vector2();
       frames = new Array<>();
       frame = new Texture("boost/boostButton/frame.png");
       splashAr = new Array<>();
    }

    public void render(SpriteBatch spriteBatch, float delta){
        if(isActive) {
            mainAccumulator += delta;
            if (mainAccumulator < Long) {
                FramesAccumulator += delta;
                if (FramesAccumulator > DeltaFrames) {
                    FramesAccumulator = 0;
                    addAnimFrame();
                }
            }else isActive = false;
        }
        for (Rectangle r : frames) {
            r.x -= IndexSize;
            r.y -= IndexSize;
            r.width += IndexSize * 2;
            r.height += IndexSize * 2;
            drawAnimFrame(delta, spriteBatch, r);
        }
        for (Rectangle r : frames)
            if (splashAr.get(frames.indexOf(r, false)) < 0) {
                splashAr.removeValue(splashAr.get(frames.indexOf(r, false)), false);
                frames.removeValue(r, false);
            }
    }
    private void drawAnimFrame(float delta, SpriteBatch spriteBatch, Rectangle r) {
        splashAr.set(frames.indexOf(r, false), splashAr.get(frames.indexOf(r, false)) - splashTimer * delta);
        Color c = spriteBatch.getColor();
        spriteBatch.end();
        spriteBatch.setColor(c.r, c.g, c.b, splashAr.get(frames.indexOf(r, false)));
        spriteBatch.begin();
        spriteBatch.draw(frame, r.x, r.y, r.width, r.height);
        spriteBatch.end();
        spriteBatch.begin();
        spriteBatch.setColor(c.r, c.g, c.b, 1);
    }

    public boolean isActive() {
        return isActive;
    }

    public void addFrame(float X, float Y, float animLong){
        Long += animLong;
        position.set(X, Y-1);
        mainAccumulator = 0;
        isActive = true;
    }
    public void changePos(float Y){
        position.y = Y-1;
    }
    private void addAnimFrame() {
        frames.add(new Rectangle(position.x, position.y, W, H));
        splashAr.add(1f);
    }
    public void dispose(){
        frame.dispose();
    }
}
