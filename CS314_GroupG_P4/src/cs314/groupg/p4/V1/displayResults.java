package cs314.groupg.p4.V1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class displayResults extends Activity{
	private Quiz quiz;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results);
		Bundle b = getIntent().getExtras();
		this.quiz = b.getParcelable("parcel");
		this.quiz.gradeQuiz();
		((TextView) findViewById(R.id.number_of_questions)).setText(Integer.toString(quiz.getQuestionList().length));
		((TextView) findViewById(R.id.questions_attempted)).setText(Integer.toString(quiz.getQuestionsAnswered()));
		((TextView) findViewById(R.id.correct_answers)).setText(Integer.toString(quiz.getQuestionsCorrect()));
		((TextView) findViewById(R.id.percent)).setText(Double.toString(getPercentage(quiz)) + "%");
	}
	
	private double getPercentage(Quiz quiz){
		return (((double) quiz.getQuestionsCorrect() / (double) quiz.getQuestionsAnswered()) * 100);
	}
	
	public void quitApplication(View view) {
		//android.os.Process.killProcess(android.os.Process.myPid());
		finish();
	}

}
