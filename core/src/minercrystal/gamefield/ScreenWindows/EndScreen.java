package minercrystal.gamefield.ScreenWindows;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import minercrystal.gamefield.Animation.PersonAnimation;
import minercrystal.gamefield.Tools.CoinAnimation;
import minercrystal.gamefield.Tools.PauseBtn;
import minercrystal.gamefield.Tools.StarAnimation;
import minercrystal.gamefield.WorkScreens.Tools.InterfacePlayScreen.Coins;
import minercrystal.gamefield.WorkScreens.Tools.InterfacePlayScreen.TimeLapse;

public class EndScreen {

    private Rectangle buttonPauseRect, buttonEndRect;
    private Texture bb;
    private Coins coins;
    private StarAnimation starAnimation;
    private String state;
    private final static float rectH = 60, rectW = 60, DELTA_Y = 65, rectX = 415, rectYStart = 500, deltaCampFire = 0.11f, fireX = 160, fireY = 400, fireHW = 160, addTimer = 10;
    private Array<TextureRegion> campFire;
    private Array<Texture> loseFire;
    private PersonAnimation minerAnim;
    private boolean isDeploy = false, isDeployEnd = false, isWin = false, isPauseTextReverse = false, isReverse = false, isLoseEnd = false, isLose = false, isTimeOverReverse = false, loseButtonsDeploy = false, isSellCrystals = false;
    private float alpha = 0, campFireAccumulator = 0, loseAlpha = 1, loseFireAccumulator = 0, adTimerAcc = addTimer, pauseTextAlpha = 0.3f;
    private final static float speed = 1.8f, loseSpeed = 0.8f, loseFireSpeed = 0.05f, pauseTextSpeed = 0.01f;
    private PauseBtn pauseBtn;
    private Rectangle buyLiveButton;
    private int campFireINDEX = 0, coinPlus = 0, starsCount = 0, loseFireINDEX = 0;
    private BitmapFont bitmapFont, loseFont;
    private Texture buyBtn;
    private TimeLapse timeLapse;
    
    public EndScreen(Coins coin, Texture bb, FreeTypeFontGenerator generator, PauseBtn pauseBtn, TimeLapse timeLapse, PersonAnimation minerAnim) {
        this.coins = coin;
        this.bb = bb;
        this.minerAnim = minerAnim;
        this.pauseBtn = pauseBtn;
        this.timeLapse = timeLapse;
        loseFire = new Array<>();
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        loseFont = generator.generateFont(parameter);
        parameter.size = 42;
        bitmapFont = generator.generateFont(parameter);
        starAnimation = new StarAnimation();

        buyLiveButton = new Rectangle(190, 60, 100, 80);
        buyBtn = new Texture("Buttons/loseBtn/btn.png");
        campFire = new Array<>();
        Texture fireMap = new Texture("tools/CampFire.png");
        for (int i = 0; i < 5; i++)
            campFire.add(new TextureRegion(fireMap, 64 * i, 0, 64, 64));
        for (int i = 1; i <= 25; i++)
            loseFire.add(new Texture("tools/fireAnim/" + i + ".png"));
        buttonPauseRect = new Rectangle(180, 200, 120, 120);
        buttonEndRect = new Rectangle(120, 200, 240, 120);

    }

    public void deploy(String state, int coinsPlus, int starsCount) {
        this.state = state;
        this.coinPlus = coinsPlus;
        this.starsCount = starsCount;
        isDeploy = true;
        isReverse = false;
        isDeployEnd = false;
        if(state.contains("WIN")) {
            loseAlpha = 0;
            pauseBtn.setPToNull();
        }
    }

