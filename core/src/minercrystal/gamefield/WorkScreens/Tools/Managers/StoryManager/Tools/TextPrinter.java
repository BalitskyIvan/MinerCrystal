package minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Tools;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class TextPrinter {
    private boolean isTextDeleted = false;
    public TextPrinter() {

    }
    public void startPrint(String text, float time, Vector2 position){

    }
    public void deletePrint(){
        isTextDeleted = true;
    }

    public void render(SpriteBatch spriteBatch, float delta){

    }

    public boolean isTextDeleted() {
        return isTextDeleted;
    }
    public void dispose(){

    }
}
