package edu.uco.twilliams84.termprojecttimothyw.Model;

public class EnemyStateDead extends GameObjectState{
    public EnemyStateDead(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void update() {
        //Enemy is destroyed
    }
}
