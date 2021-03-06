package minercrystal.gamefield.WorkScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

import minercrystal.gamefield.AdsController;
import minercrystal.gamefield.Animation.MenuSelectScreenAnimation;
import minercrystal.gamefield.AnimationScreens.HeartMinusAnimation;
import minercrystal.gamefield.FrameInterface;
import minercrystal.gamefield.GameClass;
import minercrystal.gamefield.ScreenWindows.SpinWheelWindow;
import minercrystal.gamefield.Tools.Selector;
import minercrystal.gamefield.WorkScreens.Tools.BackMenuSelectScreen;
import minercrystal.gamefield.WorkScreens.Tools.CameraScroller;

import minercrystal.gamefield.WorkScreens.Tools.Frames.PresentFrame;
import minercrystal.gamefield.WorkScreens.Tools.InterfaceSelectScreen.MenuCoins;
import minercrystal.gamefield.WorkScreens.Tools.WorkButton;

public class MenuSelectScreen implements Screen {
    GameClass gameClass;
    SpriteBatch spriteBatch;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera, buttonsCam;
    float W_INDEX, H_INDEX;
    int[] backgroundLayers = {0, 1, 2, 3, 4}; // don't allocate every frame!
    int[] foregroundLayers;    // don't allocate every frame!
    float firstX, firstY, selectR = 400, selectL = 27, DELTA_Y_SELECT = 79.9f, last;
    Selector select;
    int Location, endLevel, mapNum;
    Array<Integer> endsOfLevels;
    boolean isLeft, animationGoTOScreen = false, isWin = false, isCanTouch = true, animationGoToNextLocation = false, minusAnim = false;
    Preferences mainPrefs;
    BackMenuSelectScreen backMenuSelectScreen;
    CameraScroller cameraScroller;
    WorkButton presentButton, wheelButton;
    AdsController adsController;
    MenuSelectScreenAnimation menuSelectScreenAnimation;
    MenuCoins menuCoins;
    HeartMinusAnimation heartMinusAnimation;
    private SpinWheelWindow spinWheelWindow;
    public MenuSelectScreen(GameClass gameClass, AdsController adsController, boolean isWin) {
        this.adsController = adsController;
        this.gameClass = gameClass;
        this.isWin = isWin;
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("music.ogg"));
        spriteBatch = new SpriteBatch();
        buttonsCam = new OrthographicCamera(480, 800);
        buttonsCam.position.set(buttonsCam.viewportWidth / 2f, buttonsCam.viewportHeight / 2f, 0);
        buttonsCam.update();
        endsOfLevels = new Array<>();
        W_INDEX = (float) (480.0 / Gdx.graphics.getWidth());
        H_INDEX = (float) (800.0 / Gdx.graphics.getHeight());
        mainPrefs = Gdx.app.getPreferences("MainPreferences");
        menuCoins = new MenuCoins(mainPrefs);
        heartMinusAnimation = new HeartMinusAnimation(mainPrefs.getInteger("Lives", 3), menuCoins.getCOINS(), "ru", mainPrefs);
        createPrefs(mainPrefs);
        Location = mainPrefs.getInteger("Location", 0);
        menuSelectScreenAnimation = new MenuSelectScreenAnimation(Location);
        camera = new OrthographicCamera(480, 800);
        createMap(mainPrefs.getInteger("Map", 1), mainPrefs.getInteger("Level", 0));

        FileHandle baseFileHandle = Gdx.files.internal("Bundles/Bundle");
        Locale locale = new Locale("ru");
        I18NBundle myBundle = I18NBundle.createBundle(baseFileHandle, locale);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/MainFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 52;
        parameter.color = new Color(111f, 55f, 19f, 1);
        BitmapFont font12 = generator.generateFont(parameter);
//        adsController.loadRewardAd();
       // presentButton = new WorkButton("present", 380, 150, presentFrame, true);
        wheelButton = new WorkButton("wheel", 20, 150, false);
        spinWheelWindow = new SpinWheelWindow(backMenuSelectScreen.getBb(), wheelButton, menuCoins, mainPrefs);
        setInputProcessor();

//        sound.loop();
//
//        sound.play(0.1f);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.setProjectionMatrix(camera.combined);
        backMenuSelectScreen.renderBack(delta);

