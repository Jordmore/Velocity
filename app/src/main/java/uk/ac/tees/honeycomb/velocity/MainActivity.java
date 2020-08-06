package uk.ac.tees.honeycomb.velocity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import uk.ac.tees.honeycomb.velocity.fragments.JourneyPlannerFragment;
import uk.ac.tees.honeycomb.velocity.fragments.MainFragment;
import uk.ac.tees.honeycomb.velocity.fragments.MapsFragment;
import uk.ac.tees.honeycomb.velocity.fragments.QRCameraFragment;
import uk.ac.tees.honeycomb.velocity.fragments.QRLibraryFragment;
import uk.ac.tees.honeycomb.velocity.fragments.StopTimetableFragment;

public class MainActivity extends AppCompatActivity {

    private final StopTimetableFragment busStop = new StopTimetableFragment();
    private final JourneyPlannerFragment journeyPlanner = new JourneyPlannerFragment();
    private final MainFragment main = new MainFragment();
    private final MapsFragment maps = new MapsFragment();
    private final QRLibraryFragment qr = new QRLibraryFragment();
    private final QRCameraFragment qrcam = new QRCameraFragment();


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavController controller = Navigation.findNavController(this,R.id.nav_host_fragment);
        BottomNavigationView menu = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(menu,controller);
        load(new MainFragment()); // Initial background fragment

        menu.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.nav_bus_stop:
                    load(busStop);
                    return true;
                case R.id.nav_journey:
                    load(journeyPlanner);
                    return true;
                case R.id.nav_home:
                    load(main);
                    return true;
                case R.id.nav_map:
                    load(maps);
                    return true;
                default:
                    return false;
            }
        });

        Toolbar toolbar = findViewById(R.id.top_app_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v -> {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isOpen()){
                drawer.close();
            } else {
                drawer.open();
            }
        });

        NavigationView navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, controller);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navView.setNavigationItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.nav_bus_stop:
                    load(busStop);
                    drawer.close();
                    return true;
                case R.id.nav_journey:
                    load(journeyPlanner);
                    drawer.close();
                    return true;
                case R.id.nav_home:
                    load(main);
                    drawer.close();
                    return true;
                case R.id.nav_map:
                    load(maps);
                    drawer.close();
                    return true;
                case R.id.nav_qr_code:
                    load(qr);
                    drawer.close();
                    return true;
                case R.id.nav_qrcam:
                    load(qrcam);
                    drawer.close();
                    return true;
                default:
                    
                    return false;
            }
        });

    }

    private void load(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.disallowAddToBackStack();
        transaction.commit();
    }

    /**
     * Checks the permission statue of the camera.
     *
     * @return false - if the permission has not been granted. Otherwise returns true.
     */
    @RequiresApi(api = Build.VERSION_CODES.M) // This doesn't work for some reason.
    public boolean requestCameraPermission()
    {
        // If check should work.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true; // Assume we have permission since we can't check ourselves.
        }
        if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 999);
            return false;
        }
        return true;
    }

}
