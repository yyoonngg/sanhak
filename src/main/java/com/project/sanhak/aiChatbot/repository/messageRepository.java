package com.project.sanhak.aiChatbot.repository;

import com.project.sanhak.domain.chat.ChatMessage;
import com.project.sanhak.domain.chat.ChatRooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface messageRepository extends JpaRepository<ChatMessage, Integer> {
    void deleteByChatRoom(ChatRooms chatRoom);

    List<ChatMessage> findByCMcrid_CRIdOrderByCMTimeAsc(int chat_id);
}
