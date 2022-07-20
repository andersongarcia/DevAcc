package com.example.devacc;

import static com.google.android.apps.common.testing.accessibility.framework.AccessibilityCheckResultUtils.matchesViews;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;

import androidx.test.espresso.accessibility.AccessibilityChecks;

import org.hamcrest.Matcher;

import java.util.List;

/**
 * util class defined to enable accessibility checks in UI tests.
 */
public class AccessibilityTestUtil {
    /**
     * Invoke this method to run all accessibility rules for all views starting from root view
     */
    public static void enableAllChecks() {
        AccessibilityChecks.enable().setRunChecksFromRootView(true);
    }

    /**
     * Invoke this method to run accessibility rules
     *
     * @param viewIds list of views that will be skipped from any accessibility rules checks.
     *                use -1 for views without specified id.
     */
    public static void enableChecksWithSuppressedViews(List viewIds) {
        Matcher accessibilityCheckResultMatcher =
                is(anyOf(matchesViews(isIn(viewIds.toArray()))));

        AccessibilityChecks.enable().setRunChecksFromRootView(true)
                .setSuppressingResultMatcher(accessibilityCheckResultMatcher);
    }

    /**
     * Invoke this method to run accessibility rules  with exceptions defined in matcher
     * viewCheckResultMatcher for instance an example Matcher could be of the form ,
     * allOf( matchesCheckNames(is("TouchTargetSizeViewCheck")), matchesViews(withId(R.id.seller_primary_phone)))
     * all issues related to touch target on View objects with a resource ID of "seller_primary_phone" are suppressed
     *
     * @param viewCheckResultMatcher matcher defined
     */
    public static void enableChecksWithSuppressedRulesForViews(
            Matcher viewCheckResultMatcher) {
        AccessibilityChecks.enable().setRunChecksFromRootView(true).setSuppressingResultMatcher(viewCheckResultMatcher);
    }
}