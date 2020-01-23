package minercrystal.gamefield.WorkScreens.Tools.InterfaceSelectScreen;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import minercrystal.gamefield.WorkScreens.Tools.InterfacePlayScreen.BoostController;


public class WinScreen {

    private boolean isDeploying = false, isActive = false, isAnimFinished = false, isElementsMoving = false, deployDestroy = false, isAnimationElementDeployed = false;
    private MenuCoins menuCoins;
    private BoostController boostController;
    private float alpha = 0, alphaSpeed = 0, timerToDestroy = 0.95f, timerToGo = 0;
    private static final float speedAlphaStatic = 1.08f, timerToGoStatic = 0.3f, accStop = 400, accelerationAlpha = 1.03f, rectangleOneWH = 120, rectanglesWH = 65, minusHW = 15;
    private Texture bw;
    private Array<Vector2> speedDel, accelerationElement;
    private Array<Rectangle> winElementsGoto;
    private Array<Texture> winElementsTexture;
    private Array<Boolean> isElementGoing;
    private int mode;
    private Rectangle posGoTo;

    public WinScreen(MenuCoins menuCoins) {
        this.menuCoins = menuCoins;
        bw = new Texture("bb.jpg");
        winElementsTexture = new Array<>();
        winElementsTexture.add(new Texture("spinWheel/elements/1.png"));
        winElementsTexture.add(new Texture("crystal.png"));
        winElementsTexture.add(new Texture("spinWheel/elements/3.png"));
        winElementsTexture.add(new Texture("5heart.png"));
        winElementsTexture.add(new Texture("spinWheel/elements/5.png"));
        winElementsTexture.add(new Texture("spinWheel/elements/6.png"));

    }

    public void render(SpriteBatch batch, float delta) {
        if (isDeploying) {
            if (deployDestroy) {
                if (timerToDestroy > 0) {
                    timerToDestroy -= delta;
                } else {
                    if (alpha > 0) {
                        alpha -= alphaSpeed * delta;
                        alphaSpeed *= accelerationAlpha;
                    } else {
                        isDeploying = false;
                        isActive = false;
                        deployDestroy = false;
                        alpha = 0;
                        timerToDestroy = 0.95f;
                        alphaSpeed = speedAlphaStatic;
                        boostController.destroy();
                    }
                }
            } else {
                if (alpha < 1) {
                    alpha += alphaSpeed * delta;
                    alphaSpeed *= accelerationAlpha;
                } else {
                    alpha = 1;
                    isAnimFinished = true;
                    isDeploying = false;
                    deployDestroy = true;
                    alphaSpeed = speedAlphaStatic;
                }
            }
        }
        if (isActive) {
            Color c = batch.getColor();
            batch.setColor(c.r, c.g, c.b, alpha);
            batch.begin();
            batch.draw(bw, 0, 0, 480, 800);
            batch.end();

            batch.begin();
            if (isAnimFinished) {
                switch (mode) {
                    case 3:
                        moveCoins(batch, delta, 1);
                        break;
                    case 6:
                        moveCoins(batch, delta, 2);

                        break;
                    case 2:
                        renderElements(batch, delta, "crystal");
                        menuCoins.getCoins().renderCrystals(batch, delta);

                        break;
                    case 1:
                        if (isElementGoing.get(0)) {
                            moveElement(winElementsGoto.get(0), posGoTo, 0, rectangleOneWH);
                            batch.draw(winElementsTexture.get(mode - 1), winElementsGoto.get(0).getX(), winElementsGoto.get(0).getY(), winElementsGoto.get(0).getWidth(), winElementsGoto.get(0).getHeight());

                        } else if (!isDeploying) {
                            isDeploying = true;
                            boostController.plusElement("bomb", 1);
                        }
                        boostController.render(batch, delta);

                        break;

                    case 5:
                        if (isElementGoing.get(0)) {
                            moveElement(winElementsGoto.get(0), posGoTo, 0, rectangleOneWH);
                            batch.draw(winElementsTexture.get(mode - 1), winElementsGoto.get(0).getX(), winElementsGoto.get(0).getY(), winElementsGoto.get(0).getWidth(), winElementsGoto.get(0).getHeight());

                        } else if (!isDeploying) {
                            isDeploying = true;
                            boostController.plusElement("row", 1);
                        }
                        boostController.render(batch, delta);

                        break;
                    case 4:
                        renderElements(batch, delta, "heart");
                        menuCoins.getCoins().renderHearts(batch, delta);

                }
            }
            batch.end();

        }
    }

    private void renderElements(SpriteBatch batch, float delta, String element) {
        if (!isDeploying) {
            batch.draw(winElementsTexture.get(mode - 1), winElementsGoto.get(0).getX(), winElementsGoto.get(0).getY(), winElementsGoto.get(0).getWidth(), winElementsGoto.get(0).getHeight());
            batch.draw(winElementsTexture.get(mode - 1), winElementsGoto.get(1).getX(), winElementsGoto.get(1).getY(), winElementsGoto.get(1).getWidth(), winElementsGoto.get(1).getHeight());
            batch.draw(winElementsTexture.get(mode - 1), winElementsGoto.get(2).getX(), winElementsGoto.get(2).getY(), winElementsGoto.get(2).getWidth(), winElementsGoto.get(2).getHeight());

        }
        if (!menuCoins.isDown() && !isAnimationElementDeployed) {
            menuCoins.setDeployed(true, true);
            isAnimationElementDeployed = true;
        }
        for (int i = 0; i <= 2; i++) {
            if (isElementGoing.get(i)) {
                if (moveElement(winElementsGoto.get(i), posGoTo, i, rectanglesWH)) {
                    switch (element) {
                        case "crystal":
                            menuCoins.getCoins().addCrystals();
                            break;
                        case "heart":
                            menuCoins.getCoins().addHearts();
                            break;
                    }

                }
                if (!isElementsMoving) {
                    if (timerToGo > 0) {
                        timerToGo -= delta;
                    } else if (i != 2) {
                        if (!isElementGoing.get(i + 1)) {
                            isElementGoing.set(i + 1, true);
                            timerToGo = timerToGoStatic;
                        }
                        if (isElementGoing.get(2))
                            isElementsMoving = true;
                    }
                }
            }
        }

        if (isElementsMoving && !isElementGoing.get(0) && !isElementGoing.get(1) && !isElementGoing.get(2)) {
            isElementsMoving = false;
            isDeploying = true;
            if (menuCoins.isDown())
                menuCoins.setDeployed(true, false);

        }
    }

