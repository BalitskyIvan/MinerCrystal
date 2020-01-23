package minercrystal.gamefield.Animation;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class ExplosionAnimation {
    Array<Vector3> explosionAnimation;
    Array<Texture> explosionTextures;
    float W, H;
    public ExplosionAnimation() {
        explosionAnimation = new Array<>();
        explosionTextures = new Array<>();
        for (int i = 1; i <= 9; i++) {
            explosionTextures.add(new Texture("an" + String.valueOf(i) + ".png"));
        }
    }
    public void render(float delta, SpriteBatch spriteBatch, OrthographicCamera camera){
        for (Vector3 item : explosionAnimation) {
            spriteBatch.draw(explosionTextures.get((int) item.z - 1), item.x - 40 + camera.position.x - 240, item.y - 30, W, H);
            item.z -= 24 * delta;
            if (item.z < 1) explosionAnimation.removeValue(item, true);
        }
    }
    public void addExplosionAnimation(float X, float Y, float W, float H) {
        this.W = W;
        this.H = H;
        explosionAnimation.add(new Vector3(X, Y, 9));
    }
}
