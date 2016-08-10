package com.brandonhogan.accordionview_master;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.brandonhogan.accordionview.AccordionView;

public class MainActivity extends AppCompatActivity {

    private AccordionView accordionViewOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accordionViewOne = (AccordionView) findViewById(R.id.accordion_one);

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        setupAccordionOne(inflater);
    }


    private void setupAccordionOne(LayoutInflater inflater ) {
        accordionViewOne.setTitle(getResources().getString(R.string.accordion_one_title));

        View areaView = inflater.inflate(R.layout.accordion_content_one, accordionViewOne, false);

        TextView builderName = (TextView) areaView.findViewById(R.id.text_view_one);
        builderName.setText(getResources().getString(R.string.accordion_one_content));

//        TextView builderName2 = (TextView) areaView.findViewById(R.id.text_view_two);
//        builderName2.setText(getResources().getString(R.string.accordion_one_content));
//
//        TextView builderName3 = (TextView) areaView.findViewById(R.id.text_view_three);
//        builderName3.setText(getResources().getString(R.string.accordion_one_content));

        accordionViewOne.addContentView(areaView);
    }
}
