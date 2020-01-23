package minercrystal.gamefield.WorkScreens.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

import minercrystal.gamefield.WorkScreens.Tools.InterfaceSelectScreen.MenuCoins;

public class CameraScroller {
    public static final String TAG = "SCROLL_TAG";
    public static final float TIME_TO_SCROLL = 2.5f, MAX_X_DELTA = 100, cameraAcceleration = 1.1f, firstMap = 240, secondMap = firstMap * 3, thirdMap = firstMap * 5, nextMap = firstMap * 7;

    private OrthographicCamera mCamera;
    private final float mLowerPosition;
    private final float mUpperPosition;
    private float mTimer = 0;
    private float mVelocityY = -7, mVelicityX = 1, VelicityYUp = 1;
    private float lastY = 0, lastX = 0, firstX = 0, firstY = 0;
    private float selectorPos, moveTo, xMoveTo;
    private boolean moveDown = false, moveToSide = false, isOTR = false, isTouchUp = true, isNeedToChangeMap = false, isNextMap = false;

    public CameraScroller(OrthographicCamera camera, float lowerPosition, float upperPosition, float selectorPosition) {
        this.selectorPos = selectorPosition;
        mUpperPosition = upperPosition;
        mLowerPosition = lowerPosition;
        mCamera = camera;
        moveDown(selectorPos, camera.position.y);
    }

    public void act(float deltaTime, WorkButton w1, WorkButton w2, MenuCoins menuCoins) {
        if (isTouchUp && moveToSide) {
            if (mCamera.position.x < 480) cameraMove(firstMap);
            if (mCamera.position.x < 960 && mCamera.position.x > 480)
                cameraMove(secondMap);
            if (mCamera.position.x > 960 && mCamera.position.x < 1440)
                cameraMove(thirdMap);
            if(mCamera.position.x > 1440)
                cameraMove(nextMap);
        }

        if (moveDown) {
            float acceleration_y = mVelocityY * 0.01f;// calculate acceleration (the rate of change of velocity)
            mVelocityY -= acceleration_y;// decreasing velocity
            mCamera.position.y += mVelocityY;

            if (mCamera.position.y - 30 < moveTo) {
                moveDown = false;
//                w1.setButtonDeploy(true);
                w2.setButtonDeploy(true);
                menuCoins.setDeployed(true, true);
                mTimer = TIME_TO_SCROLL;
            }
        } else if (mTimer > 0) {// if timer is not 0
            float acceleration_y = mVelocityY * 0.01f;// calculate acceleration (the rate of change of velocity)
            mTimer -= 0.01;// decreasing timer
            mVelocityY -= acceleration_y;// decreasing velocity
            mCamera.position.y += mVelocityY;
            checkCameraPosition();// check if camera position is in not less or more than some value else stop camera (mTimer = 0)
        }
    }

    private void moveDown(float selectorPos, float camPos) {
        moveDown = true;
        moveTo = selectorPos;
        mVelocityY = (selectorPos - camPos) / 100;
    }

    public boolean cameraMove(float positionX) {
        mVelicityX *= cameraAcceleration;
        if (mCamera.position.x > positionX && positionX != nextMap)
            mCamera.position.x -= mVelicityX;
        else
            mCamera.position.x += mVelicityX;
        if(positionX != nextMap) {

            if (mCamera.position.x < positionX + mVelicityX + 1 && mCamera.position.x > positionX - mVelicityX - 1) {
                mVelicityX = 1;
                moveToSide = false;
                mCamera.position.x = positionX;
                return false;
            }
        }else if(mVelicityX > 480) {
            isNextMap = true;
            isNeedToChangeMap = true;
            moveToSide = false;
            mVelicityX = 1;
            mCamera.position.x = firstX;
        }
        mCamera.update();
        return true;
    }

    private void checkCameraPosition() {
        if (mCamera.position.y > mUpperPosition) {
            mCamera.position.y = mUpperPosition;
            mTimer = 0;
        }
        if (mCamera.position.y < mLowerPosition) {
            mCamera.position.y = mLowerPosition;
            mTimer = 0;
        }
    }

    public boolean touchDown(int screenX, int screenY) {
        firstX = screenX;
        firstY = screenY;
        lastX = screenX;
        isTouchUp = false;
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float x = Gdx.input.getDeltaX() * -1;
        float y = Gdx.input.getDeltaY();

        if (screenX > lastX) {
            if (isOTR) {
                firstX = screenX;
                isOTR = false;
            }
        } else {
            if (!isOTR) {
                firstX = screenX;
                isOTR = true;
            }
        }
        if (Math.abs(screenX - firstX) > Math.abs(screenY - firstY)) {
            if (mCamera.position.x + x >= 240) {
                mCamera.translate(x, 0);
                moveToSide = true;
            }
        } else if (!moveToSide && !moveDown) {
            mCamera.translate(0, y);
            mVelocityY = lastY / 2;
            lastY = y;

            if (mCamera.position.y > mUpperPosition) mCamera.position.y = mUpperPosition;
            if (mCamera.position.y < mLowerPosition) mCamera.position.y = mLowerPosition;

        }
        lastX = screenX;
        return false;

    }

    public boolean isNextMap() {
        return isNextMap;
    }

    public boolean isCameraMove() {
        if (!moveToSide && !moveDown)
            return true;
        else
            return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        mTimer = TIME_TO_SCROLL;
        isTouchUp = true;
        return true;
    }



    public boolean isNeedToChangeMap() {
        return isNeedToChangeMap;
    }

    public boolean MoveUp() {
        VelicityYUp *= cameraAcceleration;
        if (mCamera.position.y > mUpperPosition)
            mCamera.position.y -= VelicityYUp;
        else
            mCamera.position.y += VelicityYUp;

        if (mCamera.position.y < mUpperPosition + VelicityYUp + 1 && mCamera.position.y > mUpperPosition - VelicityYUp - 1) {
            VelicityYUp = 1;
            mCamera.position.y = mUpperPosition;
            return false;
        }
        mCamera.update();
        return true;
    }
}