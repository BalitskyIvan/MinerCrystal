package minercrystal.gamefield;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public interface FrameInterface {
    void render(SpriteBatch spriteBatch, float delta);

    void touchDown(float x, float y);

    void touchDragged(float x, float y);

    void touchUP(float x, float y);

    void setFrameDeploy(boolean frameDeploy);

    void closeFrame();

    boolean isFrameActive();

    boolean isRedBTNPressed();
}
