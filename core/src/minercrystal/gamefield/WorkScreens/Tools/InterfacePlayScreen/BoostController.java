package minercrystal.gamefield.WorkScreens.Tools.InterfacePlayScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

import minercrystal.gamefield.MovingHandler;
import minercrystal.gamefield.Objects.Item;
import minercrystal.gamefield.ScreenWindows.EndScreen;
import minercrystal.gamefield.Tools.PauseBtn;

public class BoostController implements Boost {

    private Boost boostBomb;
    private BoostRow boostRow;
    private BoostCoin boostCoins;
    private Array<Array<Item>> Items;
    private Preferences preferences;
    private MovingHandler handler;
    private final static float rectH = 60, rectW = 60, DELTA_Y = 65, rectX = 415, rectYStart = 445;
    private int i = 0;
    private EndScreen endScreen;
    private PauseBtn pauseBtn;
    private boolean isMenu = false;

    public BoostController(Array<Array<Item>> Items, Preferences preferences, MovingHandler handler, OrthographicCamera camera, EndScreen endScreen) {
        this.Items = Items;
        this.preferences = preferences;
        this.handler = handler;
        this.endScreen = endScreen;


        int bombsCount = preferences.getInteger("bombsBoost", 30);
        int rowCount = preferences.getInteger("rowBoost", 7);
        int coinsCount = preferences.getInteger("coinsBoost", 7);

        FileHandle baseFileHandle = Gdx.files.internal("Bundles/Bundle");
        Locale locale = new Locale("ru");
        I18NBundle myBundle = I18NBundle.createBundle(baseFileHandle, locale);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/MainFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        parameter.color = new Color(111f, 55f, 19f, 1);
        BitmapFont font12 = generator.generateFont(parameter);


        Array<Texture> btnTexture = new Array<>();
        for (i = 12; i >= 1; i--)
            btnTexture.add(new Texture("boost/boostButton/" + i + ".png"));
        if (bombsCount > 0) {
            boostBomb = new BoostBomb(bombsCount, Items, handler, new Rectangle(rectX, rectYStart, rectW, rectH), btnTexture, camera, font12);
            i++;
        }

        if (coinsCount > 0) {
            boostCoins = new BoostCoin(coinsCount, new Rectangle(rectX, rectYStart + DELTA_Y * i, rectW, rectH), btnTexture, font12);
            i++;
        }
        if (rowCount > 0) {
            boostRow = new BoostRow(rowCount, Items, handler, new Rectangle(rectX, rectYStart + DELTA_Y * i, rectW, rectH), btnTexture, font12);
        }
        pauseBtn = endScreen.getPauseBtn();
        pauseBtn.deploy(false, new Rectangle(rectX, rectYStart + DELTA_Y * 3 + 30, rectW, rectH));
    }

