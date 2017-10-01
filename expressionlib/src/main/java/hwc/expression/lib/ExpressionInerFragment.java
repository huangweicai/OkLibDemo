package hwc.expression.lib;

import java.util.ArrayList;

/**
 * Created by jian on 2016/6/23.
 * mabeijianxi@gmail.com
 */
public class ExpressionInerFragment extends BaseInsideFragment {

    public static ExpressionInerFragment newInstance(String[][] pageDate) {

        ExpressionInerFragment expressionInerFragment = new ExpressionInerFragment();
        expressionInerFragment.setPageDate(pageDate);

        return expressionInerFragment;
    }

    @Override
    protected boolean isNeedCirclePageIndicator() {
        return true;
    }

    @Override
    protected ArrayList<ExpressionGridFragment> setupData() {
        ArrayList<ExpressionGridFragment> expressionGridFragments = new ArrayList<>();
        String[][] pageDate = getmPageDate();
        if (pageDate != null) {
//            循环添加没类表情，这里暂时是三类，包括最近，默认与emoji
            for (int i = 0; i < pageDate.length; i++) {
                expressionGridFragments.add(ExpressionGridFragment.newInstance(pageDate[i]));
            }
        }
        return expressionGridFragments;
    }
}
