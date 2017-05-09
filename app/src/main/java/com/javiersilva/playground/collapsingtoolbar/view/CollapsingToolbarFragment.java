package com.javiersilva.playground.collapsingtoolbar.view;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import android.widget.ViewSwitcher;

import com.javiersilva.playground.R;
import com.javiersilva.playground.collapsingtoolbar.LongRunningObservableFactory;
import com.javiersilva.playground.collapsingtoolbar.model.Owner;
import com.javiersilva.playground.common.Constants;
import com.javiersilva.playground.common.view.PlaceholderFragment;
import com.javiersilva.playground.di.DaggerPlaygroundComponent;
import com.javiersilva.playground.di.PlaygroundComponent;
import com.javiersilva.playground.navigationdrawer.view.NavigationFragment;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CollapsingToolbarFragment extends NavigationFragment {

    public static final int SECTION_COUNT = 3;
    public static final int SECTION_PROFILE = 0;
    public static final int SECTION_WISH_LIST = 1;
    public static final int SECTION_FEED = 2;
    public static final int DURATION = 800;

    private ImageSwitcher imgHeader;
    private ImageView imgSectionIcon;
    private View overlay;

    private LongRunningObservableFactory factory;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.collapsing_toolbar_fragment, container, false);

        /*
          There are different ways to initialize a component/module:
          1 - If the module has a default constructor we can call .create() directly:
               PlaygroundComponent component = DaggerPlaygroundComponent.create();
          2 - If not we need specify how the module is created using a builder:
               PlaygroundComponent component = DaggerPlaygroundComponent.builder()
                   .playgroundModule(new PlaygroundModule(PARAMS)).build()
          3 - Finally, if ALL the methods are static, we do not need an instance of the module, so
              dagger will mark the module method (playgroundModule()) as deprecated since no instance
              is needed and we can use create()
               PlaygroundComponent component = DaggerPlaygroundComponent.create();
          the main difference between 1 and 3 is the code generated for the module in which 3 is
          preferred since is more efficient
         */
        PlaygroundComponent component = DaggerPlaygroundComponent.create();
        Owner owner = component.getOwner();
        factory = component.getLongRunningObservableFactory();
        owner.instructSomething();

        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        setUpToolbar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager viewPager = (ViewPager) root.findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        overlay = root.findViewById(R.id.overlay);
        imgSectionIcon = (ImageView) root.findViewById(R.id.img_section_icon);
        setUpHeaderImage(root);
        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tabs);
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

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab_init);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<String> observable = factory.start();
                Disposable disposable = observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String value) throws Exception {
                                Log.d(Constants.TAG, "New message: " + value);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e(Constants.TAG, "Error: " + throwable.getMessage());
                            }
                        });
                disposables.add(disposable);
            }
        });
        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(Constants.TAG, "onPause() called");
        if (disposables != null && !disposables.isDisposed()) {
            Log.d(Constants.TAG, "dispose: ");
            disposables.dispose();
        }
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

    private void setUpHeaderImage(View root) {
        imgHeader = (ImageSwitcher) root.findViewById(R.id.img_header);
        imgHeader.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                // Create a new ImageView and set it's properties
                ImageView imageView = new ImageView(getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                return imageView;
            }
        });
        // load an animation by using AnimationUtils class
        Animation in = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);
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
            return PlaceholderFragment.newInstance(R.string.waiting);
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
