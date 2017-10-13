package com.oklib.view.letters_nav.utils;


import com.oklib.view.letters_nav.bean.SortModel;

import java.util.Comparator;

public class PinyinComparator implements Comparator<SortModel> {

    @Override
    public int compare(SortModel lhs, SortModel rhs) {
        if (lhs.getSortLetters().equals("@")
                || rhs.getSortLetters().equals("#")) {
            return -1;
        } else if (lhs.getSortLetters().equals("#")
                || rhs.getSortLetters().equals("@")) {
            return 1;
        } else {
            return lhs.getSortLetters().compareTo(rhs.getSortLetters());
        }
    }

}
