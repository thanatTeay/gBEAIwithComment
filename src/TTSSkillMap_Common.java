
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import dataloader.BalFitnessDataLoader;

public class  TTSSkillMap_Common {
	static Map<String , String > skillMap;
	//  extracted Commentary
	
	gBEAI beai;
	
	static String  myName = "Zen";
	static String  oppName = "Lud";
	
	static void setCharacter(String myName0, String oppName0) {
		myName = myName0;
		oppName = oppName0;
	}
	
	static String beginCommentary[] = {
			"Hi everybody, welcome to the match!",
			"Alright, welcome everybody!",
			"Welcome! Let's see a match between " + oppName + " and " + myName + "",
			"Welcome to the match",
			"Welcome to " + oppName + " versus " + myName + ""

	};
	
	static String beginCommentaryCn[] = {
			
		};
	
	static String endCommentary[] = {
			"It is a nice match",
			"It is a wonderful match",
			"See you again in the next match",
			"Thank you for watching"
		
	};
	
	static String chat[] = {
			"They are trying to attack each other",
			"It would be a match of the century!",
			"I have never seen it before",
			"So attention to these two players",
			"They try to play slowly in this match",
			"Let's keep attention to these players",
			"They are trying to predict each other",
			"Haven't really seen this kind of challenging game so far"

	};
	
	static String forwardActionInstruction[] = {
		oppName + " may",
		oppName + " should",
		oppName + " can"
	};
	
	static String actionInstruction[] = {
			"step back your foot for guarding",
			"move forward to get close to the opponent",
			"lean backward to move backward",
			"lean forward for dashing",
			"get away from your opponent",
			"step back and jump to jump backward",
			"use left punch to execute lights punch",
			"use right punch to execute heavy punch",
			"crouch to make the character crouch",
			"step back and crouch to guard while crouching",
			"crouch and left punch to execute weak punch while crouching",
			"crouch and right punch to execute weak uppercut while crouching",
			"use left Kick for executing light kick while crouching",
			"use right kick to execute heavy kick while crouching",
			"use two-handed punch while the opponent is close to throw the opponent",
			"step back and two-handed punch while the opponent is close to heavily throw the opponent"
			//"right kick to execute heavy kick while crouching.",
			//"This skill can take the opponent down",
			//"Two-handed punch while the opponent is close to throw the opponent",
			//"Step back the right foot and two-handed punch while the opponent is close to heavily throw the opponent",
			//"Right swing from back to front to execute sliding attack. This skill can take the opponent down",
			//"This skill can take the opponent down",
			//"Right-handed knifehand strike (karate chop) to execute forward flying attack",
			//"Do Hadouken on your right side to execute projectile attack"

			
			
			
			
//			23	Right Step Back for guarding	Step back your right foot for guarding
//			22	He Right Step Forward for walking forward	Step forward your right foot for walking forward
//			21	Lean Backward Left punch to execute weak punchfor moving backward	Lean backward to move backward
//			20	Lean Forward for dashing	Lean forward for dashing
//			19	Jump	Jump to make the character jump
//			18	Right Step Back and Jump for jumping backward	Step back the right foot and jump to jump backward
//			17	Right Step Front and Jump for jumping forward	Step front the right foot and jump to jump forward
//			16	Left Punch for weak punching	
//			15	Right Punch for heavy punching	Right punch to execute heavy punch
//			14	Left Knee Strike for weak kicking	Raise up the left knee to execute weak kick
//			13	Right Knee Strike for heavy kicking	Raise up the right knee to execute heavy kick
//			12	Crouch	Crouch to make the character crouch
//			11	Right Step Back and Crouch for guarding while crouching	Step back the right foot and crouch to guard while crouching
//			10	Crouch and Left Punch for punching while crouching	Crouch and left punch to execute weak punch while crouching
//			9	Crouch and Right Punch for throwing uppercut while crouching	Crouch and right punch to execute weak uppercut while crouching
//			8	Left Kick for executing weak kick while crouching	Left kick to execute weak kick while crouching
//			7	Right Kick for executing heavy kick while crouching	Right kick to execute heavy kick while crouching. This skill can take the opponent down.
//			6	Two-handed Punch for throwing the opponent.	Two-handed punch while the opponent is close to throw the opponent.
//			5	Right Step Back and Two-handed Punch for throwing the opponent heavily.	Step back the right foot and two-handed punch while the opponent is close to heavily throw the opponent
//			4	Right Swing for executing sliding attack.	Right swing from back to front to execute sliding attack. This skill can take the opponent down.
//			3	Left Uppercut for throwing uppercut	Left uppercut to execute heavy uppercut. This skill can take the opponent down.
//			2	Right Knifehand Strike for executing forward flying attack.	Right-handed knifehand strike (karate chop) to execute forward flying attack.
//			1	Hadouken for executing projectile attack.	Do Hadouken on your right side to execute projectile attack.
	};

// only describe player 2 now, " + oppName + " vs Garnet
	static String actionForwardPositiveCommentary[] = {
			oppName + " used",
			oppName + " used",
			"For an opportunity, " + oppName + " used",
			oppName + " continues to use",
			"Nice time to use",
			"That's common to use",
			"That's very nice to use",
			oppName + " knew when to use",
			oppName + " is pressing his opponent by using",
			"Hit " + myName + " by",
			"For a chance, " + oppName + " used",
			"How skillfully, " + oppName + " used",
			oppName + " released a powerful"
			

			};
	
