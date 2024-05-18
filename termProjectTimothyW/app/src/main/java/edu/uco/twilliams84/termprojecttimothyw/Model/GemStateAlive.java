package edu.uco.twilliams84.termprojecttimothyw.Model;

public class GemStateAlive extends GameObjectState{
    Gem gem;
    public GemStateAlive(GameObject gameObject) {
        super(gameObject);
        gem = (Gem) gameObject;
    }

    @Override
    public void update() {
        gem.incrementLength();
        if (gem.length >= gem.OUTER_RADIUS) {
            gem.setGameObjectState(new GemStateDead(gameObject));
        }
    }
}
