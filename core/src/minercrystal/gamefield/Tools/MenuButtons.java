package minercrystal.gamefield.Tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;

public class MenuButtons {

    Rectangle playBTN;
    Array<Texture> playBtnAn;
    boolean isPlayBtnPressed, isPlayDead, isMoveUp;
    OrthographicCamera camera;
    int PlayBtnAnimation = 0;
    BitmapFont font;
    I18NBundle myBundle;
    float timerPlayBtn = 0;
    public MenuButtons(BitmapFont font, I18NBundle myBundle) {
        this.font = font;
        this.myBundle = myBundle;
        playBTN = new Rectangle(115, 0, 250, 125);
        playBtnAn = new Array<Texture>();
        for (int i = 1; i < 11; i++)
            playBtnAn.add(new Texture("Buttons/PlayBtn/" + String.valueOf(i) + ".png"));
        camera = new OrthographicCamera();
        camera = new OrthographicCamera(480, 800);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
    }

    public void render(float delta, SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if(!isPlayDead) {
            batch.draw(playBtnAn.get(PlayBtnAnimation), playBTN.getX(), playBTN.getY(), playBTN.getWidth(), playBTN.getHeight());
            if(isPlayBtnPressed) {
                timerPlayBtn += delta;
                if (timerPlayBtn > 0.05){
                    PlayBtnAnimation++;
                    timerPlayBtn = 0;
                    if (PlayBtnAnimation == 10)isPlayDead = true;
                }
            }else
                font.draw(batch, myBundle.get("playBtn"),  playBTN.getX()+63, playBTN.getY()+80);

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

    public int isContains(int X, int Y) {
        if (isPlayBtnPressedVoid(X, Y))
            return 1;
        isMoveUp = true;
        return 0;
    }


    public void reset() {


    }
}
