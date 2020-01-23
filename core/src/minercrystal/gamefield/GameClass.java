package minercrystal.gamefield;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import minercrystal.gamefield.WorkScreens.MenuSelectScreen;
import minercrystal.gamefield.WorkScreens.PlayScreen;


public class GameClass extends Game {
    SpriteBatch batch;

    public GameClass() {

    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MenuScreen(this, null));
       /// this.setScreen(new MenuSelectScreen(this, null, false));
       // this.setScreen(new PlayScreen(this, Gdx.app.getPreferences("MainPreferences"), true, 1));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
}