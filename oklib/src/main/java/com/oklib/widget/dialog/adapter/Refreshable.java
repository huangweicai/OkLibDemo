package com.oklib.widget.dialog.adapter;

import java.util.List;

public interface Refreshable {
    public void refresh(List newData);

    public void addAll(List newData);

    public void clear();

    public void delete(int position);

    public void add(Object object);
}
