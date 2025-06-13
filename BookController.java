package com.kiet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // ✅ Redirect root "/" to "/books"
    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/books";
    }

    // ✅ List all books
    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "book-list";
    }

    // ✅ Show form to add a new book
    @GetMapping("/books/new")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "book-form";
    }

    // ✅ Handle book form submission
    @PostMapping("/books")
    public String addBook(@ModelAttribute Book book) {
        bookRepository.save(book);
        return "redirect:/books";
    }

    // ✅ Show form to edit an existing book
    @GetMapping("/books/edit/{id}")
    public String showEditBookForm(@PathVariable int id, Model model) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            model.addAttribute("book", optionalBook.get());
            return "book-form";
        } else {
            return "redirect:/books";
        }
    }

    // ✅ Delete a book
    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        bookRepository.deleteById(id);
        return "redirect:/books";
    }

    // ✅ Filter available books
    @GetMapping("/books/available")
    public String getAvailableBooks(Model model) {
        List<Book> availableBooks = bookRepository.findByAvailableTrue();
        model.addAttribute("books", availableBooks);
        return "book-list";
    }

    // ✅ Filter books by author
    @GetMapping("/books/author/{name}")
    public String getBooksByAuthor(@PathVariable String name, Model model) {
        List<Book> booksByAuthor = bookRepository.findByAuthor(name);
        model.addAttribute("books", booksByAuthor);
        return "book-list";
    }
}

