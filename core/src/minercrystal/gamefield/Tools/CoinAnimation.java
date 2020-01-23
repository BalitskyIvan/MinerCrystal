package minercrystal.gamefield.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class CoinAnimation {
    private Texture coinFrame, coinAtlas;
    private Array<TextureRegion> coins;
    private float width = 38, animationDelay = 0.08f, accumulator, coinWidth = 33, coinHeigh = 33, HWzoom = 0;
    private int index = 0;
    private final static float TIME_TO_ZOOM = 5, ZOOM_SPEED = 0.5f;
    private Vector2 position;
    private Array<Rectangle> animFrames;
    private Array<Float> splashFrames;
    private float splashTimer = 4.50f, DeltaFrames = 0.75f,
            FramesAccumulator, AnimLong = 0.08f, AnimAccumulator, IndexSize = 0.45f;
    private boolean  isMaxAnimate = false;

    public CoinAnimation(float X, float Y) {
        position = new Vector2(X, Y);
        coins = new Array<>();
        coinFrame = new Texture("tools/coins/coinFrame.png");
        animFrames = new Array<>();
        splashFrames = new Array<>();
        coinAtlas = new Texture("tools/coins/coinAtlas.png");
        for (int i = 0; i <= 6; i++) {
            TextureRegion textureRegion = new TextureRegion(coinAtlas, (int) (i * width), 0, 38, 38);
            coins.add(textureRegion);
        }
    }

    public void render(SpriteBatch spriteBatch, float delta) {
        accumulator += delta;
        if (accumulator > animationDelay) {
            accumulator = 0;
            index++;
            if (index == 6)
                index = 0;
        }
        if (HWzoom > 0) {
            HWzoom -= ZOOM_SPEED;
        }

        if (isMaxAnimate) {
            AnimAccumulator += delta;
            if (AnimAccumulator < AnimLong) {
                FramesAccumulator += delta;
                if (FramesAccumulator > DeltaFrames) {
                    FramesAccumulator = 0;
                    addAnimFrame();
                }
            }
            for (Rectangle r : animFrames) {
                r.x -= IndexSize;
                r.y -= IndexSize;
                r.width += IndexSize * 2;
                r.height += IndexSize * 2;
                drawAnimFrame(delta, spriteBatch, r);
            }
            for (Rectangle r : animFrames)
                if (splashFrames.get(animFrames.indexOf(r, false)) < 0) {
                    splashFrames.removeValue(splashFrames.get(animFrames.indexOf(r, false)), false);
                    animFrames.removeValue(r, false);
                }
            if (animFrames.size == 0) {
                isMaxAnimate = false;
                AnimAccumulator = 0;
            }
        }
        spriteBatch.draw(coins.get(index), position.x - HWzoom, position.y - HWzoom, coinWidth + HWzoom*2, coinHeigh + HWzoom*2);
    }
    private void drawAnimFrame(float delta, SpriteBatch spriteBatch, Rectangle r) {
        splashFrames.set(animFrames.indexOf(r, false), splashFrames.get(animFrames.indexOf(r, false)) - splashTimer * delta);
        Color c = spriteBatch.getColor();
        spriteBatch.end();
        spriteBatch.setColor(c.r, c.g, c.b, splashFrames.get(animFrames.indexOf(r, false)));
        spriteBatch.begin();
        spriteBatch.draw(coinFrame, r.x, r.y, r.width, r.height);
        spriteBatch.end();
        spriteBatch.begin();
        spriteBatch.setColor(c.r, c.g, c.b, 1);
    }

    private void addAnimFrame() {
        animFrames.add(new Rectangle(position.x, position.y, coinWidth, coinHeigh));
        splashFrames.add(1f);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void changePosition(float X, float Y){
       position.set(getPosition().x, Y);
    }

    public void setPosition(Vector2 position, float deltaFrames, float splashTimer) {
        this.position = position;
        this.splashTimer  = splashTimer;
        this.DeltaFrames = deltaFrames;
    }

    public void kick() {
        HWzoom = TIME_TO_ZOOM;
        isMaxAnimate = true;
        addAnimFrame();
    }

    public TextureRegion getFirstTextureCoin() {
        return coins.get(0);
    }
}
