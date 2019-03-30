package eric.todo.Services;

import eric.todo.Daos.ToDoDao;
import eric.todo.Models.ListItem;
import eric.todo.Models.ToDoList;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ToDoListServiceTest {
    ToDoDao toDoDao = new ToDoDao();
    ToDoListService toDoListService = new ToDoListService(toDoDao);
    List<ListItem> listItems;

    @Before
    public void init(){
        listItems = new ArrayList<>();
        listItems.add(ListItem.builder()
                .complete(false)
                .item("item1")
                .build());
        listItems.add(ListItem.builder()
                .complete(true)
                .item("item2")
                .build());
        toDoDao.addToDoList(ToDoList.builder()
                .id("id1")
                .name("name1")
                .items(listItems)
                .build());
        toDoDao.addToDoList(ToDoList.builder()
                .id("id2")
                .name("name2")
                .items(listItems)
                .build());
    }

    @Test
    public void getAllLists(){
       List<ToDoList> toDoLists = toDoListService.getAllLists();
        assertNotNull(toDoLists);
        assertEquals(toDoLists.get(1).getId(), "id1");
        assertEquals(toDoLists.get(1).getName(), "name1");
        assertEquals(toDoLists.get(1).getItems().get(0).isComplete(), false);
        assertEquals(toDoLists.get(1).getItems().get(0).getItem(), "item1");
        assertEquals(toDoLists.get(1).getItems().get(1).isComplete(), true);
        assertEquals(toDoLists.get(1).getItems().get(1).getItem(), "item2");
        assertEquals(toDoLists.get(0).getId(), "id2");
        assertEquals(toDoLists.get(0).getName(), "name2");
        assertEquals(toDoLists.get(0).getItems().get(0).isComplete(), false);
        assertEquals(toDoLists.get(0).getItems().get(0).getItem(), "item1");
        assertEquals(toDoLists.get(0).getItems().get(1).isComplete(), true);
        assertEquals(toDoLists.get(0).getItems().get(1).getItem(), "item2");
    }

    @Test
    public void createNewToDoList(){
        List<ListItem> listItems = new ArrayList<>();
        listItems.add(ListItem.builder()
                .complete(false)
                .item("item1")
                .build());
        ToDoList toDoList = toDoListService.createNewToDoList(ToDoList.builder()
                .id("id1")
                .name("name1")
                .items(listItems)
                .build());

        assertEquals(toDoList.getName(), "name1");
        assertEquals(toDoList.getItems().get(0).isComplete(), false);
        assertEquals(toDoList.getItems().get(0).getItem(), "item1");
    }

    @Test
    public void getListById()
    {
        ToDoList toDoList = toDoListService.getListById("id2");
        assertEquals(toDoList.getId(), "id2");
        assertEquals(toDoList.getName(), "name2");
    }

    @Test
    public void addListItem(){
        List<ListItem> listItems = new ArrayList<>();
        listItems.add(ListItem.builder()
                .item("item3")
                .build());
        listItems.add(ListItem.builder()
                .item("item4")
                .build());
        ToDoList toDoList = toDoListService.addListItem("id1", listItems);
        assertEquals(toDoList.getItems().get(0).getItem(), "item3");
        assertEquals(toDoList.getItems().get(1).getItem(), "item4");
        assertEquals(toDoList.getItems().get(2).getItem(), "item1");
        assertEquals(toDoList.getItems().get(3).getItem(), "item2");
        assertNotNull(toDoList.getItems().get(0).getId());
        assertNotNull(toDoList.getItems().get(1).getId());
        assertNotNull(toDoList.getItems().get(2).getId());
        assertNotNull(toDoList.getItems().get(3).getId());
        assertEquals(toDoList.getItems().get(0).isComplete(), false);
        assertEquals(toDoList.getItems().get(1).isComplete(), false);
        assertEquals(toDoList.getItems().get(2).isComplete(), false);
        assertEquals(toDoList.getItems().get(3).isComplete(), true);
    }

    @Test
    public void deleteListItem(){
        ToDoList toDoList = toDoListService.getListById("id1");
        String id = toDoList.getItems().get(0).getId();
        toDoList = toDoListService.deleteListItem("id1", id);
        assertEquals(toDoList.getItems().size(), 1);
        assertNotEquals(toDoList.getItems().get(0).getId(), id);
    }

    @Test
    public void markListItemComplete(){
        ToDoList toDoList = toDoListService.getListById("id1");
        List<ListItem> listItems = new ArrayList<>();
        listItems.add(ListItem.builder()
                .id(toDoList.getItems().get(0).getId())
                .complete(true)
                .build());
        listItems.add(ListItem.builder()
                .complete(false)
                .id(toDoList.getItems().get(1).getId())
                .build());
        toDoList = toDoListService.markListItemComplete("id1", listItems);
        assertEquals(toDoList.getItems().get(0).isComplete(), true);
        assertEquals(toDoList.getItems().get(1).isComplete(), false);
    }
}