    public BoostController(Preferences preferences) {
        this.preferences = preferences;
        int bombsCount = preferences.getInteger("bombsBoost", 30);
        int rowCount = preferences.getInteger("rowBoost", 7);
        int coinsCount = preferences.getInteger("coinsBoost", 7);

        FileHandle baseFileHandle = Gdx.files.internal("Bundles/Bundle");
        Locale locale = new Locale("ru");
        I18NBundle myBundle = I18NBundle.createBundle(baseFileHandle, locale);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/MainFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        parameter.color = new Color(111f, 55f, 19f, 1);
        BitmapFont font12 = generator.generateFont(parameter);

        Array<Texture> btnTexture = new Array<>();
        for (i = 12; i >= 1; i--)
            btnTexture.add(new Texture("boost/boostButton/" + i + ".png"));
        if (bombsCount > 0) {
            boostBomb = new BoostBomb(bombsCount, Items, handler, new Rectangle(rectX, rectYStart, rectW, rectH), btnTexture, null, font12);
            i++;
        }

        if (coinsCount > 0) {
            boostCoins = new BoostCoin(coinsCount, new Rectangle(rectX, rectYStart + DELTA_Y * i, rectW, rectH), btnTexture, font12);
            i++;
        }
        if (rowCount > 0) {
            boostRow = new BoostRow(rowCount, Items, handler, new Rectangle(rectX, rectYStart + DELTA_Y * i, rectW, rectH), btnTexture, font12);
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch, float delta) {
        if (!isPause()) {
            if (boostBomb != null) {
                boostBomb.render(spriteBatch, delta);
                if (boostBomb.getCount() == -1 && !isMenu) {
                    boostBomb = null;
                    if (boostCoins != null) {
                        boostCoins.setGoToY(rectYStart);
                        if (boostRow != null)
                            boostRow.setGoToY(rectYStart + DELTA_Y);
                    } else if (boostRow != null)
                        boostRow.setGoToY(rectYStart);
                }
            }
            if (boostCoins != null) {
                boostCoins.render(spriteBatch, delta);
                if (boostCoins.getCount() == -1 && !isMenu) {
                    boostCoins = null;
                    if (boostBomb != null) {
                        if (boostRow != null)
                            boostRow.setGoToY(rectYStart + DELTA_Y);
                    } else if (boostRow != null)
                        boostRow.setGoToY(rectYStart);
                }

            }
            if (boostRow != null) {
                boostRow.render(spriteBatch, delta);
                if (boostRow.getCount() == -1 && !isMenu)
                    boostRow = null;
            }
        }
        if (pauseBtn != null) {
            pauseBtn.render(spriteBatch, delta);
            if (!pauseBtn.isPause() && pauseBtn.isDeploy() && pauseBtn.isReverse()) {
                endScreen.deploy("PAUSE", 0, 0);
            }
        }
    }

    public boolean isCanBlow() {
        return boostRow.isCanBlow();
    }

    public int getCoinBoost() {
        if (boostCoins != null)
            if (boostCoins.isActive())
                return boostCoins.getCoinBoost();
            else
                return 1;
        else return 1;
    }

    public void isRow(int SX1, int SY1) {
        if (boostRow != null && SX1 != -1)
            if (boostRow.isActive() && !boostRow.isLaserDeploy()) {
                boostRow.setLaserDeploy(new Vector2(SX1, SY1));
            }
    }

    public boolean isPause() {
        if (pauseBtn == null)
            return false;
        else
            return pauseBtn.isActive();
    }

    @Override
    public boolean touchDown(float X, float Y) {
        if (!isPause()) {
            if (boostBomb != null)
                if (boostBomb.getCount() > 0)
                    boostBomb.touchDown(X, Y);

            if (boostCoins != null)
                if (boostCoins.getCount() > 0)
                    boostCoins.touchDown(X, Y);

            if (boostRow != null)
                if (boostRow.getCount() > 0)
                    boostRow.touchDown(X, Y);
        }
        if (!endScreen.isDeploying()) {
            if (pauseBtn.touchDown(X, Y)) {
                pauseBtn.setReverse();
            }
        } else {
            endScreen.touchDown(X, Y);
        }

        return false;
    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean setGoToY(float Y) {
        return false;
    }

    @Override
    public Rectangle getElementRect(String element) {
        switch (element) {
            case "coins":
                return boostCoins.getElementRect(null);
            case "bomb":
                return boostBomb.getElementRect(null);
            case "row":
                return boostRow.getElementRect(null);

        }
        return null;
    }

    @Override
    public void plusElement(String element, int count) {
        switch (element) {
            case "coins":
                boostCoins.plusElement(null, count);
                boostBomb.plusElement("delete", 0);
                boostRow.plusElement("delete", 0);
                preferences.putInteger("coinsBoost", boostCoins.getCount());
                break;

            case "bomb":
                boostBomb.plusElement(null, count);
                boostCoins.plusElement("delete", 0);
                boostRow.plusElement("delete", 0);
                preferences.putInteger("bombsBoost", boostBomb.getCount());
                break;
            case "row":
                boostRow.plusElement(null, count);
                boostBomb.plusElement("delete", 0);
                boostCoins.plusElement("delete", 0);
                preferences.putInteger("rowBoost", boostRow.getCount());
                break;
        }
        preferences.flush();
    }


}
