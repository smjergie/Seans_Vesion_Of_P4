package cs314.groupg.p4.V1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class displayCorrectOrIncorrect extends Activity{
	private Quiz quiz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_result);
		Bundle b = getIntent().getExtras();
		this.quiz = b.getParcelable("parcel");
		this.quiz.gradeQuiz();
		int questionIndex = quiz.getQuestionList()[quiz.getCurrentQuestion()];
		if (quiz.getQuestions().get(questionIndex).getCorrectAnswer() == quiz.getQuestions().get(questionIndex).getUsersAnswer()) {
			((TextView) findViewById(R.id.text)).setText("Correct!");
			((TextView) findViewById(R.id.text)).setTextColor(Color.parseColor("#2ECC71"));
		}else {
			((TextView) findViewById(R.id.text)).setText("Incorrect!");
			((TextView) findViewById(R.id.text)).setTextColor(Color.parseColor("#E74C3C"));
		}
	}
	
	public void ok(View view) {		
		this.finish();
	}
	
}