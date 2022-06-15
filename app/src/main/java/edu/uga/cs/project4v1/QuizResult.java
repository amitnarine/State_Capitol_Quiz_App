package edu.uga.cs.project4v1;

public class QuizResult {

    private long   id;
    private String date;
    private long q1;
    private long q2;
    private long q3;
    private long q4;
    private long q5;
    private long q6;
    private long score;


    public QuizResult()
    {

    }

    public QuizResult(String date,long score)
    {
       this.date = date;
       this.score = score;
    }

    public QuizResult( String date, long q1, long q2, long q3,long q4,long q5,long q6,long score ) {
        this.id = 1;  // the primary key id will be set by a setter method
        this.date = date;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.q6 = q6;
        this.score = score;
    }


    public String getDate()
    {

        return date;
    }
    public void setDate(String date)
    {

        this.date = date;
    }

    public long getScore()
    {

        return score;
    }
    public void setScore(long score)
    {

        this.score = score;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public long getQ1()
    {

        return q1;
    }
    public void setQ1(long q1)
    {

    this.q1 = q1;
    }

    public long getQ2()
    {

        return q2;
    }
    public void setQ2(long q2)
    {

        this.q2 = q2;
    }

    public long getQ3()
    {

        return q3;
    }
    public void setQ3(long q3)
    {

        this.q3 = q3;
    }


    public long getQ4()
    {

        return q4;
    }
    public void setQ4(long q4)
    {

        this.q4 = q4;
    }

    public long getQ5()
    {

        return q5;
    }
    public void setQ5(long q5)
    {

        this.q5 = q5;
    }

    public long getQ6()
    {

        return q6;
    }
    public void setQ6(long q6)
    {

        this.q6 = q6;
    }






}
