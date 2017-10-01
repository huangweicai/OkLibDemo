package hwc.loadsir.target;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import hwc.loadsir.PostUtil;
import hwc.loadsir.R;
import hwc.loadsir.callback.EmptyCallback;
import hwc.loadsir.callback.LoadingCallback;
import hwc.loadsir.lib.callback.Callback;
import hwc.loadsir.lib.core.LoadService;
import hwc.loadsir.lib.core.LoadSir;

/**
 * Description:
 * Create Time:2017/9/26 14:33
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class KeepTitleFragment extends Fragment {

    private LoadService loadService;
//    @BindView(R.id.iv_back)
    ImageView mIvBack;
//    private Unbinder unBinder;
//
//    @OnClick(R.id.iv_back)
//    public void onBack() {
//        getActivity().finish();
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PostUtil.postCallbackDelayed(loadService, EmptyCallback.class, 1200);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.title_title_bar, container, false);
//        unBinder = ButterKnife.bind(this, rootView);

        mIvBack = rootView.findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        RelativeLayout titleBarView = (RelativeLayout) rootView.findViewById(R.id.rl_titleBar);
        LinearLayout contentView = (LinearLayout) rootView.findViewById(R.id.ll_content);
        rootView.removeView(contentView);
        LoadSir loadSir = new LoadSir.Builder()
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .setDefaultCallback(LoadingCallback.class)
                .build();
        loadService = loadSir.register(contentView, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                loadService.showSuccess();
            }

        });
        return loadService.getTitleLoadLayout(getContext(), rootView, titleBarView);
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        unBinder.unbind();
    }
}
