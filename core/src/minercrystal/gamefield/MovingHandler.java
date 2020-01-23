package minercrystal.gamefield;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import minercrystal.gamefield.Objects.Item;
import minercrystal.gamefield.WorkScreens.Tools.InterfacePlayScreen.BoostController;


public class MovingHandler {
    private boolean Destroy = false;
    Array<Array<Item>> Items;
    int SameX = 1, SameY = 1, count = 0;
    private final static int TOP_M = 7;
    private Array<Vector2> theSame;
    public Array<Vector2> toDestroy;
    Array<Integer> Xs, Ys;

    public MovingHandler() {
        theSame = new Array<>();
        toDestroy = new Array<>();
        Xs = new Array<>();
        Ys = new Array<>();
    }


    public boolean destroyIfExistTheSame(int SX1, int SY1, int SX2, int SY2, int tx, int ty, Array<Array<Item>> Items, BoostController boostController) {

        this.Items = Items;
        theSame.clear();
        Xs.clear();
        Ys.clear();
        int i = 1;

        if (SX2 != SX1 || SY2 != SY1) {
            theSame.add(new Vector2(SX2, SY2));

            if (SY2 + i < TOP_M)
                while (Items.get(SX1).get(SY1).getMODE() == Items.get(SX2).get(SY2 + i).getMODE() && !(SY1 == SY2 + i && SX1 == SX2)) {
                    theSame.add(new Vector2(SX2, SY2 + i));
                    i++;
                    if (SY2 + i > TOP_M)
                        break;
                }
            i = 1;

            if (SY2 > 0)
                while (Items.get(SX1).get(SY1).getMODE() == Items.get(SX2).get(SY2 - i).getMODE() && !(SY1 == SY2 - i && SX1 == SX2)) {
                    theSame.add(new Vector2(SX2, SY2 - i));
                    i++;
                    if (SY2 - i < 0)
                        break;
                }

            isNeedToDestroy();
            theSame.add(new Vector2(SX2, SY2));


            i = 1;
            if (SX2 < TOP_M + 1)
                while (Items.get(SX1).get(SY1).getMODE() == Items.get(SX2 + i).get(SY2).getMODE() && !(SX1 == SX2 + i && SY1 == SY2)) {
                    theSame.add(new Vector2(SX2 + i, SY2));
                    i++;
                    if (SX2 + i > TOP_M + 1)
                        break;

                }
            i = 1;
            if (SX2 > 0)
                while (Items.get(SX1).get(SY1).getMODE() == Items.get(SX2 - i).get(SY2).getMODE() && !(SX1 == SX2 - i && SY1 == SY2)) {
                    theSame.add(new Vector2(SX2 - i, SY2));
                    i++;
                    if (SX2 - i < 0)
                        break;

                }

            if (isNeedToDestroy()) {

                boolean isDefine = false;
                for (Vector2 v : toDestroy) {
                    if (Items.get(tx).get(ty).IndexMoveX == v.x && Items.get(tx).get(ty).IndexMoveY == v.y) {
                        boostController.isRow(tx, ty);
                        isDefine = true;
                    }
                }
                if (!isDefine)
                    for (Vector2 v : toDestroy) {
                        if (tx == v.x && ty == v.y) {
                            boostController.isRow(Items.get(tx).get(ty).IndexMoveX, Items.get(tx).get(ty).IndexMoveY);
                        }
                    }

            }
        }
        if (SX1 == SX2 && SY1 == SY2) {
            theSame.add(new Vector2(SX1, SY1));

            i = 1;
            if (SY1 + i < TOP_M)
                while (Items.get(SX1).get(SY1).getMODE() == Items.get(SX1).get(SY1 + i).getMODE()) {
                    theSame.add(new Vector2(SX1, SY1 + i));
                    i++;
                    if (SY1 + i > TOP_M) break;
                }
            i = 1;
            if (SY1 > 0)
                while (Items.get(SX1).get(SY1).getMODE() == Items.get(SX1).get(SY1 - i).getMODE()) {
                    theSame.add(new Vector2(SX1, SY1 - i));
                    i++;
                    if (SY1 - i < 0) break;
                }
            isNeedToDestroy();
            theSame.add(new Vector2(SX1, SY1));

            i = 1;
            if (SX1 < TOP_M + 1)
                while (Items.get(SX1).get(SY1).getMODE() == Items.get(SX1 + i).get(SY1).getMODE()) {
                    theSame.add(new Vector2(SX1 + i, SY1));
                    i++;
                    if (SX1 + i > TOP_M + 1) break;
                }
            i = 1;
            if (SX1 > 0)
                while (Items.get(SX1).get(SY1).getMODE() == Items.get(SX1 - i).get(SY1).getMODE()) {
                    theSame.add(new Vector2(SX1 - i, SY1));
                    i++;
                    if (SX1 - i < 0) break;
                }
            isNeedToDestroy();

        }


        if (Destroy) sortTheSame();

        return Destroy;
    }


    private boolean isNeedToDestroy() {
        SameX = 0;
        SameY = 0;
        count = 0;
        Xs.clear();
        Ys.clear();
        sortByXY();
        if (theSame.size > 2) {
            for (int i = 0; i < Xs.size - 1; i++) {
                if (Xs.get(i) == Xs.get(i + 1)) {
                    SameX++;
                    if (SameX >= 2) {
                        Destroy = true;
                        toDestroy.addAll(theSame);
                        break;
                    }
                } else {
                    SameX = 0;
                }
            }
            for (int i = 0; i < Ys.size - 1; i++) {
                if (Ys.get(i) == Ys.get(i + 1)) {
                    SameY++;
                    if (SameY >= 2) {
                        Destroy = true;
                        toDestroy.addAll(theSame);
                        break;
                    }
                } else {
                    SameY = 0;
                }
            }
        }
        theSame.clear();
        return Destroy;
    }

    private void sortByXY() {
        for (Vector2 item : theSame) {
            Xs.add((int) item.x);
            Ys.add((int) item.y);
        }
        Xs.sort();
        Ys.sort();
    }

    private void sortTheSame() {
        boolean isSort = false;
        while (!isSort) {
            isSort = true;
            for (int i = 0; i < theSame.size - 1; i++) {
                if (theSame.get(i).y < theSame.get(i + 1).y) {
                    isSort = false;
                    theSame.swap(i, i + 1);
                }
            }
        }
    }

    public boolean isNeedDestroy() {
        return Destroy;
    }

    public void setNeedDestroy(boolean needDestroy) {
        Destroy = needDestroy;
        toDestroy.clear();

    }
}
