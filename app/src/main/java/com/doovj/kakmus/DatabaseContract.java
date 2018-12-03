package com.doovj.kakmus;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_INDO_ENG = "table_indo_eng";
    static String TABLE_ENG_INDO = "table_eng_indo";

    static final class Kamus implements BaseColumns {
        static String KATA = "kata";
        static String ARTI = "arti";
    }
}
