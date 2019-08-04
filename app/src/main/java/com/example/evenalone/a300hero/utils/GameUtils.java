package com.example.evenalone.a300hero.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.bean.GameInfo;
import com.example.evenalone.a300hero.bean.HeroGuide;
import com.example.evenalone.a300hero.bean.LocalGaideListInfo;
import com.example.evenalone.a300hero.bean.LocalGaideListInfoDao;
import com.example.evenalone.a300hero.bean.LocalGameInfo;
import com.example.evenalone.a300hero.bean.LocalGameInfoDao;
import com.example.evenalone.a300hero.bean.LocalUserBean;
import com.example.evenalone.a300hero.event.JumpValueEvnet;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GameUtils {
    private int monerylv;
    private int cantuanlv;
    private long total_monery_;
    private int all_kill;
    public static int GET_USEDHERO = 0xdffafaf;
    public static int GET_YOURGUAIDE = 0xddaa;
    public static int GET_PWOERLIST = 0xfaaaaaa;

    /**
     * 获取自己的团分
     * @param handler
     */
    public void getMyPower(String usernmae,Handler handler)
    {

        List<LocalGaideListInfo> localGaideListInfos = searchbyName(usernmae);
        List<Integer> powerlist = new ArrayList<>();
        if (localGaideListInfos.size()>30)
        {
            //
        localGaideListInfos = localGaideListInfos.subList(0,19);
        }
        for (LocalGaideListInfo info:localGaideListInfos) {
            //查询游戏匹配数据
            GameInfo gameInfo = getGameinfo(info.getMatchId(), new Gson());
            if (gameInfo!=null)
            {
                HeroGuide.ListBean listBean = new Gson().fromJson(info.getResult(),HeroGuide.ListBean.class);
                if (listBean.getResult()==1)
                {
                    //赢了
                    //获取赢的玩家列表
                    List<GameInfo.MatchBean.WinSideBean> winSideBeanList = gameInfo.getMatch().getWinSide();
                    for (GameInfo.MatchBean.WinSideBean winSideBean : winSideBeanList) {
                        if (winSideBean.getRoleName().equals(SpUtils.getNowUser())) {

                            //获取自己的团分
                            powerlist.add(winSideBean.getELO()) ;

                        }
                    }
                }
                else
                {
                    //输了
                    List<GameInfo.MatchBean.LoseSideBean> loseSideBeanList = gameInfo.getMatch().getLoseSide();
                    for (GameInfo.MatchBean.LoseSideBean loseSideBean : loseSideBeanList) {
                        if (loseSideBean.getRoleName().equals(SpUtils.getNowUser())) {
                            powerlist.add(loseSideBean.getELO());
                        }
                    }
                }
            }
        }
        Message message = new Message();
        message.what = GET_PWOERLIST;
        message.obj = powerlist;
        handler.sendMessage(message);
        handler.obtainMessage();
    }
    /**
     * 获取用户每一局团分
     * @param gameInfo
     * @param listBean
     * @return
     */
    public int getNowUserPower(GameInfo gameInfo, HeroGuide.ListBean listBean) {
        int power = 0;
        if (listBean.getResult() == 1) {
            //赢了
            //获取赢的玩家列表
            List<GameInfo.MatchBean.WinSideBean> winSideBeanList = gameInfo.getMatch().getWinSide();
            for (GameInfo.MatchBean.WinSideBean winSideBean : winSideBeanList) {
                if (winSideBean.getRoleName().equals(SpUtils.getNowUser())) {

                    //获取自己的团分
                    power = winSideBean.getELO();

                }
            }
        }
        else
        {
            //输了
            List<GameInfo.MatchBean.LoseSideBean> loseSideBeanList = gameInfo.getMatch().getLoseSide();
            for (GameInfo.MatchBean.LoseSideBean loseSideBean : loseSideBeanList) {
                if (loseSideBean.getRoleName().equals(SpUtils.getNowUser())) {
                    power = loseSideBean.getELO();
                }
            }
        }
        return power;
    }
    //排序
    private static void ListSort(List<LocalGaideListInfo> list) {
        Collections.sort(list, new Comparator<LocalGaideListInfo>() {
            @Override
            public int compare(LocalGaideListInfo o1, LocalGaideListInfo o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                try {
                    Date dt1 = format.parse(o1.getTime());
                    Date dt2 = format.parse(o2.getTime());
                    if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

    /**
     * 常用英雄计算
     * @param nickname
     * @param handler
     * @return
     */
    public Map<String,String> getUsedHero(String nickname, Handler handler)
    {
        List<LocalGaideListInfo> localGaideListInfos = searchbyName(nickname);
        if (localGaideListInfos.size()>10)
        {
            Map<String,String> icon_map = new HashMap<>();
            Gson gson = new Gson();
            List<String> reapet_list = new ArrayList<>();
            //按时间排序
            ListSort(localGaideListInfos);
            reapet_list = new ArrayList<>();
            //筛选重复的数据 key 重复元素名字 value 重复次数
            Map<String,Integer> repat_chance = new HashMap<>();
            for (LocalGaideListInfo info:localGaideListInfos)
            {
                HeroGuide.ListBean listBean = gson.fromJson(info.getResult(),HeroGuide.ListBean.class);
                icon_map.put(listBean.getHero().getName(),listBean.getHero().getIconFile());
                if (repat_chance.containsKey(listBean.getHero().getName()))
                {
                    Integer num = repat_chance.get(listBean.getHero().getName());

                    repat_chance.put(listBean.getHero().getName(), num+1);

                }else{

                    repat_chance.put(listBean.getHero().getName(), 1);

                }
            }
            //使用次数大于4为常用英雄
            for (Map.Entry<String,Integer> entry:repat_chance.entrySet())
            {
                if (localGaideListInfos.size()==10)
                {
                    //使用超过两次就算
                    if (entry.getValue()>=2)
                    {
                        reapet_list.add(entry.getKey());
                    }
                }
                else
                {
                    if (entry.getValue()>=3)
                    {
                        reapet_list.add(entry.getKey());
                    }
                }

            }
            //最多取三位
            if (reapet_list.size()>3)
            {
                reapet_list = reapet_list.subList(0,3);
            }
            //获取Map
            Map<String,String> my_repat = new HashMap<>();
            for (int i=0;i<reapet_list.size();i++)
            {
                if ((icon_map.get(reapet_list.get(i))!=null))
                {
                    //有
                    my_repat.put(reapet_list.get(i),icon_map.get(reapet_list.get(i)));
                }
            }
            Message message = new Message();
            message.what = GET_USEDHERO;
            message.obj = my_repat;
            handler.sendMessage(message);
            handler.obtainMessage();
            return my_repat;
        }
        else
        {
            Message message = new Message();
            message.what = GET_USEDHERO;
            message.obj = null;
            handler.sendMessage(message);
            handler.obtainMessage();
            return null;
        }

    }

    /**
     * 计算雷达图
     * @param nickname
     * @param handler
     * @return
     */
    public Map<String,Integer> getMyGuaide(String nickname, Handler handler)
    {
        List<LocalGaideListInfo> localGaideListInfos = searchbyName(nickname);
        //数据至少大于一条内容
        Gson gson = new Gson();
        //10条开始统计
        if (localGaideListInfos.size()>=10)
        {
            //---------------------------------------------------------------
            //以下为100%一局基准
            //最大补兵数
            long max_killunincount =200*localGaideListInfos.size();
            //最大推塔数
            //一共15塔 一局一个人能推5个就是贡献多的
            long max_towercount = 5*localGaideListInfos.size();
            //最大杀敌数(50),不算战场人机，超过60不计算
            long max_killcount = 30*localGaideListInfos.size();
            //最大助攻数，助攻比人头好拿
            long max_assinatcount = 50*localGaideListInfos.size();
            //最大经济状况
            long max_money = 30000*localGaideListInfos.size();
            long all_killcount = 0;
            long all_money = 0;
            long all_assciont = 0;
            long all_tower = 0;
            long all_unicount = 0;
            //计算贡献 (总人头+总助攻)/（理想助攻+理想人头）
            //获取总人头 总助攻 总杀敌 总经济 总推塔
            List<GameInfo> gameInfoList = new ArrayList<>();
            for (LocalGaideListInfo info:localGaideListInfos)
            {
                HeroGuide.ListBean listBean = gson.fromJson(info.getResult(),HeroGuide.ListBean.class);
                //查询战局
                GameInfo in = getGameinfo(listBean.getMatchID(),gson);
                if (in!=null)
                {
                    in.setMyresult(listBean.getResult());
                    gameInfoList.add(in);
                }

            }
            //获取自己的团分变化
            List<Integer> jumpvalue = new ArrayList<>();
            //筛选战局
            //输赢都要统计
            for (GameInfo info:gameInfoList)
            {
                if (info.getMyresult()==1)
                {
                    for (GameInfo.MatchBean.WinSideBean winSideBean:info.getMatch().getWinSide())
                    {
                        if (winSideBean.getRoleName().equals(SpUtils.getNowUser()))
                        {
                            jumpvalue.add(winSideBean.getELO());
                            all_killcount = all_killcount+winSideBean.getKillCount();
                            all_money = all_money +winSideBean.getTotalMoney();
                            all_tower = all_tower+winSideBean.getTowerDestroy();
                            all_unicount = all_unicount+winSideBean.getKillUnitCount();
                            all_assciont = all_assciont+winSideBean.getAssistCount();
                        }
                    }
                }
                else
                {
                    for (GameInfo.MatchBean.LoseSideBean loseSideBean:info.getMatch().getLoseSide())
                    {
                        if (loseSideBean.getRoleName().equals(SpUtils.getNowUser()))
                        {
                            jumpvalue.add(loseSideBean.getELO());
                            all_killcount = all_killcount+loseSideBean.getKillCount();
                            all_money = all_money +loseSideBean.getTotalMoney();
                            all_tower = all_tower+loseSideBean.getTowerDestroy();
                            all_unicount = all_unicount+loseSideBean.getKillUnitCount();
                            all_assciont = all_assciont+loseSideBean.getAssistCount();
                        }
                    }
                }
            }
            //计算百分比(每个都按100计算)
            //描述值:团战 推塔 击杀 发育 贡献
            //计算团战(击杀+助攻)/总人头
            /*int viotory = (int) (((double) wincount / (double) all) * 100);*/
            //参团率
            long my_data_kill = all_assciont+all_killcount;
            int tuan = (int) (((double)my_data_kill/(double)max_killcount)*100);
            //计算击杀
            int kill = (int) (((double)all_killcount/(double) max_killcount)*100);
            //计算推塔
            int tower = (int)(((double)all_tower/(double)max_towercount)*100);
            //计算发育
            int money = (int) (((double)all_money/(double)max_money)*100);
            //计算助攻
            int assient = (int)(((double)all_assciont/(double)max_assinatcount)*100);
            //计算贡献
            //计算方式(助攻+推塔+击杀+发育/400)百分比相加
            int my_data_gong = assient+kill+tower+money;
            int gongxian = (int)(((double)my_data_gong/(double)200)*100);
            Map<String,Integer> datas = new HashMap<>();
            datas.put("击杀",kill);
            datas.put("团战",tuan);
            datas.put("发育",money);
            datas.put("推塔",tower);
            datas.put("贡献",gongxian);
            Message message = new Message();
            message.what = GET_YOURGUAIDE;
            message.obj = datas;
            handler.sendMessage(message);
            handler.obtainMessage();
            return datas;
        }
        else
        {
            Message message = new Message();
            message.what = GET_YOURGUAIDE;
            message.obj = null;
            handler.sendMessage(message);
            handler.obtainMessage();
            return null;
        }
    }
    public List<LocalGaideListInfo> searchbyName(String nickname)
    {
        LocalGaideListInfoDao gaideListInfoDao = MyApplication.getDaoSession().getLocalGaideListInfoDao();
        List<LocalGaideListInfo> datas = gaideListInfoDao.queryBuilder().where(LocalGaideListInfoDao.Properties.Nickname.eq(nickname)).list();
        return datas;
    }

    private GameInfo getGameinfo(long matchid, Gson gson)
    {
        LocalGameInfoDao gameInfoDao = MyApplication.getDaoSession().getLocalGameInfoDao();
        LocalGameInfo gameInfo = gameInfoDao.queryBuilder().where(LocalGameInfoDao.Properties.MactherId.eq(matchid)).unique();
        if (gameInfo!=null)
        {
            return gson.fromJson(gameInfo.getResult(),GameInfo.class);
        }
        else
        {
            return null;
        }

    }
    /**
     * 计算mvp
     * @param winSideBeanList
     * @return
     */
    public GameInfo.MatchBean.WinSideBean winMvpget(List<GameInfo.MatchBean.WinSideBean> winSideBeanList)
    {
        //一定有7个
        //结合kda,参团率进行比较 (参团率=(击杀数+助攻数)/团队总击杀数×100%) 角色经济/总经济(kda辅助经济不一定高 但是kda高的输出经济一定高)
        //打星 1-3全星 4-5半星  6-7空星
        long total_monery_ = 0;
        int all_kill=0;
        Map<Long,GameInfo.MatchBean.WinSideBean> winSideBeanMap = new LinkedHashMap<>();
        Map<Long,Integer> kdaall = new LinkedHashMap<>();
        boolean isfinish = false;
        //计算总经济总击杀数
        for (GameInfo.MatchBean.WinSideBean winSideBean:winSideBeanList)
        {
            total_monery_ = total_monery_+winSideBean.getTotalMoney();
            all_kill = all_kill+winSideBean.getKillCount();
        }
        for (GameInfo.MatchBean.WinSideBean winSideBean:winSideBeanList)
        {
            winSideBeanMap.put(winSideBean.getRoleID(),winSideBean);
            kdaall.put(winSideBean.getRoleID(),winSideBean.getKDA());
        }
        //排名
        //拿出三个排名中的第一再进行比较
        kdaall = sortByValueDescDown(kdaall,kdaall.size());
        //计算kda(三个满星的人选拔出mvp)
        int firstkda = 0;
        int secondkda = 0;
        int thread = 0;
        //获取一二名的kda
        List<Integer> kdalist = new LinkedList<>(kdaall.values());
        List<Long> roleidlist = new LinkedList<>(kdaall.keySet());
        firstkda = kdalist.get(0);
        secondkda = kdalist.get(1);
        thread =kdalist.get(2);
        if (firstkda-secondkda>80)
        {
            //第一名评分超出第二名
            return winSideBeanMap.get(roleidlist.get(0));
        }
        if (firstkda-secondkda<80)
        {
            //计算参团率
            int cantuanlv_thread = 0;
            int cantuanlv_first = ((winSideBeanMap.get(0).getKillCount()+winSideBeanMap.get(0).getAssistCount())/all_kill)*100;
            int cantuanlv_second = ((winSideBeanMap.get(1).getKillCount()+winSideBeanMap.get(1).getAssistCount())/all_kill)*100;
            if (firstkda-thread<80)
            {
                cantuanlv_thread = ((winSideBeanMap.get(2).getKillCount()+winSideBeanMap.get(2).getAssistCount())/all_kill)*100;
                if (cantuanlv_first-cantuanlv_second>5)
                {
                    //第一名和第二名比
                    //在和第三名比
                    if (cantuanlv_first-cantuanlv_thread>5)
                    {
                        //大于了第三名又大于第二名
                        return winSideBeanMap.get(0);
                    }
                    else
                    {
                        //小于了第三名,但是大于第二名
                        //比经济
                        if (winSideBeanMap.get(roleidlist.get(0)).getTotalMoney()>winSideBeanMap.get(roleidlist.get(2)).getTotalMoney())
                        {
                            return winSideBeanMap.get(roleidlist.get(0));
                        }
                        else
                        {
                            return winSideBeanMap.get(roleidlist.get(2));
                        }

                    }
                }
                else {
                    //和第二名相差无几
                    //第二名和第三名比
                    if (cantuanlv_thread - cantuanlv_second > 5)
                    {
                        return winSideBeanMap.get(roleidlist.get(2));
                    }
                    else
                    {
                        if (cantuanlv_first-cantuanlv_thread>5)
                        {
                            return winSideBeanMap.get(roleidlist.get(0));
                        }
                        else
                        {
                            //比一二名
                            if (winSideBeanMap.get(roleidlist.get(0)).getTotalMoney()>winSideBeanMap.get(roleidlist.get(1)).getTotalMoney())
                            {
                                //第一名经济多
                                return winSideBeanMap.get(roleidlist.get(0));
                            }
                            else
                            {
                                //第二名经济多
                                return winSideBeanMap.get(roleidlist.get(1));
                            }
                        }
                    }

                }
            }
            else
            {
                //计算第一名和第二名的差距
                //如果参团率大于5%
                if (cantuanlv_first-cantuanlv_second>5)
                {
                    return winSideBeanMap.get(0);
                }
                else
                {
                    //这里不算第三名
                    //计算经济比
                    if (winSideBeanMap.get(roleidlist.get(0)).getTotalMoney()>winSideBeanMap.get(roleidlist.get(1)).getTotalMoney())
                    {
                        //第一名经济多
                        return winSideBeanMap.get(roleidlist.get(0));
                    }
                    else
                    {
                        //第二名经济多
                        return winSideBeanMap.get(roleidlist.get(1));
                    }
                }
            }



        }
        return winSideBeanMap.get(roleidlist.get(0));
    }


    /**
     * 计算躺输
     * @param loseSideBeanList
     * @return
     */
    public  GameInfo.MatchBean.LoseSideBean loseMvpget(List<GameInfo.MatchBean.LoseSideBean> loseSideBeanList)
    {

        //一定有7个
        //结合kda,参团率进行比较 (参团率=(击杀数+助攻数)/团队总击杀数×100%) 角色经济/总经济(kda辅助经济不一定高 但是kda高的输出经济一定高)
        //打星 1-3全星 4-5半星  6-7空星
        long total_monery_ = 0;
        int all_kill=0;
        Map<Long,GameInfo.MatchBean.LoseSideBean> winSideBeanMap = new LinkedHashMap<>();
        Map<Long,Integer> kdaall = new LinkedHashMap<>();
        boolean isfinish = false;
        //计算总经济总击杀数
        for (int i=0;i<loseSideBeanList.size();i++)
        {
            //计算总经济
            total_monery_ = total_monery_+loseSideBeanList.get(i).getTotalMoney();
            //计算总击杀数
            all_kill = all_kill+loseSideBeanList.get(i).getKillCount();
            if (loseSideBeanList.get(i)==null)
            {
                Log.e("空","战局异常");

            }
            if (i==6)
            {
                isfinish = true;
                //计算每个人
                for (i=0;i<loseSideBeanList.size();i++)
                {
                    if (isfinish)
                    {
                        winSideBeanMap.put(loseSideBeanList.get(i).getRoleID(),loseSideBeanList.get(i));
                        kdaall.put(loseSideBeanList.get(i).getRoleID(),loseSideBeanList.get(i).getKDA());
                    }
                }
            }
        }

        //排名
        //拿出三个排名中的第一再进行比较
        kdaall = sortByValueDescDown(kdaall,kdaall.size());
        //计算kda(三个满星的人选拔出mvp)
        int firstkda = 0;
        int secondkda = 0;
        int thread = 0;
        //获取一二名的kda
        List<Integer> kdalist = new LinkedList<>(kdaall.values());
        List<Long> roleidlist = new LinkedList<>(kdaall.keySet());
        firstkda = kdalist.get(0);
        secondkda = kdalist.get(1);
        thread =kdalist.get(2);
        if (firstkda-secondkda>80)
        {
            //第一名评分超出第二名
            return winSideBeanMap.get(roleidlist.get(0));
        }
        if (firstkda-secondkda<80)
        {
            //计算参团率
            int cantuanlv_thread = 0;
            int cantuanlv_first = ((winSideBeanMap.get(0).getKillCount()+winSideBeanMap.get(0).getAssistCount())/all_kill)*100;
            int cantuanlv_second = ((winSideBeanMap.get(1).getKillCount()+winSideBeanMap.get(1).getAssistCount())/all_kill)*100;
            if (firstkda-thread<80)
            {
                cantuanlv_thread = ((winSideBeanMap.get(2).getKillCount()+winSideBeanMap.get(2).getAssistCount())/all_kill)*100;
                if (cantuanlv_first-cantuanlv_second>5)
                {
                    //第一名和第二名比
                    //在和第三名比
                    if (cantuanlv_first-cantuanlv_thread>5)
                    {
                        //大于了第三名又大于第二名
                        return winSideBeanMap.get(roleidlist.get(0));
                    }
                    else
                    {
                        //小于了第三名,但是大于第二名
                        //比经济
                        if (winSideBeanMap.get(roleidlist.get(0)).getTotalMoney()>winSideBeanMap.get(roleidlist.get(2)).getTotalMoney())
                        {
                            return winSideBeanMap.get(roleidlist.get(0));
                        }
                        else
                        {
                            return winSideBeanMap.get(roleidlist.get(2));
                        }
                    }
                }
                else {
                    //和第二名相差无几
                    //第二名和第三名比
                    if (cantuanlv_thread - cantuanlv_second > 5)
                    {
                        return winSideBeanMap.get(roleidlist.get(2));
                    }
                    else
                    {
                        if (cantuanlv_first-cantuanlv_thread>5)
                        {
                            return winSideBeanMap.get(roleidlist.get(0));
                        }
                        else
                        {
                            //比一二名
                            if (winSideBeanMap.get(roleidlist.get(0)).getTotalMoney()>winSideBeanMap.get(roleidlist.get(1)).getTotalMoney())
                            {
                                //第一名经济多
                                return winSideBeanMap.get(roleidlist.get(0));
                            }
                            else
                            {
                                //第二名经济多
                                return winSideBeanMap.get(roleidlist.get(1));
                            }
                        }
                    }

                }
            }
            else
            {
                //计算第一名和第二名的差距
                //如果参团率大于5%
                if (cantuanlv_first-cantuanlv_second>5)
                {
                    return winSideBeanMap.get(roleidlist.get(0));
                }
                else
                {
                    //这里不算第三名
                    //计算经济比
                    if (winSideBeanMap.get(roleidlist.get(0)).getTotalMoney()>winSideBeanMap.get(roleidlist.get(1)).getTotalMoney())
                    {
                        //第一名经济多
                        return winSideBeanMap.get(roleidlist.get(0));
                    }
                    else
                    {
                        //第二名经济多
                        return winSideBeanMap.get(roleidlist.get(1));
                    }
                }
            }



        }
        return winSideBeanMap.get(roleidlist.get(0));
    }


    /**
     * 获取某个角色在对局里某一方排名
     * @param winSideBeanList
     * @param nickname
     */
    public int getWinRank(List<GameInfo.MatchBean.WinSideBean> winSideBeanList,String nickname)
    {
        //通过kda来进行排名
        //一定有7个
        //打星 1-3全星 4-5半星  6-7空星
        Map<String,GameInfo.MatchBean.WinSideBean> winSideBeanMap = new LinkedHashMap<>();
        Map<Long,Integer> kdaall = new LinkedHashMap<>();
        boolean isfinish = false;
        //计算总经济总击杀数
        if (winSideBeanList!=null&&winSideBeanList.size()>0)
        {
            for (int i=0;i<winSideBeanList.size();i++)
            {
                if (winSideBeanList.get(i)!=null)
                {
                    winSideBeanMap.put(winSideBeanList.get(i).getRoleName(),winSideBeanList.get(i));
                    kdaall.put(winSideBeanList.get(i).getRoleID(),winSideBeanList.get(i).getKDA());
                }

            }
            //排名
            //拿出三个排名中的第一再进行比较
            kdaall = sortByValueDescDown(kdaall,kdaall.size());
            //升序从后往前
            List<Integer> winSideBeanskda = new LinkedList<>(kdaall.values());
            //名字的值
            List<String> namelist = new ArrayList<>(winSideBeanMap.keySet());
            int rank=0;//这个东西只是一个位置
            int mykda=0;
            for (int i=0;i<winSideBeanskda.size();i++) {
                if (winSideBeanMap.size()>0)
                {
                    if (winSideBeanMap.get(nickname)!=null)
                    {
                        if (winSideBeanMap.get(nickname).getKDA() == winSideBeanskda.get(i))
                        {
                            //获取到我的kda
                            rank=i+1;
                        }
                    }

                }

            }
            //rank = winSideBeanskda.indexOf(mykda);
            return rank;

        }
        else
        {
            return 8;
        }


    }

    /**
     * 获取输的一方某人的排名
     * @param loseSideBeanList
     * @param nickname
     * @return
     */
    public int getLoseRank(List<GameInfo.MatchBean.LoseSideBean> loseSideBeanList,String nickname)
    {

        //通过kda来进行排名
        //一定有7个
        //打星 1-3全星 4-5半星  6-7空星
        Map<String,GameInfo.MatchBean.LoseSideBean> winSideBeanMap = new LinkedHashMap<>();
        Map<Long,Integer> kdaall = new LinkedHashMap<>();
        boolean isfinish = false;
        //计算总经济总击杀数
        if (loseSideBeanList!=null&&loseSideBeanList.size()>0)
        {
            for (int i=0;i<loseSideBeanList.size();i++)
            {
                if (loseSideBeanList.get(i)!=null)
                {
                    winSideBeanMap.put(loseSideBeanList.get(i).getRoleName(),loseSideBeanList.get(i));
                    kdaall.put(loseSideBeanList.get(i).getRoleID(),loseSideBeanList.get(i).getKDA());
                }

            }
            //排名
            //拿出三个排名中的第一再进行比较
            kdaall = sortByValueDescDown(kdaall,kdaall.size());
            //升序从后往前
            List<Integer> winSideBeanskda = new LinkedList<>(kdaall.values());
            //名字的值
            List<String> namelist = new ArrayList<>(winSideBeanMap.keySet());
            int rank=0;//这个东西只是一个位置
            int mykda=0;
            for (int i=0;i<winSideBeanskda.size();i++) {
                if (winSideBeanMap.size()>0)
                {
                    if (winSideBeanMap.get(nickname)!=null)
                    {
                        if (winSideBeanMap.get(nickname).getKDA() == winSideBeanskda.get(i))
                        {
                            //获取到我的kda
                            rank=i+1;
                        }
                    }

                }

            }
            return rank;
        }

        else
        {
            return 8;
        }




    }


    /**
     * 按map的value升序排序
     *
     * @param map
     * @param top
     *
     * @return
     */
    public <K, V extends Comparable<? super V>> Map<K, V> sortByValueAscUp(
            Map<K, V> map, int top) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(
                map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            if (top-- == 0) {
                break;
            }
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * 按map value降序排序
     *
     * @param map
     * @param top
     *
     * @return
     */
    public <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescDown(
            Map<K, V> map, int top) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(
                map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            if (top-- == 0) {
                break;
            }
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
    /**
     * 按map value降序排序
     *
     * @param map
     * @param top
     *
     * @return
     */
    public <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescDownKey(
            Map<K, V> map, int top) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(
                map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            if (top-- == 0) {
                break;
            }
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * 获取最高助攻的角色
     * @return
     */
    public String getWinAsstentRole(List<GameInfo.MatchBean.WinSideBean> winSideBeanList)
    {
        List<Integer> asstont = new ArrayList<>();
        Map<Integer,Long> ass = new LinkedHashMap<>();
        if (winSideBeanList!=null&&winSideBeanList.size()>0)
        {
            for (GameInfo.MatchBean.WinSideBean winSideBean:winSideBeanList)
            {
                ass.put(winSideBean.getAssistCount(),winSideBean.getRoleID());
                asstont.add(winSideBean.getAssistCount());
            }
            Collections.sort(asstont);
            int all = asstont.get(asstont.size()-1);
            if (all>=30)
            {
                String name = null;
                for (GameInfo.MatchBean.WinSideBean winSideBean:winSideBeanList)
                {
                    if (winSideBean.getRoleID()==ass.get(all))
                    {
                        name = winSideBean.getRoleName();
                    }
                }

                return name;
            }
            else
            {
                return " ";
            }
        }
        else
        {
            return " ";
        }


    }

    /**
     * 获取最高击杀的角色
     * @return
     */
    public String getWinKillRole(List<GameInfo.MatchBean.WinSideBean> winSideBeanList)
    {
        List<Integer> asstont = new ArrayList<>();
        Map<Integer,Long> ass = new LinkedHashMap<>();
        if (winSideBeanList!=null&&winSideBeanList.size()>0)
        {
            for (GameInfo.MatchBean.WinSideBean winSideBean:winSideBeanList)
            {
                ass.put(winSideBean.getKillCount(),winSideBean.getRoleID());
                asstont.add(winSideBean.getKillCount());
            }
            Collections.sort(asstont);
            int all = asstont.get(asstont.size()-1);
            String name = null;
            for (GameInfo.MatchBean.WinSideBean winSideBean:winSideBeanList)
            {
                if (winSideBean.getRoleID()==ass.get(all))
                {
                        name = winSideBean.getRoleName();
                }

            }
                return name;

        }
        else
        {
            return " ";
        }


    }

    /**
     * 获取最高击杀的角色
     * @return
     */
    public String getLoseKillRole(List<GameInfo.MatchBean.LoseSideBean> winSideBeanList)
    {
        List<Integer> asstont = new ArrayList<>();
        Map<Integer,Long> ass = new LinkedHashMap<>();
        if (winSideBeanList!=null&&winSideBeanList.size()>0)
        {
            for (GameInfo.MatchBean.LoseSideBean winSideBean:winSideBeanList)
            {
                ass.put(winSideBean.getKillCount(),winSideBean.getRoleID());
                asstont.add(winSideBean.getKillCount());
            }
            Collections.sort(asstont);
            int all = asstont.get(asstont.size()-1);
            String name = null;
            for (GameInfo.MatchBean.LoseSideBean winSideBean:winSideBeanList)
            {
                if (winSideBean.getRoleID()==ass.get(all))
                {
                    name = winSideBean.getRoleName();
                }

            }
            return name;

        }
        else
        {
            return " ";
        }
    }



    public String  getKillUserDataWin(List<GameInfo.MatchBean.WinSideBean> objectList, String nickname)
    {
          //按赢的这边来
            for (int i=0;i<objectList.size();i++)
            {
                GameInfo.MatchBean.WinSideBean winSideBean = (GameInfo.MatchBean.WinSideBean) objectList.get(i);
                if (winSideBean.getRoleName().equals(nickname))
                {
                    return winSideBean.getKillCount()+"杀 "+winSideBean.getDeathCount()+"死 "+winSideBean.getAssistCount()+"助";
                }
            }
        return null;

    }

    public String  getKillUserDataLose(List<GameInfo.MatchBean.LoseSideBean> objectList, String nickname)
    {
        //按输的这边来
        //按赢的这边来
        for (int i=0;i<objectList.size();i++)
        {
            GameInfo.MatchBean.LoseSideBean winSideBean = (GameInfo.MatchBean.LoseSideBean) objectList.get(i);
            if (winSideBean.getRoleName().equals(nickname))
            {
                return winSideBean.getKillCount()+"杀 "+winSideBean.getDeathCount()+"死 "+winSideBean.getAssistCount()+"助";
            }
        }
        return  null;
    }


}
