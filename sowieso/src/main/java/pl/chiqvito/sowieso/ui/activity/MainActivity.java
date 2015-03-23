package pl.chiqvito.sowieso.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.bus.events.CategoryOperationEvent;
import pl.chiqvito.sowieso.ui.fragment.FragmentBuilder;
import pl.chiqvito.sowieso.ui.fragment.FragmentWrapper;
import pl.chiqvito.sowieso.ui.fragment.NavigationDrawerFragment;


public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, MainActivityCallback {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        mNavigationDrawerFragment.setTitle(getTitle());
    }

    @Override
    public void onNavigationDrawerItemSelected(Fragment fragment) {
        currentFragment = fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onSectionAttached(FragmentBuilder.FragmentName fn) {
        mNavigationDrawerFragment.setTitle(FragmentBuilder.title(this, fn));
        mNavigationDrawerFragment.setFragmentName(fn);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            EventBus.getDefault().post(new CategoryOperationEvent(CategoryOperationEvent.DOWNLOAD, null));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void save() {
        if (currentFragment instanceof FragmentWrapper)
            ((FragmentWrapper) currentFragment).save();
    }

    @Override
    public void refresh() {
        if (currentFragment instanceof FragmentWrapper)
            ((FragmentWrapper) currentFragment).refresh();
    }
}
