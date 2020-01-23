package minercrystal.gamefield.AnimationScreens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import minercrystal.gamefield.Tools.SkyAnimation;


public class BackgroundScreen {
    private Texture bgRect;
    private Array<Rectangle> bgStoneRect;
    private boolean isFirst = true;
    private float SPEED = 1.5f, WIDTH, HEIGHT, yNow;
    private SkyAnimation skyAnimation;
    private Random r;

    public BackgroundScreen(float w, float h, float stoneWH) {
        this.WIDTH = w;
        this.HEIGHT = h;

        bgRect = new Texture("BackgroundAnimation/BKStone.png");
        r = new Random();
        bgStoneRect = new Array<>();

        float xNow = 0;
        yNow = HEIGHT - stoneWH;
        for (; xNow < WIDTH + stoneWH ; xNow += stoneWH) {
            bgStoneRect.add(new Rectangle(xNow, yNow, stoneWH, stoneWH));
        }

        skyAnimation = new SkyAnimation(480, 800, 0.18f, 5f);
    }

    public void render(float delta, SpriteBatch spriteBatch) {
        Color c = spriteBatch.getColor();
        spriteBatch.setColor(c.r, c.g, c.b, 1); //set alpha to 1
        spriteBatch.begin();
        skyAnimation.render(spriteBatch, delta);

        skyAnimation.cameraMove(SPEED);

        for (Rectangle stone : bgStoneRect) {
            spriteBatch.draw(bgRect, stone.x, stone.y, stone.width+1, stone.height);
            yNow = stone.y - stone.width;
            for (; yNow + stone.height > 0; yNow -= stone.width) {
                spriteBatch.draw(bgRect, stone.x, yNow, stone.width+1, stone.height+1);
            }
            stone.x -= SPEED;

        }
        spriteBatch.end();

        for (Rectangle stone : bgStoneRect) {

            if (stone.x + stone.width <= 0) {
                stone.x = bgStoneRect.peek().getX() + stone.width;
                bgStoneRect.add(stone);
                bgStoneRect.removeValue(bgStoneRect.first(), true);
                bgStoneRect.shrink();

            }
        }
        skyAnimation.update();
    }



    public void setSPEED(float SPEED) {
        this.SPEED = SPEED;
    }

    public float getSPEED() {
        return SPEED;
    }

    public void dispose() {
        skyAnimation.dispose();
        bgRect.dispose();
    }
}
