package com.quickwebapp.weixin.entity;

import java.io.Serializable;

public class MenuTree implements Serializable {
    private static final long serialVersionUID = -2729288516270190312L;
    private Menu[] button; // 一级菜单数组，个数应为1~3个

    public Menu[] getButton() {
        return button;
    }

    public void setButton(Menu[] button) {
        this.button = button;
    }

}

