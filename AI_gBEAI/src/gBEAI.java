import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.logging.Logger;

import aiinterface.CommandCenter;
import enumerate.Action;
import enumerate.State;
import aiinterface.AIInterface;
import mcts.AiData;
import mcts.MCTS;
import mcts.Node;
import mcts.Prediction;
import mcts.TheIO;
import parameter.FixParameter;
import simulator.Simulator;
import struct.CharacterData;
import struct.FrameData;
import struct.GameData;
import struct.Key;
import struct.MotionData;

public class gBEAI implements AIInterface {

	private Simulator simulator;
	private Key key;
	private CommandCenter commandCenter;
	private boolean playerNumber;

	/** รฅยคยงรฆล“ยฌรฃ๏ฟฝยฎFrameData */
	private FrameData frameData;

	/** รฅยคยงรฆล“ยฌรฃโ€�ห�รฃโ€�ล FRAME_AHEADรฅห�โ€ รฉ๏ฟฝโ€ฆรฃโ€�ล’รฃ๏ฟฝลธFrameData */
	private FrameData simulatorAheadFrameData;

	/** รจโ€กยชรฅห�โ€ รฃ๏ฟฝล’รจยกล’รฃ๏ฟฝห�รฃโ€�โ€นรจยกล’รฅโ€นโ€ขรฅโ€ฆยจรฃ๏ฟฝยฆ */
	private LinkedList<Action> myActions;

	/** รงโ€บยธรฆโ€ฐโ€นรฃ๏ฟฝล’รจยกล’รฃ๏ฟฝห�รฃโ€�โ€นรจยกล’รฅโ€นโ€ขรฅโ€ฆยจรฃ๏ฟฝยฆ */
	private LinkedList<Action> oppActions;

	/** รจโ€กยชรฅห�โ€ รฃ๏ฟฝยฎรฆฦ’โ€ฆรฅย ยฑ */
	private CharacterData myCharacter;

	/** รงโ€บยธรฆโ€ฐโ€นรฃ๏ฟฝยฎรฆฦ’โ€ฆรฅย ยฑ */
	private CharacterData oppCharacter;

	private Action[] actionAir;

	private Action[] actionGround;

	/** STAND_D_DF_FCรฃ๏ฟฝยฎรฅโ€บลพรฉ๏ฟฝยฟรจยกล’รฅโ€นโ€ขรงโ€�ยจรฃฦ’โ€ขรฃฦ’ยฉรฃโ€�ยฐ */
	private boolean isFcFirst = true;

	/** รฆโ€ขยตรฃ๏ฟฝล’STAND_D_DF_FCรฃโ€�โ€�รคยฝยฟรฃ๏ฟฝยฃรฃ๏ฟฝยฆรฃ๏ฟฝ๏ฟฝรฃโ€�โ€นรฃ๏ฟฝโ€นรฃ๏ฟฝยฉรฃ๏ฟฝโ€ รฃ๏ฟฝโ€น */
	private boolean canFC = true;

	/** STAND_D_DF_FCรฃ๏ฟฝยฎรฅโ€บลพรฉ๏ฟฝยฟรจยกล’รฅโ€นโ€ขรฆโ�ขโ€�รฉโ€“โ€�รฃโ€�โ€�รจยจห�รฆยธยฌรฃ๏ฟฝโ�ขรฃโ€�โ€น */
	private long firstFcTime;

	private ArrayList<MotionData> myMotion;

	private ArrayList<MotionData> oppMotion;

	private Action spSkill;

	private Node rootNode;

	private MCTS mcts;
	
	Logger logger;

	//private double beta;

	//private int[] resultHpDiff;

	//private int target = 0;
	
