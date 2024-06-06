package com.example.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class LearnActivity extends AppCompatActivity {
    private List<String> words;
    private List<String> meanings;
    private List<String> images;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        words = new ArrayList<>();
        meanings = new ArrayList<>();
        images = new ArrayList<>();

        // 단어, 의미, 이미지를 리스트에 추가
        addWord("apple", "사과", "apple");
        addWord("banana", "바나나", "banana");
        addWord("orange","오렌지","orange");
        addWord("plum","자두","plum");
        addWord("grape","포도","grape");
        addWord("peach","복숭아","peach");
        addWord("watermelon","수박","watermelon");
        addWord("strawberry","딸기","strawberry");
        addWord("tangerine","귤","tangerine");
        addWord("persimmon","곶감","persimmon");
        // 나머지 단어도 추가...

        displayWord();

        Button nextButton = findViewById(R.id.nextButton);
        Button prevButton = findViewById(R.id.prevButton);
        Button backToMainButton = findViewById(R.id.backToMainButton);
        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextWord();
            }
        });
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPrevWord();
            }
        });
    }

    private void showPrevWord() {
        if (currentIndex > 0) {
            currentIndex--;
            displayWord();
        }
    }

    private void addWord(String word, String meaning, String image) {
        words.add(word);
        meanings.add(meaning);
        images.add(image);
    }

    private void displayWord() {
        TextView wordTextView = findViewById(R.id.wordTextView);
        TextView meaningTextView = findViewById(R.id.meaningTextView);
        ImageView imageView = findViewById(R.id.imageView); // 이미지뷰 추가

        if (currentIndex < words.size()) {
            wordTextView.setText(words.get(currentIndex));
            meaningTextView.setText(meanings.get(currentIndex));

            // 이미지뷰에 이미지 설정

            String imageName = images.get(currentIndex);
            int imageResource = getResources().getIdentifier(imageName, "drawable", getPackageName());
            imageView.setImageResource(imageResource);
        }
    }

    private void showNextWord() {
        if (currentIndex < words.size() - 1) {
            currentIndex++;
            displayWord();
        } else {
            Button nextButton = findViewById(R.id.nextButton);
            nextButton.setVisibility(View.GONE); // 다음 버튼 숨기기
        }
    }


}