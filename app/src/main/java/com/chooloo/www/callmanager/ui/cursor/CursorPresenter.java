package com.chooloo.www.callmanager.ui.cursor;

import android.database.Cursor;

import androidx.loader.content.Loader;

import android.os.Bundle;

import com.chooloo.www.callmanager.ui.base.BasePresenter;

public class CursorPresenter<V extends CursorMvpView> extends BasePresenter<V> implements CursorMvpPresenter<V> {
    @Override
    public void onRequestPermissionsResult(String[] permissions) {
        mMvpView.load();
    }

    @Override
    public void onRefresh() {
        mMvpView.load();
    }

    @Override
    public void onNoPermissions() {
        onNoResults();
        mMvpView.askForPermissions();
    }

    @Override
    public void onResults() {
        mMvpView.showEmptyPage(false);
    }

    @Override
    public void onNoResults() {
        mMvpView.showEmptyPage(true);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        mMvpView.setRefreshing(true);
        return mMvpView.getLoader(args);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mMvpView.setData(data);
        mMvpView.setRefreshing(false);
        if (mMvpView.getSize() > 0) {
            onResults();
        } else {
            onNoResults();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMvpView.setData(null);
    }

    @Override
    public void onEnablePermissionClick() {
        mMvpView.askForPermissions();
    }
}