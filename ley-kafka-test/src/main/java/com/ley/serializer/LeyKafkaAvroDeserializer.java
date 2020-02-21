package com.ley.serializer;

import example.avro.User;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author Leigh Yu
 * @date 2020/2/21 17:29
 */
public class LeyKafkaAvroDeserializer <T extends SpecificRecordBase> implements Deserializer<T> {
    @Override
    public T deserialize(String topic, byte[] data) {

        if (data == null){
            return null;
        }
        try {
            //得到主题对应的数据类型
//            TopicEnum topicEnum = TopicEnum.getTopicEnum(topic);
//            if (topicEnum == null){
//                return null;
//            }
//          SpecificRecordBase record = topicEnum.getRecord();
            SpecificRecordBase record = new User();
            DatumReader<T> datumReader = new SpecificDatumReader<>(record.getSchema());
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            BinaryDecoder decoder = DecoderFactory.get().directBinaryDecoder(byteArrayInputStream,null);
            return datumReader.read(null, decoder);

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
