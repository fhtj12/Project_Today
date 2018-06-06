package com.example.tirico.today;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
<<<<<<< HEAD
    public void useAppContext() {
=======
    public void useAppContext() throws Exception {
>>>>>>> 0c2324997bb13dfcd7d94adea786781d3784e45f
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.tirico.today", appContext.getPackageName());
    }
}
