package minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


import java.util.Random;

import minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Scene;
import minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Tools.TextPrinter;

public class K2 implements Scene {
    private boolean isStop;
    private static final float widthCol = 80, heightCol = 110;
    private float time, timeAccumulator = 0, alpha = 0, speedAlpha = 0.07f, accelerationAlpha = 1.1f, deadTime = 2f, zoomSpeed = 0;
    private Texture personTexture, bb;
    private Array<Array<Rectangle>> peopleRects;
    private TextPrinter textPrinter;

    public K2(TextPrinter textPrinter) {
        this.textPrinter = textPrinter;
        peopleRects = new Array<>();
        Random r = new Random();

        for(int i = 1; i <= 7; i++){
            Array<Rectangle> rects = new Array<>();
            for (int i2 = 0; i2 < 11; i2++){
              rects.add(new Rectangle(widthCol * i2- 30 + r.nextInt(60), heightCol*2.5f+(heightCol - 87)*i, widthCol - 7 * i, heightCol - 7 *i));
            }
            peopleRects.add(rects);
        }
        peopleRects.reverse();
        personTexture = new Texture("historyManager/1/0.png");
        bb = new Texture("bb.jpg");
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
            }

            drawPeople(spriteBatch);

        } else {
            isStop = true;
        }
    }

    @Override
    public void play(float time, String text) {
        this.time = time;
        zoomSpeed = 10/time;
        isStop = false;
        alpha = 0;
        timeAccumulator = 0;
        textPrinter.startPrint(text, time - deadTime, new Vector2(0, 700), 0f);
    }
    private void drawPeople(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        int i = 0;
        for(Array<Rectangle> column: peopleRects){
            i++;
            for(Rectangle rect: column){
                rect.set(rect.getX() - (zoomSpeed * i/2)/widthCol, rect.getY() - (zoomSpeed * i/2)/heightCol, rect.getWidth() + (zoomSpeed*i)/widthCol, rect.getHeight()+ (zoomSpeed*i)/heightCol);
                spriteBatch.draw(personTexture, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

            }
        }
        Color c = spriteBatch.getColor();
        spriteBatch.setColor(c.r, c.g, c.b, 0.2f);
        spriteBatch.draw(bb, 0, 0, 480, 800);
        spriteBatch.end();
    }

    @Override
    public boolean isStopped() {
        return isStop;
    }

    @Override
    public void dispose() {

    }
}
