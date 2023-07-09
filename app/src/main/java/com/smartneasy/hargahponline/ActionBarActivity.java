package com.smartneasy.hargahponline;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public abstract class ActionBarActivity {
    protected void onCreate(Bundle savedInstanceState) {
    }

    public abstract boolean onCreateOptionsMenu(Menu menu);

    protected boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
}
