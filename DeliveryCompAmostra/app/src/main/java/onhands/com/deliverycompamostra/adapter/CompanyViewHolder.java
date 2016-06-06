package onhands.com.deliverycompamostra.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import onhands.com.deliverycompamostra.R;

/**
 * Created by rodrigocavalcante on 5/30/16.
 */
public class CompanyViewHolder extends RecyclerView.ViewHolder {

    public final View mView;
    @Nullable @BindView (R.id.name) TextView name;
    @Nullable @BindView (R.id.image) ImageView image;
    @Nullable @BindView (R.id.separator) View separator;

    public CompanyViewHolder(View view) {
        super(view);
        mView = view;
        ButterKnife.bind(this, view);
    }

    @Override
    public String toString() {
        return super.toString() + ": " + name.getText();
    }
}
