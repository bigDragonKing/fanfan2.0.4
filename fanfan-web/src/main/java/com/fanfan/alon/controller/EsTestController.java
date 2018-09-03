package com.fanfan.alon.controller;

import com.fanfan.alon.core.ElasticSearchPage;
import com.fanfan.alon.models.Book;
import com.fanfan.alon.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/es")
public class EsTestController {
    @Autowired
    private BookService bookService;

    @PostMapping(value = "/save/{id}")
    ResponseEntity<String> saveBook(@PathVariable("id") String id, @RequestBody Book book){
        return new ResponseEntity<String>(bookService.saveDoc(id,book), HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}")
    ResponseEntity<String> getBook(@PathVariable("id") String id){
        return new ResponseEntity<String>(bookService.getIdx(id), HttpStatus.OK);
    }

    @GetMapping(value = "/getByWord")
    ResponseEntity<ElasticSearchPage<Book>> getBookByWord(@RequestParam("keyWord") String keyWord){
        return new ResponseEntity<ElasticSearchPage<Book>>(bookService.getBooksByWords(keyWord), HttpStatus.OK);
    }

    @PutMapping(value = "/createMapping")
    ResponseEntity<String> createMapping(){
        return new ResponseEntity<String>(bookService.createMapping(Book.class), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteBook/{id}")
    ResponseEntity<String> deleteBook(@PathVariable("id") String id){
        return new ResponseEntity<>(bookService.deleteById(id),HttpStatus.OK);
    }
}
