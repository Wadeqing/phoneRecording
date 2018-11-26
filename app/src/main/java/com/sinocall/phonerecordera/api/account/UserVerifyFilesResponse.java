package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qingchao on 2017/12/18.
 */

public class UserVerifyFilesResponse implements Serializable {

    /**
     * code : 0
     * codeInfo :
     * data : [{"id":154,"verifyType":"电话录音存证文件","createTime":"2017/12/15","fileName":"拨打01084186438的通话录音.wav"},{"id":153,"verifyType":"电话录音存证文件","createTime":"2017/12/15","fileName":"测试录音文件3"},{"id":152,"verifyType":"电话录音存证文件","createTime":"2017/12/14","fileName":"hello.wav"},{"id":151,"verifyType":"电话录音存证文件","createTime":"2017/12/14","fileName":"拨打法律服务的通话录音.wav"},{"id":150,"verifyType":"电话录音存证文件","createTime":"2017/12/14","fileName":"拨打法律服务的通话录音.wav"},{"id":149,"verifyType":"电话录音存证文件","createTime":"2017/12/14","fileName":"拨打法律服务的通话录音.wav"},{"id":148,"verifyType":"电话录音存证文件","createTime":"2017/12/14","fileName":"拨打法律服务的通话录音.wav"},{"id":147,"verifyType":"电话录音存证文件","createTime":"2017/12/13","fileName":"拨打01084186438的通话录音.wav"},{"id":146,"verifyType":"电话录音存证文件","createTime":"2017/12/13","fileName":"拨打01084186438的通话录音.wav"},{"id":145,"verifyType":"电话录音存证文件","createTime":"2017/12/13","fileName":"拨打01084186438的通话录音.wav"},{"id":144,"verifyType":"电话录音存证文件","createTime":"2017/12/13","fileName":"拨打018826679482的通话录音.wav"},{"id":143,"verifyType":"电话录音存证文件","createTime":"2017/12/13","fileName":"拨打018826679482的通话录音.wav"},{"id":142,"verifyType":"电话录音存证文件","createTime":"2017/12/13","fileName":"拨打018826679482的通话录音.wav"},{"id":141,"verifyType":"电话录音存证文件","createTime":"2017/12/13","fileName":"测试录音文件4"},{"id":140,"verifyType":"电话录音存证文件","createTime":"2017/12/13","fileName":"测试录音文件4"},{"id":138,"verifyType":"电话录音存证文件","createTime":"2017/11/28","fileName":"222.wav"},{"id":137,"verifyType":"电话录音存证文件","createTime":"2017/11/28","fileName":"hello.wav"},{"id":136,"verifyType":"电话录音存证文件","createTime":"2017/11/28","fileName":".wav"},{"id":135,"verifyType":"电话录音存证文件","createTime":"2017/11/28","fileName":"拨打法律服务的通话录音.wav"},{"id":134,"verifyType":"电话录音存证文件","createTime":"2017/11/27","fileName":"哈哈哈哈哈.wav"}]
     */

    public int code;
    public String codeInfo;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 154
         * verifyType : 电话录音存证文件
         * createTime : 2017/12/15
         * fileName : 拨打01084186438的通话录音.wav
         */

        public long id;
        public String verifyType;
        public String createTime;
        public String fileName;
    }
}
