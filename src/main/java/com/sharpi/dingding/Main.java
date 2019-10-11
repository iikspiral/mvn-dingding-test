package com.sharpi.dingding;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiUserListbypageRequest;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiUserListbypageResponse;
import com.taobao.api.ApiException;

public class Main {
    public static void main(String [] args) throws ApiException {
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey("key");
        request.setAppsecret("sr");
        request.setHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);
        getDeaprtment(response.getAccessToken());
        System.out.println(response.getAccessToken());
    }
    public static void getDeaprtment(String token) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setId("1");
        request.setHttpMethod("GET");
        OapiDepartmentListResponse response = client.execute(request, token);
        for (int i = 0; i < response.getDepartment().size(); i++) {
            OapiDepartmentListResponse.Department s = (OapiDepartmentListResponse.Department)response.getDepartment().get(i);
            System.out.println(s.getId()+"  "+s.getName());
            getClient(s.getId(),token);
        }
    }
    public static void getClient(Long departmentid, String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/listbypage");
        OapiUserListbypageRequest request = new OapiUserListbypageRequest();
        request.setDepartmentId(departmentid);
        request.setOffset(0L);
        request.setSize(10L);
        request.setOrder("entry_desc");
        request.setHttpMethod("GET");
        OapiUserListbypageResponse response = client.execute(request,accessToken);
        for (int i = 0; i < response.getUserlist().size(); i++) {
            OapiUserListbypageResponse.Userlist s = (OapiUserListbypageResponse.Userlist)response.getUserlist().get(i);
            System.out.println(s.getUserid() + "  " + s.getName() + "  " + s.getMobile() );
        }


    }
}
