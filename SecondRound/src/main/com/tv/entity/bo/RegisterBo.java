package tv.entity.bo;

import javax.servlet.http.Part;

/**
 * 注册
 *
 * @author 刘家辉
 * @date 2023/05/21
 */
public class RegisterBo {
    private String username;
    private String password;
    private Part avatar;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Part getAvatar() {
        return avatar;
    }

    public void setAvatar(Part avatar) {
        this.avatar = avatar;
    }
}
