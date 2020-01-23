package minercrystal.gamefield.AnimationScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class SplashScreen {
    float alphaSlpashScreenTimer = 0, alphaSlpashSpeed = 0.008f,
            SPLASH_ACCUMULATOR = 0.03f, STimer = 0, alphaBackroundTimer = 1, AnimationDelay = 0.035f, AnimationTimer = 0;
    Texture LogoTexture, bb;
    Array<Texture> LogoAnTextures;
    boolean isSplashScreenActive = true, isLogoActive = false, backgroundActive = false;
    int TextureCounter = 1;

    public SplashScreen() {
        LogoTexture = new Texture("GFLogo.png");
        bb = new Texture("bb.jpg");
        LogoAnTextures = new Array<Texture>();
        for (int i = 1; i < 10; i++)
            LogoAnTextures.add(new Texture("LogoAnimation/" + String.valueOf(i) + ".png"));

    }

    public void setSplashScreen(float delta, SpriteBatch spriteBatch) {

        if ((alphaBackroundTimer > 0 && isLogoActive) || backgroundActive) {
            STimer += delta;
            if (STimer > SPLASH_ACCUMULATOR && !backgroundActive) {
                alphaBackroundTimer -= alphaSlpashSpeed;
                STimer = 0;
            }


            Color c = spriteBatch.getColor();
            spriteBatch.setColor(c.r, c.g, c.b, alphaBackroundTimer);


            if (alphaBackroundTimer <= 0) {
                backgroundActive = true;
                alphaBackroundTimer = 0;

            }
        }
        spriteBatch.begin();
        spriteBatch.draw(bb, 0, 0 , 480, 800);

        if ((alphaSlpashScreenTimer < 1 || isLogoActive) && !backgroundActive) {
            STimer += delta;
            if (STimer > SPLASH_ACCUMULATOR && !isLogoActive) {
                alphaSlpashScreenTimer += alphaSlpashSpeed;
                STimer = 0;
            }

            Color c = spriteBatch.getColor();
            spriteBatch.setColor(c.r, c.g, c.b, alphaSlpashScreenTimer); //set alpha to 1

            spriteBatch.draw(LogoTexture, 0, 300, 480, 320);

            if (alphaSlpashScreenTimer > 0.96) {
                isLogoActive = true;
                alphaSlpashScreenTimer = 1;
            }
        }
        if (backgroundActive) {
            AnimationTimer += delta;
            if (AnimationTimer > AnimationDelay) {
                AnimationTimer = 0;
                TextureCounter++;
                if (TextureCounter == 9) {
                    TextureCounter = 8;
                    isSplashScreenActive = false;
                    dispose();
                }
            }
            if (isSplashScreenActive) {
                Color c = spriteBatch.getColor();
                spriteBatch.setColor(c.r, c.g, c.b, 1); //set alpha to 1
                spriteBatch.draw(LogoAnTextures.get(TextureCounter), 0, 300, 480, 320);
            }
        }
        spriteBatch.end();

    }

    public boolean isSplashScreenActive() {
        return isSplashScreenActive;
    }

    public void dispose() {
        for (int i = 0; i < 9; i++)
            LogoAnTextures.get(i).dispose();
        LogoTexture.dispose();
    }
}
