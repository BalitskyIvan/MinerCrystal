package minercrystal.gamefield.Buttons;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;

public class FrameRedButton {
    Rectangle playBTN;
    Array<Texture> playBtnAn;
    boolean isPlayBtnPressed, isPlayDead, isMoveUp;
    OrthographicCamera camera;
    int PlayBtnAnimation = 0;
    BitmapFont font;
    I18NBundle myBundle;
    float timerPlayBtn = 0;
    public FrameRedButton(BitmapFont font, I18NBundle myBundle, Rectangle rectangle) {
        this.font = font;
        this.myBundle = myBundle;
        this.playBTN = rectangle;
        playBtnAn = new Array<>();
        for (int i = 1; i < 8; i++)
            playBtnAn.add(new Texture("Buttons/RBtn/" + String.valueOf(i) + ".png"));
        camera = new OrthographicCamera();
        camera = new OrthographicCamera(480, 800);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
    }

    public void render(float delta, SpriteBatch batch, String s) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if(!isPlayDead) {
            batch.draw(playBtnAn.get(PlayBtnAnimation), playBTN.getX(), playBTN.getY(), playBTN.getWidth(), playBTN.getHeight());
            if(isPlayBtnPressed) {
                timerPlayBtn += delta;
                if (timerPlayBtn > 0.04){
                    PlayBtnAnimation++;
                    timerPlayBtn = 0;
                    if (PlayBtnAnimation == 7)isPlayDead = true;
                }
            }
            else
                font.draw(batch, s,  playBTN.getX() + 48, playBTN.getY() +56);

        }

        batch.end();
    }

    private boolean isPlayBtnPressedVoid(float X, float Y){
        if(playBTN.contains(X, Y))
            isPlayBtnPressed = true;
        return isPlayBtnPressed;
    }

    public boolean isPlayBtnPressed() {
        return isPlayBtnPressed;
    }

    public void setPlayDeadFalse() {
        isPlayDead = false;
        PlayBtnAnimation = 0;
        isPlayBtnPressed = false;
    }

    public int isContains(int X, int Y) {
        if (isPlayBtnPressedVoid(X, Y))
            return 1;
        isMoveUp = true;
        return 0;
    }

    public boolean isPlayDead() {
        return isPlayDead;
    }

    public void reset() {


    }
}
