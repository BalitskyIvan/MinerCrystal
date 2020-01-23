package minercrystal.gamefield.Tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


public class SkyAnimation {
    private Texture bgTexture;
    private Array<Rectangle> bgRects;
    private float SpeedBG_Y, speedBG_X, del;
    public SkyAnimation(float W, float H, float speed, float delX) {
        this.del= delX;
        this.SpeedBG_Y = speed;
        bgTexture = new Texture("BackgroundAnimation/sky.png");
        bgRects = new Array<>();
        bgRects.add(new Rectangle(0,0, W, H));
        bgRects.add(new Rectangle(0, -H, W, H));
        bgRects.add(new Rectangle(W, 0, W, H));
        bgRects.add(new Rectangle(W, -H, W, H));
    }
    public void render(SpriteBatch spriteBatch, float delta){
        for (Rectangle r: bgRects) {
            spriteBatch.draw(bgTexture, r.getX(), r.getY(), r.width+1, r.height);
            r.y += SpeedBG_Y;
            r.x -= speedBG_X;
        }

    }
    public void update(){
        for (Rectangle r: bgRects) {

            if (r.y > 800) {
                bgRects.get(0).y = bgRects.get(1).getY() - r.height;
                bgRects.get(2).y = bgRects.get(0).getY();

                bgRects.swap(0, 1);
                bgRects.swap(2, 3);

            }
            if (r.x + r.width < 0) {
                bgRects.get(0).x = bgRects.get(2).getX() + r.width;
                bgRects.get(1).x = bgRects.get(0).getX();

                bgRects.swap(0, 2);
                bgRects.swap(1, 3);

            }

        }
    }
    public void cameraMove(float speed){
      speedBG_X = speed/del;
    }
    public void dispose(){
        bgTexture.dispose();
    }
}
