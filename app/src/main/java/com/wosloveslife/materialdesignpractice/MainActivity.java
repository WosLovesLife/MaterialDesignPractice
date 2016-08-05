package com.wosloveslife.materialdesignpractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.rvToDoList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new Adapter(getData()));

        mSearchView = (SearchView) findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.w(TAG, "onQueryTextSubmit: " );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.w(TAG, "onQueryTextChange: " );
                return false;
            }
        });
    }

    public List<String> getData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add("模拟数据第 : " + (i + 1) + " 条");
        }
        return data;
    }

    class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
        List<String> mData;

        public Adapter(List<String> data) {
            mData = data;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setTextSize(20);
            textView.setPadding(8, 4, 8, 4);
            return new Holder(textView);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.onBind(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class Holder extends RecyclerView.ViewHolder {

            public Holder(View itemView) {
                super(itemView);
            }

            public void onBind(String data) {
                ((TextView) itemView).setText(data);
            }
        }
    }
}