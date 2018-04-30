package com.pdfdocumentreader.activties;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pdfdocumentreader.R;
import com.pdfdocumentreader.adapters.VerticalViewPager;
import com.pdfdocumentreader.utils.AppBaseSetGet;
import com.pdfdocumentreader.utils.AppBaseWithBackOnlyActivity;
import com.pdfdocumentreader.utils.FileModel;
import com.pdfdocumentreader.utils.PdfViewRenderer;

import java.io.IOException;

public class PdfViewerPagerActivity extends AppBaseWithBackOnlyActivity {
    MyAdapter mAdapter;
    VerticalViewPager mPager;
    static PdfViewRenderer pdfRenderer;
    FileModel fileModel;
    static int pageCount;

    @Override
    protected AppBaseSetGet getActivityData() {
        return new AppBaseSetGet(R.layout.activity_pdf_viewer_pager, "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        mPager = findViewById(R.id.viewpager);
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setToolbarTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    void setToolbarTitle(int position) {
        getSupportActionBar().setTitle(getString(R.string.app_name_with_index, position + 1, pageCount));
    }

    @Override
    public void onStop() {
        try {
            pdfRenderer.closeRenderer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onStop();
    }

    void init() {
        try {
            Bundle b = getIntent().getExtras();
            if (b != null) {
                fileModel = (FileModel) b.getSerializable("FileModel");
                pdfRenderer = new PdfViewRenderer(this, fileModel);
                pageCount = pdfRenderer.getPageCount();
                setToolbarTitle(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return pageCount;
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentOne.newInstance(position);
        }
    }

    public static class FragmentOne extends Fragment {
        private static final String MY_NUM_KEY = "num";
        private int mNum;

        // You can modify the parameters to pass in whatever you want
        static FragmentOne newInstance(int num) {
            FragmentOne f = new FragmentOne();
            Bundle args = new Bundle();
            args.putInt(MY_NUM_KEY, num);
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt(MY_NUM_KEY) : 0;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_view_pager, container, false);
            ImageView img = v.findViewById(R.id.image);
            img.setImageBitmap(pdfRenderer.showPage(mNum));
            return v;
        }
    }
}
