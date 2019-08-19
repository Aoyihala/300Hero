package com.example.evenalone.a300hero.bean.newapi;

/**
 * 新的api
 * 自己的信息
 */
public class MyRole
{


    /**
     * name : {"id":"1","roleid":"1614244650","rolename":"Fallency","rolelevel":"38","elo":"4477","jumpvalue":"2936","wincount":"312","matchcount":"397","time":"2019-08-14 08:24:15"}
     */

    private NameBean name;

    public NameBean getName() {
        return name;
    }

    public void setName(NameBean name) {
        this.name = name;
    }

    public static class NameBean {
        /**
         * id : 1
         * roleid : 1614244650
         * rolename : Fallency
         * rolelevel : 38
         * elo : 4477
         * jumpvalue : 2936
         * wincount : 312
         * matchcount : 397
         * time : 2019-08-14 08:24:15
         */

        private String id;
        private String roleid;
        private String rolename;
        private String rolelevel;
        private String elo;
        private String jumpvalue;
        private String wincount;
        private String matchcount;
        private String time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRoleid() {
            return roleid;
        }

        public void setRoleid(String roleid) {
            this.roleid = roleid;
        }

        public String getRolename() {
            return rolename;
        }

        public void setRolename(String rolename) {
            this.rolename = rolename;
        }

        public String getRolelevel() {
            return rolelevel;
        }

        public void setRolelevel(String rolelevel) {
            this.rolelevel = rolelevel;
        }

        public String getElo() {
            return elo;
        }

        public void setElo(String elo) {
            this.elo = elo;
        }

        public String getJumpvalue() {
            return jumpvalue;
        }

        public void setJumpvalue(String jumpvalue) {
            this.jumpvalue = jumpvalue;
        }

        public String getWincount() {
            return wincount;
        }

        public void setWincount(String wincount) {
            this.wincount = wincount;
        }

        public String getMatchcount() {
            return matchcount;
        }

        public void setMatchcount(String matchcount) {
            this.matchcount = matchcount;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
