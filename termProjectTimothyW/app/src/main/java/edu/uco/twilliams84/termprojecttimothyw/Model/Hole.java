package edu.uco.twilliams84.termprojecttimothyw.Model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;

import edu.uco.twilliams84.termprojecttimothyw.R;
import edu.uco.twilliams84.termprojecttimothyw.View.GameView;

public class Hole extends GameObject {
    public static final int OUTER_RADIUS = 65;
    private final int COLOR = gameView.getResources().getColor(R.color.game_hole_color);
    private final DashPathEffect dashPath = new DashPathEffect(new float[]{20,5}, (float)1.0);

    public Hole(Point point, int radius, GameView gameView) {
        super(point, radius, gameView);
        gameObjectState = new EnemyStateAlive(this);
    }

    @Override
    public void draw(Canvas canvas) {
        if (gameObjectState instanceof EnemyStateAlive) {
            paint.setStrokeWidth(5f);
            paint.setColor(Color.BLACK);
            paint.setPathEffect(dashPath);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(point.x, point.y, OUTER_RADIUS, paint);
            paint.setColor(COLOR);
            paint.setPathEffect(null);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(point.x, point.y, length, paint);
        }
    }

    @Override
    public void incrementLength() {
        length += OUTER_RADIUS/lengthIncrement;
    }
}