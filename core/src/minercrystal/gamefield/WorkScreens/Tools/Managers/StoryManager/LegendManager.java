package minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LegendManager {
    private Preferences preferences;
    private StoryMain storyMain;
    private boolean isStoryDeployed = false, isAnimationDeployed = false;
    public LegendManager() {

    }
    public LegendManager(Preferences preferences, BitmapFont bitmapFont) {
        this.preferences = preferences;
        preferences.putBoolean("isFirst", false);
        preferences.flush();
        if(!preferences.getBoolean("isFirst")){
            storyMain = new StoryMain(bitmapFont);
            isStoryDeployed = true;
        }
    }
    public void render(SpriteBatch spriteBatch, float delta){
        if(isStoryDeployed){
            storyMain.render(spriteBatch, delta);
            if(storyMain.isStoryEnded()){
                storyMain.dispose();
                preferences.putBoolean("isFirst", true);
                preferences.flush();
                isStoryDeployed = false;
            }
        }
    }

    public boolean isStoryDeployed() {
        return isStoryDeployed;
    }

    public boolean isAnimationDeployed() {
        return isAnimationDeployed;
    }
}
