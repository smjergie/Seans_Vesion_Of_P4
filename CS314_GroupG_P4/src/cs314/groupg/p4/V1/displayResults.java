package cs314.groupg.p4.V1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class displayResults extends Activity{
	public final static String EXTRA_Q_COUNT = "groupg.quiztime.Q_COUNT";
	private int countQuestions;
	private int countCorrect;
	private int countWrong;
	private int countSkipped;
	private double percentGrade;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		int[] results = intent.getIntArrayExtra(displayQuiz.EXTRA_RESULTS);

		setContentView(R.layout.results);

		//Interpret results array 
		InterpretResults(results);
		ShowResults();
	}
	
	private void InterpretResults(int[] results) {
		countQuestions = results.length;

		for(int i = 0; i < countQuestions; i++){
			switch (results[i]) {
			case -1 :
				countSkipped ++;
				break;
			case 0 :
				countWrong ++;
				break;
			case 1 :
				countCorrect ++;
				break;				
			}			
		}		
		percentGrade = Math.round(100.00 * ((double)countCorrect/countQuestions));
	}
	
	/**
	 * Updates UI to show the results stored in the current local variables:
	 * letterGrade, percentGrade, countCorrect, countQuestions as well as updating attempted
	 */
	private void ShowResults() {
		//Set Percent
		TextView textPercent = (TextView) findViewById(R.id.percent);
		textPercent.setText(percentGrade + "%");

		//Set Correct
		TextView textCorrect = (TextView) findViewById(R.id.correct_answers);
		textCorrect.setText(String.valueOf(countCorrect));

		//Set Questions
		TextView textQuestions = (TextView) findViewById(R.id.number_of_questions);
		textQuestions.setText(String.valueOf(countQuestions));

		//Set Attempts
		TextView textAttempted = (TextView) findViewById(R.id.questions_attempted);
		textAttempted.setText(String.valueOf(countQuestions - countSkipped));
	}


	public void NewQuizClick(View view){
		Intent intent = new Intent(this, displayQuiz.class);
		intent.putExtra(EXTRA_Q_COUNT, countQuestions);
		startActivity(intent);
	}
}
