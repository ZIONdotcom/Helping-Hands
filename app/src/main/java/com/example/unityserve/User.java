package com.example.unityserve;

public class User {
        private String email;
        private String usertype;

        public User() {
            // Required empty public constructor
        }

        public User(String email, String userType) {
            this.email = email;
            this.usertype = userType;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsertype() {
            return usertype;
        }

        public void setUsertype(String userType) {
            this.usertype = userType;
        }


}
