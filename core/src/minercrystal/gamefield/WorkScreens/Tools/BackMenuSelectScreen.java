package minercrystal.gamefield.WorkScreens.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Select;

import java.util.Random;
import java.util.logging.Level;

import minercrystal.gamefield.Animation.ExplosionAnimation;
import minercrystal.gamefield.Tools.Selector;

public class BackMenuSelectScreen {
    private Texture skyTexture, bb, Ten, LockTexture;
    float skyY1 = 1816, skyY2 = 1016, SPEED = 0.04f, splashAccumulator = 1, splashTimer = 0.25f, velocity = 0.3f, mTimer = 0, accumulator = 0, dY;
    boolean isD, isSkyFirst = true, AnimationGoTONextLocation = false, explosionAdded = false;
    SpriteBatch batch;
    OrthographicCamera camera;
    Array<Vector3> YTorch1, YTorch2;
    Array<Texture> torchAnimate;
    Random r;
    int XTorch1 = 100, XTorch2 = 300;
    public static final float TIME_TO_SCROLL = 4f;
    int location;
    private int Level;

    public BackMenuSelectScreen(String Map, int Location, float yCamPos, boolean isWin, Array<Integer> endsOfLevels, float DELTA_Y_SELECT, int level) {
        this.dY = yCamPos;
        this.location = Location;
        this.Level = level;
        skyTexture = new Texture("BackgroundAnimation/sky.png");
        LockTexture = new Texture("tools/lock.png");
        Ten = new Texture("tools/ten.png");
        bb = new Texture("bb.jpg");
        r = new Random();
        YTorch1 = new Array<>();
        YTorch2 = new Array<>();
        torchAnimate = new Array<>();


        initTorches(Location, yCamPos, DELTA_Y_SELECT, endsOfLevels, isWin);


        for (int i = 1; i < 9; i++) {
            torchAnimate.add(new Texture("TorchAnimation/" + String.valueOf(i) + ".png"));
        }


        batch = new SpriteBatch();
        camera = new OrthographicCamera(480, 800);
        camera.position.set(camera.viewportWidth / 2f + 480 * Location, 3300, 0);
        camera.update();
    }

    private void initTorches(float Location, float yCamPos, float DELTA_Y_SELECT, Array<Integer> endsOfLevels, boolean isWin) {
        for (int i2 = 0; i2 < Location; i2++) {
            createTorches(1679 - DELTA_Y_SELECT * endsOfLevels.get(i2) + 64 / 2.4f * 2);
            XTorch1 += 480;
            XTorch2 += 480;
        }
        float firstY = 0;
        if (yCamPos < 1555) {
            if (isWin) {
                firstY = yCamPos + 64 / 2.4f * 2 + 9;
            } else {
                firstY = yCamPos + 64 / 2.4f * 2 + 9;
            }
            createTorches(firstY);
        }
    }

    private void createTorches(float firstY) {
        YTorch1.add(new Vector3(r.nextInt(45) + XTorch1, firstY, r.nextInt(8)));
        YTorch2.add(new Vector3(r.nextInt(50) + XTorch2, firstY, r.nextInt(8)));

        for (int i = YTorch1.size; YTorch1.get(i - 1).y < 1555; i++) {
            YTorch1.add(new Vector3(r.nextInt(45) + XTorch1, YTorch1.get(i - 1).y + 64 / 2.4f * 3, r.nextInt(8)));
            YTorch2.add(new Vector3(r.nextInt(50) + XTorch2, YTorch2.get(i - 1).y + 64 / 2.4f * 3, r.nextInt(8)));
        }
    }

