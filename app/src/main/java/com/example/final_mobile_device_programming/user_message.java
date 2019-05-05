package com.example.final_mobile_device_programming;

public class user_message {
    private String text;
    private MemberData memberData;
    private boolean belongsToCurrentUser;

    public user_message(String text, MemberData data, boolean belongsToCurrentUser) {
        this.text = text;
        this.memberData = data;
        this.belongsToCurrentUser = belongsToCurrentUser;
    }

    public String getText() {
        return text;
    }

    public MemberData getMemberData() {
        return memberData;
    }

    public boolean isBelongsToCurrentUser() {
        return belongsToCurrentUser;
    }
}
}
