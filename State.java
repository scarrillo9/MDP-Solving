
public class State {
	double value;
	String health;
	String homework;
	int time;
	static State afterParty;
	int partyReward;
	static State afterRest;
	int restReward;
	static State afterStudy;
	int studyReward;
	static State afterAny;
	int anyReward;
	static int actions;
	
	public State(){
		
	}//end empty constructor
	
	public State(String health, String homework, int time, State party, 
			int partyReward, State rest, int restReward, State study, 
			int studyReward, State any, int anyReward, int actions){
		value = 0;
		this.health = health;
		this.homework = homework;
		this.time = time;
		afterParty = party;
		this.partyReward = partyReward;
		afterRest = rest;
		this.restReward = restReward;
		afterStudy = study;
		this.studyReward = studyReward;
		afterAny = any;
		this.anyReward = anyReward;
		State.actions = actions;
	}//end constructor
	
	public static State chooseNextState(){
		if(actions == 1){
			return afterAny;
		}//end if available actions is 1
		if(actions == 2){
			int ran = (int)(Math.random() *2);
			if(ran == 1) return afterParty;
			if(ran == 2) return afterRest;
		}//end if available actions are 2
		if(actions == 3){
			int ran = (int)(Math.random() *3);
			if(ran == 1) return afterParty;
			if(ran == 2) return afterRest;
			if(ran == 2) return afterStudy;
		}//end if available actions are 3
		return null;
	}//end chooseNextState
	
}
