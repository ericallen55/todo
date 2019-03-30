package eric.todo.Services;

import eric.todo.Daos.ToDoDao;
import eric.todo.Models.ListItem;
import eric.todo.Models.ToDoList;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static java.util.UUID.randomUUID;

@Component
public class ToDoListService {
    private final ToDoDao toDoDao;

    public ToDoListService(ToDoDao toDoDao) {
        this.toDoDao = toDoDao;
    }

    public ToDoList createNewToDoList(ToDoList toDoList) {
        toDoList.setId(randomUUID().toString());
        return toDoDao.addToDoList(toDoList);
    }

    public List<ToDoList> getAllLists() {
        return toDoDao.getAllLists();
    }

    public ToDoList getListById(String id) {
        ToDoList toDoList = toDoDao.getListById(id);
        if (toDoList == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "id not found"
            );
        }
        return toDoList;
    }

    public ToDoList addListItem(String id, List<ListItem> listItems) {
        return toDoDao.addListItem(id, listItems);
    }

    public ToDoList deleteListItem(String id, String listItemId) {
        return toDoDao.deleteListItem(id, listItemId);
    }

    public ToDoList markListItemComplete(String id, List<ListItem> listItems) {
        return toDoDao.markListItemComplete(id, listItems);
    }

}
