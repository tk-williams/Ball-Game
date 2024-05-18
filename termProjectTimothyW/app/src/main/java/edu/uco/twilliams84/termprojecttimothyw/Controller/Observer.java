package edu.uco.twilliams84.termprojecttimothyw.Controller;

import edu.uco.twilliams84.termprojecttimothyw.Model.Ball;

public abstract class Observer {
    protected Ball ball;
    public abstract void update();
}
