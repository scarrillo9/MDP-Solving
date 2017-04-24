/* Hector Cervantes and Stefany Carrillo
 * Artificial Intelligence: Markov Decision Processes
 * Monte Carlo, Value Iteration, Q Learning
 */
import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;

public class main {
	static Random r = new Random();
	static HashMap<String, Integer> iterationCount = new HashMap<String, Integer>();
	static HashMap<String, Double> currentValue = new HashMap<String, Double>();
	static DecimalFormat df = new DecimalFormat("#.##");
	static State end = new State();
	static State TU10a = new State("T", "U", "10AM", null, 0, null, 0, null, 0, end, -1, 1);
	static State RU10a = new State("R", "U", "10AM", null, 0, null, 0, null, 0, end, 0, 1);
	static State RD10a = new State("R", "D", "10AM", null, 0, null, 0, null, 0, end, 4, 1);
	static State TD10a = new State("T", "D", "10AM", null, 0, null, 0, null, 0, end, 3, 1);
	static State RU8a = new State("R", "U", "8AM", TU10a, 2, RU10a, 0, RD10a, -1, null, 0, 3);
	static State RD8a = new State("R", "D", "8AM", TD10a, 2, RD10a, 0, null, 0, null, 0, 2);
	static State TU10p = new State("T", "U", "10PM", RU10a, 2, RU8a, 0, null, 0, null, 0, 2);
	static State RU10p = new State("R", "U", "10PM", RU8a, 2, RU8a, 0, RD8a, -1, null, 0, .5, RU10a, 2, 3);
	static State RD10p = new State("R", "D", "10PM", RU8a, 2, RD8a, 0, null, 0, null, 0, .5, RD10a, 2, 2);
	static State RU8p = new State("R", "U", "8PM", TU10p, 2, RU10p, 0, RD10p, -1, null, 0, 3);
	static State start = RU8p;
	
	public static void main(String[] args){
		State rstart = initStates();
		Scanner input = new Scanner(System.in);
		System.out.println("Choose Markov Decision Process:\nMonte Carlo? (m)"
				+ "\nValue Iteration? (i)\nQ Learning? (q)");
		String option = input.next();
		
		if(option.equals("m"))
			monteCarlo(start);
		if(option.equals("i"))
			valueIterationInit(rstart);
		if(option.equals("q"))	
			qLearning(start);
	}//end main method
	
	public static State initStates(){ //party // rest // study
		State end = new State();
		State TU10a = new State("tired", "undone", "10AM", null, 0, null, 0, null, 0, end, -1, 1);
		currentValue.put(TU10a.health + " " + TU10a.homework+ " " + TU10a.time, 1.0);
		iterationCount.put(TU10a.health + " " + TU10a.homework + " " + TU10a.time, 0);
		State RU10a = new State("rested", "undone", "10AM", null, 0, null, 0, null, 0, end, 0, 1);
		currentValue.put(RU10a.health + " " + RU10a.homework + " "+ RU10a.time, 0.0);
		iterationCount.put(RU10a.health + " "+ RU10a.homework + " " + RU10a.time, 0);
		State RD10a = new State("rested", "done", "10AM", null, 0, null, 0, null, 0, end, 4, 1);
		currentValue.put(RD10a.health + " " + RD10a.homework +" "+ RD10a.time, 0.0);
		iterationCount.put(RD10a.health +" "+ RD10a.homework +" "+ RD10a.time, 0);
		State TD10a = new State("tired", "done", "10AM", null, 0, null, 0, null, 0, end, 3, 1);
		currentValue.put(TD10a.health + " "+ TD10a.homework + " " + TD10a.time, 0.0);
		iterationCount.put(TD10a.health + " " + TD10a.homework + " " + TD10a.time, 0);
		State RU8a = new State("rested", "undone", "8AM", TU10a, 2, RU10a, 0, RD10a, -1, null, 0, 3);
		currentValue.put(RU8a.health + " "+ RU8a.homework +" "+ RU8a.time, 0.0);
		iterationCount.put(RU8a.health + " " + RU8a.homework +" "+ RU8a.time, 0);
		State RD8a = new State("rested", "done", "8AM", TD10a, 2, RD10a, 0, null, 0, null, 0, 2);
		currentValue.put(RD8a.health + " " + RD8a.homework + " " + RD8a.time, 0.0);
		iterationCount.put(RD8a.health + " " + RD8a.homework +" " + RD8a.time, 0);
		State TU10p = new State("tired", "undone", "10PM", RU10a, 2, RU8a, 0, null, 0, null, 0, 2);
		currentValue.put(TU10p.health + " " + TU10p.homework + " " + TU10p.time, 0.0);
		iterationCount.put(TU10p.health + " " + TU10p.homework +" "+ TU10p.time, 0);
		State RU10p = new State("rested", "undone", "10PM", RU8a, 2, RU8a, 0, RD8a, -1, null, 0, .5, RU10a, 2, 3);
		currentValue.put(RU10p.health + " " +RU10p.homework + " "+  RU10p.time, 0.0);
		iterationCount.put(RU10p.health +" "+ RU10p.homework +" "+ RU10p.time, 0);
		State RD10p = new State("rested", "done", "10PM", RU8a, 2, RD8a, 0, null, 0, null, 0, .5, RD10a, 2, 2);
		currentValue.put(RD10p.health +" "+ RD10p.homework + " "+ RD10p.time, 0.0);
		iterationCount.put(RD10p.health +" "+ RD10p.homework +" "+ RD10p.time, 0);
		State RU8p = new State("rested", "undone", "8PM", TU10p, 2, RU10p, 0, RD10p, -1, null, 0, 3);
		currentValue.put(RU8p.health + " " + RU8p.homework +" "+ RU8p.time, 0.0);
		iterationCount.put(RU8p.health + " " + RU8p.homework +" "+ RU8p.time, 0);
		
		return RU8p;
	}//end initStates
	
