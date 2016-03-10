package com.example.mipo;

import android.app.Application;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;


@ReportsCrashes(formUri = "https://collector.tracepot.com/b30094f1")
public class Mipo extends Application {
    @Override
    public void onCreate() {
        super.onCreate ();
        ACRA.init (this);
    }
}