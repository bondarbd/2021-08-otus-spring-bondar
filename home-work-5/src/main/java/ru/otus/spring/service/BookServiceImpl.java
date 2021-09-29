package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import java.util.List;

/**
 * @author Denis Bondar (/bondarbd)
 */
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final IOService ioService;
    private final BookDao bookDao;
    private final NumberReaderService readerService;

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public Book findById(long id) {
        return bookDao.findById(id);
    }

    @Override
    public void update(Book book) {
        String newTitle = ioService.read("Введите новое название книги");
        book.setTitle(newTitle);
        bookDao.update(book);
        ioService.write("Книга успешно изменена");
    }

    @Override
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }

    @Override
    public void create(){
        String bookTitle = ioService.read("Введите название книги");
        Author author = authorService.getAuthor();
        Genre genre = genreService.getGenre();
        Book newBook = Book.builder().title(bookTitle).genre(genre).author(author).build();
        bookDao.create(newBook);
        ioService.write("Книга успешно создана");
    }

    @Override
    public long count() {
        return bookDao.count();
    }
}
