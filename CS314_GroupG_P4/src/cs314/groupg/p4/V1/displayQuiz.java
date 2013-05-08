package cs314.groupg.p4.V1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class displayQuiz extends Activity{
	public final static String EXTRA_RESULTS = "cs314.groupg.p4.V1.RESULTS";
	private final static int DEFAULT_QUESTION_COUNT = 10;
	private final static int QUESTIONS_IN_FILE = 50;

	private Question[] questions;
	private int[] questionStatus;
	private int questionCount;
	private int currentQ;
	private TextView stub;
	private RadioGroup answerGroup;
	private RadioButton radio0;
	private RadioButton radio1;
	private RadioButton radio2;
	private RadioButton radio3;
	private Button backBtn;
	private Button skipBtn;
	private Button answerBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		questionCount = DEFAULT_QUESTION_COUNT;		

		//create array to hold statuses of all questions
		//-1:notAttempted, 0:answeredWrong, 1:answeredCorrect
		questionStatus = new int[questionCount];
		for(int i = 0; i < questionCount; i++){			
			questionStatus[i] = -1; //set statuses all to notAttempted			
		}

		setContentView(R.layout.display_quiz);

		//getGUIObjects
		stub = (TextView) findViewById(R.id.question);
		answerGroup = (RadioGroup) findViewById(R.id.answergroup);
		radio0 = (RadioButton) findViewById(R.id.answer1);
		radio1 = (RadioButton) findViewById(R.id.answer2);
		radio2 = (RadioButton) findViewById(R.id.answer3);
		radio3 = (RadioButton) findViewById(R.id.answer4);
		backBtn = (Button) findViewById(R.id.back_button);
		skipBtn = (Button) findViewById(R.id.next_button);
		answerBtn = (Button) findViewById(R.id.answer_button);
		
		//fill Question[] with questions from file
		questions = importQuestions("P4 Questions.txt");

		//Add listener to enable answer button when
		answerGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged (RadioGroup group, int checkedId) {
				answerBtn.setEnabled(true);
			}
		});

		//Setup Initial Question
		currentQ = 0; 
		updateQuestion();		
		resetRadios();
		backBtn.setEnabled(false);
		backBtn.setBackgroundResource(R.drawable.back_button_disabled);
		answerBtn.setEnabled(false);
	}

	private Question[] importQuestions(String fileName) {

		Question[] allQs = new Question[QUESTIONS_IN_FILE];		

		AssetManager aManager = getAssets();
		InputStream iStream = null;
		try {
			iStream = aManager.open(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));
		String line;			

		for(int ctr = 0; ctr < QUESTIONS_IN_FILE; ctr++){
			Question question = new Question();

			//Stub
			try {
				line = bReader.readLine();
				question.setStub(line.toString().substring(3));
			} catch (IOException e) {
				e.printStackTrace();
			}

			String[] ans = new String[4];
			//C
			try {
				line = bReader.readLine();
				ans[0] = (line.toString().substring(3));
			} catch (IOException e) {
				e.printStackTrace();
			}

			//D1
			try {
				line = bReader.readLine();
				ans[1] = (line.toString().substring(3));
			} catch (IOException e) {
				e.printStackTrace();
			}

			//D2
			try {
				line = bReader.readLine();
				ans[2] = (line.toString().substring(3));
			} catch (IOException e) {
				e.printStackTrace();
			}

			//D3
			try {
				line = bReader.readLine();
				ans[3] = (line.toString().substring(3));	
			} catch (IOException e) {
				e.printStackTrace();
			}

			question.setAnswers(ans[1], ans[2], ans[3], ans[0]);

			allQs[ctr] = question;
		}

		return getRandomQuestions(allQs, questionCount);
	}	
	
	/**
	 * Randomizes questions from allQs into an array of size questionCt
	 * @return array[questionCt] containing random questions from allQs
	 */
	private Question[] getRandomQuestions(Question[] allQs, int questionCt) {
		Question[] _qs = new Question[questionCt];

		for(int i = 0; i < questionCount; i++){
			//Randomly pick questionCount number of questions
			int r1 = (int) (Math.random() * allQs.length); 
			Question randQ = allQs[r1];

			while(!randQ.getClean()){
				//randQ has already been read, so read another
				r1 = (int) (Math.random() * allQs.length); 
				randQ = allQs[r1]; 
			}

			//question has not been read already, add it to question list in next position and set clean=false to prevent duplicates
			_qs[i] = randQ;
			allQs[r1].setClean(false);
		}	

		return _qs;
	}
	
	/**
	 * Updates the GUI to show the question at index currentQ in questions[]
	 * Modifies answers[] to no longer have the leading "(c)" in front of correct answer
	 * @return Radio index of correct answer
	 */
	private void updateQuestion() {
		String[] answers = questions[currentQ].getAnswers();
		stub.setText((currentQ + 1) + ") " + questions[currentQ].getStub());

		radio0.setText(answers[0]);
		radio1.setText(answers[1]);
		radio2.setText(answers[2]);
		radio3.setText(answers[3]);

		//Check radio that user already answered
		resetRadios();
		if(questionStatus[currentQ] != -1){
			int userAnsIndex = questions[currentQ].getUserAnswer();
			RadioButton userAnswer = (RadioButton) answerGroup.getChildAt(userAnsIndex);
			userAnswer.setChecked(true);
			answerBtn.setEnabled(false);
		}
		if(currentQ == 0){
			//disable skip btn, end of question list
			
			backBtn.setEnabled(false);
			backBtn.setBackgroundResource(R.drawable.back_button_disabled);
		}else {
			backBtn.setEnabled(true);
			backBtn.setBackgroundResource(R.drawable.back_button);
		}
		if(currentQ == questionCount -1){
			//disable skip btn, end of question list
			
			skipBtn.setEnabled(false);
			skipBtn.setBackgroundResource(R.drawable.forward_button_disabled);
		}else {
			skipBtn.setEnabled(true);
			skipBtn.setBackgroundResource(R.drawable.forward_button);
		}
	}

	private int getSelectedAnsIndex() {
		int index = -1;
		if(radio0.isChecked())
			index = 0;
		else if(radio1.isChecked())
			index = 1;
		else if(radio2.isChecked())
			index = 2;
		else if(radio3.isChecked())
			index = 3;

		return index;
	}
	
	/**
	 * Clear radios and disable answer btn
	 */
	private void resetRadios() {
		RadioGroup radios = ((RadioGroup) findViewById(R.id.answergroup)) ;
		radios.clearCheck();
		answerBtn.setEnabled(false);
	}

	/** onClick listener for backBtn **/
	public void backClick(View view){
		//Mark Q as not answered and load previous Q
		if(currentQ == questionCount -1){
			//enable skip btn, not on last one anymore
			skipBtn.setEnabled(true);
		}

		//Setup Q unless at end
		currentQ--;		
		updateQuestion();

		if(currentQ == 0){
			//enable backBtn
			backBtn.setEnabled(false);
		}		
	}

	/** onClick listener for skipBtn **/
	public void skipClick(View view){

		//Mark Q as not answered and load next Q
		if(currentQ == 0){
			//enable backBtn
			backBtn.setEnabled(true);
			backBtn.setBackgroundResource(R.drawable.back_button_disabled);
		}
		//Setup Q uless at end
		currentQ++;		
		updateQuestion();

		if(currentQ == questionCount -1){
			//disable skip btn, end of question list
			
			skipBtn.setEnabled(false);
			skipBtn.setBackgroundResource(R.drawable.forward_button_disabled);
		}
	}

	/** onClick listener for answerBtn **/
	public void answerClick(View view){
		//Show next Q, save ans
		int userAns = getSelectedAnsIndex(); 
		questions[currentQ].setUserAnswer(userAns);
		//resetRadios();
		//answerBtn.setEnabled(false);

		if( userAns == questions[currentQ].getCorrectAnswer()){
			//user answered correctly
			questionStatus[currentQ] = 1;
			
			//show correct dialog
			AlertDialog.Builder dialog  = new AlertDialog.Builder(this);
			
			TextView correctTxt = new TextView(this);
			correctTxt.setText("Correct!");
			correctTxt.setGravity(Gravity.CENTER_HORIZONTAL);
			correctTxt.setTextSize(40);
			correctTxt.setTextColor(Color.parseColor("#2ECC71"));
			dialog.setView(correctTxt);
			
			dialog.setPositiveButton("OK", null);
			dialog.setCancelable(true);
			dialog.create().show();			
		} else {
			//user answered wrong
			questionStatus[currentQ] = 0;
			
			//show incorrect dialog
			AlertDialog.Builder dialog  = new AlertDialog.Builder(this);
			
			TextView incorrectTxt = new TextView(this);
			incorrectTxt.setText("Incorrect!");
			incorrectTxt.setGravity(Gravity.CENTER_HORIZONTAL);
			incorrectTxt.setTextSize(40);
			incorrectTxt.setTextColor(Color.parseColor("#E74C3C"));
			dialog.setView(incorrectTxt);
			
			dialog.setPositiveButton("OK", null);
			dialog.setCancelable(true);
			dialog.create().show();
		}

		if(currentQ == 0){
			//enable backBtn
			backBtn.setEnabled(true);
		}

		if(currentQ == questionCount -2){
			//disable skip btn, end of question list
			skipBtn.setEnabled(false);
		}

		if(currentQ == questionCount -1){
			//answered final question, sent to results
			Intent intent = new Intent(this, displayResults.class);
			intent.putExtra(EXTRA_RESULTS, questionStatus);
			finish();
			startActivity(intent);
			return;
		}

		//Setup Q uless at end
		
		//Delete the bellow comment to enable question change when you answer a question
		
		/*currentQ++;		
		updateQuestion();*/

	}

	/** onClick listener for endQuizBtn **/
	public void endQuizClick(View view){
		resetRadios();		

		Intent intent = new Intent(this, displayResults.class);
		intent.putExtra(EXTRA_RESULTS, questionStatus);
		finish();
		startActivity(intent);
	}
}
