package com.pratilipi.hackathon.unbranded.network;


public final class ApiEndPoint {

    public static final String ENDPOINT_PORTFOLIO = "http://www.mocky.io/v2/5aa112c23200004e2ce9fef7";
    public static final String ENDPOINT_CONTENT_HOME = "http://www.mocky.io/v2/5aa6ccab310000573de717a6";


    //    public static final String ENDPOINT_CONTENT_LIST = BASE_SERVER + "api/get/contentList";
    public static final String ENDPOINT_RESUME = "https://drive.google.com/open?id=13h5bVV5S8mW-18BwuPgxPpgOG_H7vUrY";
    private static final String BASE_SERVER = "http://192.168.0.156:8080";
    public static final String ENDPOINT_CONTENT_IMAGE_PATH = BASE_SERVER + "upload/images/";
    public static final String ENDPOINT_CONTENT_BY_ID = BASE_SERVER + "api/getContentById/{id}";
    public static final String ENDPOINT_UPLOAD_CONTENT = BASE_SERVER + "api/get/upload";
    //    public static final String ENDPOINT_TRENDING_HOME = "http://www.mocky.io/v2/5d0c844f3500005700b898be";
//    public static final String ENDPOINT_TRENDING_HOME = "http://www.mocky.io/v2/5d0c9a0a3500003c00b8996c";
    public static final String ENDPOINT_TRENDING_HOME = BASE_SERVER + "/trending";
    public static final String ENDPOINT_PRODUCT_CREATE = BASE_SERVER + "/product";
    //    public static final String ENDPOINT_TRENDING_HOME = "http://192.168.0.156:8080/trending";
    public static final String ENDPOINT_USER_PRODUCTS = BASE_SERVER + "/user/products";
    public static final String ENDPOINT_USER = BASE_SERVER + "/user";
    //    public static final String ENDPOINT_TRENDING_HOME = "http://www.mocky.io/v2/5d0cfd863500006500b89c15";
//    public static final String ENDPOINT_TRENDING_HOME = "http://www.mocky.io/v2/5d0caa683500006700b899e3";
//    public static final String ENDPOINT_TRENDING_HOME = "http://www.mocky.io/v2/5d0c9ffb3500003c00b899a3";
    //    private static final String BASE_SERVER = "http://dewnaveen.info/";
    static String BASE_LOCAL = "http://192.168.43.6/naveen_resume/public/";

    private ApiEndPoint() {
    }

}
