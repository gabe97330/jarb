package org.jarb.populator.excel.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

/**
 * In memory representation of entities from a specific type.
 * 
 * @author Jeroen van Schagen
 * @since 09-05-2011
 * 
 * @param <T> type of entities being maintained
 */
public class EntityTable<T> implements Iterable<T> {
    /** Class of entities being stored **/
    private final Class<T> entityClass;
    /** Map based container of all stored entities **/
    private Map<Object, T> entitiesMap = new HashMap<Object, T>();
    
    /**
     * Construct a new {@link EntityTable}.
     * @param entityClass class of entities stored in this table
     */
    public EntityTable(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Retrieve the class of entities stored in this table.
     * @return entity class
     */
    public Class<T> getEntityClass() {
        return entityClass;
    }
    
    /**
     * Retrieve an entity based on its identifier.
     * @param id identifier of the entity
     * @return entity matching or identifier, if any
     */
    public T find(Object id) {
        return entitiesMap.get(id);
    }
    
    /**
     * Determine if an entity with the specified identifier exists.
     * @param id identifier of the entity
     * @return {@code true} whenever the entity exists
     */
    public boolean exists(Object id) {
        return entitiesMap.containsKey(id);
    }
    
    /**
     * Count the number of stored entities.
     * @return number of stored entities
     */
    public int count() {
        return entitiesMap.size();
    }

    /**
     * Check if there are no stored entites.
     * @return {@code true} if the table is empty, else {@code false}
     */
    public boolean isEmpty() {
        return entitiesMap.isEmpty();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<T> iterator() {
        return entitiesMap.values().iterator();
    }

    /**
     * Retrieve every entity, in {@link List} format.
     * @return list representation of each stored entity
     */
    public List<T> list() {
        return Collections.unmodifiableList(new ArrayList<T>(entitiesMap.values()));
    }

    /**
     * Retrieve every entity, in {@link Map} format.
     * @return map representation of each stored entity
     */
    public Map<Object, T> map() {
        return Collections.unmodifiableMap(entitiesMap);
    }

    /**
     * Store a specific entity inside this object.
     * @param id identifier of the entity
     * @param entity reference to the entity
     */
    public void add(Object id, T entity) {
        Assert.notNull(id, "Cannot store an entity using a null identifier");
        Assert.notNull(id, "Cannot store a null entity");
        entitiesMap.put(id, entity);
    }

    /**
     * Remove a specific entity.
     * @param id identifier of the entity
     * @return removed entity
     */
    public T remove(Object id) {
        return entitiesMap.remove(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return entitiesMap.toString();
    }

}
