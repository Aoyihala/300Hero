package com.example.evenalone.a300hero.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.bean.GameInfo;
import com.example.evenalone.a300hero.bean.HeroGuide;
import com.example.evenalone.a300hero.bean.LocalGaideListInfo;
import com.example.evenalone.a300hero.bean.LocalGaideListInfoDao;
import com.example.evenalone.a300hero.bean.LocalGameInfo;
import com.example.evenalone.a300hero.bean.LocalGameInfoDao;
import com.example.evenalone.a300hero.bean.LocalUserBean;
import com.example.evenalone.a300hero.bean.LocalUserBeanDao;
import com.example.evenalone.a300hero.event.JumpValueEvnet;
import com.example.evenalone.a300hero.utils.Contacts;
import com.example.evenalone.a300hero.utils.GameUtils;
import com.example.evenalone.a300hero.utils.SpUtils;
import com.example.evenalone.a300hero.utils.UiUtlis;
import com.google.gson.Gson;

import net.wujingchao.android.view.SimpleTagImageView;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 此部分优化的地方相当的多
 */
public class HerolistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HeroGuide.ListBean> listBeans = new ArrayList<>();
    private Map<Long,String> cachemap = new HashMap<>();
    private Map<Long,Integer> cache_rank = new HashMap<>();
    private LocalGameInfoDao gameInfoDao;
    private LocalUserBeanDao userBeanDao;
    private LocalGaideListInfoDao gaideListInfoDao;
    private boolean loadingjumpvalue=false;
    private OnGuaideClickListener guaideClickListener;
    private String nickname;
    private boolean ispowervalue;

    public void setGuaideClickListener(OnGuaideClickListener guaideClickListener) {
        this.guaideClickListener = guaideClickListener;
    }

    public void setListBeans(List<HeroGuide.ListBean> listBeans) {
        this.listBeans.clear();
        this.listBeans = listBeans;

    }


    public HerolistAdapter(String nickname)
    {
        this.nickname = nickname;
        userBeanDao = MyApplication.getDaoSession().getLocalUserBeanDao();
        gaideListInfoDao = MyApplication.getDaoSession().getLocalGaideListInfoDao();
        gameInfoDao = MyApplication.getDaoSession().getLocalGameInfoDao();

    }
    private LocalGaideListInfo findlistinfo(long matchid)
    {
        return   gaideListInfoDao.queryBuilder().where(LocalGaideListInfoDao.Properties.MatchId.eq(matchid)).unique();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.game_item, viewGroup, false);
        HeroListViewHolder heroListViewHolder = new HeroListViewHolder(view);
        //静止复用视图,因为涉及到过多的加载和展示,item请求网络
       heroListViewHolder.setIsRecyclable(true);
        return heroListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof HeroListViewHolder) {
            final HeroListViewHolder listViewHolder = (HeroListViewHolder) viewHolder;

            //macthtype 1为战场
            //resulttype 1 为赢
            listViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null!=guaideClickListener)
                    {
                        guaideClickListener.click(listBeans.get(position).getMatchID());
                    }
                }
            });
            try {
                String uri =Contacts.IMG+listBeans.get(position).getHero().getIconFile();
               listViewHolder.imgHero.setTag(R.id.imageid,uri);
                if (listViewHolder.imgHero != null && Contacts.IMG+listBeans.get(position).getHero().getIconFile() != null && listViewHolder.imgHero.getTag(R.id.imageid)!=null&&uri==listViewHolder.imgHero.getTag(R.id.imageid)) {
                    RequestOptions options = new RequestOptions()
                            .autoClone()
                            .dontAnimate();
                    Glide.with(viewHolder.itemView.getContext())
                            .load(Contacts.IMG+listBeans.get(position).getHero().getIconFile())
                            .apply(options)
                            .into(listViewHolder.imgHero);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            MyApplication.getImageCenter().donwloadOnly(listBeans.get(position).getHero().getIconFile(),listBeans.get(position).getHero().getName());


                    /* Glide.with(viewHolder.itemView.getContext()).load(Contacts.IMG+listBean.getHero().getIconFile()).into(listViewHolder.imgHero);*/
                /*    try {
                        MyApplication.getImageCenter().setPic(listViewHolder.imgHero,listBeans.get(position).getHero().getName(),Contacts.IMG+listBeans.get(position).getHero().getIconFile());
                    } catch (Exception e) {
                        //接受自定义错误
                        //这里由于没有及时的载入缓存或者文件正在读取中,使用图片加载框架
                        //清除tag,有的图片框架是不需要清除的
                  *//*      listViewHolder.imgHero.setTag(null);
                        Glide.with(viewHolder.itemView.getContext()).load(Contacts.IMG+listBean.getHero().getIconFile()).into(listViewHolder.imgHero);*//*
                    }*/


            if (listBeans.get(position).getMatchType()==1)
            {
                listViewHolder.tvJumpGameType.setText("竞技场");
            }
            else
            {
                listViewHolder.tvJumpGameType.setText("战场");
                listViewHolder.imgPosition.setBackgroundResource(R.drawable.jiucai);
                listViewHolder.tvPositionDes.setText(MyApplication.getContext().getString(R.string.litle));
                listViewHolder.tvPositionDes.setBackgroundResource(R.drawable.jiucai_shape);
                listViewHolder.tvPositionDes.setTextColor(UiUtlis.getColor(R.color.jiucai));
            }
            if (listBeans.get(position).getResult()==1)
            {

                listViewHolder.tvJumpViotoryFlag.setText("WIN");
                listViewHolder.tvJumpViotoryFlag.setTextColor(UiUtlis.getColor(R.color.colorPrimaryDark));
            }
            else
            {
                listViewHolder.tvJumpViotoryFlag.setText("LOSE");
                listViewHolder.tvJumpViotoryFlag.setTextColor(UiUtlis.getColor(R.color.colorAccent));

            }
            listViewHolder.tvJumpTime.setText(listBeans.get(position).getMatchDate());
            LocalGameInfo gameInfo = getoneGame(listBeans.get(position).getMatchID());
            if (gameInfo!=null)
            {
                listViewHolder.tvJumpGuaide.setText(gameInfo.getMygaide());
                if (ispowervalue)
                {
                    getJumpvaule(gameInfo.getResult(),position,listBeans.get(position));
                }
                operationMvp(gameInfo.getResult(),listBeans.get(position),listViewHolder);
            /*    if(position<listBeans.size()-1)
                {
                    //当前item的团分
                    int nowpower = new GameUtils().getNowUserPower(new Gson().fromJson(gameInfo.getResult(),GameInfo.class),listBean);
                    //上一局的团分
                    LocalGameInfo gameInfo_latest =  getoneGame(listBeans.get(position+1).getMatchID());
                    if (gameInfo_latest!=null)
                    {
                        int latestpower = new GameUtils().getNowUserPower(new Gson().fromJson(gameInfo_latest.getResult(),GameInfo.class),listBeans.get(position+1));
                        if (nowpower>latestpower)
                        {
                            listViewHolder.tvWin.setTextColor(UiUtlis.getColor(R.color.colorPrimary));
                            listViewHolder.tvWin.setText("+"+(nowpower-latestpower));
                        }
                        else
                        {
                            listViewHolder.tvWin.setTextColor(UiUtlis.getColor(R.color.Red));
                            listViewHolder.tvWin.setText("-"+(latestpower-nowpower));
                        }
                    }
                }
                else
                {
                    //已经是最后一个了
                    //10的随机数
                    if (listBean.getResult()==1)
                    {
                        //赢
                        listViewHolder.tvWin.setTextColor(UiUtlis.getColor(R.color.colorPrimary));
                        Random random = new Random();
                        listViewHolder.tvWin.setText("+"+(random.nextInt(10)));
                    }
                    else
                    {
                        //输
                        listViewHolder.tvWin.setTextColor(UiUtlis.getColor(R.color.Red));
                        listViewHolder.tvWin.setText("-"+(new Random().nextInt(10)));
                    }
                }*/
            }
            else
            {
                listViewHolder.tvJumpGuaide.setText("loading....");
                //请求单局信息
                requestMacth(listViewHolder,position,listBeans.get(position),listBeans.get(position).getMatchID());
            }

        }
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        if (holder != null) {
            //视图被回收

        }
        super.onViewRecycled(holder);
    }


    //时间久远的可能个人排名信息里没有团分了
    //只有从战局里面查找
    private void getJumpvaule(String result, int position,HeroGuide.ListBean listBean) {
        String power;
        if (position!=0)
        {
            return;
        }
        GameInfo gameInfo = new Gson().fromJson(result,GameInfo.class);
        if (listBean.getResult() == 1) {
            //赢了
            //获取赢的玩家列表
            List<GameInfo.MatchBean.WinSideBean> winSideBeanList = gameInfo.getMatch().getWinSide();
            for (GameInfo.MatchBean.WinSideBean winSideBean : winSideBeanList) {
                if (winSideBean.getRoleName().equals(nickname)) {
                    if (!loadingjumpvalue) {
                        //获取自己的团分
                        power = winSideBean.getELO() + "";
                        LocalUserBean localUserBean = getMy(nickname);
                        if (localUserBean != null) {
                            localUserBean.setJumpvalue(power);
                            localUserBean.setId(localUserBean.getId());
                            userBeanDao.update(localUserBean);
                            //反正是给主页搞的
                            EventBus.getDefault().postSticky(new JumpValueEvnet(nickname,power));
                        } else {
                            EventBus.getDefault().postSticky(new JumpValueEvnet(nickname,power));
                        }
                        loadingjumpvalue = true;

                    }
                }
            }
                        /*
                        GameInfo.MatchBean.WinSideBean winSideBean = new GameUtils().winMvpget(winSideBeanList);
                        if (winSideBean!=null)
                        {
                            Log.e("对局时间为:"+listBean.getMatchDate(),"mvp是"+winSideBean.getRoleName());
                        }
                        */


        } else {
            //输了
            List<GameInfo.MatchBean.LoseSideBean> loseSideBeanList = gameInfo.getMatch().getLoseSide();
            for (GameInfo.MatchBean.LoseSideBean loseSideBean : loseSideBeanList) {
                if (loseSideBean.getRoleName().equals(nickname)) {
                    if (!loadingjumpvalue) {
                        power = loseSideBean.getELO() + "";
                        LocalUserBean localUserBean = getMy(nickname);
                        if (localUserBean != null) {
                            localUserBean.setJumpvalue(power);
                            localUserBean.setId(localUserBean.getId());
                            userBeanDao.update(localUserBean);
                            EventBus.getDefault().post(new JumpValueEvnet(nickname,power));
                        } else {
                            EventBus.getDefault().post(new JumpValueEvnet(nickname,power));
                        }
                        loadingjumpvalue = true;
                    }

                }
            }
        }
    }

    /**
     * 请求数据库
     * @param matchid
     * @return
     */
    public LocalGameInfo getoneGame(long matchid)
    {
        LocalGameInfo gameInfo = gameInfoDao.queryBuilder().where(LocalGameInfoDao.Properties.MactherId.eq(matchid)).unique();
        return gameInfo;
    }


    public LocalUserBean getMy(String nickname)
    {
        LocalUserBean bean = userBeanDao.queryBuilder().where(LocalUserBeanDao.Properties.Nickname.eq(nickname)).unique();
        return bean;
    }





    private void requestMacth(final HeroListViewHolder listViewHolder, final int position, final HeroGuide.ListBean listBean, final long matchID) {
        x.http().get(new RequestParams(Contacts.MATCH_GAME + "?id=" + matchID), new Callback.CommonCallback<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(String result) {
                GameInfo gameInfo = new Gson().fromJson(result,GameInfo.class);
                //加载
                if (gameInfo!=null) {
                    operationMvp(result,listBean,listViewHolder);
                    String power = null;
                    //获取自己的信息(包括杀敌死亡)
                    //输
                    int kda;
                    //杀敌数量
                    int kill = 0;
                    //经济
                    long money = 0;
                    //助攻
                    int asstent = 0;
                    //小兵击杀
                    int killunitcount = 0;
                    //死亡次数
                    int deadcount = 0;
                    //记录全局变量查看数据异常
                    if (listBean.getResult()==1)
                    {
                        //赢了
                        //获取赢的玩家列表
                        List<GameInfo.MatchBean.WinSideBean> winSideBeanList = gameInfo.getMatch().getWinSide();
                        for (GameInfo.MatchBean.WinSideBean winSideBean:winSideBeanList)
                        {
                            if (winSideBean.getRoleName().equals(nickname))
                            {
                                if (!loadingjumpvalue)
                                {
                                    power = winSideBean.getELO()+"";
                                    LocalUserBean localUserBean = getMy(nickname);
                                    if (localUserBean!=null)
                                    {
                                        localUserBean.setJumpvalue(power);
                                        localUserBean.setId(localUserBean.getId());
                                        userBeanDao.update(localUserBean);
                                    }
                                    else
                                    {
                                        EventBus.getDefault().postSticky(new JumpValueEvnet(nickname,power));
                                    }
                                    loadingjumpvalue = true;

                                }
                                kill = winSideBean.getKillCount();
                                money = winSideBean.getTotalMoney();
                                deadcount = winSideBean.getDeathCount();
                                asstent = winSideBean.getAssistCount();
                            }
                        }
                        /*
                        GameInfo.MatchBean.WinSideBean winSideBean = new GameUtils().winMvpget(winSideBeanList);
                        if (winSideBean!=null)
                        {
                            Log.e("对局时间为:"+listBean.getMatchDate(),"mvp是"+winSideBean.getRoleName());
                        }
                        */


                    }
                    else
                    {
                        //输了
                        List<GameInfo.MatchBean.LoseSideBean> loseSideBeanList = gameInfo.getMatch().getLoseSide();
                        for (GameInfo.MatchBean.LoseSideBean loseSideBean:loseSideBeanList)
                        {
                            if (loseSideBean.getRoleName().equals(nickname))
                            {
                                if (!loadingjumpvalue)
                                {
                                    power = loseSideBean.getELO()+"";
                                    LocalUserBean localUserBean = getMy(nickname);
                                    if (localUserBean!=null)
                                    {
                                        localUserBean.setJumpvalue(power);
                                        localUserBean.setId(localUserBean.getId());
                                        userBeanDao.update(localUserBean);
                                    }
                                    else
                                    {
                                        EventBus.getDefault().postSticky(new JumpValueEvnet(nickname,power));
                                    }
                                    loadingjumpvalue=true;

                                }
                                kill = loseSideBean.getKillCount();
                                money = loseSideBean.getTotalMoney();
                                deadcount = loseSideBean.getDeathCount();
                                asstent = loseSideBean.getAssistCount();
                            }
                        }

                        /*
                        GameInfo.MatchBean.LoseSideBean loseSideBean = new GameUtils().loseMvpget(loseSideBeanList);
                        if (loseSideBean!=null)
                        {
                            Log.e("对局时间为:"+listBean.getMatchDate(),"mvp是"+loseSideBean.getRoleName());
                        }
                        */
                    }
                    String result_o = kill+"杀 "+deadcount+"死 "+asstent+"助 ";
                    LocalGameInfo gameinf_loal = getoneGame(matchID);
                    if (gameinf_loal!=null)
                    {
                        gameinf_loal.setId(gameinf_loal.getId());
                        gameinf_loal.setMactherId(matchID);
                        gameinf_loal.setResult(result);
                        gameinf_loal.setMygaide(result_o);
                        gameInfoDao.update(gameinf_loal);
                    }
                    else
                    {
                        gameinf_loal = new LocalGameInfo();
                        gameinf_loal.setMactherId(matchID);
                        gameinf_loal.setResult(result);
                        gameinf_loal.setMygaide(result_o);
                        gameInfoDao.insert(gameinf_loal);
                    }


                    listViewHolder.tvJumpGuaide.setText(result_o);
                    cachemap.put(matchID,result_o);
                }
                if (listBeans.get(position).getMatchType()==1)
                {
                    int pwoer_win_adv =  new GameUtils().getAugPower(gameInfo.getMatch().getLoseSide(),gameInfo.getMatch().getWinSide());
                    if (pwoer_win_adv > 0 && pwoer_win_adv < 1500) {
                        listViewHolder.imgPosition.setBackgroundResource(R.drawable.little_fish);
                        listViewHolder.tvPositionDes.setText(MyApplication.getContext().getString(R.string.tong));
                        listViewHolder.tvPositionDes.setBackgroundResource(R.drawable.little_fish_shape);
                        listViewHolder.tvPositionDes.setTextColor(UiUtlis.getColor(R.color.gray));
                    }
                    if (pwoer_win_adv >= 1500 && pwoer_win_adv < 3000) {
                        listViewHolder.imgPosition.setBackgroundResource(R.drawable.little_fish_bigger);
                        listViewHolder.tvPositionDes.setText(MyApplication.getContext().getString(R.string.baiying));
                        listViewHolder.tvPositionDes.setBackgroundResource(R.drawable.little_fish_bigger_shape);
                        listViewHolder.tvPositionDes.setTextColor(UiUtlis.getColor(R.color.little_fish_bigger));
                    }
                    if (pwoer_win_adv >= 2000 && pwoer_win_adv < 3000) {
                        listViewHolder.imgPosition.setBackgroundResource(R.drawable.fishes);
                        listViewHolder.tvPositionDes.setText(MyApplication.getContext().getString(R.string.gold));
                        listViewHolder.tvPositionDes.setBackgroundResource(R.drawable.fishes_shap);
                        listViewHolder.tvPositionDes.setTextColor(UiUtlis.getColor(R.color.fishes));
                    }
                    if (pwoer_win_adv >= 3000) {
                        listViewHolder.imgPosition.setBackgroundResource(R.drawable.shark);
                        listViewHolder.tvPositionDes.setText(MyApplication.getContext().getString(R.string.daemo));
                        listViewHolder.tvPositionDes.setBackgroundResource(R.drawable.shark_shape);
                        listViewHolder.tvPositionDes.setTextColor(UiUtlis.getColor(R.color.shark));
                    }
                    if (pwoer_win_adv>=4000)
                    {
                        listViewHolder.imgPosition.setBackgroundResource(R.drawable.jiucai);
                        listViewHolder.tvPositionDes.setText(MyApplication.getContext().getString(R.string.litle));
                        listViewHolder.tvPositionDes.setBackgroundResource(R.drawable.jiucai_shape);
                        listViewHolder.tvPositionDes.setTextColor(UiUtlis.getColor(R.color.jiucai));
                    }
                }


                //指定位置更新
                //去除更新闪动效果
                //notifyItemChanged(position);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //通知主线程
                Log.e("出错",ex.getLocalizedMessage()+"");

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private boolean saveWeiZhi(long matchid,String w)
    {
       /* LocalGaideListInfo localGaideListInfo = findlistinfo(matchid);
        if (localGaideListInfo!=null)
        {
            localGaideListInfo.setId(localGaideListInfo.getId());
            localGaideListInfo.setMyWeizi(w);
            gaideListInfoDao.delete(localGaideListInfo);
            gaideListInfoDao.save(localGaideListInfo);
            return true;
        }
        Log.e("设置位置失败","id"+matchid);*/
        return false;
    }
    /**
     * 计算mvp/躺输
     * @param result
     * @param listBean
     * @param viewHolder
     */
    public void operationMvp(String result,HeroGuide.ListBean listBean,HeroListViewHolder viewHolder)
    {
        if (listBean.getMatchType()!=1)
        {
            return;
        }
        GameUtils gameUtils = new GameUtils();
        if (listBean.getResult()==1)
        {
            GameInfo info = new Gson().fromJson(result,GameInfo.class);
            List<GameInfo.MatchBean.WinSideBean> winSideBeanList = info.getMatch().getWinSide();
            /*
            GameInfo.MatchBean.WinSideBean winSideBean =gameUtils.winMvpget(winSideBeanList);
            if (winSideBean!=null)
            {
                if (winSideBean.getRoleName().equals(nickname.getNickname()))
                {
                    //本人没错了
                    viewHolder.tvJumpGuaide.setText("MVP");
                }
            }
            */

            Integer rank_cache = cache_rank.get(listBean.getMatchID());
            if (rank_cache!=null)
            {
                if (rank_cache==1)
                {
                    Log.e("名次:赢",rank_cache+"名出现,mvp");
                    viewHolder.imgHero.setTagEnable(true);
                    viewHolder.imgHero.setTagText("MVP");
                    viewHolder.imgHero.setTagBackgroundColor(UiUtlis.getColor(R.color.Red));
                    saveWeiZhi(listBean.getMatchID(),"MVP");

                }
                else
                {
                    if (rank_cache>=1&&rank_cache<=3)
                    {

                        /*  viewHolder.tvStar.setImageResource(R.drawable.ic_star_yellow_500_24dp);*/
                    }

                    if (rank_cache>=4&&rank_cache<=5)
                    {
                        viewHolder.imgHero.setTagEnable(false);
                        /*  viewHolder.tvStar.setImageResource(R.drawable.ic_star_half_yellow_500_24dp);*/
                    }

                    if (rank_cache>=6&&rank_cache<=7)
                    {
                        viewHolder.imgHero.setTagEnable(true);
                        viewHolder.imgHero.setTagText("划水");
                        viewHolder.imgHero.setTagBackgroundColor(UiUtlis.getColor(R.color.blue));
                        /*   viewHolder.tvStar.setImageResource(R.drawable.ic_star_border_yellow_500_24dp);*/
                        saveWeiZhi(listBean.getMatchID(),"划水");
                    }
                    else
                    {
                        viewHolder.imgHero.setTagEnable(false);
                    }
                    String name = gameUtils.getWinAsstentRole(winSideBeanList);
                    if (name.equals(nickname))
                    {
                        if (rank_cache!=1&&rank_cache!=7)
                        {
                            //最后一名没有
                            //设置
                            viewHolder.imgHero.setTagEnable(true);
                            viewHolder.imgHero.setTagText("神队友");
                            viewHolder.imgHero.setTagBackgroundColor(UiUtlis.getColor(R.color.Red));
                            saveWeiZhi(listBean.getMatchID(),"神队友");
                        }
                        else
                        {
                            viewHolder.imgHero.setTagEnable(false);
                        }

                    }
                }



                return;
            }
            int rank = gameUtils.getWinRank(winSideBeanList,nickname);
            Log.e("名次:赢",rank+"名");
            if (rank==1)
            {
                Log.e("名次:赢",rank+"名出现,mvp");
                viewHolder.imgHero.setTagEnable(true);
                viewHolder.imgHero.setTagText("MVP");
                viewHolder.imgHero.setTagBackgroundColor(UiUtlis.getColor(R.color.Red));
                saveWeiZhi(listBean.getMatchID(),"MVP");
            }
            else
            {
                if (rank>=1&&rank<=3)
                {


                    /*viewHolder.tvStar.setImageResource(R.drawable.ic_star_yellow_500_24dp);*/
                }

                if (rank>=4&&rank<=5)
                {
                    viewHolder.imgHero.setTagEnable(false);
                    /*viewHolder.tvStar.setImageResource(R.drawable.ic_star_half_yellow_500_24dp);*/
                }


                if (rank>=6&&rank<=7)
                {
                    viewHolder.imgHero.setTagEnable(true);
                    viewHolder.imgHero.setTagText("划水");
                    viewHolder.imgHero.setTagBackgroundColor(UiUtlis.getColor(R.color.blue));
                    /*viewHolder.tvStar.setImageResource(R.drawable.ic_star_border_yellow_500_24dp);*/
                    saveWeiZhi(listBean.getMatchID(),"划水");
                }
                else
                {
                    viewHolder.imgHero.setTagEnable(false);
                }
                //计算是否是神队友
                String name = gameUtils.getWinAsstentRole(winSideBeanList);
                if (name.equals(nickname))
                {

                    if (rank!=1&&rank!=7)
                    {
                        //最后一名没有
                        //设置
                        viewHolder.imgHero.setTagEnable(true);
                        viewHolder.imgHero.setTagText("神队友");
                        viewHolder.imgHero.setTagBackgroundColor(UiUtlis.getColor(R.color.Red));
                        saveWeiZhi(listBean.getMatchID(),"神队友");
                    }
                    else
                    {
                        viewHolder.imgHero.setTagEnable(false);
                    }
                }
            }
            //保存rank
            cache_rank.put(listBean.getMatchID(),rank);
        }
        else
        {
            List<GameInfo.MatchBean.LoseSideBean> loseSideBeanList = new Gson().fromJson(result,GameInfo.class).getMatch().getLoseSide();
            /*
            GameInfo.MatchBean.LoseSideBean loseSideBean = gameUtils.loseMvpget(loseSideBeanList);
            if (loseSideBean!=null)
            {
                if (loseSideBean.getRoleName().equals(nickname.getNickname()))
                {
                    //本人没错了
                    viewHolder.tvJumpGuaide.setText("躺输");
                }
            }
            */
            Integer rank_cache = cache_rank.get(listBean.getMatchID());
            if (rank_cache!=null)
            {
                if (rank_cache==1)
                {
                    Log.e("名次:赢",rank_cache+"名出现,mvp");
                    viewHolder.imgHero.setTagEnable(true);
                    viewHolder.imgHero.setTagText("躺输");
                    viewHolder.imgHero.setTagBackgroundColor(UiUtlis.getColor(R.color.Red));
                    saveWeiZhi(listBean.getMatchID(),"躺输");
                }
                else
                {
                    viewHolder.imgHero.setTagEnable(false);
                }

                if (rank_cache>=1&&rank_cache<=3)
                {

                    /*viewHolder.tvStar.setImageResource(R.drawable.ic_star_yellow_500_24dp);*/
                }

                if (rank_cache>=4&&rank_cache<=5)
                {
                    viewHolder.imgHero.setTagEnable(false);
                    /* viewHolder.tvStar.setImageResource(R.drawable.ic_star_half_yellow_500_24dp);*/
                }
                else
                {
                    viewHolder.imgHero.setTagEnable(false);
                }

                if (rank_cache>=6&&rank_cache<=7)
                {
                    viewHolder.imgHero.setTagEnable(true);
                    viewHolder.imgHero.setTagText("坑");
                    viewHolder.imgHero.setTagBackgroundColor(UiUtlis.getColor(R.color.black));
                    /*    viewHolder.tvStar.setImageResource(R.drawable.ic_star_border_yellow_500_24dp);*/
                    saveWeiZhi(listBean.getMatchID(),"坑");
                }
                else
                {
                    viewHolder.imgHero.setTagEnable(false);
                }



                return;
            }
            int rank_lose = gameUtils.getLoseRank(loseSideBeanList,nickname);
            Log.e("名次:输",rank_lose+"名");
            if (rank_lose==1)
            {
                Log.e("名次:赢",rank_lose+"名出现,mvp");
                viewHolder.imgHero.setTagEnable(true);
                viewHolder.imgHero.setTagText("躺输");
                viewHolder.imgHero.setTagBackgroundColor(UiUtlis.getColor(R.color.Red));
                saveWeiZhi(listBean.getMatchID(),"躺输");
            }
            else
            {
                viewHolder.imgHero.setTagEnable(false);
            }
            if (rank_lose>=1&&rank_lose<=3)
            {


                /*  viewHolder.tvStar.setImageResource(R.drawable.ic_star_yellow_500_24dp);*/
            }

            if (rank_lose>=4&&rank_lose<=5)
            {
                viewHolder.imgHero.setTagEnable(false);
                /*   viewHolder.tvStar.setImageResource(R.drawable.ic_star_half_yellow_500_24dp);*/
            }
            else
            {
                viewHolder.imgHero.setTagEnable(false);
            }

            if (rank_lose>=6&&rank_lose<=7)
            {
                viewHolder.imgHero.setTagEnable(true);
                viewHolder.imgHero.setTagText("坑");
                viewHolder.imgHero.setTagBackgroundColor(UiUtlis.getColor(R.color.black));
                /*viewHolder.tvStar.setImageResource(R.drawable.ic_star_border_yellow_500_24dp);*/
                saveWeiZhi(listBean.getMatchID(),"坑");
            }
            else
            {
                viewHolder.imgHero.setTagEnable(false);
            }

            //保存rank
            cache_rank.put(listBean.getMatchID(),rank_lose);
        }
        GameInfo info = new Gson().fromJson(result,GameInfo.class);
        if (listBean.getMatchType()==1)
        {
            int pwoer_win_adv =  new GameUtils().getAugPower(info.getMatch().getLoseSide(),info.getMatch().getWinSide());
            if (pwoer_win_adv > 0 && pwoer_win_adv < 1500) {
                viewHolder.imgPosition.setBackgroundResource(R.drawable.little_fish);
                viewHolder.tvPositionDes.setText(MyApplication.getContext().getString(R.string.tong));
                viewHolder.tvPositionDes.setBackgroundResource(R.drawable.little_fish_shape);
                viewHolder.tvPositionDes.setTextColor(UiUtlis.getColor(R.color.gray));
            }
            if (pwoer_win_adv >= 1500 && pwoer_win_adv < 3000) {
                viewHolder.imgPosition.setBackgroundResource(R.drawable.little_fish_bigger);
                viewHolder.tvPositionDes.setText(MyApplication.getContext().getString(R.string.baiying));
                viewHolder.tvPositionDes.setBackgroundResource(R.drawable.little_fish_bigger_shape);
                viewHolder.tvPositionDes.setTextColor(UiUtlis.getColor(R.color.little_fish_bigger));
            }
            if (pwoer_win_adv >= 2000 && pwoer_win_adv < 3000) {
                viewHolder.imgPosition.setBackgroundResource(R.drawable.fishes);
                viewHolder.tvPositionDes.setText(MyApplication.getContext().getString(R.string.gold));
                viewHolder.tvPositionDes.setBackgroundResource(R.drawable.fishes_shap);
                viewHolder.tvPositionDes.setTextColor(UiUtlis.getColor(R.color.fishes));
            }
            if (pwoer_win_adv >= 3000) {
                viewHolder.imgPosition.setBackgroundResource(R.drawable.shark);
                viewHolder.tvPositionDes.setText(MyApplication.getContext().getString(R.string.daemo));
                viewHolder.tvPositionDes.setBackgroundResource(R.drawable.shark_shape);
                viewHolder.tvPositionDes.setTextColor(UiUtlis.getColor(R.color.shark));
            }
            if (pwoer_win_adv>=4000)
            {
                viewHolder.imgPosition.setBackgroundResource(R.drawable.jiucai);
                viewHolder.tvPositionDes.setText(MyApplication.getContext().getString(R.string.litle));
                viewHolder.tvPositionDes.setBackgroundResource(R.drawable.jiucai_shape);
                viewHolder.tvPositionDes.setTextColor(UiUtlis.getColor(R.color.jiucai));
            }
        }

    }

    @Override
    public int getItemCount() {
        return listBeans == null ? 0 : listBeans.size();
    }

    public void setNickName(String nickname) {
        this.nickname = nickname;
    }

    public void setPwoerMode(boolean ispowervalue) {
        this.ispowervalue = ispowervalue;
    }

    class HeroListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_jump_viotory_flag)
        TextView tvJumpViotoryFlag;
        @BindView(R.id.img_hero)
        SimpleTagImageView imgHero;
        @BindView(R.id.tv_jump_game_type)
        TextView tvJumpGameType;
        @BindView(R.id.tv_jump_time)
        TextView tvJumpTime;
        @BindView(R.id.tv_jump_guaide)
        TextView tvJumpGuaide;
        @BindView(R.id.tv_jump_nowstate)
        TextView tvJumpNowstate;
        @BindView(R.id.tv_win)
        TextView tvWin;
        @BindView(R.id.tv_add)
        TextView tvAdd;
        @BindView(R.id.tv_guaide_postiondes)
        TextView tvPositionDes;
        @BindView(R.id.tv_guaide_powervalue)
        TextView tvPowerAdd;
        @BindView(R.id.img_guaide_imgpostion)
        ImageView imgPosition;
        public HeroListViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnGuaideClickListener
    {
        void click(long macth);
    }


}
