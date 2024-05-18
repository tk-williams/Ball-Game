package edu.uco.twilliams84.termprojecttimothyw.Model;

public class GemStateDead extends GameObjectState{
    public GemStateDead(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void update() {
        //Gem is destroyed
    }
}
