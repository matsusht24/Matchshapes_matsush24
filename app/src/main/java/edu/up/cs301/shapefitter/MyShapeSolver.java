package edu.up.cs301.shapefitter;

/**
 * Solver: finds fit for a shape; completed solution by Vegdahl.
 *
 * @author **** put your name here ****
 * @version **** put completion date here ****
 */
public class MyShapeSolver extends ShapeSolver {
    protected boolean[][] curArea;
    protected int curRow;
    protected int curCol;
    private boolean found;
    private int or;
    /**
     * Creates a solver for a particular problem.
     *
     * @param parmShape the shape to fit
     * @param parmWorld the world to fit it into
     * @param acc to send notification messages to
     */
    public MyShapeSolver(boolean[][] parmShape, boolean[][] parmWorld, ShapeSolutionAcceptor acc) {
        // invoke superclass constructor
        super(parmShape, parmWorld, acc);
        curArea = new boolean[shape.length][shape.length];
        found = false;



    }

    /**
     * Solves the problem by finding a fit, if possible. The last call to display tells where
     * the fit is. If there is no fit, no call to display should be made--alternatively, a call to
     * undisplay can be made.
     */
    public void solve() {
        //labels the outer for loop for the break
        outerloop:
        //for loop that makes the shape check every col and row for the right fit
        for (curRow = 0; curRow < world.length - shape.length; curRow++) {

                for (curCol = 0; curCol < world[curRow].length - shape.length; curCol++) {
                    //for loop to check every orientation of the shape
                    for(or = 0; or < 8; or++)
                    {
                        //calls makeArea and OrDisplay to create the area it is in the world and to show what shape its trying to match
                        makeArea(curRow,curCol);
                        orDisplay();
                        if(findShape(curRow,curCol) == true) {
                            found = true;
                            break outerloop;
                         }
                        turnRight(curRow,curCol);
                        if(or == 3 || or == 7){
                            reflect();
                        }
                    }
                }
        }
    if(found){
        orDisplay();
    } else{
        undisplay();
    }



    }

    /**
     * Checks if the shape is well-formed: has at least one square, and has all squares connected.
     *
     * @return whether the shape is well-formed
     */
    public boolean check() {
        return Math.random() < 0.5;
    }

    //method that checks the current 6x6 space its in for the shape
    public boolean findShape(int row, int col){
        for(int i = 0; i < shape.length; i++){
            for(int j = 0; j < shape.length; j++){
                if(shape[i][j] == true){
                    if (shape[i][j] != curArea[i][j]){
                        return false;
                    }

                }
            }
        }
        //if the shape exist, it finds the first location to send back for display
        for(int i = 0; i < shape.length; i++){
            for(int j = 0; j < shape.length; j++){
                if(shape[i][j] == curArea[i][j]){
                    return true;
                }
            }
        }
    return true;
    }
    //method to create a 6x6 array of the current space its in the world
    public void makeArea(int row, int col) {
        for(int i = 0; i < shape.length; i++){
            for(int j = 0; j < shape.length; j++){
                curArea[i][j] = world[i+row][j+col];


            }
        }
    }

    //method makes the current shape turn 90 degrees clockwise, into a shape we will call x
    public void turnRight(int row, int col){
        //creates a temporary double array to store x
        boolean[][] tempShape = new boolean[shape.length][shape.length];
        for(int i = 0; i < shape.length; i++){
            for(int j = 0; j < shape.length; j++){
                tempShape[i][j] = false;
            }
        }
        //changes the values in the temp array to look like x
        for(int i = 0; i < shape.length; i++){
            for(int j = 0; j < shape.length; j++){
                if(shape[i][j] == true){
                    tempShape[j][(shape.length -1)- i] = true;
                }
            }
        }
        shape = tempShape;

    }
    //a method to reflect the shape from left to right, into a shape we will call y
    public void reflect(){
        //creates a temporary double array to store y
        boolean[][] tempShape = new boolean[shape.length][shape.length];
        for(int i = 0; i < shape.length; i++){
            for(int j = 0; j < shape.length; j++){
                tempShape[i][j] = false;
            }
        }
        //changes the values in the temp array to look like y
        for(int i = 0; i < shape.length; i++){
            for(int j = 0; j < shape.length; j++){
                if(shape[i][j] == true){
                    tempShape[i][(shape.length -1) - j] = true;
                }
            }
        }
        shape = tempShape;
    }
    //method to display the shape and its orientation
    public void orDisplay(){
        if(or == 0){
            display(curRow, curCol, Orientation.ROTATE_NONE);
        }
        if(or == 1){
            display(curRow, curCol, Orientation.ROTATE_CLOCKWISE);
        }
        if(or == 2){
            display(curRow, curCol, Orientation.ROTATE_180);
        }
        if(or == 3){
            display(curRow, curCol, Orientation.ROTATE_COUNTERCLOCKWISE);
        }
        if(or == 4){
            display(curRow, curCol, Orientation.ROTATE_NONE_REV);
        }
        if(or == 5){
            display(curRow, curCol, Orientation.ROTATE_CLOCKWISE_REV);
        }
        if(or == 6){
            display(curRow, curCol, Orientation.ROTATE_180_REV);
        }
        if(or == 7){
            display(curRow, curCol, Orientation.ROTATE_COUNTERCLOCKWISE_REV);
        }
    }


}

