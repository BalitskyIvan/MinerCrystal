package minercrystal.gamefield.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;

public class StarAnimation {
    private Texture starTexture;
    private Array<Rectangle> stars;
    private Array<Vector3> postStar;
    private Array<Float> alphas;
    private Array<Boolean> isBright;
    private final static float starWH = 80, postDelta = 0.001f, tenDelta = 0.07f, speed = 130f, speedAlphas = 0.01f;
    private float postAccumulator = 0;
    private boolean isStop = false;

    public StarAnimation() {
        stars = new Array<>();
        isBright = new Array<>();
        isBright.add(false);
        isBright.add(true);
        isBright.add(false);
        alphas = new Array<>();
        alphas.add(0.75f);
        alphas.add(0.5f);
        alphas.add(1f);

        postStar = new Array<>();
        starTexture = new Texture("star.png");
    }

    public void render(SpriteBatch spriteBatch, float delta) {
        Color c = spriteBatch.getColor();

        postAccumulator += delta;
        if (postAccumulator > postDelta) {
            postAccumulator = 0;
            for (Vector3 vector3 : postStar) {
                vector3.z -= tenDelta;
                if (vector3.z <= 0)
                    postStar.removeValue(vector3, true);
            }
            if(!isStop)
            for (Rectangle r : stars) {
                postStar.add(new Vector3(r.getX(), r.getY(), 1));
            }

        }
        for (Vector3 vector3 : postStar) {
            spriteBatch.end();
            spriteBatch.setColor(c.r, c.g, c.b, vector3.z);
            spriteBatch.begin();
            spriteBatch.draw(starTexture, vector3.x, vector3.y, starWH, starWH);
            spriteBatch.end();
            spriteBatch.begin();
        }
        isStop = true;
        for (Rectangle r : stars) {
            if (r.y < 450) {
                isStop = false;
                r.y += speed * delta;
                if (stars.indexOf(r, true) == 0)
                    r.x = MathUtils.sin(r.getY() / 15) * 80 + 200;
                else {
                    if (stars.indexOf(r, true) == 1)
                        r.x = MathUtils.sin(-r.getY() / 15) * 80 + 200;
                    if (stars.indexOf(r, true) == 2)
                        r.x = MathUtils.sin(r.getY() / 15) * 80 + 280;

                }
            }

        }

        for (Rectangle r : stars) {
            if (isStop) {
                if (isBright.get(stars.indexOf(r, true))) {
                    alphas.set(stars.indexOf(r, true), alphas.get(stars.indexOf(r, true)) + speedAlphas);
                    if (alphas.get(stars.indexOf(r, true)) >= 1)
                        isBright.set(stars.indexOf(r, true), false);
                } else {
                    alphas.set(stars.indexOf(r, true), alphas.get(stars.indexOf(r, true)) - speedAlphas);
                    if (alphas.get(stars.indexOf(r, true)) <= 0.4f)
                        isBright.set(stars.indexOf(r, true), true);
                }
                spriteBatch.end();

                spriteBatch.setColor(c.r, c.g, c.b, alphas.get(stars.indexOf(r, true)));
                spriteBatch.begin();
                spriteBatch.draw(starTexture, r.getX(), r.getY(), starWH, starWH);
                spriteBatch.end();
                spriteBatch.setColor(c.r, c.g, c.b, 1);
                spriteBatch.begin();
            } else {
                spriteBatch.draw(starTexture, r.getX(), r.getY(), r.getWidth(), r.getHeight());
            }
        }
    }

    public boolean isStop() {
        return isStop;
    }

    public void addStars(int count) {
        for (int i = 0; i < count; i++) {
            stars.add(new Rectangle(i * 40 + 190, 0 - 100 * (i + 1), starWH, starWH));
        }
    }
}
