package com.pdfdocumentreader.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pdfdocumentreader.R;
import com.pdfdocumentreader.utils.PdfViewRenderer;
import com.pdfdocumentreader.utils.UtilsFunction;

import java.util.ArrayList;

public class PdfViewerRecycleViewAdapter extends RecyclerView.Adapter<PdfViewerRecycleViewAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Integer> data;
    UtilsFunction utilsFunctions;
    //DatabaseClass db;
    private AdapterListener listener;
    PdfViewRenderer pdfRenderer;

    public PdfViewerRecycleViewAdapter(Context mContext, ArrayList<Integer> data, AdapterListener listener,
                                       PdfViewRenderer pdfRenderer) {
        this.mContext = mContext;
        this.data = data;
        utilsFunctions = new UtilsFunction();
        //db = DatabaseClass.GetInstance(mContext);
        this.listener = listener;
        this.pdfRenderer = pdfRenderer;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        public ImageView img;
        public View rowView;

        public MyViewHolder(View view) {
            super(view);
            rowView = view;
            img = view.findViewById(R.id.image);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onRowClicked(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onRowLongClicked(getAdapterPosition());
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_pdf_recycle_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.img.setImageBitmap(pdfRenderer.showPage(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void removeData(int position) {
        data.remove(position);
        notifyDataSetChanged();
    }

    public interface AdapterListener {
        void onRowClicked(int position);

        void onRowLongClicked(int position);
    }
}
