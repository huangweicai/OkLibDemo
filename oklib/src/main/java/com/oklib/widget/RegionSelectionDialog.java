package com.oklib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.oklib.R;
import com.oklib.base.BaseDialogFragment;
import com.oklib.util.FastJsonUtil;
import com.oklib.widget.bean.RegionBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

 /**
   * 时间：2017/3/21
   * 作者：黄伟才
   * 简书：http://www.jianshu.com/p/87e7392a16ff
   * github：https://github.com/huangweicai/OkLibDemo
   * 描述：地区选择窗口
   */
public class RegionSelectionDialog extends BaseDialogFragment {
    private ListView lv_province;
    private ListView lv_city;
    private ListView lv_district;
    private View v_line_two;

    private List<RegionBean> provinceList = new ArrayList<>();
    private List<RegionBean.CityBean> cityList = new ArrayList<>();
    private List<String> districtList = new ArrayList<>();

    private String provinceName = "";
    private String cityName = "";
    private String districtName = "";

    @Override
    public float initDimValue() {
        return 0.7f;
    }

    @Override
    public void initOnResume() {
        setWHSize(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(getContext(), 300));
    }

    @Override
    public boolean isCancel() {
        return true;
    }

    @Override
    public int gravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public int style() {
        return R.style.oklib_common_single_dialog_window_anim;
    }

    @Override
    public int initContentView() {
        return R.layout.oklib_region_selection_dialog;
    }

    @Override
    protected void argumentsDate() {

    }

    @Override
    public void initActionBarView(View view) {

    }

    @Override
    public void initView(View view) {
        lv_province = findView(view, R.id.lv_province);
        lv_city = findView(view, R.id.lv_city);
        lv_district = findView(view, R.id.lv_district);
        v_line_two = findView(view, R.id.v_line_two);
        v_line_two.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initData(View view) {
        provinceList = new ArrayList<>();
        String regionJsonStr = getString(getActivity().getResources().openRawResource(R.raw.oklib_region_selection_area));
        provinceList = FastJsonUtil.json2List(regionJsonStr, RegionBean.class);

        lv_province.setAdapter(provinceAdapter);
        lv_city.setAdapter(cityAdapter);
        lv_district.setAdapter(districtAdapter);
    }

     @Override
     protected void initNet() {

     }

     private BaseAdapter provinceAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return provinceList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                final LayoutInflater inflater = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.oklib_region_selection_item, parent, false);
                holder = new ViewHolder();
                holder.tv_region = (TextView) convertView.findViewById(R.id.tv_region);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final RegionBean regionBean = provinceList.get(position);
            if (regionBean.isSelect()) {
                holder.tv_region.setBackgroundResource(R.color.oklib_dialog_line);
            }else{
                holder.tv_region.setBackgroundResource(R.color.oklib_default_color);
            }
            holder.tv_region.setText(regionBean.getName());
            holder.tv_region.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < provinceList.size(); i++) {
                        RegionBean regionBean = provinceList.get(i);
                        if (i == position) {
                            regionBean.setSelect(true);
                        }else{
                            regionBean.setSelect(false);
                        }
                    }
                    provinceAdapter.notifyDataSetChanged();

                    provinceName = regionBean.getName();
                    cityName = "";
                    districtName = "";
                    cityList.clear();
                    districtList.clear();
                    cityList.addAll(regionBean.getCity());
                    cityAdapter.notifyDataSetChanged();
                    districtAdapter.notifyDataSetChanged();
                    v_line_two.setVisibility(View.VISIBLE);
                }
            });
            return convertView;
        }
    };

    private BaseAdapter cityAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return cityList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                final LayoutInflater inflater = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.oklib_region_selection_item, parent, false);
                holder = new ViewHolder();
                holder.tv_region = (TextView) convertView.findViewById(R.id.tv_region);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final RegionBean.CityBean cityBean = cityList.get(position);
            if (cityBean.isSelect()) {
                holder.tv_region.setBackgroundResource(R.color.oklib_dialog_line);
            }else{
                holder.tv_region.setBackgroundResource(R.color.oklib_default_color);
            }
            holder.tv_region.setText(cityBean.getName());
            holder.tv_region.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < cityList.size(); i++) {
                        RegionBean.CityBean cityBean = cityList.get(i);
                        if (i == position) {
                            cityBean.setSelect(true);
                        }else{
                            cityBean.setSelect(false);
                        }
                    }
                    cityAdapter.notifyDataSetChanged();

                    cityName = cityBean.getName();
                    districtName = "";
                    districtList.clear();
                    districtList.addAll(cityBean.getArea());
                    districtAdapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }
    };

    private BaseAdapter districtAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return districtList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                final LayoutInflater inflater = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.oklib_region_selection_item, parent, false);
                holder = new ViewHolder();
                holder.tv_region = (TextView) convertView.findViewById(R.id.tv_region);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tv_region.setText(districtList.get(position));
            holder.tv_region.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    districtName = districtList.get(position);
                    if (null != onSelectListener) {
                        dismiss();
                        onSelectListener.onSelect(provinceName + "," + cityName + "," + districtName);
                    }
                }
            });
            return convertView;
        }
    };

    private static class ViewHolder {
        TextView tv_region;
    }


    private static FragmentManager fm;
    private static FragmentTransaction ft;
    private static RegionSelectionDialog dialog;

    @SuppressLint("ValidFragment")
    private RegionSelectionDialog() {
    }

    /**
     * 显示dialog
     */
    public static RegionSelectionDialog create(FragmentManager _fm) {
        fm = _fm;
        dialog = new RegionSelectionDialog();
        return dialog;
    }

    public void show() {
        ft = fm.beginTransaction();
        dialog.show(ft, "");
    }

    private OnSelectListener onSelectListener;

    public interface OnSelectListener {
        void onSelect(String selectRegion);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

     /**
      * 一个获取InputStream中字符串内容的方法
      * @param inputStream
      * @return
      * 默认utf-8编码
      */
     public String getString(InputStream inputStream) {
         return getString(inputStream, "utf-8");
     }

     /**
      * inputStream
      * inputStreamReader
      * BufferedReader
      * @param inputStream
      * @param format
      * @return
      */
     public String getString(InputStream inputStream, String format) {
         InputStreamReader inputStreamReader = null;
         try {
             //不同的文本文件可能编码不同，如果出现乱码，可能需要调整编码
             inputStreamReader = new InputStreamReader(inputStream, format);
         } catch (UnsupportedEncodingException e1) {
             e1.printStackTrace();
         }
         BufferedReader reader = new BufferedReader(inputStreamReader);
         StringBuffer sb = new StringBuffer("");
         String line;
         try {
             while ((line = reader.readLine()) != null) {
                 sb.append(line);
                 sb.append("\n");
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
         return sb.toString();
     }
}
