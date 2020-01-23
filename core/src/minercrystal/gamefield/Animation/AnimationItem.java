package minercrystal.gamefield.Animation;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import minercrystal.gamefield.MovingHandler;
import minercrystal.gamefield.Objects.Item;
import minercrystal.gamefield.WorkScreens.Tools.InterfacePlayScreen.BoostController;

public class AnimationItem {
    Array<Texture> modes;
    Texture backFont;
    Array<Vector2> movedStop;
    Array<Array<Vector2>> Matrix;
    float x, y, SPEED = (float) 2, DELTA = 8f;
    public boolean isMoving = false;
    Item item;
    MovingHandler handler;
    int SX1, SX2, SY1, SY2, tx, ty, Direction, count;
    OrthographicCamera camera;
    private BoostController boostController;
    public AnimationItem(int ModesSize, Array<Array<Vector2>> Matrix, MovingHandler handler, double SPEED, BoostController boostController) {
        modes = new Array<Texture>();
        this.handler = handler;
        this.boostController = boostController;
        this.Matrix = Matrix;
        camera = new OrthographicCamera(480, 800);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        movedStop = new Array<Vector2>();
        backFont = new Texture("tools/groundBack.png");

        for (int i = 1; i < ModesSize; i++)
            modes.add(new Texture("im" + String.valueOf(i) + ".png"));

    }
    public void drawBackFont(SpriteBatch batch){
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backFont, 0, 0, 480, Matrix.get(Matrix.size - 1).get(Matrix.get(Matrix.size - 1).size - 1).y + 52);
        batch.end();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void render(SpriteBatch batch, Array<Array<Item>> Items) {

        isMoving = false;
        for (Array<Item> x : Items) {
            for (Item item : x) {
                batch.draw(modes.get(item.getMODE() - 1), item.getRectangle().getX(), item.getRectangle().getY(), item.getRectangle().getWidth(), item.getRectangle().getHeight());
                if (item.isMove()) {
                    isMoving = true;
                    if (MoveToXY(item, item.getMoveToXY().x, item.getMoveToXY().y) && item.isMovingFalse() && !item.isNeedToDestroy())
                        movedStop.add(new Vector2(Items.indexOf(x, false), x.indexOf(item, false)));
                }
            }
        }
        if (!isMoving && movedStop.size > 1) {
            for (Vector2 itemIndex : movedStop) {

                SX1 = (int) itemIndex.x;
                SY1 = (int) itemIndex.y;
                SX2 = Items.get((int) itemIndex.x).get((int) itemIndex.y).IndexMoveX;
                SY2 = Items.get((int) itemIndex.x).get((int) itemIndex.y).IndexMoveY;

                Direction = getDirection(SX1, SY1, SX2, SY2);
                if (handler.destroyIfExistTheSame(SX1, SY1, SX2, SY2, tx, ty, Items, boostController))
                    count++;
            }
            if (handler.isNeedDestroy())
                if (Direction != -1) {
                    if (Direction < 2) {
                        Items.get(SX1).swap(SY1, SY2);
                        //Для Оси У
                    } else {
                        item = Items.get(SX1).get(SY1);
                        Items.get(SX1).set(SY1, Items.get(SX2).get(SY2));
                        Items.get(SX2).set(SY2, item);
                        //Для оси Х
                    }
                }

            for (int i = 0; i < handler.toDestroy.size; i++)
                Items.get((int) handler.toDestroy.get(i).x).get((int) handler.toDestroy.get(i).y).setNeedToDestroy(true);

            if (count == 0 && movedStop.size == 2) {
                Items.get(SX1).get(SY1).setMoveTo((int) Matrix.get(SX1).get(SY1).x, (int) Matrix.get(SX1).get(SY1).y, false, 0, 0, SPEED);
                Items.get(SX2).get(SY2).setMoveTo((int) Matrix.get(SX2).get(SY2).x, (int) Matrix.get(SX2).get(SY2).y, false, 0, 0, SPEED);
            }
            count = 0;
            movedStop.clear();
        }

    }

    private boolean MoveToXY(Item rectangle, float MoveToX, float MoveToY) {
        if (MoveToX > rectangle.getRectangle().getX() + SPEED)
            rectangle.rectangle.x += rectangle.getSPEED();

        if (MoveToX < rectangle.getRectangle().getX() - SPEED)
            rectangle.rectangle.x -= rectangle.getSPEED();

        if (MoveToY > rectangle.getRectangle().getY() + SPEED)
            rectangle.rectangle.y += rectangle.getSPEED();

        if (MoveToY < rectangle.getRectangle().getY() - SPEED)
            rectangle.rectangle.y -= rectangle.getSPEED();

        if ((rectangle.getRectangle().getY() - SPEED - DELTA < MoveToY && MoveToY < rectangle.getRectangle().getY() + SPEED + DELTA)
                && (rectangle.getRectangle().getX() - SPEED - DELTA < MoveToX && MoveToX < rectangle.getRectangle().getX() + SPEED + DELTA)) {
            rectangle.setMove(false);
            rectangle.rectangle.setPosition(MoveToX, MoveToY);
            return true;
        } else {
            return false;
        }
    }

