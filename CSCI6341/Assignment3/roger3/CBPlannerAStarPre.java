// Template for implementing Cost-Based Planner.

import java.util.ArrayList;
import java.util.LinkedList;


public class CBPlannerAStarPre implements Planner {

	// Limit the total number of expansions.
	static int maxMoves = 100000;

	// The frontier = all those states that have been generated but not
	// yet explored (visited).
	LinkedList<State> frontier;

	// The list of all states that have been visited.
	LinkedList<State> visitedStates;

	// Count # moves.
	int numMoves;
	int k = 5;
	double finalX = 0;
	double finalY = 0;
	double obstX = -1;
	double obstY = -1;
	double numNode = -1;
	double[] tmpX = {50,100,150,150,150};
	double[] tmpY = {150,150,150,100,50};
	LinkedList<State>[] calculated_plans = new LinkedList[k];

	// random 5 points in area
	// cal astar for each point
	// cal distance to target
	// state.tip_distance
	public LinkedList<State> makePlan (PlanningProblem problem, State start)
	{
		ArmProblem problemsub = (ArmProblem)problem;
		finalX = problemsub.targetX;
		finalY = problemsub.targetY;
		if (obstX == -1){
			obstX = problemsub.obstacleX;
		}
		if (obstY == -1){
			obstY = problemsub.obstacleY;
		}
		if (numNode == -1){
			numNode = problemsub.numNodes;
		}
		// if problem condition changed, recalculate the pre-defined path.
		if (this.calculated_plans[0] == null||obstY != problemsub.obstacleY||obstX != problemsub.obstacleX|| numNode!= problemsub.numNodes){
			//init the plans
			for (int i = 0;i < k;i++){
				problemsub.targetX = tmpX[i];
				problemsub.targetY = tmpY[i];
				this.calculated_plans[i] = makeSubPlan(problemsub,start);
			}
			obstX = problemsub.obstacleX;
			obstY = problemsub.obstacleY;
			numNode = problemsub.numNodes;
		}
		double min_distance = 100000;
		int min_index = -1;
		// find the nearest pre-defined condition
		for(int i = 0;i < k; i++){
			if (calculated_plans[i]==null){
				continue;
			}
			ArmState tmpState = (ArmState) calculated_plans[i].getLast();
			double tmp_distance = tmpState.tipDistance(finalX,finalY);
			if(tmp_distance<min_distance){
				min_index = i;
				min_distance = tmp_distance;
			}
		}
		// generating the path from pre-defined condition
		ArmState currState = (ArmState) calculated_plans[min_index].getLast();
		System.out.println("choosing plan "+min_index+" with X:"+tmpX[min_index]+" with Y:"+tmpY[min_index]);
		System.out.println("choosing plan "+min_index+" with X:"+currState.getX(problemsub.numNodes-1)+" with Y:"+currState.getY(problemsub.numNodes-1));
		problemsub.targetX = finalX;
		problemsub.targetY = finalY;
		LinkedList<State> interPlan = makeSubPlan(problemsub,currState);
//		LinkedList<State> initPlan = (LinkedList<State>) calculated_plans[min_index].clone();
//		initPlan.addAll(interPlan);
		System.out.println ("Cost: Final Solution of length=" + interPlan.size() + " found with cost=" + interPlan.getLast().costFromStart + " after " + numMoves + " moves");
		return interPlan;
	}

	// previously makePlan
    public LinkedList<State> makeSubPlan (PlanningProblem problem, State start)
    {
        // Initialize.
	frontier = new LinkedList<State> ();
	visitedStates = new LinkedList<State> ();
	numMoves = 0;

        // The start node is the first one to place in frontier.
	frontier.add (start);

	while (numMoves < maxMoves) {

            // If nothing to explore, we're done.
	    if (frontier.size() == 0) {
		break;
	    }

	    // Get first node in frontier and expand.
	    State currentState = removeBest ();
            // problem.drawState (currentState);

            // If we're at a goal node, build the solution.
	    if (problem.satisfiesGoal (currentState)) {
		return makeSolution (currentState);
	    }

	    numMoves ++;

	    // Put in visited list.
	    visitedStates.add (currentState);

	    // Expand current state (look at its neighbors) and place in frontier.
	    ArrayList<State> neighbors = problem.getNeighbors (currentState);
	    for (State s: neighbors) {
		if ( ! visitedStates.contains (s) ) {
                    int index = frontier.indexOf (s);
                    if (index >= 0) {
                        State altS = frontier.get (index);
                        if (s.costFromStart < altS.costFromStart) {
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


    State removeBest ()
    {
    	double least_cost = 10000000;
    	State tmp_state = null;
    	for (State s:frontier){
    		if (s.costFromStart + s.estimatedCostToGoal < least_cost){
    			least_cost = s.costFromStart + s.estimatedCostToGoal;
    			tmp_state = s;

    		}
    	}
        //System.out.println(tmp_state.estimatedCostToGoal);
    	frontier.remove(tmp_state);
    	return tmp_state;
        // INSERT YOUR CODE HERE
	// Pick the state s with the least s.costFromStart
    }
    


    public LinkedList<State> makeSolution (State goalState)
    {
	LinkedList<State> solution = new LinkedList<State> ();
	solution.add (goalState);

        // Start from the goal and work backwards, following
        // parent pointers.
	State currentState = goalState;
	while (currentState.getParent() != null) {
	    solution.addFirst (currentState.getParent());
	    currentState = currentState.getParent();
	}

	System.out.println ("Cost: Solution of length=" + solution.size() + " found with cost=" + goalState.costFromStart + " after " + numMoves + " moves");

	return solution;
    }    

}
