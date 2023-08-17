package ru.tsc.almadapter.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tsc.almadapter.dto.Root;
import ru.tsc.almadapter.service.DataService;

@Api(tags = "Загрузка данных в справочник")
@RestController
@RequestMapping("/api/v1/data")
public class DataController {

    private final DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @ApiOperation(value = "Загрузка и обработка данных")
    @PostMapping("/process")
    public String processData(@RequestBody Root root) {
        dataService.processAndSaveRecords(root);
        return "Data processed successfully!";
    }
}
