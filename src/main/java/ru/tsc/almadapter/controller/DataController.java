package ru.tsc.almadapter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tsc.almadapter.entity.DataEntity;
import ru.tsc.almadapter.dto.Root;
import ru.tsc.almadapter.service.DataService;

@RestController
@RequestMapping("/api/data")
public class DataController {

    private final DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    //метод для обработки и сохранения записей
    @PostMapping("/process")
    public String processData(@RequestBody Root root) {
        try {
            dataService.processAndSaveRecords(root);
            return "Data processed successfully!";
        } catch (Exception e) {
            return "An error occurred: " + e.getMessage();
        }
    }


    // метод для сохранения данных
//    @PostMapping("/save")
//    public DataEntity saveFromJson(@RequestBody Root root) {
//        return dataService.saveFromJson(root);
//    }

    /*
    @GetMapping("/{id}")
    public DataEntity getDataById(@PathVariable Long id) {
        return dataService.findById(id);
    }

    @PutMapping("/{id}")
    public DataEntity updateData(@PathVariable Long id, @RequestBody DataEntity dataEntity) {
        return dataService.update(id, dataEntity);
    }

    @DeleteMapping("/{id}")
    public void deleteData(@PathVariable Long id) {
        dataService.delete(id);
    }
    */
}
