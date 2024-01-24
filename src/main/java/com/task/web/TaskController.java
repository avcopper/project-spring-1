package com.task.web;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import com.task.dao.TaskDAO;
import com.task.domain.Task;
import javax.validation.Valid;
import com.task.domain.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

@Controller
@RequestMapping("/")
@Getter @Setter
@PropertySource("classpath:prop/app.properties")
public class TaskController {
    @Value("${page.size}")
    private Integer pageSize;
    private final TaskDAO taskDAO;

    public TaskController(@Autowired TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @ModelAttribute
    public void setStatuses(Model model) {
        model.addAttribute("statuses", Arrays.asList(Status.values()));
    }

    @GetMapping("/")
    public String getAll(Model model, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer count) {
        page = Objects.isNull(page) ? 0 : (Math.max(page - 1, 0));
        count = Objects.isNull(count) ? pageSize : count;

        Pageable pageable = Pageable.ofSize(count).withPage(page);
        Page<Task> chunk = taskDAO.findAll(pageable);
        Integer totalPages = chunk.getTotalPages();
        List<Task> tasks = chunk.toList();

        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentCount", count);
        model.addAttribute("currentPage", page + 1);
        model.addAttribute("tasks", tasks);
        model.addAttribute("task", new Task());
        return "index";
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> edit(@PathVariable Integer id) {
        if (Objects.isNull(id)) throw new EmptyResultDataAccessException("Task not found", 1);

        Task task = taskDAO.findById(id).orElseThrow(
                () -> new EmptyResultDataAccessException("Task not found", 1)
        );
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> save(@Valid Task task) {
        System.out.println(task);
        taskDAO.save(task);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        if (Objects.isNull(id)) throw new EmptyResultDataAccessException("Task not found", 1);

        taskDAO.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }
}
