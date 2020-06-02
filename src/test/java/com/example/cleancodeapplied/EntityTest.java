package com.example.cleancodeapplied;

import com.example.cleancodeapplied.entities.Entity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EntityTest {
    @Test
    void twoDifferentEntitysAreNotTheSame() {
        Entity e1 = new Entity();
        Entity e2 = new Entity();

        e1.setId("u1Id");
        e2.setId("u2Id");

        assertThat(e1.isSame(e2)).isFalse();
        assertThat(e2.isSame(e1)).isFalse();
    }

    @Test
    void oneEntityIsSameAsItself() {
        Entity u1 = new Entity();
        u1.setId("u1Id");

        assertThat(u1.isSame(u1)).isTrue();
    }

    @Test
    void EntitysWithTheSameIdAreTheSame() {
        Entity e1 = new Entity();
        Entity e2 = new Entity();

        e1.setId("u1Id");
        e2.setId("u1Id");

        assertThat(e1.isSame(e2)).isTrue();
    }

    @Test
    void EntityWithNullIdsAreNeverSame() {
        Entity e1 = new Entity();
        Entity e2 = new Entity();

        assertThat(e1.isSame(e2)).isFalse();
    }
}