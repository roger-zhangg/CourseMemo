// Template for implementing Cost-Based Planner.

import java.util.*;


public class CBPlannerAStar {

    // Limit the total number of expansions.
    static int maxMoves = 1000000;

    // The frontier = all those states that have been generated but not
    // yet explored (visited).
    LinkedList<CarState> frontier;

    // The list of all states that have been visited.
    LinkedList<CarState> visitedStates;
    int[][] costMatrix;
    int[][] grid;
    int split;
    // Count # moves.
    int numMoves;
    String CurrentDirection = "";


    //start
    public LinkedList<CarState> makePlan (int split, int[][] costMartix ,int[][] grid, CarState start, CarState end)
    {
        // Initialize.
        frontier = new LinkedList<CarState> ();
        visitedStates = new LinkedList<CarState> ();
        numMoves = 0;
        this.costMatrix = costMartix;
        this.grid = grid;
        this.split = split;


        // The start node is the first one to place in frontier.
        frontier.add (start);

        while (numMoves < maxMoves) {

            // If nothing to explore, we're done.
            if (frontier.size() == 0) {
                break;
            }

            // Get first node in frontier and expand.
            CarState currentState = removeBest ();
            // problem.drawState (currentState);

            // If we're at a goal node, build the solution.
            if ( currentState.x == end.x && currentState.y == end.y) {
                return makeSolution (currentState);
            }

            numMoves ++;

            // Put in visited list.
            visitedStates.add (currentState);

            // Expand current state (look at its neighbors) and place in frontier.
            ArrayList<CarState> neighbors = getNeighbors (currentState);
            for (CarState s: neighbors) {
                if ( ! visitedStates.contains (s) ) {
                    int index = frontier.indexOf (s);
                    if (index >= 0) {
                        CarState altS = frontier.get (index);
                        if (costMartix[s.y][s.x] < costMartix[altS.y][altS.x]) {
                            frontier.set (index, s);
                        }
                    }
                    else {
                        frontier.add (s);
                    }
                }
            }

            if (numMoves % 100 == 0) {
                System.out.println ("After " + numMoves + ": |F|=" + frontier.size() + "  |V|=" + visitedStates.size());
            }

        } // endwhile

        System.out.println ("Cost-based: No solution found after " + numMoves + " moves");
        return null;
    }

    public ArrayList<CarState> getNeighbors(CarState currentState){
        ArrayList<CarState> neighbors = new ArrayList<CarState> ();
        int currY = currentState.y;
        int currX = currentState.x;
        if (currX-1>0){
            if (grid[currY][currX-1] <= 0){
                CarState tmpState = new CarState(currentState,currX-1,currY);
                //add cost if needed
                neighbors.add(tmpState);
            }
        }
        if (currX+1<split){
            if (grid[currY][currX+1] <= 0){
                CarState tmpState = new CarState(currentState,currX+1,currY);
                //add cost if needed
                neighbors.add(tmpState);
            }
        }
        if (currY-1>0){
            if (grid[currY-1][currX] <= 0){
                CarState tmpState = new CarState(currentState,currX,currY-1);
                //add cost if needed
                neighbors.add(tmpState);
            }
        }
        if (currY+1<split){
            if (grid[currY+1][currX] <= 0){
                CarState tmpState = new CarState(currentState,currX,currY+1);
                //add cost if needed
                neighbors.add(tmpState);
            }
        }
        return neighbors;
    }
    CarState removeBest ()
    {
        double least_cost = 10000000;
        CarState tmp_state = null;
        int directCost = 0;
        for (CarState s:frontier){

            if (costMatrix[s.y][s.x] + calDirectionCost(s) <= least_cost){
                least_cost = costMatrix[s.y][s.x];
                tmp_state = s;

            }
        }
        //System.out.println(tmp_state.estimatedCostToGoal);
        frontier.remove(tmp_state);
        return tmp_state;
        // INSERT YOUR CODE HERE
        // Pick the state s with the least s.costFromStart
    }

    public int calDirectionCost(CarState currState){
        if (currState.parent == null){
            return 0;
        }
        if (currState.parent.parent == null){
            return 0;
        }
        if ((currState.x - currState.parent.x) == (currState.parent.x - currState.parent.parent.x)&& (currState.x != currState.parent.x)){

            return 0;
        }
        if ((currState.y - currState.parent.y) == (currState.parent.y - currState.parent.parent.y) && (currState.y != currState.parent.y)){
            return 0;
        }
        //not in the same direction
        return 2;

    }

    public LinkedList<CarState> makeSolution (CarState goalState)
    {
        LinkedList<CarState> solution = new LinkedList<CarState> ();
        solution.add (goalState);

        // Start from the goal and work backwards, following
        // parent pointers.
        CarState currentState = goalState;
        while (currentState.getParent() != null) {
            solution.addFirst (currentState.getParent());
            currentState = currentState.getParent();
        }

        System.out.println ("Cost: Solution of length=" + solution.size() + " found with cost=" + goalState.combinedCost + " after " + numMoves + " moves");

        return solution;
    }

}
