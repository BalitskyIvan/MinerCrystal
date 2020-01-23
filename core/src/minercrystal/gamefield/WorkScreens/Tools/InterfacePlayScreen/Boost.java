package minercrystal.gamefield.WorkScreens.Tools.InterfacePlayScreen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public interface Boost {

    void render(SpriteBatch spriteBatch, float delta);
    boolean touchDown(float X, float Y);
    void destroy();
    boolean isActive();
    int getCount();
    boolean setGoToY(float Y);
    Rectangle getElementRect(String element);

    void plusElement(String element, int count);
}
