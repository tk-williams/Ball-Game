package edu.uco.twilliams84.termprojecttimothyw.Model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;

import edu.uco.twilliams84.termprojecttimothyw.Controller.ScoreObserver;
import edu.uco.twilliams84.termprojecttimothyw.View.GameView;

public class Ball extends GameObject {
    public static final int BALL_RADIUS = 50;

    //Score
    private Integer score;
    private ScoreObserver scoreObserver;
    protected int scoreIncrease;

    //Movement
    public static final int VELOCITY_Y = GameView.screenHeight/7;
    public static final int VELOCITY_X = GameView.screenWidth/7;
    private Point destination;

    //Paint
    private final int OUTER_COLOR = Color.BLACK;
    private final int INNER_COLOR = Color.LTGRAY;

    //Gem Destroyed
    private boolean gemDestroyed;

    public Ball(Point point, int radius, GameView gameView) {
        super(point, radius, gameView);
        destination = point;
        paint.setColor(INNER_COLOR);
        score = 0;
        scoreIncrease = 0;
        gameObjectState = new BallStateAlive(this);
        scoreObserver = new ScoreObserver(this);
        gemDestroyed = false;
    }

    @Override
    public void draw(Canvas canvas) {
        if (gameObjectState instanceof BallStateAlive) {
            paint.setColor(OUTER_COLOR);
            canvas.drawCircle(point.x, point.y, length, paint);
            paint.setColor(INNER_COLOR);
            canvas.drawCircle(point.x, point.y, length-10, paint);
        }
    }

    public void notifyObservers() {
        if (scoreObserver != null) scoreObserver.update();
    }

    public Point getPoint() {
        return point;
    }

    public Point getDestination() {
        return destination;
    }

    public void setDestination(Point destination) {
        this.destination = destination;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isGemDestroyed() {
        return gemDestroyed;
    }

    public void setGemDestroyed(boolean gemDestroyed) {
        this.gemDestroyed = gemDestroyed;
    }

    @Override
    public void incrementLength() {
    }
}