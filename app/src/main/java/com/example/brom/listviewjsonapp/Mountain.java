package com.example.brom.listviewjsonapp;

public class Mountain
{
    private String
            name,
            location,
            type;
    private float height;

    private int
            size,
            id,
            cost;

    private String
            imgPath,
            url;



    public String GetName()
    {
        return name;
    }

    public String GetLocation()
    {
        return location;
    }

    public float GetHeight()
    {
        return height;
    }

    public Mountain(String name, String location, float height)
    {
        this.name = name;
        this.location = location;
        this.height = height;
    }

    public Mountain(String name, String location, float height,
                    String type, int id, int size, int cost, String imgPath,
                    String url)
    {
        this.name = name;
        this.location = location;
        this.height = height;
        this.type = type;
        this.id = id;
        this.size = size;
        this.cost = cost;
        this.imgPath = imgPath;
        this.url = url;
    }

    public String GetType() {
        return type;
    }

    public int GetSize() {
        return size;
    }

    public int GetId() {
        return id;
    }

    public int GetCost() {
        return cost;
    }

    public String GetImgPath() {
        return imgPath;
    }

    public String GetUrl() {
        return url;
    }

    @Override
    public String toString()
    {
        return name;
    }

}
