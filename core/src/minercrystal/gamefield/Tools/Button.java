package minercrystal.gamefield.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

public class Button {
    private Texture btn, btnPressed;
    private BitmapFont font12;
    private boolean isBtnPressed;
    private Rectangle btnR;
    OrthographicCamera camera;
    public Button(Texture btn, Texture btnPressed, Rectangle btnRectangle, String localeS, int n) {
        this.btn = btn;
        this.btnPressed = btnPressed;
        this.btnR = btnRectangle;
        camera = new OrthographicCamera(480, 800);
        camera.position.set(camera.viewportWidth / 2f , camera.viewportHeight / 2f , 0);
        camera.update();
        FileHandle baseFileHandle = Gdx.files.internal("Bundles/Bundle");
        Locale locale = new Locale(localeS);
        I18NBundle myBundle = I18NBundle.createBundle(baseFileHandle, locale);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/MainFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 34;
        parameter.color = new Color(111f, 55f, 19f, 1);
        font12 = generator.generateFont(parameter);
    }

    public void render(SpriteBatch spriteBatch, float delta){
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
     if(isBtnPressed)
         spriteBatch.draw(btn, btnR.x, btnR.y, btnR.width, btnR.height);
     else
         spriteBatch.draw(btnPressed, btnR.x, btnR.y, btnR.width, btnR.height);
     spriteBatch.end();
    }

    public boolean touchDown(float X, float Y){
        if(btnR.contains(X, Y)){
            isBtnPressed = true;
        }
        return isBtnPressed;
    }



}