    public void render(SpriteBatch batch, OrthographicCamera camera, float delta) {

        if (isDeploy) {
            update(delta);

            Color c = batch.getColor();
            batch.setColor(c.r, c.g, c.b, alpha);
            batch.begin();
            batch.draw(bb, 0, 0, 480, 800);
            batch.end();

            if (isDeployEnd) {
                switch (state) {
                    case "PAUSE":
                        campFireAccumulator += delta;
                        if (campFireAccumulator > deltaCampFire) {
                            campFireAccumulator = 0;
                            campFireINDEX++;
                            if (campFireINDEX == 5) {
                                campFireINDEX = 0;
                            }
                        }
                        batch.begin();
                        bitmapFont.setColor(255, 255, 255, pauseTextAlpha);
                        bitmapFont.draw(batch, "PAUSE", 167, 700);
                        batch.draw(campFire.get(campFireINDEX), fireX, fireY, fireHW, fireHW);
                        pauseBtn.render(batch, delta);
                        batch.end();
                        break;
                    case "WIN":
                        batch.begin();
                        coins.renderCoins(batch, delta);
                        starAnimation.render(batch, delta);
                        batch.end();

                        batch.setColor(c.r, c.g, c.b, loseAlpha);
                        batch.begin();
                        batch.draw(bb, 0, 0, 480, 800);
                        batch.end();

                        batch.setColor(c.r, c.g, c.b, 1);
                        batch.begin();
                        pauseBtn.render(batch, delta);
                        batch.end();
                        break;
                    case "LOSE":
                        batch.begin();
                        if (isLoseEnd) {
                            batch.draw(loseFire.get(loseFireINDEX), buyLiveButton.getX() - 85, buyLiveButton.getY() + 30, buyLiveButton.getWidth() + 170, 260);
                            batch.draw(buyBtn, buyLiveButton.getX(), buyLiveButton.getY(), buyLiveButton.getWidth(), buyLiveButton.getHeight());
                            if (isSellCrystals) {
                             coins.renderCrystals(batch, delta, true, 1, new Rectangle(buyLiveButton.getX() + 20, buyLiveButton.getY()+20, 40, 40));
                            }
                        }
                        batch.end();

                        batch.setColor(c.r, c.g, c.b, loseAlpha);
                        batch.begin();
                        if (!isLoseEnd)
                            bitmapFont.draw(batch, "Time is over", 100, 500);
                        else {
                            if (isSellCrystals) {
                                loseFont.draw(batch, "Do you want to resume?", 4, 600);
                            } else {
                                loseFont.draw(batch, "Do you want to watch", 22, 700);
                                loseFont.draw(batch, "an" + "     " + "   and resume?", 50, 665);
                                loseFont.setColor(Color.GOLD);
                                loseFont.draw(batch, "ad", 112, 665);
                                loseFont.setColor(Color.WHITE);
                                bitmapFont.draw(batch, "YES", buyLiveButton.getX() + 8, buyLiveButton.getY() + 50);
                            }
                            bitmapFont.draw(batch, String.valueOf((int) adTimerAcc), 230, 500);

                        }
                        batch.draw(bb, 0, 0, 480, 800);
                        timeLapse.render(batch, delta, true);
                        batch.end();
                        batch.setColor(c.r, c.g, c.b, 1);

                        break;
                }

            }
            batch.setColor(c.r, c.g, c.b, 1);

        }
    }

