package com.pdfdocumentreader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pdfdocumentreader.utils.UtilsFunction;

import java.util.ArrayList;

/**
 * Created by mayur.p on 10/11/2017.
 */

public class PdfListAdapter extends RecyclerView.Adapter<PdfListAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<FileModel> data;
    UtilsFunction utilsFunctions;
    //DatabaseClass db;
    private AdapterListener listener;

    public PdfListAdapter(Context mContext, ArrayList<FileModel> data, AdapterListener listener) {
        this.mContext = mContext;
        this.data = data;
        utilsFunctions = new UtilsFunction();
        //db = DatabaseClass.GetInstance(mContext);
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        public TextView tv_name, tv_size;
        public View rowView;

        public MyViewHolder(View view) {
            super(view);
            rowView = view;
            tv_name = view.findViewById(R.id.tv_name);
            tv_size = view.findViewById(R.id.tv_size);
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
                .inflate(R.layout.custom_pdf_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // displaying text view data
        FileModel model = data.get(position);
        holder.tv_name.setText(model.getName());
        holder.tv_size.setText(model.getPath() + "\n" + model.getSize());
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
