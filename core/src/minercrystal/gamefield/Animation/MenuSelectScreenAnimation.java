package minercrystal.gamefield.Animation;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import minercrystal.gamefield.WorkScreens.Tools.BackMenuSelectScreen;

public class MenuSelectScreenAnimation {
    static final float accelerationAnimationGoToScreen = 0.02f, a = 1.12f, accBlow = 0.035f;
    float blowAccumulator = 0, speedAnimationGoToScreen = 0.001f;
    ExplosionAnimation explosionAnimation;
    TextureRegion blockTexture;
    Array<Rectangle> blocks;
    boolean AnimationGoTONextLocation = false;
    int Location;

    public MenuSelectScreenAnimation(int Location) {
        this.Location = Location;
        blocks = new Array<>();
        explosionAnimation = new ExplosionAnimation();
        Texture map1 = new Texture("Maps/Map1/tailSet.png");
        blockTexture = new TextureRegion(map1, 64 * Location, 448, 64, 64);
    }

    public void render(SpriteBatch batch, float delta, OrthographicCamera camera) {

        for (Rectangle block : blocks)
            batch.draw(blockTexture, block.x, block.y, block.width, block.height);
        explosionAnimation.render(delta, batch, camera);
    }

    public void renderFront(SpriteBatch batch, float delta, OrthographicCamera camera) {
        // explosionAnimation.render(delta, batch, camera);
    }

    public boolean updateBlocks(float delta) {
        if (blocks.size > 0) {
            blowAccumulator += delta;
            if (blowAccumulator > accBlow) {
                explosionAnimation.addExplosionAnimation(blocks.peek().x + 36 - 480 * Location, blocks.peek().y + 24, blocks.peek().width + 12, blocks.peek().height + 12);
                blocks.removeValue(blocks.peek(), false);
                blowAccumulator = 0;
            }
        }
        return blocks.size == 0;
    }

    public void initRBlocks(boolean isWin, float selectY, boolean isLast, int Location) {
        if (isWin) {
            if (isLast) {
                createEndRLevel(selectY + 9, Location);
            } else {
                for (int i = 0; i < 16; i++) {
                    blocks.add(new Rectangle(480 * (Location + 1) - 64 / 2.4f * i - 128 / 2.4f, selectY - 2, 64 / 2.4f, 64 / 2.4f + 4));
                    blocks.add(new Rectangle(480 * (Location + 1) - 64 / 2.4f * i - 128 / 2.4f, selectY + 64 / 2.4f - 2, 64 / 2.4f, 64 / 2.4f + 4));
                }
                blocks.add(new Rectangle(128 / 2.4f + 480 * Location, selectY + 128 / 2.4f, 64 / 2.4f, 64 / 2.4f));
                blocks.add(new Rectangle(64 / 2.4f + 480 * Location, selectY + 128 / 2.4f, 64 / 2.4f, 64 / 2.4f));
            }
        }
    }

    public void initLBlocks(boolean isWin, float selectY, boolean isLast, int Location) {
        this.AnimationGoTONextLocation = isLast;
        if (isWin) {
            if (isLast) {
                createEndLLevel(selectY + 9, Location);
            } else {
                for (int i = 0; i < 16; i++) {
                    blocks.add(new Rectangle((i * 64 / 2.4f) + 480 * Location + 64 / 2.4f, selectY - 2, 64 / 2.4f, 64 / 2.4f + 4));
                    blocks.add(new Rectangle((i * 64 / 2.4f) + 480 * Location + 64 / 2.4f, selectY + 64 / 2.4f - 2, 64 / 2.4f, 64 / 2.4f + 4));
                }
                blocks.add(new Rectangle(64 / 2.4f + 64 * 15 / 2.4f + 480 * Location, selectY + 128 / 2.4f, 64 / 2.4f, 64 / 2.4f));
                blocks.add(new Rectangle(64 / 2.4f + 64 * 14 / 2.4f + 480 * Location, selectY + 128 / 2.4f, 64 / 2.4f, 64 / 2.4f));

            }
        }
    }

