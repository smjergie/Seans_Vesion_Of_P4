package cs314.groupg.p4.V1;

import java.util.Random;

public class Question {
	
	/** Instance Variables */
	private String _stub;
	private String[] _answers;
	private int _userAns;
	private int _correctAns;
	private boolean _isClean;
	
	/** Constructors */
	//Default Constructor
	public Question (){
		_stub = null;
		_answers = null;
		_isClean = true;
	}
	
	//Complete Constructor
	public Question (String stub, String dummy1, String dummy2, String dummy3, String correct){
		_stub = stub;
		_answers = getRandAnswerArray(dummy1, dummy2, dummy3, correct);
		_isClean = true;
	}
	
	/** Setters */
	public void setStub(String stub){
		_stub = stub;
	}
	
	public void setAnswers(String dummy1, String dummy2, String dummy3, String correct){
		_answers = getRandAnswerArray(dummy1, dummy2, dummy3, correct);
	}
	
	public void setUserAnswer(int userAns) {
		_userAns = userAns;		
	}
	
	public void setClean(boolean isClean) {
		_isClean = isClean;
	}	

	/** Getters */
	public String getStub(){
		return _stub;
	}
	
	public String[] getAnswers(){
		return _answers;
	}
	
	public int getUserAnswer(){
		return _userAns;
	}
	
	public int getCorrectAnswer(){
		return _correctAns;
	}
	
	public boolean getClean(){
		return _isClean;
	}

	/**
	 * @return A randomized String array containing d1,d2,d3, and 
	 * c with leading "(c)" infront of correct answer
	 */
	private String[] getRandAnswerArray( String d1, String d2, String d3, String c) {
		
		//create and null answers
		String[] answers = new String[4];
		for(int i = 0; i < 4; i++){
			answers[i] = null;
		}
		
		/**randomize 3 dummy answers and correct answer into answers[] */
		//add _dummy1 to random spot
		int radNum = (int) (Math.random() * 4);
		answers[radNum] = d1;
		
		//add _dummy2 to random empty spot
		radNum = (int) (Math.random() * 4);//get next random
		if(answers[radNum] == null)
			answers[radNum] = d2;
		else { //spot in array already used, move to next spot
			answers[(radNum + 1)%4] = d2;
		}
		
		//add _dummy3 to first empty spot
		for(int i = 0; i < 4; i++){
			if(answers[i] == null){
				answers[i] = d3;
				break;
			}
		}
		
		//add _correct to remaining empty spot
		for(int i = 0; i < 4; i++){
			if(answers[i] == null){
				answers[i] = c;
				_correctAns = i;
				break;
			}
		}
		
		return answers;
	}
}
