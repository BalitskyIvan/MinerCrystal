package minercrystal.gamefield.Animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class CrystalsExplosion {
    private Array<Texture> an1, an2, an3, an4;
    private Array<Rectangle> anPos1, anPos2, anPos3, anPos4;
    private final static float animDelay = 0.015f;

    public CrystalsExplosion() {
        an1 = new Array<>();
        an2 = new Array<>();
        an3 = new Array<>();
        an4 = new Array<>();
        anPos1 = new Array<>();
        anPos2 = new Array<>();
        anPos3 = new Array<>();
        anPos4 = new Array<>();

        for (int i = 0; i <= 6; i++) {
            an1.add(new Texture("crystalAnim/1/" + i + ".png"));
        }
        for (int i = 0; i <= 6; i++) {
            an2.add(new Texture("crystalAnim/2/" + i + ".png"));
        }
        for (int i = 0; i <= 6; i++) {
            an3.add(new Texture("crystalAnim/3/" + i + ".png"));
        }
        for (int i = 0; i <= 6; i++) {
            an4.add(new Texture("crystalAnim/4/" + i + ".png"));
        }
    }

    public void render(SpriteBatch spriteBatch, float delta) {
        for (Rectangle an : anPos1) {
            spriteBatch.draw(an1.get((int) an.height), an.x, an.y, 75, 100);
            an.width += delta;
            if (an.width > animDelay) {
               an.height++;
                an.width = 0;
                if (an.height > 6) {
                    anPos1.removeValue(an, true);
                }
            }
        }
        for (Rectangle an : anPos2) {
            spriteBatch.draw(an2.get((int) an.height), an.x, an.y, 75, 100);
            an.width += delta;
            if (an.width > animDelay) {
                an.height++;
                an.width = 0;
                if (an.height > 6) {
                    anPos2.removeValue(an, true);
                }
            }
        }
        for (Rectangle an : anPos3) {
            spriteBatch.draw(an3.get((int) an.height), an.x, an.y, 75, 100);
            an.width += delta;
            if (an.width > animDelay) {
                an.height++;
                an.width = 0;
                if (an.height > 6) {
                    anPos3.removeValue(an, true);
                }
            }
        }
        for (Rectangle an : anPos4) {
            spriteBatch.draw(an4.get((int) an.height), an.x, an.y, 75, 100);
            an.width += delta;
            if (an.width > animDelay) {
                an.height++;
                an.width = 0;
                if (an.height > 6) {
                    anPos4.removeValue(an, true);
                }
            }
        }
    }

    public void add(float X, float Y, int mode) {
        switch (mode){
            case 1:
                anPos1.add(new Rectangle(X - 12, Y - 50, 0, 0));
                break;
            case 2:
                anPos2.add(new Rectangle(X - 12, Y - 50, 0, 0));
                break;
            case 3:
                anPos3.add(new Rectangle(X - 12, Y - 50, 0, 0));
                break;
            case 4:
                anPos4.add(new Rectangle(X - 12, Y - 50, 0, 0));
                break;
        }

    }
}
