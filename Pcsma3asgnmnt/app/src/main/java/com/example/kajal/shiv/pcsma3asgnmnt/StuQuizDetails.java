package com.example.kajal.shiv.pcsma3asgnmnt;

/**
 * Created by IIITD on 3/13/2016.
 */
public class StuQuizDetails
  {
    private String rollNumber;
    private String Name;
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    private String email;
    private String response;
    public String getRollNumber()
    {
        return rollNumber;
    }
    public void setRollNumber(String rollNumber)
    {
        this.rollNumber = rollNumber;
    }
    public String getName()
    {
        return Name;
    }
    public void setName(String name)
    {
        Name = name;
    }
    public String getResponse()
    {
        return response;
    }
    public void setResponse(String response)
    {
        this.response = response;
    }
  }