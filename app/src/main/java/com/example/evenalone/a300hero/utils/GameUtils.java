package com.example.evenalone.a300hero.utils;

import android.util.Log;

import com.example.evenalone.a300hero.bean.GameInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GameUtils
{
    private int monerylv;
    private int cantuanlv;
    private long total_monery_;
    private int all_kill;

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

}
