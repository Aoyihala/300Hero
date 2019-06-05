package com.example.evenalone.a300hero.bean;

import java.util.List;

/**
 * 排名信息
 * 头像计算规则 type-15,而且两位数的type将加上00,三位数加上0
 */
public class YourRole {
    private String localruselt;

    public void setLocalruselt(String localruselt) {
        this.localruselt = localruselt;
    }

    public String getLocalruselt() {
        return localruselt;
    }

    /**
     * Result : OK
     * Role : {"RoleName":"本萌新不高兴了","RoleID":2954895377,"RoleLevel":43,"JumpValue":6701,"WinCount":599,"MatchCount":1141,"UpdateTime":"2019-05-26 19:18:03"}
     * Rank : [{"Type":195,"RankName":"尼禄本命排行","ValueName":"场数","Rank":578,"Value":"1","RankChange":1006,"RankIndex":577},{"Type":143,"RankName":"喜羊羊本命排行","ValueName":"场数","Rank":756,"Value":"1","RankChange":-99,"RankIndex":755},{"Type":170,"RankName":"两仪式本命排行","ValueName":"场数","Rank":3544,"Value":"1","RankChange":-183,"RankIndex":3543},{"Type":4,"RankName":"个人实力排行","ValueName":"实力值","Rank":22502,"Value":"378","RankChange":4242,"RankIndex":22501},{"Type":7,"RankName":"最新助攻王","ValueName":"助攻数","Rank":46758,"Value":"68","RankChange":-3089,"RankIndex":46757},{"Type":13,"RankName":"最新常胜王排行","ValueName":"胜场数","Rank":52784,"Value":"3","RankChange":-1677,"RankIndex":52783},{"Type":2,"RankName":"常胜王排行","ValueName":"胜场数","Rank":62980,"Value":"599","RankChange":363,"RankIndex":62979},{"Type":3,"RankName":"重度玩家排行","ValueName":"总场数","Rank":65109,"Value":"1141","RankChange":375,"RankIndex":65108},{"Type":10,"RankName":"最新打钱王","ValueName":"打钱数","Rank":66333,"Value":"37958","RankChange":-11781,"RankIndex":66332},{"Type":8,"RankName":"最新拆迁王","ValueName":"建筑摧毁数","Rank":67201,"Value":"2","RankChange":-11778,"RankIndex":67200},{"Type":0,"RankName":"最受欢迎玩家","ValueName":"节操值","Rank":71393,"Value":"6701","RankChange":374,"RankIndex":71392},{"Type":1,"RankName":"团队实力排行","ValueName":"实力值","Rank":75299,"Value":"1687","RankChange":461,"RankIndex":75298},{"Type":11,"RankName":"最新金钱获取王","ValueName":"获取金钱","Rank":75453,"Value":"664","RankChange":-5968,"RankIndex":75452},{"Type":5,"RankName":"最新杀人王","ValueName":"杀人数","Rank":76384,"Value":"23","RankChange":-19519,"RankIndex":76383},{"Type":12,"RankName":"最新经验获取王","ValueName":"获取经验","Rank":79442,"Value":"430","RankChange":626,"RankIndex":79441},{"Type":9,"RankName":"最新小兵终结者","ValueName":"小兵击杀数","Rank":82267,"Value":"243","RankChange":-13322,"RankIndex":82266},{"Type":14,"RankName":"最新重度玩家","ValueName":"总场数","Rank":87034,"Value":"3","RankChange":-6211,"RankIndex":87033},{"Type":6,"RankName":"最新必须死","ValueName":"死亡数","Rank":90801,"Value":"16","RankChange":-20747,"RankIndex":90800}]
     */

    private String Result;
    private RoleBean Role;
    private List<RankBean> Rank;

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public RoleBean getRole() {
        return Role;
    }

    public void setRole(RoleBean Role) {
        this.Role = Role;
    }

    public List<RankBean> getRank() {
        return Rank;
    }

    public void setRank(List<RankBean> Rank) {
        this.Rank = Rank;
    }

    public static class RoleBean {
        /**
         * RoleName : 本萌新不高兴了
         * RoleID : 2954895377
         * RoleLevel : 43
         * JumpValue : 6701
         * WinCount : 599
         * MatchCount : 1141
         * UpdateTime : 2019-05-26 19:18:03
         */

        private String RoleName;
        private long RoleID;
        private int RoleLevel;
        private int JumpValue;
        private int WinCount;
        private int MatchCount;
        private String UpdateTime;

        public String getRoleName() {
            return RoleName;
        }

        public void setRoleName(String RoleName) {
            this.RoleName = RoleName;
        }

        public long getRoleID() {
            return RoleID;
        }

        public void setRoleID(long RoleID) {
            this.RoleID = RoleID;
        }

        public int getRoleLevel() {
            return RoleLevel;
        }

        public void setRoleLevel(int RoleLevel) {
            this.RoleLevel = RoleLevel;
        }

        public int getJumpValue() {
            return JumpValue;
        }

        public void setJumpValue(int JumpValue) {
            this.JumpValue = JumpValue;
        }

        public int getWinCount() {
            return WinCount;
        }

        public void setWinCount(int WinCount) {
            this.WinCount = WinCount;
        }

        public int getMatchCount() {
            return MatchCount;
        }

        public void setMatchCount(int MatchCount) {
            this.MatchCount = MatchCount;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }
    }

    public static class RankBean {
        /**
         * Type : 195
         * RankName : 尼禄本命排行
         * ValueName : 场数
         * Rank : 578
         * Value : 1
         * RankChange : 1006
         * RankIndex : 577
         */

        private int Type;
        private String RankName;
        private String ValueName;
        private int Rank;
        private String Value;
        private int RankChange;
        private int RankIndex;

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }

        public String getRankName() {
            return RankName;
        }

        public void setRankName(String RankName) {
            this.RankName = RankName;
        }

        public String getValueName() {
            return ValueName;
        }

        public void setValueName(String ValueName) {
            this.ValueName = ValueName;
        }

        public int getRank() {
            return Rank;
        }

        public void setRank(int Rank) {
            this.Rank = Rank;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String Value) {
            this.Value = Value;
        }

        public int getRankChange() {
            return RankChange;
        }

        public void setRankChange(int RankChange) {
            this.RankChange = RankChange;
        }

        public int getRankIndex() {
            return RankIndex;
        }

        public void setRankIndex(int RankIndex) {
            this.RankIndex = RankIndex;
        }
    }
}
