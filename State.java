import java.util.concurrent.ThreadLocalRandom;

public class State {
	double value;
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
	State afterParty2;
	int party2Reward;
	int actions;
	boolean finalState;
	boolean transitionState;
	double tProbability;
	
	public State(){
		finalState = true;
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
		this.actions = actions;
		finalState = false;
		transitionState = false;
	}//end constructor
	
	public State(String health, String homework, int time, State party, 
			int partyReward, State rest, int restReward, State study, 
			int studyReward, State any, int anyReward, double transition,
			State party2, int partyReward2, int actions){
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
		this.tProbability = transition;
		this.afterParty2 = party2;
		this.party2Reward = partyReward2;
		this.actions = actions;
		finalState = false;
		transitionState = true;
	}//end constructor
	
	
	
}
