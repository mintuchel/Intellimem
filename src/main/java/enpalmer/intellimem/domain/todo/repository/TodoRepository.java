package enpalmer.intellimem.domain.todo.repository;

import enpalmer.intellimem.domain.todo.entity.Todo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

    // TODO 삭제
    @Modifying
    @Query(value = "DELETE FROM todo WHERE id = :id", nativeQuery = true)
    void deleteById(@Param("id") Integer id);

    // 특정 유저의 모든 TODO 조회
    @Query(value = "SELECT * FROM todo WHERE user_id = :user_id ORDER BY scheduled_at ASC", nativeQuery = true)
    List<Todo> getTodoListByUserId(@Param("user_id") String user_id);

    // 특정 일자 TODO 만 가져오기
    @Query(value = "SELECT * FROM todo WHERE user_id = :user_id AND CAST(scheduled_at AS DATE) = :today", nativeQuery = true)
    List<Todo> getCertainDateTodoListByUserId(@Param("user_id") String user_id, @Param("today") String today);

    // TODO completed 업데이트
    @Modifying
    @Transactional
    @Query(value = "UPDATE todo SET completed = CASE WHEN completed = 1 THEN 0 ELSE 1 END WHERE id = :todo_id", nativeQuery = true)
    void updateCompletedStatus(@Param("todo_id") int todo_id);

    // TODO calendered 업데이트
    @Modifying
    @Transactional
    @Query(value = "UPDATE todo SET calendered = CASE WHEN calendered = 1 THEN 0 ELSE 1 END WHERE id = :todo_id", nativeQuery = true)
    void updateCalenderedStatus(@Param("todo_id") int todo_id);
}
