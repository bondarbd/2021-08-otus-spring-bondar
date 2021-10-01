package ru.otus.spring.controller;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.model.Book;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.NumberReaderService;

import java.util.List;

/**
 * @author Denis Bondar (/bondarbd)
 */

@ShellComponent
@AllArgsConstructor
public class BookController {

    private final BookService bookService;
    private final IOService ioService;
    private final NumberReaderService numberReaderService;

    @ShellMethod(key = {"-findAllBooks", "--all"}, value = "Show all books")
    public void findAllBooks() {
        List<Book> allBooks = bookService.findAll();
        allBooks.forEach(book -> ioService.write(book.getFormattedString()));
    }

    @ShellMethod(key = {"-add"}, value = "Add book to library")
    public void addBook() {
        bookService.create();
    }

    @ShellMethod(key = {"-findById", "--find"}, value = "Get book by id")
    public void findBookById() {
        long id = numberReaderService.readNumber(Long.MAX_VALUE);
        ioService.write(bookService.findById(id).getFormattedString());
    }

    @ShellMethod(key = {"-deleteById", "--delete"}, value = "Delete book by id")
    public void deleteBookById() {
        long id = numberReaderService.readNumber(Long.MAX_VALUE);
        bookService.deleteById(id);
    }

    @ShellMethod(key = {"-updateById", "--update"}, value = "Update book by id")
    public void updateBook() {
        ioService.write("Выберите номер книги для изменений");
        long readNumber = numberReaderService.readNumber(Long.MAX_VALUE);
        Book bookToUpdate = bookService.findById(readNumber);
        ioService.write("Выбранная книга: " + bookToUpdate.getFormattedString() );
        bookService.update(bookToUpdate);
    }

    @ShellMethod(key = {"-count", "--c"}, value = "Count of all books")
    public void bookCount() {
        ioService.write("Количество книг: " + bookService.count());
    }

}
