
public class State {
	String health;
	String homework;
	int time;
	State afterParty;
	int partyReward;
	State afterRest;
	int restReward;
	State afterStudy;
	int studyReward;
	State afterAny;
	int anyReward;
	
	public State(){
		
	}//end empty constructor
	
	public State(String health, String homework, int time, State party, 
			int partyReward, State rest, int restReward, State study, 
			int studyReward, State any, int anyReward){
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
	}//end constructor
	
}
