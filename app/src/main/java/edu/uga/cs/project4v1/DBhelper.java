package edu.uga.cs.project4v1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;

// nov 1st

public class DBhelper extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "JobLeadsDBHelper";

    private static final String DB_NAME = "project.db";
    private static final int DB_VERSION = 14; //keep updating until right

    /** Define all names (strings) for table and column names.
     This will be useful if we want to change these names later.
     */
    public static final String TABLE_QUESTIONS = "Question";
    public static final String QUESTIONS_QUESTION_ID = "QuestionID";
    public static final String QUESTIONS_STATE_NAME = "State";
    public static final String QUESTIONS_CAPITAL = "capital";
    public static final String QUESTIONS_ALT_ONE = "alt1";
    public static final String QUESTIONS_ALT_TWO = "alt2";


    public static final String TABLE_QUIZZES = "Quizzes";
    public static final String QUIZZES_ID= "QuestionID";
    public static final String QUIZZES_DATE= "Date";
    public static final String QUIZZES_SCORE = "Score";
    public static final String QUIZZES_QUESTION_ONE = "q1";
    public static final String QUIZZES_QUESTION_TWO = "q2";
    public static final String QUIZZES_QUESTION_THREE = "q3";
    public static final String QUIZZES_QUESTION_FOUR = "q4";
    public static final String QUIZZES_QUESTION_FIVE = "q5";
    public static final String QUIZZES_QUESTION_SIX = "q6";


    // This is a reference to the only instance for the helper.
    private static DBhelper helperInstance;

    /**
     * A Create table SQL statement to create a table for job leads.
     * Note that _id is an auto increment primary key, i.e. the database will
     * automatically generate unique id values as keys.
     */
    private static final String CREATE_QUESTIONS =
            "create table " + TABLE_QUESTIONS + " ( "
                    + QUESTIONS_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUESTIONS_STATE_NAME + " TEXT, "
                    + QUESTIONS_CAPITAL + " TEXT, "
                    + QUESTIONS_ALT_ONE + " TEXT, "
                    + QUESTIONS_ALT_TWO + " TEXT "
                    + ")";



    /**
     * A Create table SQL statement to create a table for job leads.
     * Note that _id is an auto increment primary key, i.e. the database will
     * automatically generate unique id values as keys.
     */
    private static final String CREATE_QUIZZES =
            "create table " + TABLE_QUIZZES + " ("
                    + QUIZZES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUIZZES_DATE + " TEXT, "
                    + QUIZZES_SCORE + " INTEGER NOT NULL, "
                    + QUIZZES_QUESTION_ONE + " INTEGER NOT NULL, "
                    + QUIZZES_QUESTION_TWO + " INTEGER NOT NULL, "
                    + QUIZZES_QUESTION_THREE + " INTEGER NOT NULL, "
                    + QUIZZES_QUESTION_FOUR + " INTEGER NOT NULL, "
                    + QUIZZES_QUESTION_FIVE + " INTEGER NOT NULL, "
                    + QUIZZES_QUESTION_SIX + " INTEGER NOT NULL, "
                    + " FOREIGN KEY ("+ QUIZZES_QUESTION_ONE +") REFERENCES "+ TABLE_QUESTIONS+"("+QUESTIONS_QUESTION_ID +"),"
                    + " FOREIGN KEY ("+ QUIZZES_QUESTION_TWO +") REFERENCES "+ TABLE_QUESTIONS+"("+QUESTIONS_QUESTION_ID +"),"
                    + " FOREIGN KEY ("+ QUIZZES_QUESTION_THREE +") REFERENCES "+ TABLE_QUESTIONS+"("+QUESTIONS_QUESTION_ID +"),"
                    + " FOREIGN KEY ("+ QUIZZES_QUESTION_FOUR +") REFERENCES "+ TABLE_QUESTIONS+"("+QUESTIONS_QUESTION_ID +"),"
                    + " FOREIGN KEY ("+ QUIZZES_QUESTION_FIVE +") REFERENCES "+ TABLE_QUESTIONS+"("+QUESTIONS_QUESTION_ID +"),"
                    + " FOREIGN KEY ("+ QUIZZES_QUESTION_SIX +") REFERENCES "+ TABLE_QUESTIONS+"("+QUESTIONS_QUESTION_ID +"))";




    /**
     * Note that the constructor is private!
     * So, it can be called only from
     * this class, in the getInstance method.
     * @param context
     */

    private Context context;
    private DBhelper( Context context ) {
        super( context, DB_NAME, null, DB_VERSION );
        this.context = context;

    }

       /**
        * Access method to the single instance of the class.
        * It is synchronized, so that only one thread can executes this method.
        * @param context
          */


    public static synchronized DBhelper getInstance( Context context ) {
        // check if the instance already exists and if not, create the instance
        if( helperInstance == null ) {
            helperInstance = new DBhelper( context.getApplicationContext() );
        }
        return helperInstance;
    }


    /**
     * We must override onCreate method, which will be used to create the database if
     * it does not exist yet.
     * @param db
     */
    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL( CREATE_QUESTIONS );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUESTIONS+ " created" );
        createDb(db);

        db.execSQL( CREATE_QUIZZES );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUIZZES+ " created" );
        //createDb(db);
    }

    /**
     * Method to populate our database
     * @param db
     */
    public void createDb(SQLiteDatabase db){
        try {
            // Open the CSV data file in the assets folder
            InputStream in_s = context.getAssets().open( "state_capitals.csv" );
            // get the TableLayout view
            // read the CSV data
            CSVReader reader = new CSVReader( new InputStreamReader( in_s ) );
            String[] nextLine;
            while( ( nextLine = reader.readNext() ) != null ) {
                // nextLine[] is an array of values from the line
                // create the next table row for the layout
                //for( int i = 0; i < nextLine.length; i++ ) {

                questions question = new questions(nextLine[0],nextLine[1],nextLine[2],nextLine[3]);

                // Prepare the values for all of the necessary columns in the table
                // and set their values to the variables of the JobLead argument.
                // This is how we are providing persistence to a JobLead (Java object) instance
                // by storing it as a new row in the database table representing job leads.
                ContentValues values = new ContentValues();

                values.put( DBhelper.QUESTIONS_STATE_NAME, question.getStateName() );
                values.put( DBhelper.QUESTIONS_CAPITAL, question.getCapital() );
                values.put( DBhelper.QUESTIONS_ALT_ONE, question.getaltOne() );
                values.put( DBhelper.QUESTIONS_ALT_TWO, question.getaltTwo() );
                // Insert the new row into the database table;
                // The id (primary key) is automatically generated by the database system
                // and returned as from the insert method call.
                long id = db.insert( DBhelper.TABLE_QUESTIONS, null, values );

                // store the id (the primary key) in the JobLead instance, as it is now persistent
                question.setId( id );

                Log.d( DEBUG_TAG, "Stored new job lead with id: " + String.valueOf( question.getId() ) );


                }


        } catch (Exception e) {
            //Log.e( TAG, e.toString() );
        }
    }

    /**
     * We should override onUpgrade method, which will be used to upgrade the database if
     * its version (DB_VERSION) has changed.  This will be done automatically by Android
     * if the version will be bumped up, as we modify the database schema.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "drop table if exists " + TABLE_QUESTIONS);
        onCreate(db);
        Log.d( DEBUG_TAG, "Table " + TABLE_QUESTIONS + " upgraded" );
    }
}
