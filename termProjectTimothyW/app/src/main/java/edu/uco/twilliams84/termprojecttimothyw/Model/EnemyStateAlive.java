package edu.uco.twilliams84.termprojecttimothyw.Model;

public class EnemyStateAlive extends GameObjectState{
    public EnemyStateAlive(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void update() {
        //Increment the size of the inner shape
        if (gameObject instanceof Wall) {
            Wall wall = (Wall) gameObject;
            if (wall.length >= Wall.OUTER_SIDE_LENGTH) {
                gameObject.setGameObjectState(new EnemyStateDead(gameObject));
            } else {
                gameObject.incrementLength();
            }
        } else if (gameObject instanceof Hole) {
            Hole hole = (Hole) gameObject;
            if (hole.length >= Hole.OUTER_RADIUS) {
                gameObject.setGameObjectState(new EnemyStateDead(gameObject));
            } else {
                gameObject.incrementLength();
            }
        }
    }
}