        cameraScroller.act(delta, presentButton, wheelButton, menuCoins);
        backMenuSelectScreen.update(delta);
        camera.update();
        backGroundRender(camera);
        frontGroundRender(camera);
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        backMenuSelectScreen.renderSelector(spriteBatch, select, delta, camera);
        menuSelectScreenAnimation.render(spriteBatch, delta, camera);
        spriteBatch.end();
        backMenuSelectScreen.cameraScroll(camera, delta);
        backMenuSelectScreen.renderFront(delta, spriteBatch, select, camera, 1679 - DELTA_Y_SELECT * endsOfLevels.get(0), 1679 - DELTA_Y_SELECT * endsOfLevels.get(1), 1679 - DELTA_Y_SELECT * endsOfLevels.get(2));
        menuSelectScreenAnimation.renderFront(spriteBatch, delta, camera);
        Color c = spriteBatch.getColor();
        spriteBatch.setColor(c.r, c.g, c.b, 1);
        spriteBatch.setProjectionMatrix(buttonsCam.combined);
        wheelButton.render(delta, spriteBatch);
//        presentButton.render(delta, spriteBatch);

        menuCoins.render(spriteBatch, delta);


        if (animationGoTOScreen)
            if (menuSelectScreenAnimation.renderGoToScreen(camera, delta, spriteBatch, buttonsCam, backMenuSelectScreen)) {
                minusAnim = true;
                camera.zoom = 2.5f;
                camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
                camera.update();
                animationGoTOScreen = false;
            }
        if (minusAnim) {
            heartMinusAnimation.render(spriteBatch, camera, backMenuSelectScreen.getBb(), delta);
            if(heartMinusAnimation.isAnimEnd()) {
                mainPrefs.putInteger("Lives", mainPrefs.getInteger("Lives") - 1);
                mainPrefs.flush();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                gameClass.setScreen(new PlayScreen(gameClass, mainPrefs, isLeft, Location));
            }
        }
        if(cameraScroller.isNeedToChangeMap()){
            map.dispose();
            backMenuSelectScreen.dispose();
            if(cameraScroller.isNextMap())
                mapNum++;
            else
                mapNum--;
            createMap(mapNum, 0);
        }
        if (cameraScroller.isCameraMove() && isWin)
            if (menuSelectScreenAnimation.updateBlocks(delta)) {
                if (animationGoToNextLocation) {
                    if (!backMenuSelectScreen.GoToNextLocation(Location - 1, cameraScroller, menuSelectScreenAnimation.getExplosionAnimation())) {
                    } else {
                        isWin = false;
                        isCanTouch = true;
                    }
                } else {
                    isWin = false;
                    backMenuSelectScreen.createNewTorchs(select.y);
                }
            }
         spinWheelWindow.render(spriteBatch, delta);
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
      map.dispose();
    }

    public void backGroundRender(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render(backgroundLayers);
    }

    public void frontGroundRender(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render(foregroundLayers);
    }

    private void setInputProcessor() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (isCanTouch) {
                    if(spinWheelWindow.isActive()){
                        spinWheelWindow.touchDown(screenX * W_INDEX, 800 - screenY * H_INDEX);
                        return false;
                    }
//                    if (isPresentFrameActive) {
//                        presentFrame.touchDown(screenX * W_INDEX, 800 - screenY * H_INDEX);
//                        return false;
//                    }
//                    if (isWheelFrameActive) {
//                        wheelFrame.touchDown(screenX * W_INDEX, 800 - screenY * H_INDEX);
//                        return false;
//                    }
//                    if (presentButton.touchDown(screenX * W_INDEX, 800 - screenY * H_INDEX))
//                        return false;
                    if (wheelButton.touchDown(screenX * W_INDEX, 800 - screenY * H_INDEX)) {
                        spinWheelWindow.setDeployTrue();
                        menuCoins.setDeployed(true, false);
                        return false;
                    }
                    backMenuSelectScreen.tochDown();
                    firstX = screenX;
                    firstY = screenY;
                    if (select.contains(screenX * W_INDEX + camera.position.x - 240, camera.position.y + 400 - screenY * H_INDEX) && cameraScroller.isCameraMove())
                        animationGoTOScreen = true;
                    if(heartMinusAnimation.isLoad())
                        heartMinusAnimation.touchDown(screenX * W_INDEX, 800 - screenY * H_INDEX);
                    else if (!minusAnim)
                        cameraScroller.touchDown(screenX, screenY);

                }
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (isCanTouch) {
//                    if (isPresentFrameActive) {
//                        presentFrame.touchDragged(screenX * W_INDEX, 800 - screenY * H_INDEX);
//                        return false;
//                    }
//                    if (isWheelFrameActive) {
//                        wheelFrame.touchDragged(screenX * W_INDEX, 800 - screenY * H_INDEX);
//                        return false;
//                    }
//
//                    if (presentButton.touchDragged(screenX * W_INDEX, 800 - screenY * H_INDEX))
//                        return false;
                    if (wheelButton.touchDragged(screenX * W_INDEX, 800 - screenY * H_INDEX))
                        return false;

                    if (Gdx.input.getDeltaY() >= 0 && last <= 0 || Gdx.input.getDeltaY() <= 0 && last >= 0)
                        backMenuSelectScreen.tochDown();
                    last = Gdx.input.getDeltaY();
                    if (!minusAnim)
                    cameraScroller.touchDragged(screenX, screenY, pointer);
                }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if(!minusAnim)
                cameraScroller.touchUp(screenX, screenY, pointer, button);
