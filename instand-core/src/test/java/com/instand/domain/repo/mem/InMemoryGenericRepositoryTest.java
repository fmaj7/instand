package com.instand.domain.repo.mem;

import com.instand.domain.Entity;
import com.instand.domain.repo.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class InMemoryGenericRepositoryTest {

    @Data
    @AllArgsConstructor
    private static class Foo implements Entity<String> {
        String id;
        String name;
    }

    private static Foo foo1 = new Foo("00001", "hello, foo1");
    private static Foo foo2 = new Foo("00002", "hello, foo2");

    @Test
    public void testCreate() throws Exception {
        InMemoryGenericRepository<String, Foo> repo = new InMemoryGenericRepository<>();
        repo.create(foo1);
        repo.create(foo2);
        assertThat(repo.findAll().size(), equalTo(2));
    }

    @Test(expected = RuntimeException.class)
    public void testCreateWhenDuplicate() throws Exception {
        InMemoryGenericRepository<String, Foo> repo = new InMemoryGenericRepository<>();
        repo.create(foo1);
        repo.create(foo1);
    }

    @Test
    public void testFind() throws Exception {
        InMemoryGenericRepository<String, Foo> repo = new InMemoryGenericRepository<>();
        repo.create(foo1);
        repo.create(foo2);
        Optional<Foo> resultOpt = repo.find("00001");
        assertThat(resultOpt.get(), equalTo(foo1));
    }

    @Test
    public void testFindWhenNotExist() throws Exception {
        InMemoryGenericRepository<String, Foo> repo = new InMemoryGenericRepository<>();
        repo.create(foo1);
        repo.create(foo2);
        Optional<Foo> resultOpt = repo.find("00003");
        assertThat(resultOpt, equalTo(Optional.empty()));
    }

    @Test
    public void testFindOrElseThrow() throws Exception {
        InMemoryGenericRepository<String, Foo> repo = new InMemoryGenericRepository<>();
        repo.create(foo1);
        repo.create(foo2);
        Foo result = repo.findOrElseThrow("00001");
        assertThat(result, equalTo(foo1));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testFindOrElseThrowWhenNotExist() throws Exception {
        InMemoryGenericRepository<String, Foo> repo = new InMemoryGenericRepository<>();
        repo.create(foo1);
        repo.create(foo2);
        Foo result = repo.findOrElseThrow("00003");
        assertThat(result, equalTo(foo1));
    }

    @Test
    public void testFindAll() throws Exception {
        InMemoryGenericRepository<String, Foo> repo = new InMemoryGenericRepository<>();
        repo.create(foo1);
        repo.create(foo2);
        assertThat(repo.findAll().size(), equalTo(2));
    }

    @Test
    public void testUpdate() throws Exception {
        InMemoryGenericRepository<String, Foo> repo = new InMemoryGenericRepository<>();
        repo.create(foo1);
        repo.create(foo2);
        foo2.setName("foo 2");
        repo.update(foo2);
        assertThat(repo.findOrElseThrow("00002").getName(), equalTo("foo 2"));
    }

    @Test(expected = RuntimeException.class)
    public void testUpdateWhenNotExist() throws Exception {
        InMemoryGenericRepository<String, Foo> repo = new InMemoryGenericRepository<>();
        repo.create(foo1);
        repo.update(foo2);
    }

    @Test
    public void testDelete() throws Exception {
        InMemoryGenericRepository<String, Foo> repo = new InMemoryGenericRepository<>();
        repo.create(foo1);
        repo.delete(foo1);
        assertThat(repo.findAll().size(), equalTo(0));
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteWhenNotExist() throws Exception {
        InMemoryGenericRepository<String, Foo> repo = new InMemoryGenericRepository<>();
        repo.create(foo1);
        repo.delete(foo2);
    }

    @Test
    public void testExists() throws Exception {
        InMemoryGenericRepository<String, Foo> repo = new InMemoryGenericRepository<>();
        repo.create(foo1);
        assertThat(repo.exists("00001"), equalTo(true));
    }

    @Test
    public void testSave() throws Exception {
        InMemoryGenericRepository<String, Foo> repo = new InMemoryGenericRepository<>();
        repo.create(foo1);
        repo.save(foo2);
        repo.save(foo1);
        assertThat(repo.findAll().size(), equalTo(2));
    }
}