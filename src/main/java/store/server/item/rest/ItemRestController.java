package store.server.item.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.server.item.domain.Item;
import store.server.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("store/server/item")
public class ItemRestController {

    private final ItemService itemService;

    @DeleteMapping("/delete/existingId={existingId}")
    public ResponseEntity<?> deleteById(@PathVariable Long existingId) {
        itemService.deleteById(existingId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Item> save(@Valid @RequestBody Item itemRequest) {
        return new ResponseEntity<>(itemService.save(itemRequest), HttpStatus.CREATED);
    }

    @PutMapping("/update/existingId={existingId}")
    public ResponseEntity<Item> update(@PathVariable Long existingId, @Valid @RequestBody Item itemRequest) {
        return new ResponseEntity<>(itemService.update(existingId, itemRequest), HttpStatus.OK);
    }

    @GetMapping("/id={id}")
    public ResponseEntity<Item> findById(@PathVariable Long id) {
        return new ResponseEntity<>(itemService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Item>> findAll() {
        return new ResponseEntity<>(itemService.findAll(), HttpStatus.OK);
    }

}
