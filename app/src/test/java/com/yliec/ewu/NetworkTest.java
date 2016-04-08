package com.yliec.ewu;

import com.yliec.ewu.api.entity.element.AuthUser;

import org.junit.Before;
import org.junit.Test;


/**
 * Created by Lecion on 11/30/15.
 */
public class NetworkTest {
    private ApiHub api;

    @Before
    public void setUp() throws Exception {
        api = new ApiHub();
    }

    @Test
    public void testGoodsList() throws Exception {
        api.goodsList();
    }

    @Test
    public void testGoods() throws Exception {
        api.getGoods("6d641e4219");
    }

    @Test
    public void testGetUserInfo() {
        api.getUserInfo("aaaa");
    }

    @Test
    public void testGetToken() {
        AuthUser authUser = new AuthUser("bubble", "123456");

        api.getToken(authUser);
    }

    @Test
    public void testGetUsers() {
        api.getUsers();
    }

    @Test
    public void testGetUserInfoRx() throws Exception {
        api.getUserInfoRx("lecion");
    }
}