//                presentButton.touchUp();
                wheelButton.touchUp();
                return true;
            }
        });
    }

    private void initSelectRectangle(boolean isWin, int Level) {
        if (Level == 0) {
            select = new Selector(322 + 480 * (Location), 3413 - 1734, 53, 15);
            isLeft = false;
        } else {
            if (Level % 2 == 0) {
                select = new Selector(selectR + 480 * (Location), 3413 - DELTA_Y_SELECT * Level - 1734, 53, 15);
                isLeft = false;
                menuSelectScreenAnimation.initRBlocks(isWin, select.y, false, Location);
            } else {
                select = new Selector(selectL + 480 * (Location), 3413 - DELTA_Y_SELECT * Level - 1734, 53, 15);
                isLeft = true;
                menuSelectScreenAnimation.initLBlocks(isWin, select.y, false, Location);
            }
        }
        select.setActive(100000);
    }


    private void createEndsOfLevels() {
        for (int i = 0; i < 3; i++)
            endsOfLevels.add(mainPrefs.getInteger("location" + String.valueOf(i), 8));
    }
    private void createMap(int MapNumber, int Level){
        this.mapNum = MapNumber;
        map = new TmxMapLoader().load("Maps/Map" + MapNumber + "/map.tmx");
        createEndsOfLevels();
        camera.position.set(camera.viewportWidth / 2f + 480 * Location, 3219 - 1653, 0);
        camera.update();

        cameraScroller = new CameraScroller(camera, 0 + 400, 3220 - 1653, 1679 - DELTA_Y_SELECT * Level);
        endLevel = mainPrefs.getInteger("location" + String.valueOf(Location), 8);

        if (endLevel == Level && isWin) {
            Location++;
            isCanTouch = false;
            animationGoToNextLocation = true;
            menuSelectScreenAnimation.initLBlocks(true, 1670 - DELTA_Y_SELECT * Level, true, Location - 1);
//            Level = 0;
//            mainPrefs.putInteger("Location", Location);
//            mainPrefs.putInteger("Level", 0);
//            mainPrefs.flush();
            backMenuSelectScreen = new BackMenuSelectScreen(String.valueOf(mainPrefs.getInteger("Map", 1)), Location, 1670 - DELTA_Y_SELECT * Level, isWin, endsOfLevels, DELTA_Y_SELECT, 0);

        } else {
            backMenuSelectScreen = new BackMenuSelectScreen(String.valueOf(mainPrefs.getInteger("Map", 1)), Location, 1670 - DELTA_Y_SELECT * Level, isWin, endsOfLevels, DELTA_Y_SELECT, Level);

        }

        initSelectRectangle(isWin, Level);

        renderer = new OrthogonalTiledMapRenderer(map, 1 / 2.4f);
        int locationsBefore = 0;
        for (int i = 0; i < Location; i++)
            locationsBefore += endsOfLevels.get(i);
        foregroundLayers = new int[(Level + locationsBefore) * 2 + 1];



        for (int i = 1; i < (Level + locationsBefore) * 2 + 1; i++) {
            foregroundLayers[i] = i;
        }

    }
    private void createPrefs(Preferences preferences) {
        preferences.putInteger("Map", 1);
        preferences.putInteger("Level", 0);
        preferences.putInteger("Location", 0);
        preferences.putInteger("location0", 8);
        preferences.putInteger("location1", 12);
        preferences.putInteger("location2", 9);
        preferences.putInteger("Lives", 5);
        preferences.flush();
    }
}
