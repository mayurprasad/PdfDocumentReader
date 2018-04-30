package com.pdfdocumentreader.activties;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pdfdocumentreader.R;
import com.pdfdocumentreader.adapters.PdfListAdapter;
import com.pdfdocumentreader.utils.FileModel;
import com.pdfdocumentreader.utils.GridSpacingItemDecoration;
import com.pdfdocumentreader.utils.UtilsFunction;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PdfListActivity extends AppCompatActivity implements PdfListAdapter.AdapterListener,
        SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    File sdcardObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_list);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prepareList();
        setUpRefresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_type) {
            CreateAlertDialogWithRadioButtonGroup();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /****PREPARE DATA****/
    ArrayList<FileModel> fileModels = new ArrayList<>();
    PdfListAdapter adapter;

    //private Animator spruceAnimator;
    private void prepareList() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, GridSpacingItemDecoration.COLUMNS);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(PdfListActivity.this,
                GridSpacingItemDecoration.COLUMNS, GridSpacingItemDecoration.PADDING, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new PdfListAdapter(PdfListActivity.this, fileModels, this);
        recyclerView.setAdapter(adapter);

        sdcardObj = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    @Override
    public void onRowClicked(int position) {
        Intent intent;
        switch (selection) {
            case 0:
                intent = new Intent(PdfListActivity.this, PdfViewerRecycleViewActivity.class);
                break;
            case 1:
                intent = new Intent(PdfListActivity.this, PdfViewerPagerActivity.class);
                break;
            case 2:
                intent = new Intent(PdfListActivity.this, PdfViewerActivity.class);
                break;

            default:
                intent = new Intent(PdfListActivity.this, PdfViewerRecycleViewActivity.class);
                break;
        }

        intent.putExtra("FileModel", fileModels.get(position));
        startActivity(intent);
    }

    @Override
    public void onRowLongClicked(int position) {

    }

    /****PREPARE DATA****/

    private void listFiles(File sdcard) {
        if (sdcard.isDirectory()) {
            File[] files = sdcard.listFiles();

            try {
                for (File f : files) {
                    if (!f.isDirectory()) {
                        String fileName = f.getName();
                        if (fileName.endsWith(".pdf") || fileName.endsWith(".PDF")) {
                            // Log.d(" FILES",f.getName());
                            fileModels.add(new FileModel(fileName, f.getAbsolutePath(), UtilsFunction.getFileSize(f)));
                        }
                    } else {
                        this.listFiles(f);
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    int selection = 0;
    AlertDialog alertDialog;
    CharSequence[] values = {"Recycle View", "View Pager", "Button Click"};

    public void CreateAlertDialogWithRadioButtonGroup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PdfListActivity.this);
        builder.setTitle("Select Your Choice");
        builder.setSingleChoiceItems(values, selection, (DialogInterface dialog, int item) -> {
            selection = item;
            alertDialog.dismiss();
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    void setUpRefresh() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary, R.color.colorAccent);
        swipeRefreshLayout.post(() ->
                new AsyncTaskWS().execute()
        );
    }

    void showHideRefresh(boolean isShow) {
        if (isShow) {
            swipeRefreshLayout.setRefreshing(true);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        new AsyncTaskWS().execute();
    }

    // To use the AsyncTask, it must be subclassed
    private class AsyncTaskWS extends AsyncTask<Void, Integer, Void> {
        // Before running code in separate thread
        @Override
        protected void onPreExecute() {
            showHideRefresh(true);
        }

        // The code to be executed in a background thread.
        @Override
        protected Void doInBackground(Void... params) {
            fileModels.clear();
            listFiles(sdcardObj);
            return null;
        }

        // after executing the code in the thread
        @Override
        protected void onPostExecute(Void result) {
            showHideRefresh(false);
            adapter.notifyDataSetChanged();
        }
    }
}
