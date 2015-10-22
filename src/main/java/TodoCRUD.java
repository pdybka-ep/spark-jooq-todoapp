import com.example.db.tables.Category;
import com.example.db.tables.Task;
import com.example.db.tables.records.CategoryRecord;
import com.example.db.tables.records.TaskRecord;
import org.apache.commons.dbcp.BasicDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;


import java.util.List;

import static spark.Spark.*;

/**
 * Created by pdybka on 15.10.15.
 */
public class TodoCRUD {

    public static void main(String[] args) throws Exception {

        final BasicDataSource ds = new BasicDataSource();
        // configure the connection to database
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/sparktodoapp");
        ds.setUsername("xxx");
        ds.setPassword("xxx");

        DSLContext dsl = DSL.using(ds, SQLDialect.POSTGRES);


        get("/hello", (request, response) -> "Hello world");

        //CREATE
        post("/tasks", (request, response) -> {

            CategoryRecord category = dsl
                    .selectFrom(Category.CATEGORY)
                    .where(Category.CATEGORY.NAME.equal(request.queryParams("name")))
                    .fetchOne();

            String title = request.queryParams("title"); // select title from the form
            String description = request.queryParams("description"); // select description from the form


            TaskRecord taskRecord = dsl.newRecord(Task.TASK);
            taskRecord.setTitle(title);
            taskRecord.setDescription(description);
            taskRecord.setIsDone(false);
            taskRecord.setCategoryId(category.getId());
            taskRecord.store();

            return taskRecord;

        });

        // READ
        get("/tasks", (request, response) -> {

            List<TaskRecord> todoList = dsl.select(Task.TASK.ID, Task.TASK.TITLE, Task.TASK.DESCRIPTION,
                    Task.TASK.IS_DONE, Task.TASK.CATEGORY_ID)
                    .from(Task.TASK)
                    .fetchInto(TaskRecord.class);

            return todoList;

        });

        //UPDATE
        put("/tasks/:id", (request, response) -> {

            TaskRecord task = dsl
                    .selectFrom(Task.TASK)
                    .where(Task.TASK.ID.equal(Task.TASK.ID.getDataType().convert(request.params(":id"))))
                    .fetchOne();

            //if found
            if (task != null) {

                //retrieve new data for todolist
                String newTitle = request.queryParams("title");
                String newDescription = request.queryParams("description");
                task.setTitle(newTitle);
                task.setDescription(newDescription);
                task.update();

            } else {
                response.status(404);
            }

            return "Example updated";
        });

        //DELETE
        delete("/tasks/:id", (request, response) -> {

            TaskRecord task = dsl
                    .deleteFrom(Task.TASK)
                    .where(Task.TASK.ID.equal(Task.TASK.ID.getDataType().convert(request.params(":id"))))
                    .returning()
                    .fetchOne();

            if (task != null) {
                return "Example with id: " + task.getId() + " deleted";
            } else {
                response.status(404);
                return "Example not found";
            }

        });


    }
}