    public void itemMoving(int DIRECTION, float X, float Y, int SX, int SY, Array<Array<Item>>
            Items, Array<Array<Vector2>> Matrix) {
        tx = SX;
        ty = SY;
        x = Matrix.get(SX).get(SY).x;
        y = Matrix.get(SX).get(SY).y;
        switch (DIRECTION) {
            case 0:       //Y--
                if (!Items.get(SX).get(SY).isMove() && !Items.get(SX).get(SY - 1).isMove())
                    if (Math.abs((int) (y - Items.get(SX).get(SY).getRectangle().getY())) > Items.get(SX).get(SY).getRectangle().height / 3) {
                        Items.get(SX).get(SY).setMoveTo((int) Matrix.get(SX).get(SY).x, (int) Matrix.get(SX).get(SY - 1).y, true, SX, SY - 1, SPEED);
                        Items.get(SX).get(SY - 1).setMoveTo((int) Matrix.get(SX).get(SY).x, (int) Matrix.get(SX).get(SY).y, true, SX, SY, SPEED);
                    } else if (Y > 0 && Y < Items.get(SX).get(SY).getRectangle().height) {
                        Items.get(SX).get(SY).rectangle.y = y - Y;
                        Items.get(SX).get(SY - 1).rectangle.y = Matrix.get(SX).get(SY - 1).y + Y;
                    }
                break;
            case 1:       //Y++
                if (!Items.get(SX).get(SY).isMove() && !Items.get(SX).get(SY + 1).isMove())
                    if (Math.abs((int) (y - Items.get(SX).get(SY).getRectangle().getY())) > Items.get(SX).get(SY).getRectangle().height / 3) {
                        Items.get(SX).get(SY).setMoveTo((int) Matrix.get(SX).get(SY).x, (int) Matrix.get(SX).get(SY + 1).y, true, SX, SY + 1, SPEED);
                        Items.get(SX).get(SY + 1).setMoveTo((int) Matrix.get(SX).get(SY).x, (int) Matrix.get(SX).get(SY).y, true, SX, SY, SPEED);
                    } else if (Y < 0 && Y > Items.get(SX).get(SY).getRectangle().height * -1) {
                        Items.get(SX).get(SY).rectangle.y = y - Y;
                        Items.get(SX).get(SY + 1).rectangle.y = Matrix.get(SX).get(SY + 1).y + Y;
                    }
                break;
            case 2:      //X--
                if (!Items.get(SX).get(SY).isMove() && !Items.get(SX - 1).get(SY).isMove())
                    if (Math.abs((int) (x - Items.get(SX).get(SY).getRectangle().getX())) > Items.get(SX).get(SY).getRectangle().height / 3) {
                        Items.get(SX).get(SY).setMoveTo((int) Matrix.get(SX - 1).get(SY).x, (int) Matrix.get(SX).get(SY).y, true, SX - 1, SY, SPEED);
                        Items.get(SX - 1).get(SY).setMoveTo((int) Matrix.get(SX).get(SY).x, (int) Matrix.get(SX).get(SY).y, true, SX, SY, SPEED);
                    } else if (X > 0 && X < Items.get(SX).get(SY).getRectangle().height) {
                        Items.get(SX).get(SY).getRectangle().x = x - X;
                        Items.get(SX - 1).get(SY).getRectangle().x = Matrix.get(SX - 1).get(SY).x + X;
                    }
                break;
            case 3:      //X++
                if (!Items.get(SX).get(SY).isMove() && !Items.get(SX + 1).get(SY).isMove())
                    if (Math.abs((int) (x - Items.get(SX).get(SY).getRectangle().getX())) > Items.get(SX).get(SY).getRectangle().height / 3) {
                        Items.get(SX).get(SY).setMoveTo((int) Matrix.get(SX + 1).get(SY).x, (int) Matrix.get(SX).get(SY).y, true, SX + 1, SY, SPEED);
                        Items.get(SX + 1).get(SY).setMoveTo((int) Matrix.get(SX).get(SY).x, (int) Matrix.get(SX).get(SY).y, true, SX, SY, SPEED);
                    } else if (X < 0 && X > Items.get(SX).get(SY).getRectangle().height * -1) {
                        Items.get(SX).get(SY).getRectangle().x = x - X;
                        Items.get(SX + 1).get(SY).getRectangle().x = Matrix.get(SX + 1).get(SY).x + X;
                    }
                break;
        }
    }

    public void setTouchUp(Array<Array<Item>> Items) {
        for (int i = 0; i < Items.size; i++)
            for (int i2 = 0; i2 < Items.get(i).size; i2++) {
                if (Items.get(i).get(i2).isTouch() && !Items.get(i).get(i2).isMove()) {
                    Items.get(i).get(i2).setMoveTo((int) Matrix.get(i).get(i2).x, (int) Matrix.get(i).get(i2).y, false, 0, 0, SPEED);
                    Items.get(i).get(i2).setTouch(false);
                }
            }
    }

    private int getDirection(int SX1, int SY1, int SX2, int SY2) {
        if (SY1 > SY2)
            return 0;
        if (SY1 < SY2)
            return 1;
        if (SX1 > SX2)
            return 2;
        if (SX1 < SX2)
            return 3;
        return -1;
    }

    public boolean isMoving() {
        return isMoving;
    }
}
