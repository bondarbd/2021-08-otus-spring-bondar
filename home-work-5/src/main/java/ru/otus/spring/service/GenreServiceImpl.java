package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.model.Genre;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Denis Bondar (/bondarbd)
 */
@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {
    
    private final GenreDao genreDao;
    private final NumberReaderService readerService;
    private final IOService ioService;

    @Override
    public Genre getGenre(){
        String genreName = ioService.read("Введите жанр для поиска");
        List<Genre> allByGenreNameContain = genreDao.findAllByGenreTypeContain(genreName);
        if(allByGenreNameContain.isEmpty()){
            ioService.write("Запрашиваемый жанр не найден");
            return createGenre();
        }
        else if(allByGenreNameContain.size() > 1){
            return selectGenre(allByGenreNameContain);
        }
        Genre foundGenre = allByGenreNameContain.get(0);
        ioService.write("Жанр найден и успешно выбран: " + foundGenre.getFormattedString());
        return foundGenre;

    }

    private Genre createGenre(){
        String genreType = ioService.read("Введите жанр создания новой записи");
        Genre genre = Genre.builder().genreType(genreType).build();
        long insertGenreId = genreDao.insert(genre);
        return genreDao.findById(insertGenreId);
    }

    private Genre selectGenre(List<Genre> genreList){
        ioService.write("По введенным Вами данным, найдены следующие жанры: ");
        genreList.forEach(genre -> ioService.write(genre.getFormattedString()));
        Long[] availableIndex = genreList.stream()
                .map(Genre::getId)
                .collect(Collectors.toList())
                .toArray(Long[]::new);
        ioService.write("Выбор жанра осуществляется по номеру");
        Long selectedGenreId  = readerService.readNumber(availableIndex);
        Genre selectedGenre = genreList.stream().filter(genre -> selectedGenreId.equals(genre.getId())).findFirst().orElseThrow();
        ioService.write("Автор успешно выбран");
        return selectedGenre;
    }
}
