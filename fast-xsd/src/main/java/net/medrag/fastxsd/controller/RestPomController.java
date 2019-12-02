package net.medrag.fastxsd.controller;

import net.medrag.fastxsd.pom.SomeData;
import net.medrag.fastxsd.service.pom.SomeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * {@author} Stanislav Tretyakov
 * 28.11.2019
 */
@RestController
public class RestPomController {

    private SomeDataService someDataService;

    @Autowired
    public RestPomController(SomeDataService someDataService) {
        this.someDataService = someDataService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<String> saveConnectionConfiguration(@RequestBody List<SomeData> someData) {
        someDataService.saveData(someData);
        return ResponseEntity.ok("OK");
    }

    @RequestMapping(value = "/saveIndexes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public SomeData[] saveRowIndexes(@RequestBody int[] dbRequest) {
        return someDataService.saveIndexes(dbRequest);
    }
}
