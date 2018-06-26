package com.example.demotopic03.controllers;


import com.example.demotopic03.models.Book;
import com.example.demotopic03.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping({"/index", "/", "home"})
//    @RequestMapping(value = {"/index", "/", "/home"}, method = RequestMethod.GET)
    public String index(ModelMap model) {

        List<Book> bookList = this.bookService.getAll();

        model.addAttribute("books", bookList);

        return "book/index";
    }


    @GetMapping("view/{id}")
    public String view(@PathVariable("id") Integer id, Model model) {
        System.out.println("ID: " + id);


        Book book = this.bookService.findOne(id);

        model.addAttribute("book", book);

        return "book/view-detail";

    }


    @GetMapping("/update/{book_id}")
    public String showFormUpdate(@PathVariable Integer book_id, ModelMap modelMap) {
        System.out.println("ID to update: " + book_id);

        Book book = this.bookService.findOne(book_id);

        modelMap.addAttribute("book", book);

        return "book/update";
    }


    @PostMapping("update/submit")
    public String updateSubmit(@ModelAttribute Book book, MultipartFile file) {
        System.out.println(book);


        File path = new File("/btb");

        if (!path.exists()) {
            path.mkdirs();
        }

        String filename = file.getOriginalFilename();
        String extension = filename.substring(filename.lastIndexOf('.') + 1);
        System.out.println(filename);
        System.out.println(extension);

        filename = UUID.randomUUID() + "." + extension;
        try {
            Files.copy(file.getInputStream(), Paths.get("/btb", filename));
        } catch (IOException e) {

        }

        if (!file.isEmpty())
            book.setThumbnail("/images-btb/" + filename);

        this.bookService.update(book);
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {

        this.bookService.delete(id);

        return "redirect:/index";
    }

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("book", new Book());
        return "book/create-book";
    }

    @PostMapping("/create/submit")
    public String create(@Valid Book book, BindingResult bindingResult, MultipartFile file) {

        if (file == null) {
            return null;
        }
        File path = new File("/btb");

        if (!path.exists()) {
            path.mkdirs();
        }

        String filename = file.getOriginalFilename();
        String extension = filename.substring(filename.lastIndexOf('.') + 1);
        System.out.println(filename);
        System.out.println(extension);

        filename = UUID.randomUUID() + "." + extension;
        try {
            Files.copy(file.getInputStream(), Paths.get("/btb", filename));
        } catch (IOException e) {

        }

        book.setThumbnail("/images-btb/" + filename);
        System.out.println(filename);

        if (bindingResult.hasErrors()) {

            return "book/create-book";
        }
        System.out.println(book);
        this.bookService.create(book);
        return "redirect:/index";
    }
}
