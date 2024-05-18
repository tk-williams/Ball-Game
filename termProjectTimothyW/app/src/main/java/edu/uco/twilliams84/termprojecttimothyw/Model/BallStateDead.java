package edu.uco.twilliams84.termprojecttimothyw.Model;

public class BallStateDead extends GameObjectState{
    Ball ball;

    public BallStateDead(GameObject gameObject) {
        super(gameObject);
        Ball ball = (Ball) gameObject;
    }

    @Override
    public void update() {
        //Add score to high score list
    }
}
