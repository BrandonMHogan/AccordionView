package com.brandonhogan.accordionview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AccordionView extends FrameLayout implements Animation.AnimationListener {


    // Private Static Properties
    private static int ANIMATION_DURATION = 500;


    // Private Properties

    private LinearLayout expandableSection;
    private FrameLayout container;
    private RelativeLayout titleSectionLayout;
    private TextView titleView;
    private ImageView expandIcon;
    private boolean isExpanded;
    private boolean hasBeenClicked = false;
    private int animationDuration = ANIMATION_DURATION;
    private ColorStateList originalTitleColor;
    private int highlightBackgroundColor;
    private int highlightTextColor;
    private int height;


    // Constructors

    public AccordionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        customProperties(context, attrs, 0);
        initControl();
    }

    public AccordionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        customProperties(context, attrs, defStyleAttr);
        initControl();
    }

    public AccordionView(Context context) {
        super(context);
        initControl();
    }


    // Private Functions

    private void customProperties(Context context, AttributeSet attrs, int defStyleAttr) {
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AccordionView, defStyleAttr, 0);
//
//        highlightBackgroundColor = a.getInt(R.styleable.AccordionView_title_highlight_background, 0);
//        highlightTextColor = a.getInt(R.styleable.AccordionView_title_highlight_font_color, 0);

    }

    private void initControl() {
        inflate(getContext(), R.layout.accordion_view, this);

        titleSectionLayout = (RelativeLayout) findViewById(R.id.title_section);
        titleView = (TextView) findViewById(R.id.title);
        expandIcon = (ImageView) findViewById(R.id.expand_icon);
        expandableSection = (LinearLayout) findViewById(R.id.expandable_view);

        expandIcon.animate().rotation(180).setDuration(animationDuration).start();
        originalTitleColor =  titleView.getTextColors();

        expandableSection.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener(){

                    @Override
                    public void onGlobalLayout() {
                        height = expandableSection.getHeight();

                        expandableSection.getViewTreeObserver().removeGlobalOnLayoutListener( this );
                        expandableSection.setVisibility( View.GONE );
                    }

                });

        setOnClick();
    }

    private void setHighlight(boolean isHighlighted) {
        if (isHighlighted) {

            if(highlightBackgroundColor > 0)
                titleSectionLayout.setBackgroundColor(getResources().getColor(highlightBackgroundColor));

            if (highlightTextColor > 0)
                titleView.setTextColor(getResources().getColor(highlightTextColor));
        }
        else {
            titleSectionLayout.setBackgroundColor(0x00000000);
            titleView.setTextColor(originalTitleColor);
        }
    }

    private void setOnClick() {
        titleSectionLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
    }

    private void clearOnClick() {
        titleSectionLayout.setOnClickListener(null);
    }


    // Public Functions

    public void setTitle(String title) {
        titleView.setText(title);
    }

    public void toggle(){

        if(expandableSection.getVisibility() == View.VISIBLE){
            AccordionAnimation a = new AccordionAnimation(expandableSection, animationDuration, AccordionAnimation.COLLAPSE);
            height = a.getHeight();
            a.setAnimationListener(this);
            expandableSection.startAnimation(a);
            expandIcon.animate().rotation(180).setDuration(animationDuration).start();
            setHighlight(false);
        }else{
            AccordionAnimation a = new AccordionAnimation(expandableSection, animationDuration, AccordionAnimation.EXPAND);
            a.setHeight(height);
            a.setAnimationListener(this);
            expandableSection.requestLayout();
            expandableSection.startAnimation(a);
            expandIcon.animate().rotation(0).setDuration(animationDuration).start();
            setHighlight(true);
        }
    }

    /*
        Adds the passed in view to the expanding view
     */
    public void addContentView(View newContentView) {
        expandableSection.addView(newContentView);
        expandableSection.invalidate();
    }

    /*
        Clears the passed in view from the exandping view
     */
    public void clearContentView(View contentView) {
        expandableSection.removeView(contentView);
    }

    /*
        Clears all content inside the expanding view
     */
    public void clearContent() {
        expandableSection.removeAllViews();
    }

    /*
        Change the speed in which the view will animate open and close
     */
    public void setAnimationDuration(int duration) {
        animationDuration = duration;
    }


    // Click Event Overrides

    @Override
    public void onAnimationStart(Animation animation) {
        clearOnClick();
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        setOnClick();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