    private void update(float delta) {
        switch (state) {
            case "PAUSE":
                if (!isDeployEnd) {
                    if (!isReverse) {
                        alpha += delta * speed;
                        if (alpha > 1) {
                            pauseBtn.deploy(true, buttonPauseRect);
                            isDeployEnd = true;
                            alpha = 1;
                        }
                    } else {
                        alpha -= delta * speed;
                        if (alpha <= 0.0f) {
                            pauseBtn.deploy(false, new Rectangle(rectX, rectYStart + DELTA_Y * 3, rectW, rectH));
                            alpha = 0;
                            isDeploy = false;
                        }
                    }
                } else {
                    if (isPauseTextReverse) {
                        pauseTextAlpha -= pauseTextSpeed;
                        if (pauseTextAlpha < 0.3f)
                            isPauseTextReverse = false;
                    } else {
                        pauseTextAlpha += pauseTextSpeed;
                        if (pauseTextAlpha >= 1f)
                            isPauseTextReverse = true;

                    }
                }
                break;
            case "WIN":
                if (!isDeployEnd) {
                    if (!isReverse) {
                        alpha += delta * speed;
                        if (alpha > 1) {
                            isDeployEnd = true;
                            alpha = 1;
                            coins.changePosRect(new Rectangle(200, 630, 35, 35), 140, 1f, 2.75f);
                            coins.addCoin(coinPlus / 4, -800, 0, 800);
                            coins.addCoin(coinPlus / 4, 0, -480, 480);
                            coins.addCoin(coinPlus / 4, 800, 0, 800);
                            coins.addCoin(coinPlus / 4, 0, 800, 800);
                            starAnimation.addStars(3);
                        }
                    } else {

                    }
                }else{
                    if(starAnimation.isStop()){
                       pauseBtn.winDeploy(buttonPauseRect);
                    }
                    if(isReverse) {
                        loseAlpha += delta*speed / 2f;
                        if (loseAlpha >= 1.1f) {
                            loseAlpha = 1;
                            isWin = true;
                            isDeploy = false;
                        }
                    }
                }
                break;
            case "LOSE":
                if (!isDeployEnd) {

                    if (!isReverse) {
                        alpha += delta * speed;
                        if (alpha > 1) {
                            isDeployEnd = true;
                            alpha = 1;
                        }
                    } else {
                        alpha -= delta * speed;
                        if (alpha <= 0.0f) {
                            alpha = 0;
                            isDeploy = false;
                        }
                    }
                } else {
                    if (isLoseEnd) {
                        loseFireAccumulator += delta;

                            adTimerAcc -= delta;
                        if (adTimerAcc <= 0)
                            isLose = true;
                        if (loseFireAccumulator > loseFireSpeed) {
                            loseFireAccumulator = 0;
                            loseFireINDEX++;
                            if (loseFireINDEX == 25)
                                loseFireINDEX = 0;
                        }
                        if (loseAlpha > 0) {
                            loseAlpha -= delta * loseSpeed;
                            if (loseAlpha < 0)
                                loseAlpha = 0;
                        } else {

                            loseButtonsDeploy = true;
                        }
                    } else {
                        if (!isTimeOverReverse) {
                            loseAlpha -= delta * loseSpeed;
                            if (loseAlpha <= 0) {
                                isTimeOverReverse = true;
                                loseAlpha = 0;
                            }
                        } else {
                            loseAlpha += delta * loseSpeed;
                            if (loseAlpha >= 1) {
                                isLoseEnd = true;
                                loseAlpha = 1;
                                if (coins.getCrystals() >= 1)
                                    isSellCrystals = true;
                            }
                        }
                    }

                }
                break;
        }


    }

    public boolean isLose() {
        return isLose;
    }

    public boolean isDeploy() {
        return isDeployEnd;
    }

    public boolean isReverse() {
        return isReverse;
    }

    public PauseBtn getPauseBtn() {
        return pauseBtn;
    }

    public boolean isDeploying() {
        return isDeploy;
    }

    public boolean isWin() {
        return isWin;
    }

    public void touchDown(float X, float Y) {
        if (isDeployEnd) {
            switch (state) {
                case "PAUSE":
                    if (buttonPauseRect.contains(X, Y)) {
                        isReverse = true;
                        isDeployEnd = false;
                        pauseBtn.setReverse();
                    }
                    break;
                case "WIN":
                   if(pauseBtn.touchDown(X, Y)) {
                       isReverse = true;
                   }
                    break;
                case "LOSE":
                    if (loseButtonsDeploy) {
                        if (isSellCrystals) {
                            if (buyLiveButton.contains(X, Y)) {
                                coins.delCrystal(1);
                                timeLapseCharge();
                                isDeployEnd = false;
                                isReverse = true;
                                isLoseEnd = false;
                                isLose = false;
                            }
                        } else {
                            if (buyLiveButton.contains(X, Y)) {
                                timeLapseCharge();
                                isDeployEnd = false;
                                isReverse = true;
                                isLose = false;
                                isLoseEnd = false;
                            }
                        }
                    }
                    break;
            }
        }
    }
    private void timeLapseCharge(){
        Array<Vector2> it = new Array<>();
        Array<Integer> modes = new Array<>();
        Random r = new Random();
        for(int i = 0; i < 60; i++){
            it.add(new Vector2(buyLiveButton.getX()-40+r.nextInt(90), buyLiveButton.getY()-40 + r.nextInt(90)));
            modes.add(r.nextInt(3)+1);
        }
        timeLapse.plus(it, modes);
        timeLapse.setLose(false);
    }

    public void dispose() {


    }
}
