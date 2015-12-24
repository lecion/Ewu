package com.yliec.ewu;

import com.yliec.ewu.api.entity.element.RequestUser;

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
        RequestUser requestUser = new RequestUser("aaaaa", "aaaaaa");

        api.getToken(requestUser);
    }

    @Test
    public void testGetUsers() {
        api.getUsers();
    }
}