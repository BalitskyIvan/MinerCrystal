package minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Scene {
    void render(SpriteBatch spriteBatch, float delta);
    void play(float time, String text);
    boolean isStopped();
    void dispose();
}
