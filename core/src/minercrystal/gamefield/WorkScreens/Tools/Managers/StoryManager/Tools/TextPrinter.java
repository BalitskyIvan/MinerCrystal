package minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class TextPrinter {
    private boolean isDeployed = false,isTextDeleted = false;
    private float speed = 0, accumulator, deltaLong, alpha = 0;
    private int charIndex = 0;
    private Vector2 startPos;
    private BitmapFont font;
    private String textDefault, textNow, newEl;

    public TextPrinter(BitmapFont font) {
        this.font = font;
        startPos = new Vector2();
    }

    public void startPrint(String text, float time, Vector2 position, float deltaLong) {
        this.textDefault = text;
        this.deltaLong = deltaLong;
        this.startPos = position;
        speed = (time-deltaLong)/text.length();
        textNow = "";
        charIndex = 0;
        newEl = String.valueOf(textDefault.charAt(charIndex));
        accumulator = 0;
        isTextDeleted = false;
        isDeployed = false;
    }

    public void deletePrint() {
        isTextDeleted = true;
        textNow = "";
    }

    public void render(SpriteBatch spriteBatch, float delta) {
        update(delta);
        Color fcolor = font.getColor();
        font.setColor(fcolor.r, fcolor.g,  fcolor.b, alpha);

        spriteBatch.begin();

        font.draw(spriteBatch, (textNow + newEl), startPos.x, startPos.y);
        spriteBatch.end();
        font.setColor(fcolor.r,fcolor.g,  fcolor.b, 1);

        spriteBatch.begin();
        font.draw(spriteBatch, textNow, startPos.x, startPos.y);
        spriteBatch.end();

    }

    private void update(float delta) {
        accumulator += delta;
        if(isDeployed) {
            alpha += delta/speed;
            if (accumulator > speed && charIndex < textDefault.length()) {
                textNow += newEl;
                charIndex++;
                if(charIndex< textDefault.length())
                newEl = String.valueOf(textDefault.charAt(charIndex));
                alpha = 0;
                accumulator = 0;
            }
        }else if(accumulator > deltaLong)isDeployed = true;
    }

    public boolean isTextDeleted() {
        return isTextDeleted;
    }

    public void dispose() {
        font.dispose();
    }
}
