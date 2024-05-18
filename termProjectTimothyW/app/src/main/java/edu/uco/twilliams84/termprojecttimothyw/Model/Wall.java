package edu.uco.twilliams84.termprojecttimothyw.Model;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;

import edu.uco.twilliams84.termprojecttimothyw.R;
import edu.uco.twilliams84.termprojecttimothyw.View.GameView;

public class Wall extends GameObject {
    public static final float OUTER_SIDE_LENGTH = 125;
    private final int color = gameView.getResources().getColor(R.color.game_wall_color);
    private final DashPathEffect dashPath = new DashPathEffect(new float[]{20,5}, (float)1.0);

    public Wall(Point point, int length, GameView gameView) {
        super(point, length, gameView);
        gameObjectState = new EnemyStateAlive(this);
    }

    @Override
    public void draw(Canvas canvas) {
        if (gameObjectState instanceof EnemyStateAlive) {
            paint.setColor(color);
            paint.setStrokeWidth(5f);
            paint.setStyle(Paint.Style.STROKE);
            paint.setPathEffect(dashPath);
            canvas.drawRect(point.x - OUTER_SIDE_LENGTH/2, point.y - OUTER_SIDE_LENGTH/2, point.x + OUTER_SIDE_LENGTH/2, point.y + OUTER_SIDE_LENGTH/2, paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setPathEffect(null);
            canvas.drawRect(point.x - length/2, point.y - length/2, point.x + length/2, point.y + length/2, paint);
        }
    }

    @Override
    public void incrementLength() {
        length += OUTER_SIDE_LENGTH/lengthIncrement;
    }
}