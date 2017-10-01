package hwc.expression.lib;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import hwc.expression.lib.core.ExpressionTransformEngine;
import hwc.expression.lib.widget.CustomGridView;


/**
 * Created by jian on 2016/6/23.
 * mabeijianxi@gmail.com
 */
public class ExpressionGridFragment extends Fragment implements AdapterView.OnItemClickListener {
    private String[] mExpressionName;
    private ExpressionClickListener mExpressionClickListener;
    private ExpressionaddRecentListener mExpressionaddRecentListener;
    private ExpressionItemAdapter expressionItemAdapter;
    private ExpressionDeleteClickListener mExpressionDeleteClickListener;

    public static ExpressionGridFragment newInstance(String[] expressionName) {
        ExpressionGridFragment expressionGridFragment = new ExpressionGridFragment();
        expressionGridFragment.setData(expressionName);
        return expressionGridFragment;
    }

    private void setData(String[] expressionName) {
        mExpressionName = expressionName;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (getActivity() instanceof ExpressionClickListener) {
            mExpressionClickListener = (ExpressionClickListener) getActivity();
        } else if (getParentFragment() instanceof ExpressionClickListener) {
            mExpressionClickListener = (ExpressionClickListener) getParentFragment();
        } else {
//            这里必须实现ExpressionClickListener，不然没法添加表情到输入框
            throw new IllegalArgumentException("需要实现ExpressionClickListener");
        }
//        获取添加到最近使用的具体实现
        if (getActivity() instanceof ExpressionaddRecentListener) {
            mExpressionaddRecentListener = (ExpressionaddRecentListener) getActivity();
        } else if (getParentFragment().getParentFragment() instanceof ExpressionaddRecentListener) {
            mExpressionaddRecentListener = (ExpressionaddRecentListener) getParentFragment().getParentFragment();
        }

        if (getActivity() instanceof ExpressionDeleteClickListener) {
            mExpressionDeleteClickListener = (ExpressionDeleteClickListener) getActivity();
        }  else {
            throw new IllegalArgumentException(activity + "需要实现ExpressionDeleteClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_expression_gridview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CustomGridView gv_expression = (CustomGridView) view.findViewById(R.id.gv_expression);
        expressionItemAdapter = new ExpressionItemAdapter(getActivity(), mExpressionName);
        gv_expression.setAdapter(expressionItemAdapter);
        gv_expression.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position==20){
            if(mExpressionDeleteClickListener!=null){
                mExpressionDeleteClickListener.expressionDeleteClick(view);
            }
            return;
        }

        String itemAtPosition = (String) parent.getItemAtPosition(position);
        if (!TextUtils.isEmpty(itemAtPosition)) {
            mExpressionClickListener.expressionClick(itemAtPosition);
            ExpressionTransformEngine.addRecentExpression(itemAtPosition);
            if (mExpressionaddRecentListener != null) {
                mExpressionaddRecentListener.expressionaddRecent(itemAtPosition);
            }
        }

    }

    /**
     * 列表更新，比如更新最近使用列表。在这里其实也只会更新最近使用列表
     */
    public void notifyData() {
        if (expressionItemAdapter != null) {
            expressionItemAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 表情点击回调
     */
    public interface ExpressionClickListener {
        void expressionClick(String str);
    }

    /**
     * 其实也是表情点击回调，主要是为了添加表情到最近使用
     */
    public interface ExpressionaddRecentListener {
        void expressionaddRecent(String str);
    }

    public interface ExpressionDeleteClickListener {
        void expressionDeleteClick(View v);
    }
}
