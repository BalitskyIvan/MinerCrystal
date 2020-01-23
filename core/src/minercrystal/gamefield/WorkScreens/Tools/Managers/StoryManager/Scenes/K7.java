package minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Scenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Scene;
import minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Tools.TextPrinter;

public class K7 implements Scene {
    private boolean isStop;
    private float time, timeAccumulator = 0;

    public K7(TextPrinter textPrinter) {

    }

    @Override
    public void render(SpriteBatch spriteBatch, float delta) {
        timeAccumulator += delta;
        if(timeAccumulator < time){

        }else{
            isStop = true;
        }
    }

    @Override
    public void play(float time, String text) {
        this.time = time;
        isStop = false;
        timeAccumulator = 0;
    }

    @Override
    public boolean isStopped() {
        return isStop;
    }

    @Override
    public void dispose() {

    }
}
