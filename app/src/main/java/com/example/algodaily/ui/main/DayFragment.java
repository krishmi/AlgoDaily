package com.example.algodaily.ui.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.algodaily.AlgoDatabaseHelper;
import com.example.algodaily.Algorithm;
import com.example.algodaily.R;

public class DayFragment extends Fragment {

    private final AlgoDatabaseHelper dbHelper;

    public DayFragment(AlgoDatabaseHelper db) { dbHelper = db; }

    public static DayFragment newInstance(AlgoDatabaseHelper db) { return new DayFragment(db); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_day, container, false);
        Algorithm algorithm = dbHelper.getNextAlgo();

        TextView textView = root.findViewById(R.id.algoTitle);
        textView.setText(algorithm.getCol_title());

        textView = root.findViewById(R.id.article);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( algorithm.getCol_article() ) );
                startActivity( browse );
            }
        });

        textView = root.findViewById(R.id.easyProblem);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( algorithm.getCol_easyProblem() ) );
                startActivity( browse );
            }
        });

        textView = root.findViewById(R.id.medProblem);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( algorithm.getCol_mediumProblem() ) );
                startActivity( browse );
            }
        });

        textView = root.findViewById(R.id.hardProblem);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( algorithm.getCol_hardProblem() ) );
                startActivity( browse );
            }
        });

        return root;
    }
}