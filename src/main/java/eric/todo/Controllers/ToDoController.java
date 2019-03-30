package eric.todo.Controllers;

import eric.todo.Models.ListItem;
import eric.todo.Models.ToDoList;
import eric.todo.Services.ToDoListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ToDoController {
    private final ToDoListService toDoListService;

    ToDoController(ToDoListService toDoListService){
        this.toDoListService = toDoListService;
    }

    @PostMapping("list")
    ToDoList newList(@RequestBody ToDoList toDoList){
        return toDoListService.createNewToDoList(toDoList);
    }

    @GetMapping("list")
    List<ToDoList> getAllLists(){
        return toDoListService.getAllLists();
    }

    @GetMapping("list/{id}")
    ToDoList getListById(@PathVariable("id") String id){
        return toDoListService.getListById(id);
    }

    @PostMapping("list/{id}")
    ToDoList addListItem(@PathVariable("id") String id, @RequestBody List<ListItem> items){
        return toDoListService.addListItem(id, items);
    }

    @DeleteMapping("list/{id}/itemId/{itemId}")
    ToDoList deleteListItem(@PathVariable("id") String id, @PathVariable("itemId") String itemId){
        return toDoListService.deleteListItem(id, itemId);
    }

    @PutMapping("list/{id}/markcomplete")
    ToDoList markComplete(@PathVariable("id") String id, @RequestBody List<ListItem> items){
        return toDoListService.markListItemComplete(id, items);
    }
}