    private void createEndRLevel(float selectY, int Location) {
        blocks.add(new Rectangle(480 - 64 / 2.4f * 8 + 480 * Location, selectY - 64 / 2.4f * 6, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 9 + 480 * Location, selectY - 64 / 2.4f * 6, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 10 + 480 * Location, selectY - 64 / 2.4f * 6, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 11 + 480 * Location, selectY - 64 / 2.4f * 6, 64 / 2.4f, 64 / 2.4f + 4));

        blocks.add(new Rectangle(480 - 64 / 2.4f * 7 + 480 * Location, selectY - 64 / 2.4f * 5, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 8 + 480 * Location, selectY - 64 / 2.4f * 5, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 9 + 480 * Location, selectY - 64 / 2.4f * 5, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 10 + 480 * Location, selectY - 64 / 2.4f * 5, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 11 + 480 * Location, selectY - 64 / 2.4f * 5, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 12 + 480 * Location, selectY - 64 / 2.4f * 5, 64 / 2.4f, 64 / 2.4f + 4));

        blocks.add(new Rectangle(480 - 64 / 2.4f * 6 + 480 * Location, selectY - 64 / 2.4f * 4, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 7 + 480 * Location, selectY - 64 / 2.4f * 4, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 8 + 480 * Location, selectY - 64 / 2.4f * 4, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 9 + 480 * Location, selectY - 64 / 2.4f * 4, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 10 + 480 * Location, selectY - 64 / 2.4f * 4, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 11 + 480 * Location, selectY - 64 / 2.4f * 4, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 12 + 480 * Location, selectY - 64 / 2.4f * 4, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 13 + 480 * Location, selectY - 64 / 2.4f * 4, 64 / 2.4f, 64 / 2.4f + 4));

        blocks.add(new Rectangle(480 - 64 / 2.4f * 6 + 480 * Location, selectY - 64 / 2.4f * 3, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 7 + 480 * Location, selectY - 64 / 2.4f * 3, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 8 + 480 * Location, selectY - 64 / 2.4f * 3, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 9 + 480 * Location, selectY - 64 / 2.4f * 3, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 10 + 480 * Location, selectY - 64 / 2.4f * 3, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 11 + 480 * Location, selectY - 64 / 2.4f * 3, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 12 + 480 * Location, selectY - 64 / 2.4f * 3, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 13 + 480 * Location, selectY - 64 / 2.4f * 3, 64 / 2.4f, 64 / 2.4f + 4));

        blocks.add(new Rectangle(480 - 64 / 2.4f * 6 + 480 * Location, selectY - 64 / 2.4f * 2, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 7 + 480 * Location, selectY - 64 / 2.4f * 2, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 8 + 480 * Location, selectY - 64 / 2.4f * 2, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 9 + 480 * Location, selectY - 64 / 2.4f * 2, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 10 + 480 * Location, selectY - 64 / 2.4f * 2, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 11 + 480 * Location, selectY - 64 / 2.4f * 2, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 12 + 480 * Location, selectY - 64 / 2.4f * 2, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 13 + 480 * Location, selectY - 64 / 2.4f * 2, 64 / 2.4f, 64 / 2.4f + 4));

        blocks.add(new Rectangle(480 - 64 / 2.4f * 6 + 480 * Location, selectY - 64 / 2.4f, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 7 + 480 * Location, selectY - 64 / 2.4f, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 8 + 480 * Location, selectY - 64 / 2.4f, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 9 + 480 * Location, selectY - 64 / 2.4f, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 10 + 480 * Location, selectY - 64 / 2.4f, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 11 + 480 * Location, selectY - 64 / 2.4f, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 12 + 480 * Location, selectY - 64 / 2.4f, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 13 + 480 * Location, selectY - 64 / 2.4f, 64 / 2.4f, 64 / 2.4f + 4));

        blocks.add(new Rectangle(480 - 64 / 2.4f * 7 + 480 * Location, selectY, 64 / 2.4f, 64 / 2.4f + 4));

        for (int i = 8; i < 18; i++) {
            blocks.add(new Rectangle(480 - 64 / 2.4f * i + 480 * Location, selectY - 2, 64 / 2.4f, 64 / 2.4f + 4));
            blocks.add(new Rectangle(480 - 64 / 2.4f * i + 480 * Location, selectY + 64 / 2.4f - 2, 64 / 2.4f, 64 / 2.4f + 4));
        }

        blocks.add(new Rectangle(128 / 2.4f + 480 * Location, selectY + 128 / 2.4f, 64 / 2.4f, 64 / 2.4f));
        blocks.add(new Rectangle(64 / 2.4f + 480 * Location, selectY + 128 / 2.4f, 64 / 2.4f, 64 / 2.4f));
    }


