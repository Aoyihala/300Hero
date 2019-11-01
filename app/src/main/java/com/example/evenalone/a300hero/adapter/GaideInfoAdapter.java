package com.example.evenalone.a300hero.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.evenalone.a300hero.R;
import com.example.evenalone.a300hero.app.MyApplication;
import com.example.evenalone.a300hero.bean.GameInfo;
import com.example.evenalone.a300hero.ui.ListActivity;
import com.example.evenalone.a300hero.utils.Contacts;
import com.example.evenalone.a300hero.utils.GameUtils;
import com.example.evenalone.a300hero.utils.SpUtils;
import com.example.evenalone.a300hero.utils.UiUtlis;
import com.example.evenalone.a300hero.wedgit.NoScrollLinearLayout;

import net.wujingchao.android.view.SimpleTagImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GaideInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private List<GameInfo.MatchBean.WinSideBean> winSideBeanList = new ArrayList<>();
    private List<GameInfo.MatchBean.LoseSideBean> loseSideBeanList = new ArrayList<>();
    private String nickname;
    private Activity activity;
    private OnTipoffClickListener tipoffClickListener;
    public GaideInfoAdapter(String nickname, Activity activity) {
        this.nickname = nickname;
        this.activity = activity;
    }

    public void setTipoffClickListener(OnTipoffClickListener tipoffClickListener) {
        this.tipoffClickListener = tipoffClickListener;
    }

    private boolean iswin = false;


    public void setIswin(boolean iswin) {
        this.iswin = iswin;
    }

    public void setLoseSideBeanList(List<GameInfo.MatchBean.LoseSideBean> loseSideBeanList) {
        this.loseSideBeanList = loseSideBeanList;
    }

    public void setWinSideBeanList(List<GameInfo.MatchBean.WinSideBean> winSideBeanList) {
        this.winSideBeanList = winSideBeanList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.guaide_item, viewGroup, false);
        GaideInfoViewHolder gaideInfoViewHolder = new GaideInfoViewHolder(view);
        return gaideInfoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof GaideInfoViewHolder) {
            GameUtils gameUtils = new GameUtils();
            GaideInfoViewHolder gaideInfoViewHolder = (GaideInfoViewHolder) viewHolder;
            if (iswin) {
                //赢
                final GameInfo.MatchBean.WinSideBean winSideBean = winSideBeanList.get(i);
                if (winSideBean.getRoleName().equals(nickname)) {
                    gaideInfoViewHolder.tvGuaideUserguaide.setVisibility(View.VISIBLE);
                    if (winSideBean.getRoleName().equals(SpUtils.getMainUser())) {
                        gaideInfoViewHolder.tvGuaideUserguaide.setText("我");
                    } else {
                        gaideInfoViewHolder.tvGuaideUserguaide.setText("他");
                    }

                } else {
                    gaideInfoViewHolder.tvGuaideUserguaide.setVisibility(View.GONE);
                }
                gaideInfoViewHolder.tvGuaideTipoff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tipoffClickListener!=null)
                        {
                            tipoffClickListener.click(i,winSideBean);
                        }
                    }
                });
                gaideInfoViewHolder.tvGuaideUserguaideinfo.setText(winSideBean.getKillCount() + "杀 " + winSideBean.getDeathCount() + "死 " + winSideBean.getAssistCount() + "助 评分" + winSideBean.getKDA());
                gaideInfoViewHolder.tvGuaideUsername.setText(winSideBean.getRoleName());
                //处理mvp&杀敌数最多
                if (winSideBean.getRoleName().equals(gameUtils.getWinKillRole(winSideBeanList))) {
                    gaideInfoViewHolder.imgKillAll.setVisibility(View.VISIBLE);
                } else {
                    gaideInfoViewHolder.imgKillAll.setVisibility(View.INVISIBLE);
                }
                int rank = gameUtils.getWinRank(winSideBeanList, winSideBean.getRoleName());
                //团分 胜率
                gaideInfoViewHolder.tvGuaidePowervalue.setText("团分:"+winSideBean.getELO());
                int percent = (int)(((double) winSideBean.getWinCount()/(double) winSideBean.getMatchCount())*100);
                gaideInfoViewHolder.tvGuaidePercent.setText("胜率:"+percent+"%");
                if (rank == 1) {
                    gaideInfoViewHolder.imgGaideAvator.setTagEnable(true);
                    gaideInfoViewHolder.imgGaideAvator.setTagText("MVP");
                    gaideInfoViewHolder.imgGaideAvator.setTagBackgroundColor(UiUtlis.getColor(R.color.Red));

                } else {
                    //处理其他标签
                    if (rank > 1 && rank <= 3) {

                        gaideInfoViewHolder.imgGaideAvator.setTagEnable(false);
                    }


                    if (rank >= 4 && rank <= 5) {
                        gaideInfoViewHolder.imgGaideAvator.setTagEnable(false);
                    }


                    if (rank >= 6 && rank <= 7) {
                        gaideInfoViewHolder.imgGaideAvator.setTagEnable(true);
                        gaideInfoViewHolder.imgGaideAvator.setTagText("划水");
                        gaideInfoViewHolder.imgGaideAvator.setTagBackgroundColor(UiUtlis.getColor(R.color.blue));
                        gaideInfoViewHolder.tvGuaideTipoff.setVisibility(View.VISIBLE);
                    } else {
                        gaideInfoViewHolder.imgGaideAvator.setTagEnable(false);
                    }
                    //计算是否是神队友
                    String name = gameUtils.getWinAsstentRole(winSideBeanList);
                    if (name.equals(winSideBean.getRoleName())) {

                        if (rank != 1 && rank != 7) {
                            //最后一名没有
                            //设置
                            gaideInfoViewHolder.imgGaideAvator.setTagEnable(true);
                            gaideInfoViewHolder.imgGaideAvator.setTagText("神队友");
                            gaideInfoViewHolder.imgGaideAvator.setTagBackgroundColor(UiUtlis.getColor(R.color.Red));
                        } else {
                            gaideInfoViewHolder.imgGaideAvator.setTagEnable(false);
                        }
                    }
                }

                //加载图片
                /*   Glide.with(viewHolder.itemView.getContext()).load(Contacts.IMG+winSideBean.getHero().getIconFile()).into(gaideInfoViewHolder.imgGaideAvator);*/
                try {
                    gaideInfoViewHolder.imgGaideAvator.setTag(null);
                    MyApplication.getImageCenter().setPic(gaideInfoViewHolder.imgGaideAvator, winSideBean.getHero().getName(), Contacts.IMG + winSideBean.getHero().getIconFile());
                } catch (Exception e) {
                    gaideInfoViewHolder.imgGaideAvator.setTag(null);
                    Glide.with(viewHolder.itemView.getContext()).load(Contacts.IMG + winSideBean.getHero().getIconFile()).into(gaideInfoViewHolder.imgGaideAvator);
                }
                //召唤师技能
                Glide.with(viewHolder.itemView.getContext()).load(Contacts.IMG + winSideBean.getSkill().get(0).getIconFile()).into(gaideInfoViewHolder.imgGuadieUserskill1);
                Glide.with(viewHolder.itemView.getContext()).load(Contacts.IMG + winSideBean.getSkill().get(1).getIconFile()).into(gaideInfoViewHolder.imgGuadieUserskill2);
                //装备
               /* gaideInfoViewHolder.recyclerEquaipment.setLayoutManager(new NoScrollLinearLayout(viewHolder.itemView.getContext()
                        , LinearLayoutManager.HORIZONTAL, false));*/
               gaideInfoViewHolder.recyclerEquaipment.setLayoutManager(new GridLayoutManager(viewHolder.itemView.getContext(),3));
                gaideInfoViewHolder.recyclerEquaipment.setNestedScrollingEnabled(false);
                EquaipeMentAdapter equaipeMentAdapter = new EquaipeMentAdapter();
                gaideInfoViewHolder.recyclerEquaipment.setAdapter(equaipeMentAdapter);
                equaipeMentAdapter.setIswin(true);
                equaipeMentAdapter.setWin_equipBeanList(winSideBean.getEquip());
                equaipeMentAdapter.notifyDataSetChanged();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!winSideBean.getRoleName().equals(nickname)) {
                            Intent intent = new Intent(viewHolder.itemView.getContext(), ListActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("mode", true);
                            bundle.putString("nickname", winSideBean.getRoleName());
                                  /*   if (SpUtils.getMainUser()==null)
                        {
                            SpUtils.setbackUser(SpUtils.getNowUser());
                        }
                        SpUtils.selectUser(winSideBean.getRoleName());*/
                            intent.putExtras(bundle);
                            viewHolder.itemView.getContext().startActivity(intent);
                        } else {
                            Toast.makeText(viewHolder.itemView.getContext(), "请点击查看其他人的战绩", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
            } else {
                //输
                final GameInfo.MatchBean.LoseSideBean loseSideBean = loseSideBeanList.get(i);
                if (loseSideBean.getRoleName().equals(nickname)) {
                    gaideInfoViewHolder.tvGuaideUserguaide.setVisibility(View.VISIBLE);
                    if (loseSideBean.getRoleName().equals(SpUtils.getMainUser())) {
                        gaideInfoViewHolder.tvGuaideUserguaide.setText("我");
                    } else {
                        gaideInfoViewHolder.tvGuaideUserguaide.setText("他");
                    }

                } else {
                    gaideInfoViewHolder.tvGuaideUserguaide.setVisibility(View.GONE);
                }
                gaideInfoViewHolder.tvGuaideTipoff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tipoffClickListener!=null)
                        {
                            tipoffClickListener.click(i,loseSideBean);
                        }
                    }
                });
                gaideInfoViewHolder.tvGuaideUserguaideinfo.setText(loseSideBean.getKillCount() + "杀 " + loseSideBean.getDeathCount() + "死 " + loseSideBean.getAssistCount() + "助 评分" + loseSideBean.getKDA());
                gaideInfoViewHolder.tvGuaideUsername.setText(loseSideBean.getRoleName());
                gaideInfoViewHolder.tvGuaidePowervalue.setText("团分:"+loseSideBean.getELO());
                int percent = (int)(((double) loseSideBean.getWinCount()/(double) loseSideBean.getMatchCount())*100);
                gaideInfoViewHolder.tvGuaidePercent.setText("胜率:"+percent+"%");
                //处理mvp
                if (loseSideBean.getRoleName().equals(gameUtils.getLoseKillRole(loseSideBeanList))) {
                    gaideInfoViewHolder.imgKillAll.setVisibility(View.VISIBLE);

                } else {
                    gaideInfoViewHolder.imgKillAll.setVisibility(View.INVISIBLE);
                }
                int rank = gameUtils.getLoseRank(loseSideBeanList, loseSideBean.getRoleName());
                if (rank == 1) {
                    gaideInfoViewHolder.imgGaideAvator.setTagEnable(true);
                    gaideInfoViewHolder.imgGaideAvator.setTagText("躺输");
                    gaideInfoViewHolder.imgGaideAvator.setTagBackgroundColor(UiUtlis.getColor(R.color.Red));
                } else {
                    //处理其他标签
                    if (rank >= 1 && rank <= 3) {

                        gaideInfoViewHolder.imgGaideAvator.setTagEnable(false);

                    }

                    if (rank >= 4 && rank <= 5) {
                        gaideInfoViewHolder.imgGaideAvator.setTagEnable(false);
                    }


                    if (rank >= 6 && rank <= 7) {
                        gaideInfoViewHolder.imgGaideAvator.setTagEnable(true);
                        gaideInfoViewHolder.imgGaideAvator.setTagText("坑");
                        gaideInfoViewHolder.imgGaideAvator.setTagBackgroundColor(UiUtlis.getColor(R.color.black));
                        gaideInfoViewHolder.tvGuaideTipoff.setVisibility(View.VISIBLE);

                    } else {
                        gaideInfoViewHolder.imgGaideAvator.setTagEnable(false);
                    }
                }


                //加载图片
                /*  Glide.with(viewHolder.itemView.getContext()).load(Contacts.IMG+loseSideBean.getHero().getIconFile()).into(gaideInfoViewHolder.imgGaideAvator);*/
                try {
                    gaideInfoViewHolder.imgGaideAvator.setTag(null);
                    MyApplication.getImageCenter().setPic(gaideInfoViewHolder.imgGaideAvator, loseSideBean.getHero().getName(), Contacts.IMG + loseSideBean.getHero().getIconFile());
                } catch (Exception e) {
                    gaideInfoViewHolder.imgGaideAvator.setTag(null);
                    Glide.with(viewHolder.itemView.getContext()).load(Contacts.IMG + loseSideBean.getHero().getIconFile()).into(gaideInfoViewHolder.imgGaideAvator);
                }
                //召唤师技能
                Glide.with(viewHolder.itemView.getContext()).load(Contacts.IMG + loseSideBean.getSkill().get(0).getIconFile()).into(gaideInfoViewHolder.imgGuadieUserskill1);
                Glide.with(viewHolder.itemView.getContext()).load(Contacts.IMG + loseSideBean.getSkill().get(1).getIconFile()).into(gaideInfoViewHolder.imgGuadieUserskill2);
                //装备
          /*      gaideInfoViewHolder.recyclerEquaipment.setLayoutManager(new NoScrollLinearLayout(viewHolder.itemView.getContext()
                    ,LinearLayoutManager.HORIZONTAL,false));*/
                gaideInfoViewHolder.recyclerEquaipment.setLayoutManager(new GridLayoutManager(viewHolder.itemView.getContext()
                        , 3));
                gaideInfoViewHolder.recyclerEquaipment.setNestedScrollingEnabled(false);
                EquaipeMentAdapter equaipeMentAdapter = new EquaipeMentAdapter();
                gaideInfoViewHolder.recyclerEquaipment.setAdapter(equaipeMentAdapter);
                equaipeMentAdapter.setIswin(false);
                equaipeMentAdapter.setLose_equipBeanList(loseSideBean.getEquip());
                equaipeMentAdapter.notifyDataSetChanged();

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!loseSideBean.getRoleName().equals(nickname)) {
                            Intent intent = new Intent(viewHolder.itemView.getContext(), ListActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("mode", true);
                            bundle.putString("nickname", loseSideBean.getRoleName());
                     /*   if (SpUtils.getMainUser()==null)
                        {
                            SpUtils.setbackUser(SpUtils.getNowUser());
                        }
                        SpUtils.selectUser(winSideBean.getRoleName());*/

                            intent.putExtras(bundle);
                            viewHolder.itemView.getContext().startActivity(intent);
                        } else {
                            Toast.makeText(viewHolder.itemView.getContext(), "请查看其他人的战绩", Toast.LENGTH_SHORT).show();
                        }


                    }
                });


            }
        }
    }

    @Override
    public int getItemCount() {
        if (winSideBeanList != null && winSideBeanList.size() > 0) {
            return winSideBeanList.size();
        }
        if (loseSideBeanList != null && loseSideBeanList.size() > 0) {
            return loseSideBeanList.size();
        }
        return 0;
    }

    class GaideInfoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_gaide_avator)
        SimpleTagImageView imgGaideAvator;
        @BindView(R.id.tv_guaide_username)
        TextView tvGuaideUsername;
        @BindView(R.id.img_kill_all)
        ImageView imgKillAll;
        @BindView(R.id.tv_guaide_userguaideinfo)
        TextView tvGuaideUserguaideinfo;
        @BindView(R.id.tv_guaide_userguaide)
        TextView tvGuaideUserguaide;
        @BindView(R.id.img_guadie_userskill1)
        ImageView imgGuadieUserskill1;
        @BindView(R.id.img_guadie_userskill2)
        ImageView imgGuadieUserskill2;
        @BindView(R.id.recycler_equaipment)
        RecyclerView recyclerEquaipment;
        @BindView(R.id.tv_guaide_powervalue)
        TextView tvGuaidePowervalue;
        @BindView(R.id.tv_guaide_percent)
        TextView tvGuaidePercent;
        @BindView(R.id.tv_guaide_tipoff)
        TextView tvGuaideTipoff;
        public GaideInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }
    public interface OnTipoffClickListener
    {
        //两种类型
        void click(int pos,Object object);
    }
}