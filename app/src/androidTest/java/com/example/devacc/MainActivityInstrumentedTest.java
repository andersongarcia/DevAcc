package com.example.devacc;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.AllOf.allOf;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.graphics.ColorUtils;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Text;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityInstrumentedTest {
    public static final double MIN_CONSTRAST_RATIO = 4.5;

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void deve_EstarVisivel_Textview() {
        ViewInteraction textView = onView(withId(R.id.text_label));
        textView.check(matches(isDisplayed()));
    }

    @Test
    public void deve_UtilizarTaxaDeContrasteAdequada_Espresso(){
        onView(withId(R.id.text_label))
                .check(matches(utilizaTaxaDeContrasteAdequada(MIN_CONSTRAST_RATIO)));
    }

    Matcher<View> utilizaTaxaDeContrasteAdequada(final double ratio) {

        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("Utiliza taxa de contraste maior que " + ratio);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof TextView) && !(view instanceof EditText)) {
                    return false;
                }
                if (view != null) {
                    if (view.getBackground() instanceof ColorDrawable) {
                        ColorDrawable cd = (ColorDrawable) view.getBackground();

                        int backgroundColor = cd.getColor();

                        int textColor;
                        if (view instanceof TextView) {
                            textColor = ((TextView) view).getCurrentTextColor();
                        } else {
                            textColor = ((EditText) view).getCurrentTextColor();
                        }

                        double r = ColorUtils.calculateContrast(backgroundColor, textColor);

                        return r >= ratio;
                    }
                }
                return false;
            }
        };
    }
}
