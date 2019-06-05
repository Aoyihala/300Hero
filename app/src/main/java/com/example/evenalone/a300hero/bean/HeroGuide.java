package com.example.evenalone.a300hero.bean;

import java.util.List;

/**
 * 英雄战绩
 */
public class HeroGuide {

    private String localruselt;

    public void setLocalruselt(String localruselt) {
        this.localruselt = localruselt;
    }

    public String getLocalruselt() {
        return localruselt;
    }
    /**
     * Result : OK
     * List : [{"MatchID":124275991,"MatchType":1,"HeroLevel":18,"Result":1,"MatchDate":"2019-05-26 18:45:19","Hero":{"ID":128,"Name":"喜羊羊","IconFile":"herohead/chara_0128.png"}},{"MatchID":124272406,"MatchType":1,"HeroLevel":16,"Result":1,"MatchDate":"2019-05-26 17:41:44","Hero":{"ID":155,"Name":"两仪式","IconFile":"herohead/chara_0155.png"}},{"MatchID":124217556,"MatchType":1,"HeroLevel":16,"Result":2,"MatchDate":"2019-05-25 20:29:05","Hero":{"ID":155,"Name":"两仪式","IconFile":"herohead/chara_0155.png"}},{"MatchID":124214690,"MatchType":1,"HeroLevel":18,"Result":1,"MatchDate":"2019-05-25 19:19:33","Hero":{"ID":34,"Name":"雪菜","IconFile":"herohead/chara_0034.png"}},{"MatchID":124209433,"MatchType":1,"HeroLevel":18,"Result":2,"MatchDate":"2019-05-25 17:49:05","Hero":{"ID":53,"Name":"阿尔托莉雅","IconFile":"herohead/chara_0053.png"}},{"MatchID":124205817,"MatchType":1,"HeroLevel":13,"Result":2,"MatchDate":"2019-05-25 16:31:18","Hero":{"ID":35,"Name":"桔梗","IconFile":"herohead/chara_0035.png"}},{"MatchID":124168579,"MatchType":1,"HeroLevel":18,"Result":2,"MatchDate":"2019-05-24 22:34:26","Hero":{"ID":35,"Name":"桔梗","IconFile":"herohead/chara_0035.png"}},{"MatchID":124165844,"MatchType":1,"HeroLevel":18,"Result":1,"MatchDate":"2019-05-24 21:54:23","Hero":{"ID":34,"Name":"雪菜","IconFile":"herohead/chara_0034.png"}},{"MatchID":124160843,"MatchType":1,"HeroLevel":18,"Result":2,"MatchDate":"2019-05-24 20:45:17","Hero":{"ID":192,"Name":"牧濑红莉栖","IconFile":"herohead/chara_0192.png"}},{"MatchID":124054792,"MatchType":1,"HeroLevel":18,"Result":1,"MatchDate":"2019-05-21 22:47:13","Hero":{"ID":241,"Name":"我妻由乃","IconFile":"herohead/chara_0241.png"}}]
     */
    private String Result;
    private java.util.List<ListBean> List;

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public List<ListBean> getList() {
        return List;
    }

    public void setList(List<ListBean> List) {
        this.List = List;
    }

    public static class ListBean {
        /**
         * MatchID : 124275991
         * MatchType : 1
         * HeroLevel : 18
         * Result : 1
         * MatchDate : 2019-05-26 18:45:19
         * Hero : {"ID":128,"Name":"喜羊羊","IconFile":"herohead/chara_0128.png"}
         */

        private long MatchID;
        private int MatchType;
        private int HeroLevel;
        private int Result;
        private String MatchDate;
        private HeroBean Hero;

        public long getMatchID() {
            return MatchID;
        }

        public void setMatchID(int MatchID) {
            this.MatchID = MatchID;
        }

        public int getMatchType() {
            return MatchType;
        }

        public void setMatchType(int MatchType) {
            this.MatchType = MatchType;
        }

        public int getHeroLevel() {
            return HeroLevel;
        }

        public void setHeroLevel(int HeroLevel) {
            this.HeroLevel = HeroLevel;
        }

        public int getResult() {
            return Result;
        }

        public void setResult(int Result) {
            this.Result = Result;
        }

        public String getMatchDate() {
            return MatchDate;
        }

        public void setMatchDate(String MatchDate) {
            this.MatchDate = MatchDate;
        }

        public HeroBean getHero() {
            return Hero;
        }

        public void setHero(HeroBean Hero) {
            this.Hero = Hero;
        }

        public static class HeroBean {
            /**
             * ID : 128
             * Name : 喜羊羊
             * IconFile : herohead/chara_0128.png
             */

            private int ID;
            private String Name;
            private String IconFile;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getIconFile() {
                return IconFile;
            }

            public void setIconFile(String IconFile) {
                this.IconFile = IconFile;
            }
        }
    }
}
