package minercrystal.gamefield.WorkScreens.Tools.InterfacePlayScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;
import java.util.Random;

import minercrystal.gamefield.Tools.CoinAnimation;
import minercrystal.gamefield.Tools.CrystalAnimation;
import minercrystal.gamefield.Tools.HeartAnimation;

public class Coins {
    CoinAnimation coinAnimation;
    CrystalAnimation crystalAnimation;
    HeartAnimation heartAnimation;
    BitmapFont font12;
    private int coins, crystals, hearts;
    private Rectangle coinRect, crystalRect, heartRect;
    private Array<Rectangle> coinsRects, crystalsRects;
    private Random random;
    private Array<Vector2> coinsSpeed, crystalsSpeed;
    private float speedDel = 60;
    private Sound sound;
    private Preferences preferences;

    public Coins(int crystals, int coins, Rectangle coinRect, Rectangle crystalRect, Preferences preferences) {
        this.crystals = crystals;
        this.coins = coins;
        this.preferences = preferences;
        this.crystalRect = crystalRect;
        this.coinRect = coinRect;
        random = new Random();
        coinsSpeed = new Array<Vector2>();
        crystalAnimation = new CrystalAnimation(crystalRect.x, crystalRect.y);
        coinAnimation = new CoinAnimation(coinRect.x, coinRect.y);
        coinsRects = new Array<Rectangle>();
        crystalsRects = new Array<>();
        crystalsSpeed = new Array<>();
        sound = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
        FileHandle baseFileHandle = Gdx.files.internal("Bundles/Bundle");
        Locale locale = new Locale("ru");
        I18NBundle myBundle = I18NBundle.createBundle(baseFileHandle, locale);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/MainFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 34;
        parameter.color = new Color(111f, 55f, 19f, 1);
        font12 = generator.generateFont(parameter);
    }
    public Coins(int crystals, int coins, int hearts, Rectangle heartRect, Rectangle coinRect, Rectangle crystalRect, Preferences preferences) {
        this.crystals = crystals;
        this.coins = coins;
        this.preferences = preferences;
        this.crystalRect = crystalRect;
        this.coinRect = coinRect;
        this.heartRect = heartRect;
        this.hearts = hearts;
        random = new Random();
        coinsSpeed = new Array<Vector2>();
        crystalAnimation = new CrystalAnimation(crystalRect.x, crystalRect.y);
        coinAnimation = new CoinAnimation(coinRect.x, coinRect.y);
        heartAnimation = new HeartAnimation(heartRect);
        coinsRects = new Array<Rectangle>();
        crystalsRects = new Array<>();
        crystalsSpeed = new Array<>();
        sound = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
        FileHandle baseFileHandle = Gdx.files.internal("Bundles/Bundle");
        Locale locale = new Locale("ru");
        I18NBundle myBundle = I18NBundle.createBundle(baseFileHandle, locale);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/MainFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 34;
        parameter.color = new Color(111f, 55f, 19f, 1);
        font12 = generator.generateFont(parameter);
    }
    public void renderCoins(SpriteBatch spriteBatch, float delta) {
        coinAnimation.render(spriteBatch, delta);
        font12.draw(spriteBatch, String.valueOf(coins), coinAnimation.getPosition().x + 37, coinAnimation.getPosition().y + 29);
        if (coinsRects.size > 0) {
            for (int i = 0; i < coinsRects.size; i++) {
                coinsRects.get(i).y += coinsSpeed.get(i).y;
                coinsRects.get(i).x += coinsSpeed.get(i).x;
                if (coinsRects.get(i).overlaps(coinRect)) {
                    sound.play(0.4f);

                    coinsSpeed.removeValue(coinsSpeed.get(i), false);
                    coinsRects.removeValue(coinsRects.get(i), false);
                    coinAnimation.kick();
                    coins++;
                } else {
                    spriteBatch.draw(coinAnimation.getFirstTextureCoin(), coinsRects.get(i).x, coinsRects.get(i).y, coinsRects.get(i).width, coinsRects.get(i).height);
                }
            }
        }
    }