	static String actionForwardNegativeCommentary[] = {
	};
				
	static String actionBackwardPositiveCommentary[] = {
			". Excellent for that",
			". That is a good move!",
			". Wow, that happened really quick",
			", really a good one",
			"that " + myName + " should be very careful",
			". That'll be a great deal",
			". Some damage here",
			"that should tell " + myName + " to quit this game",
			". It's a good deal of damage",
			". That's a nice shot",
			". That's perfect",
			". " + myName + " gotta be punished!",
			". " + myName + " will lose for sure!",
			". Nice!"
			
			};
	
	static String actionBackwardNegativeCommentary[] = {
	};
	
	// health PDA 
	
	static String healthForwardQuestion[] = {
			"There's a button there,",
			"It's good to try to employ",
			"It seems possible to"
		
	};
	
	static String healthBackwardQuestion[] = {
			", that " + myName + " can rely on being pretty safe to hit",
			"to get " + myName + " punished",
			", and knock " + myName + " down"

	};
	
	static String healthPositiveCommentary[] = {
			"Yeah, " + oppName + " successfully punished " + myName + "!",
			"Oh, I can't believe it! Such a pretty move!",
			"Wow, Nice!",
			"Hoo, nice timing!",
			"That's fantastic!",
			"Oh, that worked!",
			"Excellent!",
			"Wonderful!",
			"Good game",
			"Nice shot",
			"Nice move",
			"Good",
			"That shot!!",
			"Those moves are nice",
			oppName + " moves're great!!"

		
	};
	
	static String healthNegativeCommentary[] = {
			oppName + " is at a dangerous point",
			oppName + " likes being in that range",	
			"Hoo, what a bad timing",
			oppName + " could have been knocked down",
			"What is " + oppName + " doing!!",
			"That's a bad move",
			"Oh, no. Not that",
			"That's not a good idea"

	};
	
	static String cheerUpCommentaryWin[] = {
			"Go Go Go, " + oppName + ", Go Go!!!",
			"We want more! We want more!",	
			"Go go go",
			oppName + " gonna win",
			"Attack! Attack!"

	};
	static String cheerUpCommentaryLose[] = {
			"Don't give up " + oppName + ". Try again",
			"Don't give up! Come back " + oppName + "",	
			"Fight " + oppName + " Fight!!",
			"You have not been defeated yet, do not give up, just fight it",
			"C'mon, defense"

	};
	static String cheerUpCommentarySame[] = {
			"They are the stars!",
			"We want more! We want more!",	
			"Attack! Attack!"

	};
	
	
	
	
	public TTSSkillMap_Common() {
		skillMap = new HashMap<String , String >();
		
		skillMap.put("STAND_D_DB_BA", "Flying crop");
//		skillMap.put("BACK_STEP", "Back step");
//		skillMap.put("FORWARD_WALK", "Step forward");
//		skillMap.put("DASH", "Lean forward");
		skillMap.put("STAND_GUARD", "Guard");
		skillMap.put("CROUCH_GUARD", "Guard");
		skillMap.put("THROW_A", "Throw");
		skillMap.put("THROW_B", "Great Throw");
		skillMap.put("STAND_A", "Punch");
		skillMap.put("STAND_B", "Kick");
		skillMap.put("CROUCH_A", "Low Punch");
		skillMap.put("CROUCH_B", "Low Kick");
		skillMap.put("STAND_FA", "Heavy Punch");
		skillMap.put("STAND_FB", "Heavy Kick");
		skillMap.put("CROUCH_FA", "Low Heavy Punch");
		skillMap.put("CROUCH_FB", "Low Heavy Kick");
		skillMap.put("STAND_D_DF_FA", "Hadouken");
		skillMap.put("STAND_D_DF_FB", "Super Hadouken");	
		skillMap.put("STAND_F_D_DFA", "Uppercut");
		skillMap.put("STAND_F_D_DFB", "Super Uppercut");
		skillMap.put("STAND_D_DB_BB", "Slide Kick");
		skillMap.put("STAND_D_DF_FC", "Ultimate Hadouken");	
	}
	
