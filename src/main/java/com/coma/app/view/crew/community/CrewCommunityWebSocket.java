package com.coma.app.view.crew.community;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ServerEndpoint("/chat/{member_id}")
public class CrewCommunityWebSocket {

    private static final List<Session> sessions = new ArrayList<>(); // 현재 채팅방 사용자 목록

    @OnOpen // WebSocket 연결 열기
    public void open(Session memberSession, @PathParam("member_id") String member_id) {
        log.info("CrewCommunityWebSocket.open : 실시간 채팅 연결");
        sessions.add(memberSession); // 세션에 사용자 추가
        log.info(memberSession.getId());
        log.info(member_id);

        // 모든 클라이언트에게 입장 메시지 전송
        broadcastMessage(createJsonMessage(member_id, member_id + "님이 입장하였습니다", "./img/arashmil.jpg", "코마"));
    }

    @OnMessage // 메시지 받기
    public void getMsg(Session receiveSession, String msg, @PathParam("member_id") String member_id) {
        log.info("전달 메세지 [{}] [{}]",member_id,msg);

        // 모든 클라이언트에게 메시지 전송
        broadcastMessage(msg);
    }

    @OnClose // WebSocket 연결 닫기
    public void onClose(Session receiveSession, @PathParam("member_id") String member_id) {
        // 현재 websocket 사용자 리스트에서 현재 사용자 제거
        sessions.remove(receiveSession);
        log.info("CrewCommunityWebSocket.onClose : 실시간 채팅 연결 해제");
        // 모든 클라이언트에게 퇴장 메시지 전송
        broadcastMessage(createJsonMessage(member_id, member_id + "님이 채팅방을 나갔습니다", "./img/arashmil.jpg", "코마"));
    }

    private void broadcastMessage(String msg) {
        for (Session session : sessions) {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String createJsonMessage(String member_id, String content, String profileUrl, String member_name) {
        String jsonMessage = """
                {crew_board_writer_id:%s, crew_board_content:%s, crew_board_writer_profile:%s, crew_board_writer_name:%s}""";
        return String.format(jsonMessage, member_id, content, profileUrl, member_name);
    }
}
