package minercrystal.gamefield;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

import minercrystal.gamefield.AnimationScreens.BackgroundScreen;
import minercrystal.gamefield.AnimationScreens.SplashScreen;
import minercrystal.gamefield.Tools.MenuButtons;
import minercrystal.gamefield.WorkScreens.MenuSelectScreen;
import minercrystal.gamefield.WorkScreens.Tools.Managers.StoryManager.LegendManager;

public class MenuScreen implements Screen {
    private GameClass gameClass;
    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    private SplashScreen splashScreen;
    private BackgroundScreen backgroundScreen;
    private MenuButtons menuButtons;
    private float W_INDEX, H_INDEX;
    private BitmapFont font12;
    private AdsController adsController;
    private LegendManager legendManager;

    public MenuScreen(GameClass gameClass, AdsController adsController) {
        this.adsController = adsController;
        this.gameClass = gameClass;
        camera = new OrthographicCamera();
        camera = new OrthographicCamera(480, 800);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        W_INDEX = (float) (480.0 / Gdx.graphics.getWidth());
        H_INDEX = (float) (800.0 / Gdx.graphics.getHeight());
        backgroundScreen = new BackgroundScreen(480, 350, 100);
        spriteBatch = new SpriteBatch();
        splashScreen = new SplashScreen();
        setInputProcessor();
        FileHandle baseFileHandle = Gdx.files.internal("Bundles/Bundle");
        Locale locale = new Locale("ru");
        I18NBundle myBundle = I18NBundle.createBundle(baseFileHandle, locale);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/MainFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 27;
        parameter.color = new Color(111f, 55f, 19f, 1);
        font12 = generator.generateFont(parameter);
        menuButtons = new MenuButtons(font12, myBundle);
        legendManager = new LegendManager(Gdx.app.getPreferences("MainPreferences"), font12);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.setProjectionMatrix(camera.combined);
        if(!legendManager.isStoryDeployed())
        backgroundScreen.render(delta, spriteBatch);

        if (splashScreen.isSplashScreenActive())
            splashScreen.setSplashScreen(delta, spriteBatch);
        else {
            if (legendManager.isStoryDeployed()) {
                legendManager.render(spriteBatch, delta);
            } else {
                menuButtons.render(delta, spriteBatch);
                if (menuButtons.isPlayBtnPressed()) {
                    backgroundScreen.setSPEED(backgroundScreen.getSPEED() * 1.02f);
                    if (backgroundScreen.getSPEED() > 40) {
                        Color c = spriteBatch.getColor();

                        float alpha = 1 - (backgroundScreen.getSPEED() - 40) / 50;
                        if (alpha < 0.1) alpha = 0;
                        spriteBatch.setColor(c.r, c.g, c.b, alpha);
                    }
                    if (backgroundScreen.getSPEED() > 120) {
                        gameClass.setScreen(new MenuSelectScreen(gameClass, adsController, true));
                    }
                }
            }
        }


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        backgroundScreen.dispose();
    }

    private void setInputProcessor() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                menuButtons.reset();
                menuButtons.isContains((int) (screenX * W_INDEX), (int) (800 - screenY * H_INDEX));
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                //buttons.dragged((int) (screenX * W_INDEX), (int) (480 - screenY * H_INDEX));

                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                menuButtons.reset();
                return true;
            }
        });
    }
}
