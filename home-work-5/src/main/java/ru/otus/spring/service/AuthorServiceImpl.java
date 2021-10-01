package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.model.Author;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Denis Bondar (/bondarbd)
 */
@Service
@AllArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;
    private final NumberReaderService readerService;
    private final IOService ioService;

    @Override
    public Author getAuthor(){
        String authorName = ioService.read("Введите имя автора для поиска: ");
        List<Author> allByAuthorNameContain = authorDao.findAllByAuthorNameContain(authorName);
        if(allByAuthorNameContain.isEmpty()){
            ioService.write("Запрашиваемый автор не найден");
            return createAuthor();
        }
        else if(allByAuthorNameContain.size() > 1){
            return selectAuthor(allByAuthorNameContain);
        }

        Author author = allByAuthorNameContain.get(0);
        ioService.write("Автор найден и успешно выбран: " + author.getFormattedString());
        return author;

    }

    private Author createAuthor(){
        String authorName = ioService.read("Введите ФИО автора для создания новой записи");
        Author author = Author.builder().authorName(authorName).build();
        long insertAuthorId = authorDao.insert(author);
        return authorDao.findById(insertAuthorId);
    }

    private Author selectAuthor(List<Author> authorList){
        ioService.write("По введенным Вами данным, найдены следующие авторы: ");
        authorList.forEach(author -> ioService.write(author.getFormattedString()));
        Long[] availableIndex = authorList.stream()
                .map(Author::getId)
                .collect(Collectors.toList())
                .toArray(Long[]::new);
        ioService.write("Выбор автора осуществляется по номеру");
        Long selectedAuthorId  = readerService.readNumber(availableIndex);
        return authorList.stream().filter(author -> selectedAuthorId.equals(author.getId())).findFirst().orElseThrow();
    }



}
