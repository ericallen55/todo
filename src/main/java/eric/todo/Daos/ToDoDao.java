package eric.todo.Daos;

import eric.todo.Models.ListItem;
import eric.todo.Models.ToDoList;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.UUID.randomUUID;

@Component
public class ToDoDao {
    Map<String, ToDoList> toDoListMap = new HashMap<>();

    public ToDoList addToDoList(ToDoList toDoList) {
        toDoListMap.put(toDoList.getId(), createLineItemId(toDoList));
        return toDoListMap.get(toDoList.getId());
    }

    public List<ToDoList> getAllLists() {
        return new ArrayList<>(toDoListMap.values());
    }

    public ToDoList getListById(String id) {
        return toDoListMap.get(id);
    }

    public ToDoList addListItem(String id, List<ListItem> listItems) {
        ToDoList toDoList = getListById(id);
        if(toDoList == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "id not found"
            );
        }
        createLineItemIds(listItems);
        List<ListItem> temp = listItems;
        temp.addAll(toDoList.getItems());
        toDoList.setItems(temp);
        return getListById(id);
    }

    public ToDoList deleteListItem(String id, String listItemId){
        ToDoList toDoList = getListById(id);
        toDoList.getItems().removeIf(item -> item.getId().equals(listItemId));
        return toDoList;
    }

    public ToDoList markListItemComplete(String id, List<ListItem> listItems){
        ToDoList toDoList = getListById(id);
        listItems.forEach(listItem -> toDoList.getItems().forEach(item -> {
            if(item.getId().equals(listItem.getId())){
                item.setComplete(listItem.isComplete());
                return;
            }
        }));
        return toDoList;
    }

    private void createLineItemIds(List<ListItem> listItems){
        listItems.forEach(item->item.setId(randomUUID().toString()));
    }

    private ToDoList createLineItemId(ToDoList toDoList){
        List<ListItem> items = toDoList.getItems();
        createLineItemIds(items);
        return toDoList;
    }
}
