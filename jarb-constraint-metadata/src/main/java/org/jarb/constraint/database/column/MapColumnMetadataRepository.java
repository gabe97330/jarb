package org.jarb.constraint.database.column;

import static org.apache.commons.lang.StringUtils.lowerCase;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

/**
 * Map based column constraint repository. Matching is made case insensitve by
 * storing, and searching, based on lowercased character sequences.
 * 
 * @author Jeroen van Schagen
 * @since 30-05-2011
 */
public class MapColumnMetadataRepository implements ColumnMetadataRepository {
    private final Map<String, Map<String, ColumnMetadata>> columnMetadataMap;

    /**
     * Construct a new {@link MapColumnMetadataRepository}.
     */
    public MapColumnMetadataRepository() {
        columnMetadataMap = new HashMap<String, Map<String, ColumnMetadata>>();
    }

    /**
     * Store a column constraint inside this repository.
     * @param columnMetadata column constraint that should be added
     * @return the same repository instance, for chaining
     */
    public MapColumnMetadataRepository store(ColumnMetadata columnMetadata) {
        Assert.notNull(columnMetadata, "Cannot store null pointer column metadata");
        Map<String, ColumnMetadata> tableSpecificColumnMetadataMap = columnMetadataMap.get(lowerCase(columnMetadata.getTableName()));
        if (tableSpecificColumnMetadataMap == null) {
            tableSpecificColumnMetadataMap = new HashMap<String, ColumnMetadata>();
            columnMetadataMap.put(lowerCase(columnMetadata.getTableName()), tableSpecificColumnMetadataMap);
        }
        tableSpecificColumnMetadataMap.put(lowerCase(columnMetadata.getColumnName()), columnMetadata);
        return this;
    }

    /**
     * Remove all stored constraints from this repository.
     * @return the same repository instance, for chaining
     */
    public MapColumnMetadataRepository removeAll() {
        columnMetadataMap.clear();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ColumnMetadata getColumnMetadata(String tableName, String columnName) {
        ColumnMetadata columnMetadata = null;
        if (columnMetadataMap.containsKey(lowerCase(tableName))) {
            columnMetadata = columnMetadataMap.get(lowerCase(tableName)).get(lowerCase(columnName));
        }
        return columnMetadata;
    }

}
