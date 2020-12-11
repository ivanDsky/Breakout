import acm.graphics.GObject;
import acm.program.GraphicsProgram;

import java.awt.event.MouseEvent;

public class Main extends GraphicsProgram {

    /** Width and height of application window in pixels */
    public static final int APPLICATION_WIDTH = 1200;
    public static final int APPLICATION_HEIGHT = 600;

    /** Dimensions of game board (usually the same) */
    public static final int WIDTH = APPLICATION_WIDTH - 15;
    public static final int HEIGHT = APPLICATION_HEIGHT - 60;

    /** Dimensions of the paddle */
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 10;

    /** Offset of the paddle up from the bottom */
    private static final int PADDLE_Y_OFFSET = 500;

    /** Number of bricks per row */
    private static final int NBRICKS_PER_ROW = 10;

    /** Number of rows of bricks */
    private static final int NBRICK_ROWS = 10;

    /** Separation between bricks */
    private static final int BRICK_SEP = 4;

    /** Width of a brick */
    private static final int BRICK_WIDTH =
            (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

    /** Height of a brick */
    private static final int BRICK_HEIGHT = 8;

    /** Radius of the ball in pixels */
    private static final int BALL_RADIUS = 10;

    /** Offset of the top brick row from the top */
    private static final int BRICK_Y_OFFSET = 70;

    /** Number of turns */
    private static final int NTURNS = 3;

    private static final int FRAME_UPDATE = 10;

    Ball ball = new Ball(0,400,BALL_RADIUS,BALL_RADIUS);
    Platform plat = new Platform(PADDLE_WIDTH,PADDLE_HEIGHT);

    /* Method: run() */
    /** Runs the Breakout program. */
    @Override
    public void run() {
        /* You fill this in, along with any subsidiary methods */
        this.setSize(APPLICATION_WIDTH,APPLICATION_HEIGHT);
        addMouseListeners();
        add(ball);
        plat.setLocation(WIDTH / 2 - plat.getWidth() / 2,PADDLE_Y_OFFSET);
        add(plat);
        createBricks();
        while (true){
            ball.moveByVelocity();
            ball.checkWallCollision();
            checkBrick();
            checkPlat();
            pause(FRAME_UPDATE);
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        plat.setLocation(mouseEvent.getX(),plat.getY());
    }

    private void createBricks(){
        double width = WIDTH / 11.0 - 1;
        double height = 25;
        double x = 0,y = BRICK_Y_OFFSET;
        for(int i = 0;i <= 10; ++i){
            x = -width;
            for(int j = 0;j <= 10; ++j){
                x += width + 1;
                if(j == 3 || j == 7)continue;;
                Brick brick = new Brick(x,y,width,height);
                add(brick);
            }
            y += height + 1;
        }
    }

    private void checkBrick(){
        for(double addX = 0;addX <= ball.getBounds().getWidth(); addX += ball.getBounds().getWidth()){
            for(double addY = 0;addY <= ball.getBounds().getWidth(); addY += ball.getBounds().getWidth()){
                GObject elementAt = getElementAt(ball.getX1() + addX, ball.getY1() + addY);
                if(elementAt == null || elementAt == ball || elementAt == plat)continue;
                if(ball.checkRectCollision(elementAt)){
                    remove(elementAt);
                    return;
                }
            }
        }
    }

    private void checkPlat() {
        for(double addX = 0;addX <= ball.getBounds().getWidth(); addX += ball.getBounds().getWidth()){
            for(double addY = 0;addY <= ball.getBounds().getWidth(); addY += ball.getBounds().getWidth()){
                GObject elementAt = getElementAt(ball.getX1() + addX, ball.getY1() + addY);
                if(elementAt == plat && ball.checkRectCollision(elementAt)){
                    return;
                }
            }
        }
    }

}