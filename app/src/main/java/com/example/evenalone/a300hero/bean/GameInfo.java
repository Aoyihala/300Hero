package com.example.evenalone.a300hero.bean;

import org.greenrobot.greendao.annotation.Id;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.PropertyKey;

import java.io.Serializable;
import java.util.List;

/***
 * 内容多的对局信息
 * 双方对局
 */
@SuppressWarnings(value = "")
public class GameInfo implements Serializable
{
    private static final long serialVersionUID=1l;
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
    private int Myresult;

    public void setMyresult(int myresult) {
        Myresult = myresult;
    }

    public int getMyresult() {
        return Myresult;
    }

    private String localruselt;

    public void setLocalruselt(String localruselt) {
        this.localruselt = localruselt;
    }

    public String getLocalruselt() {
        return localruselt;
    }

    /**
     * Result : OK
     * Match : {"MatchType":1,"WinSideKill":95,"LoseSideKill":55,"UsedTime":2048,"MatchDate":"2019-05-26 18:45:18","WinSide":[{"RoleName":"GG思密达し","RoleID":1613108882,"RoleLevel":23,"HeroID":86,"HeroLevel":18,"Result":1,"TeamResult":1,"IsFirstWin":0,"KillCount":12,"DeathCount":5,"AssistCount":39,"TowerDestroy":0,"KillUnitCount":23,"TotalMoney":15425,"SkillID":[8029,8035],"EquipID":[21069,21085,21067,21066,21081,21109],"RewardMoney":174,"RewardExp":372,"JumpValue":1170,"WinCount":72,"MatchCount":158,"ELO":1521,"KDA":268,"Hero":{"ID":86,"Name":"死神","IconFile":"herohead/chara_0086.png"},"Skill":[{"ID":8029,"Name":"治愈术","IconFile":"skill/ico_8029..png"},{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"}],"Equip":[{"ID":21069,"Name":"寒冬节杖","IconFile":"equip/equip_27.png"},{"ID":21085,"Name":"妖精的光辉","IconFile":"equip/acg016.png"},{"ID":21067,"Name":"时间跳跃脚镯","IconFile":"equip/equip_14.png"},{"ID":21066,"Name":"邪王真眼","IconFile":"equip/acg009.png"},{"ID":21081,"Name":"法师之靴","IconFile":"equip/equip_43.png"},{"ID":21109,"Name":"氪金猫眼","IconFile":"equip/21018.png"}]},{"RoleName":"加藤惠、神","RoleID":3493066149,"RoleLevel":36,"HeroID":227,"HeroLevel":18,"Result":1,"TeamResult":1,"IsFirstWin":0,"KillCount":21,"DeathCount":7,"AssistCount":29,"TowerDestroy":2,"KillUnitCount":163,"TotalMoney":18137,"SkillID":[8035,8026],"EquipID":[21050,21005,21109,25278,21087,21204],"RewardMoney":174,"RewardExp":248,"JumpValue":2765,"WinCount":228,"MatchCount":378,"ELO":2442,"KDA":492,"Hero":{"ID":227,"Name":"黑崎一护","IconFile":"herohead/chara_0227.png"},"Skill":[{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"},{"ID":8026,"Name":"传送","IconFile":"skill/ico_8026..png"}],"Equip":[{"ID":21050,"Name":"凤凰羽衣","IconFile":"equip/acg005.png"},{"ID":21005,"Name":"鳞甲","IconFile":"equip/21012.png"},{"ID":21109,"Name":"氪金猫眼","IconFile":"equip/21018.png"},{"ID":25278,"Name":"武术家草鞋Max","IconFile":"equip/wushujiacaoxie.png"},{"ID":21087,"Name":"遥远的蹂躏制霸","IconFile":"equip/acg018.png"},{"ID":21204,"Name":"血族亲王","IconFile":"equip/equip_6033.png"}]},{"RoleName":"半听冰紫","RoleID":3224815348,"RoleLevel":35,"HeroID":35,"HeroLevel":18,"Result":1,"TeamResult":1,"IsFirstWin":0,"KillCount":37,"DeathCount":8,"AssistCount":11,"TowerDestroy":9,"KillUnitCount":172,"TotalMoney":20976,"SkillID":[8026,8035],"EquipID":[28010,28011,21086,21120,21083,21088],"RewardMoney":190,"RewardExp":408,"JumpValue":2135,"WinCount":165,"MatchCount":290,"ELO":1931,"KDA":697,"Hero":{"ID":35,"Name":"桔梗","IconFile":"herohead/chara_0035.png"},"Skill":[{"ID":8026,"Name":"传送","IconFile":"skill/ico_8026..png"},{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"}],"Equip":[{"ID":28010,"Name":"Nice杖","IconFile":"equip/nicezhang.png"},{"ID":28011,"Name":"净魂之刃","IconFile":"equip/jinghunzhiren.png"},{"ID":21086,"Name":"index的移动教会","IconFile":"equip/acg017.png"},{"ID":21120,"Name":"蔷薇双枪","IconFile":"equip/equip_119.png"},{"ID":21083,"Name":"疾行靴","IconFile":"equip/equip_42.png"},{"ID":21088,"Name":"三圣器","IconFile":"equip/equip_71.png"}]},{"RoleName":"夏娜老爷","RoleID":808756546,"RoleLevel":32,"HeroID":167,"HeroLevel":17,"Result":1,"TeamResult":1,"IsFirstWin":0,"KillCount":1,"DeathCount":7,"AssistCount":22,"TowerDestroy":2,"KillUnitCount":99,"TotalMoney":12140,"SkillID":[8026,8035],"EquipID":[21003,25276,21029,21025,21104,21049],"RewardMoney":190,"RewardExp":408,"JumpValue":2551,"WinCount":200,"MatchCount":407,"ELO":1493,"KDA":2,"Hero":{"ID":167,"Name":"朝田诗乃","IconFile":"herohead/chara_0167.png"},"Skill":[{"ID":8026,"Name":"传送","IconFile":"skill/ico_8026..png"},{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"}],"Equip":[{"ID":21003,"Name":"玄铁剑","IconFile":"equip/equip_83.png"},{"ID":25276,"Name":"疾风之靴Max","IconFile":"equip/jifengzhixue.png"},{"ID":21029,"Name":"黄金齿轮","IconFile":"equip/equip_37.png"},{"ID":21025,"Name":"契约之戒","IconFile":"equip/equip_65.png"},{"ID":21104,"Name":"冈格尼尔之枪","IconFile":"equip/acg028.png"},{"ID":21049,"Name":"青釭剑","IconFile":"equip/acg004.png"}]},{"RoleName":"我已躺好了ン","RoleID":271202883,"RoleLevel":13,"HeroID":121,"HeroLevel":18,"Result":1,"TeamResult":1,"IsFirstWin":0,"KillCount":10,"DeathCount":5,"AssistCount":16,"TowerDestroy":0,"KillUnitCount":145,"TotalMoney":14715,"SkillID":[8035,8026],"EquipID":[21083,28010,21057,21088,21017,21011],"RewardMoney":174,"RewardExp":248,"JumpValue":775,"WinCount":29,"MatchCount":54,"ELO":1253,"KDA":138,"Hero":{"ID":121,"Name":"绯村剑心","IconFile":"herohead/chara_0121.png"},"Skill":[{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"},{"ID":8026,"Name":"传送","IconFile":"skill/ico_8026..png"}],"Equip":[{"ID":21083,"Name":"疾行靴","IconFile":"equip/equip_42.png"},{"ID":28010,"Name":"Nice杖","IconFile":"equip/nicezhang.png"},{"ID":21057,"Name":"铁碎牙","IconFile":"equip/acg026.png"},{"ID":21088,"Name":"三圣器","IconFile":"equip/equip_71.png"},{"ID":21017,"Name":"致命拳套","IconFile":"equip/equip_109.png"},{"ID":21011,"Name":"疾风匕","IconFile":"equip/equip_41.png"}]},{"RoleName":"本萌新不高兴了","RoleID":2954895377,"RoleLevel":43,"HeroID":128,"HeroLevel":18,"Result":1,"TeamResult":1,"IsFirstWin":0,"KillCount":9,"DeathCount":9,"AssistCount":42,"TowerDestroy":0,"KillUnitCount":64,"TotalMoney":14818,"SkillID":[8026,8035],"EquipID":[21088,21083,21109,21112,21061,21030],"RewardMoney":113,"RewardExp":161,"JumpValue":6696,"WinCount":599,"MatchCount":1141,"ELO":1677,"KDA":272,"Hero":{"ID":128,"Name":"喜羊羊","IconFile":"herohead/chara_0128.png"},"Skill":[{"ID":8026,"Name":"传送","IconFile":"skill/ico_8026..png"},{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"}],"Equip":[{"ID":21088,"Name":"三圣器","IconFile":"equip/equip_71.png"},{"ID":21083,"Name":"疾行靴","IconFile":"equip/equip_42.png"},{"ID":21109,"Name":"氪金猫眼","IconFile":"equip/21018.png"},{"ID":21112,"Name":"电击陷阱","IconFile":"equip/yaokongzhadan.png"},{"ID":21061,"Name":"SOS团的徽记","IconFile":"equip/acg008.png"},{"ID":21030,"Name":"润滑宝石","IconFile":"equip/equip_18.png"}]},{"RoleName":"黑奴型AI","RoleID":808465181,"RoleLevel":44,"HeroID":102,"HeroLevel":18,"Result":1,"TeamResult":1,"IsFirstWin":1,"KillCount":5,"DeathCount":14,"AssistCount":16,"TowerDestroy":1,"KillUnitCount":104,"TotalMoney":11691,"SkillID":[8035,8026],"EquipID":[28010,21076,21150,21204],"RewardMoney":362,"RewardExp":372,"JumpValue":5722,"WinCount":327,"MatchCount":676,"ELO":1594,"KDA":-62,"Hero":{"ID":102,"Name":"桐人","IconFile":"herohead/chara_0102.png"},"Skill":[{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"},{"ID":8026,"Name":"传送","IconFile":"skill/ico_8026..png"}],"Equip":[{"ID":28010,"Name":"Nice杖","IconFile":"equip/nicezhang.png"},{"ID":21076,"Name":"速度之靴","IconFile":"equip/equip_102.png"},{"ID":21150,"Name":"阐释者","IconFile":"equip/chanshizhe.png"},{"ID":21204,"Name":"血族亲王","IconFile":"equip/equip_6033.png"}]}],"LoseSide":[{"RoleName":"Master丶苏沐沐","RoleID":1089932,"RoleLevel":41,"HeroID":121,"HeroLevel":17,"Result":2,"TeamResult":0,"IsFirstWin":0,"KillCount":4,"DeathCount":17,"AssistCount":15,"TowerDestroy":0,"KillUnitCount":78,"TotalMoney":8354,"SkillID":[8035,8059],"EquipID":[21031,21032,21083,21057,21033],"RewardMoney":76,"RewardExp":160,"JumpValue":2647,"WinCount":395,"MatchCount":867,"ELO":1426,"KDA":-231,"Hero":{"ID":121,"Name":"绯村剑心","IconFile":"herohead/chara_0121.png"},"Skill":[{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"},{"ID":8059,"Name":"缴械","IconFile":"skill/ico_8059..png"}],"Equip":[{"ID":21031,"Name":"蓄力之剑","IconFile":"equip/equip_111.png"},{"ID":21032,"Name":"残废之锤","IconFile":"equip/equip_8.png"},{"ID":21083,"Name":"疾行靴","IconFile":"equip/equip_42.png"},{"ID":21057,"Name":"铁碎牙","IconFile":"equip/acg026.png"},{"ID":21033,"Name":"凶暴双刀","IconFile":"equip/equip_94.png"}]},{"RoleName":"劳模亚飞","RoleID":1074681072,"RoleLevel":34,"HeroID":91,"HeroLevel":17,"Result":2,"TeamResult":0,"IsFirstWin":0,"KillCount":4,"DeathCount":11,"AssistCount":8,"TowerDestroy":2,"KillUnitCount":158,"TotalMoney":10785,"SkillID":[8024,8035],"EquipID":[21161,21214,21060,21121,21109,21083],"RewardMoney":59,"RewardExp":62,"JumpValue":4634,"WinCount":279,"MatchCount":515,"ELO":1776,"KDA":-101,"Hero":{"ID":91,"Name":"天草四郎时贞","IconFile":"herohead/chara_0091.png"},"Skill":[{"ID":8024,"Name":"审判","IconFile":"skill/ico_8024..png"},{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"}],"Equip":[{"ID":21161,"Name":"大师球","IconFile":"equip/21161.png"},{"ID":21214,"Name":"破刃之剑","IconFile":"equip/porenzhijian.png"},{"ID":21060,"Name":"黄蔷薇","IconFile":"equip/equip_118.png"},{"ID":21121,"Name":"红蔷薇","IconFile":"equip/equip_117.png"},{"ID":21109,"Name":"氪金猫眼","IconFile":"equip/21018.png"},{"ID":21083,"Name":"疾行靴","IconFile":"equip/equip_42.png"}]},{"RoleName":"奈ね白","RoleID":538000081,"RoleLevel":43,"HeroID":236,"HeroLevel":18,"Result":2,"TeamResult":0,"IsFirstWin":0,"KillCount":14,"DeathCount":10,"AssistCount":18,"TowerDestroy":0,"KillUnitCount":83,"TotalMoney":12484,"SkillID":[8035,8026],"EquipID":[21085,21093,25279,21010,21029,21028],"RewardMoney":70,"RewardExp":74,"JumpValue":6274,"WinCount":593,"MatchCount":1042,"ELO":2593,"KDA":24,"Hero":{"ID":236,"Name":"佩姬","IconFile":"herohead/chara_0236.png"},"Skill":[{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"},{"ID":8026,"Name":"传送","IconFile":"skill/ico_8026..png"}],"Equip":[{"ID":21085,"Name":"妖精的光辉","IconFile":"equip/acg016.png"},{"ID":21093,"Name":"魔剑瓦莱汀","IconFile":"equip/acg022.png"},{"ID":25279,"Name":"法师之靴Max","IconFile":"equip/fashizhixue.png"},{"ID":21010,"Name":"毁灭魔杖","IconFile":"equip/equip_123.png"},{"ID":21029,"Name":"黄金齿轮","IconFile":"equip/equip_37.png"},{"ID":21028,"Name":"单面金币","IconFile":"equip/equip_13.png"}]},{"RoleName":"她的名字","RoleID":1075808690,"RoleLevel":30,"HeroID":224,"HeroLevel":18,"Result":2,"TeamResult":0,"IsFirstWin":0,"KillCount":9,"DeathCount":11,"AssistCount":16,"TowerDestroy":0,"KillUnitCount":152,"TotalMoney":12583,"SkillID":[8035,8026],"EquipID":[21204,21088,21094,21023,21082],"RewardMoney":118,"RewardExp":62,"JumpValue":2066,"WinCount":123,"MatchCount":260,"ELO":1509,"KDA":18,"Hero":{"ID":224,"Name":"君莫笑","IconFile":"herohead/chara_0224.png"},"Skill":[{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"},{"ID":8026,"Name":"传送","IconFile":"skill/ico_8026..png"}],"Equip":[{"ID":21204,"Name":"血族亲王","IconFile":"equip/equip_6033.png"},{"ID":21088,"Name":"三圣器","IconFile":"equip/equip_71.png"},{"ID":21094,"Name":"霜之哀伤","IconFile":"equip/acg023.png"},{"ID":21023,"Name":"契约之剑","IconFile":"equip/equip_64.png"},{"ID":21082,"Name":"黑曜石之靴","IconFile":"equip/equip_81.png"}]},{"RoleName":"最后的易大师","RoleID":2954660610,"RoleLevel":45,"HeroID":135,"HeroLevel":17,"Result":2,"TeamResult":0,"IsFirstWin":0,"KillCount":5,"DeathCount":14,"AssistCount":13,"TowerDestroy":0,"KillUnitCount":57,"TotalMoney":9209,"SkillID":[8035,8026],"EquipID":[21081,21085,21036,21059,21046],"RewardMoney":176,"RewardExp":93,"JumpValue":5777,"WinCount":465,"MatchCount":984,"ELO":1723,"KDA":-190,"Hero":{"ID":135,"Name":"十六夜咲夜","IconFile":"herohead/chara_0135.png"},"Skill":[{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"},{"ID":8026,"Name":"传送","IconFile":"skill/ico_8026..png"}],"Equip":[{"ID":21081,"Name":"法师之靴","IconFile":"equip/equip_43.png"},{"ID":21085,"Name":"妖精的光辉","IconFile":"equip/acg016.png"},{"ID":21036,"Name":"真名法典","IconFile":"equip/equip_21.png"},{"ID":21059,"Name":"无尽水源","IconFile":"equip/equip_89.png"},{"ID":21046,"Name":"死亡笔记","IconFile":"equip/acg001.png"}]},{"RoleName":"诛情KK","RoleID":2953915935,"RoleLevel":38,"HeroID":66,"HeroLevel":17,"Result":2,"TeamResult":0,"IsFirstWin":0,"KillCount":4,"DeathCount":15,"AssistCount":13,"TowerDestroy":0,"KillUnitCount":103,"TotalMoney":10285,"SkillID":[8035,8026],"EquipID":[21046,21081,21010,21025,21236],"RewardMoney":88,"RewardExp":93,"JumpValue":1179,"WinCount":263,"MatchCount":538,"ELO":1565,"KDA":-270,"Hero":{"ID":66,"Name":"蛇血舞姬","IconFile":"herohead/chara_0066.png"},"Skill":[{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"},{"ID":8026,"Name":"传送","IconFile":"skill/ico_8026..png"}],"Equip":[{"ID":21046,"Name":"死亡笔记","IconFile":"equip/acg001.png"},{"ID":21081,"Name":"法师之靴","IconFile":"equip/equip_43.png"},{"ID":21010,"Name":"毁灭魔杖","IconFile":"equip/equip_123.png"},{"ID":21025,"Name":"契约之戒","IconFile":"equip/equip_65.png"},{"ID":21236,"Name":"银镞","IconFile":"equip/21236.png"}]},{"RoleName":"铁锁链舟而行","RoleID":1345752439,"RoleLevel":13,"HeroID":107,"HeroLevel":18,"Result":2,"TeamResult":0,"IsFirstWin":0,"KillCount":15,"DeathCount":17,"AssistCount":14,"TowerDestroy":0,"KillUnitCount":118,"TotalMoney":13528,"SkillID":[8026,8023],"EquipID":[25275,28010,21049,21023,21207,21083],"RewardMoney":0,"RewardExp":0,"JumpValue":650,"WinCount":16,"MatchCount":22,"ELO":1397,"KDA":-54,"Hero":{"ID":107,"Name":"三笠","IconFile":"herohead/chara_0107.png"},"Skill":[{"ID":8026,"Name":"传送","IconFile":"skill/ico_8026..png"},{"ID":8023,"Name":"复活","IconFile":"skill/ico_8023..png"}],"Equip":[{"ID":25275,"Name":"特种瓦斯气罐","IconFile":"equip/tezhongwasiqiguan.png"},{"ID":28010,"Name":"Nice杖","IconFile":"equip/nicezhang.png"},{"ID":21049,"Name":"青釭剑","IconFile":"equip/acg004.png"},{"ID":21023,"Name":"契约之剑","IconFile":"equip/equip_64.png"},{"ID":21207,"Name":"绯想之剑","IconFile":"equip/acg013.png"},{"ID":21083,"Name":"疾行靴","IconFile":"equip/equip_42.png"}]}],"FindTs":1558952409,"FindCount":1}
     */

