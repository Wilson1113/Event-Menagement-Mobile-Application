package com.fit2081.assignment3.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GestureDetectorCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.fit2081.assignment3.R;
import com.fit2081.assignment3.Util;
import com.fit2081.assignment3.categories.Category;
import com.fit2081.assignment3.categories.FragmentListCategory;
import com.fit2081.assignment3.categories.ListCategoryActivity;
import com.fit2081.assignment3.categories.NewEventCategory;
import com.fit2081.assignment3.provider.ViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.regex.Pattern;

public class Dashboard extends AppCompatActivity {

    private Intent intentToAddEventCategory;
    private Intent intentToListCategory;
    private Intent intentToListEvent;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private EditText tvEventId;
    private EditText tvName;
    private EditText tvCategoryId;
    private EditText tvTicketsAvailable;
    private TextView tv_gesture;
    private View tvView;
    private Switch switchActive;
    private FragmentListCategory fragmentCategory;
    private ViewModel viewModel;
    private String[] last;

    private FloatingActionButton fab;

    private List<Category> categories;
    private List<Event> events;

    // help detect basic gestures like scroll, single tap, double tap, etc
    private GestureDetectorCompat mDetector;

    // help detect multi touch gesture like pinch-in, pinch-out specifically used for zoom functionality
    private ScaleGestureDetector mScaleDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        intentToAddEventCategory = new Intent(this, NewEventCategory.class);
        intentToListCategory = new Intent(this, ListCategoryActivity.class);
        intentToListEvent = new Intent(this, ListEventActivity.class);


        tvEventId = findViewById(R.id.editEventId);
        tvName = findViewById(R.id.editEventName);
        tvCategoryId = findViewById(R.id.editCategoryId2);
        tvTicketsAvailable = findViewById(R.id.editTicketsAvailable);
        tv_gesture = findViewById(R.id.tv_gesture);

        fab = findViewById(R.id.fab);

        switchActive = findViewById(R.id.switchIsActive2);

        tvView = findViewById(R.id.view);
        tvView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return true;
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        fragmentCategory = new FragmentListCategory();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragmentCategory, fragmentCategory);
        transaction.commit();

        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        viewModel.getAllCategories().observe(this, newData -> {
            categories = newData;
        });
        viewModel.getAllEvents().observe(this, newData -> {
            events = newData;
        });

        // initialise new instance of CustomGestureDetector class
        CustomGestureDetector customGestureDetector = new CustomGestureDetector();

        // register GestureDetector and set listener as CustomGestureDetector
        mDetector = new GestureDetectorCompat(this, customGestureDetector);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clear_event_form) {
            tvEventId.setText("");
            tvName.setText("");
            tvCategoryId.setText("");
            tvTicketsAvailable.setText("");
            switchActive.setChecked(false);
        } else if (id == R.id.delete_all_categories) {
            viewModel.deleteAllCategories();
        } else if (id == R.id.delete_all_events) {
            for (Event event : events) {
                viewModel.minusCount(event.getCategoryId());
            }
            viewModel.deleteAllEvents();
        }
        return true;
    }

    public void saveEventOnClick(View view) {

        String name = tvName.getText().toString();
        String categoryId = tvCategoryId.getText().toString();

        String ticketsAvailableStr = tvTicketsAvailable.getText().toString();
        int ticketsAvailable = ticketsAvailableStr.isEmpty() ? 0 : Integer.parseInt(ticketsAvailableStr);



        boolean isActive = switchActive.isChecked();
        if (name.isEmpty()) {
            Toast.makeText(this, "Event Name must be filled!", Toast.LENGTH_SHORT).show();
        } else if (!Pattern.matches("^[a-zA-z]([a-zA-Z0-9]|\\s)*", name)) {
            Toast.makeText(this, "Invalid event name", Toast.LENGTH_SHORT).show();
        } else if (!check(categoryId)) {
            Toast.makeText(this, "Invalid Category Id!", Toast.LENGTH_SHORT).show();
        } else {

            if (ticketsAvailable < 0) {
                Toast.makeText(this, "Invalid 'Tickets Available'", Toast.LENGTH_SHORT).show();
                ticketsAvailable = 0;
            }
            String eventId = Util.generateEventId();

            tvEventId.setText(eventId);
            
            viewModel.insert(new Event(eventId, categoryId, name, ticketsAvailable, isActive));
            viewModel.addCount(categoryId);
            last = new String[]{categoryId, eventId};

            Snackbar.make(view, "Event saved: " + eventId + " to " + categoryId, Snackbar.LENGTH_LONG).setAction("Undo", undoOnClickListener).show();
        }
    }

    View.OnClickListener undoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (last != null) {
                viewModel.minusCount(last[0]);
                viewModel.deleteEventById(last[1]);
                last = null;
            }
        }
    };

    public boolean check(String categoryId) {
        for (Category category : categories) {
            if (category.getCategoryId().equals(categoryId)) {
                return true;
            }
        }
        return false;
    }

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // get the id of the selected item
            int id = item.getItemId();

            if (id == R.id.view_all_categories) {
                startActivity(intentToListCategory);
            } else if (id == R.id.add_categories) {
                startActivity(intentToAddEventCategory);
            } else if (id == R.id.view_all_events) {
                startActivity(intentToListEvent);
            } else if (id == R.id.logout) {
                finish();
            }
            // close the drawer
            drawerLayout.closeDrawers();
            // tell the OS
            return true;
        }
    }

    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            saveEventOnClick(fab);
            tv_gesture.setText("onDoubleTap");
            return true;
        }

        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            tvEventId.setText("");
            tvName.setText("");
            tvCategoryId.setText("");
            tvTicketsAvailable.setText("");
            switchActive.setChecked(false);
            tv_gesture.setText("onLongPress");
        }
    }
}