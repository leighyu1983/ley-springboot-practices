package com.ley.serializer;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Leigh Yu
 * @date 2020/2/21 17:24
 */
public class LeyKafkaAvroSerializer <T extends SpecificRecordBase> implements Serializer<T> {

    @Override
    public byte[] serialize(String topic, T data) {
        if (data == null){
            return null;
        }
        DatumWriter<T> writer = new SpecificDatumWriter<>(data.getSchema());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BinaryEncoder encoder = EncoderFactory.get().directBinaryEncoder(outputStream,null);
        try {
            writer.write(data,encoder);
        }catch (IOException e){
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
}
