package com.alchemint.bo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by cheng on 2018/12/13.
 */
@Document(collection = "person")
public class Person implements Serializable{

    @Id
    private String id;

    @Indexed
    private String name;

    private  int age;

    private  Double mount;

    private List<String> addrs;

    private Date time;

    public Person() {

    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Double getMount() {
        return mount;
    }

    public void setMount(Double mount) {
        this.mount = mount;
    }

    public List<String> getAddrs() {
        return addrs;
    }

    public void setAddrs(List<String> addrs) {
        this.addrs = addrs;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", mount=" + mount +
                ", addrs=" + addrs +
                ", time=" + time +
                '}';
    }
}
