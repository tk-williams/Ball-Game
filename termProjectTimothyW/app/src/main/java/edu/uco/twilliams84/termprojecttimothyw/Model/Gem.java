package edu.uco.twilliams84.termprojecttimothyw.Model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import edu.uco.twilliams84.termprojecttimothyw.View.GameView;

public class Gem extends GameObject {
    public static final int OUTER_RADIUS = 40;
    private final int COLOR = Color.BLUE;

    public Gem(Point point, int radius, GameView gameView) {
        super(point, radius, gameView);
        gameObjectState = new GemStateAlive(this);
    }

    @Override
    public void draw(Canvas canvas) {
        if (gameObjectState instanceof GemStateAlive) {
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(5f);
            paint.setStyle(Paint.Style.STROKE);
            paint.setPathEffect(null);
            canvas.drawCircle(point.x, point.y, OUTER_RADIUS, paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setPathEffect(null);
            paint.setColor(COLOR);
            canvas.drawCircle(point.x, point.y, length, paint);
        }
    }

    @Override
    public void incrementLength() {
        length++;
    }
}