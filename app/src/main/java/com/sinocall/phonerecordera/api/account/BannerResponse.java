package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qingchao on 2017/12/19.
 *
 * @author l
 */

public class BannerResponse implements Serializable {

    /**
     * code : 0
     * codeInfo :
     * data : [{"imageUrl":"http://p1.bqimg.com/1949/6dde0e1172d19eb4.png","jumpUrl":"http://i1.piimg.com/1949/b4a840d7df24e052.png","msg":"录音官微推出微信端","title":"录音官微推出微信端","type":0},{"imageUrl":"http://i1.piimg.com/1949/116cacddcd48b451.png","jumpUrl":"http://s.wcd.im/v/1qo0gZ36/","msg":"315录音与你同行","title":"福利回馈-315录音与你同行","type":1},{"imageUrl":"http://i1.piimg.com/1949/269343fe3666cdb9.png","jumpUrl":"https://itunes.apple.com/cn/app/id1184827252?mt=8","msg":"好评立得通话券","title":"好评有礼","type":0}]
     */

    public int code;
    public String codeInfo;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * imageUrl : http://p1.bqimg.com/1949/6dde0e1172d19eb4.png
         * jumpUrl : http://i1.piimg.com/1949/b4a840d7df24e052.png
         * msg : 录音官微推出微信端
         * title : 录音官微推出微信端
         * type : 0
         */

        public String imageUrl;
        public String jumpUrl;
        public String msg;
        public String title;
        public int type;
    }
}