    private void createEndLLevel(float selectY, int Location) {

        blocks.add(new Rectangle(480 - 64 / 2.4f * 11 + 480 * Location, selectY - 64 / 2.4f * 6, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 10 + 480 * Location, selectY - 64 / 2.4f * 6, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 9 + 480 * Location, selectY - 64 / 2.4f * 6, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 8 + 480 * Location, selectY - 64 / 2.4f * 6, 64 / 2.4f, 64 / 2.4f + 4));

        blocks.add(new Rectangle(480 - 64 / 2.4f * 12 + 480 * Location, selectY - 64 / 2.4f * 5, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 11 + 480 * Location, selectY - 64 / 2.4f * 5, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 10 + 480 * Location, selectY - 64 / 2.4f * 5, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 9 + 480 * Location, selectY - 64 / 2.4f * 5, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 8 + 480 * Location, selectY - 64 / 2.4f * 5, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 7 + 480 * Location, selectY - 64 / 2.4f * 5, 64 / 2.4f, 64 / 2.4f + 4));

        blocks.add(new Rectangle(480 - 64 / 2.4f * 13 + 480 * Location, selectY - 64 / 2.4f * 4, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 12 + 480 * Location, selectY - 64 / 2.4f * 4, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 11 + 480 * Location, selectY - 64 / 2.4f * 4, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 10 + 480 * Location, selectY - 64 / 2.4f * 4, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 9 + 480 * Location, selectY - 64 / 2.4f * 4, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 8 + 480 * Location, selectY - 64 / 2.4f * 4, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 7 + 480 * Location, selectY - 64 / 2.4f * 4, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 6 + 480 * Location, selectY - 64 / 2.4f * 4, 64 / 2.4f, 64 / 2.4f + 4));

        blocks.add(new Rectangle(480 - 64 / 2.4f * 13 + 480 * Location, selectY - 64 / 2.4f * 3, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 12 + 480 * Location, selectY - 64 / 2.4f * 3, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 11 + 480 * Location, selectY - 64 / 2.4f * 3, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 10 + 480 * Location, selectY - 64 / 2.4f * 3, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 9 + 480 * Location, selectY - 64 / 2.4f * 3, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 8 + 480 * Location, selectY - 64 / 2.4f * 3, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 7 + 480 * Location, selectY - 64 / 2.4f * 3, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 6 + 480 * Location, selectY - 64 / 2.4f * 3, 64 / 2.4f, 64 / 2.4f + 4));

        blocks.add(new Rectangle(480 - 64 / 2.4f * 13 + 480 * Location, selectY - 64 / 2.4f * 2, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 12 + 480 * Location, selectY - 64 / 2.4f * 2, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 11 + 480 * Location, selectY - 64 / 2.4f * 2, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 10 + 480 * Location, selectY - 64 / 2.4f * 2, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 9 + 480 * Location, selectY - 64 / 2.4f * 2, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 8 + 480 * Location, selectY - 64 / 2.4f * 2, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 7 + 480 * Location, selectY - 64 / 2.4f * 2, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 6 + 480 * Location, selectY - 64 / 2.4f * 2, 64 / 2.4f, 64 / 2.4f + 4));

        blocks.add(new Rectangle(480 - 64 / 2.4f * 13 + 480 * Location, selectY - 64 / 2.4f, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 12 + 480 * Location, selectY - 64 / 2.4f, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 11 + 480 * Location, selectY - 64 / 2.4f, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 10 + 480 * Location, selectY - 64 / 2.4f, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 9 + 480 * Location, selectY - 64 / 2.4f, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 8 + 480 * Location, selectY - 64 / 2.4f, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 7 + 480 * Location, selectY - 64 / 2.4f, 64 / 2.4f, 64 / 2.4f + 4));
        blocks.add(new Rectangle(480 - 64 / 2.4f * 6 + 480 * Location, selectY - 64 / 2.4f, 64 / 2.4f, 64 / 2.4f + 4));

        blocks.add(new Rectangle(480 - 64 / 2.4f * 7 + 480 * Location, selectY, 64 / 2.4f, 64 / 2.4f + 4));

        for (int i = 8; i < 18; i++) {
            blocks.add(new Rectangle(480 - 64 / 2.4f * i + 480 * Location, selectY - 2, 64 / 2.4f, 64 / 2.4f + 4));
            blocks.add(new Rectangle(480 - 64 / 2.4f * i + 480 * Location, selectY + 64 / 2.4f - 2, 64 / 2.4f, 64 / 2.4f + 4));
        }

        blocks.add(new Rectangle(128 / 2.4f + 480 * Location, selectY + 128 / 2.4f, 64 / 2.4f, 64 / 2.4f));
        blocks.add(new Rectangle(64 / 2.4f + 480 * Location, selectY + 128 / 2.4f, 64 / 2.4f, 64 / 2.4f));
    }

    public ExplosionAnimation getExplosionAnimation() {
        return explosionAnimation;
    }

    public boolean renderGoToScreen(OrthographicCamera camera, float delta, SpriteBatch spriteBatch, OrthographicCamera buttonsCam, BackMenuSelectScreen backMenuSelectScreen) {
        speedAnimationGoToScreen += (accelerationAnimationGoToScreen * delta);
        speedAnimationGoToScreen *= a;
        camera.zoom -= speedAnimationGoToScreen;
        camera.update();
        spriteBatch.setProjectionMatrix(buttonsCam.combined);
        if (camera.zoom < 0.2f)
            backMenuSelectScreen.setGoScreen(0, spriteBatch);
        else
            backMenuSelectScreen.setGoScreen(camera.zoom, spriteBatch);
        return camera.zoom < 0.2f;
    }
}
