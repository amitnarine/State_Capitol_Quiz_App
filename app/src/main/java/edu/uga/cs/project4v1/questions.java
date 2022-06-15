package edu.uga.cs.project4v1;

public class questions {


    private long   id;
    private String stateName;
    private String capital;
    private String altOne;
    private String altTwo;

    public questions()
    {
        this.id = -1;
        this.stateName = null;
        this.capital = null;
        this.altOne = null;
        this.altTwo = null;
    }

    public questions( String state, String capital, String alt1, String alt2 ) {
        this.id = -1;  // the primary key id will be set by a setter method
        this.stateName = state;
        this.capital = capital;
        this.altOne = alt1;
        this.altTwo = alt2;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getStateName()
    {
        return stateName;
    }

    public void setStateName(String stateName)
    {
        this.stateName = stateName;
    }

    public String getCapital()
    {
        return capital;
    }

    public void setCapital(String capital)
    {
        this.capital = capital;
    }

    public String getaltOne()
    {
        return altOne;
    }

    public void setaltOne(String altOne)
    {
        this.altOne = altOne;
    }

    public String getaltTwo()
    {
        return altTwo;
    }

    public void setaltTwo(String altTwo)
    {
        this.altTwo = altTwo;
    }

    public String toString()
    {
        return id + ": " + stateName+ " " + capital + " " + altOne+ " " + altTwo;
    }

}
