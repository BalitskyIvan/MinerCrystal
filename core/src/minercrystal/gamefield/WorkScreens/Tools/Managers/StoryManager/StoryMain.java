package minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Scenes.K1;
import minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Scenes.K2;
import minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Scenes.K3;
import minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Scenes.K4;
import minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Scenes.K5;
import minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Scenes.K6;
import minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Scenes.K7;
import minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.Tools.TextPrinter;

public class StoryMain {
    private Scene CutNow;
    private int numberofCut = 0;
    private TextPrinter textPrinter;
    private boolean isStoryEnded;

    public StoryMain() {
        textPrinter = new TextPrinter();
        CutNow = new K2(textPrinter);
        CutNow.play(12, "");
    }

    public void render(SpriteBatch spriteBatch, float delta){
        Color c = spriteBatch.getColor();
        spriteBatch.setColor(c.r, c.g, c.b, 1);
        textPrinter.render(spriteBatch, delta);
        CutNow.render(spriteBatch, delta);
        if(CutNow.isStopped())
            changeCut();
    }
    private void changeCut(){
        CutNow.dispose();

        switch (numberofCut){
            case 0:
               CutNow = new K2(textPrinter);
               CutNow.play(20, "");
                break;
            case 1:
                CutNow = new K3(textPrinter);
                CutNow.play(5, "");

                break;
            case 2:
                CutNow = new K4(textPrinter);
                CutNow.play(4, "");

                break;
            case 3:
                CutNow = new K5(textPrinter);
                CutNow.play(4, "");

                break;
            case 4:
                CutNow = new K6(textPrinter);
                CutNow.play(4, "");

                break;
            case 5:
                CutNow = new K7(textPrinter);
                CutNow.play(4, "");

                break;
            case 6:
                isStoryEnded = true;
                break;
        }
        numberofCut++;


    }

    public boolean isStoryEnded() {
        return isStoryEnded;
    }

    public void dispose(){
      textPrinter.dispose();
    }
}
