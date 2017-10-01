package hwc.expression.lib;


import java.util.ArrayList;

import hwc.expression.lib.core.ExpressionCache;

/**
 * Created by jian on 2016/6/23.
 * mabeijianxi@gmail.com
 */
public class ExpressionRecentsFragment extends BaseInsideFragment {

    @Override
    protected boolean isNeedCirclePageIndicator() {
        return false;
    }

    @Override
    protected ArrayList<ExpressionGridFragment> setupData() {
        ArrayList<ExpressionGridFragment> expressionGridFragments = new ArrayList<>();
        String[] recentExpression = ExpressionCache.getRecentExpression();
            expressionGridFragments.add(ExpressionGridFragment.newInstance(recentExpression));
        return expressionGridFragments;
    }

    /**
     * 更新最近使用表情
     * @param str
     */
    public void expressionaddRecent(String str) {
        ArrayList<ExpressionGridFragment> data = getData();
//        其实i暂时永远为0，因为最近使用页限制为了一页，当然可拓展
        for(int i=0;i<data.size();i++){
            data.get(i).notifyData();
        }
    }
}