    private String Result;
    private MatchBean Match;

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public MatchBean getMatch() {
        return Match;
    }

    public void setMatch(MatchBean Match) {
        this.Match = Match;
    }

    public static class MatchBean implements Serializable {
        private static final long serialVersionUID=2l;
        /**
         * MatchType : 1
         * WinSideKill : 95
         * LoseSideKill : 55
         * UsedTime : 2048
         * MatchDate : 2019-05-26 18:45:18
         * WinSide : [{"RoleName":"GG思密达し","RoleID":1613108882,"RoleLevel":23,"HeroID":86,"HeroLevel":18,"Result":1,"TeamResult":1,"IsFirstWin":0,"KillCount":12,"DeathCount":5,"AssistCount":39,"TowerDestroy":0,"KillUnitCount":23,"TotalMoney":15425,"SkillID":[8029,8035],"EquipID":[21069,21085,21067,21066,21081,21109],"RewardMoney":174,"RewardExp":372,"JumpValue":1170,"WinCount":72,"MatchCount":158,"ELO":1521,"KDA":268,"Hero":{"ID":86,"Name":"死神","IconFile":"herohead/chara_0086.png"},"Skill":[{"ID":8029,"Name":"治愈术","IconFile":"skill/ico_8029..png"},{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"}],"Equip":[{"ID":21069,"Name":"寒冬节杖","IconFile":"equip/equip_27.png"},{"ID":21085,"Name":"妖精的光辉","IconFile":"equip/acg016.png"},{"ID":21067,"Name":"时间跳跃脚镯","IconFile":"equip/equip_14.png"},{"ID":21066,"Name":"邪王真眼","IconFile":"equip/acg009.png"},{"ID":21081,"Name":"法师之靴","IconFile":"equip/equip_43.png"},{"ID":21109,"Name":"氪金猫眼","IconFile":"equip/21018.png"}]},{"RoleName":"加藤惠、神","RoleID":3493066149,"RoleLevel":36,"HeroID":227,"HeroLevel":18,"Result":1,"TeamResult":1,"IsFirstWin":0,"KillCount":21,"DeathCount":7,"AssistCount":29,"TowerDestroy":2,"KillUnitCount":163,"TotalMoney":18137,"SkillID":[8035,8026],"EquipID":[21050,21005,21109,25278,21087,21204],"RewardMoney":174,"RewardExp":248,"JumpValue":2765,"WinCount":228,"MatchCount":378,"ELO":2442,"KDA":492,"Hero":{"ID":227,"Name":"黑崎一护","IconFile":"herohead/chara_0227.png"},"Skill":[{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"},{"ID":8026,"Name":"传送","IconFile":"skill/ico_8026..png"}],"Equip":[{"ID":21050,"Name":"凤凰羽衣","IconFile":"equip/acg005.png"},{"ID":21005,"Name":"鳞甲","IconFile":"equip/21012.png"},{"ID":21109,"Name":"氪金猫眼","IconFile":"equip/21018.png"},{"ID":25278,"Name":"武术家草鞋Max","IconFile":"equip/wushujiacaoxie.png"},{"ID":21087,"Name":"遥远的蹂躏制霸","IconFile":"equip/acg018.png"},{"ID":21204,"Name":"血族亲王","IconFile":"equip/equip_6033.png"}]},{"RoleName":"半听冰紫","RoleID":3224815348,"RoleLevel":35,"HeroID":35,"HeroLevel":18,"Result":1,"TeamResult":1,"IsFirstWin":0,"KillCount":37,"DeathCount":8,"AssistCount":11,"TowerDestroy":9,"KillUnitCount":172,"TotalMoney":20976,"SkillID":[8026,8035],"EquipID":[28010,28011,21086,21120,21083,21088],"RewardMoney":190,"RewardExp":408,"JumpValue":2135,"WinCount":165,"MatchCount":290,"ELO":1931,"KDA":697,"Hero":{"ID":35,"Name":"桔梗","IconFile":"herohead/chara_0035.png"},"Skill":[{"ID":8026,"Name":"传送","IconFile":"skill/ico_8026..png"},{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"}],"Equip":[{"ID":28010,"Name":"Nice杖","IconFile":"equip/nicezhang.png"},{"ID":28011,"Name":"净魂之刃","IconFile":"equip/jinghunzhiren.png"},{"ID":21086,"Name":"index的移动教会","IconFile":"equip/acg017.png"},{"ID":21120,"Name":"蔷薇双枪","IconFile":"equip/equip_119.png"},{"ID":21083,"Name":"疾行靴","IconFile":"equip/equip_42.png"},{"ID":21088,"Name":"三圣器","IconFile":"equip/equip_71.png"}]},{"RoleName":"夏娜老爷","RoleID":808756546,"RoleLevel":32,"HeroID":167,"HeroLevel":17,"Result":1,"TeamResult":1,"IsFirstWin":0,"KillCount":1,"DeathCount":7,"AssistCount":22,"TowerDestroy":2,"KillUnitCount":99,"TotalMoney":12140,"SkillID":[8026,8035],"EquipID":[21003,25276,21029,21025,21104,21049],"RewardMoney":190,"RewardExp":408,"JumpValue":2551,"WinCount":200,"MatchCount":407,"ELO":1493,"KDA":2,"Hero":{"ID":167,"Name":"朝田诗乃","IconFile":"herohead/chara_0167.png"},"Skill":[{"ID":8026,"Name":"传送","IconFile":"skill/ico_8026..png"},{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"}],"Equip":[{"ID":21003,"Name":"玄铁剑","IconFile":"equip/equip_83.png"},{"ID":25276,"Name":"疾风之靴Max","IconFile":"equip/jifengzhixue.png"},{"ID":21029,"Name":"黄金齿轮","IconFile":"equip/equip_37.png"},{"ID":21025,"Name":"契约之戒","IconFile":"equip/equip_65.png"},{"ID":21104,"Name":"冈格尼尔之枪","IconFile":"equip/acg028.png"},{"ID":21049,"Name":"青釭剑","IconFile":"equip/acg004.png"}]},{"RoleName":"我已躺好了ン","RoleID":271202883,"RoleLevel":13,"HeroID":121,"HeroLevel":18,"Result":1,"TeamResult":1,"IsFirstWin":0,"KillCount":10,"DeathCount":5,"AssistCount":16,"TowerDestroy":0,"KillUnitCount":145,"TotalMoney":14715,"SkillID":[8035,8026],"EquipID":[21083,28010,21057,21088,21017,21011],"RewardMoney":174,"RewardExp":248,"JumpValue":775,"WinCount":29,"MatchCount":54,"ELO":1253,"KDA":138,"Hero":{"ID":121,"Name":"绯村剑心","IconFile":"herohead/chara_0121.png"},"Skill":[{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"},{"ID":8026,"Name":"传送","IconFile":"skill/ico_8026..png"}],"Equip":[{"ID":21083,"Name":"疾行靴","IconFile":"equip/equip_42.png"},{"ID":28010,"Name":"Nice杖","IconFile":"equip/nicezhang.png"},{"ID":21057,"Name":"铁碎牙","IconFile":"equip/acg026.png"},{"ID":21088,"Name":"三圣器","IconFile":"equip/equip_71.png"},{"ID":21017,"Name":"致命拳套","IconFile":"equip/equip_109.png"},{"ID":21011,"Name":"疾风匕","IconFile":"equip/equip_41.png"}]},{"RoleName":"本萌新不高兴了","RoleID":2954895377,"RoleLevel":43,"HeroID":128,"HeroLevel":18,"Result":1,"TeamResult":1,"IsFirstWin":0,"KillCount":9,"DeathCount":9,"AssistCount":42,"TowerDestroy":0,"KillUnitCount":64,"TotalMoney":14818,"SkillID":[8026,8035],"EquipID":[21088,21083,21109,21112,21061,21030],"RewardMoney":113,"RewardExp":161,"JumpValue":6696,"WinCount":599,"MatchCount":1141,"ELO":1677,"KDA":272,"Hero":{"ID":128,"Name":"喜羊羊","IconFile":"herohead/chara_0128.png"},"Skill":[{"ID":8026,"Name":"传送","IconFile":"skill/ico_8026..png"},{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"}],"Equip":[{"ID":21088,"Name":"三圣器","IconFile":"equip/equip_71.png"},{"ID":21083,"Name":"疾行靴","IconFile":"equip/equip_42.png"},{"ID":21109,"Name":"氪金猫眼","IconFile":"equip/21018.png"},{"ID":21112,"Name":"电击陷阱","IconFile":"equip/yaokongzhadan.png"},{"ID":21061,"Name":"SOS团的徽记","IconFile":"equip/acg008.png"},{"ID":21030,"Name":"润滑宝石","IconFile":"equip/equip_18.png"}]},{"RoleName":"黑奴型AI","RoleID":808465181,"RoleLevel":44,"HeroID":102,"HeroLevel":18,"Result":1,"TeamResult":1,"IsFirstWin":1,"KillCount":5,"DeathCount":14,"AssistCount":16,"TowerDestroy":1,"KillUnitCount":104,"TotalMoney":11691,"SkillID":[8035,8026],"EquipID":[28010,21076,21150,21204],"RewardMoney":362,"RewardExp":372,"JumpValue":5722,"WinCount":327,"MatchCount":676,"ELO":1594,"KDA":-62,"Hero":{"ID":102,"Name":"桐人","IconFile":"herohead/chara_0102.png"},"Skill":[{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"},{"ID":8026,"Name":"传送","IconFile":"skill/ico_8026..png"}],"Equip":[{"ID":28010,"Name":"Nice杖","IconFile":"equip/nicezhang.png"},{"ID":21076,"Name":"速度之靴","IconFile":"equip/equip_102.png"},{"ID":21150,"Name":"阐释者","IconFile":"equip/chanshizhe.png"},{"ID":21204,"Name":"血族亲王","IconFile":"equip/equip_6033.png"}]}]
         * LoseSide : [{"RoleName":"Master丶苏沐沐","RoleID":1089932,"RoleLevel":41,"HeroID":121,"HeroLevel":17,"Result":2,"TeamResult":0,"IsFirstWin":0,"KillCount":4,"DeathCount":17,"AssistCount":15,"TowerDestroy":0,"KillUnitCount":78,"TotalMoney":8354,"SkillID":[8035,8059],"EquipID":[21031,21032,21083,21057,21033],"RewardMoney":76,"RewardExp":160,"JumpValue":2647,"WinCount":395,"MatchCount":867,"ELO":1426,"KDA":-231,"Hero":{"ID":121,"Name":"绯村剑心","IconFile":"herohead/chara_0121.png"},"Skill":[{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"},{"ID":8059,"Name":"缴械","IconFile":"skill/ico_8059..png"}],"Equip":[{"ID":21031,"Name":"蓄力之剑","IconFile":"equip/equip_111.png"},{"ID":21032,"Name":"残废之锤","IconFile":"equip/equip_8.png"},{"ID":21083,"Name":"疾行靴","IconFile":"equip/equip_42.png"},{"ID":21057,"Name":"铁碎牙","IconFile":"equip/acg026.png"},{"ID":21033,"Name":"凶暴双刀","IconFile":"equip/equip_94.png"}]},{"RoleName":"劳模亚飞","RoleID":1074681072,"RoleLevel":34,"HeroID":91,"HeroLevel":17,"Result":2,"TeamResult":0,"IsFirstWin":0,"KillCount":4,"DeathCount":11,"AssistCount":8,"TowerDestroy":2,"KillUnitCount":158,"TotalMoney":10785,"SkillID":[8024,8035],"EquipID":[21161,21214,21060,21121,21109,21083],"RewardMoney":59,"RewardExp":62,"JumpValue":4634,"WinCount":279,"MatchCount":515,"ELO":1776,"KDA":-101,"Hero":{"ID":91,"Name":"天草四郎时贞","IconFile":"herohead/chara_0091.png"},"Skill":[{"ID":8024,"Name":"审判","IconFile":"skill/ico_8024..png"},{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"}],"Equip":[{"ID":21161,"Name":"大师球","IconFile":"equip/21161.png"},{"ID":21214,"Name":"破刃之剑","IconFile":"equip/porenzhijian.png"},{"ID":21060,"Name":"黄蔷薇","IconFile":"equip/equip_118.png"},{"ID":21121,"Name":"红蔷薇","IconFile":"equip/equip_117.png"},{"ID":21109,"Name":"氪金猫眼","IconFile":"equip/21018.png"},{"ID":21083,"Name":"疾行靴","IconFile":"equip/equip_42.png"}]},{"RoleName":"奈ね白","RoleID":538000081,"RoleLevel":43,"HeroID":236,"HeroLevel":18,"Result":2,"TeamResult":0,"IsFirstWin":0,"KillCount":14,"DeathCount":10,"AssistCount":18,"TowerDestroy":0,"KillUnitCount":83,"TotalMoney":12484,"SkillID":[8035,8026],"EquipID":[21085,21093,25279,21010,21029,21028],"RewardMoney":70,"RewardExp":74,"JumpValue":6274,"WinCount":593,"MatchCount":1042,"ELO":2593,"KDA":24,"Hero":{"ID":236,"Name":"佩姬","IconFile":"herohead/chara_0236.png"},"Skill":[{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"},{"ID":8026,"Name":"传送","IconFile":"skill/ico_8026..png"}],"Equip":[{"ID":21085,"Name":"妖精的光辉","IconFile":"equip/acg016.png"},{"ID":21093,"Name":"魔剑瓦莱汀","IconFile":"equip/acg022.png"},{"ID":25279,"Name":"法师之靴Max","IconFile":"equip/fashizhixue.png"},{"ID":21010,"Name":"毁灭魔杖","IconFile":"equip/equip_123.png"},{"ID":21029,"Name":"黄金齿轮","IconFile":"equip/equip_37.png"},{"ID":21028,"Name":"单面金币","IconFile":"equip/equip_13.png"}]},{"RoleName":"她的名字","RoleID":1075808690,"RoleLevel":30,"HeroID":224,"HeroLevel":18,"Result":2,"TeamResult":0,"IsFirstWin":0,"KillCount":9,"DeathCount":11,"AssistCount":16,"TowerDestroy":0,"KillUnitCount":152,"TotalMoney":12583,"SkillID":[8035,8026],"EquipID":[21204,21088,21094,21023,21082],"RewardMoney":118,"RewardExp":62,"JumpValue":2066,"WinCount":123,"MatchCount":260,"ELO":1509,"KDA":18,"Hero":{"ID":224,"Name":"君莫笑","IconFile":"herohead/chara_0224.png"},"Skill":[{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"},{"ID":8026,"Name":"传送","IconFile":"skill/ico_8026..png"}],"Equip":[{"ID":21204,"Name":"血族亲王","IconFile":"equip/equip_6033.png"},{"ID":21088,"Name":"三圣器","IconFile":"equip/equip_71.png"},{"ID":21094,"Name":"霜之哀伤","IconFile":"equip/acg023.png"},{"ID":21023,"Name":"契约之剑","IconFile":"equip/equip_64.png"},{"ID":21082,"Name":"黑曜石之靴","IconFile":"equip/equip_81.png"}]},{"RoleName":"最后的易大师","RoleID":2954660610,"RoleLevel":45,"HeroID":135,"HeroLevel":17,"Result":2,"TeamResult":0,"IsFirstWin":0,"KillCount":5,"DeathCount":14,"AssistCount":13,"TowerDestroy":0,"KillUnitCount":57,"TotalMoney":9209,"SkillID":[8035,8026],"EquipID":[21081,21085,21036,21059,21046],"RewardMoney":176,"RewardExp":93,"JumpValue":5777,"WinCount":465,"MatchCount":984,"ELO":1723,"KDA":-190,"Hero":{"ID":135,"Name":"十六夜咲夜","IconFile":"herohead/chara_0135.png"},"Skill":[{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"},{"ID":8026,"Name":"传送","IconFile":"skill/ico_8026..png"}],"Equip":[{"ID":21081,"Name":"法师之靴","IconFile":"equip/equip_43.png"},{"ID":21085,"Name":"妖精的光辉","IconFile":"equip/acg016.png"},{"ID":21036,"Name":"真名法典","IconFile":"equip/equip_21.png"},{"ID":21059,"Name":"无尽水源","IconFile":"equip/equip_89.png"},{"ID":21046,"Name":"死亡笔记","IconFile":"equip/acg001.png"}]},{"RoleName":"诛情KK","RoleID":2953915935,"RoleLevel":38,"HeroID":66,"HeroLevel":17,"Result":2,"TeamResult":0,"IsFirstWin":0,"KillCount":4,"DeathCount":15,"AssistCount":13,"TowerDestroy":0,"KillUnitCount":103,"TotalMoney":10285,"SkillID":[8035,8026],"EquipID":[21046,21081,21010,21025,21236],"RewardMoney":88,"RewardExp":93,"JumpValue":1179,"WinCount":263,"MatchCount":538,"ELO":1565,"KDA":-270,"Hero":{"ID":66,"Name":"蛇血舞姬","IconFile":"herohead/chara_0066.png"},"Skill":[{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"},{"ID":8026,"Name":"传送","IconFile":"skill/ico_8026..png"}],"Equip":[{"ID":21046,"Name":"死亡笔记","IconFile":"equip/acg001.png"},{"ID":21081,"Name":"法师之靴","IconFile":"equip/equip_43.png"},{"ID":21010,"Name":"毁灭魔杖","IconFile":"equip/equip_123.png"},{"ID":21025,"Name":"契约之戒","IconFile":"equip/equip_65.png"},{"ID":21236,"Name":"银镞","IconFile":"equip/21236.png"}]},{"RoleName":"铁锁链舟而行","RoleID":1345752439,"RoleLevel":13,"HeroID":107,"HeroLevel":18,"Result":2,"TeamResult":0,"IsFirstWin":0,"KillCount":15,"DeathCount":17,"AssistCount":14,"TowerDestroy":0,"KillUnitCount":118,"TotalMoney":13528,"SkillID":[8026,8023],"EquipID":[25275,28010,21049,21023,21207,21083],"RewardMoney":0,"RewardExp":0,"JumpValue":650,"WinCount":16,"MatchCount":22,"ELO":1397,"KDA":-54,"Hero":{"ID":107,"Name":"三笠","IconFile":"herohead/chara_0107.png"},"Skill":[{"ID":8026,"Name":"传送","IconFile":"skill/ico_8026..png"},{"ID":8023,"Name":"复活","IconFile":"skill/ico_8023..png"}],"Equip":[{"ID":25275,"Name":"特种瓦斯气罐","IconFile":"equip/tezhongwasiqiguan.png"},{"ID":28010,"Name":"Nice杖","IconFile":"equip/nicezhang.png"},{"ID":21049,"Name":"青釭剑","IconFile":"equip/acg004.png"},{"ID":21023,"Name":"契约之剑","IconFile":"equip/equip_64.png"},{"ID":21207,"Name":"绯想之剑","IconFile":"equip/acg013.png"},{"ID":21083,"Name":"疾行靴","IconFile":"equip/equip_42.png"}]}]
         * FindTs : 1558952409
         * FindCount : 1
         */

