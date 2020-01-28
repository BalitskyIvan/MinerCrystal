package minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Scene;
import minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Tools.TextPrinter;

public class K3 implements Scene {
    private boolean isStop;
    private int kirkIndex = 0;
    private static final float width = 80, height = 80;
    private float time, timeAccumulator = 0, alpha = 0, deadTime = 2f, zoomSpeed = 0;
    private Texture personTexture, bb, mainTexture;
    private Array<Texture> knockTextures;
    private TextPrinter textPrinter;
    private Array<Rectangle> backBlocks, blackBlocks, deleteBlocks;
    private Rectangle personRect;
    public K3(TextPrinter textPrinter) {
        this.textPrinter = textPrinter;

        personTexture = new Texture("historyManager/3/personBody.png");
        bb = new Texture("tools/groundBack.png");
        mainTexture = new Texture("BackgroundAnimation/BKStone.png");

        knockTextures = new Array<>();
        for (int i = 0; i <= 3; i++)
            knockTextures.add(new Texture("historyManager/3/" + i + ".png"));

        backBlocks = new Array<>();
        backBlocks.add(new Rectangle(0, 0, width, height));

        blackBlocks = new Array<>();
        blackBlocks.add(new Rectangle(0, 440, width, height));

        deleteBlocks = new Array<>();
        deleteBlocks.add(new Rectangle(0, 440, width, height));

        personRect = new Rectangle(200, 440, 110, 210);
        float nowX = backBlocks.peek().getX();
        while (backBlocks.peek().getX() < 480) {
            float nowY = backBlocks.peek().getY();
            while (backBlocks.peek().getY() < 800) {
                backBlocks.add(new Rectangle(nowX, nowY, width, height));
                nowY += height;
            }
            nowX += width;
        }
        nowX = blackBlocks.peek().getX();
        while (blackBlocks.peek().getX() < 480) {
            float nowY = blackBlocks.peek().getY();
            while (blackBlocks.peek().getY() < 680) {
                blackBlocks.add(new Rectangle(nowX, nowY, width, height));
                deleteBlocks.add(new Rectangle(nowX, nowY, width, height));
                nowY += height;
            }
            nowX += width;
        }

    }

    @Override
    public void render(SpriteBatch spriteBatch, float delta) {
        timeAccumulator += delta;
        if (timeAccumulator < time) {

            if (time - timeAccumulator <= deadTime) {
                alpha -= 1 / deadTime * delta;
                Color c = spriteBatch.getColor();
                spriteBatch.setColor(c.r, c.g, c.b, alpha);
                if (!textPrinter.isTextDeleted())
                    textPrinter.deletePrint();
            }

            drawScene(spriteBatch);

        } else {
            isStop = true;
        }
    }

    private void drawScene(SpriteBatch spriteBatch) {
        spriteBatch.begin();
         for (Rectangle block: backBlocks){
             spriteBatch.draw(mainTexture, block.getX(), block.getY(), block.getWidth(), block.getHeight());
         }
         for (Rectangle bBlock: blackBlocks){
             spriteBatch.draw(bb, bBlock.getX(), bBlock.getY(), bBlock.getWidth(), bBlock.getHeight());
         }
         for (Rectangle dBlock: deleteBlocks){
             spriteBatch.draw(mainTexture, dBlock.getX(), dBlock.getY(), dBlock.getWidth(), dBlock.getHeight());
         }
         spriteBatch.draw(personTexture, personRect.getX(), personRect.getY(), personRect.getWidth(), personRect.getHeight());
         spriteBatch.draw(knockTextures.get(kirkIndex), personRect.getX(), personRect.getY(), personRect.getWidth(), personRect.getHeight());
         spriteBatch.end();
    }

    @Override
    public void play(float time, String text) {
        this.time = time;
        zoomSpeed = 10 / time;
        isStop = false;
        alpha = 0;
        timeAccumulator = 0;
        textPrinter.startPrint(text, time - deadTime * 2.5f, new Vector2(50, 700), 0f);
    }

    @Override
    public boolean isStopped() {
        return isStop;
    }

    @Override
    public void dispose() {

    }
}