	/**
	 * transfer action code into real action name in natural language
	 */
	public static String  getActionRealName(String  skillCode) {
		return skillMap.getOrDefault(skillCode, "Default");
	}
		
	/**
	 * natural language processing using real action name, just prototype for it
	 * TODO
	 * @return complete Commentary
	 */
	
	public static String  generateNormalCommentary(String  actionRealName, gBEAI beai, boolean isP1) {
		if (actionRealName == "Default") {
			if(Math.abs(beai.deltaHp) > 60 && beai.p1_gotDamaged > 0) {	
				return generateHealthCommentary(beai.myCurrentMove, isP1);
			}
			else if(Math.abs(beai.deltaHp) > 60 && beai.p2_gotDamaged > 0) {	
				return generateHealthCommentary(beai.myCurrentMove, isP1);
			}
			else {
				//diff less than 60 OR noone did damage
				return chat[getRandomNumber(chat.length)];	
			}	
		} 
		else {
			if (Math.abs(beai.deltaHp) > 60)
			{
				return healthForwardQuestion[getRandomNumber(healthForwardQuestion.length)] + " " + actionRealName + healthBackwardQuestion[getRandomNumber(healthBackwardQuestion.length)] + ".";
			}
			else
			{
				return actionForwardPositiveCommentary[getRandomNumber(actionForwardPositiveCommentary.length)] + " " + actionRealName + actionBackwardPositiveCommentary[getRandomNumber(actionBackwardPositiveCommentary.length)] + ".";			
			}
		}

	}
	
	public static String  generateHealthQuestion(String  recommendedActionRealName) {
		return healthForwardQuestion[getRandomNumber(healthForwardQuestion.length)] + " " + recommendedActionRealName + healthBackwardQuestion[getRandomNumber(healthBackwardQuestion.length)];
	}
	//TODO
	public static String  generateActionInstruction(String  recommendedActionRealName) {
		return forwardActionInstruction[getRandomNumber(forwardActionInstruction.length)] + " " +actionInstruction[getRandomNumber(actionInstruction.length)];
	}
	
	public static String  generateHealthCommentary(String  actionRealName, boolean judgement) {
		if (actionRealName == "Default") {
			if (judgement) {	
				return healthPositiveCommentary[getRandomNumber(healthPositiveCommentary.length)];			
			} else {
				return healthNegativeCommentary[getRandomNumber(healthNegativeCommentary.length)];				
			}
		} else {
			return actionForwardPositiveCommentary[getRandomNumber(actionForwardPositiveCommentary.length)] + " " + actionRealName + actionBackwardPositiveCommentary[getRandomNumber(actionBackwardPositiveCommentary.length)] + ".";
		}
		
	}
	
	public static String  generateBeginCommentary(){
		return beginCommentary[getRandomNumber(beginCommentary.length)];
//		return beginCommentaryCn[getRandomNumber(beginCommentary.length)];
	}
	
	public static String  generateCheerUpCommentaryWin(){
		return cheerUpCommentaryWin[getRandomNumber(cheerUpCommentaryWin.length)];

	}
	
	public static String  generateCheerUpCommentaryLose(){
		return cheerUpCommentaryLose[getRandomNumber(cheerUpCommentaryLose.length)];

	}
	
	public static String  generateCheerUpCommentarySame(){
		return cheerUpCommentarySame[getRandomNumber(cheerUpCommentarySame.length)];

	}
	
	public static String  generateEndCommentary(){
		return endCommentary[getRandomNumber(endCommentary.length)];
	}
	
	
	/**
	 * random number generator
	 * 
	 * @param range
	 * @return random number within range
	 */
	public static int getRandomNumber(int range) {
		Random random = new Random();
		return random.nextInt(range);
	}
}