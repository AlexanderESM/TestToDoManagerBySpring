package net.ork.testtodomanagerbyspring.controller;

import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import net.ork.testtodomanagerbyspring.model.Task;
import net.ork.testtodomanagerbyspring.repository.TaskRepository;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
/*
    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "tasks/tasks";
    }
    */

    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "tasks/tasks";
    }


    @GetMapping("/create")
    public String createTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "tasks/create_task";
    }

    // Обработка сохранения задачи через веб-форму
    @PostMapping
    public String saveTask(@Valid @ModelAttribute Task task, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // Если есть ошибки валидации, верните форму с ошибками
            return "tasks/create_task"; // Возврат на форму с отображением ошибок
        }

        // Если ошибок нет, сохраняем задачу в базе данных
        taskRepository.save(task);
        return "redirect:/tasks"; // Перенаправление на страницу со списком задач
    }

    @GetMapping("/edit/{id}")
    public String editTaskForm(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskRepository.findById(id).orElseThrow());
        return "tasks/edit_task";
    }

    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task updatedTask) {
        Task task = taskRepository.findById(id).orElseThrow();
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        taskRepository.save(task);
        return "redirect:/tasks"; // Перенаправление на список задач
    }

    // Удаление задачи
    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id); // Удаляем задачу по ID
        return "redirect:/tasks"; // Перенаправление на страницу со списком задач
    }
}
