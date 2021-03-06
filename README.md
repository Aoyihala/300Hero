
# 该项目是工具型app,欢迎使用和拓展。(举例)自己拓展了标识不正当游戏玩家的功能，后台源码待测试完毕后不日放上github，至此该项目更新停止。
## 一款md风格的300英雄战绩查询软件
#### 主要功能
> - 数据本地持久化
> - 角色绑定
> - 战绩列表分页查询
> - 战绩角色切换
> - mvp和角色在当前场次的角色定位计算密码
> - 针对于加载缓慢添加的ip代理地址
> - 战局详情
> - 段位局势
> - 定位雷达图
#### 数据来源
300战绩查询报告官网
#### 角色战局定位计算公式
1.**MVP**计算是根据评分和参团率和总经济来计算的,战败方名称为**躺输**计算规则一致。  

2.**坑**和**划水**是直接按评分来计算的，最后一名即划水大师或坑神。  

3.**神队友**是只要单局助攻大于30个即可，战胜方的标记。
#### 关于数值计算
查看[GameUtils](https://github.com/Aoyihala/300Hero/blob/master/app/src/main/java/com/example/evenalone/a300hero/utils/GameUtils.java)  
包括**MVp**,**统计图数据获取**,**输赢里自己的数据(杀敌 评分)**。
#### 段位计算方式
0-1000 青铜 1000-2000 白银 2000-3000黄金 3000以上 钻石  
再往上没意义（万分单排大佬占少数，套分车太多了）这个段位看着玩好了。自己也才1000多分。
****
#### 界面预览(不代表最终页面效果)(先关掉)
![战绩列表预览1](https://github.com/Aoyihala/img/blob/master/300data/guaide.png?raw=true)  
#### 功能点更新
##### 2019.8.8
> - 1.更新雷达图
> - 2.更新团分统计图,默认查看20局,滑块查看最多50局的趋势(20局缓存数量不够的话有多少显示多少)
> - 3.提示App性能去除请求队列,启动更快
> - 4.团分对比简图,蓝队和红队其实是不能分的,是胜利方和失败方  
![团分趋势1](https://github.com/Aoyihala/img/blob/master/300data/power1.png?raw=true)  
![团分趋势2](https://github.com/Aoyihala/img/blob/master/300data/power2.png?raw=true)  
##### 2019.8.25
> - 1.添加小工具功能
> - 2.添加通知栏定时推送功能
> - 3.新增对统计图的详细操作  
![通知栏1](https://github.com/Aoyihala/img/blob/master/300data/notify2.png?raw=true)  
![通知栏2](https://github.com/Aoyihala/img/blob/master/300data/notify1.png?raw=true)  
![小工具1](https://github.com/Aoyihala/img/blob/master/300data/tool.png?raw=true)  
##### 2019.9.14
> - 1.利用个推推送更新
> - 2.优化图片加载流程
> - 3.优化小工具图片加载过程
##### 2019.9.25
> - 1.利用个推透传推送更新,但是由于没有固定的服务器地址,更新采取推送通知然后在群里下载群文件更新
> - 2.图片加载优化,更改段位描述
> - 3.透传功能保留,定时更新服务保留,等待新的服务器地址
![新界面](https://github.com/Aoyihala/img/blob/master/300data/listshape.png?raw=true)  
##### 2019.10.20
> -1.主动更新功能更新,使用服务常驻自动检测更新
> -2.角色管理功能更新,长久没有管的一个重要功能
> -3.举报功能后台初步测试完成
#### 个推网址:https://www.getui.com/
#### 关于ip地址代理池
[西刺](https://www.xicidaili.com)ip代理池，利用[jsoup](https://jsoup.org/download)解析
## License

          300Hero  Copyright (C) 2019  Aoyihala xiataoyun@gmail.com or 1214504624@qq.com
          This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.
          This is free software, and you are welcome to redistribute it
          under certain conditions; type `show c' for details.

      The hypothetical commands `show w' and `show c' should show the appropriate
      parts of the General Public License.  Of course, your program's commands
      might be different; for a GUI interface, you would use an "about box".

        You should also get your employer (if you work as a programmer) or school,
      if any, to sign a "copyright disclaimer" for the program, if necessary.
      For more information on this, and how to apply and follow the GNU GPL, see
      <http://www.gnu.org/licenses/>.

        The GNU General Public License does not permit incorporating your program
      into proprietary programs.  If your program is a subroutine library, you
      may consider it more useful to permit linking proprietary applications with
      the library.  If this is what you want to do, use the GNU Lesser General
      Public License instead of this License.  But first, please read
      <http://www.gnu.org/philosophy/why-not-lgpl.html>.