	public static void qLearning(State start){
		qArrays(); //setting up arrays for Q values
		double alpha = 0.1;
		double lambda = 0.99;
		boolean maxChange = false;
		int episodes = 0;
		State temp = start;
		System.out.println("Q Learning:\nState, Reward >> PreviousValue->CurrentValue");
		while(!maxChange){
			System.out.printf("Episode " + (episodes+1) + ": ");
			while(!temp.finalState){
				
				int nextState = chooseNextState(temp);
				int tempReward = getReward(temp, nextState);
				System.out.printf(temp.health + "/" + temp.homework + ", rw: " + tempReward + " >> " 
						+ df.format(temp.qValues[nextState-1]) + " -> ");
				
				double currValue = temp.qValues[nextState-1];
				
				//Q-learning: off policy
				double newValue = currValue + (alpha * (tempReward + (lambda * getState(temp, nextState).value) - temp.qValues[nextState-1]));
				temp.qValues[nextState-1] = newValue;
				System.out.printf(df.format(temp.qValues[nextState-1]) + " || ");
				
				if(((newValue - currValue) < 0.001) && ((newValue - currValue) > 0)){
					maxChange = true;
				}
				temp = getState(temp, nextState);
			}//end while
			System.out.println();
			alpha *= lambda;
			temp = start;
			episodes++;
			
		}//end while loop
		System.out.println("\nNumber of episodes: " + episodes);
		stateValuesQ();
	}//end qLearning
	
	private static void valueIterationInit(State start) {
		int i = 0;
		int iterations = 100;
	    while(i < iterations){
			valueIteration(start);
			i++;
		}
	    System.out.println();
	    for (String name: iterationCount.keySet()){

            String key = name.toString();
            String value = iterationCount.get(name).toString();  
            System.out.println("Number of iterations for " + key + " " + value);  
	    }
	    System.out.println();
	    for (String name: currentValue.keySet()){

            String key = name.toString();
            String value = currentValue.get(name).toString();  
            System.out.println("Current values for " + key + " " + value);  
	    }
		
	    System.out.println("");
	    System.out.println("Optimality");
	    printOptimal(start);
	    
	    
	}
	
	public static void monteCarlo(State start){
		double alpha = 0.1;
		State temp = start;
		int[] currReward = new int[50];
		double avgReward = 0;
		
		for(int i = 0; i < 50; i++){
			System.out.printf("Episode %d\n", i+1);
			
			while(!temp.finalState){
				System.out.printf("%s/%s Time: %s\n", temp.health, temp.homework, temp.time);
				
				int nextState = chooseNextState(temp);
				int tempReward = getReward(temp, nextState);
				
				//constant alpha-MC
				temp.value += (alpha * (tempReward - temp.value));
				
				currReward[i] += tempReward;
				temp = getState(temp, nextState);
			}//end while
			
			System.out.printf("Reward: %d\n\n", currReward[i]);
			avgReward += currReward[i];
			temp = start;
		}//end 50 episodes
		avgReward /= 50;
		stateValues();
		System.out.println("\n\nAverage reward for Monte Carlo: " + avgReward);
	}//end monteCarlo 
	
