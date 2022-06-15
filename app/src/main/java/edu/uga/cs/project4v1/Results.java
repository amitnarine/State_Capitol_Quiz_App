package edu.uga.cs.project4v1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Results extends AppCompatActivity {

    private TextView prompt;
    private TextView score;
    private TextView six;
    private Button homeButton;

    String text;

    /**
     * this is what creates the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        Intent intent = getIntent();
        int scoreNum = intent.getIntExtra(startQuiz.MESSAGE_TYPE,startQuiz.score);

        prompt = findViewById(R.id.yourscore);
        score = findViewById(R.id.score);
        six = findViewById(R.id.six);

        homeButton = findViewById(R.id.main);

        text = String.valueOf(scoreNum);

        score.setText(text);
        homeButton.setOnClickListener( new homeButtonClickListener() );


    }


    private class homeButtonClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(v.getContext(), MainActivity.class);

            v.getContext().startActivity(intent);

        }
    }



}
