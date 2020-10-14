package com.exademy.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exademy.R;
import com.exademy.model.QuestionAnswer;
import com.exademy.utility.Constant;
import com.exademy.utility.TypefaceUtility;

import java.util.List;

public class QuestionAnswerAdapter extends RecyclerView.Adapter<QuestionAnswerAdapter.QuestionAnswerViewHolder> {
    Context context;
    List<QuestionAnswer> questionAnswerList;
    TypefaceUtility tfUtil;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public class QuestionAnswerViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_container;
        TextView tv_question, tv_answer;

        public QuestionAnswerViewHolder(View itemView) {
            super(itemView);
            ll_container = itemView.findViewById(R.id.ll_container);
            tv_question = itemView.findViewById(R.id.tv_question);
            tv_answer = itemView.findViewById(R.id.tv_answer);

            tv_question.setTypeface(tfUtil.getTypefaceRegular());
            tv_answer.setTypeface(tfUtil.getTypefaceRegular());
        }
    }

    public QuestionAnswerAdapter(Context context, List<QuestionAnswer> questionAnswerList) {
        this.context = context;
        this.questionAnswerList = questionAnswerList;
        tfUtil = new TypefaceUtility(context);
        sp = context.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    @Override
    public QuestionAnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_question_answer, parent, false);
        return new QuestionAnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final QuestionAnswerViewHolder holder, int position) {
        final QuestionAnswer questionAnswer=questionAnswerList.get(position);

        holder.tv_question.setText(questionAnswer.getQuestion());
        holder.tv_answer.setText(questionAnswer.getAnswer());
    }

    @Override
    public int getItemCount() {
        return questionAnswerList.size();
    }

}
