package enpalmer.intellimem.domain.todo.api;

import enpalmer.intellimem.domain.todo.dto.CreateTodoRequest;
import enpalmer.intellimem.domain.todo.dto.TodoInfoResponse;
import enpalmer.intellimem.domain.todo.dto.UpdateTodoStatusRequest;
import enpalmer.intellimem.domain.todo.service.TodoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todo")
@Tag(name = "오늘의 할일 API", description = "오늘의 할일 조회, 추가, 상태변경")
public class TodoController {
    private final TodoService todoService;

    @GetMapping("")
    @Operation(summary = "특정 유저 오늘의 할일 조회")
    public List<TodoInfoResponse> getTodoListById(@RequestParam String userId){
        return todoService.getTodoListByUserId(userId);
    }

    @PostMapping("")
    @Operation(summary = "특정 유저 오늘의 할일 추가")
    public int createTodo(@RequestBody CreateTodoRequest createTodoRequest){
        return todoService.createNewTodo(createTodoRequest);
    }

    @PatchMapping("")
    @Operation(summary = "특정 유저의 오늘의 할일 상태 변경")
    public int updateTodoStatus(@RequestBody UpdateTodoStatusRequest updateTodoStatusRequest){
        return todoService.updateTodoStatus(updateTodoStatusRequest);
    }
}

