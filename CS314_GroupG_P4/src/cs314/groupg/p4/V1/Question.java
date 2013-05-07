package cs314.groupg.p4.V1;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable{
	private String question;
	private String answer1;
	private String answer2;
	private String answer3;
	private String answer4;
	private int correctAnswer;
	private int usersAnswer;

	public Question(String questionFromFile, String answer1, String answer2, String answer3, String answer4, int correctAnswer) {

		this.setQuestion(questionFromFile);
		this.setAnswer1(answer1);
		this.setAnswer2(answer2);
		this.setAnswer3(answer3);
		this.setAnswer4(answer4);
		this.setCorrectAnswer(correctAnswer);
		this.setUsersAnswer(-1);
	}



	public Question(Parcel source) {
		this.setQuestion(source.readString());
		this.setAnswer1(source.readString());
		this.setAnswer2(source.readString());
		this.setAnswer3(source.readString());
		this.setAnswer4(source.readString());
		this.setCorrectAnswer(source.readInt());
		this.setUsersAnswer(source.readInt());
	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the answer1
	 */
	public String getAnswer1() {
		return answer1;
	}

	/**
	 * @param answer1 the answer1 to set
	 */
	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	/**
	 * @return the answer2
	 */
	public String getAnswer2() {
		return answer2;
	}

	/**
	 * @param answer2 the answer2 to set
	 */
	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	/**
	 * @return the answer3
	 */
	public String getAnswer3() {
		return answer3;
	}

	/**
	 * @param answer3 the answer3 to set
	 */
	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	/**
	 * @return the answer4
	 */
	public String getAnswer4() {
		return answer4;
	}

	/**
	 * @param answer4 the answer4 to set
	 */
	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}

	/**
	 * @return the correctAnswer
	 */
	public int getCorrectAnswer() {
		return correctAnswer;
	}

	/**
	 * @param correctAnswer the correctAnswer to set
	 */
	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
	public void setUsersAnswer(int i) {
		this.usersAnswer = i;
	}
	
	public int getUsersAnswer() {
		return usersAnswer;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(question);
		dest.writeString(answer1);
		dest.writeString(answer2);
		dest.writeString(answer3);
		dest.writeString(answer4);
		dest.writeInt(correctAnswer);
		dest.writeInt(usersAnswer);
	}

	public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {

		@Override
		public Question createFromParcel(Parcel source) {
			return new Question(source);
		}

		@Override
		public Question[] newArray(int size) {
			return new Question[size];
		}
	};

}
