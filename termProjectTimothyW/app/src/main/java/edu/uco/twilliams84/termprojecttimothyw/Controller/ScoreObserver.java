package edu.uco.twilliams84.termprojecttimothyw.Controller;

import edu.uco.twilliams84.termprojecttimothyw.Model.Ball;

public class ScoreObserver extends Observer {
    private long startTime;

    public ScoreObserver(Ball ball) {
        this.ball = ball;
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        //Time
        long currentTime = System.currentTimeMillis();
        if ((currentTime - startTime)/1000 >= 1){
            ball.setScore(ball.getScore()+1);
            startTime = currentTime;
        }
        //Gem destroyed
        if (ball.isGemDestroyed()) {
            ball.setScore(ball.getScore()+10);
            ball.setGemDestroyed(false);
        }
    }
}
