package server.api;

import commons.Gamer;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.GamerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestGamerRepository implements GamerRepository {

    public final List<Gamer> gamers = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    private void call(String name) {
        calledMethods.add(name);
    }

    @Override
    public List<Gamer> findAll() {
        call("findAll");
        return gamers;
    }

    @Override
    public List<Gamer> findAll(Sort sort) {
        ArrayList<Gamer> list = new ArrayList<Gamer>();
        Gamer gamer = new Gamer("a", 5, 4);
        list.add(gamer);
        return list;
    }

    @Override
    public Page<Gamer> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Gamer> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return findAll().size();
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Gamer gamer) {
        call("delete");
        for (int i = 0; i < gamers.size(); i++) {
            if (gamers.get(i).equals(gamer)) {
                gamers.remove(i);
            }
        }
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Gamer> entities) {

    }

    @Override
    public void deleteAll() {
        for (int i = 0; i < gamers.size(); i++) {
            call("deleteAll");
            gamers.remove(i);
        }
    }

    @Override
    public <S extends Gamer> S save(S entity) {
        call("save");
        gamers.add(entity);
        return entity;
    }

    public void save(List<Gamer> entities) {
        call("save");
        for (Gamer entity : entities) {
            gamers.add(entity);
        }
    }

    @Override
    public <S extends Gamer> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Gamer> findById(Long id) {
        return gamers.stream().filter(q -> q.Id == id).findFirst();
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
    public <S extends Gamer> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Gamer> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Gamer> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Gamer getOne(Long aLong) {
        return null;
    }

    @Override
    public Gamer getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Gamer> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Gamer> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Gamer> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Gamer> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Gamer> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Gamer> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Gamer, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