        private int MatchType;
        private int WinSideKill;
        private int LoseSideKill;
        private int UsedTime;
        private String MatchDate;
        private int FindTs;
        private int FindCount;
        private List<WinSideBean> WinSide;
        private List<LoseSideBean> LoseSide;

        public int getMatchType() {
            return MatchType;
        }

        public void setMatchType(int MatchType) {
            this.MatchType = MatchType;
        }

        public int getWinSideKill() {
            return WinSideKill;
        }

        public void setWinSideKill(int WinSideKill) {
            this.WinSideKill = WinSideKill;
        }

        public int getLoseSideKill() {
            return LoseSideKill;
        }

        public void setLoseSideKill(int LoseSideKill) {
            this.LoseSideKill = LoseSideKill;
        }

        public int getUsedTime() {
            return UsedTime;
        }

        public void setUsedTime(int UsedTime) {
            this.UsedTime = UsedTime;
        }

        public String getMatchDate() {
            return MatchDate;
        }

        public void setMatchDate(String MatchDate) {
            this.MatchDate = MatchDate;
        }

        public int getFindTs() {
            return FindTs;
        }

        public void setFindTs(int FindTs) {
            this.FindTs = FindTs;
        }

        public int getFindCount() {
            return FindCount;
        }

        public void setFindCount(int FindCount) {
            this.FindCount = FindCount;
        }

