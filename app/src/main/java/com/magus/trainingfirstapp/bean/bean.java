package com.magus.trainingfirstapp.bean;

import java.util.List;

/**
 * Created by yangshuai on 2015/9/25 0025.
 */
public class bean {

    /**
     * appkey : 你的appkey
     * timestamp : 你的timestamp
     * type : groupcast
     * filter : {"where":{"and":[{"app_version":"1.0"}]}}
     * payload : {"display_type":"notification","body":{"ticker":"测试提示文字","title":"测试标题","text":"测试文字描述","after_open":"go_url","url":"http://message.umeng.com"}}
     * policy : {"expire_time":"2013-10-30 12:00:00"}
     * description : 测试组播通知-Android
     */

    private String appkey;
    private String timestamp;
    private String type;
    private FilterEntity filter;
    private PayloadEntity payload;
    private PolicyEntity policy;
    private String description;

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFilter(FilterEntity filter) {
        this.filter = filter;
    }

    public void setPayload(PayloadEntity payload) {
        this.payload = payload;
    }

    public void setPolicy(PolicyEntity policy) {
        this.policy = policy;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppkey() {
        return appkey;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public FilterEntity getFilter() {
        return filter;
    }

    public PayloadEntity getPayload() {
        return payload;
    }

    public PolicyEntity getPolicy() {
        return policy;
    }

    public String getDescription() {
        return description;
    }

    public static class FilterEntity {
        /**
         * where : {"and":[{"app_version":"1.0"}]}
         */

        private WhereEntity where;

        public void setWhere(WhereEntity where) {
            this.where = where;
        }

        public WhereEntity getWhere() {
            return where;
        }

        public static class WhereEntity {
            /**
             * and : [{"app_version":"1.0"}]
             */

            private List<AndEntity> and;

            public void setAnd(List<AndEntity> and) {
                this.and = and;
            }

            public List<AndEntity> getAnd() {
                return and;
            }

            public static class AndEntity {
                /**
                 * app_version : 1.0
                 */

                private String app_version;

                public void setApp_version(String app_version) {
                    this.app_version = app_version;
                }

                public String getApp_version() {
                    return app_version;
                }
            }
        }
    }

    public static class PayloadEntity {
        /**
         * display_type : notification
         * body : {"ticker":"测试提示文字","title":"测试标题","text":"测试文字描述","after_open":"go_url","url":"http://message.umeng.com"}
         */

        private String display_type;
        private BodyEntity body;

        public void setDisplay_type(String display_type) {
            this.display_type = display_type;
        }

        public void setBody(BodyEntity body) {
            this.body = body;
        }

        public String getDisplay_type() {
            return display_type;
        }

        public BodyEntity getBody() {
            return body;
        }

        public static class BodyEntity {
            /**
             * ticker : 测试提示文字
             * title : 测试标题
             * text : 测试文字描述
             * after_open : go_url
             * url : http://message.umeng.com
             */

            private String ticker;
            private String title;
            private String text;
            private String after_open;
            private String url;

            public void setTicker(String ticker) {
                this.ticker = ticker;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setText(String text) {
                this.text = text;
            }

            public void setAfter_open(String after_open) {
                this.after_open = after_open;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getTicker() {
                return ticker;
            }

            public String getTitle() {
                return title;
            }

            public String getText() {
                return text;
            }

            public String getAfter_open() {
                return after_open;
            }

            public String getUrl() {
                return url;
            }
        }
    }

    public static class PolicyEntity {
        /**
         * expire_time : 2013-10-30 12:00:00
         */

        private String expire_time;

        public void setExpire_time(String expire_time) {
            this.expire_time = expire_time;
        }

        public String getExpire_time() {
            return expire_time;
        }
    }
}
