package com.example.javiersilva.tabssample;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class MainActivity extends AppCompatActivity {

    public static final int SECTION_COUNT = 3;
    public static final int SECTION_PROFILE = 0;
    public static final int SECTION_WISH_LIST = 1;
    public static final int SECTION_FEED = 2;
    public static final int DURATION = 800;

    private ImageSwitcher imgHeader;
    private ImageView imgSectionIcon;
    private View overlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        overlay = findViewById(R.id.overlay);
        imgSectionIcon = (ImageView) findViewById(R.id.img_section_icon);
        setUpHeaderImage();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateHeaderImage(tab.getPosition());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    animateOverlay();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setTitle(null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void animateOverlay() {
        int finalRadius = (int) Math.sqrt(overlay.getWidth() * overlay.getWidth() + overlay.getHeight() * overlay.getHeight());
        final int sourceX = overlay.getWidth() / 2;
        final int sourceY = overlay.getHeight() / 2;
        if (ViewCompat.isAttachedToWindow(overlay)) {
            final Animator circularReveal = ViewAnimationUtils.createCircularReveal(overlay, sourceX, sourceY, 0, finalRadius);
            // customize the animation here
            circularReveal.setDuration(DURATION);
            circularReveal.setInterpolator(new AccelerateInterpolator());
            circularReveal.start();
        }
    }

    private void setUpHeaderImage() {
        imgHeader = (ImageSwitcher) findViewById(R.id.img_header);
        imgHeader.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                // Create a new ImageView and set it's properties
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                return imageView;
            }
        });
        // load an animation by using AnimationUtils class
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        imgHeader.setInAnimation(in);
        imgHeader.setOutAnimation(out);
        updateHeaderImage(SECTION_PROFILE);
    }

    private void updateHeaderImage(int tabPosition) {
        int background = R.drawable.header_profile;
        int icon = R.drawable.circle_profile;
        switch (tabPosition) {
            case SECTION_PROFILE:
                background = R.drawable.header_profile;
                icon = R.drawable.circle_profile;
                break;
            case SECTION_WISH_LIST:
                background = R.drawable.header_wish_list;
                icon = R.drawable.circle_wish_list;
                break;
            case SECTION_FEED:
                background = R.drawable.header_feed;
                icon = R.drawable.circle_feed;
                break;
        }
        imgHeader.setImageResource(background);
        imgSectionIcon.setImageResource(icon);
    }

    //TODO: Delete this fragment after adding the containers

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f;
            switch (position) {
                case SECTION_PROFILE:
                    //TODO: Replace by profile fragment
                    f = PlaceholderFragment.newInstance(position + 1);
                    break;
                case SECTION_WISH_LIST:
                    //TODO: Replace by wish list fragment
                    f = PlaceholderFragment.newInstance(position + 1);
                    break;
                case SECTION_FEED:
                    //TODO: Replace by feed fragment
                    f = PlaceholderFragment.newInstance(position + 1);
                    break;
                default:
                    f = PlaceholderFragment.newInstance(position + 1);
            }
            return f;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return SECTION_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            int title = R.string.app_name;
            switch (position) {
                case SECTION_PROFILE:
                    title = R.string.section_title_profile;
                    break;
                case SECTION_WISH_LIST:
                    title = R.string.section_title_wish_list;
                    break;
                case SECTION_FEED:
                    title = R.string.section_title_feed;
                    break;
            }
            return getString(title);
        }
    }
}
