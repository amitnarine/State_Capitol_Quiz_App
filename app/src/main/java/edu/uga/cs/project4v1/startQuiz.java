package edu.uga.cs.project4v1;



import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class startQuiz extends AppCompatActivity {

    public static final String MESSAGE_TYPE = "edu.uga.cs.project4v1";

    public static int[] rememberSelections = new int[6];

    public static final String DEBUG_TAG = "NewJobLeadActivity";



    public final static String[] randomStates = new String[6];
    public final static String[] correctCapital= new String[6];
    public final static long[] questionId= new long[6];
    public final static String[] altOnelist = new String[6];
    public final static String[] altTwolist = new String[6];

    static List<questions> list;
    static int score = 0;



    static List<questions> wrongQuestions;



    private SQLiteDatabase db;

    private static quizData quizData = null;

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    ActionBar mActionBar;

    public final static Integer[] imageIds = new Integer[]{
            R.drawable.figs, R.drawable.grapes, R.drawable.heirloom_tomatoes,
            R.drawable.lemons, R.drawable.lime, R.drawable.oranges
    };

    public final static String[] imageDescriptions = new String[]{
            "Figs", "Grapes", "Heirloom Tomatoes",
            "Lemons", "Lime", "Oranges"
    };

    /**
     * this is what saves the state when orientation
     * is changed
     * @param  outState
     */
    @Override
    public void onSaveInstanceState( Bundle outState ) {
        super.onSaveInstanceState(outState);

        // save the list index selection
        outState.putIntArray( "androidVersionSelection",rememberSelections);

    }
    /**
     * this is what creates the activity
     * method for when overviewButton button is clicked on
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_view_pager);

        quizData = new quizData( this );

        mActionBar = getSupportActionBar();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),8 );
        mActionBar.setTitle(mSectionsPagerAdapter.getPageTitle(0));
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        if(quizData != null) {

            quizData.open();
        }





        List<questions> questionslist = quizData.retrieveAllQuestions();

        Log.d(DEBUG_TAG,"the size of question list is" + questionslist.size());

        if(quizData != null) {

            quizData.close();
        }

        if(savedInstanceState != null) {
            rememberSelections = savedInstanceState.getIntArray("androidVersionSelection");
            score = score - 1;
        } else {

            //int[] numList = new int[6];

            ArrayList numList = new ArrayList();


            Random rand = new Random();
            int num = rand.nextInt(50);

            rememberSelections[0] = num;

            randomStates[0] = questionslist.get(num).getStateName();
            correctCapital[0] = questionslist.get(num).getCapital();
            questionId[0] = questionslist.get(num).getId();
            altOnelist[0] = questionslist.get(num).getaltOne();
            altTwolist[0] = questionslist.get(num).getaltTwo();

            numList.add(num);

            for (int i = 1; i < 6; i++) {

                Boolean dup = false;


                while (dup == false) {

                    num = rand.nextInt(50);
                    if (numList.contains(num)) {


                    } else {

                        rememberSelections[i] = num;
                        numList.add(num);
                        dup = true;


                    }


                }


                randomStates[i] = questionslist.get(num).getStateName();
                correctCapital[i] = questionslist.get(num).getCapital();
                questionId[i] = questionslist.get(num).getId();
                altOnelist[i] = questionslist.get(num).getaltOne();
                altTwolist[i] = questionslist.get(num).getaltTwo();


            }

            list = questionslist;
        }


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mActionBar.setTitle(mSectionsPagerAdapter.getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        // Create a JobLeadsData instance, since we will need to save a new JobLead to the dn.
        // Note that even though more activites may create their own instances of the JobLeadsData
        // class, we will be using a single instance of the JobLeadsDBHelper object, since
        // that class is a singleton class.

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final int mSize;

        public SectionsPagerAdapter(FragmentManager fm, int size) {
            super(fm);
            this.mSize = size;
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return mSize;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            int imageNum = position + 1;
            return String.valueOf("question " + imageNum);
        }
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        private int questionNum;

        private RadioGroup radioGroup;
        private TextView question;
        private TextView state;
        private RadioButton optionone;
        private RadioButton optiontwo;
        private RadioButton optionthree;

        public static QuizResult scoreDatabase = new QuizResult();


        public static PlaceholderFragment newInstance(int questionNumber) {


            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, questionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {

                questionNum= getArguments().getInt(ARG_SECTION_NUMBER);
            } else {
                questionNum = -1;
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_simple_view_pager, container, false);

            state = rootView.findViewById( R.id.state );
            radioGroup = rootView.findViewById( R.id.radiogroup );
            question = rootView.findViewById( R.id.question );
            optionone = rootView.findViewById( R.id.optionone );
            optiontwo = rootView.findViewById( R.id.optiontwo);
            optionthree = rootView.findViewById( R.id.optionthree );


            // score logic for if one of the radio buttons was selected then the score will update by one

    radioGroup.setOnCheckedChangeListener(((group, checkedID) -> {

        if (checkedID == -1) {


        } else if (checkedID == R.id.optionone) {

            if (optionone.getText().toString().trim().equals(correctCapital[questionNum - 1])) {

                score = score + 1;
            }
        } else if (checkedID == R.id.optiontwo) {

            if (optiontwo.getText().toString().trim().equals(correctCapital[questionNum - 1])) {

                score = score + 1;
            }


        } else if (checkedID == R.id.optionthree) {

            if (optionthree.getText().toString().trim().equals(correctCapital[questionNum - 1])) {

                score = score + 1;
            }


        }
    }));

            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {



            if(savedInstanceState != null) {
                rememberSelections = savedInstanceState.getIntArray("androidVersionSelection");
            }


            super.onActivityCreated(savedInstanceState);
            if (startQuiz.class.isInstance(getActivity())) {


                if(questionNum < 7) {

                    Random rand = new Random();


                    int num = rand.nextInt(3);

                    if (num == 0) {

                        state.setText(randomStates[questionNum - 1]);

                        optionone.setText(correctCapital[questionNum - 1]);

                        Boolean found = false;

                       String check = " ";

                        while (found == false) {

                            int mix = rand.nextInt(50);

                            if (list.get(mix).getCapital() == correctCapital[questionNum - 1]) {


                            } else {
                                check = list.get(mix).getCapital();
                                optiontwo.setText(list.get(mix).getCapital());
                                found = true;

                            }

                        }

                        Boolean foundtwo = false;

                        while (foundtwo == false) {

                            int mix = rand.nextInt(50);

                            if (list.get(mix).getCapital() == correctCapital[questionNum - 1]) {


                            } else if (check.equalsIgnoreCase(list.get(mix).getCapital())) {


                            } else {

                                optionthree.setText(list.get(mix).getCapital());
                                foundtwo = true;
                            }

                        }

                        //randomStates[i] = questionslist.get(num).getStateName();

                        //optiontwo.setText(altOnelist[questionNum-1]);


                        //optionthree.setText(altTwolist[questionNum-1]);

                    }

                    if (num == 1) {

                        state.setText(randomStates[questionNum - 1]);

                        optiontwo.setText(correctCapital[questionNum - 1]);

                        Boolean found = false;
                        String check = " ";

                        while (found == false) {

                            int mix = rand.nextInt(50);

                            if (list.get(mix).getCapital() == correctCapital[questionNum - 1]) {


                            } else {
                                check = list.get(mix).getCapital();
                                optionone.setText(list.get(mix).getCapital());
                                found = true;

                            }

                        }

                        Boolean foundtwo = false;

                        while (foundtwo == false) {

                            int mix = rand.nextInt(50);

                            if (list.get(mix).getCapital() == correctCapital[questionNum - 1]) {


                            } else if(check.equalsIgnoreCase(list.get(mix).getCapital())) {


                            } else {

                                optionthree.setText(list.get(mix).getCapital());
                                foundtwo = true;
                            }

                        }


                    }

                    if (num == 2) {

                        state.setText(randomStates[questionNum - 1]);

                        optionthree.setText(correctCapital[questionNum - 1]);

                        Boolean found = false;
                        String check = " ";

                        while (found == false) {

                            int mix = rand.nextInt(50);

                            if (list.get(mix).getCapital() == correctCapital[questionNum - 1]) {


                            } else {
                                check = list.get(mix).getCapital();
                                optionone.setText(list.get(mix).getCapital());
                                found = true;

                            }

                        }

                        Boolean foundtwo = false;

                        while (foundtwo == false) {

                            int mix = rand.nextInt(50);

                            if (list.get(mix).getCapital() == correctCapital[questionNum - 1]) {


                            } else if(check.equalsIgnoreCase(list.get(mix).getCapital())) {


                            } else {

                                optiontwo.setText(list.get(mix).getCapital());
                                foundtwo = true;
                            }

                        }


                    }

                } else if (questionNum == 7) {

                    Date date = Calendar.getInstance().getTime();

                    SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

                    String fancyDate = format.format(date);

                    new QuizResultDBWriter().execute(new QuizResult(fancyDate, questionId[0],questionId[1], questionId[2], questionId[3], questionId[4],questionId[5], score + 1) );




                } else if ((questionNum > 7)) {

                    Intent intent = new Intent();
                    intent.putExtra(MESSAGE_TYPE,score);
                    intent.setClass(getActivity(),Results.class);
                    startActivity(intent);

                }
            }
        }
    }




    /**
     *This is an AsyncTask class (it extends AsyncTask) to perform DB writing of a job lead, asynchronously.
     *
     */
    public class QuestionsDBWriter extends AsyncTask<questions,Void, questions> {

        // This method will run as a background process to write into db.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onClick listener of the Save button.
        @Override
        protected questions doInBackground( questions... question ) {
            quizData.storeQuestion( question[0] );
            return question[0];
        }

        // This method will be automatically called by Android once the writing to the database
        // in a background process has finished.  Note that doInBackground returns a JobLead object.
        // That object will be passed as argument to onPostExecute.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        @Override
        protected void onPostExecute( questions question ) {
            // Show a quick confirmation message
            Toast.makeText( getApplicationContext(), "Job lead created for " + question.getStateName(),
                    Toast.LENGTH_SHORT).show();

            // Clear the EditTexts for next use.


            Log.d( DEBUG_TAG, "Job lead saved: " + question );
        }
    }

    public static class QuizResultDBWriter extends AsyncTask<QuizResult,Void, QuizResult> {

        @Override
        protected QuizResult doInBackground(QuizResult... quizResult) {
            quizData.storeQuiz(quizResult[0]);
            return quizResult[0];
        }

        // This method will be automatically called by Android once the writing to the database
        // in a background process has finished.  Note that doInBackground returns a JobLead object.
        // That object will be passed as argument to onPostExecute.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        @Override
        protected void onPostExecute(QuizResult quizResult) {
            // Show a quick confirmation message
            //Toast.makeText( getApplicationContext(), "Job lead created for " + question.getStateName(),
            //Toast.LENGTH_SHORT).show();

            // Clear the EditTexts for next use.

            super.onPostExecute(quizResult);

            Log.d(DEBUG_TAG, "Job lead saved: " + quizResult);


        }

    }






    @Override
    protected void onResume() {
        Log.d( DEBUG_TAG, "NewJobLeadActivity.onResume()" );
        // open the database in onResume
        if( quizData != null )
            quizData.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d( DEBUG_TAG, "NewJobLeadActivity.onPause()" );
        // close the database in onPause
        if( quizData != null )
            quizData.close();
        super.onPause();
    }

    // The following activity callback methods are not needed and are for
    // educational purposes only.
    @Override
    protected void onStart() {
        Log.d( DEBUG_TAG, "NewJobLeadActivity.onStart()" );
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d( DEBUG_TAG, "NewJobLeadActivity.onStop()" );
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d( DEBUG_TAG, "NewJobLeadActivity.onDestroy()" );
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d( DEBUG_TAG, "NewJobLeadActivity.onRestart()" );
        super.onRestart();
    }
}
