package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {
    private List<String> words;
    private List<String> meanings;
    private List<Integer> indexes;
    private int currentIndex = 0;
    private int score = 0;
    private Handler handler = new Handler();
    private Map<String, Integer> wordToImageMap;
    private List<String> wrongWords;
    private List<String> correctMeanings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        words = new ArrayList<>();
        meanings = new ArrayList<>();
        indexes = new ArrayList<>();
        wordToImageMap = new HashMap<>();
        wrongWords = new ArrayList<>();  // Initialize wrongWords list
        correctMeanings = new ArrayList<>();  // Initialize correctMeanings list

        // LearnActivity에서 단어 리스트를 가져옴
        words.add("apple");
        words.add("banana");
        words.add("orange");
        words.add("plum");
        words.add("grape");
        words.add("peach");
        words.add("watermelon");
        words.add("strawberry");
        words.add("tangerine");
        words.add("persimmon");

        // 각 단어의 의미 리스트 초기화
        meanings.add("사과");
        meanings.add("바나나");
        meanings.add("오렌지");
        meanings.add("자두");
        meanings.add("포도");
        meanings.add("복숭아");
        meanings.add("수박");
        meanings.add("딸기");
        meanings.add("귤");
        meanings.add("곶감");

        // 단어와 이미지 리소스를 매핑
        wordToImageMap.put("apple", R.drawable.apple);
        wordToImageMap.put("banana", R.drawable.banana);
        wordToImageMap.put("orange", R.drawable.orange);
        wordToImageMap.put("plum", R.drawable.plum);
        wordToImageMap.put("grape", R.drawable.grape);
        wordToImageMap.put("peach", R.drawable.peach);
        wordToImageMap.put("watermelon", R.drawable.watermelon);
        wordToImageMap.put("strawberry", R.drawable.strawberry);
        wordToImageMap.put("tangerine", R.drawable.tangerine);
        wordToImageMap.put("persimmon", R.drawable.persimmon);

        // 단어 리스트의 순서를 섞음
        for (int i = 0; i < words.size(); i++) {
            indexes.add(i);
        }
        Collections.shuffle(indexes);

        displayWord();

        Button backToMainButton = findViewById(R.id.backToMainButton);
        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료
            }
        });

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

        Switch hintSwitch = findViewById(R.id.hintSwitch);
        hintSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> toggleHint(isChecked));

        Button showResultButton = findViewById(R.id.showResultButton);
        showResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResult();
            }
        });
    }

    private void displayWord() {
        TextView wordTextView = findViewById(R.id.wordTextView);
        TextView progressTextView = findViewById(R.id.progressTextView);
        ImageView hintImageView = findViewById(R.id.hintImageView);
        hintImageView.setVisibility(View.GONE);
        if (currentIndex < words.size()) {
            int index = indexes.get(currentIndex);
            wordTextView.setText(words.get(index));
            progressTextView.setText("(" + (currentIndex + 1) + "/" + words.size() + ")");
        }
    }

    private boolean isLastQuestion() {
        return currentIndex >= words.size();
    }

    private void checkAnswer() {
        EditText answerEditText = findViewById(R.id.answerTextView); // EditText로 변경
        String answer = answerEditText.getText().toString().trim();

        if (currentIndex < words.size()) {
            int index = indexes.get(currentIndex);

            if (index < meanings.size()) {
                if (answer.equalsIgnoreCase(meanings.get(index))) {
                    Toast.makeText(this, "정답입니다!", Toast.LENGTH_SHORT).show();
                    score++;
                } else {
                    Toast.makeText(this, "오답입니다!", Toast.LENGTH_SHORT).show();
                    wrongWords.add(words.get(index));
                    correctMeanings.add(meanings.get(index));
                }
            }

            currentIndex++;
            answerEditText.setText(""); // 입력창 초기화
            displayWord();

            if (isLastQuestion()) {
                // 모든 문제를 다 풀었을 때 결과창 버튼을 보이도록 설정
                Button showResultButton = findViewById(R.id.showResultButton);
                showResultButton.setVisibility(View.VISIBLE);

                // 다른 버튼들의 가시성을 숨김
                Button submitButton = findViewById(R.id.submitButton);
                submitButton.setVisibility(View.GONE);
                answerEditText.setVisibility(View.GONE);
                TextView wordTextView = findViewById(R.id.wordTextView);
                wordTextView.setVisibility(View.GONE);
                Switch hintSwitch = findViewById(R.id.hintSwitch);
                hintSwitch.setVisibility(View.GONE);
            } else {
                // 5초 후에 다음 문제 출력
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        displayWord();
                    }
                }, 5000); // 5초 지연
            }
        }
    }

    private void toggleHint(boolean show) {
        ImageView hintImageView = findViewById(R.id.hintImageView);
        if (show) {
            if (currentIndex < words.size()) {
                int index = indexes.get(currentIndex);
                String word = words.get(index);
                Integer imageResId = wordToImageMap.get(word);
                if (imageResId != null) {
                    hintImageView.setImageResource(imageResId);
                    hintImageView.setVisibility(View.VISIBLE);
                }
            }
        } else {
            hintImageView.setVisibility(View.GONE);
        }
    }

    private void showResult() {
        Intent resultIntent = new Intent(QuizActivity.this, ResultActivity.class);
        resultIntent.putExtra("totalQuestions", words.size());
        resultIntent.putExtra("correctAnswers", score);

        // 틀린 문제와 정답 리스트 전달
        resultIntent.putStringArrayListExtra("wrongWords", new ArrayList<>(wrongWords));
        resultIntent.putStringArrayListExtra("correctMeanings", new ArrayList<>(correctMeanings));

        startActivity(resultIntent);
        finish(); // 현재 액티비티 종료
    }
}