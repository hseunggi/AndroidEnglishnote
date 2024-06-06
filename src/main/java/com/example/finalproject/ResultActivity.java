package com.example.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Button backToMainButton = findViewById(R.id.backToMainButton);
        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료
            }
        });

        // QuizActivity에서 넘어온 데이터 받기
        Bundle extras = getIntent().getExtras();
        int totalQuestions = extras.getInt("totalQuestions");
        int correctAnswers = extras.getInt("correctAnswers");

        // 틀린 문제와 정답 리스트 받기
        ArrayList<String> wrongWords = extras.getStringArrayList("wrongWords");
        ArrayList<String> correctMeanings = extras.getStringArrayList("correctMeanings");

        // 결과 출력
        TextView resultTextView = findViewById(R.id.resultTextView);
        resultTextView.setText("총 문제 수: " + totalQuestions + "\n"
                + "맞은 문제 수: " + correctAnswers + "\n\n"
                + "틀린 문제와 정답:\n");

        TextView wrongAnswersTextView = findViewById(R.id.wrongAnswersTextView);
        StringBuilder wrongAnswersText = new StringBuilder();
        for (int i = 0; i < wrongWords.size(); i++) {
            wrongAnswersText.append("문제: ").append(wrongWords.get(i)).append("\n");
            wrongAnswersText.append("정답: ").append(correctMeanings.get(i)).append("\n\n");
        }
        wrongAnswersTextView.setText(wrongAnswersText.toString());
    }
}