package com.lyc.study.go016;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lyc.common.CustomDialog;
import com.lyc.common.UtilsManager;
import com.lyc.study.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by lyc on 17/6/29.
 */

public class GoRecycleViewActivity  extends Activity implements View.OnClickListener {
    private EditText et_add_index,et_add_content,et_del_index;
    private Button btn_add,btn_del;
    private RecyclerView lv;
    private ArrayList<Data> dataArr=new ArrayList<>();
    private Adapter adapter;
    private RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        et_add_index = (EditText) findViewById(R.id.et_add_index);
        et_add_content = (EditText) findViewById(R.id.et_add_content);
        et_del_index = (EditText) findViewById(R.id.et_del_index);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        btn_del = (Button) findViewById(R.id.btn_del);
        btn_del.setOnClickListener(this);
        lv = (RecyclerView) findViewById(R.id.lv);
        manager=new LinearLayoutManager(this);
        lv.setLayoutManager(manager);
        initData();
        adapter = new Adapter();
        lv.setAdapter(adapter);
    }

    private void initData(){
        for (int i=0;i<20;i++){
            Data item= new Data();
            item.originPos=i;
            item.content=getChineseName();
            dataArr.add(item);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:{
                if (TextUtils.isEmpty(et_add_index.getText())){
                    return;
                }
                int position= Integer.valueOf(et_add_index.getText().toString());
                if (position<0){
                    UtilsManager.toast(this,"不能小于0");
                    return;
                }

                String content= et_add_content.getText().toString();
                if (TextUtils.isEmpty(content)){
                    UtilsManager.toast(this,"输入内容");
                    return;
                }
                if (position!=0&&position>adapter.getItemCount()-1){
                    UtilsManager.toast(this,"现在一共有"+adapter.getItemCount()+"个，,要插入的位置不存在");
                    return;
                }
                Data item= new Data();
                item.originPos= position;
                item.content= content;
                dataArr.add(position,item);
                adapter.notifyItemInserted(position);
                et_add_index.setText("");
                et_add_content.setText("");
                break;
            }
            case R.id.btn_del:{
                if (TextUtils.isEmpty(et_del_index.getText())){
                    return;
                }
                int position= Integer.valueOf(et_del_index.getText().toString());
                if (position<0){
                    UtilsManager.toast(this,"不能小于0");
                    return;
                }
                if (position>adapter.getItemCount()-1){
                    UtilsManager.toast(this,"现在一共有"+adapter.getItemCount()+"个,要删除的位置不存在");
                    return;
                }
                dataArr.remove(position);
                adapter.notifyItemRemoved(position);
                et_add_index.setText("");
                break;
            }
        }
    }

    public class Data{
        public int originPos;
        public String content;
    }

    public class Adapter extends  RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(GoRecycleViewActivity.this).inflate(R.layout.view_lv_item,null);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((MyViewHolder)holder).initData(dataArr.get(position));

        }

        @Override
        public int getItemCount() {
            return dataArr.size();
        }
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_origin_index,tv_origin_content;
        private Button btn_check,btn_insert;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_origin_index = (TextView) itemView.findViewById(R.id.tv_origin_index);
            tv_origin_content = (TextView) itemView.findViewById(R.id.tv_origin_content);
            btn_check = (Button) itemView.findViewById(R.id.btn_check);
            btn_insert = (Button) itemView.findViewById(R.id.btn_insert);

        }

        public void initData(final Data item){
            tv_origin_index.setText(String.valueOf(item.originPos));
            tv_origin_content.setText(item.content);
            btn_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StringBuilder sb=new StringBuilder();
                    sb.append("RecyclerView.getChildLayoutPosition"+lv.getChildLayoutPosition(itemView)).append("\n");
                    sb.append("RecyclerView.getChildLayoutPosition"+MyViewHolder.this.getLayoutPosition()).append("\n");
                    sb.append("originPosition"+item.originPos);
                    CustomDialog infoDialog= new CustomDialog(GoRecycleViewActivity.this);
                    infoDialog.show(sb.toString());
                }
            });

            btn_insert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int targetPos=getLayoutPosition()+1;
                    Data item= new Data();
                    item.originPos=targetPos;
                    item.content=getChineseName();
                    if (targetPos>dataArr.size()-1){
                        dataArr.add(item);
                    }else {
                        dataArr.add(targetPos,item);
                    }
                    adapter.notifyItemInserted(targetPos);
                }
            });


        }
    }

    private static String firstName="赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张孔曹严华金魏陶姜戚谢邹喻柏水窦章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳酆鲍史唐费廉岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅皮卞齐康伍余元卜顾孟平黄和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计伏成戴谈宋茅庞熊纪舒屈项祝董梁杜阮蓝闵席季麻强贾路娄危江童颜郭梅盛林刁钟徐邱骆高夏蔡田樊胡凌霍虞万支柯咎管卢莫经房裘缪干解应宗宣丁贲邓郁单杭洪包诸左石崔吉钮龚程嵇邢滑裴陆荣翁荀羊於惠甄魏加封芮羿储靳汲邴糜松井段富巫乌焦巴弓牧隗山谷车侯宓蓬全郗班仰秋仲伊宫宁仇栾暴甘钭厉戎祖武符刘姜詹束龙叶幸司韶郜黎蓟薄印宿白怀蒲台从鄂索咸籍赖卓蔺屠蒙池乔阴郁胥能苍双闻莘党翟谭贡劳逄姬申扶堵冉宰郦雍却璩桑桂濮牛寿通边扈燕冀郏浦尚农温别庄晏柴瞿阎充慕连茹习宦艾鱼容向古易慎戈廖庚终暨居衡步都耿满弘匡国文寇广禄阙东殴殳沃利蔚越夔隆师巩厍聂晁勾敖融冷訾辛阚那简饶空曾毋沙乜养鞠须丰巢关蒯相查后江红游竺权逯盖益桓公万俟司马上官欧阳夏侯诸葛闻人东方赫连皇甫尉迟公羊澹台公冶宗政濮阳淳于仲孙太叔申屠公孙乐正轩辕令狐钟离闾丘长孙慕容鲜于宇文司徒司空亓官司寇仉督子车颛孙端木巫马公西漆雕乐正壤驷公良拓拔夹谷宰父谷粱晋楚阎法汝鄢涂钦段干百里东郭南门呼延归海羊舌微生岳帅缑亢况后有琴梁丘左丘东门西门商牟佘佴伯赏南宫墨哈谯笪年爱阳佟第五言福百家姓续";
    private static String girl="秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽 ";
    private static String boy="伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子杰涛昌成康星光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘";


    public static int getNum(int start,int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }
    /**
     * 返回中文姓名
     */
    private static String name_sex = "";
    private static String getChineseName() {
        int index=getNum(0, firstName.length()-1);
        String first=firstName.substring(index, index+1);
        int sex=getNum(0,1);
        String str=boy;
        int length=boy.length();
        if(sex==0){
            str=girl;
            length=girl.length();
            name_sex = "女";
        }else {
            name_sex="男";
        }
        index=getNum(0,length-1);
        String second=str.substring(index, index+1);
        int hasThird=getNum(0,1);
        String third="";
        if(hasThird==1){
            index=getNum(0,length-1);
            third=str.substring(index, index+1);
        }
        return first+second+third;
    }
}