    private void moveCoins(SpriteBatch batch, float delta, int count) {
        if (isElementGoing.get(0)) {
            moveElement(winElementsGoto.get(0), posGoTo, 0, rectangleOneWH);
            batch.draw(winElementsTexture.get(mode - 1), winElementsGoto.get(0).getX(), winElementsGoto.get(0).getY(), winElementsGoto.get(0).getWidth(), winElementsGoto.get(0).getHeight());

        } else if (!isDeploying) {
            isDeploying = true;
            boostController.plusElement("coins", count);
        }
        boostController.render(batch, delta);
    }

    private boolean moveElement(Rectangle element, Rectangle goTo, int elementIndex, float rectangleStaticWH) {
        if (!goTo.contains(element)) {
            element.x += speedDel.get(elementIndex).x + accelerationElement.get(elementIndex).x;
            element.y += speedDel.get(elementIndex).y + accelerationElement.get(elementIndex).y;
            accelerationElement.get(elementIndex).x += speedDel.get(elementIndex).x / 30;
            accelerationElement.get(elementIndex).y += speedDel.get(elementIndex).y / 30;

            element.width = (goTo.width - minusHW) + ((rectangleStaticWH - (goTo.width - minusHW)) * ((goTo.getX() - element.getX()) / accStop / (speedDel.get(elementIndex).x)));
            element.height = (goTo.height - minusHW) + ((rectangleStaticWH - (goTo.height - minusHW)) * ((goTo.getY() - element.getY()) / accStop / (speedDel.get(elementIndex).y)));

            return false;
        } else {
            isElementGoing.set(elementIndex, false);
            return true;
        }
    }

    public void setDeploying(boolean deploying, int Mode, Preferences preferences) {
        this.mode = Mode;
        this.isDeploying = deploying;
        alphaSpeed = speedAlphaStatic;
        isActive = true;
        isElementsMoving = false;
        isAnimFinished = false;
        isAnimationElementDeployed = false;
        boostController = new BoostController(preferences);
        accelerationElement = new Array<>();
        winElementsGoto = new Array<>();
        isElementGoing = new Array<>();
        speedDel = new Array<>();
        switch (mode) {
            case 1:
                createOneElement("bomb");
                break;
            case 4:
                createElements(menuCoins.getCoins().getHeartRect());


                break;
            case 2:

                createElements(menuCoins.getCoins().getCrystalRect());
                break;
            case 3:
            case 6:
                createOneElement("coins");
                break;


            case 5:
                createOneElement("row");

                break;

        }

    }

    public void createElements(Rectangle goTo) {
        winElementsGoto.add(new Rectangle(480 / 2 - rectanglesWH, 800 / 2, rectanglesWH, rectanglesWH));
        winElementsGoto.add(new Rectangle(480 / 2, 800 / 2, rectanglesWH, rectanglesWH));
        winElementsGoto.add(new Rectangle(480 / 2 - rectanglesWH / 2, 800 / 2 - rectanglesWH, rectanglesWH, rectanglesWH));


        posGoTo = goTo;
        posGoTo.y = 755;
        // posGoTo.x += (posGoTo.width/2);
        speedDel.add(new Vector2((posGoTo.getX() - winElementsGoto.get(0).getX()) / accStop, (posGoTo.getY() - winElementsGoto.get(0).getY()) / accStop));
        speedDel.add(new Vector2((posGoTo.getX() + 1 - winElementsGoto.get(1).getX()) / accStop, (posGoTo.getY() - winElementsGoto.get(1).getY()) / accStop));
        speedDel.add(new Vector2((posGoTo.getX() - winElementsGoto.get(2).getX()) / accStop, (posGoTo.getY() - winElementsGoto.get(2).getY()) / accStop));
        isElementGoing.add(true);
        isElementGoing.add(false);
        isElementGoing.add(false);
        accelerationElement.add(speedDel.get(0));
        accelerationElement.add(speedDel.get(1));
        accelerationElement.add(speedDel.get(2));
        timerToGo = timerToGoStatic;
    }

    public void createOneElement(String elementID) {
        winElementsGoto.add(new Rectangle(480 / 2 - rectangleOneWH / 2, 800 / 2 - rectangleOneWH / 2, rectangleOneWH, rectangleOneWH));
        posGoTo = new Rectangle(boostController.getElementRect(elementID));
        speedDel.add(new Vector2((posGoTo.getX() - winElementsGoto.get(0).getX()) / accStop, (posGoTo.getY() - winElementsGoto.get(0).getY()) / accStop));
        isElementGoing.add(true);
        accelerationElement.add(speedDel.get(0));
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isAnimFinished() {
        return isAnimFinished;
    }

    public boolean isDeployDestroy() {
        return deployDestroy;
    }

    public void dispose() {
        boostController.destroy();

    }
}
