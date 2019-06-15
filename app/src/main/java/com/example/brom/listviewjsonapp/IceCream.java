package com.example.brom.listviewjsonapp;

public class IceCream
{
    private String name;

    private int price;
    private int size;
    private int grades;
    private int kidsGrades;

    private String pros;
    private String cons;
    private String tagline;
    private String imagePath;


    public IceCream(String name, String tagline)
    {
        this.name = name;
        this.tagline = tagline;
    }

    public IceCream(String name, int price, int size, int grades,
                    int kidsGrades, String tagline, String pros,
                    String cons, String imageLink)
    {
        this.name = name;
        this.price = price;
        this.size = size;
        this.grades = grades;
        this.kidsGrades = kidsGrades;
        this.tagline = tagline;
        this.pros = pros;
        this.cons = cons;
        this.imagePath = imageLink;
    }

    public String GetName()
    {
        return name;
    }

    public int GetPrice() {
        return price;
    }

    public int GetSize() {
        return size;
    }

    public int GetGrades() {
        return grades;
    }

    public int GetKidsGrades() {
        return kidsGrades;
    }

    public String GetTagline()
    {
        return tagline;
    }

    public String GetPros() {
        return pros;
    }

    public String GetCons() {
        return cons;
    }

    public String GetImagePath() {
        return imagePath;
    }


    @Override
    public String toString()
    {
        return name;
    }

}
