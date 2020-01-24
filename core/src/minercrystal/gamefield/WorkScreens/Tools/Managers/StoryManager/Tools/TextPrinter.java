package minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Tools;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class TextPrinter {
    private boolean isTextDeleted = false;
    private float speed = 0, accumulator;
    private int charIndex = 0;
    private Vector2 startPos;
    private BitmapFont font;
    private String textDefault, textNow;

    public TextPrinter(BitmapFont font) {
        this.font = font;
        startPos = new Vector2();
    }

    public void startPrint(String text, float time, Vector2 position) {
        this.textDefault = text;
        this.startPos = position;
        speed = time/text.length();
        textNow = "";
        accumulator = 0;
        charIndex = 0;
        isTextDeleted = false;
    }

    public void deletePrint() {
        isTextDeleted = true;
    }

    public void render(SpriteBatch spriteBatch, float delta) {
        update(delta);
        spriteBatch.begin();
        font.draw(spriteBatch, textNow, startPos.x, startPos.y);
        spriteBatch.end();

    }

    private void update(float delta) {
        accumulator += delta;
        if (accumulator > speed && charIndex < textDefault.length()) {
            textNow += textDefault.charAt(charIndex);
            charIndex++;
            accumulator = 0;
        }
    }

    public boolean isTextDeleted() {
        return isTextDeleted;
    }

    public void dispose() {
        font.dispose();
    }
}
