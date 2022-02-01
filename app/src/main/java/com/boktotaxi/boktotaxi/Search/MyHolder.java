package com.boktotaxi.boktotaxi.Search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.boktotaxi.boktotaxi.R;


/**
 * Created by Oclemmy on 4/2/2016 for ProgrammingWizards Channel.
 */
public class MyHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView nameTxt;
    TextView rattingTxt;

    RecyclerOnItemClickListener recyclerOnItemClickListener;

    public MyHolder(View itemView) {
        super(itemView);

        nameTxt= (TextView) itemView.findViewById(R.id.tv_name);
        rattingTxt= (TextView) itemView.findViewById(R.id.tv_ratting);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.recyclerOnItemClickListener.onItemClick(v,getLayoutPosition());
    }

    public void setRecyclerOnItemClickListener(RecyclerOnItemClickListener rc)
    {
        this.recyclerOnItemClickListener=rc;
    }
}
