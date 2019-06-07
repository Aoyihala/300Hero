# 一款md风格的300英雄战绩查询软件
#### 开发进度(15%)
> - 数据本地持久化
> - 角色绑定
> - 战绩列表分页查询
> - 战绩角色切换
> - mvp和角色在当前场次的角色定位计算密码
> - 针对于加载缓慢添加的ip代理地址
> - 战局详情
> - 段位局势
#### 数据来源
300战绩查询报告官网
#### 角色战局定位计算公式
1.**MVP**计算是根据评分和参团率和总经济来计算的,战败方名称为**躺输**计算规则一致。  

2.**坑**和**划水**是直接按评分来计算的，最后一名即划水大师或坑神。  

3.**神队友**是只要单局助攻大于30个即可，战胜方的标记。
#### 段位计算方式
0-1000 青铜 1000-2000 白银 2000-3000黄金 3000以上 钻石  
再往上没意义（万分单排大佬占少数，套分车太多了）这个段位看着玩好了，后面再来个大师王者
****
#### 界面预览(不代表最终页面效果)
![战绩列表预览1](https://github.com/Aoyihala/img/blob/master/300data/guaide.png?raw=true)  
![战绩列表预览1](https://github.com/Aoyihala/img/blob/master/300data/myguaide.png?raw=true)  
![角色管理](https://github.com/Aoyihala/img/blob/master/300data/charatermanage.png?raw=true)  
![战绩列表预览2](https://github.com/Aoyihala/img/blob/master/300data/myguaide2.png?raw=true)  
#### 关于ip地址代理池
[西刺](https://www.xicidaili.com)ip代理池，利用[jsoup](https://jsoup.org/download)解析
## License

          300Hero  Copyright (C) 2019  Aoyihala xiataoyunr@gmail.com or 1214504624@qq.com
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