    public void renderBack(float delta) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        Color c = batch.getColor();
        batch.setColor(c.r, c.g, c.b, 1);
        batch.draw(skyTexture, 0, skyY1, 540, 802);
        batch.draw(skyTexture, 0, skyY2, 540, 802);
        batch.end();
    }

    public void renderSelector(SpriteBatch spriteBatch, Selector select, float delta, OrthographicCamera camera) {

        accumulator += delta;
        if (accumulator > 0.08) {
            for (Vector3 torch : YTorch1) {
                if (camera.position.y > torch.y - 400 && camera.position.y < torch.y + 480) {
                    torch.z++;
                    if (torch.z > 7) torch.z = 0;
                }
            }
            for (Vector3 torch : YTorch2) {
                if (camera.position.y > torch.y - 400 && camera.position.y < torch.y + 480) {
                    torch.z++;
                    if (torch.z > 7) torch.z = 0;
                }
            }
            accumulator = 0;
        }
        for (int i = 0; i < YTorch1.size; i++) {
            if (camera.position.y > YTorch1.get(i).y - 400 && camera.position.y < YTorch1.get(i).y + 480) {
                spriteBatch.draw(torchAnimate.get((int) YTorch1.get(i).z), YTorch1.get(i).x, YTorch1.get(i).y, 40, 76);
                spriteBatch.draw(torchAnimate.get((int) YTorch2.get(i).z), YTorch2.get(i).x, YTorch2.get(i).y, 40, 76);
            }
        }
        select.render(delta, spriteBatch);
    }

    public void renderFront(float delta, SpriteBatch spriteBatch, Rectangle select, OrthographicCamera camera, float endFirstLevel, float endSecondLevel, float endThirdLevel) {
        spriteBatch.setProjectionMatrix(camera.combined);
        Color c = spriteBatch.getColor();
        spriteBatch.setColor(c.r, c.g, c.b, 1);
        spriteBatch.begin();
        spriteBatch.setColor(c.r, c.g, c.b, 0.85f);
        switch (location) {
            case 0:
                spriteBatch.draw(Ten, 0, select.getY() - 200, 480, 200);
                spriteBatch.draw(bb, 0, 0, 480, select.getY() - 200);
                spriteBatch.draw(bb, 480, camera.position.y - 400, 480, 800);
                spriteBatch.draw(bb, 960, camera.position.y - 400, 480, 800);
                spriteBatch.draw(LockTexture, 480 + 190, camera.position.y - 50, 100, 80);
                spriteBatch.draw(LockTexture, 960 + 190, camera.position.y - 50, 100, 80);
                break;
            case 1:
                spriteBatch.draw(Ten, 0, endFirstLevel - 200, 480, 200);
                spriteBatch.draw(Ten, 480, select.getY() - 200, 480, 200);
                spriteBatch.draw(bb, 0, 0, 480, endFirstLevel - 200);
                spriteBatch.draw(bb, 480, 0, 480, select.getY() - 200);
                spriteBatch.draw(bb, 960, camera.position.y - 400, 480, 800);
                spriteBatch.draw(LockTexture, 960 + 190, camera.position.y - 50, 100, 80);
                break;
            case 2:
                spriteBatch.draw(Ten, 0, endFirstLevel - 200, 480, 200);
                spriteBatch.draw(Ten, 480, endSecondLevel - 200, 480, 200);
                spriteBatch.draw(Ten, 960, select.getY() - 200, 480, 200);
                spriteBatch.draw(bb, 0, 0, 480, endFirstLevel - 200);
                spriteBatch.draw(bb, 480, 0, 480, endSecondLevel - 200);
                spriteBatch.draw(bb, 960, 0, 480, select.getY() - 200);
                break;
            default:

                break;
        }

        spriteBatch.end();
        if (!isD) {
            setAlpha(delta, spriteBatch);
            spriteBatch.begin();
            spriteBatch.draw(bb, camera.position.x - 240, camera.position.y - 400, 480, 800);
            spriteBatch.end();
        }
    }

    public void createNewTorchs(float selectorY) {
        if (Level != 0) {
            YTorch1.add(new Vector3(r.nextInt(45) + XTorch1, selectorY - 64 / 2.4f, r.nextInt(8)));
            YTorch2.add(new Vector3(r.nextInt(50) + XTorch2, selectorY - 64 / 2.4f, r.nextInt(8)));
        }
    }

    public boolean GoToNextLocation(int LocationNow, CameraScroller cameraScroller, ExplosionAnimation explosionAnimation) {
        if (cameraScroller.cameraMove(720 + 480 * (LocationNow))) {
        } else {
            if (!explosionAdded) {
                explosionAnimation.addExplosionAnimation(camera.position.x - 90, camera.position.y - 460, 200, 200);
            }
            explosionAdded = true;
            if (!cameraScroller.MoveUp()) {

                return true;
            }
        }
        return false;
    }

    public void update(float delta) {
        skyY1 += SPEED;
        skyY2 += SPEED;

        if (skyY1 > 2566) {
            skyY1 = 1016;
            isSkyFirst = false;
        }

        if (skyY2 > 2566) {
            skyY2 = 1016;
            isSkyFirst = true;
        }
    }

    //upper - 3300, lower - 3000
    public void cameraScroll(OrthographicCamera camera1, float deltaTime) {
        if (camera1.position.y > 3000 - 1734)
            camera.position.y = 3300 - 1734 - (3300 - 1734 - camera1.position.y) / 4;
        camera.position.x = 240 + (camera1.position.x - 240) / 16;
        camera.update();
    }

    public void tochDown() {
        //velocity = 2.0f;
    }

    public void dispose(){
        for (int i = 0; i < 8; i++) {
            torchAnimate.get(i).dispose();
        }
        skyTexture.dispose();
        LockTexture.dispose();
        skyTexture.dispose();
        Ten.dispose();
        bb.dispose();
    }
    public Texture getBb() {
        return bb;
    }

    public void setGoScreen(float alpha, SpriteBatch spriteBatch) {
        Color c = spriteBatch.getColor();
        float a = 1.2f - alpha;
        if (a < 1)
            spriteBatch.setColor(c.r, c.g, c.b, a);
        else
            spriteBatch.setColor(c.r, c.g, c.b, 1);
        spriteBatch.begin();
        spriteBatch.draw(bb, 0, 0, 480, 800);
        spriteBatch.end();
    }

    private void setAlpha(float delta, SpriteBatch spriteBatch) {
        splashAccumulator -= splashTimer * delta;
        Color c = spriteBatch.getColor();
        spriteBatch.setColor(c.r, c.g, c.b, splashAccumulator);
        if (splashAccumulator < 0.05) {
            splashAccumulator = 1;
            isD = true;
            spriteBatch.setColor(c.r, c.g, c.b, 0);
        }
    }
}
