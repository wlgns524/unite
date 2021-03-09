package com.rightcode.unite.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.rightcode.unite.Adapter.RecyclerViewAdapter.FilterCatogoryRecyclerViewAdapter;
import com.rightcode.unite.Adapter.RecyclerViewAdapter.FilterTravelAreaRecyclerViewAdapter;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.Log;
import com.rightcode.unite.Util.MoneyHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends BaseFragment {

    public enum Menu {
        SEARCH, WRITE, LIKE, FILTER
    }

    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.et_search)
    AppCompatEditText et_search;
    @BindView(R.id.iv_search)
    ImageView iv_search;
    @BindView(R.id.iv_write)
    ImageView iv_write;
    @BindView(R.id.iv_like)
    ImageView iv_like;
    @BindView(R.id.iv_filter)
    ImageView iv_filter;
    @BindView(R.id.ll_filter)
    LinearLayout ll_filter;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.rsb_price)
    RangeSeekBar rsb_price;
    @BindView(R.id.rv_travel_area)
    RecyclerView rv_travel_area;
    @BindView(R.id.rv_cateogry)
    RecyclerView rv_cateogry;

    private View root;
    private Context mContext;
    private FilterTravelAreaRecyclerViewAdapter mFilterTravelAreaRecyclerViewAdapter;
    private FilterCatogoryRecyclerViewAdapter mFilterCatogoryRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, root);
        initialize();

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public void setVisible(Menu menu, Boolean visible) {
        switch (menu) {
            case WRITE:
                if (visible)
                    iv_write.setVisibility(View.VISIBLE);
                else
                    iv_write.setVisibility(View.GONE);
                break;
            case LIKE:
                if (visible)
                    iv_like.setVisibility(View.VISIBLE);
                else
                    iv_like.setVisibility(View.GONE);
                break;
            case FILTER:
                if (visible)
                    iv_filter.setVisibility(View.VISIBLE);
                else
                    iv_filter.setVisibility(View.GONE);
                break;
        }
    }

    public void setListener(Menu menu, View.OnClickListener listener) {
        Log.d(menu);
        switch (menu) {
            case SEARCH:
                iv_search.setOnClickListener(listener);
                break;
            case WRITE:
                iv_write.setOnClickListener(listener);
                break;
            case LIKE:
                iv_like.setOnClickListener(listener);
                break;
            case FILTER:
                iv_filter.setOnClickListener(listener);
                break;
        }
    }

    public Boolean isShowFilter() {
        if (ll_filter.getVisibility() == View.GONE) {
            return false;
        } else {
            return true;
        }
    }

    public void filterShow() {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                -ll_filter.getHeight() - ll_search.getHeight(),                 // fromYDelta
                0); // toYDelta
        animate.setDuration(300);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv_filter.setImageDrawable(mContext.getDrawable(R.drawable.ic_filter_on));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ll_filter.setVisibility(View.VISIBLE);
        ll_filter.setAlpha(1f);
        ll_filter.startAnimation(animate);
    }

    public void filterHide() {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                -ll_filter.getHeight() - ll_search.getHeight()); // toYDelta
        animate.setDuration(300);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv_filter.setImageDrawable(mContext.getDrawable(R.drawable.ic_filter_off));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ll_filter.setVisibility(View.GONE);
        ll_filter.setAlpha(0f);
        ll_filter.startAnimation(animate);
    }

    public void setVisible(int i) {
        if (i == View.VISIBLE) {
            root.setVisibility(View.VISIBLE);
        } else if (i == View.INVISIBLE) {
            root.setVisibility(View.INVISIBLE);
        } else {
            root.setVisibility(View.GONE);
        }
    }

    public String getSearchText() {
        return et_search.getText().toString();
    }

    public void setSearchText(CharSequence search) {
        et_search.setText(search);
    }

    public String getArea() {
        return mFilterTravelAreaRecyclerViewAdapter.getSelectList();
    }

    public String getCategory() {
        return mFilterCatogoryRecyclerViewAdapter.getSelectList();
    }

    public Float getStartPrice() {
        return rsb_price.getLeftSeekBar().getProgress();
    }

    public Float getEndPrice() {
        return rsb_price.getRightSeekBar().getProgress();
    }

    private void initialize() {
        rsb_price.setProgress(50000f, 1000000f);
        rsb_price.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                Float left = leftValue;
                Float right = rightValue;
                tv_price.setText(String.format("%s ~ %s원", MoneyHelper.getKor(left.intValue() / 10000 * 10000), MoneyHelper.getKor(right.intValue() / 10000 * 10000)));
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });

        rv_travel_area.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mFilterTravelAreaRecyclerViewAdapter = new FilterTravelAreaRecyclerViewAdapter(getContext());
        rv_travel_area.setAdapter(mFilterTravelAreaRecyclerViewAdapter);

        rv_cateogry.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mFilterCatogoryRecyclerViewAdapter = new FilterCatogoryRecyclerViewAdapter(getContext());
        rv_cateogry.setAdapter(mFilterCatogoryRecyclerViewAdapter);

        ViewTreeObserver viewTreeObserver = ll_filter.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int filterHeight = ll_filter.getHeight();
                    if (filterHeight != 0) {
                        Log.d("onGlobalLayout");
                        filterShow();
                        ll_filter.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }
    }
}
