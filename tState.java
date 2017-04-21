
public class tState extends State{
	double transition;
	static State party2;
	int partyReward2;
	
	public tState(String health, String homework, int time, State party, 
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
		this.transition = transition;
		this.party2 = party2;
		this.partyReward2 = partyReward2;
		this.actions = actions;
	}//end constructor
	
	public static State chooseNextState(){
		if(actions == 1){
			return afterAny;
		}//end if available actions is 1
		if(actions == 2){
			int ran = (int)(Math.random() *2);
			if(ran == 1){
				int ran2 = (int)(Math.random() *2);
				if(ran2 == 1)
					return afterParty;
				if(ran2 == 2)
					return party2;
			}//end if party transition
			if(ran == 2) return afterRest;
		}//end if available actions are 2
		if(actions == 3){
			int ran = (int)(Math.random() *3);
			if(afterStudy != null){
				if(ran == 1){
					int ran2 = (int)(Math.random() *2);
					if(ran2 == 1)
						return afterParty;
					if(ran2 == 2)
						return party2;
				}//end if party transition
				if(ran == 2) return afterRest;
				if(ran == 2) return afterStudy;
			}//end if study is not null
		}//end if available actions are 3
		return null;
	}//end chooseNextState
}
