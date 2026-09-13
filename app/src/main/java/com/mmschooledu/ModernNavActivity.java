package com.mmschooledu;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ModernNavActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modern_nav);

        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Setup ViewPager
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false); // Disable swipe gesture if needed

        // Handle navigation between fragments
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                viewPager.setCurrentItem(0);
            } else if (itemId == R.id.navigation_favourite) {
                viewPager.setCurrentItem(1);
            } else if (itemId == R.id.navigation_setting) {
                viewPager.setCurrentItem(2);
            } else if (itemId == R.id.navigation_setting) {
                viewPager.setCurrentItem(3);
            } else if (itemId == R.id.navigation_about) {
                viewPager.setCurrentItem(4);
            }
            return true;
        });

        // Sync ViewPager with BottomNavigationView
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.navigation_favourite);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.navigation_setting);
                        break;
                    case 3:
                        bottomNavigationView.setSelectedItemId(R.id.navigation_setting);
                        break;
                    case 4:
                        bottomNavigationView.setSelectedItemId(R.id.navigation_about);
                        break;
                }
            }
        });
    }
}