    public void renderCrystals(SpriteBatch spriteBatch, float delta, boolean isDrawByMenu, int value, Rectangle pos) {
        if (!isDrawByMenu) {
            crystalAnimation.render(spriteBatch, delta);
            font12.draw(spriteBatch, String.valueOf(crystals), crystalAnimation.getPosition().x + 37, crystalAnimation.getPosition().y + 29);
            if (crystalsRects.size > 0) {
                for (int i = 0; i < crystalsRects.size; i++) {
                    crystalsRects.get(i).y += crystalsSpeed.get(i).y;
                    crystalsRects.get(i).x += crystalsSpeed.get(i).x;
                    if (crystalsRects.get(i).overlaps(crystalRect)) {
                        sound.play();

                        crystalsSpeed.removeValue(crystalsSpeed.get(i), false);
                        crystalsRects.removeValue(crystalsRects.get(i), false);
                        crystalAnimation.kick();
                        crystals++;
                    } else {
                        //   spriteBatch.draw(crystalAnimation.getFirstTextureCoin(),  crystalsRects.get(i).x,  crystalsRects.get(i).y,  crystalsRects.get(i).width,  crystalsRects.get(i).height);
                    }
                }
            }
        } else {
            font12.draw(spriteBatch, String.valueOf(value), pos.getX() + 47, pos.getY() + 35);
            spriteBatch.draw(crystalAnimation.getCrystalAtlas(), pos.getX(), pos.getY(), pos.getWidth(), pos.getHeight());

        }
    }

    public void renderCrystals(SpriteBatch spriteBatch, float delta) {
        font12.draw(spriteBatch, String.valueOf(crystals), crystalAnimation.getPosition().x + 37, crystalAnimation.getPosition().y + 29);
        crystalAnimation.render(spriteBatch, delta);
    }
    public void renderHearts(SpriteBatch spriteBatch, float delta) {
        font12.draw(spriteBatch, String.valueOf(hearts), heartAnimation.getPosition().x + 50, heartAnimation.getPosition().y + 29);
        heartAnimation.render(spriteBatch, delta);
    }

    public void addCrystals(int count, float X, float Y, int deltaRandom, boolean isWheel) {
        for (int i = 0; i < count; i++) {
            Rectangle r = new Rectangle(X + random.nextInt(deltaRandom), Y + random.nextInt(deltaRandom), 35, 35);
            crystalsRects.add(r);
            crystalsSpeed.add(new Vector2(((crystalRect.x) - r.x) / speedDel, ((crystalRect.y) - r.y) / speedDel));
        }

    }

    public void addCrystals() {
        crystals++;
        preferences.putInteger("Coins", crystals);
        preferences.flush();
    }

    public void changePosRect(Rectangle rectangle, float speedDel, float deltaFrames, float splashTimer) {
        this.coinRect = rectangle;
        this.speedDel = speedDel;
        coinAnimation.setPosition(new Vector2(coinRect.getX(), coinRect.getY()), deltaFrames, splashTimer);
    }

    public int getCoins() {
        return coins;
    }

    public void changeCrystalsPosition(float X, float Y) {
        crystalAnimation.changePosition(X, Y);
    }

    public void changeCoinsPosition(float X, float Y) {
        coinAnimation.changePosition(X, Y);
    }

    public void changeHeartPosition(float X, float Y) {
        if (heartAnimation != null)
            heartAnimation.changePosition(X, Y);
    }

    public void addCoin(int boost, float X, float Y, int WHBLOCK) {
        for (int i = 0; i < boost; i++) {
            Rectangle r = new Rectangle(X + random.nextInt(WHBLOCK), Y + random.nextInt(WHBLOCK), 35, 35);
            coinsRects.add(r);
            coinsSpeed.add(new Vector2(((coinRect.x) - r.x) / speedDel, ((coinRect.y) - r.y) / speedDel));
        }
    }

    public Rectangle getCoinRect() {
        return coinRect;
    }

    public Rectangle getCrystalRect() {
        return crystalAnimation.getPosition();
    }

    public Rectangle getHeartRect() {
        return heartAnimation.getPosition();
    }

    public int getCrystals() {
        return crystals;
    }

    public void delCrystal(int count) {
        crystals -= count;
        preferences.putInteger("Crystals", getCrystals());
        preferences.flush();
    }

    public void destroy() {

    }

    public void addHearts() {
        heartAnimation.startBeatingAnimation();
        hearts++;
        preferences.putInteger("Lives", hearts);
        preferences.flush();
    }
}
