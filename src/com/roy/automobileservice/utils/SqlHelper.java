package com.roy.automobileservice.utils;

/**
 * Created by Roy on 2016/6/9.
 */
public class SqlHelper {
    public static final String SEARCH_CAR_FROM_BMBO = " select * from ChinaCar";
    public static final String SEARCH_USER_FROM_BMBO = " select * from User";
    public static final String SEARCH_STAFF_FROM_BMBO = " select * from Staff";
    public static final String SEARCH_CARORDER_FROM_BMBO = " select * from CarOrder order by createdAt desc";
    public static final String SEARCH_CARBORDER_FROM_BMBO = " select * from CarBOrder order by createAt desc";
    public static final String SEARCH_CARMORDER_FROM_BMBO = " select * from CarMOrder order by createdAt desc";
}
