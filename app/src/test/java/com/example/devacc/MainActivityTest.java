package com.example.devacc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.graphics.ColorUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    public static final double MIN_CONSTRAST_RATIO = 4.5;

    @Test
    public void deve_UtilizarTaxaDeContrasteAdequada_Roboletric() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        View view = activity.findViewById(R.id.text_label);

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

                double ratio = ColorUtils.calculateContrast(backgroundColor, textColor);

                assertThat(ratio, greaterThan(MIN_CONSTRAST_RATIO));
            }
        }
    }
}
