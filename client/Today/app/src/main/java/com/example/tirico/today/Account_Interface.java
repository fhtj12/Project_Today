package com.example.tirico.today;

/**
 * Created by Tirico on 2018-06-06.
 */

public interface Account_Interface {
    public String sign_in(String id, String pwd);
    public String sign_out(String uid);
    public String update_account(Account account);
    public String delete_account(String id, String pwd);
    public String sign_up(Account account);
}
