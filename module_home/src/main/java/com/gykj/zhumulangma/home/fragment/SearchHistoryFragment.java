package com.gykj.zhumulangma.home.fragment;


import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gykj.zhumulangma.common.mvvm.BaseMvvmFragment;
import com.gykj.zhumulangma.home.R;
import com.gykj.zhumulangma.home.adapter.SearchHistoryAdapter;
import com.gykj.zhumulangma.home.adapter.SearchHotAdapter;
import com.gykj.zhumulangma.home.mvvm.ViewModelFactory;
import com.gykj.zhumulangma.home.mvvm.viewmodel.SearchViewModel;

import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class SearchHistoryFragment extends BaseMvvmFragment<SearchViewModel> implements View.OnClickListener {
    private static final String TAG = "HistoryFragment";
    RecyclerView rvHistory;
    TextView tvClear;
    RecyclerView rvHot;

    SearchHistoryAdapter mHistoryAdapter;


    SearchHotAdapter mHotAdapter;

    private onSearchListener mSearchListener;
    public SearchHistoryFragment() {

    }

    @Override
    protected int onBindLayout() {
        return R.layout.home_fragment_search_history;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setSwipeBackEnable(false);
    }
    @Override
    protected void initView(View view) {
        rvHistory=view.findViewById(R.id.rv_history);
        tvClear=view.findViewById(R.id.tv_clear);
        tvClear.setOnClickListener(this);
        rvHot=view.findViewById(R.id.rv_hot);

        rvHistory.setLayoutManager(new com.library.flowlayout.FlowLayoutManager());
        rvHistory.setHasFixedSize(true);

        mHistoryAdapter=new SearchHistoryAdapter(R.layout.common_item_tag);
        mHistoryAdapter.bindToRecyclerView(rvHistory);
        mHistoryAdapter.setOnItemClickListener((adapter, view1, position) -> {
            if(mSearchListener !=null)
                mSearchListener.onSearch(mHistoryAdapter.getItem(position).getKeyword());
        });

        rvHot.setLayoutManager(new com.library.flowlayout.FlowLayoutManager());
        rvHot.setHasFixedSize(true);

        mHotAdapter=new SearchHotAdapter(R.layout.common_item_tag);
        mHotAdapter.bindToRecyclerView(rvHot);
        mHotAdapter.setOnItemClickListener((adapter, view12, position) -> {
            if(mSearchListener !=null)
                mSearchListener.onSearch(mHotAdapter.getItem(position).getSearchword());
        });
    }


    @Override
    public void onRevisible() {
        super.onRevisible();
        mHistoryAdapter.setNewData(null);
        mViewModel.getHistory();
    }

    @Override
    public void initData() {
     mViewModel._getHotWords();
     mViewModel.getHistory();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(R.id.tv_clear==id){
            mViewModel.clearHistory();
            mHistoryAdapter.setNewData(null);
        }
    }
    public void setSearchListener(onSearchListener searchListener) {
        mSearchListener = searchListener;
    }

    @Override
    public Class<SearchViewModel> onBindViewModel() {
        return SearchViewModel.class;
    }

    @Override
    public ViewModelProvider.Factory onBindViewModelFactory() {
        return ViewModelFactory.getInstance(mApplication);
    }

    public interface onSearchListener {

        void onSearch(String keyword);
    }

    @Override
    public void initViewObservable() {
        mViewModel.getHotWordsSingleLiveEvent().observe(this, hotWords -> mHotAdapter.setNewData(hotWords));
        mViewModel.getHistorySingleLiveEvent().observe(this, historyBeanList ->
                mHistoryAdapter.setNewData(historyBeanList));
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultNoAnimator();
    }
    @Override
    protected boolean enableSimplebar() {
        return false;
    }
}