	//TTS text generator	
	int gameState; // initialize game state, 0 start, 1 early game, 2 mid game, 3 near end game, 4 end game, 5 specific mode
	int AIState; // 0 highlight, 1 mcts, 2 harmless
	String textFromAI, previousText, nameC, textFromCheering;
	String opponentCurrentMove;	
	String opponentPreviousMove;
	int opponentCurrentMoveDamage;
	int opponentCurrentMoveDamageMax;
	private LinkedList<String> tempOpponentActionList;	
	boolean isSpeaking;//check if it's speaking
	float balancednessFitness;
	//TTSSkillMap_Male ttsSkillMap;
	TTSSkillMap ttsSkillMap;
	TTSSkillMap_Male ttsSkillMap_male;
	String opponentActionPath;
	String opponentCurrentAction;
	String opponentPreviousAction;
		//UKI Map
		private Map<String, String> ukiSkillMap;
		private Map<String, Integer> realToUkiMap;	
		int getmyHp,getoppHp, checkHp;
		float pdaEvalMin;
		float pdaEvalMax;
		float distanceMin;	
		float distanceMax;
		float current;
		float actionValue;
		boolean cheering,writing;
		int count =0, k=0;
		Timer timer = new Timer();
	    TimerTask task = new TimerTask() {
	    	public void run() {
	    		count++;
	    		//writing = true;
	    		System.out.println("timing!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	    	}
	    };
	    
	public void start()
	{
		timer.scheduleAtFixedRate(task, 1000, 1000);
		
	}
	public void stop()
	{
		timer.cancel();
	}
		
	private void exportTextFile()
		{
		
			if(cheering)//checkCheering
			{
				try {
					BufferedWriter writer =
	                    new BufferedWriter(new FileWriter("C:\\Users\\Thanat Jumneanbun\\Documents\\texttospeechAPG\\textfile\\Cheering.txt"));

					writer.write(textFromCheering);
					System.out.println(textFromCheering);
					System.out.println(count);
					writer.close();          
					cheering = false;
	          
					} catch (IOException e) {
						e.printStackTrace();
						cheering = false;
				}			
			}
		
			if(playerNumber)//P1 action
			{
				try {
			           BufferedWriter writer =
			                  new BufferedWriter(new FileWriter("C:\\Users\\Thanat Jumneanbun\\Documents\\texttospeechAPG\\textfile\\P1Comment.txt"));

			           writer.write(textFromAI);
			           System.out.println("Player1: "+textFromAI);
			           System.out.println(count);
			           writer.close();       
			           writing = false;
			           
			          
			       } catch (IOException e) {
			           e.printStackTrace();
			       }
					//Print HP
				
				try {
					BufferedWriter writer =
			                  new BufferedWriter(new FileWriter("D:\\FTGexp\\F\\p1HP.txt"));
					
					 writer.write(String.valueOf(getmyHp));
					 writer.close();
			         System.out.println("Player1 HP: "+getmyHp);
				}catch (IOException e){
					e.printStackTrace();
				}
				
				
				
				
				}
			else if(!playerNumber)//P2 action
			{
				try {
			           BufferedWriter writer =
			                   new BufferedWriter(new FileWriter("C:\\Users\\Thanat Jumneanbun\\Documents\\texttospeechAPG\\textfile\\P2Comment.txt"));

			           writer.write(textFromAI);
			           System.out.println("Player2: "+textFromAI);
			           System.out.println(count);
			           writer.close();          
			           writing = false;
			          
			       } catch (IOException e) {
			           e.printStackTrace();
			       }
				
				try {
					BufferedWriter writer =
			                  new BufferedWriter(new FileWriter("D:\\FTGexp\\F\\p2HP.txt"));
					
					 writer.write(String.valueOf(getmyHp));
			         System.out.println("Player1 HP: "+getmyHp);
			         writer.close();
				}catch (IOException e){
					e.printStackTrace();
				}
			}
				//textFromAI = ttsSkillMap.generateCheerUpCommentary();
			
			try {
				BufferedWriter writer =
		                  new BufferedWriter(new FileWriter("D:\\FTGexp\\F\\roundEnd.txt"));
				
				 writer.write("0");
				 writer.close();
			}catch (IOException e){
				e.printStackTrace();
			}
				isSpeaking = false;
				count=0;
				
		}
			/*else {
				count++;
				isSpeaking = true;
			}*/
			
			
		
	
	private void getOpponentCurrentMoveInformation() {
		//TODO
//		if (opponentPreviousMove != ttsSkillMap.getActionRealName(this.frameData.getCharacter(!playerNumber).getAction().name())) {
//			opponentPreviousMove = opponentCurrentMove;
			opponentCurrentMove = ttsSkillMap.getActionRealName(this.frameData.getCharacter(!playerNumber).getAction().name());
			
			//Ishii CoG 2019
//			if (opponentCurrentMove == "Ultimate Hadouken"){
//				actionValue = 1.0f;
//			} else if (opponentCurrentMove == "Super Uppercut"){
//				actionValue = 0.5f;
//			} else if (opponentCurrentMove == "Slide Kick"){
//				actionValue = 0.25f;
//			} else if (opponentCurrentMove == "Super Hadouken"){
//				actionValue = 0.125f;		
//			} else {
//				actionValue = 0.0f;
//			}

			if (isSpeaking == true) {
				if (!opponentCurrentMove.contains("Default"))
				{		
					tempOpponentActionList.add(opponentCurrentMove);
					opponentCurrentMoveDamage = myMotion.get(this.frameData.getCharacter(!playerNumber).getAction().ordinal()).getAttackHitDamage();
					if (opponentCurrentMoveDamage > opponentCurrentMoveDamageMax) 
					{
						opponentCurrentMoveDamageMax = opponentCurrentMoveDamage;
						
					}
				}
			} else {
				if (tempOpponentActionList.size() != 0) {
					opponentCurrentMove = tempOpponentActionList.get(ttsSkillMap.getRandomNumber(tempOpponentActionList.size()));
					
//					current = (float) opponentCurrentMoveDamageMax / (float) myMotion.get(Action.STAND_D_DF_FC.ordinal()).getAttackHitDamage();
//					//System.out.println("Max=" + (float) opponentCurrentMoveDamageMax + "current" + (float) myMotion.get(Action.STAND_D_DF_FC.ordinal()).getAttackHitDamage());
					opponentCurrentMoveDamage = myMotion.get(Action.STAND_D_DF_FC.ordinal()).getAttackHitDamage() / myMotion.get(Action.STAND_D_DF_FC.ordinal()).getAttackHitDamage();
					tempOpponentActionList.clear();
					opponentCurrentMoveDamageMax = 0;
				}
			}			
		}

	@Override
	public void close() {
		// TODO รจโ€กยชรฅโ€นโ€ขรงโ€�ลธรฆห�๏ฟฝรฃ๏ฟฝโ€ขรฃโ€�ล’รฃ๏ฟฝลธรฃฦ’ยกรฃโ€�ยฝรฃฦ’ฦ’รฃฦ’โ€ฐรฃฦ’ยปรฃโ€�ยนรฃโ€�ยฟรฃฦ’โ€“
		textFromAI = ttsSkillMap.generateEndCommentary();
		//System.out.println("end");
		System.out.println(textFromAI);
		stop();
		//count = 200;
		exportTextFile();

	}

	@Override
	public void getInformation(FrameData frameData, boolean isControl) {
		this.frameData = frameData;
		this.commandCenter.setFrameData(this.frameData, playerNumber);
		this.myCharacter = this.frameData.getCharacter(playerNumber);
		this.oppCharacter = this.frameData.getCharacter(!playerNumber);
	}

	@Override
	public int initialize(GameData gameData, boolean playerNumber) {
		setupBesfAI(playerNumber);
		//--------------
		this.playerNumber = playerNumber;
		
		this.key = new Key();
		this.frameData = new FrameData();
		this.commandCenter = new CommandCenter();

		this.myActions = new LinkedList<Action>();
		this.oppActions = new LinkedList<Action>();

		this.simulator = gameData.getSimulator();
		this.myMotion = gameData.getMotionData(playerNumber);
//		this.myMotion = gameData.getMyMotion(playerNumber);
		this.oppMotion = gameData.getMotionData(!playerNumber);
//		this.oppMotion = gameData.getOpponentMotion(playerNumber);
		this.opponentPreviousMove = "Default";
		this.tempOpponentActionList = new LinkedList<String>();
	/*	this.beta = 0;
		this.resultHpDiff = new int[3];
		Arrays.fill(resultHpDiff, 0);*/

		// รฅ๏ฟฝโ€�รงยจยฎรฉย โ€ฆรงโ€บยฎรฃ๏ฟฝยฎรฅห�๏ฟฝรฆล“ลธรฅล’โ€“
		setPerformAction();
		count=0;
		getmyHp = 0;
		getoppHp = 0;
		checkHp = 0;
		start();
		isSpeaking = false;
		cheering = false;
		ttsSkillMap_male = new TTSSkillMap_Male();
		ttsSkillMap = new TTSSkillMap();
		textFromAI = ttsSkillMap.generateBeginCommentary();
		exportTextFile() ;
		
		return 0;
	}

	@Override
	public void processing() {

		
		if (canProcessing()) {
			 getmyHp = myCharacter.getHp();
			 getoppHp = oppCharacter.getHp();
			//System.out.print("Process!!!!!!");
			getOpponentCurrentMoveInformation();
			if(count == 3)
			{
				if(playerNumber)
				{
					checkHp = getmyHp - getoppHp;
					
					if(checkHp < -100)
					{
						textFromAI = ttsSkillMap.generateHealthCommentary(opponentCurrentMove, false);
						
					}else if (checkHp > 100){
						textFromAI = ttsSkillMap.generateHealthCommentary(opponentCurrentMove, true);
					}
					else {
						textFromAI = ttsSkillMap.generateNormalCommentary(opponentCurrentMove);	
					}
					
				}
				else if(!playerNumber)
				{
					checkHp = getmyHp - getoppHp;
					
					if(checkHp < -100)
					{
						textFromAI = ttsSkillMap_male.generateHealthCommentary(opponentCurrentMove, false);
						
					}else if (checkHp > 100){
						textFromAI = ttsSkillMap_male.generateHealthCommentary(opponentCurrentMove, true);
					}
					else {
						textFromAI = ttsSkillMap_male.generateNormalCommentary(opponentCurrentMove);	
					}	
				}
				textFromCheering = ttsSkillMap.generateCheerUpCommentary();
				cheering = true;
				exportTextFile() ;
				
				
			}
			
			/*else if(count == 2){
				isSpeaking = true;
			}
			else {
				isSpeaking = false;
			}*/
			
			
			// รฃฦ’โ€ขรฃฦ’ยฉรฃโ€�ยฐรฃ๏ฟฝยซรฃโ€�ห�รฃ๏ฟฝยฃรฃ๏ฟฝยฆรคยบห�รฆยธยฌรฃโ€�โ€�รฃ๏ฟฝโ�ขรฃโ€�โ€นรฃ๏ฟฝโ€นรฉ๏ฟฝยธรฆล ลพ
			if (FixParameter.PREDICT_FLAG) {
				if (oppMotion.get(oppCharacter.getAction().ordinal()).getFrameNumber() == oppCharacter
						.getRemainingFrame()) {
					Prediction.getInstance().countOppAction(this.frameData,oppCharacter, commandCenter);
				}
			}

			if (commandCenter.getSkillFlag()) {
				key = commandCenter.getSkillKey();
			} else {
				key.empty();
				commandCenter.skillCancel();

				aheadFrame(); // รฉ๏ฟฝโ€ฆรฃโ€�ล’รฃฦ’โ€ขรฃฦ’ยฌรฃฦ’ยผรฃฦ’ย รฅห�โ€ รฉโ�ฌยฒรฃโ€�๏ฟฝรฃโ€�โ€น

				// รฃฦ’โ€ขรฃฦ’ยฉรฃโ€�ยฐรฃ๏ฟฝยซรฃโ€�ห�รฃ๏ฟฝยฃรฃ๏ฟฝยฆรฅโ€บลพรฉ๏ฟฝยฟรจยกล’รฅโ€นโ€ขรฃโ€�โ€�รฃ๏ฟฝโ�ขรฃโ€�โ€นรฃ๏ฟฝโ€นรฃ๏ฟฝยฉรฃ๏ฟฝโ€ รฃ๏ฟฝโ€นรฉ๏ฟฝยธรฆล ลพ
				if (FixParameter.AVOID_FLAG) {
					String enemyAction = this.frameData.getCharacter(!playerNumber).getAction().name();
					int enemyEnergy = this.frameData.getCharacter(!playerNumber).getEnergy();

					if (enemyAction.equals("STAND_D_DF_FC")) {
						canFC = true;
						isFcFirst = true;
					}

					if (enemyEnergy >= 150 && canFC) {
						if (isFcFirst) {
							firstFcTime = frameData.getRemainingTime();
							isFcFirst = false;
						}
						if (firstFcTime - frameData.getRemainingTime() >= FixParameter.AVOIDANCE_TIME) {
							canFC = false;
							isFcFirst = true;
						} else {
							commandCenter.commandCall("STAND_D_DB_BA");
							return;
						}
					}
				}

				if (FixParameter.PREDICT_FLAG) {
					Prediction.getInstance().getInfomation(); // รฅโ€บลพรฆโ€ขยฐรฉย โ€ รฃ๏ฟฝยงรฃโ€�ยฝรฃฦ’ยผรฃฦ’ห�
				}

				// MCTSรฃ๏ฟฝยซรฃโ€�ห�รฃโ€�โ€นรจยกล’รฅโ€นโ€ขรฆยฑยบรฅยฎลก
				Action bestAction = Action.STAND_D_DB_BA;
				mctsPrepare(); // MCTSรฃ๏ฟฝยฎรคยธโ€นรฆยบโ€“รฅโ€�โ�ขรฃโ€�โ€�รจยกล’รฃ๏ฟฝโ€ 

				bestAction = mcts.runMcts(); // MCTSรฃ๏ฟฝยฎรฅยฎลธรจยกล’
				commandCenter.commandCall(bestAction.name()); // MCTSรฃ๏ฟฝยงรฉ๏ฟฝยธรฆล ลพรฃ๏ฟฝโ€ขรฃโ€�ล’รฃ๏ฟฝลธรจยกล’รฅโ€นโ€ขรฃโ€�โ€�รฅยฎลธรจยกล’รฃ๏ฟฝโ�ขรฃโ€�โ€น

				if (FixParameter.DEBUG_MODE) {
					mcts.printNode(rootNode);
				}
			}
		} else {
			canFC = true;
			isFcFirst = true;
		}
	}

	@Override
	public void roundEnd(int x, int y, int frame) {
		
		/*this.resultHpDiff[frameData.getRound()] = this.playerNumber ? x - y : y - x;

		double targetHP = 0;
		int i = 0;
		for (i = 0; i <= frameData.getRound() && frameData.getRound() != 2; i++) {
			targetHP += resultHpDiff[i];
		}

		if (frameData.getRound() != 2) {
			this.beta = ((double) targetHP / i) / FixParameter.TANH_SCALE;

			//System.out.println("target: " + targetHP + " beta " + beta);
		}*/

		/*if (frameData.getRound() != 2) {
			this.beta = ((double) target) / FixParameter.TANH_SCALE;

			//System.out.println("target: " + target + " beta " + beta);
		}*/
		try {
			BufferedWriter writer =
	                  new BufferedWriter(new FileWriter("D:\\FTGexp\\F\\roundEnd.txt"));
			
			 writer.write("1");
			 writer.close(); 
	         
		}catch (IOException e){
			e.printStackTrace();
		}
		if(logData) {exportData();}
	}

	/**
	 * MCTSรฃ๏ฟฝยฎรคยธโ€นรฆยบโ€“รฅโ€�โ�ข <br>
	 * รฉ๏ฟฝโ€ฆรฃโ€�ล’รฃฦ’โ€ขรฃฦ’ยฌรฃฦ’ยผรฃฦ’ย รฅห�โ€ รฉโ�ฌยฒรฃ๏ฟฝยพรฃ๏ฟฝโ€บรฃ๏ฟฝลธFrameDataรฃ๏ฟฝยฎรฅ๏ฟฝโ€“รฅยพโ€”รฃ๏ฟฝยชรฃ๏ฟฝยฉรฃโ€�โ€�รจยกล’รฃ๏ฟฝโ€ 
	 */
	public void mctsPrepare() {
		setMyAction();
		setOppAction();
		rootNode = new Node(null);
		mcts = new MCTS(rootNode, simulatorAheadFrameData, simulator, myCharacter.getHp(), oppCharacter.getHp(),
				myActions, oppActions, playerNumber, filePath);
		mcts.createNode(rootNode);
		if(logData) {logData();}
	}

	/** รจโ€กยชรจยบยซรฃ๏ฟฝยฎรฅ๏ฟฝยฏรจฦ’ยฝรฃ๏ฟฝยชรจยกล’รฅโ€นโ€ขรฃโ€�โ€�รฃโ€�ยปรฃฦ’ฦ’รฃฦ’ห�รฃ๏ฟฝโ�ขรฃโ€�โ€น */
	public void setMyAction() {
		myActions.clear();

		int energy = myCharacter.getEnergy();

		if (myCharacter.getState() == State.AIR) {
			for (int i = 0; i < actionAir.length; i++) {
				if (Math.abs(myMotion.get(Action.valueOf(actionAir[i].name()).ordinal())
						.getAttackStartAddEnergy()) <= energy) {
					myActions.add(actionAir[i]);
				}
			}
		} else {
			if (ultimateOK &&
					Math.abs(
					myMotion.get(Action.valueOf(spSkill.name()).ordinal()).getAttackStartAddEnergy()) <= energy) {
				myActions.add(spSkill);
			}

			for (int i = 0; i < actionGround.length; i++) {
				if (Math.abs(myMotion.get(Action.valueOf(actionGround[i].name()).ordinal())
						.getAttackStartAddEnergy()) <= energy) {
					myActions.add(actionGround[i]);
				}
			}
		}

	}

	/** รงโ€บยธรฆโ€ฐโ€นรฃ๏ฟฝยฎรฅ๏ฟฝยฏรจฦ’ยฝรฃ๏ฟฝยชรจยกล’รฅโ€นโ€ขรฃโ€�โ€�รฃโ€�ยปรฃฦ’ฦ’รฃฦ’ห�รฃ๏ฟฝโ�ขรฃโ€�โ€น */
	public void setOppAction() {
		oppActions.clear();

		int energy = oppCharacter.getEnergy();

		if (oppCharacter.getState() == State.AIR) {
			for (int i = 0; i < actionAir.length; i++) {
				if (Math.abs(oppMotion.get(Action.valueOf(actionAir[i].name()).ordinal())
						.getAttackStartAddEnergy()) <= energy) {
					oppActions.add(actionAir[i]);
				}
			}
		} else {
			if (Math.abs(oppMotion.get(Action.valueOf(spSkill.name()).ordinal())
					.getAttackStartAddEnergy()) <= energy) {
				oppActions.add(spSkill);
			}

			for (int i = 0; i < actionGround.length; i++) {
				if (Math.abs(oppMotion.get(Action.valueOf(actionGround[i].name()).ordinal())
						.getAttackStartAddEnergy()) <= energy) {
					oppActions.add(actionGround[i]);
				}
			}
		}
	}

	/** รฉ๏ฟฝโ€ฆรฃโ€�ล’รฃฦ’โ€ขรฃฦ’ยฌรฃฦ’ยผรฃฦ’ย รฅห�โ€ รฉโ�ฌยฒรฃโ€�๏ฟฝรฃโ€�โ€น */
	private void aheadFrame() {
		simulatorAheadFrameData = simulator.simulate(this.frameData, playerNumber, null, null,
				FixParameter.FRAME_AHEAD);
		myCharacter = simulatorAheadFrameData.getCharacter(playerNumber);
		oppCharacter = simulatorAheadFrameData.getCharacter(!playerNumber);
	}

	/** รฃโ€�ยขรฃโ€�ยฏรฃโ€�ยทรฃฦ’ยงรฃฦ’ยณรฃ๏ฟฝยฎรฉโ€ฆ๏ฟฝรฅห�โ€”รฃ๏ฟฝยฎรฅห�๏ฟฝรฆล“ลธรฅล’โ€“ */
	private void setPerformAction() {
		actionAir = new Action[] { Action.AIR_GUARD, Action.AIR_A, Action.AIR_B, Action.AIR_DA, Action.AIR_DB,
				Action.AIR_FA, Action.AIR_FB, Action.AIR_UA, Action.AIR_UB, Action.AIR_D_DF_FA, Action.AIR_D_DF_FB,
				Action.AIR_F_D_DFA, Action.AIR_F_D_DFB, Action.AIR_D_DB_BA, Action.AIR_D_DB_BB };
		actionGround = new Action[] { Action.STAND_D_DB_BA, Action.BACK_STEP, Action.FORWARD_WALK, Action.DASH,
				Action.JUMP, Action.FOR_JUMP, Action.BACK_JUMP, Action.STAND_GUARD, Action.CROUCH_GUARD, Action.THROW_A,
				Action.THROW_B, Action.STAND_A, Action.STAND_B, Action.CROUCH_A, Action.CROUCH_B, Action.STAND_FA,
				Action.STAND_FB, Action.CROUCH_FA, Action.CROUCH_FB, Action.STAND_D_DF_FA, Action.STAND_D_DF_FB,
				Action.STAND_F_D_DFA, Action.STAND_F_D_DFB, Action.STAND_D_DB_BB };
		spSkill = Action.STAND_D_DF_FC;
	}

	/**
	 * AIรฃ๏ฟฝล’รจยกล’รฅโ€นโ€ขรฃ๏ฟฝยงรฃ๏ฟฝ๏ฟฝรฃโ€�โ€นรฃ๏ฟฝโ€นรฃ๏ฟฝยฉรฃ๏ฟฝโ€ รฃ๏ฟฝโ€นรฃโ€�โ€�รฅห�ยครฅห�ยฅรฃ๏ฟฝโ�ขรฃโ€�โ€น
	 *
	 * @return AIรฃ๏ฟฝล’รจยกล’รฅโ€นโ€ขรฃ๏ฟฝยงรฃ๏ฟฝ๏ฟฝรฃโ€�โ€นรฃ๏ฟฝโ€นรฃ๏ฟฝยฉรฃ๏ฟฝโ€ รฃ๏ฟฝโ€น
	 */
	public boolean canProcessing() {
		return !frameData.getEmptyFlag() && frameData.getRemainingTime() > 0;
	}

	@Override
	public Key input() {
		// TODO รจโ€กยชรฅโ€นโ€ขรงโ€�ลธรฆห�๏ฟฝรฃ๏ฟฝโ€ขรฃโ€�ล’รฃ๏ฟฝลธรฃฦ’ยกรฃโ€�ยฝรฃฦ’ฦ’รฃฦ’โ€ฐรฃฦ’ยปรฃโ€�ยนรฃโ€�ยฟรฃฦ’โ€“
		return key;
	}


	//BesfAI============================================
	Boolean testMODE = false;
	Boolean ultimateOK = true;
	Boolean logData = false;
	
	public String filePath = "";
	public String dataPath = "data/aiData/gBEAI/";
	
	public void setupBesfAI(boolean playerNumber) {
		if(!testMODE) {
			//use file paths that match Sound Capture (specified in path_.txt)
			try {
				System.out.println("Load Setting"); 
				if(playerNumber) { filePath = TheIO.readFile(dataPath + "path1.txt"); }
				else { filePath = TheIO.readFile(dataPath + "path2.txt"); }
				System.out.print("FILE: " + filePath);
			}
			catch(Exception ex) {
				System.out.print("File Path: " + ex.toString()); 
			}
		}
		else {
			String p = dataPath + "P1.txt";
			String p2 = dataPath + "P2.txt";
			if(playerNumber) { filePath = p; }
			else { filePath = p2; }
		}
	}


	//=================
	List<AiData> list_data = new ArrayList<AiData>();
	void logData() {
		int t = mcts.getTimeLeftSecond();
		if(firstRecord) {
			AiData d  = mcts.getData();
			list_data.add(d);
			firstRecord = false;
		}
		else if(lastRecord) {
			AiData d  = mcts.getData();
			list_data.remove(list_data.size() - 1);
			list_data.add(d);
		}
		else if(t <= nextData) {
			AiData d  = mcts.getData();
			list_data.add(d);
			nextData -= interval;
			if(nextData <= 0) {
				lastRecord = true;
			}
		}
//		System.out.println("log " + list_data.size()); 
	}
	
	int nextData = 59400;
	int interval = 600;
	Boolean lastRecord = false;
	Boolean firstRecord = true;
		
	void exportData(){
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");  
		    Date date = new Date();  
		    String filename = formatter.format(date);
		    if(playerNumber) { filename += " P1"; }
		    else { filename += " P2"; }
		    filename += ".csv";
			String path = "data/aiData/gBEAI/log/" + filename;
			PrintWriter writer = new PrintWriter(path, "UTF-8");
			//
			//writer.println("time,hp_my,hp_opp,alpha,dB");
			for(AiData d : list_data){
				String s = "";
				s += d.time + "," + d.myHP + "," + d.oppHP + "," + d.f + "," + d.dB;
				//s += d.myHP + "," + d.oppHP;
				writer.println(s);
			}
			writer.close();
			System.out.print("Export " + filename); 
		}
		catch(Exception ex){
			System.out.print(ex.toString()); 
		}
	}
	
}