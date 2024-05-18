package edu.uco.twilliams84.termprojecttimothyw.Model;

public abstract class GameObjectState {
    protected GameObject gameObject;
    protected GameObjectState(GameObject gameObject) {
        this.gameObject = gameObject;
    }
    public abstract void update();
}
