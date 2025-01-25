package com.ChenDll.classroommanagement.classroom;

public class Classroom {
    private int id;               // 课室ID
    private String name;          // 课室名称
    private int capacity;         // 容量
    private boolean hasMultimedia; // 是否有多媒体
    private boolean isAvailable;  // 是否可用

    public Classroom(int id, String name, int capacity, boolean hasMultimedia, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.hasMultimedia = hasMultimedia;
        this.isAvailable = isAvailable;
    }

    // Getter 和 Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isHasMultimedia() {
        return hasMultimedia;
    }

    public void setHasMultimedia(boolean hasMultimedia) {
        this.hasMultimedia = hasMultimedia;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", hasMultimedia=" + hasMultimedia +
                ", isAvailable=" + isAvailable +
                '}';
    }
}