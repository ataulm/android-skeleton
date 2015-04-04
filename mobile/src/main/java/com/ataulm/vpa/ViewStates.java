package com.ataulm.vpa;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

public final class ViewStates implements Parcelable {

    public static final Creator<ViewStates> CREATOR = new Creator<ViewStates>() {

        public ViewStates createFromParcel(Parcel in) {
            return ViewStates.from(in);
        }

        public ViewStates[] newArray(int size) {
            return new ViewStates[size];
        }

    };

    private final Map<Integer, SparseArray<Parcelable>> viewStates;

    public static ViewStates newInstance() {
        return new ViewStates(new HashMap<Integer, SparseArray<Parcelable>>());
    }

    private static ViewStates from(Parcel in) {
        Bundle bundle = in.readBundle();
        Map<Integer, SparseArray<Parcelable>> viewStates = new HashMap<>(bundle.keySet().size());
        for (String key : bundle.keySet()) {
            SparseArray<Parcelable> sparseParcelableArray = bundle.getSparseParcelableArray(key);
            viewStates.put(Integer.parseInt(key), sparseParcelableArray);
        }
        return new ViewStates(viewStates);
    }

    private ViewStates(Map<Integer, SparseArray<Parcelable>> viewStates) {
        this.viewStates = viewStates;
    }

    public void put(int position, SparseArray<Parcelable> viewState) {
        viewStates.put(position, viewState);
    }

    public SparseArray<Parcelable> get(int position) {
        return viewStates.get(position);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        for (Map.Entry<Integer, SparseArray<Parcelable>> entry : viewStates.entrySet()) {
            bundle.putSparseParcelableArray(String.valueOf(entry.getKey()), entry.getValue());
        }
        dest.writeBundle(bundle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
