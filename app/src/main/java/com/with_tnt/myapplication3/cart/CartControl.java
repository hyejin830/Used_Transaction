package com.with_tnt.myapplication3.cart;

import android.graphics.drawable.Drawable;

/**
 * Created by ANGELZIN on 2017-05-09.
 */

public class CartControl {
    private String titleStr;
    private Drawable ProductImage;
    private int ProductNum;
    private int categoryStr;
    private int stateStr;
    private int costStr;

    public CartControl(String title, int category, int state, int cost,int pnumber) {
        // TODO Auto-generated constructor stub

        this.titleStr = title;
        this.categoryStr = category;
        this.stateStr = state;
        this.costStr = cost;
        this.ProductNum= pnumber;
    }

    public void setTitle(String title) {
        titleStr = title;
    }

    public void setProduct(Drawable product) {
        ProductImage = product;
    }


    public String getTitle() {
        return this.titleStr;
    }

    public Drawable getProductImage() {
        return this.ProductImage;
    }

    public int getCategoryStr() {
        return this.categoryStr;
    }

    public int getStateStr() {
        return this.stateStr;
    }

    public int getCostStr() {
        return this.costStr;
    }

    public int getProductNum(){
        return this.ProductNum;
    }
}