        public List<WinSideBean> getWinSide() {
            return WinSide;
        }

        public void setWinSide(List<WinSideBean> WinSide) {
            this.WinSide = WinSide;
        }

        public List<LoseSideBean> getLoseSide() {
            return LoseSide;
        }

        public void setLoseSide(List<LoseSideBean> LoseSide) {
            this.LoseSide = LoseSide;
        }

        public static class WinSideBean implements Serializable {
            private static final long serialVersionUID=3l;
            /**
             * RoleName : GG思密达し
             * RoleID : 1613108882
             * RoleLevel : 23
             * HeroID : 86
             * HeroLevel : 18
             * Result : 1
             * TeamResult : 1
             * IsFirstWin : 0
             * KillCount : 12
             * DeathCount : 5
             * AssistCount : 39
             * TowerDestroy : 0
             * KillUnitCount : 23
             * TotalMoney : 15425
             * SkillID : [8029,8035]
             * EquipID : [21069,21085,21067,21066,21081,21109]
             * RewardMoney : 174
             * RewardExp : 372
             * JumpValue : 1170
             * WinCount : 72
             * MatchCount : 158
             * ELO : 1521
             * KDA : 268
             * Hero : {"ID":86,"Name":"死神","IconFile":"herohead/chara_0086.png"}
             * Skill : [{"ID":8029,"Name":"治愈术","IconFile":"skill/ico_8029..png"},{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"}]
             * Equip : [{"ID":21069,"Name":"寒冬节杖","IconFile":"equip/equip_27.png"},{"ID":21085,"Name":"妖精的光辉","IconFile":"equip/acg016.png"},{"ID":21067,"Name":"时间跳跃脚镯","IconFile":"equip/equip_14.png"},{"ID":21066,"Name":"邪王真眼","IconFile":"equip/acg009.png"},{"ID":21081,"Name":"法师之靴","IconFile":"equip/equip_43.png"},{"ID":21109,"Name":"氪金猫眼","IconFile":"equip/21018.png"}]
             */

