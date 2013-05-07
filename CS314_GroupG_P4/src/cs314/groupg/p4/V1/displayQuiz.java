package cs314.groupg.p4.V1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

public class displayQuiz extends Activity{
	private Quiz quiz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_quiz);
		Bundle b = getIntent().getExtras();
		quiz = b.getParcelable("parcel");
		//Set question text
		displayQuestion();

	}

	private void displayQuestion() {


		((TextView) findViewById(R.id.question)).setText(quiz.getQuestions().get(quiz.getQuestionList()[quiz.getCurrentQuestion()]).getQuestion());
		((TextView) findViewById(R.id.answer1)).setText(quiz.getQuestions().get(quiz.getQuestionList()[quiz.getCurrentQuestion()]).getAnswer1());
		((TextView) findViewById(R.id.answer2)).setText(quiz.getQuestions().get(quiz.getQuestionList()[quiz.getCurrentQuestion()]).getAnswer2());
		((TextView) findViewById(R.id.answer3)).setText(quiz.getQuestions().get(quiz.getQuestionList()[quiz.getCurrentQuestion()]).getAnswer3());
		((TextView) findViewById(R.id.answer4)).setText(quiz.getQuestions().get(quiz.getQuestionList()[quiz.getCurrentQuestion()]).getAnswer4());

		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.answergroup);
		int questionIndex = quiz.getQuestionList()[quiz.getCurrentQuestion()];
		int answerSelected = quiz.getQuestions().get(questionIndex).getUsersAnswer();

		switch (answerSelected) {
		case 0:
			radioGroup.check(R.id.answer1);
			break;
		case 1:
			radioGroup.check(R.id.answer2);
			break;
		case 2:
			radioGroup.check(R.id.answer3);
			break;
		case 3:
			radioGroup.check(R.id.answer4);
			break;
		default:
			radioGroup.clearCheck();
			break;
		}

		if (quiz.getCurrentQuestion() == 0) {
			findViewById(R.id.back_button).setBackgroundResource(R.drawable.back_button_disabled);
			findViewById(R.id.back_button).setEnabled(false);
		}else {
			findViewById(R.id.back_button).setBackgroundResource(R.drawable.back_button);
			findViewById(R.id.back_button).setEnabled(true);
		}

		if (quiz.getCurrentQuestion() == quiz.getQuestionList().length - 1) {
			findViewById(R.id.next_button).setBackgroundResource(R.drawable.forward_button_disabled);
			findViewById(R.id.next_button).setEnabled(false);
		}else {
			findViewById(R.id.next_button).setBackgroundResource(R.drawable.forward_button);
			findViewById(R.id.next_button).setEnabled(true);
		}
	}

	public void nextQuestion(View view) {
		quiz.setCurrentQuestion(quiz.getCurrentQuestion() + 1);
		displayQuestion();
	}

	public void previousQuestion(View view) {
		quiz.setCurrentQuestion(quiz.getCurrentQuestion() - 1);
		displayQuestion();
	}

	public void answerQuestion(View view) {
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.answergroup);
		int answerSelected = radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));
		int questionIndex = quiz.getQuestionList()[quiz.getCurrentQuestion()];
		if (quiz.getQuestions().get(questionIndex).getUsersAnswer() == -1) {
			quiz.setQuestionsAnswered(quiz.getQuestionsAnswered() + 1);
		}
		quiz.getQuestions().get(questionIndex).setUsersAnswer(answerSelected);

		final Intent intent = new Intent(this, displayCorrectOrIncorrect.class);
		intent.putExtra("parcel", quiz);
		startActivity(intent);
		
		/*if(quiz.getCurrentQuestion() != quiz.getQuestionList().length - 1) {
			nextQuestion(view);
		}*/
	}

	public void finishQuiz(View view) {
		Intent intent = new Intent(this, displayResults.class);
		intent.putExtra("parcel", quiz);
		finish();
		startActivity(intent);
	}
}
