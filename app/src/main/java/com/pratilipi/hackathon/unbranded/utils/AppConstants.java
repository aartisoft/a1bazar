/*
 * Copyright (c) 2017.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 *
 */

package com.pratilipi.hackathon.unbranded.utils;


public final class AppConstants {
    public static final int REQUEST_PERMISSION_STORAGE = 3;
    public static final int REQUEST_CODE_RECORDER = 112;

    public static final String STATUS_CODE_SUCCESS = "success";
    public static final String STATUS_CODE_FAILED = "failed";

    public static final int API_STATUS_CODE_LOCAL_ERROR = 0;

    public static final String DB_NAME = "text_editor.db";
    public static final String PREF_NAME = "text_editor_pref";

    public static final long NULL_INDEX = -1L;

//    public static final String SEED_DATABASE_OPTIONS = "seed/options.json";
//    public static final String SEED_DATABASE_QUESTIONS = "seed/questions.json";

    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";

    public static final String PRODUCT_LIST = "PRODUCT_LIST";
    public static final String PRODUCT_STORE = "PRODUCT_STORE";
    public static final String USER_STORE = "USER_STORE";

    public static final String EXTRA_IMAGE_PATH= "EXTRA_IMAGE_PATH";
    public static final String EXTRA_VID_PATH= "EXTRA_VID_PATH";
    public static final String EXTRA_PRODUCT = "EXTRA_PRODUCT";
    private AppConstants() {
        // This utility class is not publicly instantiable
    }
}
