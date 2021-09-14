package edu.up.cs301.shapefitter;

/**
 * Solver: finds fit for a shape; completed solution by Vegdahl.
 *
 * @author **** put your name here ****
 * @version **** put completion date here ****
 */
public class MyShapeSolver extends ShapeSolver {
    //double array to hold the current area being inspected
    protected boolean[][] curArea;
    //integers for the current row and collum of the first element that is being inspected
    protected int curRow;
    protected int curCol;
    //boolean for whether the shape is matched
    private boolean found;
    //integer to keep track of the orientation
    private int or;
    //boolean for whether the shape is the same as the original
    private boolean same;
    //double array that holds the original orientation of the shape
    protected boolean[][] origShape;
    //array containing the non repeating orientations
    private int[] ori;
    //the amount of total non repeating orientations
    private int orCount;
    private int initialCheck;
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
        //declares instance variables
        curArea = new boolean[shape.length][shape.length];
        found = false;
        same = false;
        origShape = shape;
        ori = new int[8];
        orCount = 0;
        initialCheck = 0;



    }

    /**
     * Solves the problem by finding a fit, if possible. The last call to display tells where
     * the fit is. If there is no fit, no call to display should be made--alternatively, a call to
     * undisplay can be made.
     */
    public void solve() {
        for(or = 0; or < 8; or++)
        {
            orDisplay();
            if(!same){
              ori[orCount] = or;
              orCount +=1;
            }

        }
       initialCheck = 1;
        //labels the outer for loop for the break
        outerloop:
        //for loop that makes the shape check every col and row for the right fit
        for (curRow = 0; curRow < world.length - shape.length + 1; curRow++) {

                for (curCol = 0; curCol < world[curRow].length - shape.length + 1; curCol++) {
                    //for loop to check every orientation of the shape
                    for(int i = 0; i < orCount; i++)
                    {
                        //calls makeArea and OrDisplay to create the area it is in the world and to show what shape its trying to match
                        makeArea();
                        or = ori[i];
                        orDisplay();
                        if(findShape() == true) {
                            found = true;
                            break outerloop;
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
    public boolean findShape(){
        for(int i = 0; i < shape.length; i++){
            for(int j = 0; j < shape.length; j++){
                if(shape[i][j] == true){
                    if (shape[i][j] != curArea[i][j]){
                        return false;
                    }

                }
            }
        }

    return true;
    }
    //method to create a 6x6 array of the current space its in the world
    public void makeArea() {
        for(int i = 0; i < shape.length; i++){
            for(int j = 0; j < shape.length; j++){
                curArea[i][j] = world[i+curRow][j+curCol];


            }
        }
    }

    //method makes the current shape turn 90 degrees clockwise, into a shape we will call x
    public void turnRight(){
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
        //checks if the rotation is the same as the shape
        if(tempShape == shape){
            same = true;
        }else{
            same = false;
            shape = tempShape;
        }


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
        if(tempShape == shape){
            same = true;
        }else{
            same = false;
            shape = tempShape;
        }

    }
    //method to display the shape and its orientation
    public void orDisplay(){
        if(or == 0){
            shape = origShape;
            display(curRow, curCol, Orientation.ROTATE_NONE);
        }
        if(or == 1){
            turnRight();
            if(initialCheck == 1) {
                display(curRow, curCol, Orientation.ROTATE_CLOCKWISE);
            }
        }
        if(or == 2){
            for(int i = 0; i < 2; i++){
                turnRight();
            }
            if(initialCheck == 1) {
                display(curRow, curCol, Orientation.ROTATE_180);
            }
        }
        if(or == 3){
            for(int i = 0; i < 3; i++){
                turnRight();
            }
            if(initialCheck == 1) {
                display(curRow, curCol, Orientation.ROTATE_COUNTERCLOCKWISE);
            }
        }
        if(or == 4){
            shape = origShape;
            reflect();
            if(initialCheck == 1) {
                display(curRow, curCol, Orientation.ROTATE_NONE_REV);
            }

        }
        if(or == 5){
            turnRight();
            if(initialCheck == 1) {
                display(curRow, curCol, Orientation.ROTATE_COUNTERCLOCKWISE_REV);
            }

        }
        if(or == 6){
            for(int i = 0; i < 2; i++){
                turnRight();
            }
            if(initialCheck == 1) {
                display(curRow, curCol, Orientation.ROTATE_180_REV);
            }

        }
        if(or == 7){
            for(int i = 0; i < 3; i++){
                turnRight();
            }
            if(initialCheck == 1) {
                display(curRow, curCol, Orientation.ROTATE_CLOCKWISE_REV);
            }

        }
    }


}

