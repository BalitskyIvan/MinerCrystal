package minercrystal.gamefield.AnimationScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

import minercrystal.gamefield.Tools.Button;

public class HeartMinusAnimation {

    private boolean isAnimEnd = false, isLoad = false, isStayLoad = false, nullMoneyToBuy = false;
    private Array<Texture> heartTextures;
    private int heartCount = 0, moneyToBuy;
    private static final int X = 100, Y = 350, H = 280, W = 280;
    private static final float accelerationAnimationGoToScreen = 0.01f, a = 1.1f, animDelta = 0.015f, stayDelta = 0.8f;
    private float speedAnimationGoToScreen = 0.03f, animAccumulator = 0, darkTimer = 0.12f;
    private int INDEX = 0;
    private BitmapFont font12;
    private Button btnBuy, btnYes, btnNo;
    private OrthographicCamera camera;
    private Preferences preferences;
    public HeartMinusAnimation(int heartCount, int moneyToBuy, String localeS, Preferences preferences) {
        this.heartCount = heartCount;
        this.moneyToBuy = 300;
        this.preferences = preferences;
        heartTextures = new Array<>();
        for (int i = 1; i < 10; i++) {
            heartTextures.add(new Texture("heartAnimation/" + i + ".png"));
        }
        btnBuy = new Button(new Texture("Buttons/StandartBtn/plusBtn1.png"), new Texture("Buttons/StandartBtn/plusBtn2.png"), new Rectangle(210, 300, 60, 30), localeS, 1);
        btnYes = new Button(new Texture("Buttons/StandartBtn/plusBtn1.png"), new Texture("Buttons/StandartBtn/plusBtn2.png"), new Rectangle(170, 300, 40, 30), localeS, 1);
        btnNo = new Button(new Texture("Buttons/StandartBtn/closeBtn1.png"), new Texture("Buttons/StandartBtn/closeBtn2.png"), new Rectangle(270, 300, 40, 30), localeS, 1);
        FileHandle baseFileHandle = Gdx.files.internal("Bundles/Bundle");
        Locale locale = new Locale(localeS);
        I18NBundle myBundle = I18NBundle.createBundle(baseFileHandle, locale);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/MainFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 34;
        parameter.color = new Color(111f, 55f, 19f, 1);
        font12 = generator.generateFont(parameter);
    }

    public void render(SpriteBatch batch, OrthographicCamera camera, Texture bb, float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(heartTextures.get(INDEX), X, Y, H, W);
        batch.end();
        if (!isLoad) {
            speedAnimationGoToScreen += (accelerationAnimationGoToScreen * delta);
            speedAnimationGoToScreen *= a;
            camera.zoom -= speedAnimationGoToScreen;
            camera.update();
            batch.setProjectionMatrix(camera.combined);
            Color c = batch.getColor();
            batch.setColor(c.r, c.g, c.b, (camera.zoom - 0.6f) / 1.9f);
            batch.begin();
            batch.draw(bb, 0, 0, 480, 800);
            batch.end();
            if (camera.zoom < 0.6f) {
                speedAnimationGoToScreen = 0.03f;
                camera.zoom = 0.6f;
                camera.update();
                isLoad = true;
                this.camera = camera;
            }
            batch.setProjectionMatrix(camera.combined);
            batch.setColor(c.r, c.g, c.b, 1);
        } else {
            animAccumulator += delta;
            if (heartCount <= 0) {
                if (moneyToBuy == 0) {
                    nullMoneyToBuy = true;
                    btnYes.render(batch, delta);
                    btnNo.render(batch, delta);
                } else {
                    btnBuy.render(batch, delta);
                }

            } else {
                batch.begin();
                font12.draw(batch, String.valueOf(heartCount), X + 130, Y + 40);
                batch.end();
                if (animAccumulator > stayDelta) {
                    isStayLoad = true;
                    heartCount--;
                    animAccumulator = 0;
                }
            }
            if (animAccumulator > animDelta && isStayLoad) {
                speedAnimationGoToScreen += (accelerationAnimationGoToScreen * delta);
                speedAnimationGoToScreen *= a;
                animAccumulator = 0;
                darkTimer -= delta;
                camera.zoom -= speedAnimationGoToScreen;
                camera.update();

                batch.setProjectionMatrix(camera.combined);
                Color c = batch.getColor();
                batch.setColor(c.r, c.g, c.b, 1 - darkTimer / 0.12f);
                batch.begin();
                batch.draw(bb, 0, 0, 480, 800);
                batch.end();
                INDEX++;
                if (INDEX == 9) {
                    isAnimEnd = true;
                    batch.setProjectionMatrix(camera.combined);
                    batch.setColor(c.r, c.g, c.b, 1);
                    batch.begin();
                    batch.draw(bb, 0, 0, 480, 800);
                    batch.end();

                }


            }
        }

    }

    public boolean isLoad() {
        return isLoad;
    }

    public void touchDown(float X, float Y) {
        if (!nullMoneyToBuy) {
            if(btnBuy.touchDown(X, Y))
                isStayLoad = true;
            preferences.putInteger("Coins",preferences.getInteger("Coins")-300);
        } else {
            btnYes.touchDown(X, Y);
            btnNo.touchDown(X, Y);
        }
    }

    public boolean isAnimEnd() {
        return isAnimEnd;
    }
}
