package enpalmer.intellimem.domain.todo.service;

import enpalmer.intellimem.domain.todo.dto.TodoInfoResponse;
import enpalmer.intellimem.domain.todo.dto.UpdateTodoStatusRequest;
import enpalmer.intellimem.domain.todo.dto.CreateTodoRequest;
import enpalmer.intellimem.domain.todo.entity.Todo;
import enpalmer.intellimem.domain.todo.repository.TodoRepository;
import enpalmer.intellimem.domain.todo.utility.DateTimeFormatterUtil;
import enpalmer.intellimem.domain.user.repository.UserRepository;
import enpalmer.intellimem.domain.user.entity.User;
import enpalmer.intellimem.global.exception.errorcode.TodoErrorCode;
import enpalmer.intellimem.global.exception.errorcode.UserErrorCode;
import enpalmer.intellimem.global.exception.exception.TodoException;
import enpalmer.intellimem.global.exception.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final DateTimeFormatterUtil dateTimeFormatterUtil;
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<TodoInfoResponse> getTodoListByUserId(String userId){
        return todoRepository.getTodoByUserId(userId).stream()
                .map(todo -> new TodoInfoResponse(
                        todo.getId(),
                        todo.getTask(),
                        dateTimeFormatterUtil.localDateTimeToString(todo.getScheduledAt()),
                        todo.isCalendered(),
                        todo.isCompleted()))
                .toList();
    }

    @Transactional
    public int createNewTodo(CreateTodoRequest createTodoRequest){
        User user = userRepository.findById(createTodoRequest.userId())
                .orElseThrow(()-> new UserException(UserErrorCode.NOT_FOUND));

        Todo todo = Todo.builder()
                .user(user)
                .scheduledAt(dateTimeFormatterUtil.stringToLocalDateTime(createTodoRequest.time()))
                .task(createTodoRequest.task())
                .completed(false)
                .build();

        todoRepository.save(todo);

        return todo.getId();
    }

    @Transactional
    public int updateTodoStatus(UpdateTodoStatusRequest updateTodoStatusRequest){
        Todo todo = todoRepository.findById(updateTodoStatusRequest.todoId())
                .orElseThrow(() -> new TodoException(TodoErrorCode.NOT_FOUND));

        todoRepository.changeTodoStatus(todo.getId());

        return todo.getId();
    }
}
