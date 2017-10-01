package hwc.expression.lib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import hwc.expression.lib.core.ExpressionCache;
import hwc.expression.lib.core.ExpressionTransformEngine;
import hwc.expression.lib.widget.PagerSlidingTabStrip;

/**
 * Created by jian on 2016/6/23.
 * mabeijianxi@gmail.com
 */
public class ExpressionShowFragment extends Fragment implements ExpressionGridFragment.ExpressionaddRecentListener {

    private ViewPager vp_expression;
    private PagerSlidingTabStrip psts_expression;
    private ExpressionRecentsFragment expressionRecentsFragment;

    public static void input(EditText editText, String str) {
        ExpressionTransformEngine.input(editText, str);
    }
    public static void delete(EditText editText) {
        ExpressionTransformEngine.delete(editText);
    }
    public static ExpressionShowFragment newInstance() {
        ExpressionShowFragment expressionShowFragment = new ExpressionShowFragment();
        return expressionShowFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.expression_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setupData();
    }

    /**
     * 可拓展N类表情
     */
    private void setupData() {

        ArrayList<BaseInsideFragment> expressionInerFragments = new ArrayList<>();

        expressionRecentsFragment = new ExpressionRecentsFragment();
//        最近使用
        expressionInerFragments.add(expressionRecentsFragment);
//        表情一
        expressionInerFragments.add(ExpressionInerFragment.newInstance(new String[][]{ExpressionCache.page_1, ExpressionCache.page_2, ExpressionCache.page_3,ExpressionCache.page_4, ExpressionCache.page_5}));
//       表二
//        expressionInerFragments.add(ExpressionInerFragment.newInstance(new String[][]{ExpressionCache.page_4, ExpressionCache.page_5}));
//      TODO 自己拓展
        vp_expression.setOffscreenPageLimit(2);
        vp_expression.setAdapter(new ExpressionShowApadater(getChildFragmentManager(), expressionInerFragments));
        psts_expression.setViewPager(vp_expression);
        vp_expression.setCurrentItem(1, false);
    }

    private void initView(View view) {
        vp_expression = (ViewPager) view.findViewById(R.id.vp_expression);
        psts_expression = (PagerSlidingTabStrip) view.findViewById(R.id.psts_expression);
    }

    @Override
    public void expressionaddRecent(String str) {
        if (expressionRecentsFragment != null) {
            expressionRecentsFragment.expressionaddRecent(str);
        }
    }

    private static class ExpressionShowApadater extends FragmentStatePagerAdapter {

        private final ArrayList<BaseInsideFragment> expressionInerFragments;

        public ExpressionShowApadater(FragmentManager fm, ArrayList<BaseInsideFragment> expressionInerFragments) {
            super(fm);
            this.expressionInerFragments = expressionInerFragments;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return ExpressionCache.getPageTitle()[position];
        }

        @Override
        public Fragment getItem(int position) {
            return expressionInerFragments.get(position);
        }

        @Override
        public int getCount() {
            return expressionInerFragments == null ? 0 : expressionInerFragments.size();
        }
    }

}
