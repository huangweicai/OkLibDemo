package hwc.expression.lib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by jian on 2016/6/23.
 * mabeijianxi@gmail.com
 */
public class ExpressionItemAdapter extends ArrayAdapter<String> {
    public ExpressionItemAdapter(Context context, String[] objects) {
        super(context, R.layout.expression_gv_item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (position == 20) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.expression_gv_delete_item, parent, false);
        } else {
            v = LayoutInflater.from(getContext()).inflate(R.layout.expression_gv_item, parent, false);
            TextView icon = (TextView) v.findViewById(R.id.tv_expression);
            String str = getItem(position);
            icon.setText(str);
        }
        return v;
    }

}
