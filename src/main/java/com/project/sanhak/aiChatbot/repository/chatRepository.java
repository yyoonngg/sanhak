package com.project.sanhak.aiChatbot.repository;

import com.project.sanhak.domain.card.ExperienceCard;
import com.project.sanhak.domain.chat.ChatRooms;
import com.project.sanhak.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface chatRepository extends JpaRepository<ChatRooms, Integer> {

    List<ChatRooms> findByCRecid(ExperienceCard card);

    int findCRTypeByCRId(int chatId);

    ChatRooms findTopByCRuidOrderByCRIdDesc(User CRuid);

    int findCRecIdByCRId(int chatId);

    // 사용자별 채팅방 목록 조회
    List<ChatRooms> findByCRuid_UId(int uid);

    // 특정 채팅방이 해당 사용자에 속하는지 확인
    Optional<ChatRooms> findByCRIdAndCRuid_UId(int chat_id, int uid);
}
