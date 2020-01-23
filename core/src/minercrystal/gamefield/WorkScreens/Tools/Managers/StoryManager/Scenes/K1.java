package minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Scene;
import minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Tools.TextPrinter;

public class K1 implements Scene {

    private boolean isStop;
    private float time, timeAccumulator = 0, alpha = 0, speedAlpha = 0.07f, accelerationAlpha = 1.1f, deadTime = 2f, zoomSpeed = 0;
    private Texture islandTexture;
    private Rectangle islandRect;
    private TextPrinter textPrinter;
    public K1(TextPrinter textPrinter) {
        this.textPrinter = textPrinter;
        islandRect = new Rectangle(200, 360, 80, 80);
        islandTexture = new Texture("historyManager/0/0.png");
    }

    @Override
    public void render(SpriteBatch spriteBatch, float delta) {
        timeAccumulator += delta;
        if (timeAccumulator < time) {

            if (time - timeAccumulator <= deadTime) {
                alpha -= 1/deadTime * delta;
                Color c = spriteBatch.getColor();
                spriteBatch.setColor(c.r, c.g, c.b, alpha);
                if(!textPrinter.isTextDeleted())
                    textPrinter.deletePrint();
            } else if (alpha < 1) {
                speedAlpha *= accelerationAlpha;
                alpha += speedAlpha;
                if (alpha > 1)
                    alpha = 1;
                Color c = spriteBatch.getColor();
                spriteBatch.setColor(c.r, c.g, c.b, alpha);
            }
            drawIsland(spriteBatch);

        } else {
            isStop = true;
        }
    }

    @Override
    public void play(float time, String text) {
        this.time = time;
        zoomSpeed = 10 / time;
        isStop = false;
        alpha = 0;
        timeAccumulator = 0;
        textPrinter.startPrint(text, time - deadTime, new Vector2(0, 700));
    }

    private void drawIsland(SpriteBatch spriteBatch) {
        islandRect.set(islandRect.getX() - zoomSpeed / 2, islandRect.getY() - zoomSpeed / 2, islandRect.width + zoomSpeed, islandRect.height + zoomSpeed);
        spriteBatch.begin();
        spriteBatch.draw(islandTexture, islandRect.getX(), islandRect.getY(), islandRect.width, islandRect.height);
        spriteBatch.end();
    }

    @Override
    public boolean isStopped() {
        return isStop;
    }

    @Override
    public void dispose() {
      islandTexture.dispose();
    }
}
