package com.gykj.zhumulangma.home.fragment;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gykj.zhumulangma.common.adapter.NavigatorAdapter;
import com.gykj.zhumulangma.common.mvvm.BaseFragment;
import com.gykj.zhumulangma.home.R;
import com.gykj.zhumulangma.home.adapter.RankCategotyAdapter;
import com.gykj.zhumulangma.home.adapter.RankFreeAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankFragment extends BaseFragment implements View.OnClickListener,
        BaseQuickAdapter.OnItemClickListener {


    public RankFragment() {}


    MagicIndicator magicIndicator;
    ViewPager viewpager;
    RecyclerView rvCategory;
    FrameLayout flMask;

    ViewGroup layoutFree;
    ViewGroup layoutPaid;
    RefreshLayout rlFree;
    RefreshLayout rlPaid;
    RecyclerView rvFree;
    RecyclerView rvPaid;
    RankFreeAdapter mFreeAdapter;
    RankFreeAdapter mPaidAdapter;

    //下拉中间视图
    View llbarCenter;
    View ivCategoryDown;
    TextView tvTitle;

    private String[] tabs = {"免费榜", "付费榜"};
    private String[] c_labels = {"热门", "音乐", "娱乐", "有声书"
            , "儿童", "3D体验馆", "资讯", "脱口秀"
            , "情感生活", "历史", "人文", "英语"
            , "小语种", "教育培训", "广播剧", "国学书院"
            , "电台", "商业财经", "IT科技", "健康养生"
            , "旅游", "汽车", "动漫游戏", "电影"};
    private String[] c_ids = {"0", "2", "4", "3"
            , "6", "29", "1", "28"
            , "10", "9", "39", "38"
            , "32", "13", "15", "40"
            , "17", "8", "18", "7"
            , "22", "21", "24", "23"};

    private static final int PAGESIZE = 20;
    private int curFreePage = 1;
    private int totalFreePage;
    private String cid = c_ids[0];



    @Override
    protected int onBindLayout() {
        return R.layout.home_fragment_rank;
    }

    @Override
    public void initView(View view) {
        magicIndicator=fd(R.id.magic_indicator);
        viewpager=fd(R.id.viewpager);
        rvCategory=fd(R.id.rv_category);
        flMask=fd(R.id.fl_mask);

        ivCategoryDown=llbarCenter.findViewById(R.id.iv_down);
        tvTitle=llbarCenter.findViewById(R.id.tv_title);
        tvTitle.setText(c_labels[0]);

        layoutFree = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.common_layout_refresh_loadmore, null);
        layoutPaid = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.common_layout_refresh_loadmore, null);
        rlFree = layoutFree.findViewById(R.id.refreshLayout);
        rlPaid = layoutPaid.findViewById(R.id.refreshLayout);
        rvFree = layoutFree.findViewById(R.id.rv);
        rvPaid = layoutPaid.findViewById(R.id.rv);
        rvFree.setLayoutManager(new LinearLayoutManager(mContext));
        rvPaid.setLayoutManager(new LinearLayoutManager(mContext));
        mFreeAdapter = new RankFreeAdapter(R.layout.home_item_rank_free);
        mPaidAdapter = new RankFreeAdapter(R.layout.home_item_rank_free);
        rvFree.setHasFixedSize(true);
        rvPaid.setHasFixedSize(true);
        mFreeAdapter.bindToRecyclerView(rvFree);
        mPaidAdapter.bindToRecyclerView(rvPaid);

        viewpager.setAdapter(new RankPagerAdapter());
        final CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new NavigatorAdapter(Arrays.asList(tabs), viewpager, 125));
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewpager);

        rvCategory.setLayoutManager(new GridLayoutManager(mContext, 4));
        RankCategotyAdapter categotyAdapter=new RankCategotyAdapter(R.layout.home_item_rank_category,
                Arrays.asList(c_labels));
        rvCategory.setHasFixedSize(true);
        categotyAdapter.bindToRecyclerView(rvCategory);
        categotyAdapter.setOnItemClickListener(this);


    }

    @Override
    public void initListener() {
        super.initListener();

        llbarCenter.setOnClickListener(this);
        flMask.setOnClickListener(this);

        mFreeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
              /*  SupportFragment fragment=new AlbumDetailFragment();
                Bundle bundle=new Bundle();
                bundle.putLong(AlbumDetailFragment.ALBUMID,mFreeList.get(position).getId());
                fragment.setArguments(bundle);
                start(fragment);*/
            }
        });
        mPaidAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
          /*      SupportFragment fragment=new AlbumDetailFragment();
                Bundle bundle=new Bundle();
                bundle.putLong(AlbumDetailFragment.ALBUMID,mPaidList.get(position).getId());
                fragment.setArguments(bundle);
                start(fragment);*/
            }
        });
        rlFree.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }
        });
        rlPaid.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(view==llbarCenter||id==R.id.fl_mask){
            switchCategory();
        }
    }

    private void switchCategory() {
        if (flMask.getVisibility() == View.VISIBLE) {
            flMask.setVisibility(View.GONE);
            ivCategoryDown.animate().rotationBy(180).setDuration(200);
        } else {
            flMask.setVisibility(View.VISIBLE);
            ivCategoryDown.animate().rotationBy(-180).setDuration(200);
        }
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switchCategory();
        tvTitle.setText(c_labels[position]);
        cid=c_ids[position];

    }

    class RankPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 2;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = position == 0 ? layoutFree : layoutPaid;
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

    @Override
    protected Integer[] onBindBarRightIcon() {
        return new Integer[]{R.drawable.ic_common_share};
    }

    @Override
    protected int onBindBarCenterStyle() {
        return BarStyle.CENTER_CUSTOME;
    }

    @Override
    protected int onBindBarRightStyle() {
        return BarStyle.RIGHT_ICON;
    }

    @Override
    protected View onBindBarCenterCustome() {
       llbarCenter= LayoutInflater.from(mContext).inflate(R.layout.home_layout_rank_bar_center,null);
        return llbarCenter;
    }
}