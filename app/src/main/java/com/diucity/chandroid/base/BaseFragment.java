package com.diucity.chandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diucity.chandroid.base.mvp.Delegate;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<D extends Delegate> extends Fragment {
    protected final String TAG = this.getClass().getSimpleName();

    @Inject
    protected D mDelegate;
    public View mFragmentView;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initInject();
        if (null == mFragmentView) {
            mFragmentView = View.inflate(getActivity(), getLayoutId(), null);
            bind = ButterKnife.bind(this, mFragmentView);
            init();
            loadData();
        } else {
            bind = ButterKnife.bind(this, mFragmentView);
        }
        return mFragmentView;
    }

    protected abstract void loadData();

    protected abstract void init();

    protected abstract int getLayoutId();

    protected abstract void initInject();

    @Override
    public void onDetach() {
        super.onDetach();
        if (null != mFragmentView) {
            ((ViewGroup) mFragmentView.getParent()).removeView(mFragmentView);
        }
        if (bind != null) {
            bind.unbind();
        }
        if (mDelegate != null) {
            mDelegate.onDestroy();
        }
    }
}
