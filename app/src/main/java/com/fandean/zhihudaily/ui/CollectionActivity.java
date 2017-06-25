package com.fandean.zhihudaily.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.fandean.zhihudaily.R;
import com.fandean.zhihudaily.adapter.CollectionAdapter;
import com.fandean.zhihudaily.bean.Collection;
import com.fandean.zhihudaily.util.DbUtil;

import java.util.List;

public class CollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.collection_toolbar);
        toolbar.setTitle("收藏夹");
        setSupportActionBar(toolbar);

//        getSupportActionBar().setTitle("收藏夹");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.collection_recyclerView);

        List<Collection> collections = DbUtil.getCollections(MainActivity.mdb);
        CollectionAdapter adapter = new CollectionAdapter(collections,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
