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
    private RelativeLayout titleSectionLayout;
    private TextView titleView;
    private ImageView expandIcon;

    private int animationDuration = ANIMATION_DURATION;
    private ColorStateList originalTitleColor;
    private ColorStateList titleBackgroundHighlight;
    private ColorStateList titleColorHighlight;
    private int height;


    // Constructors

    public AccordionView(Context context) {
        this(context, null);
    }

    public AccordionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AccordionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(attrs);
    }


    // Private Functions

    private void initControl(AttributeSet attrs) {

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.AccordionView);

        if (typedArray != null) {
            titleBackgroundHighlight = typedArray.getColorStateList(R.styleable.AccordionView_titleBackgroundHighlight);
            titleColorHighlight = typedArray.getColorStateList(R.styleable.AccordionView_titleColorHighlight);
        }

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
        typedArray.recycle();
    }

    private void setHighlight(boolean isHighlighted) {

        if (isHighlighted) {

            if(titleBackgroundHighlight != null)
                titleSectionLayout.setBackgroundColor(titleBackgroundHighlight.getDefaultColor());

            if (titleColorHighlight != null)
                titleView.setTextColor(titleColorHighlight);
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
            AccordionAnimation animation =
                    new AccordionAnimation(expandableSection, animationDuration, AccordionAnimation.COLLAPSE);
            height = animation.getHeight();
            animation.setAnimationListener(this);
            expandableSection.startAnimation(animation);
            expandIcon.animate().rotation(180).setDuration(animationDuration).start();
            setHighlight(false);
        }else{
            AccordionAnimation animation = new AccordionAnimation(expandableSection, animationDuration, AccordionAnimation.EXPAND);
            animation.setHeight(height);
            animation.setAnimationListener(this);
            expandableSection.requestLayout();
            expandableSection.startAnimation(animation);
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
