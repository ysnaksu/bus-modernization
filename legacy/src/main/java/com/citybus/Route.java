package com.citybus;

public class Route {

    private int id;
    private String name;
    private String startStop;
    private String endStop;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStartStop() { return startStop; }
    public void setStartStop(String startStop) { this.startStop = startStop; }

    public String getEndStop() { return endStop; }
    public void setEndStop(String endStop) { this.endStop = endStop; }
}
