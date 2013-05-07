package cs314.groupg.p4.V1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import android.os.Parcel;
import android.os.Parcelable;

public class Quiz implements Parcelable {


	Quiz(InputStream fs) {

		//initialize the question list
		questions = new ArrayList<Question>();
		//get questions from the question file
		try {
			getQestionsFromFile(fs);
		} catch (IOException e) {
			System.out.println("Error: Could not read file;");
		}
		//get 10 random numbers to chose form list later
		questionList = new int[10];
		for (int i = 0; i < 10; i++) {
			Random rand = new Random();
			while(true) {
				int random = rand.nextInt(questions.size() - 1);
				//if (!questionList.contains(random)) {
				if (!Arrays.asList(questionList).contains(random)) {
					questionList[i] = random;
					break;
				}
			}
		}
		questionsAnswered = 0;
		questionsCorrect = 0;
		currentQuestion = 0;
		beginQuiz();
	}

	Quiz(Parcel source) {
		this.questions = source.createTypedArrayList(Question.CREATOR);
		this.questionList = source.createIntArray();
		this.questionsAnswered = source.readInt();
		this.questionsCorrect = source.readInt();
		this.currentQuestion = source.readInt();
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

	public int[] getQuestionList() {
		return questionList;
	}

	public void setQuestionList(int[] questionList) {
		this.questionList = questionList;
	}

	public int getQuestionsAnswered() {
		return questionsAnswered;
	}

	public void setQuestionsAnswered(int questionsAnswered) {
		this.questionsAnswered = questionsAnswered;
	}

	public int getQuestionsCorrect() {
		return questionsCorrect;
	}

	public void setQuestionsCorrect(int questionsCorrect) {
		this.questionsCorrect = questionsCorrect;
	}
	private ArrayList<Question> questions;
	private int[] questionList;
	private int questionsAnswered;
	private int questionsCorrect;
	private int currentQuestion;

	public int getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(int currentQuestion) {
		this.currentQuestion = currentQuestion;
	}

	private void beginQuiz() {
	}
	
	public void gradeQuiz(){
		for (int i = 0; i < questionList.length; i++) {
			Question question = getQuestions().get(questionList[i]);
			if (question.getCorrectAnswer() == question.getUsersAnswer()) {
				questionsCorrect++;
			}
		}
	}

	private void getQestionsFromFile(InputStream fs) throws IOException {
		InputStreamReader reader = new InputStreamReader(fs);
		BufferedReader br = new BufferedReader(reader);
		String questionFromFile;
		while ((questionFromFile = br.readLine()) != null) {
			questionFromFile = questionFromFile.substring(3);
			String answer1 = br.readLine();
			String answer2 = br.readLine();
			String answer3 = br.readLine();
			String answer4 = br.readLine();
			int correctAnswer = Integer.parseInt(br.readLine());
			questions.add(new Question(questionFromFile, answer1, answer2, answer3, answer4, correctAnswer));
		}
		br.close();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedList(questions);
		dest.writeIntArray(questionList);
		dest.writeInt(questionsAnswered);
		dest.writeInt(questionsCorrect);
		dest.writeInt(currentQuestion);
	}
	public static final Parcelable.Creator<Quiz> CREATOR = new Parcelable.Creator<Quiz>() {

		@Override
		public Quiz createFromParcel(Parcel in) {
			return new Quiz(in);
		}

		@Override
		public Quiz[] newArray(int size) {
			return new Quiz[size];
		}
	};
}
