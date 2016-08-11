package com.brandonhogan.accordionview_master;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.brandonhogan.accordionview.AccordionView;

public class MainActivity extends AppCompatActivity {

    private AccordionView accordionViewOne;
    private AccordionView accordionViewTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accordionViewOne = (AccordionView) findViewById(R.id.accordion_one);
        accordionViewTwo = (AccordionView) findViewById(R.id.accordion_two);

        setupAccordionOne();
        setupAccordionTwo();
    }


    private void setupAccordionOne() {
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());

        accordionViewOne.setTitle(getResources().getString(R.string.accordion_one_title));

        View areaView = inflater.inflate(R.layout.accordion_content_one, accordionViewOne, false);

        TextView builderName = (TextView) areaView.findViewById(R.id.text_view_one);
        builderName.setText(getResources().getString(R.string.accordion_one_content));

        accordionViewOne.addContentView(areaView);
    }

    private void setupAccordionTwo() {
        accordionViewTwo.setTitle(getResources().getString(R.string.accordion_two_title));

        TextView contentTwo = new TextView(getApplicationContext());
        contentTwo.setText(getResources().getString(R.string.accordion_two_content));

        accordionViewTwo.addContentView(contentTwo);
    }
}
