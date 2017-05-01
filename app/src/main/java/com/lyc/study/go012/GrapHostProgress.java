package com.lyc.study.go012;

/**
 * Created by lyc on 17/4/22.
 */

public class GrapHostProgress {


    public  class OnlookerState  extends State{
        @Override
        void refreshView() {

        }

        void  transAudience(){

        }

    }

    public  class Go2JoinState  extends State{
        @Override
        void refreshView() {

        }

        void  transAudience(){

        }

        //转成HaveJoinState 状态  并开始倒计时
        void  sitDown(){

        }

    }

    /**
     *  已经占位   到 准备结束/准备超时的状态
     */
    public  class HaveJoinState  extends State{

        @Override
        void refreshView() {

        }

        /**
         * 转成OnlookerState 状态
         */
        void coutdownFinish() {

        }

        void transHost() {

        }
    }



}
