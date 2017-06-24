package com.fandean.zhihudaily.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fandean.zhihudaily.R;
import com.fandean.zhihudaily.adapter.CollectionAdapter;
import com.fandean.zhihudaily.bean.Collection;
import com.fandean.zhihudaily.util.DbUtil;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionFragment extends Fragment {


    public CollectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.collection_recyclerView);

        List<Collection> collections = DbUtil.getCollections(MainActivity.mdb);
        CollectionAdapter adapter = new CollectionAdapter(collections,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        return view;
    }

}
