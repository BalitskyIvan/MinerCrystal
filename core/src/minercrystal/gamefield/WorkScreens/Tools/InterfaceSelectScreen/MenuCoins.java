package minercrystal.gamefield.WorkScreens.Tools.InterfaceSelectScreen;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import minercrystal.gamefield.Tools.HeartAnimation;
import minercrystal.gamefield.WorkScreens.Tools.InterfacePlayScreen.Coins;

public class MenuCoins {

    private boolean isDeployed = false, isDeployedFinished = false, isPlusPressed = false, isDown = true;
    private int COINS, LIVES, CRYSTALS;
    private float Y = 800, velocity = 1f;
    private final static float acceleration = 2, lowYPosition = 755, upperYPos = 800;
    private Coins Coins;

    public MenuCoins(Preferences preferences) {
        this.COINS = preferences.getInteger("Coins", 0);
        this.LIVES = preferences.getInteger("Lives", 0);
        this.CRYSTALS = preferences.getInteger("Crystals", 0);
        Coins = new Coins(CRYSTALS, COINS, LIVES, new Rectangle(480 / 2-45/2, 800, 45, 45), new Rectangle(27, 800, 33, 33), new Rectangle(367, 800, 33, 33), preferences);
    }

    public void render(SpriteBatch spriteBatch, float delta) {
        if (isDeployed) {
            if (isDown)
                goDown(delta);
            else
                goUp(delta);
        }
        spriteBatch.begin();
        Coins.renderCoins(spriteBatch, delta);
        Coins.renderCrystals(spriteBatch, delta);
        spriteBatch.end();
    }

    private void goDown(float delta) {
        velocity += acceleration * delta;
        Y -= velocity;
        if (Y <= lowYPosition) {
            Y = lowYPosition;
            isDeployedFinished = true;
            isDeployed = false;
        }
        changePos(0, Y);
    }

    private void goUp(float delta) {
        velocity += acceleration * delta;
        Y += velocity;
        if (Y >= upperYPos) {
            Y = upperYPos;
            isDeployedFinished = true;
            isDeployed = false;
        }
        changePos(0, Y);
    }

    public minercrystal.gamefield.WorkScreens.Tools.InterfacePlayScreen.Coins getCoins() {
        return Coins;
    }

    public int getCOINS() {
        return COINS;
    }

    public boolean isDown() {
        return isDown;
    }

    public void setDeployed(boolean deployed, boolean isDown) {
        isDeployed = deployed;
        this.isDown = isDown;
        velocity = 1f;
    }

    public boolean isDeployed() {
        return isDeployed;
    }

    private void changePos(float X, float Y) {
        Coins.changeCoinsPosition(X, Y);
        Coins.changeCrystalsPosition(X, Y);
        Coins.changeHeartPosition(X, Y);
    }
}

