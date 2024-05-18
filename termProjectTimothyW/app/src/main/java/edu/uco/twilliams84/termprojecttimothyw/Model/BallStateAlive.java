package edu.uco.twilliams84.termprojecttimothyw.Model;

public class BallStateAlive extends GameObjectState{
    Ball ball;

    public BallStateAlive(GameObject gameObject) {
        super(gameObject);
        ball = (Ball) gameObject;
    }

    @Override
    public void update() {
        //Notify observers
        ball.notifyObservers();

        //Moving the ball
        if (ball.getPoint() != ball.getDestination()) {
            if (ball.getDestination().y > ball.getPoint().y) {
                if (ball.getPoint().y + ball.VELOCITY_Y > ball.getDestination().y) {
                    ball.getPoint().y = ball.getDestination().y;
                } else {
                    ball.getPoint().y += ball.VELOCITY_Y;
                }
            } else if (ball.getDestination().y < ball.getPoint().y) {
                if (ball.getPoint().y - ball.VELOCITY_Y < ball.getDestination().y) {
                    ball.getPoint().y = ball.getDestination().y;
                } else {
                    ball.getPoint().y -= ball.VELOCITY_Y;
                }
            } else if (ball.getDestination().x > ball.getPoint().x) {
                if (ball.getPoint().x + ball.VELOCITY_X> ball.getDestination().x) {
                    ball.getPoint().x = ball.getDestination().x;
                } else {
                    ball.getPoint().x += ball.VELOCITY_X;
                }
            } else if (ball.getDestination().x < ball.getPoint().x) {
                if (ball.getPoint().x - ball.VELOCITY_X< ball.getDestination().x) {
                    ball.getPoint().x = ball.getDestination().x;
                } else {
                    ball.getPoint().x -= ball.VELOCITY_X;
                }
            }
        }
    }
}
