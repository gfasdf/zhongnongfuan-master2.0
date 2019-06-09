package com.zhongnongfuan.app.bean;

import java.io.Serializable;
import java.util.List;

public class LoginResponseBean implements Serializable {


    /**
     * code : 1
     * msg : Success
     * accesstoken : null
     * count : 0
     * data : {"Claims":[],"Logins":[],"Roles":[{"UserId":"223c64d9-ee50-4253-8aaf-00161bd4752d","RoleId":"bf0762b0-aa5f-437c-ab10-e83becd30a75"}],"userType":null,"name":"系统管理员","departId":null,"departName":null,"gender":null,"phone":null,"IsLocked":false,"Email":"admin@example.com","EmailConfirmed":false,"PasswordHash":"AIpwfXnPSLFEeD1iUHAbXxufvK4N0h7E8tDSOQhXxYXt09xth7eybjxJGrBDHJfWYA==","SecurityStamp":"0a0b7bb2-acaa-46a9-bacf-04c168254570","PhoneNumber":null,"PhoneNumberConfirmed":false,"TwoFactorEnabled":false,"LockoutEndDateUtc":null,"LockoutEnabled":false,"AccessFailedCount":0,"Id":"223c64d9-ee50-4253-8aaf-00161bd4752d","UserName":"Admin"}
     */

    private int code;
    private String msg;
    private Object accesstoken;
    private int count;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(Object accesstoken) {
        this.accesstoken = accesstoken;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements  Serializable{
        /**
         * Claims : []
         * Logins : []
         * Roles : [{"UserId":"223c64d9-ee50-4253-8aaf-00161bd4752d","RoleId":"bf0762b0-aa5f-437c-ab10-e83becd30a75"}]
         * userType : null
         * name : 系统管理员
         * departId : null
         * departName : null
         * gender : null
         * phone : null
         * IsLocked : false
         * Email : admin@example.com
         * EmailConfirmed : false
         * PasswordHash : AIpwfXnPSLFEeD1iUHAbXxufvK4N0h7E8tDSOQhXxYXt09xth7eybjxJGrBDHJfWYA==
         * SecurityStamp : 0a0b7bb2-acaa-46a9-bacf-04c168254570
         * PhoneNumber : null
         * PhoneNumberConfirmed : false
         * TwoFactorEnabled : false
         * LockoutEndDateUtc : null
         * LockoutEnabled : false
         * AccessFailedCount : 0
         * Id : 223c64d9-ee50-4253-8aaf-00161bd4752d
         * UserName : Admin
         */

        private Object userType;
        private String name;
        private Object departId;
        private Object departName;
        private Object gender;
        private Object phone;
        private boolean IsLocked;
        private String Email;
        private boolean EmailConfirmed;
        private String PasswordHash;
        private String SecurityStamp;
        private Object PhoneNumber;
        private boolean PhoneNumberConfirmed;
        private boolean TwoFactorEnabled;
        private Object LockoutEndDateUtc;
        private boolean LockoutEnabled;
        private int AccessFailedCount;
        private String Id;
        private String UserName;
        private List<?> Claims;
        private List<?> Logins;
        private List<RolesBean> Roles;

        public Object getUserType() {
            return userType;
        }

        public void setUserType(Object userType) {
            this.userType = userType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getDepartId() {
            return departId;
        }

        public void setDepartId(Object departId) {
            this.departId = departId;
        }

        public Object getDepartName() {
            return departName;
        }

        public void setDepartName(Object departName) {
            this.departName = departName;
        }

        public Object getGender() {
            return gender;
        }

        public void setGender(Object gender) {
            this.gender = gender;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public boolean isIsLocked() {
            return IsLocked;
        }

        public void setIsLocked(boolean IsLocked) {
            this.IsLocked = IsLocked;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public boolean isEmailConfirmed() {
            return EmailConfirmed;
        }

        public void setEmailConfirmed(boolean EmailConfirmed) {
            this.EmailConfirmed = EmailConfirmed;
        }

        public String getPasswordHash() {
            return PasswordHash;
        }

        public void setPasswordHash(String PasswordHash) {
            this.PasswordHash = PasswordHash;
        }

        public String getSecurityStamp() {
            return SecurityStamp;
        }

        public void setSecurityStamp(String SecurityStamp) {
            this.SecurityStamp = SecurityStamp;
        }

        public Object getPhoneNumber() {
            return PhoneNumber;
        }

        public void setPhoneNumber(Object PhoneNumber) {
            this.PhoneNumber = PhoneNumber;
        }

        public boolean isPhoneNumberConfirmed() {
            return PhoneNumberConfirmed;
        }

        public void setPhoneNumberConfirmed(boolean PhoneNumberConfirmed) {
            this.PhoneNumberConfirmed = PhoneNumberConfirmed;
        }

        public boolean isTwoFactorEnabled() {
            return TwoFactorEnabled;
        }

        public void setTwoFactorEnabled(boolean TwoFactorEnabled) {
            this.TwoFactorEnabled = TwoFactorEnabled;
        }

        public Object getLockoutEndDateUtc() {
            return LockoutEndDateUtc;
        }

        public void setLockoutEndDateUtc(Object LockoutEndDateUtc) {
            this.LockoutEndDateUtc = LockoutEndDateUtc;
        }

        public boolean isLockoutEnabled() {
            return LockoutEnabled;
        }

        public void setLockoutEnabled(boolean LockoutEnabled) {
            this.LockoutEnabled = LockoutEnabled;
        }

        public int getAccessFailedCount() {
            return AccessFailedCount;
        }

        public void setAccessFailedCount(int AccessFailedCount) {
            this.AccessFailedCount = AccessFailedCount;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public List<?> getClaims() {
            return Claims;
        }

        public void setClaims(List<?> Claims) {
            this.Claims = Claims;
        }

        public List<?> getLogins() {
            return Logins;
        }

        public void setLogins(List<?> Logins) {
            this.Logins = Logins;
        }

        public List<RolesBean> getRoles() {
            return Roles;
        }

        public void setRoles(List<RolesBean> Roles) {
            this.Roles = Roles;
        }

        public static class RolesBean implements Serializable{
            /**
             * UserId : 223c64d9-ee50-4253-8aaf-00161bd4752d
             * RoleId : bf0762b0-aa5f-437c-ab10-e83becd30a75
             */

            private String UserId;
            private String RoleId;

            public String getUserId() {
                return UserId;
            }

            public void setUserId(String UserId) {
                this.UserId = UserId;
            }

            public String getRoleId() {
                return RoleId;
            }

            public void setRoleId(String RoleId) {
                this.RoleId = RoleId;
            }
        }
    }
}
