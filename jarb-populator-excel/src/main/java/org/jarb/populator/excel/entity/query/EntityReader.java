package org.jarb.populator.excel.entity.query;

import org.jarb.populator.excel.entity.EntityRegistry;

/**
 * Queries all entities from the database.
 * @author Jeroen van Schagen
 * @since 10-05-2011
 */
public interface EntityReader {

    /**
     * Retrieve all entities from the database.
     * @return registry containing every entities
     */
    EntityRegistry fetchAll();

    /**
     * Retrieve all entities, with a specific type, from the database.
     * @param entityClasses type of entities to retrieve
     * @return registry containing all entities of the specified types
     */
    EntityRegistry fetch(Iterable<Class<?>> entityClasses);

}