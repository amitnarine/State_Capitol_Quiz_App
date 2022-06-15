package edu.uga.cs.project4v1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PastQuizzes  extends AppCompatActivity {


    private RecyclerView recyclerView;
    private QuizResultRecyclerAdapter recyclerAdapter;

    private quizData quizData = null;
    private List<QuizResult> pastQuestions;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {


        super.onCreate( savedInstanceState );
        setContentView( R.layout.pastquizzes );

        recyclerView = findViewById( R.id.list );

        // use a linear layout manager for the recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        /**
        * Create a JobLeadsData instance, since we will need to save a new JobLead to the dn.
        * Note that even though more activites may create their own instances of the JobLeadsData
        *  class, we will be using a single instance of the JobLeadsDBHelper object, since
        * that class is a singleton class.
         */

        quizData = new quizData( this );
        //startQuiz s = new startQuiz();

        /**
         * Execute the retrieval of the job leads in an asynchronous way
         * without blocking the main UI thread.
         */

        new QuizResultDBReader().execute();

    }

    /**
     * This is an AsyncTask class (it extends AsyncTask)
     * to perform DB reading of job leads, asynchronously.
     */
    private class QuizResultDBReader extends AsyncTask<Void, Void, List<QuizResult>> {

        /**
         * This method will run as a background process to read from db.
         * It returns a list of retrieved JobLead objects.
         * It will be automatically invoked by Android, when we call the execute method
         *  in the onCreate callback (the job leads review activity is started).
         * @param params
         */
        @Override
        protected List<QuizResult> doInBackground( Void... params ) {
            quizData.open();
            pastQuestions = quizData.retrieveAllQuizzes();
            quizData.close();

            //Log.d( DEBUG_TAG, "JobLeadDBReaderTask: Job leads retrieved: " +pastQuestionsList.size() );

            return pastQuestions;
        }

        /**
         *  This method will be automatically called by Android once the db reading
         * It returns a list of retrieved JobLead objects.
         *  background process is finished.  It will then create and set an adapter to provide
         *  values for the RecyclerView.
         *  onPostExecute is like the notify method in an asynchronous method call discussed in class.
         * @param quizResultList
         */
        @Override
        protected void onPostExecute( List<QuizResult> quizResultList ) {
            recyclerAdapter = new QuizResultRecyclerAdapter( quizResultList );
            recyclerView.setAdapter( recyclerAdapter );
        }
    }


}
