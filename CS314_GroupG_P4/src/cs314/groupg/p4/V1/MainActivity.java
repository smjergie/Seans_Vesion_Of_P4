package cs314.groupg.p4.V1;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/** Called when the user clicks the start quiz button */
	public void startQuiz(View view) {
		Intent intent = new Intent(this, displayQuiz.class);
		AssetManager am = this.getAssets();
		try {
			InputStream FileStream = am.open("P4 Questions.txt");
			Quiz quiz = new Quiz(FileStream);
			intent.putExtra("parcel", quiz);
			finish();
			startActivity(intent);
		} catch (IOException e) {
			System.exit(0);
		}

	}
	
}