	private static void printOptimal(State start) {
		if(start.health == null){
			return;
		}
		String state = start.health+ " " + start.homework + " " + start.time;
		System.out.println(state + " value: " + currentValue.get(state));
		State optimalState = start;
		String maxNeighbor = " ";
		double max = 0.0;
		String neighbor;
		if(start.afterParty != null){
			neighbor = start.afterParty.health + " " + start.afterParty.homework + " " + start.afterParty.time;
			if(currentValue.get(neighbor) >= max){
				max = currentValue.get(neighbor);
				optimalState = start.afterParty;
				maxNeighbor = neighbor;
			}
		}
		if(start.afterRest != null){
			neighbor = start.afterRest.health + " " + start.afterRest.homework + " " + start.afterRest.time;
			if(currentValue.get(neighbor) >= max){
				max = currentValue.get(neighbor);
				optimalState = start.afterRest;
				maxNeighbor = neighbor;
			}
		}
		if(start.afterStudy != null){
			neighbor = start.afterStudy.health + " " + start.afterStudy.homework + " " + start.afterStudy.time;
			if(currentValue.get(neighbor) >= max){
				max = currentValue.get(neighbor);
				optimalState = start.afterStudy;
				maxNeighbor = neighbor;
			}
		}
		if(start.afterAny != null){
			neighbor = start.afterAny.health + " " + start.afterAny.homework + " " + start.afterAny.time;
			if(currentValue.get(state) >= max){
				max = currentValue.get(state);
				optimalState = start.afterAny;
				maxNeighbor = neighbor;
			}
		}
		
		printOptimal(optimalState);
		
	}
	
	private static boolean valueIteration(State start) {
		if(start.health == null){
			return true;
		}
		String state = start.health +" "+ start.homework +" "+ start.time;
		System.out.printf("%s/%s Time: %s\n", start.health, start.homework, start.time);
		System.out.println("Current Value: " + currentValue.get(state));
		State neighbor = start;
		int action = r.nextInt(start.actions - 1 + 1) + 1;
		double value = 0.0;
		
		if(start.afterAny != null){
			neighbor = start.afterAny;
			value = (1.0/3.0) * (start.anyReward + .99 * 1.0);
		}
		else{
			if(action == 1 && start.transitionState){
				int tState = r.nextInt(2 - 1 + 1) + 1;
				if(tState == 1){
					neighbor = start.afterParty;
					String neighborName = neighbor.health +" " + neighbor.homework + " " + neighbor.time;
					value = .5 * (getReward(start, 1) + .99 * currentValue.get(neighborName));
				}

				else{
					neighbor = start.afterParty2;
					String neighborName = neighbor.health + " " + neighbor.homework + " "+ neighbor.time;
					value = .5 * (getReward(start, 1) + .99 * currentValue.get(neighborName));
				}
			}
			else{
				neighbor = getState(start, action);
				String neighborName = neighbor.health + " "+ neighbor.homework + " " + neighbor.time;
				value = (1/ (double) start.actions) * (getReward(start, 1) + .99 * currentValue.get(neighborName));
			}
		}
		System.out.println("Next Value: " + value);
		System.out.println("Action Taken: " + getAction(action));
		// count the number of times state changes
		iterationCount.put(state, iterationCount.get(state) + 1);
		// update values
		currentValue.put(state, value);
		
		return valueIteration(neighbor);
	}
	
	private static String getAction(int actionTaken) {
		switch(actionTaken){
		case 1: return "Party";
		case 2: return "Rest";
		case 3: return "Study";
		case 5: return "Party";
		default: return "Any";
		}
	}
	public static void stateValues(){
		System.out.printf("Values of States:\n");
		System.out.printf("State: RU8p, Value: " + RU8p.value);
		System.out.printf("\nState: TU10p, Value: " + TU10p.value);
		System.out.printf("\nState: RU10p, Value: " + RU10p.value);
		System.out.printf("\nState: RD10p, Value: " + RD10p.value);
		System.out.printf("\nState: RU8a, Value: " + RU8a.value);
		System.out.printf("\nState: RD8a, Value: " + RD8a.value);
		System.out.printf("\nState: TU10a, Value: " + TU10a.value);
		System.out.printf("\nState: RU10a, Value: " + RU10a.value);
		System.out.printf("\nState: RD10a, Value: " + RD10a.value);
		System.out.printf("\nState: TD10a, Value: " + TD10a.value);
	}//end stateValues
	
	public static void stateValuesQ(){
		System.out.printf("Values of States:\n");
		System.out.printf("State: RU8p, Values: "); printValuesQ(RU8p);
		System.out.printf("\nState: TU10p, Value: "); printValuesQ(TU10p);
		System.out.printf("\nState: RU10p, Value: "); printValuesQ(RU10p);
		System.out.printf("\nState: RD10p, Value: "); printValuesQ(RD10p);
		System.out.printf("\nState: RU8a, Value: "); printValuesQ(RU8a);
		System.out.printf("\nState: RD8a, Value: "); printValuesQ(RD8a);
		System.out.printf("\nState: TU10a, Value: "); printValuesQ(TU10p);
		System.out.printf("\nState: RU10a, Value: "); printValuesQ(RU10a);
		System.out.printf("\nState: RD10a, Value: "); printValuesQ(RD10a);
		System.out.printf("\nState: TD10a, Value: "); printValuesQ(TD10a);
	}//end stateValues
	
