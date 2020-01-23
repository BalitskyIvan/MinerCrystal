package minercrystal.gamefield.Objects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Item {

    public int MoveToX = 0, MoveToY = 0, IndexMoveX = 0, IndexMoveY = 0;
    public Rectangle rectangle;
    private int MODE;
    private boolean isMove = false, isTouch = false, isNeedToDestroy = false, isMovingFalse = false;
    private double SPEED = 2;

    public Item(float X, float Y, int WEIGHT, int HEIGHT) {
        this.rectangle = new Rectangle(X, Y, WEIGHT, HEIGHT);
    }

    public void setMODE(int MODE) {
        this.MODE = MODE;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setMoveTo(int X, int Y, boolean isMovingFalse, int IndexMoveX, int IndexMoveY, double SPEED) {
        this.MoveToX = X;
        this.MoveToY = Y;
        this.IndexMoveX = IndexMoveX;
        this.IndexMoveY = IndexMoveY;
        this.isMovingFalse = isMovingFalse;
        this.SPEED = SPEED;
        isMove = true;
    }
    public boolean isContains(float X, float Y) {
        return rectangle.contains(X, Y);
    }

    public boolean isMovingFalse() {
        return isMovingFalse;
    }

    public int getMODE() {
        return MODE;
    }

    public Vector2 getMoveToXY() {
        return new Vector2(MoveToX, MoveToY);
    }


    public boolean isTouch() {
        return isTouch;
    }

    public void setTouch(boolean touch) {
        isTouch = touch;
    }



    public void setMove(boolean move) {
        isMove = move;
    }

    public boolean isMove() {
        return isMove;
    }

    public boolean isNeedToDestroy() {
        return isNeedToDestroy;
    }

    public double getSPEED() {
        return SPEED;
    }

    public void setNeedToDestroy(boolean needToDestroy) {
        isNeedToDestroy = needToDestroy;
    }
}