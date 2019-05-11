package com.wearewaes.assignment.cache.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 * This class abstracts all the information for a difference
 * evaluation, the values and differences
 * It uses Lombok to remove unnecessary code (get/setter, equal and hashcode, etc)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiffCacheEntry implements DataSerializable {

    private String id;
    private String leftValue;
    private String rightValue;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CacheDiff> diffs;

    public DiffCacheEntry(String id, String leftValue, String rightValue) {
        this.id = id;
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }

    /**
     * Required by Hazelcast to deserialize object
     * @param out deserialize object
     * @throws IOException in case serialization failed
     */
    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        StringWriter payloadWriter = new StringWriter();
        ObjectMapper payloadMapper = new ObjectMapper();
        payloadMapper.writeValue(payloadWriter, this);
        out.writeUTF(payloadWriter.toString());
    }

    /**
     * Required by Hazelcast to serialize object
     * @param in serialized object
     * @throws IOException in case deserialization failed
     */
    @Override
    public void readData(ObjectDataInput in) throws IOException {
        String payload = in.readUTF();
        ObjectMapper payloadMapper = new ObjectMapper();
        DiffCacheEntry deserializeCacheEntry = payloadMapper.readValue(payload, DiffCacheEntry.class);
        this.setId(deserializeCacheEntry.getId());
        this.setLeftValue(deserializeCacheEntry.getLeftValue());
        this.setRightValue(deserializeCacheEntry.getRightValue());
        this.setDiffs(deserializeCacheEntry.getDiffs());
    }
}
