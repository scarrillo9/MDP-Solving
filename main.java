import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

public class main {
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
		Scanner input = new Scanner(System.in);
		System.out.println("Choose Markov Decision Process:\nMonte Carlo? (m)"
				+ "\nValue Iteration? (i)\nQ Learning? (q)");
		String option = input.next();
		
		if(option.equals("m"))
			monteCarlo(start);
		//if(option.equals("i"))
			
		if(option.equals("q"))	
			qLearning(start);
	}//end main method
	
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
	}//end qLearning
	
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
