package com.liuguilin.tallybook.entity;

/*
 *  项目名：  TallyBook
 *  包名：    com.liuguilin.tallybook.entity
 *  文件名:   CostModel
 *  创建者:   LiuGuiLinAndroid
 *  创建时间:  2017/3/26 17:50
 *  描述：    数据模型
 */
public class CostModel {

    private String title;
    private String date;
    private String money;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
