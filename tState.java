
public class tState extends State{
	double transition;
	State party2;
	int partyReward2;
	
	public tState(String health, String homework, int time, State party, 
			int partyReward, State rest, int restReward, State study, 
			int studyReward, State any, int anyReward, double transition,
			State party2, int partyReward2){
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
		this.transition = transition;
		this.party2 = party2;
		this.partyReward2 = partyReward2;
	}//end constructor
}
