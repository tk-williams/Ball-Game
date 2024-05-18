package edu.uco.twilliams84.termprojecttimothyw.Model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import edu.uco.twilliams84.termprojecttimothyw.View.GameView;

public abstract class GameObject {
    //GameView
    GameView gameView;

    //Paint
    protected Paint paint;

    //Point
    protected Point point;

    //Length
    protected int length;
    public static final int MIN_LENGTH_INCREMENT = 60;
    public static final int MID_LENGTH_INCREMENT = 45;
    public static final int MAX_LENGTH_INCREMENT = 30;
    protected static int lengthIncrement;

    //State
    protected GameObjectState gameObjectState;

    public GameObject(Point point, int length, GameView gameView) {
        this.point = point;
        this.length = length;
        this.paint = new Paint();
        this.gameView = gameView;
        this.lengthIncrement = MIN_LENGTH_INCREMENT;
    }

    public abstract void draw(Canvas canvas);

    public void update() {
        gameObjectState.update();
    }

    public abstract void incrementLength();

    public static void increaseIncrement(){
        lengthIncrement++;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public static int getLengthIncrement() {
        return lengthIncrement;
    }

    public static void setLengthIncrement(int lengthIncrement) {
        GameObject.lengthIncrement = lengthIncrement;
    }

    public GameObjectState getGameObjectState() {
        return gameObjectState;
    }

    public void setGameObjectState(GameObjectState gameObjectState) {
        this.gameObjectState = gameObjectState;
    }

    public GameView getGameView() {
        return gameView;
    }
}
