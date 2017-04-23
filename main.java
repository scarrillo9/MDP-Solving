import java.util.concurrent.ThreadLocalRandom;

public class main {
	public static void main(String[] args){
		State start = initStates();
		monteCarlo(start);
	}//end main method
	
	public static void monteCarlo(State start){
		double alpha = 0.1;
		State temp = start;
		int currReward = 0;
		
		//for(int i = 0; i < 50; i++){
			//System.out.printf("Episode %d\n", i+1);
			while(!temp.finalState){
				System.out.printf("Current state: %s/%s Time: %d\n", temp.health, temp.homework, temp.time);
				//State temp = rstart;
				
				int nextState = chooseNextState(temp);
				int tempReward = getReward(temp, nextState);
				temp.value += (alpha * (tempReward - temp.value));
				currReward += tempReward;
				temp = getState(temp, nextState);
				//System.out.printf("\nCurrent state: %s/%s Time: %d %s", temp.health, temp.homework, temp.time, temp.finalState);
			}//end while
			System.out.println();
			temp = start;
		//}//end 50 episodes
		

	}//end monteCarlo 
	
	
	public static State initStates(){ //party // rest // study
		State end = new State();
		State TU10a = new State("tired", "undone", 10, null, 0, null, 0, null, 0, end, -1, 1);
		State RU10a = new State("rested", "undone", 10, null, 0, null, 0, null, 0, end, 0, 1);
		State RD10a = new State("rested", "done", 10, null, 0, null, 0, null, 0, end, 4, 1);
		State TD10a = new State("tired", "done", 10, null, 0, null, 0, null, 0, end, 3, 1);
		State RU8a = new State("rested", "undone", 8, TU10a, 2, RU10a, 0, RD10a, -1, null, 0, 3);
		State RD8a = new State("rested", "done", 8, TD10a, 2, RD10a, 0, null, 0, null, 0, 2);
		State TU10p = new State("tired", "undone", 22, RU10a, 2, RU8a, 0, null, 0, null, 0, 2);
		State RU10p = new tState("rested", "undone", 22, RU8a, 2, RU8a, 0, RD8a, -1, null, 0, .5, RU10a, 2, 3);
		State RD10p = new tState("rested", "done", 22, RU8a, 2, RD8a, 0, null, 0, null, 0, .5, RD10a, 2, 2);
		State RU8p = new State("rested", "undone", 20, TU10p, 2, RU10p, 0, RD10p, -1, null, 0, 3);
		return RU8p;
	}//end initStates
	
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
	
	/*
	 * 1 = party
	 * 2 = rest
	 * 3 = study 
	 * 4 = any
	 */
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
