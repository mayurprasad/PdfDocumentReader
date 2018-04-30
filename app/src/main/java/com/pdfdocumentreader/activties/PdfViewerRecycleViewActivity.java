package com.pdfdocumentreader.activties;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pdfdocumentreader.R;
import com.pdfdocumentreader.adapters.PdfViewerRecycleViewAdapter;
import com.pdfdocumentreader.utils.AppBaseSetGet;
import com.pdfdocumentreader.utils.AppBaseWithBackOnlyActivity;
import com.pdfdocumentreader.utils.FileModel;
import com.pdfdocumentreader.utils.GridSpacingItemDecoration;
import com.pdfdocumentreader.utils.PdfViewRenderer;

import java.util.ArrayList;

import butterknife.BindView;

public class PdfViewerRecycleViewActivity extends AppBaseWithBackOnlyActivity implements PdfViewerRecycleViewAdapter.AdapterListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    static PdfViewRenderer pdfRenderer;
    FileModel fileModel;
    int pageCount;

    @Override
    protected AppBaseSetGet getActivityData() {
        return new AppBaseSetGet(R.layout.activity_pdf_viewer_recycle_view, "PDF");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    void init() {
        try {
            Bundle b = getIntent().getExtras();
            if (b != null) {
                fileModel = (FileModel) b.getSerializable("FileModel");
                pdfRenderer = new PdfViewRenderer(this, fileModel);
                pageCount = pdfRenderer.getPageCount();
                for (int i = 0; i < pageCount; i++) {
                    data.add(i);
                }
                prepareList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /****PREPARE DATA****/
    ArrayList<Integer> data = new ArrayList<>();
    PdfViewerRecycleViewAdapter adapter;

    //private Animator spruceAnimator;
    private void prepareList() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, GridSpacingItemDecoration.COLUMNS);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(PdfViewerRecycleViewActivity.this,
                GridSpacingItemDecoration.COLUMNS, GridSpacingItemDecoration.PADDING, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new PdfViewerRecycleViewAdapter(PdfViewerRecycleViewActivity.this, data, this, pdfRenderer);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRowClicked(int position) {
    }

    @Override
    public void onRowLongClicked(int position) {

    }
    /****PREPARE DATA****/
}
