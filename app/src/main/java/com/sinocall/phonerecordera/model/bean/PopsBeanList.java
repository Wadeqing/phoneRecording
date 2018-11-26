package com.sinocall.phonerecordera.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qingchao on 2018/2/2.
 *
 */

public class PopsBeanList implements Serializable {

    public List<PopsBean> pops;

    public static class PopsBean {
        /**
         * picUrl : http://192.168.10.242:8080/sinomini//res/popPic/20180205/SINOV201802051715213385.jpg
         * popType : 2
         * sysScreenPopId : 64
         * title : ceshi
         * url : www.baidu.com
         */

        public String picUrl;
        public int popType;
        public int sysScreenPopId;
        public String title;
        public String url;
    }
}
