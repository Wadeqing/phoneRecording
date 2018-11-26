package com.sinocall.phonerecordera.api.account;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qingchao on 2017/12/8.
 *
 */

public class LawConsultResponse implements Serializable {


    /**
     * code : 0
     * codeInfo :
     * data : {"recommendLaws":[{"UIFirm":"河北俱时律师事务所","UIName":"徐延爽","UIPic":"http://testapi.legaland.cn/Files/AppFile/7653a2a6216d43bb85d7c763560c4ccc/Head/201602/20160219235646_08323.jpg","Province":"河北","City":"石家庄","UISignature":"河北俱时律师事务所律师。"},{"UIFirm":"河南润之林律师事务所","UIName":"董艳丽","UIPic":"http://testapi.legaland.cn/Files/AppFile/676cd04a9875417fa4e3f92276b63bce/Head/201512/20151207093803_46920.jpg","Province":"河南","City":"郑州","UISignature":"企业专业法律服务十余年，对企业劳资关系、职业经理人合同管理风险防范、企业知识产权产品设计与规划、如何做好企业法律风险防范等课题有专项研究，愿意为您和您的企业保驾护航!"},{"UIFirm":"金博大律师事务所","UIName":"赵海峰","UIPic":"http://testapi.legaland.cn/Files/AppFile/507d6349e65a4af38827bdffa03b1ae1/Head/201601/20160130094254_11368.jpg","Province":"河南","City":"","UISignature":""},{"UIFirm":"北京市炜衡律师事务所","UIName":"赵英春","UIPic":"http://testapi.legaland.cn/Files/AppFile/3b89e4235cc54451b4b4814b655846e1/Head/201512/20151211142626_74858.jpg","Province":"北京","City":"海淀","UISignature":"赵英春律师"}],"prices":[{"Code":"QPRICE1","Value":48},{"Code":"QPRICE2","Value":78},{"Code":"QPRICE3","Value":108}],"verifyCoin":"900"}
     */

    public int code;
    public String codeInfo;
    public DataBean data;

    public static class DataBean {
        /**
         * recommendLaws : [{"UIFirm":"河北俱时律师事务所","UIName":"徐延爽","UIPic":"http://testapi.legaland.cn/Files/AppFile/7653a2a6216d43bb85d7c763560c4ccc/Head/201602/20160219235646_08323.jpg","Province":"河北","City":"石家庄","UISignature":"河北俱时律师事务所律师。"},{"UIFirm":"河南润之林律师事务所","UIName":"董艳丽","UIPic":"http://testapi.legaland.cn/Files/AppFile/676cd04a9875417fa4e3f92276b63bce/Head/201512/20151207093803_46920.jpg","Province":"河南","City":"郑州","UISignature":"企业专业法律服务十余年，对企业劳资关系、职业经理人合同管理风险防范、企业知识产权产品设计与规划、如何做好企业法律风险防范等课题有专项研究，愿意为您和您的企业保驾护航!"},{"UIFirm":"金博大律师事务所","UIName":"赵海峰","UIPic":"http://testapi.legaland.cn/Files/AppFile/507d6349e65a4af38827bdffa03b1ae1/Head/201601/20160130094254_11368.jpg","Province":"河南","City":"","UISignature":""},{"UIFirm":"北京市炜衡律师事务所","UIName":"赵英春","UIPic":"http://testapi.legaland.cn/Files/AppFile/3b89e4235cc54451b4b4814b655846e1/Head/201512/20151211142626_74858.jpg","Province":"北京","City":"海淀","UISignature":"赵英春律师"}]
         * prices : [{"Code":"QPRICE1","Value":48},{"Code":"QPRICE2","Value":78},{"Code":"QPRICE3","Value":108}]
         * verifyCoin : 900
         */

        public String verifyCoin;
        public List<RecommendLawsBean> recommendLaws;
        public List<PricesBean> prices;

        public static class RecommendLawsBean {
            /**
             * UIFirm : 河北俱时律师事务所
             * UIName : 徐延爽
             * UIPic : http://testapi.legaland.cn/Files/AppFile/7653a2a6216d43bb85d7c763560c4ccc/Head/201602/20160219235646_08323.jpg
             * Province : 河北
             * City : 石家庄
             * UISignature : 河北俱时律师事务所律师。
             */

            public String UIFirm;
            public String UIName;
            public String UIPic;
            public String Province;
            public String City;
            public String UISignature;
        }

        public static class PricesBean {
            /**
             * Code : QPRICE1
             * Value : 48
             */

            public String Code;
            public double Value;
        }
    }
}
