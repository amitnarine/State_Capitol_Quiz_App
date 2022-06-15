package edu.uga.cs.project4v1;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizResultRecyclerAdapter extends RecyclerView.Adapter<QuizResultRecyclerAdapter.QuizHolder>{

    public static final String DEBUG_TAG = "JobLeadRecyclerAdapter";

    private List<QuizResult> quizList;

    public QuizResultRecyclerAdapter( List<QuizResult> quizList) {
        this.quizList= quizList;
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    class QuizHolder extends RecyclerView.ViewHolder {

        TextView idQuiz;
        TextView date;
        TextView score;



        public QuizHolder (View itemView) {
            super(itemView);

            idQuiz = (TextView) itemView.findViewById( R.id.quizid);
            date = (TextView) itemView.findViewById( R.id.quizdate);
            score = (TextView) itemView.findViewById( R.id.quizscore );

        }

    }


    @Override
    public QuizHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.quizentry,  null );
        return new QuizHolder(view);
    }
    /**
     * This method fills in the values of a holder to show a quiz.
     * The position parameter indicates the position on the list of jobs list.
     * @param holder
     * @param position
     */
    // This method fills in the values of a holder to show a JobLead.
    // The position parameter indicates the position on the list of jobs list.
    @Override
    public void onBindViewHolder( QuizHolder holder, int position ) {
        QuizResult quizResult = quizList.get( position );


        String test;

        String testtwo;

        test = String.valueOf(quizResult.getId());

        testtwo  = String.valueOf(quizResult.getScore());
        holder.idQuiz.setText( "Quiz ID: " + test);
        holder.date.setText( "Quiz Date: " + quizResult.getDate() );
        holder.score.setText( "Quiz Score: " + testtwo);



    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }
}











