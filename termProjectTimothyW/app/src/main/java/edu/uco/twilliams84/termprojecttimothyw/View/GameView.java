package edu.uco.twilliams84.termprojecttimothyw.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import edu.uco.twilliams84.termprojecttimothyw.Controller.OnSwipeListener;
import edu.uco.twilliams84.termprojecttimothyw.Controller.ScoreObserver;
import edu.uco.twilliams84.termprojecttimothyw.Database.DatabaseOperator;
import edu.uco.twilliams84.termprojecttimothyw.Model.Ball;
import edu.uco.twilliams84.termprojecttimothyw.Model.BallStateAlive;
import edu.uco.twilliams84.termprojecttimothyw.Model.BallStateDead;
import edu.uco.twilliams84.termprojecttimothyw.Model.EnemyStateDead;
import edu.uco.twilliams84.termprojecttimothyw.Model.GameObject;
import edu.uco.twilliams84.termprojecttimothyw.Model.Gem;
import edu.uco.twilliams84.termprojecttimothyw.Model.GemStateDead;
import edu.uco.twilliams84.termprojecttimothyw.Model.Hole;
import edu.uco.twilliams84.termprojecttimothyw.Model.Player;
import edu.uco.twilliams84.termprojecttimothyw.Model.Wall;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;          //Thread containing game loop
    private boolean gameViewOpen;           //Value to keep track of whether view is open or not
    private GameActivity activity;
    private AlertDialog.Builder builder;
    private Ball ball;                      //Ball object used to play the game

    private Integer gameTimer;              //Timer used for countdown
    private long startTime;                 //Used in conjunction with gameTimer

    private static final int FRAME_TIMER = 17;

    //Timer Variables
    private final int MAX_GAME_TIMER = 3;

    //Enemy Variables
    private ArrayList<GameObject> enemies;
    private int numberOfEnemies;
    private final int MIN_NUMBER_OF_ENEMIES = 1;
    private final int MID_NUMBER_OF_ENEMIES = 2;
    private final int MAX_NUMBER_OF_ENEMIES = 3;

    //Gem Variables
    private Gem gem = null;

    //Screen Variables
    public static Integer screenWidth;
    public static Integer screenHeight;

    //Background Variables
    private ArrayList<Point> backgroundPoints;
    private ArrayList<Paint> backgroundPointsPaint;
    private final int BACKGROUND_POINT_RADIUS = 5;
    private Paint backgroundPaint;

    //Score variables
    private Paint textPaint;

    //Position calculation variables
    Random randomNumber;
    int randomPosition;
    int enemyRandomizer;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        activity = (GameActivity) context;
        getHolder().addCallback(this);

        //Declaring the game view to be open
        gameViewOpen = true;

        //Getting Window Dimensions
        setScreenMetrics();

        //Create text
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(100);

        //Create background
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);

        backgroundPoints = new ArrayList<>();
        //Row 1
        backgroundPoints.add(new Point((screenWidth/3)-(screenWidth/6), (screenHeight/3)-(screenHeight/6)));
        backgroundPoints.add(new Point((2*screenWidth/3)-(screenWidth/6), (screenHeight/3)-(screenHeight/6)));
        backgroundPoints.add(new Point((screenWidth)-(screenWidth/6), (screenHeight/3)-(screenHeight/6)));
        //Row 2
        backgroundPoints.add(new Point((screenWidth/3)-(screenWidth/6), (2*screenHeight/3)-(screenHeight/6)));
        backgroundPoints.add(new Point((2*screenWidth/3)-(screenWidth/6), (2*screenHeight/3)-(screenHeight/6)));
        backgroundPoints.add(new Point((screenWidth)-(screenWidth/6), (2*screenHeight/3)-(screenHeight/6)));
        //Row 3
        backgroundPoints.add(new Point((screenWidth/3)-(screenWidth/6), (screenHeight)-(screenHeight/6)));
        backgroundPoints.add(new Point((2*screenWidth/3)-(screenWidth/6), (screenHeight)-(screenHeight/6)));
        backgroundPoints.add(new Point((screenWidth)-(screenWidth/6), (screenHeight)-(screenHeight/6)));

        backgroundPointsPaint = new ArrayList<>();
        for (int i = 0; i < backgroundPoints.size(); i++) {
            backgroundPointsPaint.add(new Paint());
            backgroundPointsPaint.get(i).setColor(Color.BLACK);
        }

        setOnTouchListener(new OnSwipeListener(activity) {
            public void onSwipeUp() {
                if (ball != null && ball.getGameObjectState() instanceof BallStateAlive) {
                    for(int i = 0; i < backgroundPoints.size(); i++) {
                        if (backgroundPoints.get(i).equals(ball.getPoint().x, ball.getPoint().y) && i >= 2) {
                            ball.setDestination(backgroundPoints.get(i - 3));
                            break;
                        }
                    }
                }
            }
            public void onSwipeRight() {
                if (ball != null && ball.getGameObjectState() instanceof BallStateAlive) {
                    for(int i = 0; i < backgroundPoints.size(); i++) {
                        if (backgroundPoints.get(i).equals(ball.getPoint().x, ball.getPoint().y)
                                && i != 2 && i != 5 && i != 8) {
                            ball.setDestination(backgroundPoints.get(i + 1));
                            break;
                        }
                    }
                }
            }
            public void onSwipeLeft() {
                if (ball != null && ball.getGameObjectState() instanceof BallStateAlive) {
                    for(int i = 0; i < backgroundPoints.size(); i++) {
                        if (backgroundPoints.get(i).equals(ball.getPoint().x, ball.getPoint().y)
                                && i != 0 && i != 3 && i != 6) {
                            ball.setDestination(backgroundPoints.get(i - 1));
                            break;
                        }
                    }
                }
            }
            public void onSwipeDown() {
                if (ball != null && ball.getGameObjectState() instanceof BallStateAlive) {
                    for(int i = 0; i < backgroundPoints.size(); i++) {
                        if (backgroundPoints.get(i).equals(ball.getPoint().x, ball.getPoint().y) && i <= 5) {
                            ball.setDestination(backgroundPoints.get(i + 3));
                            break;
                        }
                    }
                }
            }
            public void onTap() {
                if (gem != null && ball.getPoint().equals(gem.getPoint())) {
                    gem.setGameObjectState(new GemStateDead(gem));
                    ball.setGemDestroyed(true);
                }
            }
        });

        //Initializing random number generator
        randomNumber = new Random();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        setScreenMetrics();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        setScreenMetrics();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startGame();
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    public void draw(Canvas canvas) {
        //Draw background
        canvas.drawRect(0, 0, screenWidth, screenHeight, backgroundPaint);
        for (int i = 0; i < backgroundPoints.size(); i++) {
            canvas.drawCircle(backgroundPoints.get(i).x - BACKGROUND_POINT_RADIUS/2
                    , backgroundPoints.get(i).y - BACKGROUND_POINT_RADIUS / 2
                    , BACKGROUND_POINT_RADIUS
                    , backgroundPointsPaint.get(i));
        }
        if (ball.getGameObjectState() instanceof BallStateAlive) {
            if (gameTimer > 0) {
                canvas.drawText(gameTimer.toString(),(screenWidth/2) - (textPaint.measureText(ball.getScore().toString())/2), screenHeight/12,textPaint);
                long currentTime = System.currentTimeMillis();
                if ((currentTime - startTime)/1000 >= 1) {
                    startTime = currentTime;
                    gameTimer--;
                }
            } else if (gameTimer == 0) {
                canvas.drawText("Go!",(screenWidth/2) - (textPaint.measureText(ball.getScore().toString())/2), screenHeight/12,textPaint);
                long currentTime = System.currentTimeMillis();
                if ((currentTime - startTime)/1000 >= 1) {
                    startTime = currentTime;
                    gameTimer--;
                }
                //Draw enemies
                for (GameObject enemy : enemies) {
                    enemy.draw(canvas);
                }
                //Draw ball
                if (ball != null) ball.draw(canvas);
                //Draw gem
                if (gem != null) gem.draw(canvas);
            } else {
                //Draw score
                canvas.drawText(ball.getScore().toString(),(screenWidth/2) - (textPaint.measureText(ball.getScore().toString())/2), screenHeight/12,textPaint);
                //Draw enemies
                for (GameObject enemy : enemies) {
                    if (enemy != null) enemy.draw(canvas);
                }
                //Draw ball
                if (ball != null) ball.draw(canvas);
                //Draw gems
                if (gem != null) gem.draw(canvas);
            }
        }
    }

    public void updateGame() {

        if (gameTimer <= 0) {
            //Game speed
            if (200 > ball.getScore() && ball.getScore() >= 100) {
                GameObject.setLengthIncrement(GameObject.MID_LENGTH_INCREMENT);
            } else if (ball.getScore() >= 200) {
                GameObject.setLengthIncrement(GameObject.MAX_LENGTH_INCREMENT);
            }

            //Killing enemies
            Iterator<GameObject> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                GameObject enemy = enemyIterator.next();
                if (enemy.getGameObjectState() instanceof EnemyStateDead) {
                    if (enemy instanceof Hole && !ball.getPoint().equals(enemy.getPoint())) {
                        ball.setGameObjectState(new BallStateDead(ball));
                    } else if (enemy instanceof Wall && ball.getPoint().equals(enemy.getPoint())) {
                        ball.setGameObjectState(new BallStateDead(ball));
                    }
                    enemyIterator.remove();
                }
            }

            //Number of enemies allowed
            if (ball.getScore() >= 25 && ball.getScore() < 50) {
                numberOfEnemies = MID_NUMBER_OF_ENEMIES;
            } else if (ball.getScore() >= 50) {
                numberOfEnemies = MAX_NUMBER_OF_ENEMIES;
            }

            //Creating enemies
            if (enemies.size() < numberOfEnemies) {
                boolean freePointFound;
                do {
                    freePointFound = true;
                    randomPosition = randomNumber.nextInt(8);
                    for (GameObject enemy : enemies) {
                        if (backgroundPoints.get(randomPosition) == enemy.getPoint()) {
                            freePointFound = false;
                        }
                    }
                } while (!freePointFound);
                enemyRandomizer = randomNumber.nextInt(100);
                boolean holeExists = false;
                for (GameObject enemy : enemies) {
                    if (enemy instanceof Hole) {
                        holeExists = true;
                    }
                }
                if (enemies.size() == 0 || !holeExists) {
                    if (enemyRandomizer >= 51) {
                        enemies.add(new Hole(backgroundPoints.get(randomPosition), 5, this));
                    } else {
                        enemies.add(new Wall(backgroundPoints.get(randomPosition), 5, this));
                    }
                }
            }

            //Creating and deleting gems
            if (gem == null) {
                randomPosition = randomNumber.nextInt(8);
                gem = new Gem(backgroundPoints.get(randomPosition), 5, this);
            } else if (gem.getGameObjectState() instanceof GemStateDead) {
                gem = null;
            }

            //If the ball is dead
            if (ball.getGameObjectState() instanceof BallStateDead) {

                //Stop game thread
                gameThread.running = false;

                //Add score (if logged in)
                if (Player.player != null) {
                    DatabaseOperator databaseOperator = new DatabaseOperator(activity);
                    databaseOperator.addScore(Player.player, ball.getScore());
                }

                //Show notification
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (builder == null) {
                            builder = new AlertDialog.Builder(activity);
                            builder.setTitle("Game Over")
                                    .setMessage("Final Score: " + ball.getScore().toString() + "\nWould you like to try again?");
                            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startGame();
                                }
                            });
                            builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    activity.finish();
                                    Intent intent = new Intent(activity, MenuActivity.class);
                                }
                            });
                            builder.setCancelable(false);
                            builder.show();
                        }
                    }
                });
                //Else, update GameObjects
            } else {
                ball.getGameObjectState().update();
                for (GameObject enemy : enemies) {
                    enemy.update();
                }
                if (gem != null) gem.update();
            }
        }
    }

    public void startGame() {
        //Initializing builder
        builder = null;

        //Initializing ball and observer
        ball = new Ball(new Point(backgroundPoints.get(4)), Ball.BALL_RADIUS, this);
        new ScoreObserver(ball);

        //Initializing GameObject variables
        enemies = new ArrayList<>(MAX_NUMBER_OF_ENEMIES);
        numberOfEnemies = MIN_NUMBER_OF_ENEMIES;
        gem = null;

        //Initializing timer variables
        gameTimer = MAX_GAME_TIMER;
        startTime = System.currentTimeMillis();

        //Initializing static variables
        GameObject.setLengthIncrement(GameObject.MIN_LENGTH_INCREMENT);

        //Starting the game
        if (gameThread != null) gameThread.interrupt();
        gameThread = new GameThread(getHolder());
        gameThread.start();
    }

    private class GameThread extends Thread {
        private SurfaceHolder surfaceHolder;
        private boolean running = true;
        public GameThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        @Override
        public void run() {
            Canvas canvas = null;
            //Time used for frame rate
            long previousFrameTime = System.currentTimeMillis();
            //Time used for scoring
            long scoreFrameTime = System.currentTimeMillis();
            while (running && gameViewOpen) {
                try {
                    //The canvas is exclusively drawn from this thread
                    canvas = surfaceHolder.lockCanvas(null);
                    //Lock the surface holder for drawing
                    synchronized (surfaceHolder) {
                        long currentTime = System.currentTimeMillis();
                        updateGame();
                        if (ball.getGameObjectState() instanceof BallStateAlive) {
                            if (currentTime - scoreFrameTime >= 1000.0) {
                                if (gameTimer <= 0) {
                                    ball.notifyObservers();
                                    scoreFrameTime = currentTime;
                                }
                            }
                            draw(canvas);
                        } else {
                            interrupt();
                        }
                        long currentFrameTime = System.currentTimeMillis();
                        if (currentFrameTime - previousFrameTime < FRAME_TIMER) {
                            sleep(FRAME_TIMER - (currentFrameTime - previousFrameTime));
                        }
                    }
                } catch (InterruptedException e) {
                    //sleep catch
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

    public void setScreenMetrics() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
    }

    public GameThread getGameThread() {
        return gameThread;
    }

    public void setGameViewOpen(boolean gameViewOpen) {
        this.gameViewOpen = gameViewOpen;
    }
}