            private String RoleName;
            private long RoleID;
            private int RoleLevel;
            private int HeroID;
            private int HeroLevel;
            private int Result;
            private int TeamResult;
            private int IsFirstWin;
            private int KillCount;
            private int DeathCount;
            private int AssistCount;
            private int TowerDestroy;
            private int KillUnitCount;
            private long TotalMoney;
            private int RewardMoney;
            private int RewardExp;
            private int JumpValue;
            private int WinCount;
            private int MatchCount;
            private int ELO;
            private int KDA;
            private HeroBean Hero;
            private List<Integer> SkillID;
            private List<Integer> EquipID;
            private List<SkillBean> Skill;
            private List<EquipBean> Equip;

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

            public int getHeroID() {
                return HeroID;
            }

            public void setHeroID(int HeroID) {
                this.HeroID = HeroID;
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

            public int getTeamResult() {
                return TeamResult;
            }

            public void setTeamResult(int TeamResult) {
                this.TeamResult = TeamResult;
            }

            public int getIsFirstWin() {
                return IsFirstWin;
            }

            public void setIsFirstWin(int IsFirstWin) {
                this.IsFirstWin = IsFirstWin;
            }

            public int getKillCount() {
                return KillCount;
            }

            public void setKillCount(int KillCount) {
                this.KillCount = KillCount;
            }

            public int getDeathCount() {
                return DeathCount;
            }

            public void setDeathCount(int DeathCount) {
                this.DeathCount = DeathCount;
            }

            public int getAssistCount() {
                return AssistCount;
            }

            public void setAssistCount(int AssistCount) {
                this.AssistCount = AssistCount;
            }

            public int getTowerDestroy() {
                return TowerDestroy;
            }

            public void setTowerDestroy(int TowerDestroy) {
                this.TowerDestroy = TowerDestroy;
            }

            public int getKillUnitCount() {
                return KillUnitCount;
            }

            public void setKillUnitCount(int KillUnitCount) {
                this.KillUnitCount = KillUnitCount;
            }

            public long getTotalMoney() {
                return TotalMoney;
            }

            public void setTotalMoney(long TotalMoney) {
                this.TotalMoney = TotalMoney;
            }

            public int getRewardMoney() {
                return RewardMoney;
            }

            public void setRewardMoney(int RewardMoney) {
                this.RewardMoney = RewardMoney;
            }

            public int getRewardExp() {
                return RewardExp;
            }

            public void setRewardExp(int RewardExp) {
                this.RewardExp = RewardExp;
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

            public int getELO() {
                return ELO;
            }

            public void setELO(int ELO) {
                this.ELO = ELO;
            }

            public int getKDA() {
                return KDA;
            }

            public void setKDA(int KDA) {
                this.KDA = KDA;
            }

            public HeroBean getHero() {
                return Hero;
            }

            public void setHero(HeroBean Hero) {
                this.Hero = Hero;
            }

            public List<Integer> getSkillID() {
                return SkillID;
            }

            public void setSkillID(List<Integer> SkillID) {
                this.SkillID = SkillID;
            }

            public List<Integer> getEquipID() {
                return EquipID;
            }

            public void setEquipID(List<Integer> EquipID) {
                this.EquipID = EquipID;
            }

            public List<SkillBean> getSkill() {
                return Skill;
            }

            public void setSkill(List<SkillBean> Skill) {
                this.Skill = Skill;
            }

            public List<EquipBean> getEquip() {
                return Equip;
            }

            public void setEquip(List<EquipBean> Equip) {
                this.Equip = Equip;
            }

            public static class HeroBean implements Serializable {
                /**
                 * ID : 86
                 * Name : 死神
                 * IconFile : herohead/chara_0086.png
                 */
                private static final long serialVersionUID=4l;
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

            public static class SkillBean implements Serializable {
                /**
                 * ID : 8029
                 * Name : 治愈术
                 * IconFile : skill/ico_8029..png
                 */
                private static final long serialVersionUID=5l;
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

            public static class EquipBean implements Serializable {
                /**
                 * ID : 21069
                 * Name : 寒冬节杖
                 * IconFile : equip/equip_27.png
                 */
                private static final long serialVersionUID=6l;
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

        public static class LoseSideBean implements Serializable {
            /**
             * RoleName : Master丶苏沐沐
             * RoleID : 1089932
             * RoleLevel : 41
             * HeroID : 121
             * HeroLevel : 17
             * Result : 2
             * TeamResult : 0
             * IsFirstWin : 0
             * KillCount : 4
             * DeathCount : 17
             * AssistCount : 15
             * TowerDestroy : 0
             * KillUnitCount : 78
             * TotalMoney : 8354
             * SkillID : [8035,8059]
             * EquipID : [21031,21032,21083,21057,21033]
             * RewardMoney : 76
             * RewardExp : 160
             * JumpValue : 2647
             * WinCount : 395
             * MatchCount : 867
             * ELO : 1426
             * KDA : -231
             * Hero : {"ID":121,"Name":"绯村剑心","IconFile":"herohead/chara_0121.png"}
             * Skill : [{"ID":8035,"Name":"闪现","IconFile":"skill/ico_8035..png"},{"ID":8059,"Name":"缴械","IconFile":"skill/ico_8059..png"}]
             * Equip : [{"ID":21031,"Name":"蓄力之剑","IconFile":"equip/equip_111.png"},{"ID":21032,"Name":"残废之锤","IconFile":"equip/equip_8.png"},{"ID":21083,"Name":"疾行靴","IconFile":"equip/equip_42.png"},{"ID":21057,"Name":"铁碎牙","IconFile":"equip/acg026.png"},{"ID":21033,"Name":"凶暴双刀","IconFile":"equip/equip_94.png"}]
             */
            private static final long serialVersionUID=7l;
            private String RoleName;
            private long RoleID;
            private int RoleLevel;
            private int HeroID;
            private int HeroLevel;
            private int Result;
            private int TeamResult;
            private int IsFirstWin;
            private int KillCount;
            private int DeathCount;
            private int AssistCount;
            private int TowerDestroy;
            private int KillUnitCount;
            private long TotalMoney;
            private int RewardMoney;
            private int RewardExp;
            private int JumpValue;
            private int WinCount;
            private int MatchCount;
            private int ELO;
            private int KDA;
            private HeroBeanX Hero;
            private List<Integer> SkillID;
            private List<Integer> EquipID;
            private List<SkillBeanX> Skill;
            private List<EquipBeanX> Equip;

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

            public int getHeroID() {
                return HeroID;
            }

            public void setHeroID(int HeroID) {
                this.HeroID = HeroID;
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

            public int getTeamResult() {
                return TeamResult;
            }

            public void setTeamResult(int TeamResult) {
                this.TeamResult = TeamResult;
            }

            public int getIsFirstWin() {
                return IsFirstWin;
            }

            public void setIsFirstWin(int IsFirstWin) {
                this.IsFirstWin = IsFirstWin;
            }

            public int getKillCount() {
                return KillCount;
            }

            public void setKillCount(int KillCount) {
                this.KillCount = KillCount;
            }

            public int getDeathCount() {
                return DeathCount;
            }

            public void setDeathCount(int DeathCount) {
                this.DeathCount = DeathCount;
            }

            public int getAssistCount() {
                return AssistCount;
            }

            public void setAssistCount(int AssistCount) {
                this.AssistCount = AssistCount;
            }

            public int getTowerDestroy() {
                return TowerDestroy;
            }

            public void setTowerDestroy(int TowerDestroy) {
                this.TowerDestroy = TowerDestroy;
            }

            public int getKillUnitCount() {
                return KillUnitCount;
            }

            public void setKillUnitCount(int KillUnitCount) {
                this.KillUnitCount = KillUnitCount;
            }

            public long getTotalMoney() {
                return TotalMoney;
            }

            public void setTotalMoney(long TotalMoney) {
                this.TotalMoney = TotalMoney;
            }

            public int getRewardMoney() {
                return RewardMoney;
            }

            public void setRewardMoney(int RewardMoney) {
                this.RewardMoney = RewardMoney;
            }

            public int getRewardExp() {
                return RewardExp;
            }

            public void setRewardExp(int RewardExp) {
                this.RewardExp = RewardExp;
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

            public int getELO() {
                return ELO;
            }

            public void setELO(int ELO) {
                this.ELO = ELO;
            }

            public int getKDA() {
                return KDA;
            }

            public void setKDA(int KDA) {
                this.KDA = KDA;
            }

            public HeroBeanX getHero() {
                return Hero;
            }

            public void setHero(HeroBeanX Hero) {
                this.Hero = Hero;
            }

            public List<Integer> getSkillID() {
                return SkillID;
            }

            public void setSkillID(List<Integer> SkillID) {
                this.SkillID = SkillID;
            }

            public List<Integer> getEquipID() {
                return EquipID;
            }

            public void setEquipID(List<Integer> EquipID) {
                this.EquipID = EquipID;
            }

            public List<SkillBeanX> getSkill() {
                return Skill;
            }

            public void setSkill(List<SkillBeanX> Skill) {
                this.Skill = Skill;
            }

            public List<EquipBeanX> getEquip() {
                return Equip;
            }

            public void setEquip(List<EquipBeanX> Equip) {
                this.Equip = Equip;
            }

            public static class HeroBeanX implements Serializable {
                /**
                 * ID : 121
                 * Name : 绯村剑心
                 * IconFile : herohead/chara_0121.png
                 */
                private static final long serialVersionUID=8l;
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

            public static class SkillBeanX implements Serializable {
                /**
                 * ID : 8035
                 * Name : 闪现
                 * IconFile : skill/ico_8035..png
                 */
                private static final long serialVersionUID=9l;
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

            public static class EquipBeanX implements Serializable {
                /**
                 * ID : 21031
                 * Name : 蓄力之剑
                 * IconFile : equip/equip_111.png
                 */
                private static final long serialVersionUID=10l;
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
}
