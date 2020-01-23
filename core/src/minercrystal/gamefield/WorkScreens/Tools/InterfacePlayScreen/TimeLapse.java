package minercrystal.gamefield.WorkScreens.Tools.InterfacePlayScreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class TimeLapse {
    private float splashTimer = 1.60f, Speed, HealthSpeed = 0.12f, HealthSpeedAccumulator = 0, DeltaFrames = 0.55f, speedDel = 0.5f,
            FramesAccumulator, AnimLong = 0.38f, AnimAccumulator, accumulator, HWZOOM, IndexSize = 0.35f, PartsDelay = 16, PartsHw = 24;
    private final static float TIME_TO_ZOOM = 5, animDelta = 0.018f, ZOOM_SPEED = 0.7f;
    private Texture timeLapseMain;
    private Array<Texture> timeLapsePartsTextures, modesHeartTexture, heartTextures;
    private Rectangle heartRect;
    private Array<Rectangle> animFrames, timeLapseParts;
    private Array<Float> splashFrames;
    private Array<Rectangle> heartsAr;
    private Array<Integer> modes;
    private boolean isMaxAnimate = false, isLose = false, timeTOZoom = false, timeToNorm = false, isRecharge = false;
    private int timeLapseNowX = 0, partOfPartTimeLapse = 8, mode = 4, heartsINDEX = 0;

    public TimeLapse(float Speed) {
        this.Speed = Speed * 2;
        animFrames = new Array<>();
        splashFrames = new Array<>();
        heartsAr = new Array<>();
        modesHeartTexture = new Array<>();
        heartRect = new Rectangle(215, 757, 50, 45);
        timeLapsePartsTextures = new Array<>();
        modes = new Array<>();
        heartTextures = new Array<>();
        timeLapseParts = new Array<>();
        for (int i = 1; i <= 5; i++)
            modesHeartTexture.add(new Texture(i + "heart.png"));
        for (int i = 1; i < 8; i++)
            timeLapsePartsTextures.add(new Texture("timeLapseAnimation/" + i + ".png"));

        for (int i = 1; i < 10; i++) {
            heartTextures.add(new Texture("heartAnimation/" + i + ".png"));
        }
        timeLapseMain = new Texture("timeLapseAnimation/main.png");
        int x = 240;
        for (; x < 480; x += PartsDelay)
            timeLapseParts.add(new Rectangle(x, 800 - PartsHw, PartsHw, PartsHw));
        timeLapseNowX = x;
    }

    public void render(SpriteBatch spriteBatch, float delta, boolean isPause) {
            for (Rectangle h : heartsAr) {
                if(modes.get(heartsAr.indexOf(h, false))!=0)
                spriteBatch.draw(modesHeartTexture.get(modes.get(heartsAr.indexOf(h, false)) - 1), h.x, h.y, 40, 40);
                if(isRecharge) {
                    h.x += h.width/2.5 * delta;
                    h.y += h.height/2.5 * delta;
                }else{
                    h.x += h.width * delta;
                    h.y += h.height * delta;
                }
                if (h.y + 38 > 760) {
                    timeTOZoom = true;
                    mode = modes.get(heartsAr.indexOf(h, false)) - 1;
                    modes.removeIndex(heartsAr.indexOf(h, false));
                    heartsAr.removeValue(h, false);
                    if (timeLapseNowX < 480) {
                        partOfPartTimeLapse += 2;
                        if (partOfPartTimeLapse >= 8) {
                            if (timeLapseParts.size != 0)
                                timeLapseParts.add(new Rectangle(timeLapseParts.peek().x + PartsDelay, 800 - PartsHw, PartsHw, PartsHw));
                            else
                                timeLapseParts.add(new Rectangle(240, 800 - PartsHw, PartsHw, PartsHw));

                            timeLapseNowX += PartsDelay;
                            partOfPartTimeLapse = 0;
                        }
                    } else {
                        isRecharge = false;
                        if (!isMaxAnimate)
                            isMaxAnimate = true;
                        addAnimFrame();
                    }
                }




        }

        if (isMaxAnimate && !isPause) {
            AnimAccumulator += delta;
            if (AnimAccumulator < AnimLong) {
                FramesAccumulator += delta;
                if (FramesAccumulator > DeltaFrames) {
                    FramesAccumulator = 0;
                    addAnimFrame();
                }
            }
            for (Rectangle r : animFrames) {
                r.x -= IndexSize;
                r.y -= IndexSize;
                r.width += IndexSize * 2;
                r.height += IndexSize * 2;
                drawAnimFrame(delta, spriteBatch, r);
            }
            if (animFrames.size == 0) {
                isMaxAnimate = false;
                AnimAccumulator = 0;
            }
        }

        if (timeTOZoom && !isPause) {
            if (timeToNorm) {
                if (HWZOOM > 0)
                    HWZOOM -= ZOOM_SPEED;
                else {
                    timeToNorm = false;
                    timeTOZoom = false;
                    HWZOOM = 0;
                    mode = 4;
                }
            } else {
                if (HWZOOM < TIME_TO_ZOOM)
                    HWZOOM += ZOOM_SPEED;
                else
                    timeToNorm = true;
            }
        }

        if (timeLapseParts.size > 0) {
            for (Rectangle r : timeLapseParts) {
                spriteBatch.draw(timeLapseMain, r.x, r.y, r.width, r.height);
                spriteBatch.draw(timeLapseMain, 240 - ((timeLapseParts.indexOf(r, false) + 1) * PartsDelay) + PartsDelay, r.y, -r.width, r.height);
            }
            for (int i = 0; i < partOfPartTimeLapse - 1; i++) {
                spriteBatch.draw(timeLapsePartsTextures.get(i), timeLapseNowX, 800 - PartsHw, PartsHw, PartsHw);
                spriteBatch.draw(timeLapsePartsTextures.get(i), 240 - (timeLapseNowX - 240), 800 - PartsHw, -PartsHw, PartsHw);
            }
            spriteBatch.draw(modesHeartTexture.get(mode), heartRect.x - HWZOOM, heartRect.y - HWZOOM, heartRect.width + HWZOOM * 2, heartRect.height + HWZOOM * 2);
            if(!isPause) {
                HealthSpeedAccumulator += delta;
                if (HealthSpeedAccumulator > HealthSpeed) {
                    HealthSpeedAccumulator = 0;
                    partOfPartTimeLapse--;
                    if (partOfPartTimeLapse < 0) {
                        partOfPartTimeLapse = 8;
                        timeLapseNowX = (int) timeLapseParts.get(timeLapseParts.size - 1).x;
                        timeLapseParts.removeIndex(timeLapseParts.size - 1);
                    }
                }
            }
        } else {
            if(!isLose)
            spriteBatch.draw(heartTextures.get(heartsINDEX), heartRect.x, heartRect.y, heartRect.width, heartRect.height);
            if(!isPause && !isRecharge) {
                accumulator += delta;
                if (accumulator > animDelta) {
                    heartsINDEX++;
                    if (heartsINDEX == 9)
                        isLose = true;
                }
            }
        }
    }

    public void plus(Array<Vector2> itemsToDestroy, Array<Integer> Mode) {
        for (Vector2 i : itemsToDestroy)
            heartsAr.add(new Rectangle(i.x, i.y, (heartRect.x - i.x) / speedDel, (heartRect.y - i.y) / speedDel));
        for (Integer i : Mode)
            modes.add(i);

    }

    private void drawAnimFrame(float delta, SpriteBatch spriteBatch, Rectangle r) {
        splashFrames.set(animFrames.indexOf(r, false), splashFrames.get(animFrames.indexOf(r, false)) - splashTimer * delta);
        Color c = spriteBatch.getColor();
        spriteBatch.end();
        spriteBatch.setColor(c.r, c.g, c.b, splashFrames.get(animFrames.indexOf(r, false)));
        spriteBatch.begin();
        spriteBatch.end();
        spriteBatch.begin();
        if (splashFrames.get(animFrames.indexOf(r, false)) < 0) {
            splashFrames.removeValue(splashFrames.get(animFrames.indexOf(r, false)), false);
            animFrames.removeValue(r, false);
        }
        spriteBatch.setColor(c.r, c.g, c.b, 1);
    }

    private void addAnimFrame() {
        splashFrames.add(1f);
    }

    public void setLose(boolean lose) {
        isLose = lose;
        heartsINDEX = 0;
        isRecharge = true;
    }

    public boolean isLose() {
        return isLose;
    }
}
