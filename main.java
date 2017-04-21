
public class main {
	public static void main(String[] args){
		State start = initStates();
	}//end main method
	
	public static void monteCarlo(State start){
		double alpha = 0.1;
		State rstart = start;
		
		for(int i = 0; i < 50; i++){
			rstart.value += (alpha * (0 - rstart.value));
			rstart = State.chooseNextState();
			
		}//end 50 episodes
		

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
		tState RU10p = new tState("rested", "undone", 22, RU8a, 2, RU8a, 0, RD8a, -1, null, 0, .5, RU10a, 2, 3);
		tState RD10p = new tState("rested", "done", 22, RU8a, 2, RD8a, 0, null, 0, null, 0, .5, RD10a, 2, 2);
		State RU8p = new State("rested", "undone", 22, TU10p, 2, RU10p, 0, RD10p, -1, null, 0, 3);
		return RU8p;
	}//end initStates
	
}//end main class
