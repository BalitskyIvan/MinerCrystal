package minercrystal.gamefield.Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import minercrystal.gamefield.Animation.PersonAnimation;

public class Person {
    private PersonAnimation personAnimation;
    private int blockToDestroy = 3;
    private boolean isLeft;
    Vector2 person;
    String STATE = "JUMP"; // Player's States: STAY, WALK, DIG1, DIG2, DIG3
    private float velocity = 2, xVelocity = 2.2f, moveToX;
    private final static float acceleration = 3;

    public Person(boolean isLeft) {
        this.isLeft = isLeft;
        personAnimation = new PersonAnimation(isLeft);
        if (!isLeft)
            person = new Vector2(232, 800);
        else
            person = new Vector2(248, 800);
        moveToX = person.x;
    }

    public void update(SpriteBatch spriteBatch, float delta) {
        if (STATE.contains("DIG"))
            STATE = "DIG" + String.valueOf(blockToDestroy);

        switch (STATE) {
            case "JUMP":
                person.y -= velocity;
                velocity += acceleration * delta;
                if (person.y < 520) {
                    person.y = 520;
                    changeState("DIG");
                }
                personAnimation.drawJump(spriteBatch, delta, person);
                break;
            case "WALK":
                moveToNextX();
                personAnimation.drawWalk(spriteBatch, delta, person);
                break;
            case "DIG1":
                personAnimation.drawDigBlock1(spriteBatch, delta, person);
                break;
            case "DIG2":
                personAnimation.drawDigBlock2(spriteBatch, delta, person);
                break;
            case "DIG3":
                personAnimation.drawDigBlock3(spriteBatch, delta, person);
                break;
            default:
                System.out.println("STATE ERROR");
                break;
        }
    }

    public PersonAnimation getPersonAnimation() {
        return personAnimation;
    }

    private void changeState(String state) {
        this.STATE = state;
        personAnimation.setIndexToNULL();
    }

    public void blockDestroyed() {
        blockToDestroy--;
        if(STATE.contains("WALK")) {
            changeState("DIG");
            person.x = moveToX;
        }
        if (blockToDestroy == 0) {
            blockToDestroy = 3;
            changeState("WALK");
            if (!isLeft)
                moveToX -= 80;
            else
                moveToX += 80;
        }
        personAnimation.setIndexToNULL();
    }

    private void moveToNextX() {
        if (!isLeft) {
            if (person.x > moveToX)
                person.x -= xVelocity;
            else {
                changeState("DIG");
                person.x = moveToX;
            }
        } else {

            if (person.x < moveToX)
                person.x += xVelocity;
            else {
                changeState("DIG");
                person.x = moveToX;
            }
        }
    }
}
