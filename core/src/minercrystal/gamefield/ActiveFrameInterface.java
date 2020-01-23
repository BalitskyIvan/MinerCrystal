package minercrystal.gamefield;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface ActiveFrameInterface {
    void render(float delta, SpriteBatch spriteBatch);
    void setActive(float milliseconds);
    void changeRect(Rectangle r);
}
