package com.example.myfcm.model;

// FCM 으로 보낸 메시지를 포어그라운드(Foreground)에서 처리하는 데이터 클래스
public class Data {
    // 발신자 정보
    private String sender;
    // 내용
    private String contents;
    // send time
    private String time;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