	public static void printValuesQ(State curr){
				  //party/rest/study/any/trans//
		if(curr.actions == 1)
			System.out.printf("/any: " + df.format(curr.qValues[3]));
		if(curr.actions == 2){
			if(curr.transitionState){
				System.out.printf("/party: " + df.format(curr.qValues[0]) + " /rest: " + df.format(curr.qValues[1])
						+ " /party2: " + df.format(curr.qValues[4]));
			}//if transition
			else
				System.out.printf("/party: " + df.format(curr.qValues[0]) + " /rest: " + df.format(curr.qValues[1]));
			}//end if 2 actions
		if(curr.actions == 3){
			if(curr.transitionState){
				System.out.printf("/party: " + df.format(curr.qValues[0]) + " /rest: " + df.format(curr.qValues[1])
					+ " /study: " + df.format(curr.qValues[2]) + " /party2: " + df.format(curr.qValues[4]));
			}//if transition
			else
				System.out.printf("/party: " + df.format(curr.qValues[0]) + " /rest: " + df.format(curr.qValues[1])
					+ " /study: " + df.format(curr.qValues[2]));
			}//end if 3 actions
	}//end stateValueQ
	
	public static int getReward(State curr, int state){
		if(state == 1)
			return curr.partyReward;
		if(state == 2)
			return curr.restReward;
		if(state == 3)
			return curr.studyReward;
		if(state == 4)
			return curr.anyReward;
		if(state == 5)
			return curr.party2Reward;
		return -1;
	}//end getReward
	
	public static State getState(State curr, int state){
		if(state == 1)
			return curr.afterParty;
		if(state == 2)
			return curr.afterRest;
		if(state == 3)
			return curr.afterStudy;
		if(state == 4)
			return curr.afterAny;
		if(state == 5)
			return curr.afterParty2;
		return null;
	}//end getReward
	
	public static void qArrays(){
		RU8p.qValues = qSetUp(RU8p);
		TU10p.qValues = qSetUp(TU10p);
		RU10p.qValues = qSetUp(RU10p);
		RD10p.qValues = qSetUp(RD10p);
		RU8a.qValues = qSetUp(RU8a);
		RD8a.qValues = qSetUp(RD8a);
		TU10a.qValues = qSetUp(TU10a);
		RU10a.qValues = qSetUp(RU10a);
		RD10a.qValues = qSetUp(RD10a);
		TD10a.qValues = qSetUp(TD10a);
	}
	
	public static double[] qSetUp(State curr){
	        		  //party/rest/study/any/trans//
		double[] qValues = {-1, -1, -1, -1, -1};
		if(curr.actions == 1)
			qValues[3] = 0;
		if(curr.actions == 2){
			if(curr.transitionState){
				qValues[0] = qValues[1] = qValues[4] = 0;
			}//if transition
			else
				qValues[0] = qValues[1] = 0;
		}//end if 2 actions
		if(curr.actions == 3){
			if(curr.transitionState){
				qValues[0] = qValues[1] = qValues[2] = qValues[4] = 0;
			}//if transition
			else
				qValues[0] = qValues[1] = qValues[2] = 0;
		}//end if 3 actions
		
		return qValues;
}//end qSetUp

//	 * 1 = party
//	 * 2 = rest
//	 * 3 = study 
//	 * 4 = any
//	 * 5 = transitive party state
	public static int chooseNextState(State curr){
		if(curr.actions == 1){
			return 4;
		}//end if available actions is 1
		if(curr.actions == 2){
			int ran = ThreadLocalRandom.current().nextInt(1, 3);
			if(ran == 1){
				if(curr.transitionState){
					int ran2 = ThreadLocalRandom.current().nextInt(1, 3);
					if(ran2 == 1)
						return 1;
					if(ran2 == 2)
						return 5;
				}//end if party transition
				else	
					return 1;
			}//end party
			if(ran == 2) return 2;
		}//end if available actions are 2
		if(curr.actions == 3){
			int ran = ThreadLocalRandom.current().nextInt(1, 4);
			if(ran == 1){
				if(curr.transitionState){
					int ran2 = ThreadLocalRandom.current().nextInt(1, 3);
					if(ran2 == 1)
						return 1;
					if(ran2 == 2)
						return 5;
				}//end if party transition
				else
					return 1;
			}//end party
			if(ran == 2) return 2;
			if(ran == 3) return 3;
		}//end if available actions are 3
		return -1;
	}//end chooseNextState
	
}//end main class
