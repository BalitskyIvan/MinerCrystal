package minercrystal.gamefield.ScreenWindows;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import minercrystal.gamefield.Buttons.CloseBtn;
import minercrystal.gamefield.SpinWheel.SpinWheel;
import minercrystal.gamefield.WorkScreens.Tools.InterfacePlayScreen.Coins;
import minercrystal.gamefield.WorkScreens.Tools.InterfaceSelectScreen.MenuCoins;
import minercrystal.gamefield.WorkScreens.Tools.InterfaceSelectScreen.WinScreen;
import minercrystal.gamefield.WorkScreens.Tools.WorkButton;

public class SpinWheelWindow {
    private boolean isActive = false, isDeployEnd = false, isSpin = false, isReverse = false;
    private SpinWheel spinWheel;
    private Texture bb;
    private WorkButton wheelButton;
    private Random r;
    private float alpha = 0;
    private final static float alphaSpeed = 0.9f;
    private MenuCoins menuCoins;
    private WinScreen winScreen;
    private CloseBtn closeBtn;
    private Array<Texture> winElement;
    private Preferences prefs;
    public SpinWheelWindow(Texture bb, WorkButton wheelButton, MenuCoins menuCoins, Preferences preferences) {
        this.bb = bb;
        this.prefs = preferences;
        this.menuCoins = menuCoins;
        this.wheelButton = wheelButton;
        spinWheel = new SpinWheel(480, 800, 380, 240, 400, 6);
        r = new Random();
        closeBtn = new CloseBtn(new Rectangle(420, 730, 40, 40));
        winScreen = new WinScreen(menuCoins);
        winElement = new Array<>();
        for(int i = 1; i<=6; i++){
            winElement.add(new Texture("spinWheel/elements/"+i+".png"));
        }
    }

    public void render(SpriteBatch batch, float delta) {
        Color c = batch.getColor();
        batch.setColor(c.r, c.g, c.b, alpha);
        batch.begin();
        batch.draw(bb, 0, 0, 480, 800);
        batch.end();

        spinWheel.render(false, batch);
        wheelButton.render(delta, batch);

        batch.begin();
        if (!isSpin && isActive && isDeployEnd&& wheelButton.isButtonDeploed())
            menuCoins.getCoins().renderCrystals(batch, delta, true, 1, new Rectangle(wheelButton.getBtnRectangle().getX() + 20, wheelButton.getBtnRectangle().getY() + 20, 40, 40));
        closeBtn.render(batch, delta);
        batch.end();

        if (isActive && !isDeployEnd && !wheelButton.isButtonDeploed()) {
            if(isReverse){
                if (alpha > 0) {
                    alpha -= alphaSpeed * delta;
                } else {
                    alpha = 0;
                    isDeployEnd = true;
                    isActive = false;
                    isReverse = false;
                    wheelButton.changeRect(new Rectangle(20, 150, 100, 100));
                    wheelButton.setButtonDeploy(true);
                }
            }else {
                if (alpha < 1) {
                    alpha += alphaSpeed * delta;
                } else {
                    alpha = 1;
                    isDeployEnd = true;
                    wheelButton.changeRect(new Rectangle(190, 100, 100, 100));
                    wheelButton.setButtonDeploy(true);
                    closeBtn.setDeploying(true);

                }
            }
        }
        if(winScreen.isAnimFinished() && winScreen.isDeployDestroy()&& !wheelButton.isActive() && !wheelButton.isButtonDeploed() && isActive){
            wheelButton.setButtonDeploy(true);
            closeBtn.setDeploying(true);
        }
        if (isSpin) {
            if (spinWheel.spinningStopped()) {
                isSpin = false;
             //   winScreen.setDeploying(true, 4, prefs);
               winScreen.setDeploying(true, spinWheel.getLuckyWinElement(), prefs);
            }
        }
        winScreen.render(batch, delta);
    }

    public void setDeployTrue() {
        isActive = true;
        isDeployEnd = false;
    }

    public void touchDown(float X, float Y) {
        if(!isSpin&&!winScreen.isActive()) {
            if (wheelButton.touchDown(X, Y)) {
                closeBtn.setDistruct();
                isSpin = true;
                r.nextInt();
                spinWheel.spin(12 + r.nextInt(16));
            }
            if (closeBtn.touchDown(X, Y)) {
                wheelButton.setReverse(true);
                isReverse = true;
                isDeployEnd = false;
                menuCoins.setDeployed(true, true);
            }
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void dispose() {
        spinWheel.dispose();
        winScreen.dispose();
    }
}
