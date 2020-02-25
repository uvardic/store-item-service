package store.server.category.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.server.category.domain.Category;
import store.server.category.service.CategoryService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("shop/server/category")
public class CategoryRestController {

    private final CategoryService categoryService;

    @DeleteMapping("/delete/existingId={existingId}")
    public ResponseEntity<?> deleteById(@PathVariable Long existingId) {
        categoryService.deleteById(existingId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Category> save(@Valid @RequestBody Category categoryRequest) {
        return new ResponseEntity<>(categoryService.save(categoryRequest), HttpStatus.CREATED);
    }

    @PutMapping("/update/existingId={existingId}")
    public ResponseEntity<Category> update(
            @PathVariable Long existingId, @Valid @RequestBody Category categoryRequest
    ) {
        return new ResponseEntity<>(categoryService.update(existingId, categoryRequest), HttpStatus.OK);
    }

    @GetMapping("/id={id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> findAll() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

}
