package server.api;

import commons.Game;
import commons.Gamer;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.GameRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestGameRepository implements GameRepository {

    public final List<Game> games = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    private void call(String name) {
        calledMethods.add(name);
    }

    @Override
    public List<Game> findAll() {
        call("findAll");
        return games;
    }

    @Override
    public List<Game> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Game> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Game> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return games.size();
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Game entity) {

    }

    public void delete(Gamer gamer) {
        call("delete");
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).equals(gamer)) {
                games.remove(i);
            }
        }
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Game> entities) {

    }

    @Override
    public void deleteAll() {
        for (int i = 0; i < games.size(); i++) {
            call("deleteAll");
            games.remove(i);
        }
    }

    @Override
    public <S extends Game> S save(S entity) {
        call("save");
        games.add(entity);
        return entity;
    }

    public void save(List<Game> entities) {
        call("save");
        for (Game game : entities) {
            games.add(game);
        }
    }

    @Override
    public <S extends Game> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Game> findById(Long id) {
        return games.stream().filter(q -> q.id == id).findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        call("existsById");
        return findById(id).isPresent();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Game> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Game> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Game> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Game getOne(Long aLong) {
        return null;
    }

    @Override
    public Game getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Game> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Game> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Game> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Game> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Game> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Game> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Game, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
