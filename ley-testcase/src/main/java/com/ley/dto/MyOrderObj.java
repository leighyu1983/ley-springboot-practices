package com.ley.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
@ToString
@AllArgsConstructor
public class MyOrderObj implements Comparable<MyOrderObj> {
    private int age;
    private String name;

    @Override
    public int compareTo(MyOrderObj o) {
        log.info("this {}  another {}", this.name, o.name);
        //return this.age - o.age; // ascending
        //return o.age - this.age; // descending
        //return this.name.compareTo(o.name); // ascending
        //return o.name.compareTo(this.name); // descending

        // desending
//        if(this.name == null && o.name != null) {
//            return 1;
//        } else if (this.name != null && o.name == null) {
//            return -1;
//        } else if (this.name == null && o.name == null) {
//            return 0; // no change
//        } else {
//            int r = o.name.compareTo(this.name) ;
//            return r;
//        }

        // ascending
        if(this.name == null && o.name != null) {
            return -1;
        } else if (this.name != null && o.name == null) {
            return 1;
        } else if (this.name == null && o.name == null) {
            return 0; // no change
        } else {
            int r = this.name.compareTo(o.name) ;
            return r;
        }
    }
}
